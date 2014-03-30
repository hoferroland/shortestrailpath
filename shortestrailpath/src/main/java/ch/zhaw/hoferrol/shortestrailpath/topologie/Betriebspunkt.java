package ch.zhaw.hoferrol.shortestrailpath.topologie;

public class Betriebspunkt {

	String abkuerzung;
	String bezeichnung;
	long id_betriebspunkt;
	int betriebspunkt_typ;
	float koo_x;
	float koo_y;

	public Betriebspunkt(String abk, String bez, long id_bp, int bp_typ,
			float xPos, float yPos) {
		abkuerzung = abk;
		bezeichnung = bez;
		id_betriebspunkt = id_bp;
		this.betriebspunkt_typ = bp_typ;
		koo_x = xPos;
		koo_y = yPos;
	}

	// Getter-Methoden
	public String getAbkuerzung() {
		return abkuerzung;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public long getId_betriebspunkt() {
		return id_betriebspunkt;
	}

	public int getBetriebspunkt_typ() {
		return betriebspunkt_typ;
	}

	public float getKoo_x() {
		return koo_x;
	}

	public float getKoo_y() {
		return koo_y;
	}

}
