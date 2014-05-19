package ch.zhaw.hoferrol.shortestrailpath.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpStatusEnum;
import ch.zhaw.hoferrol.shortestrailpath.topologie.NeighbourCalculator;

/**
 * Klasse BpHelper - stattet einen Betriebspunkt mit zusätzlichen Attributen für
 * den Dijkstra bzw. A*-Algorithmus aus.
 * 
 * Ein BpHelper besteht grundsätzlich aus einem Betriebspunkt (1:1-Beziehung).
 * Zusätzlich werden auf einem BpHelper folgende Attribute verwaltet: Liste von
 * Betriebspunkten nextBp für die Nachbarn eines Betriebspunktes
 * (1:n-Beziehung); vorher = 'optimaler' Vorgänger Betriebspunkt (wird durch
 * Dijkstra bzw. A* ermittelt); distanzZumStart = Aufaddierte Distanz des Weges
 * zum Start (wird durch Dijkstra bzw. A* ermittelt); status = status rot oder
 * grün (ob Betriebspunkt durch Algorithmus besucht wurde oder nicht); iKoo =
 * Bildschirm-Koordinate für die x-Achse; jKoo = Bildschirm-Koordinate für die
 * y-Achse; airDistanzZumZiel = Luftdistanz zum Ziel (wird für Heuristik beim
 * A*-Algorithmus benötigt.
 * 
 * 
 * @author Roland Hofer, V1.2 - 12.05.2014
 * 
 */

public class BpHelper {

	// Variabeln
	Betriebspunkt bp;
	List<Betriebspunkt> nextBp = new ArrayList<Betriebspunkt>();
	Betriebspunkt vorher;
	long distanzZumStart;
	BpStatusEnum status;
	BpHelper bpHelper;
	String bpTypKurz;
	String bpTypLang;
	float iKoo;
	float jKoo;
	long airDistanzZumZiel;

	// Konstruktor für einen BpHelper
	public BpHelper(Betriebspunkt bp) {
		this.bp = bp;
	}

	// Getter und Setter-Methoden
	public BpHelper getBpHelper(Long id_bp) {
		return bpHelper;
	}

	// Setze Typ des Betriebspunktes für Tabelle in MainFrame
	public void setBpTypKurz(String bpTypKurz) {
		this.bpTypKurz = bpTypKurz;
	}

	public String getBpTypKurz() {
		return bpTypKurz;
	}

	// Setze Typ des Betriebspunktes für Tooltip in Mainframe
	public void setBpTypLang(String bpTypLang) {
		this.bpTypLang = bpTypLang;
	}

	public String getBpTypLang() {
		return bpTypLang;
	}

	public void setNextBp(Betriebspunkt next) {
		this.nextBp.add(next);
	}

	// setze Liste von Nachbarn
	public void setNextBpList(List<Betriebspunkt> nextList) {
		this.nextBp = nextList;
	}

	List<Betriebspunkt> getNext() {
		return nextBp;
	}

	// setze idealen Vorgänger
	public void setBpVorher(Betriebspunkt vorher) {
		this.vorher = vorher;
	}

	public Betriebspunkt getBp() {
		return bp;
	}

	public Betriebspunkt getBpVorher() {
		return vorher;
	}

	public void setDistanzZumStart(long distanz) {
		this.distanzZumStart = distanz;
	}

	public long getDistanzZumStart() {
		return distanzZumStart;
	}

	public BpStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BpStatusEnum status) {
		this.status = status;
	}

	// Bildschirmkoordinate I
	public void setIKoo(float iKoo) {
		this.iKoo = iKoo;
	}

	public float getIKoo() {
		return iKoo;
	}

	// Bildschirmkoordinaten J
	public void setJKoo(float jKoo) {
		this.jKoo = jKoo;
	}

	public float getJKoo() {
		return jKoo;
	}

	// Luftdistanz zum Ziel für Heuristik A*-Algo
	public void setAirDistanzZumZiel(long airdistanz) {
		this.airDistanzZumZiel = airdistanz;
	}

	public long getAirDistanzZumZiel() {
		return airDistanzZumZiel;
	}

	// Methode, welche aus der Map Betriebspunkt und der Map
	// BetriebspunktVerbindungen eine Map mit BpHelpern macht
	public static Map<Long, BpHelper> buildHelperMap(
			Map<Long, Betriebspunkt> bpMap,
			Map<Long, BetriebspunktVerbindungen> bpVerMap) {

		// Aufruf der Methode um die Liste mit den Nachbarbetriebspunkten pro
		// BpHelper zu erhalten
		NeighbourCalculator neighbourCalc = new NeighbourCalculator(bpVerMap,
				bpMap);

		// Initiierung der neuen Map für alle BpHelper
		// (key = id_Betriebspunkt = id_BpHelper)
		Map<Long, BpHelper> bpHelperMap = new HashMap<Long, BpHelper>();

		// Iterieren über die bpMap
		Iterator<Long> it = bpMap.keySet().iterator();

		while (it.hasNext()) {
			Object key = it.next();
			Betriebspunkt bp = (Betriebspunkt) bpMap.get(key);
			// erstelle neuen BpHelper mit Betriebspunkt als Uebergabeparameter
			// Vereinfachung zur Darstellung in JTable in MainFrame, Zuweisung
			// des BpTypKurz und BpTypLang
			BpHelper h = new BpHelper(bp);
			int typBp = bp.getBetriebspunkt_typ();
			if ((typBp == 0) || (typBp == 2) || (typBp == 4)) {
				h.setBpTypKurz("Bhf");
				h.setBpTypLang("Bahnhof");
			}
			if ((typBp == 1) || (typBp == 3) || (typBp == 10)) {
				h.setBpTypKurz("Dienst");
				h.setBpTypLang("Dienstbahnhof/-betriebspunkt");
			}
			if ((typBp == 5) || (typBp == 6)) {
				h.setBpTypKurz("Hst");
				h.setBpTypLang("Haltestelle/Halt auf Verlangen");
			}
			if ((typBp == 7) || (typBp == 8) || (typBp == 9)) {
				h.setBpTypKurz("Abzw");
				h.setBpTypLang("Abzweigung/Spurwechsel");
			}

			// füge dem BpHelpter seine Liste von NachbarBetriebspunkten zu
			h.setNextBpList(neighbourCalc.getNext(bp.getId_betriebspunkt()));
			// füge jedem BpHelper seine bpHelperMap hinzu
			bpHelperMap.put(bp.getId_betriebspunkt(), h);

		}
		return bpHelperMap;
	}
}
