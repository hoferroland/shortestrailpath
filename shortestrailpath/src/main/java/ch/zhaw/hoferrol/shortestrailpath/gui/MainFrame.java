package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.algorithm.Dijkstra;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BorderPoint;

/**
 * Klasse MainFrame - Hier wird der 'ShortestRailPath - Navigator' erstellt. Die
 * Klasse wird vom GuiMainHandler aufgerufen und stellt den Hauptteil des
 * UserInterface dar.
 * 
 * @author Roland Hofer, V1.8 - 18.05.2014
 * 
 */

public class MainFrame extends JFrame implements ActionListener {

	// Variabeln
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel contentPaneLeft;
	private static JPanel contentPaneRight;
	private static JPanel contentPaneLeftTop;
	private static JPanel contentPaneLeftStart;
	private static JPanel contentPaneLeftZiel;
	private static JPanel contentPaneLeftModus;
	private static JPanel contentPaneLeftButton;
	private static JPanel contentPaneLeftTime;
	private static JPanel contentPaneRightTop;
	private static JPanel contentPaneRightTable;
	private static JPanel contentPaneRightButton;
	private static JPanel contentPaneRightBlank;
	private static JMenuBar menuLeiste;
	private static JMenu menuDatei;
	private static JMenu menuInfo;
	private static JMenuItem itemSchliessen;
	private static JMenuItem itemInfo;
	private static JRadioButton dijkstraNormal;
	private static JRadioButton dijkstraOpt;
	private static JRadioButton dijkstraAStern;
	private static ButtonGroup radioAlgorithmusGroup = new ButtonGroup();
	private static Box box = Box.createVerticalBox();
	private static JRadioButton anzeigeWeg;
	private static JRadioButton anzeigeBearbeitet;
	private static ButtonGroup radioAnzeigeModus = new ButtonGroup();
	private static Box boxAnzeige = Box.createVerticalBox();
	private static JTextField runTime;
	private static JTextField distanzStart;
	private static JButton btnGo;
	private static JButton btnGrafik;
	List<Row> bhfRows = new ArrayList<Row>();
	List<BorderPoint> border = new ArrayList<BorderPoint>();
	private static JTable resultTable;
	private static JLabel blank;
	private static JScrollPane scroll;
	private static String[] resultTableColumnNames;
	private static Object[][] model;
	static String result[] = new String[] { null };
	private Row startRow = null;
	private Row zielRow = null;
	private int modusAlgorithmus;
	private int modusAnzeige;
	private JComboBox<Row> cbo_startHelper = new JComboBox<Row>();
	private JComboBox<Row> cbo_zielHelper = new JComboBox<Row>();
	private static final String LOGO_FILE_NAME = "/Logo.jpg";

	/**
	 * Konstruktor
	 */
	public MainFrame(List<Row> bhfRows, List<BpHelper> shortestPath,
			List<BorderPoint> border) {

		this.bhfRows = bhfRows;
		this.border = border;

		setTitle("ShortestRailPath - Navigator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);

		// Erstellen der Menüleiste
		menuLeiste = new JMenuBar();
		setJMenuBar(menuLeiste);
		menuDatei = new JMenu("Datei");
		menuInfo = new JMenu("Info");
		menuLeiste.add(menuDatei);
		menuLeiste.add(menuInfo);

		itemSchliessen = new JMenuItem("schliessen");
		menuDatei.add(itemSchliessen);
		itemSchliessen.addActionListener(this);
		itemInfo = new JMenuItem("Info über");
		menuInfo.add(itemInfo);
		itemInfo.addActionListener(this);

		// Erstellung der Panels
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new GridLayout(0, 2));
		contentPaneLeft = new JPanel();
		contentPaneLeft.setBorder(new EmptyBorder(5, 15, 20, 5));
		contentPaneLeft.setLayout(new BoxLayout(contentPaneLeft,
				BoxLayout.Y_AXIS));
		contentPaneRight = new JPanel();
		contentPane.add(contentPaneLeft);
		contentPane.add(contentPaneRight);

		contentPaneLeftTop = new JPanel();

		contentPaneLeftStart = new JPanel();
		contentPaneRightTop = new JPanel();
		contentPaneRightTable = new JPanel();
		contentPaneRightButton = new JPanel();
		contentPaneRightBlank = new JPanel();

		contentPaneLeftTop.setLayout(new GridLayout(3, 2));
		contentPaneLeftStart.setLayout(new GridLayout(3, 2));
		contentPaneLeftZiel = new JPanel();
		contentPaneLeftZiel.setLayout(new GridLayout(3, 2));
		contentPaneLeftModus = new JPanel();
		contentPaneLeftModus.setLayout(new GridLayout(3, 2));
		contentPaneLeftButton = new JPanel();
		contentPaneLeftButton.setLayout(new GridLayout(3, 2));
		contentPaneLeftTime = new JPanel();
		BorderLayout borderLayout = new BorderLayout();
		contentPaneLeftTime.setLayout(borderLayout);

		contentPaneLeft.add(contentPaneLeftTop);
		contentPaneLeft.add(contentPaneLeftStart);
		contentPaneLeft.add(contentPaneLeftZiel);
		contentPaneLeft.add(contentPaneLeftModus);
		contentPaneLeft.add(contentPaneLeftButton);
		contentPaneLeft.add(contentPaneLeftTime);

		contentPaneRightTop.setLayout(new FlowLayout());
		contentPaneRightTable.setLayout(new BorderLayout());
		contentPaneRightButton.setLayout(new BorderLayout());

		contentPaneRight.add(contentPaneRightTop);
		contentPaneRight.add(contentPaneRightTable);
		contentPaneRight.add(contentPaneRightButton);

		// Bezeichnungs- und Dropdownfeld 'Ausgangsbahnhof'
		JLabel lblStartHelper = new JLabel("Ausgangsbahnhof:");
		lblStartHelper.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStartHelper.setBounds(10, 47, 238, 21);
		contentPaneLeftStart.add(lblStartHelper);

		cbo_startHelper.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Row selectedItem = (Row) cbo_startHelper.getSelectedItem();
				startRow = selectedItem;
			}
		});
		cbo_startHelper.setBounds(10, 79, 238, 29);
		contentPaneLeftStart.add(cbo_startHelper);

		// Bezeichnungs- und Dropdownfeld 'Zielbahnhof'
		JLabel lblZielHelper = new JLabel("Zielbahnhof:");
		lblZielHelper.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblZielHelper.setBounds(10, 149, 238, 21);
		contentPaneLeftZiel.add(lblZielHelper);

		cbo_zielHelper.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Row selectedItem = (Row) cbo_zielHelper.getSelectedItem();
				zielRow = selectedItem;
			}
		});
		cbo_zielHelper.setBounds(10, 181, 238, 29);
		contentPaneLeftZiel.add(cbo_zielHelper);

		// Option zur Berechnung des kürzesten Weges
		// Optionsfelder erzeugen
		dijkstraNormal = new JRadioButton("Dijkstra-Classic", true);
		dijkstraOpt = new JRadioButton("Dijkstra-Optimiert");
		dijkstraAStern = new JRadioButton("Dijkstra mit A* kombiniert");

		radioAlgorithmusGroup.add(dijkstraNormal);
		radioAlgorithmusGroup.add(dijkstraOpt);
		radioAlgorithmusGroup.add(dijkstraAStern);

		box.add(Box.createVerticalGlue());
		box.add(dijkstraNormal);
		box.add(dijkstraOpt);
		box.add(dijkstraAStern);
		box.add(Box.createVerticalGlue());

		contentPaneLeftModus.add(box);

		// Feld zur Anzeige der Laufzeit
		runTime = new JTextField();
		runTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		runTime.setEditable(false);
		contentPaneLeftTime.add(runTime, BorderLayout.WEST);
		runTime.setVisible(true);
		runTime.setBorder(null);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(321, 11, 1, 410);
		contentPaneLeftModus.add(verticalStrut);

		// Trennlinie oberhalb des Laufzeitfeldes
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(310, 11, 1, 376);
		contentPaneLeftTime.add(separator, BorderLayout.NORTH);

		// Trennlinie unterhalb des Laufzeitfeldes
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 299, 300, 2);
		contentPaneLeftTime.add(separator_1, BorderLayout.SOUTH);

		// Button um Suche zu starten
		btnGo = new JButton("Suche starten");
		btnGo.setBounds(134, 247, 114, 23);
		contentPaneLeftButton.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GuiMainHandler handler = new GuiMainHandler();
				if (dijkstraNormal.isSelected()) {
					modusAlgorithmus = Dijkstra.MODUS_CLASSIC;
				}
				if (dijkstraOpt.isSelected()) {
					modusAlgorithmus = Dijkstra.MODUS_OPTIMIERT;
				}
				if (dijkstraAStern.isSelected()) {
					modusAlgorithmus = Dijkstra.MODUS_ASTERN;
				}
				handler.getWork(startRow.getId(), zielRow.getId(),
						modusAlgorithmus);
			}
		});

		// Feld um Gesamtdistanz des gefundenen Weges anzuzeigen
		distanzStart = new JTextField();
		distanzStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		distanzStart.setEditable(false);
		contentPaneRightTop.add(distanzStart);
		distanzStart.setVisible(true);
		distanzStart.setBorder(null);

		// Anzeige der Abfolge von Betriebspunkten des gefundenen Weges in
		// einer Tabelle
		resultTable = new JTable(new DefaultTableModel(model, new Object[] {
				"Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz" }) {

			private static final long serialVersionUID = -1867719985967119185L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		resultTable.setBounds(366, 26, 280, 346);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scroll = new JScrollPane(resultTable);
		TableColumnAdjuster tca = new TableColumnAdjuster(resultTable);

		blank = new JLabel(" ");
		blank.setFont(new Font("Tahoma", Font.PLAIN, 86));
		contentPaneRightBlank.add(blank, BorderLayout.CENTER);
		contentPaneRightTable.add(contentPaneRightBlank, BorderLayout.CENTER);

		// Anzeige des Logo's (wenn noch keine Suchanfrage gestartet wurde)
		JLabel imageLabel = new JLabel("", new ImageIcon(getClass()
				.getResource(LOGO_FILE_NAME)), JLabel.CENTER);
		JPanel logoPanel = new JPanel(new BorderLayout());
		logoPanel.add(imageLabel, BorderLayout.SOUTH);
		contentPaneRightTable.add(logoPanel, BorderLayout.SOUTH);

		// Option zur grafischen Anzeige des kürzesten Weges
		anzeigeWeg = new JRadioButton("Nur Anzeige befahrene Betriebspunkte",
				true);
		anzeigeBearbeitet = new JRadioButton(
				"Anzeige 'besuchte' und befahrene Betriebspunkte");

		radioAnzeigeModus.add(anzeigeWeg);
		radioAnzeigeModus.add(anzeigeBearbeitet);

		boxAnzeige.add(Box.createVerticalGlue());
		boxAnzeige.add(anzeigeWeg);
		boxAnzeige.add(anzeigeBearbeitet);
		boxAnzeige.add(Box.createVerticalGlue());

		contentPaneRightButton.add(boxAnzeige, BorderLayout.WEST);
		boxAnzeige.setVisible(false);

		// Button um grafische Anzeige zu öffnen
		btnGrafik = new JButton("Grafische Darstellung");
		btnGrafik.setBounds(366, 383, 165, 23);
		contentPaneRightButton.add(btnGrafik, BorderLayout.EAST);
		btnGrafik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (anzeigeWeg.isSelected()) {
					modusAnzeige = GrafikView.ANZEIGEMODUS_BEFAHREN;
				}
				if (anzeigeBearbeitet.isSelected()) {
					modusAnzeige = GrafikView.ANZEIGEMODUS_BEARBEITET;
				}

				GrafikView grafik = new GrafikView(GuiMainHandler.shortestPath,
						GuiMainHandler.border, GuiMainHandler.greenBpList,
						modusAnzeige);
			}
		});
		btnGrafik.setVisible(false);

		// Zuweisung der selektierten Bahnhöfe an die Variabeln für
		// den start- und ziel Helper
		for (Row bhf : bhfRows) {
			cbo_zielHelper.addItem(bhf);
			cbo_startHelper.addItem(bhf);
		}

	}

	// Methode um Tabelle mit Suchresultat zu befüllen
	public static void refreshResultPane(List<ResultRow> bpResultRows,
			List<BpHelper> greenBpList) {

		DefaultTableModel model = null;
		model = (DefaultTableModel) resultTable.getModel();
		// zuerst Tabelleninhalt löschen (falls 'alte' Abfrageresultate
		// drinstehen
		model.setRowCount(0);
		for (ResultRow bp : bpResultRows) {
			model.addRow(new Object[] { (bp.getBpAbk()), bp.getBpName(),
					bp.getBpTypKurz(), bp.getDistToNext() });
		}

		resultTable.setModel(model);
		contentPaneRightTable.removeAll();
		contentPaneRightTable.add(scroll);
		scroll.setVisible(true);

		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(resultTable);

		resultTableColumnNames = new String[] { "Bp-Abk.", " Bp-Bezeichnung",
				" Bp-Typ", "Distanz" };

		boxAnzeige.setVisible(true);
		btnGrafik.setVisible(true);

	}

	// Methode um Gesamtdistanz anzuzeigen
	public static void showDistanzStart(String gesamtDistanz) {
		distanzStart.setText("Die Länge des kürzesten Weges beträgt: "
				+ gesamtDistanz + " Kilometer.");
		distanzStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPaneRightTop.add(distanzStart);
		distanzStart.setEditable(false);
		distanzStart.setVisible(true);
		contentPane.revalidate();

	}

	// Methode um Laufzeit anzuzeigen
	public static void showRuntime(float getRunTime) {
		runTime.setText("Die Laufzeit der Wegsuche betrug: "
				+ (String.valueOf(getRunTime)) + " Millisekunden.");
		runTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPaneLeftTime.add(runTime, BorderLayout.WEST);
		runTime.setEditable(false);
		runTime.setVisible(true);
		contentPane.revalidate();

	}

	@Override
	public void actionPerformed(ActionEvent object) {
		if (object.getSource() == itemInfo) {
			InfoView info = new InfoView();
		}
		if (object.getSource() == itemSchliessen) {
			System.exit(0);
		}

	}

}
