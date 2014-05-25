package ch.zhaw.hoferrol.shortestrailpath.algorithm;

/**
 * Test-Klasse ASternHeuristikHelperTest
 * 
 * @author Roland Hofer, V1.1 - 28.04.2014
 */

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;

public class ASternHeuristikHelperTest {

	@Test
	public void test() {

		Map<Long, BpHelper> allBpMap = new HashMap<Long, BpHelper>();

		// Variabel für erwartetes Resultat
		double airDistTest1 = 3.605551275463989;

		// Aufbereitung der Testdaten
		Betriebspunkt bp1 = new Betriebspunkt(null, null, 100, 0, 5L, 5L, 0);
		Betriebspunkt bp2 = new Betriebspunkt(null, null, 101, 0, 2L, 3L, 0);
		BpHelper vonBpHelper = new BpHelper(bp1);
		BpHelper nachBpHelper = new BpHelper(bp2);
		allBpMap.put(vonBpHelper.bp.getId_betriebspunkt(), vonBpHelper);
		allBpMap.put(nachBpHelper.bp.getId_betriebspunkt(), nachBpHelper);

		ASternHeuristikHelper aSternHelper = new ASternHeuristikHelper(allBpMap);

		// Testdurchführung
		assertEquals(
				aSternHelper.getAirDistance(
						vonBpHelper.bp.getId_betriebspunkt(),
						nachBpHelper.bp.getId_betriebspunkt()), airDistTest1);

	}

}
