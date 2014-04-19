package ch.zhaw.hoferrol.shortestrailpath.gui;

class Row {

	private long id;
	private String val;

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
