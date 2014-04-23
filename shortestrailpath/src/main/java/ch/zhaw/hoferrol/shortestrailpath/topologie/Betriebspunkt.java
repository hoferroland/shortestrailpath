package ch.zhaw.hoferrol.shortestrailpath.topologie;

public class Betriebspunkt {

	String abkuerzung;
	String bezeichnung;
	long id_betriebspunkt;
	int betriebspunkt_typ;
	float koo_x;
	float koo_y;
	int uic;

	// List<Betriebspunkt> nextBp = new ArrayList<Betriebspunkt>();
	// BpHelper vorher;
	// double distanzZumStart;
	// BpStatusEnum status;

	public Betriebspunkt(String abk, String bez, long id_bp, int bp_typ,
			float xPos, float yPos, int uic) {
		abkuerzung = abk;
		bezeichnung = bez;
		id_betriebspunkt = id_bp;
		this.betriebspunkt_typ = bp_typ;
		koo_x = xPos;
		koo_y = yPos;
		this.uic = uic;
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

	public String toString() {
		return this.bezeichnung;
	}

	public int getUic() {
		return uic;
	}

	// public void setNextBp(Betriebspunkt next) {
	// this.nextBp.add(next);
	// }
	//
	// public void setNextBpList(List<Betriebspunkt> nextList) {
	// this.nextBp = nextList;
	// }
	//
	// List<Betriebspunkt> getNext() {
	// return nextBp;
	// }

	// public void setBpVorher(BpHelper bpHelper) {
	// this.vorher = bpHelper;
	// }
	//
	// public BpHelper getBpVorher() {
	// return vorher;
	// }
	//
	// public void setDistanzZumStart(double distanz) {
	// this.distanzZumStart = distanz;
	// }
	//
	// public double getDistanzZumStart() {
	// return distanzZumStart;
	// }
	//
	// public String toString() {
	// return this.bezeichnung;
	// }
	//
	// public BpStatusEnum getStatus() {
	// return status;
	// }
	//
	// public void setStatus(BpStatusEnum status) {
	// this.status = status;
	// }

}
