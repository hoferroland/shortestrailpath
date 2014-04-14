package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.ArrayList;
import java.util.List;

public class BetriebspunktVerbindungen {

	long id_bpVerbindung;
	long id_bpVon;
	long id_bpBis;
	long distanz;
	int anzGleise;
	Betriebspunkt start;
	Betriebspunkt ziel;
	BpStatusEnum status;
	List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();
	BpKonverter bpKonv = new BpKonverter();

	public BetriebspunktVerbindungen(long id_bpVerb, long id_bpVon,
			long id_bpBis, long distanz, int anzGleise, Betriebspunkt bpStart,
			Betriebspunkt bpZiel) {
		id_bpVerbindung = id_bpVerb;
		this.id_bpVon = id_bpVon;
		this.id_bpBis = id_bpBis;
		this.distanz = distanz;
		this.anzGleise = anzGleise;
		this.start = bpStart;
		this.ziel = bpZiel;

	}

	// Getter-Methoden
	public long getId_bpVerbindung() {
		return id_bpVerbindung;
	}

	public long getId_bpVon() {
		return id_bpVon;
	}

	public long getId_bpBis() {
		return id_bpBis;
	}

	public long getDistanz() {
		return distanz;
	}

	public int getAnzGleise() {
		return anzGleise;
	}

	public Betriebspunkt getBpStart() {
		return start;
	}

	public Betriebspunkt getBpZiel() {
		return ziel;
	}

}
