package ch.zhaw.hoferrol.shortestrailpath.topologie;

public class BetriebspunktVerbindungen {

	long id_bpVerbindung;
	long id_bpVon;
	long id_bpBis;
	long distanz;
	int anzGleise;

	public BetriebspunktVerbindungen(long id_bpVerb, long id_bpVon,
			long id_bpBis, long distanz, int anzGleise) {
		id_bpVerbindung = id_bpVerb;
		this.id_bpVon = id_bpVon;
		this.id_bpBis = id_bpBis;
		this.distanz = distanz;
		this.anzGleise = anzGleise;

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

}
