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

public class BpHelper {

	// Variabeln
	Betriebspunkt bp;
	List<Betriebspunkt> nextBp = new ArrayList<Betriebspunkt>();
	Betriebspunkt vorher;
	long distanzZumStart;
	BpStatusEnum status;
	BpHelper bpHelper;
	float iKoo;
	float jKoo;

	// Konstruktor für einen BpHelper
	public BpHelper(Betriebspunkt bp) {
		this.bp = bp;
	}

	// Getter und Setter-Methoden
	public BpHelper getBpHelper(Long id_bp) {
		return bpHelper;
	}

	public void setNextBp(Betriebspunkt next) {
		this.nextBp.add(next);
	}

	public void setNextBpList(List<Betriebspunkt> nextList) {
		this.nextBp = nextList;
	}

	List<Betriebspunkt> getNext() {
		return nextBp;
	}

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

	public void setIKoo(float iKoo) {
		this.iKoo = iKoo;
	}

	public float getIKoo() {
		return iKoo;
	}

	public void setJKoo(float jKoo) {
		this.jKoo = jKoo;
	}

	public float getJKoo() {
		return jKoo;
	}

	// Methode, welche aus der Map Betriebspunkt und der Map
	// BetriebspunktVerbindungen
	// eine Map mit BpHelpern macht
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
			BpHelper h = new BpHelper(bp);
			// füge dem BpHelpter seine Liste von NachbarBetriebspunkte zu
			h.setNextBpList(neighbourCalc.getNext(bp.getId_betriebspunkt()));
			// füge jeden BpHelper der bpHelperMap hinzu
			bpHelperMap.put(bp.getId_betriebspunkt(), h);

			// Konsolenausgabe, welche Betriebspunkte dem BpHelper
			// hinzugefügt wurden
			// (Debug und Test)
			// System.out.println("Zum BpHelper " + h.bp.getAbkuerzung()
			// + " folgende Nachbarn hinzugefügt: ");
			// for (Betriebspunkt bp1 : (neighbourCalc.getNext(bp
			// .getId_betriebspunkt()))) {
			// System.out.print(bp1.getAbkuerzung() + ", "
			// + bp1.getBezeichnung());
			// }

		}
		return bpHelperMap;
	}
}
