package ch.zhaw.hoferrol.shortestrailpath.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpStatusEnum;
import ch.zhaw.hoferrol.shortestrailpath.topologie.NeighbourCalculator;

/**
 * Klasse Dijkstra - Logikteil der Applikation, berechnet den kürzesten Weg
 * zwischen zwei übergebenen Betriebspunkten. Mit dem übergebenen Modus wird die
 * Wahl der Berechnungsvariaten (Dijkstra-classic, Dijkstra-optimiert oder A*)
 * defininiert.
 * 
 * @author Roland Hofer, V1.8 - 08.05.2014
 */

public class Dijkstra {
	// Konstanten für Wahl des Berechnungsmodus
	public static final int MODUS_CLASSIC = 0;
	public static final int MODUS_OPTIMIERT = 1;
	public static final int MODUS_ASTERN = 2;

	// Variabeln für Log4J
	private static final Logger LOG = Logger.getLogger(Dijkstra.class);

	// Variabeln für Zeitmessung
	private long startZeitDijkstraWork;
	private long endZeitDijkstraWork;
	private long laufZeitDijkstraWork;
	private long startZeitShortestPath;
	private long endZeitShortestPath;
	private long laufZeitShortestPath;
	private long laufZeitAlgorithmus;

	// Map zur Speicherung aller BpHelper drin (key = id_Bp)
	private Map<Long, BpHelper> allBpMap;
	// Liste wo alle BpHelper abgelegt werden, die noch nicht
	// vom Dijkstra bearbeitet wurden
	private List<BpHelper> redBpList;
	// Liste mit allen BpHelpern, welche vom Dijkstra
	// berührt aber noch nicht besucht wurden
	private List<BpHelper> yellowBpList;
	// Liste mit allen BpHelpern, welche vom Dijkstra besucht wurden
	private List<BpHelper> greenBpList;

	private List<BpHelper> shortestBpList;
	private Set<BetriebspunktVerbindungen> shortestBpVerbList;
	private List<BpHelper> shortestPath = new ArrayList<BpHelper>();
	private BpHelper zielBpHelper;
	private BpHelper nachbarBpHelper;
	// Instanzvariabel der Klasse ASternHeuristikHelper
	private ASternHeuristikHelper aSternHeuristikHelper;

	// Konstruktor Dijkstra mit Übergabe der BpHelper-Map
	public Dijkstra(Map<Long, BpHelper> bpHelperMap) {

		// Map mit allen BpHelpern drin
		allBpMap = bpHelperMap;
		// Initialisierung der Listen
		redBpList = new ArrayList<BpHelper>();
		yellowBpList = new ArrayList<BpHelper>();
		greenBpList = new ArrayList<BpHelper>();

		// shortestBpList = new ArrayList<BpHelper>();
		// shortestBpVerbList = new HashSet<BetriebspunktVerbindungen>();
		// Instanzierung ASternHeuristikHelper
		aSternHeuristikHelper = new ASternHeuristikHelper(bpHelperMap);

		// Iterator über alle BpHelper. Dabei wird der Status
		// aller BpHelper zu 'rot' gesetzt
		Iterator<Long> it = allBpMap.keySet().iterator();
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
		yellowBpList.clear();
		greenBpList.clear();
		shortestBpList.clear();
		shortestBpVerbList.clear();
	}

	// Methode um Status eines BpHelpers zu grün zu setzen.
	// BpHelper wird in der redBpList-Array oder in der yellowBpList
	// gelöscht. Der BpHelpers wird zur greenBpList-Array hinzu-
	// gefügt und der Status des BpHelpers auf 'gruen'
	// gesetzt.
	public void changeBpToGreen(BpHelper bpHelperBp) {
		if (redBpList.contains(bpHelperBp)) {
			redBpList.remove(bpHelperBp);
		} else if (yellowBpList.contains(bpHelperBp)) {
			yellowBpList.remove(bpHelperBp);
		}
		greenBpList.add(bpHelperBp);
		bpHelperBp.setStatus(BpStatusEnum.gruen);
	}

	// Methode um Status eines BpHelpers zu rot zu setzen.
	// BpHelper wird in die redBpList aufgenommen und in der
	// greenBpList entfernt
	public void changeBpToRed(BpHelper bpHelperBp) {
		if (bpHelperBp.getStatus().equals(BpStatusEnum.rot)) {
			redBpList.add(bpHelperBp);
			greenBpList.remove(bpHelperBp);
		}
		bpHelperBp.setStatus(BpStatusEnum.rot);
	}

	// Methode um einen BpHelpers in die yellowBpList
	// aufzunehmen. Dabei wird dieser aus der redBpList
	// entfernt.
	// Bei Modus AStern wird zusätzlich die Heuristik berechnet und
	// im BpHelper abgespeichert.
	public void changeBpToYellow(BpHelper bpHelperBp, int modus) {
		if (redBpList.contains(bpHelperBp)) {
			redBpList.remove(bpHelperBp);

		}
		if (yellowBpList.contains(bpHelperBp)) {

		} else {
			yellowBpList.add(bpHelperBp);
			if (modus == MODUS_ASTERN) {
				bpHelperBp.setAirDistanzZumZiel((long) aSternHeuristikHelper
						.getAirDistance(bpHelperBp.bp.getId_betriebspunkt(),
								zielBpHelper.bp.getId_betriebspunkt()));
			}
		}
	}

	// Methode um zwei BpHelper aufgrund der Distanzen zu sortieren
	// -> wird für Modus Dijkstra-classic und Dijkstra-optimiert verwendet
	Comparator<BpHelper> sortByDistanz = new Comparator<BpHelper>() {
		public int compare(BpHelper helperBp1, BpHelper helperBp2) {
			return Long.signum((helperBp1.getDistanzZumStart() - helperBp2
					.getDistanzZumStart()));
		}
	};

	// Methode um zwei BpHelper aufgrund der Distanzen zu sortieren
	// -> wird für Modus AStern verwendet
	Comparator<BpHelper> sortByDistanzMitAir = new Comparator<BpHelper>() {

		public int compare(BpHelper helperBp1, BpHelper helperBp2) {
			return Long
					.signum((heuristicDistance(helperBp1) - heuristicDistance(helperBp2)));
		}

		private long heuristicDistance(BpHelper helperBp1) {
			if (helperBp1.getDistanzZumStart() == Long.MAX_VALUE) {
				return helperBp1.getDistanzZumStart();
			} else {
				return helperBp1.getDistanzZumStart()
						+ helperBp1.getAirDistanzZumZiel();
			}
		}
	};

	// Work-Methode. Mit dem Dijkstra-Algorithmus wird der ganze Graph durch-
	// laufen und zu den jeweiligen BpHelpern der 'optimale' Vorgänger sowie
	// die Distanz zum Start-BpHelper ermittelt.
	// Der Methode werden folgende Parameter übergeben
	// - BpHelper welcher als Startpunkt übergeben wird
	// - BpHelper welcher als Zielpunkt übergeben wird
	// - HashMap mit allen BpHelpern (key = id_BpHelper = id_Betriebspunkt)

	public List<BpHelper> work(BpHelper bpHelperStart, BpHelper zielHelper,
			int modus, Map<Long, BpHelper> allBpMap) {

		// Startmesspunkt für Messung Algorithmus
		startZeitDijkstraWork = System.nanoTime();

		LOG.info("Dijkstra mit Modus " + modus + " gestartet");

		this.zielBpHelper = zielHelper;
		// BpHelper werden vorbereitet. Dazu wird über allBpMap iteriert
		bpHelperVorbereiten(allBpMap);

		// Spezielle Einstellungen für den Startpunkt
		initialisierenStartKnoten(bpHelperStart);

		// Definition Abbruchbedingung für den schlechtesten Fall für Dijkstra
		// n * n
		int worstCase = allBpMap.size() * allBpMap.size();

		// Dijkstra-Schleife um Vorgänger und Distanzen zu berechnen
		for (int i = 0; i < worstCase; i++) {

			// Abbruch der Schleife, wenn redBpList bzw. yellowBpList leer ist
			// sprich alle BpHelper besucht wurden
			if ((redBpList.size() == 0) || (yellowBpList.size() == 0)) {
				break;
			}

			// ArrayList 'yellowBpList' sortieren nach Distanz.
			// Sortiermethode je nach ausgewähltem Modus
			if (modus == MODUS_CLASSIC || modus == MODUS_OPTIMIERT) {
				Collections.sort(yellowBpList, sortByDistanz);
			} else if (modus == MODUS_ASTERN) {
				Collections.sort(yellowBpList, sortByDistanzMitAir);
			}

			// Setze den BpHelper mit der kleinsten DistanzZumStart = ArrayList
			// yellowBpList pos[0] als nextBp, also zum neuen AusgangsBpHelper
			// für den nächsten Schleifendurchlauf
			BpHelper aktuellerBP = yellowBpList.get(0);

			// Wechsle den neuen AusgangsBpHelper (aktuellerBp) zu grün
			changeBpToGreen(aktuellerBP);

			// Für alle NachfolgeBetriebspunkte (nachbarBp) von aktuellerBp
			// mache
			for (Betriebspunkt nachbarBp : aktuellerBP.getNext()) {
				// Abbruchbedingung falls kein nachbarBp vorhanden ist
				if (aktuellerBP.getNext().size() == 0) {
					break;
				}

				// Abfangen des Falles wenn ein Nachbar-Bp gesucht wird, welcher
				// im Ausland liegt und deshalb keine Topologiedaten hat
				if (allBpMap.containsKey(aktuellerBP.bp.getId_betriebspunkt())) {
					nachbarBpHelper = allBpMap.get(nachbarBp
							.getId_betriebspunkt());
				} else {
					break;
				}

				// Abfangen des Fall wenn ein Nachbar-Bp gesucht wird, welcher
				// im Ausland liegt und deshalb keine Topologiedaten hat
				if (allBpMap.containsKey(nachbarBp.getId_betriebspunkt())) {
					nachbarBpHelper = allBpMap.get(nachbarBp
							.getId_betriebspunkt());
				} else {
					break;
				}

				// berechne Distanz zwischen 'aktuellerBp' und 'nachbarBp'
				long distanzBpHelperVonZu = NeighbourCalculator.getDistanz(
						aktuellerBP.bp.getId_betriebspunkt(),
						nachbarBpHelper.bp.getId_betriebspunkt());

				// Ausgabe der eruierten Distanz zwischen 'aktuellerBp' und
				// 'nachbarBp' auf Konsole
				LOG.debug("Distanz von : " + aktuellerBP.bp.getAbkuerzung()
						+ " nach: " + nachbarBpHelper.bp.getAbkuerzung()
						+ " betraegt: " + distanzBpHelperVonZu);

				// Addiere neu eruierte Distanz zu bereits bestehender Distanz
				// vom 'aktuellerBp'BpHelper
				long tmpDistanz = (aktuellerBP.getDistanzZumStart() + distanzBpHelperVonZu);

				// Ausgabe einer Warnmeldung auf der Konsole, falls neu
				// berechnete Distanz grösser dem max-Wert ist
				if (tmpDistanz >= Integer.MAX_VALUE) {
					LOG.debug("Achtung hier stimmt was nicht! - AktuellerBp: "
							+ aktuellerBP.getBp().getId_betriebspunkt() + ", "
							+ aktuellerBP.getBp().getAbkuerzung()
							+ " tmpDistanz: " + tmpDistanz
							+ " grösser als maxWert!");
				}

				// Falls die neu berechnete Distanz (tmpDistanz) kleiner ist
				// als die nachbarBpHelper-Distanz, setze die neue Distanz und
				// den
				// Vorgänger beim 'nachbarBpHelper' ein und nehme den
				// wechsle ihn auf gelb
				if (tmpDistanz <= nachbarBpHelper.getDistanzZumStart()) {
					nachbarBpHelper.setDistanzZumStart(tmpDistanz);
					nachbarBpHelper.setBpVorher(aktuellerBP.bp);
					allBpMap.put(nachbarBpHelper.bp.getId_betriebspunkt(),
							nachbarBpHelper);

					changeBpToYellow(nachbarBpHelper, modus);

				}

			}

			// Modus 1 = Dijksta Opt; Modus 2 = AStern
			// Abbruchbedingung für Dijkstra Opt und AStern
			// Wenn ZielHelperKnoten erreicht ist, wird aufgehört den Graphen
			// weiter durchzusteppen.
			if ((modus == MODUS_OPTIMIERT || modus == MODUS_ASTERN)
					&& aktuellerBP.bp.getId_betriebspunkt() == zielHelper.bp
							.getId_betriebspunkt()) {
				break;
			}

		}

		// Alle roten BpHelper auf Konsole ausgeben (sollte nach Durchlaufen von
		// Dijkstra immer 'alle besucht' kommen.
		// Falls nicht, rote Knoten ausgeben
		LOG.debug("Rote Betriebspunkte");
		if (modus == MODUS_CLASSIC && redBpList.size() == 0) {
			LOG.debug("-- alle besucht --");
		} else if (modus == MODUS_CLASSIC && redBpList.size() > 0) {
			for (BpHelper red : redBpList) {
				LOG.debug(red.bp.getAbkuerzung() + ", "
						+ red.bp.getBezeichnung());
			}
		}
		if ((modus == MODUS_OPTIMIERT || modus == MODUS_ASTERN)
				|| redBpList.size() == 0) {
			LOG.debug("---leer--- oder Modus optimiert bzw. AStern eingeschaltet");
		}

		// Alle grünen BpHelper auf Konsole ausgeben (sollten immer alle
		// BpHelper
		// sein nach Durchlaufen von Dijkstra.
		// Falls keine grünen Punte, 'leer' ausgeben
		LOG.debug("Grüne Betriebspunkte");
		if (greenBpList.size() == 0) {
			LOG.debug("-- leer --");
		}
		LOG.debug("Folgende 'gruene' Betriebspunkte besucht");
		if (modus == MODUS_OPTIMIERT || modus == MODUS_ASTERN) {
			for (BpHelper green : greenBpList) {
				LOG.debug(green.bp.getAbkuerzung() + ", "
						+ green.bp.getBezeichnung() + " besucht");
			}
		}
		// Ausgabe auf Konsole von BpHelpern welche keinen Vorgänger haben
		if (modus == 0) {
			Iterator<Long> iterator = allBpMap.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = (Object) iterator.next();
				BpHelper helperVorg = (BpHelper) allBpMap.get(key);
				if (helperVorg.getBpVorher() != null) {
				} else {
					LOG.debug(helperVorg.bp.getAbkuerzung() + ", "
							+ " hat keinen Vorgaenger! - Topologie pruefen");
				}
			}
		}

		// Rückgabewert der Methode ist die Liste aller besuchten BpHelpern
		endZeitDijkstraWork = System.nanoTime();
		return greenBpList;

	}

	private void initialisierenStartKnoten(BpHelper bpHelperStart) {
		// Der Status des Startpunktes wird true gesetzt (=gelb)
		// changeBpToGreen(bpHelperStart);
		// Die Distanz zu seinem Vorgänger wird zu 0 gesetzt
		bpHelperStart.setDistanzZumStart(0L);
		// Dem Startknoten wird er selbst zu seinem Vorgänger definiert
		bpHelperStart.setBpVorher(bpHelperStart.bp);
		yellowBpList.add(bpHelperStart);
		redBpList.remove(bpHelperStart);
	}

	private void bpHelperVorbereiten(Map<Long, BpHelper> allBpMap) {
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
				// (Abfangen Fall, falls Bp im ohne Bp-Verbindung in Topologie
				// wäre)
				allBpMap.remove(helper);
				// (Test und Debug) Ausgabe von gelöschten BpHelpern auf der
				// Konsole
				LOG.debug("BpHelper ohne Nachbar: " + helper.bp.getAbkuerzung()
						+ " wurde geloescht");
			}
		}
	}

	// Methode zur Rückgabe des kürzesten Weges zwischen BpHelper 'start'
	// und BpHelper 'ziel'
	public List<BpHelper> getShortestPath(BpHelper start, BpHelper ziel) {
		startZeitShortestPath = System.nanoTime();
		LOG.info("Kuerzester Weg von " + start.bp.getBezeichnung() + " nach "
				+ ziel.bp.getBezeichnung() + ":");
		shortestPath.clear();

		// Hinzufügen des ziel-BpHelpers 'ziel' zur ArrayList 'shortestPath'
		shortestPath.add(ziel);
		LOG.info("Gesamtdistanz in Metern:   " + ziel.getDistanzZumStart() + "");

		// Definition des Abbruchkriteriums - max Laufzeit n*n (n = Anz
		// BpHelper)
		int maximalRuns = allBpMap.size() * allBpMap.size();

		for (int i = 0; i < maximalRuns; i++) {

			// Prüfen ob der 'ziel'BpHelper einen Vorgänger hat, falls nein wird
			// Liste 'shortestPath' geleert und eine leere Liste übergeben.
			if (ziel.getBpVorher() != null) {
				// Abbruch der Schlaufe falls der 'vorher'Betriebspunkt =
				// dem 'start'BpHelper ist.
				if (ziel.getBpVorher().equals(start.bp)) {
					shortestPath.add(start);
					break;
				}
			} else {
				shortestPath.clear();
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

		// Ausgabe der BpHelperabfolge in der ArrayList 'shortestPath'
		for (BpHelper bpHelper : shortestPath) {
			LOG.info("Der Weg fuehrt ueber: " + bpHelper.bp.getAbkuerzung()
					+ ", " + bpHelper.bp.getBezeichnung());
		}
		endZeitShortestPath = System.nanoTime();
		return shortestPath;
	}

	// Methode zur Berechung der Laufzeit und Aufbereitung der Ausgabe
	public long getLaufzeit() {
		laufZeitDijkstraWork = endZeitDijkstraWork - startZeitDijkstraWork;
		laufZeitShortestPath = endZeitShortestPath - startZeitShortestPath;
		laufZeitAlgorithmus = laufZeitDijkstraWork + laufZeitShortestPath;
		return laufZeitAlgorithmus;
	}
}
