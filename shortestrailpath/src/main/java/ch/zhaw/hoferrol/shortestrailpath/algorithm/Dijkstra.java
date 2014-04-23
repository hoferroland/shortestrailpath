package ch.zhaw.hoferrol.shortestrailpath.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpStatusEnum;
import ch.zhaw.hoferrol.shortestrailpath.topologie.NeighbourCalculator;

public class Dijkstra {

	// Variabeln
	// Map zur Speicherung aller BpHelper drin (key = id_Bp)
	private static Map<Long, BpHelper> allBpMap;
	// Liste wo alle BpHelper abgelegt werden, die noch nicht
	// vom Dijkstra bearbeitet wurden
	private static List<BpHelper> redBpList;
	// Liste mit allen BpHelpern, welche vom Dijkstra
	// bearbeitet wurden
	private static List<BpHelper> greenBpList;

	private List<BpHelper> shortestBpList;
	private Set<BetriebspunktVerbindungen> shortestBpVerbList;
	private static List<BpHelper> shortestPath = new ArrayList<BpHelper>();
	private static BpHelper zielBpHelper;
	private static BpHelper nextBpHelper;
	private static BpHelper nextHelper;
	private static BpHelper next2Helper;

	// Konstruktor Dijkstra mit Übergabe der BpHelper-Map
	public Dijkstra(Map<Long, BpHelper> bpHelperMap) {

		// Map mit allen BpHelpern drin
		allBpMap = bpHelperMap;
		// Initialisierung der Listen
		redBpList = new ArrayList<BpHelper>();
		greenBpList = new ArrayList<BpHelper>();
		shortestBpList = new ArrayList<BpHelper>();
		shortestBpVerbList = new HashSet<BetriebspunktVerbindungen>();

		// Iterator über alle BpHelper. Dabei wird der Status
		// aller BpHelper zu 'rot' gesetzt
		Iterator it = allBpMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = (Object) it.next();
			BpHelper helper = (BpHelper) allBpMap.get(key);
			helper.setStatus(BpStatusEnum.rot);
		}
	}

	public void cleanUp() {
		// alle Listen leeren
		allBpMap.clear();
		redBpList.clear();
		greenBpList.clear();
		shortestBpList.clear();
		shortestBpVerbList.clear();
	}

	// Methode um Status eines BpHelpers zu grün zu setzen.
	// BpHelper wird in der redBpList-Array gelöscht. Falls der Status
	// des Bp-Helpers 'rot' ist, hinzufügen des BpHelpers zur
	// greenBpList-Array. Anschliessend den Status des BpHelpers auf 'gruen'
	// setzen.
	public void changeBpToGreen(BpHelper bpHelperBp) {
		redBpList.remove(bpHelperBp);
		if (bpHelperBp.getStatus().equals(BpStatusEnum.gruen)) {
			greenBpList.add(bpHelperBp);
		}
		bpHelperBp.setStatus(BpStatusEnum.gruen);
	}

	// Methode um Status eines BpHelpers zu rot zu setzen.
	// BpHelper wird in die redBpList aufgenommen und in der
	// greenBpList entfernt
	public static void changeBpToRed(BpHelper bpHelperBp) {
		if (bpHelperBp.getStatus().equals(BpStatusEnum.rot)) {
			redBpList.add(bpHelperBp);
			greenBpList.remove(bpHelperBp);
		}
		bpHelperBp.setStatus(BpStatusEnum.rot);
	}

	// Methode um zwei BpHelper aufgrund der Distanzen zu sortieren
	static Comparator<BpHelper> sortByDistanz = new Comparator<BpHelper>() {
		public int compare(BpHelper helperBp1, BpHelper helperBp2) {
			return Long.signum((helperBp1.getDistanzZumStart() - helperBp2
					.getDistanzZumStart()));
		}
	};

	// Work-Methode. Mit dem Dijkstra-Algorithmus wird der ganze Graph durch-
	// laufen und zu den jeweiligen BpHelpern der 'optimale' Vorgänger sowie
	// die Distanz zum Start-BpHelper ermittelt.
	// Der Methode werden folgende Parameter übergeben
	// - HashMap mit allen BetriebspunktVerbindungen (key = id_BpVerbindung)
	// - BpHelper welcher als Startpunkt übergeben wird
	// - HashMap mit allen BpHelpern (key = id_BpHelper = id_Betriebspunkt)
	public List<BpHelper> work(Map<Long, BetriebspunktVerbindungen> graph,
			BpHelper bpHelperStart, Map<Long, BpHelper> allBpMap) {

		// BpHelper werden vorbereitet. Dazu wird über allBpMap iteriert
		Iterator<Long> iter = allBpMap.keySet().iterator();
		while (iter.hasNext()) {
			Object key = (Object) iter.next();
			BpHelper helper = (BpHelper) allBpMap.get(key);
			// Alle BpHelper erhalten eine Distanangabe von unendlich
			helper.setDistanzZumStart(Integer.MAX_VALUE);
			// Alle BpHelper haben keinen Vorgänger. Allfällig alte Daten
			// werden gelöscht
			helper.setBpVorher(null);
			// Alle BpHelper erhalten den Status-Wert 'rot'
			helper.setStatus(BpStatusEnum.rot);
			if (helper.getNext() != null) {
				// Alle BpHelper werden der redBpList zugeordnet, falls.
				// dieser einen 'Nachbar-BpHelper' hat
				redBpList.add(helper);
			} else {
				// ansonsten wird der BpHelper aus der allBpMap entfernt.
				// (Abfangen Fal, falls Bp im ohne Bp-Verbindung in Topologie
				// wäre)
				allBpMap.remove(helper);
				// (Test und Debug) Ausgabe von gelöschten BpHelpern auf der
				// Konsole
				System.out.println("BpHelper ohne Nachbar: "
						+ helper.bp.getAbkuerzung() + " wurde gelöscht");
			}
		}

		// Massnahmen für den Startknoten (initial)
		bpHelperStart.setStatus(BpStatusEnum.rot);
		redBpList.add(bpHelperStart);

		// Spezielle Einstellungen für den Startpunkt
		// Der Status des Startpunktes wird true gesetzt (=grün)
		changeBpToGreen(bpHelperStart);
		// Die Distanz zu seinem Vorgänger wird zu 0 gesetzt
		bpHelperStart.setDistanzZumStart(0L);
		// Dem Startknoten wird er selbst zu seinem Vorgänger definiert
		bpHelperStart.setBpVorher(bpHelperStart.bp);

		// Definition Abbruchbedingung für den schlechtesten Fall für Dijkstra
		// n * n
		int worstCase = allBpMap.size() * allBpMap.size();

		// Dijkstra-Schleife um Vorgänger und Distanzen zu berechnen
		for (int i = 0; i < worstCase; i++) {

			// Erster Schleifendurchgang mit Startpunkt
			if (i == 0) {
				// Für alle NachfolgeBetriebspunkte von 'bpHelperStart' mache
				for (Betriebspunkt next : bpHelperStart.getNext()) {
					// Ausgabe des aktuell zu bearbeitenden Betriebspunktes auf
					// Konsole
					// (Test und Debug)
					// System.out.println("Aktuell betrachteter Bp "
					// + next.getAbkuerzung());

					// berechne Distanz vom start zum Nachbar-Betriebspunkt
					long distanzBpHelperStartToNext = NeighbourCalculator
							.getDistanz(bpHelperStart.bp.getId_betriebspunkt(),
									next.getId_betriebspunkt());

					// Ausgabe der Distanz vom Start zum nächsten Betriebspunkt
					// auf
					// Konsole
					// (Test und Debug)
					System.out.println("Distanz vom Start zum nächsten Bp "
							+ distanzBpHelperStartToNext);

					// Setze Distanz zum nächsten Betriebsknoten
					nextHelper = allBpMap.get(next.getId_betriebspunkt());
					nextHelper.setDistanzZumStart(distanzBpHelperStartToNext);

					// Setze bpHelperStart als Vorgänger ein
					nextHelper.setBpVorher(bpHelperStart.bp);

					// Ausgabe des Vorgänger-bpHelpers (hier bpHelperStart) auf
					// Konsole
					// (Test und Debug)
					// System.out.println("Vorgängerknoten = " + bpHelperStart);
				}
			}

			// Abbruch der Schleife, wenn redBpList leer ist
			// sprich alle BpHelper besucht wurden
			if (redBpList.size() == 0) {
				break;
			}

			// ArrayList 'redBpList' sortieren nach Distanz
			Collections.sort(redBpList, sortByDistanz);

			// Ausgabe der neu sortierten ArrayList 'redBpList' auf Konsole
			// (Test und Debug)
			// System.out.println("Restliche BpHelper sortiert: " + redBpList);

			// Setze den BpHelper mit der kleinsten DistanzZumStart = ArrayList
			// redBpList pos[0] als nextBp, also zum neuen AusgangsBpHelper
			// für den nächsten Schleifendurchlauf
			BpHelper nextBp = redBpList.get(0);

			// Ausgabe des nextBp (neuen AusgangsBpHelper für den nächsten
			// Schleifendurchlauf) auf Konsole
			// (Test und Debug)
			System.out.println("-----------------------------------------");
			System.out.println("Roter BpHelper mit kleinster Distanz: "
					+ nextBp.getBp().getBezeichnung() + ", ("
					+ nextBp.getBp().getAbkuerzung() + ")");

			// Wechsle den neuen AusgangsBpHelper (nextBp) zu grün
			changeBpToGreen(nextBp);

			// Für alle NachfolgeBetriebspunkte (next2) von nextBp mache
			for (Betriebspunkt next2 : nextBp.getNext()) {
				if (nextBp.getNext().size() == 0) {
					break;
				}

				// Abfangen des Falles wenn ein Nachbar-Bp gesucht wird, welcher
				// im Ausland liegt und deshalb keine Topologiedaten hat
				if (allBpMap.containsKey(nextBp.bp.getId_betriebspunkt())) {
					next2Helper = allBpMap.get(next2.getId_betriebspunkt());
				} else {
					break;
				}

				// Abfangen des Fall wenn ein Nachbar-Bp gesucht wird, welcher
				// im Ausland liegt und deshalb keine Topologiedaten hat
				if (allBpMap.containsKey(next2.getId_betriebspunkt())) {
					next2Helper = allBpMap.get(next2.getId_betriebspunkt());
				} else {
					break;
				}

				// Ausgabe des neuen 'next2'Betriebspunktes auf Konsole
				// (Test und Debug)
				System.out.println("Nächster Betriebspunkt: "
						+ next2Helper.bp.getAbkuerzung());

				// berechne Distanz zwischen 'next2' und 'nextBp'
				long distanzBpHelperVonZu = NeighbourCalculator.getDistanz(
						nextBp.bp.getId_betriebspunkt(),
						next2Helper.bp.getId_betriebspunkt());

				// Ausgabe der eruierten Distanz zwischen 'next2' und 'nextBp'
				// auf Konsole
				// (Test und Debug)
				System.out.println("Distanz: " + distanzBpHelperVonZu);

				// Addiere neu eruierte Distanz zu bereits bestehender Distanz
				// vom 'start'BpHelper zum 'nextBp'-BpHelper.
				long tmpDistanz = (nextBp.getDistanzZumStart() + distanzBpHelperVonZu);

				// Ausgabe einer Warnmeldung auf der Konsole, falls neu
				// berechnete Distanz
				// grösser dem max-Wert ist
				// (Test und Debug)
				if (tmpDistanz >= Integer.MAX_VALUE) {
					System.out.println("Achtung hier stimmt was nicht!");
				}

				// Falls die neu berechnete Distanz (tmpDistanz) kleiner ist
				// als die next2-Distanz, setze die neue Distanz und den
				// Vorgänger beim 'next2'-BpHelper ein
				if (tmpDistanz <= next2Helper.getDistanzZumStart()) {
					next2Helper.setDistanzZumStart(tmpDistanz);
					next2Helper.setBpVorher(nextBp.bp);
					allBpMap.put(next2Helper.bp.getId_betriebspunkt(),
							next2Helper);

					// Ausgabe des Schleifenzählers, des Betriebspunktes 'next2
					// sowie des
					// BpHelpers 'nextBp'
					// (Test und Debug)
					// System.out.println("i: " + i + ", nextw: " + next2Helper
					// + ", nextBp: " + nextBp.bp.getAbkuerzung());
				}

				// Wechsle den neuen Ausgangs-BpHelper (nextBp) zu grün
				changeBpToGreen(nextBp);
			}
		}

		// Alle BpHelper mit ihrem 'optimalen' Vorgänger und der Entfernung zum
		// Start auf Konsole ausgeben
		// (Test und Debug)
		// System.out.println("Betriebspunkt - Vorgänger - Entfernung");
		//
		// Iterator ite = allBpMap.keySet().iterator();
		// while (ite.hasNext()) {
		// Object key = (Object) ite.next();
		// BpHelper helperPrint = (BpHelper) allBpMap.get(key);
		// System.out.println(helperPrint.bp.getAbkuerzung() + "     -      "
		// + helperPrint.getBpVorher().getAbkuerzung()
		// + "      -      " + helperPrint.getDistanzZumStart());
		// }

		// Alle roten BpHelper auf Konsole ausgeben (sollte nach Durchlaufen von
		// Dijkstra immer 'alle besucht' kommen.
		// Falls nicht, rote Knoten ausgeben
		System.out.println("Rote Betriebspunkte");
		if (redBpList.size() == 0) {
			System.out.println("-- alle besucht --");
		}
		for (BpHelper red : redBpList) {
			System.out.println(red.bp.getAbkuerzung());
		}

		// Alle grünen BpHelper auf Konsole ausgeben (sollten immer alle
		// BpHelper
		// sein nach Durchlaufen von Dijkstra.
		// Falls keine grünen Punte, 'leer' ausgeben
		// (Test und Debug)
		System.out.println("Grüne Betriebspunkte");
		if (greenBpList.size() == 0) {
			System.out.println("-- leer --");
		}
		// for (BpHelper green : greenBpList) {
		// System.out.println(green.bp.getAbkuerzung());
		// }

		// Ausgabe auf Konsole von BpHelpern welche keinen Vorgänger haben
		// (Test und Debug)
		Iterator<Long> iterator = allBpMap.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = (Object) iterator.next();
			BpHelper helperVorg = (BpHelper) allBpMap.get(key);
			if (helperVorg.getBpVorher() != null) {
			} else {
				System.out.println(helperVorg.bp.getAbkuerzung()
						+ " hat keinen Vorgänger!");
			}
			// System.out.println("Alle haben Vorgänger");
		}

		// Rückgabewert der Methode ist die Liste aller besuchten BpHelpern
		return greenBpList;

	}

	// Methode zur Rückgabe des kürzesten Weges zwischen BpHelper 'start'
	// und BpHelper 'ziel'
	public static List<BpHelper> getShortestPath(BpHelper start, BpHelper ziel) {
		System.out.println("Kürzester Weg von " + start.bp.getBezeichnung()
				+ " nach " + ziel.bp.getBezeichnung() + ":");
		shortestPath.clear();

		// Hinzufügen des ziel-BpHelpers 'ziel' zur ArrayList 'shortestPath'
		shortestPath.add(ziel);
		System.out.println("Gesamtdistanz in Metern:   "
				+ ziel.getDistanzZumStart() + "");

		// Definition des Abbruchkriteriums - max Laufzeit n*n (n = Anz
		// BpHelper)
		int maximalRuns = allBpMap.size() * allBpMap.size();

		for (int i = 0; i < maximalRuns; i++) {

			// Abbruch der Schlaufe, falls der 'Vorher'Betriebspunkt = dem
			// 'start'BpHelper ist
			if (ziel.getBpVorher().equals(start.bp)) {
				shortestPath.add(start);
				break;
			}

			// Hinzufügen des 'Vorher'-BpHelper zur ArrayList 'shortestPath'
			shortestPath.add(allBpMap.get(ziel.getBpVorher()
					.getId_betriebspunkt()));

			// Der 'Vorher'-BpHelper wird zum Ausgang für den nächsten
			// Schleifendurchgang
			ziel = allBpMap.get(ziel.getBpVorher().getId_betriebspunkt());
		}

		// ArrayList am Ende sortieren (umgekehrt), so dass die Reihenfolge der
		// BpHelper vom Start zum Ziel der Berührung in der ArrayList
		// 'shortestPath' ist.
		Collections.reverse(shortestPath);

		// Ausgabe der Knotenabfolge in der ArrayList 'shortestPath'
		for (BpHelper bpHelper : shortestPath) {
			System.out.println("Der Weg führt über: "
					+ bpHelper.bp.getAbkuerzung() + ", "
					+ bpHelper.bp.getBezeichnung());
		}

		return shortestPath;
	}
}
