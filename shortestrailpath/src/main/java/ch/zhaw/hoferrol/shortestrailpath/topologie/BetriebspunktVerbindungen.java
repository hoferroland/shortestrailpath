package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse BetriebspunktVerbindungen verwaltet in der Topologie die Verbindungen
 * zwischen den Betriebspunkten.
 * 
 * 
 * Parameter:
 * 
 * id_bpVerbindung: id, welche durch den Topologie-Lieferant (UNO) vergeben
 * wurde. Dient später als key in der Hashmap.
 * 
 * id_bpVon : id des Betriebspunktes, von welcher die Verbindung her zeigt
 * (Startpunkt der Betriebspunktverbindung)
 * 
 * id_bpBis : id des Betriebspunktes, nach welcher die Verbindung hin zeigt
 * (Zielpunkt der Betriebspunktverbindung)
 * 
 * distanz : Gleisdistanz zwischen den beiden Betriebspunkten in Meter
 * 
 * anzGleise : gibt an, wie viele Gleise zwischen den Betriebspunkten vorhanden
 * sind (zB. Einspur, Doppel- oder Mehrspurstrecken im Kernnetz)
 * 
 * @author Roland Hofer, V1.0 - 20.04.2014
 * 
 */

public class BetriebspunktVerbindungen {

	// Variabeln
	long id_bpVerbindung;
	long id_bpVon;
	long id_bpBis;
	long distanz;
	int anzGleise;

	// Konstruktor
	public BetriebspunktVerbindungen(long id_bpVerb, long id_bpVon,
			long id_bpBis, long distanz, int anzGleise, Betriebspunkt bpStart,
			Betriebspunkt bpZiel) {
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
