package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.algorithm.Dijkstra;
import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BorderPoint;
import ch.zhaw.hoferrol.shortestrailpath.topologie.NeighbourCalculator;

public class GuiMainHandler implements IGuiMainHandler {

	private static final Logger LOG = Logger.getLogger(GuiMainHandler.class);

	// ActionListener actionListener = new ActionListener();
	Map<Long, Betriebspunkt> bpMap = new HashMap<Long, Betriebspunkt>();
	static Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();
	static Map<Long, BpHelper> helperMap = new HashMap<Long, BpHelper>();

	List<Row> bhfRows = new ArrayList<Row>();
	List<ResultRow> bpResultRows = new ArrayList<ResultRow>();
	private float runTime;

	static List<BpHelper> shortestPath = new ArrayList<BpHelper>();
	static List<BpHelper> greenBpList = new ArrayList<BpHelper>();
	static List<BorderPoint> border = new ArrayList<BorderPoint>();

	static BpHelper startHelper = null;
	static BpHelper zielHelper = null;

	public GuiMainHandler(Map<Long, Betriebspunkt> bpMap,
			Map<Long, BetriebspunktVerbindungen> bpVerbMap,
			Map<Long, BpHelper> helperMap, List<BorderPoint> border) {
		this.bpMap = bpMap;
		getSortedHelperMap();
		this.helperMap = helperMap;
		this.bpVerbMap = bpVerbMap;
		this.border = border;

	}

	public GuiMainHandler() {

	}

	public List<Row> getSortedHelperMap() {
		// TODO Auto-generated method stub

		List<Betriebspunkt> bhfList = new ArrayList<Betriebspunkt>();

		// Iterieren über die bpMap
		Iterator<Long> it = bpMap.keySet().iterator();

		while (it.hasNext()) {
			Object key = it.next();
			Betriebspunkt bp = (Betriebspunkt) bpMap.get(key);
			if ((bp.getUic() == 85)
					&& ((bp.getBetriebspunkt_typ() <= 2)
							|| (bp.getBetriebspunkt_typ() == 4)
							|| (bp.getBetriebspunkt_typ() == 5) || (bp
							.getBetriebspunkt_typ() == 6))) {
				bhfList.add(bp);
			}

			// if (bp.getBetriebspunkt_typ() == 1
			// || bp.getBetriebspunkt_typ() == 4) {
			// bhfList.add(bp);
			// }
		}
		Collections.sort(bhfList, new Comparator<Betriebspunkt>() {
			public int compare(final Betriebspunkt bp1, final Betriebspunkt bp2) {
				return bp1.toString().compareTo(bp2.toString());
			}
		});

		// TODO Rückgabe einer Liste mit id-bp und bezeichnung zur Verwendung im
		// GUI

		// (Debug und Test)
		// Ausgabe der sortierten Liste auf Konsole
		Betriebspunkt bhf = null;
		Row tmpBhf;

		for (int i = 0; i < bhfList.size(); i++) {
			bhf = (Betriebspunkt) bhfList.get(i);

			tmpBhf = new Row(bhf.getId_betriebspunkt(), bhf.getBezeichnung());
			bhfRows.add(tmpBhf);

			// System.out.println(bhf.getBezeichnung());

		}
		MainFrame mainFrame = new MainFrame(bhfRows, shortestPath, border);
		// MainFrame mainFrame = new MainFrame(bhfRows);
		LOG.debug("Grösse shortestPath bei GuiMainHandler: "
				+ shortestPath.size());
		mainFrame.setVisible(true);

		return bhfRows;

	}

	public String getShortestDistanzToStart() {
		if (shortestPath.size() > 0) {
			float dist = ((float) zielHelper.getDistanzZumStart() / 1000);
			MainFrame.showDistanzStart(String.valueOf(dist));
			// System.out.println(String.valueOf(dist));
			return (String.valueOf(dist));

		} else {
			return String.valueOf(Long.MAX_VALUE);
		}
	}

	public List<ResultRow> getResult(List<BpHelper> shortestPath) {

		this.shortestPath = shortestPath;
		BpHelper helper = null;
		Betriebspunkt helperBp = null;
		ResultRow tmpBp;
		long distToNext = 0;

		for (int i = 0; i < (shortestPath.size() - 1); i++) {
			helper = (BpHelper) shortestPath.get(i);
			helperBp = helper.getBp();

			distToNext = NeighbourCalculator.getDistanz(helper.getBp()
					.getId_betriebspunkt(), shortestPath.get(i + 1).getBp()
					.getId_betriebspunkt());

			tmpBp = new ResultRow(helperBp.getId_betriebspunkt(),
					helperBp.getBezeichnung(), helperBp.getAbkuerzung(),
					helperBp.getBetriebspunkt_typ(), helperBp.getKoo_x(),
					helperBp.getKoo_y(), distToNext);

			bpResultRows.add(tmpBp);
		}
		helper = (BpHelper) shortestPath.get(shortestPath.size() - 1);
		helperBp = helper.getBp();
		bpResultRows.add(new ResultRow(helperBp.getId_betriebspunkt(), helperBp
				.getBezeichnung(), helperBp.getAbkuerzung(), helperBp
				.getBetriebspunkt_typ(), helperBp.getKoo_x(), helperBp
				.getKoo_y(), 0L));
		MainFrame.refreshResultPane(bpResultRows, greenBpList);
		// MainFrame.refreshResultPane(bpResultRows);
		getShortestDistanzToStart();
		getRuntime();
		// MainFrame.showRuntime(getRuntime());
		return bpResultRows;

	}

	public void getWork(long start_id, long ziel_id, int modus) {
		startHelper = helperMap.get(start_id);
		zielHelper = helperMap.get(ziel_id);

		if (start_id != ziel_id) {

			Dijkstra dijkstra = new Dijkstra(helperMap);
			// int modus = Dijkstra.MODUS_OPTIMIERT;
			// int modus = Dijkstra.MODUS_CLASSIC;
			// int modus = Dijkstra.MODUS_ASTERN;
			greenBpList = dijkstra.work(bpVerbMap, startHelper, zielHelper,
					modus, helperMap);
			LOG.info("Grösse greenBpList im GuiMainHandler beträgt: "
					+ greenBpList.size());
			// dijkstra.work(bpVerbMap, startHelper, zielHelper, modus,
			// helperMap);
			shortestPath = dijkstra.getShortestPath(startHelper, zielHelper);
			runTime = ((float) dijkstra.getLaufzeit() / 1000000);
			getResult(shortestPath);
			LOG.info("Laufzeit: " + dijkstra.getLaufzeit());

		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"Ausgangsort entspricht dem Zielort. Bitte Auswahl überprüfen",
							"Start ist gleich Ziel", 0);
		}

	}

	public List<BpHelper> guiGetShortestPath() {
		return shortestPath;
	}

	public void getRuntime() {
		MainFrame.showRuntime(runTime);
		LOG.info("Laufzeit in Methode getRuntime " + runTime);

	}

	public List<BpHelper> getBesuchteBp() {
		return greenBpList;
	}

}
