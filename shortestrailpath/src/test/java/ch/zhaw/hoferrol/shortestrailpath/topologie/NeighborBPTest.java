package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NeighborBPTest {

	@Test
	public void test() {
		long bpId = 23L;
		long nextBpId1 = 199L;
		long nextBpId2 = 39L;
		Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();

		BetriebspunktVerbindungen v1 = new BetriebspunktVerbindungen(123L,
				nextBpId1, nextBpId2, 12, 12, null, null);
		BetriebspunktVerbindungen v2 = new BetriebspunktVerbindungen(123L,
				nextBpId2, bpId, 12, 12, null, null);
		BetriebspunktVerbindungen v3 = new BetriebspunktVerbindungen(123L,
				bpId, nextBpId1, 12, 12, null, null);

		bpVerbMap.put(1L, v1);
		bpVerbMap.put(2L, v2);
		bpVerbMap.put(3L, v3);

		// Collection<BetriebspunktVerbindungen> verbindungen =
		// Arrays.asList(v1,
		// v2, v3);

		// NeighbourCalculator n = new NeighbourCalculator(verbindungen);
		// NeighbourCalculator n = new NeighbourCalculator(bpVerbMap);
		//
		// // Map<Long, List<Betriebspunkt>> neighbours = n.g
		// // n.getNext(bpId);
		// // List<Long> neighbours = n.getNext(bpId);
		// assertEquals(neighbours.size(), 2);
		// assertEquals(neighbours.get(1), nextBpId2);
		// assertEquals(neighbours.get(1), nextBpId1);

	}

	@Test
	public void testWeight() {
		long bpId = 23L;
		long nextBpId1 = 199L;
		long nextBpId2 = 39L;

		long distanz1 = 399;
		long distanz2 = 38;
		long distanz3 = 838;

		BetriebspunktVerbindungen v1 = new BetriebspunktVerbindungen(123L,
				nextBpId1, nextBpId2, distanz1, 12, null, null);
		BetriebspunktVerbindungen v2 = new BetriebspunktVerbindungen(123L,
				nextBpId2, bpId, distanz2, 12, null, null);
		BetriebspunktVerbindungen v3 = new BetriebspunktVerbindungen(123L,
				bpId, nextBpId1, distanz3, 12, null, null);

		Collection<BetriebspunktVerbindungen> verbindungen = Arrays.asList(v1,
				v2, v3);
		//
		// NeighbourCalculator n = new NeighbourCalculator(verbindungen);
		//
		// assertEquals(n.getDistanz(nextBpId1, nextBpId2), distanz1);
		// assertEquals(n.getDistanz(nextBpId2, bpId), distanz2);
		// assertEquals(n.getDistanz(bpId, nextBpId1), distanz3);
	}

}
