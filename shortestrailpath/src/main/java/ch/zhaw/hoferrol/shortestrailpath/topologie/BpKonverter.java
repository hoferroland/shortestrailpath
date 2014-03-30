package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BpKonverter {

	Map<Long, Betriebspunkt> hashBp = new HashMap<Long, Betriebspunkt>();
	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();

	public void convertBP(List<Betriebspunkt> bpList) {

		this.bpList = bpList;

		int i;
		int size = bpList.size();

		for (i = 0; i < size; i++) {
			hashBp.put(bpList.get(i).id_betriebspunkt, bpList.get(i));
			// Konsolenausgabe nur zu Debugzwecken
			// System.out.print(bpList.get(i).id_betriebspunkt + ", "
			// + bpList.get(i).abkuerzung + " ---");
			// System.out
			// .println(hashBp.get(bpList.get(i).id_betriebspunkt).abkuerzung);

		}
		System.out.println("Grösse der HashMap 'hashBp' beträgt: "
				+ hashBp.size());

	}

}
