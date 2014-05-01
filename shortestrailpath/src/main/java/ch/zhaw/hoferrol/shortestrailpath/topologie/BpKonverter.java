package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse BpKonverter erstellt aus einer Liste mit Betriebspunkten eine 
 * HashMap<Betriebspunkt>. Schlüsselwert ist die id des Betriebspunktes,
 * Value-Wert ist der jeweils zugehörige Betriebspunkt.
 * 
 * 
 * @author Roland Hofer, V1.0 - 20.04.2014
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BpKonverter {

	// Variabeln
	private static final Logger LOG = Logger.getLogger(BpKonverter.class);
	Map<Long, Betriebspunkt> hashBp = new HashMap<Long, Betriebspunkt>();
	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();

	public void convertBP(List<Betriebspunkt> bpList) {

		this.bpList = bpList;

		// Schlaufe, durchläuft die übergebene Liste von Betriebspunkten und
		// legt die Betriebspunkte in eine HashMap mit der Struktur
		// key = id_betriebspunkt, value = Betriebspunkt ab.
		for (int i = 0; i < bpList.size(); i++) {
			hashBp.put(bpList.get(i).id_betriebspunkt, bpList.get(i));

		}
		LOG.info("Grösse der HashMap 'hashBp' beträgt: " + hashBp.size());

	}

	// Getter-Methoden
	public Map<Long, Betriebspunkt> getHashBp() {
		return hashBp;
	}

	public Betriebspunkt getBp(long id) {
		return hashBp.get(id);
	}

}
