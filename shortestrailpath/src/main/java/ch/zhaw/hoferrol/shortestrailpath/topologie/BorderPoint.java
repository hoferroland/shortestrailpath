package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse BorderPoint Verwaltet Zeichnungspunkte zur Visualisierung der Grenz-
 * punkte der Schweiz für Kartenplott.
 * 
 * Parameter:
 * 
 * id_boderPoint : id des BorderPoint, die beim Einlesen der BorderDatei
 * automatisch durch den Schleifenzähler gesetzt wird.
 * 
 * koo_x : X-Koordinate
 * 
 * koo_y : Y-Koordinate
 * 
 * koo_i : I-Bildschirmkoordinate zur Darstellung auf Bildschirm
 * 
 * koo_j : J-Bildschirmkoordinate zur Darstellung auf Bildschirm
 * 
 * @author Roland Hofer, V1.0 - 01.05.2014
 * 
 */

public class BorderPoint {

	// Variabeln
	private int id_borderPoint;
	private float koo_x;
	private float koo_y;
	private float koo_i;
	private float koo_j;

	// Konstruktor
	public BorderPoint(int id, float koo_x, float koo_y) {
		this.id_borderPoint = id;
		this.koo_x = koo_x;
		this.koo_y = koo_y;
	}

	// Getter-Methoden
	public int getId() {
		return id_borderPoint;
	}

	public float getKooX() {
		return koo_x;
	}

	public float getKooY() {
		return koo_y;
	}

	// Bildschirmkoordinate I setzen
	public void setKooI(float kooI) {
		this.koo_i = kooI;
	}

	public float getKooI() {
		return koo_i;
	}

	// Bildschirmkoordinate J setzen
	public void setKooJ(float kooJ) {
		this.koo_j = kooJ;
	}

	public float getKooJ() {
		return koo_j;
	}

}
