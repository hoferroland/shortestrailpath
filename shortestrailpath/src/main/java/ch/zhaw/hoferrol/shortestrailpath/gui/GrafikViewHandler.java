package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BorderPoint;

/**
 * Klasse GrafikViewHandler - berechnet aus den effektiven x und y Koordinaten
 * der BpHelper sowie der BorderPoint die zur Anzeige nötigen
 * Bildschirm-Koordinaten.
 * 
 * Eingabeparameter:
 * 
 * List BpHelper (greenBpList = Suchresultat)
 * 
 * List BorderPoint (Punkte für das Zeichnen der CH-Karte)
 * 
 * Array Grafiksize (Uebergabe der Grösse des Zeichenblattes)
 * 
 * Rückgabewert:
 * 
 * keinen. Es werden die Bildschirmkoordinaten bei den BpHelpern sowie den
 * BorderPoint abgespeichert.
 * 
 * @author Roland Hofer, V1.2 - 08.05.2014
 */

public class GrafikViewHandler {

	private static final Logger LOG = Logger.getLogger(GrafikViewHandler.class);
	private List<BpHelper> shortestpath = new ArrayList<BpHelper>();
	private List<BpHelper> greenBpList = new ArrayList<BpHelper>();
	private List<BorderPoint> border = new ArrayList<BorderPoint>();

	private static final float xKooMax = 838000;
	private static final float xKooMin = 485000;
	private static final float yKooMax = 295000;
	private static final float yKooMin = 75000;
	private int iKooMax;
	private int iKooMin;
	private int jKooMax;
	private int jKooMin;

	// Konstruktor
	public GrafikViewHandler(List<BpHelper> shortestpath,
			List<BpHelper> greenBpList, List<BorderPoint> border,
			int[] grafiksize) {

		this.shortestpath = shortestpath;
		this.border = border;
		this.greenBpList = greenBpList;
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
		BorderPoint borderpoint;

		for (int i = 0; i < (shortestpath.size()); i++) {
			kooHelper = (BpHelper) shortestpath.get(i);
			helperBildschirmKooUmrechner(i, kooHelper);
		}

		for (int i = 0; i < (greenBpList.size()); i++) {
			kooHelper = (BpHelper) greenBpList.get(i);

			helperBildschirmKooUmrechner(i, kooHelper);
		}

		// Koordinatenumrechnung für Grenzpunkte (Schweizer-Karte) von
		// LV03 auf Bildschirmkoordinaten
		for (int iborder = 0; iborder < (border.size()); iborder++) {
			borderpoint = (BorderPoint) border.get(iborder);
			xKoo = borderpoint.getKooX();
			yKoo = borderpoint.getKooY();

			iKoo = ((xKoo - xKooMin) / (xKooMax - xKooMin))
					* (iKooMax - iKooMin);

			jKoo = (1 - ((yKoo - yKooMin) / (yKooMax - yKooMin)))
					* (jKooMax - jKooMin);

			borderpoint.setKooI(iKoo);
			borderpoint.setKooJ(jKoo);

			LOG.debug("Ausgabe Zeichenkoordinaten BorderPoint: ID: "
					+ borderpoint.getId() + ", I-Koo: " + borderpoint.getKooI()
					+ ", J-Koo: " + borderpoint.getKooJ());
		}

	}

	// Koordinatenumrechnung von LV03 auf Bildschirmkoordinaten für BpHelper
	private void helperBildschirmKooUmrechner(int i, BpHelper kooHelper) {
		float xKoo;
		float yKoo;
		float iKoo;
		float jKoo;
		xKoo = kooHelper.getBp().getKoo_x();
		yKoo = kooHelper.getBp().getKoo_y();

		iKoo = ((xKoo - xKooMin) / (xKooMax - xKooMin)) * (iKooMax - iKooMin);

		jKoo = (1 - ((yKoo - yKooMin) / (yKooMax - yKooMin)))
				* (jKooMax - jKooMin);

		kooHelper.setIKoo(iKoo);
		kooHelper.setJKoo(jKoo);

		LOG.debug("Ausgabe neue Koordinaten pro Bp: "
				+ kooHelper.getBp().getBezeichnung() + "  I-Koo: "
				+ kooHelper.getIKoo() + "  J-Koo: " + kooHelper.getJKoo());
	}

}