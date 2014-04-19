package ch.zhaw.hoferrol.shortestrailpath.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.gui.GuiMainHandler;
import ch.zhaw.hoferrol.shortestrailpath.gui.IGuiMainHandler;
import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpVerbKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBPVerbindungenImport;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBetriebspunkteImport;

public class Main {

	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();
	static List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();
	static Map<Long, Betriebspunkt> hashBp = new HashMap<Long, Betriebspunkt>();
	static Map<Long, BetriebspunktVerbindungen> hashBpVerb = new HashMap<Long, BetriebspunktVerbindungen>();
	static BpHelper startHelper;
	static BpHelper zielHelper;
	private static Map<Long, BpHelper> helperMap = new HashMap<Long, BpHelper>();

	private static List<BpHelper> shortestPath = new ArrayList<BpHelper>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLBetriebspunkteImport xmlBpImp = new XMLBetriebspunkteImport();
		xmlBpImp.xmlBpImport();
		// XMLBetriebspunkteImport.xmlBpImport();
		bpList = xmlBpImp.getBpList();
		BpKonverter bpKonv = new BpKonverter();
		bpKonv.convertBP(bpList);
		hashBp = bpKonv.getHashBp();
		// bpKonv.getHashBp();

		XMLBPVerbindungenImport xmlBpVerbImp = new XMLBPVerbindungenImport();
		xmlBpVerbImp.xmlBpVerbindungImport(hashBp);
		// XMLBPVerbindungenImport.xmlBpVerbindungImport();
		bpVerbList = xmlBpVerbImp.getBpVerbList();
		BpVerbKonverter bpVerbKonv = new BpVerbKonverter();
		bpVerbKonv.convertBpVerb(bpVerbList);
		hashBpVerb = bpVerbKonv.getHashBpVerb();

		// GUI-Aufruf

		// Dijkstra
		// BpHelper.buildHelperList(hashBp, hashBpVerb);
		BpHelper.buildHelperMap(hashBp, hashBpVerb);
		// BpHelper.buildHelperList(hashBp, hashBpVerb);

		helperMap = BpHelper.buildHelperMap(hashBp, hashBpVerb);

		IGuiMainHandler guiMainHandler = new GuiMainHandler(hashBp, hashBpVerb,
				helperMap);

		// Dijkstra dijkstra = new Dijkstra(helperMap);
		// Dijkstra dijkstra = new Dijkstra(BpHelper.buildHelperList(hashBp,
		// hashBpVerb));

		// Durchführung des Tests A = Startknoten

		// Betriebspunkt startBp = hashBp.get(182L);

		// BpHelper startHelper = helperMap.get(167L); // Murgenthal
		// BpHelper startHelper = helperMap.get(182L); // Olten
		// BpHelper zielHelper = helperMap.get(182L); // Olten
		// BpHelper zielHelper = helperMap.get(241L); // Trimbach
		// BpHelper zielHelper = helperMap.get(406L); // Winti

		// dijkstra.work(hashBpVerb, startHelper, helperMap);

		// dijkstra.work(hashBpVerb, startHelper,
		// BpHelper.buildHelperList(hashBp, hashBpVerb));
		// Ausgabe des kürzesten Weges zwischen Olten (id_bp 182) und
		// Winterthur (id_bp 406)
		// shortestPath = dijkstra.getShortestPath(startHelper, zielHelper);
		// guiMainHandler.getResult(shortestPath);

	}

}
