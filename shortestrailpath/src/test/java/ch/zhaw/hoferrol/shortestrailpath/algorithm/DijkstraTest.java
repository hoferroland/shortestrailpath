package ch.zhaw.hoferrol.shortestrailpath.algorithm;

/**
 * Test-Klasse DijkstraTest
 * 
 * @author Roland Hofer, V1.8 - 25.05.2014
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpStatusEnum;

public class DijkstraTest {

	@Test
	public void changeBpToGreenTest() {

		// Aufbereitung der Testdaten
		Map<Long, BpHelper> bpHelperMap = new HashMap<Long, BpHelper>();
		List<BpHelper> redBpList = new ArrayList<BpHelper>();

		Betriebspunkt bpRed = new Betriebspunkt("BpRed", "BpRed", 100, 0, 0, 0,
				0);
		Betriebspunkt bpGreen = new Betriebspunkt("BpGreen", "BpGreen", 101, 0,
				0, 0, 0);

		BpHelper helperRed = new BpHelper(bpRed);
		BpHelper helperGreen = new BpHelper(bpGreen);

		redBpList.add(helperRed);
		helperRed.setStatus(BpStatusEnum.rot);
		helperGreen.setStatus(BpStatusEnum.gruen);

		Dijkstra dijkstra = new Dijkstra(bpHelperMap);

		dijkstra.changeBpToGreen(helperRed);

		assertSame((helperRed.getStatus()), BpStatusEnum.gruen);

	}

	@Test
	public void getShortestPathTest() {
		Map<Long, BpHelper> allBpMap = new HashMap<Long, BpHelper>();
		List<BpHelper> shortestPathSOLL = new ArrayList<BpHelper>();

		// Aufbereitung der Testdaten
		Betriebspunkt bp1 = new Betriebspunkt("Bp1", "Bp1", 101, 0, 0, 0, 0);
		Betriebspunkt bp2 = new Betriebspunkt("Bp2", "Bp2", 102, 0, 0, 0, 0);
		Betriebspunkt bp3 = new Betriebspunkt("Bp3", "Bp3", 103, 0, 0, 0, 0);
		Betriebspunkt bp4 = new Betriebspunkt("Bp4", "Bp4", 104, 0, 0, 0, 0);
		Betriebspunkt bp5 = new Betriebspunkt("Bp5", "Bp5", 105, 0, 0, 0, 0);
		Betriebspunkt bp6 = new Betriebspunkt("Bp6", "Bp6", 106, 0, 0, 0, 0);

		BpHelper helper1 = new BpHelper(bp1);
		BpHelper helper2 = new BpHelper(bp2);
		BpHelper helper3 = new BpHelper(bp3);
		BpHelper helper4 = new BpHelper(bp4);
		BpHelper helper5 = new BpHelper(bp5);
		BpHelper helper6 = new BpHelper(bp6);

		helper1.setBpVorher(bp6);
		helper2.setBpVorher(bp1);
		helper3.setBpVorher(bp2);
		helper4.setBpVorher(bp3);
		helper5.setBpVorher(bp4);
		helper6.setBpVorher(bp5);

		allBpMap.put(105L, helper5);
		allBpMap.put(102L, helper2);
		allBpMap.put(104L, helper4);
		allBpMap.put(103L, helper3);
		allBpMap.put(106L, helper6);
		allBpMap.put(101L, helper1);

		// Erwartete Liste aufbauen
		shortestPathSOLL.add(helper5);
		shortestPathSOLL.add(helper6);
		shortestPathSOLL.add(helper1);
		shortestPathSOLL.add(helper2);

		Dijkstra dijkstra = new Dijkstra(allBpMap);

		assertEquals(dijkstra.getShortestPath(helper5, helper2),
				shortestPathSOLL);

	}
}
