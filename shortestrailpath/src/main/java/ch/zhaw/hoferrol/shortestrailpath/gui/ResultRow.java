package ch.zhaw.hoferrol.shortestrailpath.gui;

class ResultRow {

	private long id_bp;
	private String id_bp_s;
	private String bp_name;
	private String bp_abk;
	private int bp_typ;
	private String bp_typ_s;
	private String bp_typkurz;
	private String bp_typlang;
	private float xKoo;
	private String xKoo_s;
	private float yKoo;
	private String yKoo_s;
	private long distToNext;
	private String distToNext_s;

	public ResultRow(long id_bp, String bp_name, String bp_abk, int bp_typ,
			String bp_typkurz, String bp_typlang, float xKoo, float yKoo,
			long distToNext) {
		this.id_bp = id_bp;
		this.id_bp_s = String.valueOf(id_bp);
		this.bp_name = bp_name;
		this.bp_abk = bp_abk;
		this.bp_typ = bp_typ;
		this.bp_typ_s = String.valueOf(bp_typ);
		this.bp_typkurz = bp_typkurz;
		this.bp_typlang = bp_typlang;
		this.xKoo = xKoo;
		this.xKoo_s = Float.toString(xKoo);
		this.yKoo = yKoo;
		this.yKoo_s = Float.toString(yKoo);
		this.distToNext = distToNext;
		this.distToNext_s = String.valueOf(distToNext);
	}

	public long getId() {
		return id_bp;
	}

	public String getId_s() {
		return id_bp_s;
	}

	public String getBpName() {
		return bp_name;
	}

	public String getBpAbk() {
		return bp_abk;
	}

	public int getBpTyp() {
		return bp_typ;
	}

	public String getBpTyp_s() {
		return bp_typ_s;
	}

	public String getBpTypKurz() {
		return bp_typkurz;
	}

	public String getBpTypLang() {
		return bp_typlang;
	}

	public float getxKoo() {
		return xKoo;
	}

	public String getxKoo_s() {
		return xKoo_s;
	}

	public float getyKoo() {
		return yKoo;
	}

	public String getyKoo_s() {
		return yKoo_s;
	}

	public long getDistToNext() {
		return distToNext;
	}

	public String getDistToNext_s() {
		return distToNext_s;
	}

	public String toString() {
		return (bp_name + ", " + bp_abk);
	}

}
