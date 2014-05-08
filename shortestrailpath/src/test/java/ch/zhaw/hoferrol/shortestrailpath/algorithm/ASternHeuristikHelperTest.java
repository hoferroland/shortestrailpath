package ch.zhaw.hoferrol.shortestrailpath.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;

public class ASternHeuristikHelperTest {

	@Test
	public void test() {

		// double[] xKoo = new double[1];
		// double[] yKoo = new double[1];
		Map<Long, BpHelper> allBpMap = new HashMap<Long, BpHelper>();

		// double airDist = Long.MAX_VALUE;
		// double airDist2 = Long.MAX_VALUE;

		double airDistTest1 = 3.605551275463989;

		Betriebspunkt bp1 = new Betriebspunkt(null, null, 100, 0, 5L, 5L, 0);
		Betriebspunkt bp2 = new Betriebspunkt(null, null, 101, 0, 2L, 3L, 0);
		BpHelper vonBpHelper = new BpHelper(bp1);
		BpHelper nachBpHelper = new BpHelper(bp2);
		allBpMap.put(vonBpHelper.bp.getId_betriebspunkt(), vonBpHelper);
		allBpMap.put(nachBpHelper.bp.getId_betriebspunkt(), nachBpHelper);
		// xKoo[0] = 5d;
		// xKoo[1] = 2d;
		// yKoo[0] = 5d;
		// yKoo[1] = 3d;

		ASternHeuristikHelper aSternHelper = new ASternHeuristikHelper(allBpMap);
		// aSternHelper.getAirDistance(vonBpHelper.bp.getId_betriebspunkt(),
		// nachBpHelper.bp.getId_betriebspunkt());

		assertEquals(
				aSternHelper.getAirDistance(
						vonBpHelper.bp.getId_betriebspunkt(),
						nachBpHelper.bp.getId_betriebspunkt()), airDistTest1);
		// assertEquals(n.getDistanz(nextBpId2, bpId), distanz2);
		// assertEquals(n.getDistanz(bpId, nextBpId1), distanz3);

	}

}
