package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;

public class NeighbourCalculator {

	List<BpHelper> nextList = new ArrayList<BpHelper>();
	Map<Betriebspunkt, List<Betriebspunkt>> hashNextBp = new HashMap<Betriebspunkt, List<Betriebspunkt>>();
	static Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();
	static Map<Long, Betriebspunkt> bpMap = new HashMap<Long, Betriebspunkt>();

	// Iteration über alle BP-Verbindungen um pro BP jeweils die Nachbarn
	// und die dazugehörigen Distanzen herauszufinden
	private final Map<Long, List<Betriebspunkt>> neighbours;
	private static Map<Long, Map<Long, Long>> distanzMapUeber;

	// Konstruktor des NeighbourCalculator
	// Uebergabe der BetriebspunktVerbindungen-Map sowie der Betriebspunkte-Map
	public NeighbourCalculator(
			Map<Long, BetriebspunktVerbindungen> verbindungen,
			Map<Long, Betriebspunkt> bpMap) {

		// Anlegen von zwei neuen HashMaps
		neighbours = new HashMap<Long, List<Betriebspunkt>>();
		distanzMapUeber = new HashMap<Long, Map<Long, Long>>();
		this.bpMap = bpMap;

		Iterator it = verbindungen.keySet().iterator();
		while (it.hasNext()) {
			Long key = (Long) it.next();
			BetriebspunktVerbindungen verbindung = (BetriebspunktVerbindungen) verbindungen
					.get(key);

			// Zuerst mal alle Nachbar-Betriebspunkte ermitteln
			Betriebspunkt bpVon = bpMap.get(verbindung.getId_bpVon());
			Betriebspunkt bpBis = bpMap.get(verbindung.getId_bpBis());

			// Prüfung ob der betrachtete Betriebspunkt bereits eine Liste mit
			// Nachbarn hat
			List<Betriebspunkt> neighbourVon = neighbours
					.get(bpVon.id_betriebspunkt);
			if (neighbourVon == null) {
				// Falls nicht, wird eine neue ArrayList erstellt und der
				// eruierte
				// bpBis in die neu erstellte ArrayList abgelegt
				neighbourVon = new ArrayList<Betriebspunkt>();
				neighbourVon.add(bpBis);
				// ArrayList wird der Map 'neighbours' hinzugefügt
				// (key = bpVon id)
				neighbours.put(bpVon.id_betriebspunkt, neighbourVon);

			} else {
				// Falls bereits eine Liste mit Nachfolge-Betriebspunkten für
				// diesen
				// Betriebspunkt besteht, wird diese Liste einer temporären
				// Liste zugewiesen
				// (tmpNeighbourVonList) und die einzelnen Betriebspunkte in
				// einer neuen Liste
				// (tmpNeighbourVonBp) abgelegt.
				List<Betriebspunkt> tmpNeighbourVonList = new ArrayList<Betriebspunkt>();
				List<Betriebspunkt> tmpNeighbourVonBp = new ArrayList<Betriebspunkt>();
				tmpNeighbourVonList = neighbours.get(bpVon.id_betriebspunkt);
				for (Betriebspunkt neighbour : tmpNeighbourVonList) {
					tmpNeighbourVonBp.add(neighbour);
				}
				// Der neu eruierte Nachbar-Betriebspunkt wird der Liste
				// hinzugefügt
				tmpNeighbourVonBp.add(bpBis);
				// die temporäre Liste wird der neighbours-Map hinzugefügt
				neighbours.put(bpVon.id_betriebspunkt, tmpNeighbourVonBp);
			}

			// analoges Vorgehen wie oben - jetzt einfach für die
			// entgegengesetzte Richtung
			List<Betriebspunkt> neighbourBis = neighbours
					.get(bpBis.id_betriebspunkt);
			if (neighbourBis == null) {
				neighbourBis = new ArrayList<Betriebspunkt>();
				neighbourBis.add(bpVon);
				neighbours.put(bpBis.id_betriebspunkt, neighbourBis);
			} else {
				List<Betriebspunkt> tmpNeighbourBisList = new ArrayList<Betriebspunkt>();
				List<Betriebspunkt> tmpNeighbourBisBp = new ArrayList<Betriebspunkt>();
				tmpNeighbourBisList = neighbours.get(bpBis.id_betriebspunkt);
				for (Betriebspunkt neighbour : tmpNeighbourBisList) {
					tmpNeighbourBisBp.add(neighbour);
				}
				tmpNeighbourBisBp.add(bpVon);
				neighbours.put(bpBis.id_betriebspunkt, tmpNeighbourBisBp);
			}

			// Map aufbauen mit den Distanzen zwischen zwei Betriebspunkten
			Map<Long, Long> distanzMapKleinVon = distanzMapUeber.get(bpVon
					.getId_betriebspunkt());
			if (distanzMapKleinVon == null) {
				distanzMapKleinVon = new HashMap<Long, Long>();
				distanzMapUeber.put(bpVon.getId_betriebspunkt(),
						distanzMapKleinVon);
			}
			Map<Long, Long> distanzMapKleinBis = distanzMapUeber.get(bpBis
					.getId_betriebspunkt());
			if (distanzMapKleinBis == null) {
				distanzMapKleinBis = new HashMap<Long, Long>();
				distanzMapUeber.put(bpBis.getId_betriebspunkt(),
						distanzMapKleinBis);
			}
			distanzMapKleinBis.put(bpVon.id_betriebspunkt,
					verbindung.getDistanz());
			distanzMapKleinVon.put(bpBis.id_betriebspunkt,
					verbindung.getDistanz());
		}
	}

	// Methode liefert Liste von Betriebspunkten
	// Parameter id_Betriebspunkt
	public List<Betriebspunkt> getNext(Long idBp) {

		List<Betriebspunkt> bps = neighbours.get(idBp);
		// Falls keine Nachfolger bestehen, wird eine leere
		// Liste erstellt (Vermeidung von NullPointer!)
		if (bps == null) {
			return new ArrayList<Betriebspunkt>();
		}
		return bps;
	}

	// Methode welche Distanz zwischen zwei Betriebspunkten liefert
	// Parameter id_BetriebspunktVon und id_BetriebspunktNach
	public static Long getDistanz(long vonBpId, long nachBpId) {
		// TODO null checks...
		Map<Long, Long> distanzMap = distanzMapUeber.get(vonBpId);
		if (distanzMap == null) {
			System.out.println("Distanz zwischen: " + vonBpId + " und "
					+ nachBpId + " beträgt null!");
			return 0L;
		}
		return distanzMap.get(nachBpId);
	}
}