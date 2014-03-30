package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BpVerbKonverter {

	Map<Long, BetriebspunktVerbindungen> hashBpVerb = new HashMap<Long, BetriebspunktVerbindungen>();
	static List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();

	public void convertBpVerb(List<BetriebspunktVerbindungen> bpVerbList) {

		this.bpVerbList = bpVerbList;

		int i;
		int size = bpVerbList.size();

		for (i = 0; i < size; i++) {
			hashBpVerb
					.put(bpVerbList.get(i).id_bpVerbindung, bpVerbList.get(i));
			// System.out.println("ID_Betriebspunkt: " +
			// bpList.get(i).id_betriebspunkt);

		}
		System.out.println("Grösse der HashMap 'hashBpVerb' beträgt: "
				+ hashBpVerb.size());

	}
}
