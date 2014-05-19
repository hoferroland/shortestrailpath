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

/**
 * - Klasse GuiMainHandler
 * 
 * - ist zuständig für die Filterung und Sortierung der Betriebspunkte für die
 * Dropdown-Felder im MainFrame und füllt diese zur Anzeige in bhfRows ab.
 * 
 * - Aufruf der 'work'-Methode von Dijkstra mit den nötigen Parametern.
 * 
 * - Aufbereiten der Suchresultate in ResultRows -
 * 
 * - Methode zur Anzeige der Gesamtdistanz in Kilometer
 * 
 * - Methode zur Rückgabe der Laufzeit.
 * 
 * @author Roland Hofer, V1.4 - 17.05.2014
 */

public class GuiMainHandler implements IGuiMainHandler {

	private static final Logger LOG = Logger.getLogger(GuiMainHandler.class);

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

	// Konstruktor
	public GuiMainHandler(Map<Long, Betriebspunkt> bpMap,
			Map<Long, BetriebspunktVerbindungen> bpVerbMap,
			Map<Long, BpHelper> helperMap, List<BorderPoint> border) {
		this.bpMap = bpMap;
		this.helperMap = helperMap;
		this.bpVerbMap = bpVerbMap;
		this.border = border;
		getSortedHelperMap();
	}

	public GuiMainHandler() {

	}

	// Methode zur Filterung und Sortierung von Betriebspunkten für
	// die Dropdown-Felder im MainFrame sowie Aufruf von MainFrame
	// mit Uebergabe der bhfRows.
	public List<Row> getSortedHelperMap() {

		List<Betriebspunkt> bhfList = new ArrayList<Betriebspunkt>();

		// Iterieren über die bpMap
		Iterator<Long> it = bpMap.keySet().iterator();

		while (it.hasNext()) {
			Object key = it.next();
			Betriebspunkt bp = (Betriebspunkt) bpMap.get(key);
			if ((bp.getUic() == 85)
					&& ((bp.getBetriebspunkt_typ() == 0)
							|| (bp.getBetriebspunkt_typ() == 2)
							|| (bp.getBetriebspunkt_typ() == 4)
							|| (bp.getBetriebspunkt_typ() == 5) || (bp
							.getBetriebspunkt_typ() == 6))) {
				bhfList.add(bp);
			}

		}
		Collections.sort(bhfList, new Comparator<Betriebspunkt>() {
			public int compare(final Betriebspunkt bp1, final Betriebspunkt bp2) {
				return bp1.toString().compareTo(bp2.toString());
			}
		});

		Betriebspunkt bhf = null;
		Row tmpBhf;

		for (int i = 0; i < bhfList.size(); i++) {
			bhf = (Betriebspunkt) bhfList.get(i);

			tmpBhf = new Row(bhf.getId_betriebspunkt(), bhf.getBezeichnung());
			bhfRows.add(tmpBhf);
		}

		MainFrame mainFrame = new MainFrame(bhfRows, shortestPath, border);

		mainFrame.setVisible(true);

		return bhfRows;

	}

	// Methode zur Anzeige der Gesamtdistanz in Kilometer
	public String getShortestDistanzToStart() {
		if (shortestPath.size() > 0) {
			float dist = ((float) zielHelper.getDistanzZumStart() / 1000);
			MainFrame.showDistanzStart(String.valueOf(dist));
			return (String.valueOf(dist));

		} else {
			return String.valueOf(Long.MAX_VALUE);
		}
	}

	// Methode zur Aufbereitung des Suchresultates in ResultRows zur
	// Anzeige in der JTable des MainFrame
	public List<ResultRow> getResult(List<BpHelper> shortestPath) {

		this.shortestPath = shortestPath;
		BpHelper helper = null;
		Betriebspunkt helperBp = null;
		ResultRow tmpBp;
		long distToNext = 0;

		// Falls kein Weg gefunden wurde, wird diese dem User in einer
		// MessageBox angezeigt
		if (shortestPath.size() == 0) {
			JOptionPane
					.showMessageDialog(
							null,
							"Es kann kein Weg gefunden werden. \n"
									+ "Mögliche Ursache: \n"
									+ "Fehlende Betriebspunktverbindung an Umsteigebahnhof zwischen zwei Bahnunternehmungen. \n"
									+ "Bitte versuchen Sie eine andere Verbindungsanfrage. Besten Dank.",
							"Kein Weg gefunden", 0);
			LOG.info("Grösse von shortestPath ist 0 - es konnte kein Weg gefunden werden");
		} else {

			for (int i = 0; i < (shortestPath.size() - 1); i++) {
				helper = (BpHelper) shortestPath.get(i);
				helperBp = helper.getBp();

				distToNext = NeighbourCalculator.getDistanz(helper.getBp()
						.getId_betriebspunkt(), shortestPath.get(i + 1).getBp()
						.getId_betriebspunkt());

				tmpBp = new ResultRow(helperBp.getId_betriebspunkt(),
						helperBp.getBezeichnung(), helperBp.getAbkuerzung(),
						helperBp.getBetriebspunkt_typ(), helper.getBpTypKurz(),
						helper.getBpTypLang(), helperBp.getKoo_x(),
						helperBp.getKoo_y(), distToNext);

				bpResultRows.add(tmpBp);
			}
			helper = (BpHelper) shortestPath.get(shortestPath.size() - 1);
			helperBp = helper.getBp();
			bpResultRows.add(new ResultRow(helperBp.getId_betriebspunkt(),
					helperBp.getBezeichnung(), helperBp.getAbkuerzung(),
					helperBp.getBetriebspunkt_typ(), helper.getBpTypKurz(),
					helper.getBpTypLang(), helperBp.getKoo_x(), helperBp
							.getKoo_y(), 0L));
			MainFrame.refreshResultPane(bpResultRows, greenBpList);
			getShortestDistanzToStart();
			getRuntime();

		}
		return bpResultRows;
	}

	// Aufruf der 'work'-Methode im Dijkstra
	public void getWork(long start_id, long ziel_id, int modus) {
		startHelper = helperMap.get(start_id);
		zielHelper = helperMap.get(ziel_id);

		if (start_id != ziel_id) {

			Dijkstra dijkstra = new Dijkstra(helperMap);
			greenBpList = dijkstra.work(startHelper, zielHelper, modus,
					helperMap);
			shortestPath = dijkstra.getShortestPath(startHelper, zielHelper);
			runTime = ((float) dijkstra.getLaufzeit() / 1000000);
			getResult(shortestPath);
			LOG.info("Laufzeit: " + dijkstra.getLaufzeit());

			// Falls Ausgangsbahnhof = Zielbahnhof, Algorithmus nicht starten
			// und User eine MessageBox anzeigen.
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

	// Rückgabe der Laufzeit
	public void getRuntime() {
		MainFrame.showRuntime(runTime);
		LOG.info("Laufzeit in Methode getRuntime " + runTime);

	}

	public List<BpHelper> getBesuchteBp() {
		return greenBpList;
	}

}
