package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse Betriebspunkt Verwaltet in der Topologie die Betriebspunkte.
 * 
 * Parameter:
 * 
 * abkuerzung : Kürzel des Betriebspunktes. Dient im Bahnumfeld zur Abkürzung
 * von Namen in der Umgangssprache sowie in bahnspezifischen Dokumenten.
 * 
 * bezeichnung : Ausgeschriebener Name des Betriebspunktes, meist ein
 * Ortschaftsnamen oder eine Regionsbezeichnung.
 * 
 * id_betriebspunkt: id welche durch den Topologie-Lieferant (UNO) vergeben
 * wurde. Dient später als key in der Hashmap.
 * 
 * betriebspunkt_typ: Dient der Differenzierung ob ein Betriebspunkt ein
 * Bahnhof, eine Haltestelle oder eine Abzweigung usw. ist.
 * 
 * TODO!! koo_x : X-Koordinaten des Betriebspunktes auf Basis von
 * 
 * TODO!! koo_y : Y-Koordinaten des Betriebspunktes auf Basis von
 * 
 * uic : Ländercode. Ermöglicht Unterscheidung zwischen Betriebspunkten in der
 * Schweiz oder dem Ausland.
 * 
 * @author Roland Hofer, V1.0 - 20.04.2014
 * 
 */
public class Betriebspunkt {

	// Variabeln
	String abkuerzung;
	String bezeichnung;
	long id_betriebspunkt;
	int betriebspunkt_typ;
	float koo_x;
	float koo_y;
	int uic;

	// Konstruktor
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

}
