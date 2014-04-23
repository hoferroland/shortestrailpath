package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.algorithm.Dijkstra;
import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.NeighbourCalculator;

public class GuiMainHandler implements IGuiMainHandler {

	// ActionListener actionListener = new ActionListener();
	Map<Long, Betriebspunkt> bpMap = new HashMap<Long, Betriebspunkt>();
	static Map<Long, BetriebspunktVerbindungen> bpVerbMap = new HashMap<Long, BetriebspunktVerbindungen>();
	static Map<Long, BpHelper> helperMap = new HashMap<Long, BpHelper>();

	List<Row> bhfRows = new ArrayList<Row>();
	List<ResultRow> bpResultRows = new ArrayList<ResultRow>();

	static List<BpHelper> shortestPath = new ArrayList<BpHelper>();

	static BpHelper startHelper = null;
	static BpHelper zielHelper = null;

	public GuiMainHandler(Map<Long, Betriebspunkt> bpMap,
			Map<Long, BetriebspunktVerbindungen> bpVerbMap,
			Map<Long, BpHelper> helperMap) {
		this.bpMap = bpMap;
		getSortedHelperMap();
		this.helperMap = helperMap;
		this.bpVerbMap = bpVerbMap;

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
			if ((bp.getUic() == 85) & (bp.getBetriebspunkt_typ() <= 2)) {
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
		MainFrame mainFrame = new MainFrame(bhfRows, shortestPath);
		// MainFrame mainFrame = new MainFrame(bhfRows);
		System.out.println("Grösse shortestPath bei GuiMainHandler: "
				+ shortestPath.size());
		mainFrame.setVisible(true);
		return bhfRows;

	}

	public List<ResultRow> getResult(List<BpHelper> shortestPath) {

		this.shortestPath = shortestPath;
		System.out.println("Grösse shortestPath nach Suche: "
				+ shortestPath.size());
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
		MainFrame.refreshResultPane(bpResultRows);
		return bpResultRows;

	}

	public void getWork(long start_id, long ziel_id) {
		startHelper = helperMap.get(start_id);
		zielHelper = helperMap.get(ziel_id);

		if (start_id != ziel_id) {

			Dijkstra dijkstra = new Dijkstra(helperMap);
			dijkstra.work(bpVerbMap, startHelper, helperMap);
			shortestPath = dijkstra.getShortestPath(startHelper, zielHelper);
			getResult(shortestPath);
			System.out.println("Grösse shortestPath bei Klick auf los: "
					+ shortestPath.size());
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
}
