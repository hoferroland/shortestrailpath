package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Test-Klasse NeighbourTest
 * 
 * @author Roland Hofer, V1.12 - 21.05.2014
 */

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class NeighbourBPTest {

	@Test
	public void testGetNeighbours() {
		long bpId = 23L;
		long nextBpId1 = 199L;
		long nextBpId2 = 39L;
		Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();

		// Aufbau der Testdaten
		BetriebspunktVerbindungen v1 = new BetriebspunktVerbindungen(123L,
				nextBpId1, nextBpId2, 12, 12, null, null);
		BetriebspunktVerbindungen v2 = new BetriebspunktVerbindungen(124L,
				nextBpId2, bpId, 12, 12, null, null);
		BetriebspunktVerbindungen v3 = new BetriebspunktVerbindungen(125L,
				bpId, nextBpId1, 12, 12, null, null);

		bpVerbMap.put(123L, v1);
		bpVerbMap.put(124L, v2);
		bpVerbMap.put(125L, v3);

		Map<Long, Betriebspunkt> bpMap = new HashMap<Long, Betriebspunkt>();
		bpMap.put(bpId, new Betriebspunkt("ABC", "Test1", bpId, 1, 12, 12, 85));
		bpMap.put(nextBpId1, new Betriebspunkt("BCA", "Test2", nextBpId1, 1,
				12, 12, 85));
		bpMap.put(nextBpId2, new Betriebspunkt("CAB", "Test3", nextBpId2, 1,
				12, 12, 85));

		// Testdurchführung
		NeighbourCalculator n = new NeighbourCalculator(bpVerbMap, bpMap);

		List<Betriebspunkt> neighbours = n.getNext(bpId);

		assertEquals(2, neighbours.size());
		assertEquals(neighbours.get(0).getId_betriebspunkt(), nextBpId1);
		assertEquals(neighbours.get(1).getId_betriebspunkt(), nextBpId2);

	}

	@Test
	public void testGetDistanz() {
		long bpId = 23L;
		long nextBpId1 = 199L;
		long nextBpId2 = 39L;

		long distanz1 = 399;
		long distanz2 = 38;
		long distanz3 = 838;

		Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();
		Map<Long, Betriebspunkt> bpMap = new HashMap<Long, Betriebspunkt>();

		// Aufbereitung der Testdaten
		BetriebspunktVerbindungen v1 = new BetriebspunktVerbindungen(123L,
				nextBpId1, nextBpId2, distanz1, 12, null, null);
		BetriebspunktVerbindungen v2 = new BetriebspunktVerbindungen(124L,
				nextBpId2, bpId, distanz2, 12, null, null);
		BetriebspunktVerbindungen v3 = new BetriebspunktVerbindungen(125L,
				bpId, nextBpId1, distanz3, 12, null, null);

		bpVerbMap.put(123L, v1);
		bpVerbMap.put(124L, v2);
		bpVerbMap.put(125L, v3);

		bpMap.put(bpId, new Betriebspunkt("ABC", "Test1", bpId, 1, 12, 12, 85));
		bpMap.put(nextBpId1, new Betriebspunkt("BCA", "Test2", nextBpId1, 1,
				12, 12, 85));
		bpMap.put(nextBpId2, new Betriebspunkt("CAB", "Test3", nextBpId2, 1,
				12, 12, 85));

		// Testdurchführung
		NeighbourCalculator n = new NeighbourCalculator(bpVerbMap, bpMap);

		assertEquals(n.getDistanz(nextBpId1, nextBpId2), distanz1);
		assertEquals(n.getDistanz(nextBpId2, bpId), distanz2);
		assertEquals(n.getDistanz(bpId, nextBpId1), distanz3);
	}

}
