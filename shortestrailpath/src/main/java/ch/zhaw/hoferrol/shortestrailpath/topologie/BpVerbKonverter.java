package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Klasse BpVerbKonverter erstellt aus einer Liste mit BetriebspunktVerbindungen
 * eine HashMap<BetriebspunktVerbindungen>. Schlüsselwert ist die id der
 * Betriebspunktverbindung, Value-Wert ist die jeweils zugehörige
 * Betriebspunktverbindung.
 * 
 * 
 * @author Roland Hofer, V1.0 - 20.04.2014
 * 
 */

public class BpVerbKonverter {

	// Klassenvariabeln
	private static final Logger LOG = Logger.getLogger(BpVerbKonverter.class);
	Map<Long, BetriebspunktVerbindungen> hashBpVerb = new HashMap<Long, BetriebspunktVerbindungen>();
	static List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();

	public void convertBpVerb(List<BetriebspunktVerbindungen> bpVerbList) {

		this.bpVerbList = bpVerbList;

		// Schlaufe, durchläuft die übergebene Liste von
		// Betriebspunktverbindungen und
		// legt die Betriebspunktverbindungen in eine HashMap mit der Struktur
		// key = id_betriebspunktverbindung, value = Betriebspunktverbindung ab.
		for (int i = 0; i < bpVerbList.size(); i++) {
			hashBpVerb
					.put(bpVerbList.get(i).id_bpVerbindung, bpVerbList.get(i));
		}
		LOG.info("Grösse der HashMap 'hashBpVerb' beträgt: "
				+ hashBpVerb.size());

	}

	public Map<Long, BetriebspunktVerbindungen> getHashBpVerb() {
		return hashBpVerb;
	}

}
