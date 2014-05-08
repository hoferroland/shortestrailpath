package ch.zhaw.hoferrol.shortestrailpath.algorithm;

import java.util.Map;

/**
 * Klasse ASternHeuristikHelper - berechnet den 'Heuristikwert' für den
 * A*-Algorithmus. Dazu berechnet die Klasse anhand der Koordinaten die
 * Luftdistanz der zwei übergebenen BpHelpern.
 * 
 * Eingabeparameter:
 * 
 * long (id vonBpHelper); long (id nachBpHelper)
 * 
 * Rückgabewert:
 * 
 * double airDist (Luftdistanz)
 * 
 * @author Roland Hofer, V1.0 - 22.04.2014
 * 
 */

public class ASternHeuristikHelper {

	private Map<Long, BpHelper> allBpMap;

	// Konstruktor
	public ASternHeuristikHelper(Map<Long, BpHelper> allBpMap) {
		this.allBpMap = allBpMap;
	}

	// Methode zur Berechnung der Luftdistanz zwischen zwei BpHelpern
	public double getAirDistance(long vonBpHelper, long nachBpHelper) {
		double airDist = Long.MAX_VALUE;
		double[] xKoo = new double[2];
		double[] yKoo = new double[2];

		xKoo[0] = (double) allBpMap.get(vonBpHelper).getBp().getKoo_x();
		xKoo[1] = (double) allBpMap.get(nachBpHelper).getBp().getKoo_x();
		yKoo[0] = (double) allBpMap.get(vonBpHelper).getBp().getKoo_y();
		yKoo[1] = (double) allBpMap.get(nachBpHelper).getBp().getKoo_y();

		airDist = Math.hypot((xKoo[1] - xKoo[0]), (yKoo[1] - yKoo[0]));

		return airDist;

	}

}
