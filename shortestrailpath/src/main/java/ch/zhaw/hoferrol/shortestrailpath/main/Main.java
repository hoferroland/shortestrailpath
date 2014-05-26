package ch.zhaw.hoferrol.shortestrailpath.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.gui.GuiMainHandler;
import ch.zhaw.hoferrol.shortestrailpath.gui.IGuiMainHandler;
import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BorderPoint;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpVerbKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.CHBordersImport;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBPVerbindungenImport;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBetriebspunkteImport;

/**
 * Klasse Main - Startklasse von ShortestRailPath. Initialisiert
 * Topologie-Import und ruft via Interface IGuiMainHandler das GUI auf.
 * 
 * @author Roland Hofer, V1.4 - 25.05.2014
 * 
 */

public class Main {

	private static final Logger LOG = Logger.getLogger(Main.class);
	static List<BorderPoint> border = new ArrayList<BorderPoint>();
	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();
	static List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();
	static Map<Long, Betriebspunkt> hashBp = new HashMap<Long, Betriebspunkt>();
	static Map<Long, BetriebspunktVerbindungen> hashBpVerb = new HashMap<Long, BetriebspunktVerbindungen>();
	static BpHelper startHelper;
	static BpHelper zielHelper;
	private static Map<Long, BpHelper> helperMap = new HashMap<Long, BpHelper>();
	private static final String INTERNAL_BP_FILE_NAME = "betriebspunkte_internal.xml";
	private static final String EXTERNAL_BP_FILE_NAME = "betriebspunkte.xml";
	private static final String INTERNAL_BPVerb_FILE_NAME = "betriebspunktverbindungen_internal.xml";
	private static final String EXTERNAL_BPVerb_FILE_NAME = "betriebspunktverbindungen.xml";

	private static final String CHBORDER_FILE_NAME = "/CHBorders.txt";

	public static void main(String[] args) {

		// Betriebspunkte-Import wird initiiert
		URL bpImportUrl = getBpImportUrl();
		XMLBetriebspunkteImport xmlBpImp = new XMLBetriebspunkteImport(
				bpImportUrl);
		bpList = xmlBpImp.xmlBpImport();

		BpKonverter bpKonv = new BpKonverter();
		bpKonv.convertBP(bpList);
		hashBp = bpKonv.getHashBp();

		// BetriebspunkteVerbindungen-Import wird initiiert
		URL bpVerbImportUrl = getBpVerbImportUrl();
		XMLBPVerbindungenImport xmlBpVerbImp = new XMLBPVerbindungenImport(
				bpVerbImportUrl);
		bpVerbList = xmlBpVerbImp.xmlBpVerbindungImport(hashBp);
		BpVerbKonverter bpVerbKonv = new BpVerbKonverter();
		bpVerbKonv.convertBpVerb(bpVerbList);
		hashBpVerb = bpVerbKonv.getHashBpVerb();

		// Grenzpunkte-Import wird initiiert
		CHBordersImport borderImport = new CHBordersImport(CHBORDER_FILE_NAME);
		border = borderImport.importBorders();
		LOG.debug("Grösse Border-Liste in Main beträgt: " + border.size());

		// BpHelper werden angelegt
		helperMap = BpHelper.buildHelperMap(hashBp, hashBpVerb);

		// Initiieren des GUI
		IGuiMainHandler guiMainHandler = new GuiMainHandler(hashBp, hashBpVerb,
				helperMap, border);

		// // Auskommentierte Aufrufe für Debugging ohne GUI
		//
		// // Dijkstra dijkstra = new Dijkstra(helperMap);
		//
		// // Durchführung des Tests A = Startknoten
		//
		// // BpHelper startHelper = helperMap.get(167L); // Murgenthal
		// // BpHelper startHelper = helperMap.get(171L); // Niederbipp
		// // BpHelper startHelper = helperMap.get(1124L); // Genève
		// // BpHelper startHelper = helperMap.get(182L); // Olten
		// // BpHelper zielHelper = helperMap.get(182L); // Olten
		// // BpHelper zielHelper = helperMap.get(392L); // Lausanne
		// // BpHelper zielHelper = helperMap.get(573L); // Aarwangen
		// // BpHelper zielHelper = helperMap.get(241L); // Trimbach
		// // BpHelper zielHelper = helperMap.get(406L); // Winti
		//
		// // dijkstra.work(hashBpVerb, startHelper, zielHelper, 1, helperMap);
		//
		// // BpHelper.buildHelperList(hashBp, hashBpVerb));
		// // shortestPath = dijkstra.getShortestPath(startHelper, zielHelper);

	}

	private static URL getBpImportUrl() {
		// falls externes file gefunden wird
		File f = new File(EXTERNAL_BP_FILE_NAME);
		if (f.canRead()) {
			try {
				return f.toURI().toURL();
			} catch (MalformedURLException e) {
				// fallback
			}
		}

		// fallback: internes file:
		URL url = Main.class.getClass()
				.getResource("/" + INTERNAL_BP_FILE_NAME);
		if (url == null) {
			throw new IllegalArgumentException("interne datei "
					+ INTERNAL_BP_FILE_NAME
					+ " zum einlesen der betriebspunkte wurde nicht gefunden!");
		}
		return url;
	}

	private static URL getBpVerbImportUrl() {
		// falls externes file gefunden wird
		File f = new File(EXTERNAL_BPVerb_FILE_NAME);
		if (f.canRead()) {
			try {
				return f.toURI().toURL();
			} catch (MalformedURLException e) {
				// fallback
			}
		}

		// fallback: internes file:
		URL url = Main.class.getClass().getResource(
				"/" + INTERNAL_BPVerb_FILE_NAME);
		if (url == null) {
			throw new IllegalArgumentException("interne datei "
					+ INTERNAL_BPVerb_FILE_NAME
					+ " zum einlesen der betriebspunkte wurde nicht gefunden!");
		}
		return url;
	}

}
