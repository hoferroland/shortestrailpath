package ch.zhaw.hoferrol.shortestrailpath.gui;

/**
 * Klasse Row - Verwaltet Zeilen mit id und einem dazugehörenden Wert. Dies wird
 * verwendet um später im MainFrame in den Dropdown-Feldern einen Bahnhof
 * auswählen zu können, jedoch mit der ID dazu arbeiten zu können (für Zugriff
 * in HashMap).
 * 
 * @author Roland Hofer, V1.1 - 11.05.2014
 * 
 */

class Row {

	private long id;
	private String val;

	// Konstruktor
	public Row(long id, String val) {
		this.id = id;
		this.val = val;
	}

	public long getId() {
		return id;
	}

	public String getVal() {
		return val;
	}

	public String toString() {
		return val;
	}
}
