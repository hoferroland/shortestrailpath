package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;

public class GrafikViewHandler {

	private List<BpHelper> shortestpath = new ArrayList<BpHelper>();
	// private Map<Long, BpHelper> shortestKooHelper = new HashMap<Long,
	// BpHelper>();

	private static final float xKooMax = 818000;
	private static final float xKooMin = 485000;
	private static final float yKooMax = 295000;
	private static final float yKooMin = 75000;
	private int iKooMax;
	private int iKooMin;
	private int jKooMax;
	private int jKooMin;
	private int[] grafiksize = new int[3];

	public GrafikViewHandler(List<BpHelper> shortestpath, int[] grafiksize) {

		this.shortestpath = shortestpath;
		iKooMax = grafiksize[0];
		iKooMin = grafiksize[1];
		jKooMax = grafiksize[2];
		jKooMin = grafiksize[3];

		getViewKoo();
	}

	public void getViewKoo() {

		float xKoo;
		float yKoo;
		float iKoo;
		float jKoo;
		BpHelper kooHelper;

		System.out.println("Bin in Methode 'getViewKoo' angekommen");
		System.out.println("Grösse von shortestpath: " + shortestpath.size());

		for (int i = 0; i < (shortestpath.size()); i++) {
			kooHelper = (BpHelper) shortestpath.get(i);
			xKoo = kooHelper.getBp().getKoo_x();
			yKoo = kooHelper.getBp().getKoo_y();

			iKoo = ((xKoo - xKooMin) / (xKooMax - xKooMin))
					* (iKooMax - iKooMin);
			System.out.println("Bp: " + kooHelper.getBp().getBezeichnung()
					+ " iKoo = " + iKoo);

			jKoo = (1 - ((yKoo - yKooMin) / (yKooMax - yKooMin)))
					* (jKooMax - jKooMin);

			kooHelper.setIKoo(iKoo);
			kooHelper.setJKoo(jKoo);

			System.out.println("Ausgabe neue Koordinaten pro Bp: "
					+ kooHelper.getBp().getBezeichnung() + "  I-Koo: "
					+ kooHelper.getIKoo() + "  J-Koo: " + kooHelper.getJKoo());
		}

	}

}