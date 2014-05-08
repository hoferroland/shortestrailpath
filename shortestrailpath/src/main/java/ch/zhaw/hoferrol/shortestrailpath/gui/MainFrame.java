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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class MainFrame extends JFrame { // implements ActionListener,
										// ItemListener {

	/**
	 * 
	 */
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
	private static JLabel resultDistanz;
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
	private static JTable resultTable;
	private static String[] resultTableColumnNames;
	private static Object[][] model;
	static String result[] = new String[] { null };
	private Row startRow = null;
	private Row zielRow = null;
	private int modusAlgorithmus;
	private int modusAnzeige;
	private List<ResultRow> bpResultRows = new ArrayList<ResultRow>();
	private JComboBox<Row> cbo_startHelper = new JComboBox<Row>();
	private JComboBox<Row> cbo_zielHelper = new JComboBox<Row>();

	// private List<BpHelper> greenBpList = new ArrayList<BpHelper>();

	// private List<BpHelper> shortestPath = new ArrayList<BpHelper>();

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// MainFrame frame = new MainFrame();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public MainFrame(List<Row> bhfRows, List<BpHelper> shortestPath,
			List<BorderPoint> border) {
		// public MainFrame(List<Row> bhfRows) {

		this.bhfRows = bhfRows;
		// this.shortestPath = shortestPath;

		setTitle("shortestRailPath - Navigator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmDatei = new JMenuItem("Datei");
		menuBar.add(mntmDatei);

		JMenuItem mntmGrafik = new JMenuItem("Grafik");
		menuBar.add(mntmGrafik);

		JMenuItem mntmInfo = new JMenuItem("Info");
		menuBar.add(mntmInfo);
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
		contentPaneRightTable.setLayout(new GridLayout(1, 1));
		contentPaneRightButton.setLayout(new BorderLayout());

		contentPaneRight.add(contentPaneRightTop);
		contentPaneRight.add(contentPaneRightTable);
		contentPaneRight.add(contentPaneRightButton);

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
		// {
		// public void actionPerformed(ActionEvent ae) {
		// Row selectedItem = (Row) cbo_startHelper.getSelectedItem();
		// startRow = selectedItem;
		// // System.out.println("StartHelper = " + startRow.getId());
		//
		// }

		// @Override
		// public void itemStateChanged(ItemEvent arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// cbo_startHelper.addActionListener(actionListener.itemStateChanged(cbo_startHelper);

		// action listener
		// cbo_startHelper.addActionListener(actionListener);

		cbo_startHelper.setBounds(10, 79, 238, 29);
		contentPaneLeftStart.add(cbo_startHelper);

		JLabel lblZielHelper = new JLabel("Zielbahnhof:");
		lblZielHelper.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblZielHelper.setBounds(10, 149, 238, 21);
		contentPaneLeftZiel.add(lblZielHelper);

		cbo_zielHelper.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Row selectedItem = (Row) cbo_zielHelper.getSelectedItem();
				zielRow = selectedItem;
				// System.out.println("ZielHelper = " + zielRow.getId());

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

		runTime = new JTextField();
		runTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		runTime.setEditable(false);
		contentPaneLeftTime.add(runTime, BorderLayout.WEST);
		runTime.setVisible(true);
		runTime.setBorder(null);

		// Listeners Radio Buttons
		// dijkstraNormal.addItemListener(norm);
		// dijkstraOpt.addItemListener(opt);
		// dijkstraAStern.addItemListener(stern);

		// Beschriftungsfeld für aktiviertes Kontrollkäs

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(321, 11, 1, 410);
		contentPaneLeft.add(verticalStrut);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(310, 11, 1, 376);
		contentPaneLeftTime.add(separator, BorderLayout.NORTH);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 299, 300, 2);
		contentPaneLeftTime.add(separator_1, BorderLayout.SOUTH);

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

		distanzStart = new JTextField();
		distanzStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		distanzStart.setEditable(false);
		contentPaneRightTop.add(distanzStart);
		distanzStart.setVisible(true);
		distanzStart.setBorder(null);

		// resultTable = new JTable();
		// Object o[] = new Object[] { "Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz"
		// };
		// resultTable.setModel(new DefaultTableModel());
		//
		// resultTable.setBounds(366, 26, 408, 346);
		// contentPane.add(new JScrollPane(resultTable));

		resultTable = new JTable(new DefaultTableModel(model, new Object[] {
				"Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		resultTable.setBounds(366, 26, 280, 346);
		// resultTable.setBounds(366, 26, 408, 346);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scroll = new JScrollPane(resultTable);
		TableColumnAdjuster tca = new TableColumnAdjuster(resultTable);
		contentPaneRightTable.add(scroll);
		/* PW contentPane.add(resultTable); */

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

		// JButton btnGrafik = new JButton("Grafische Darstellung");
		// btnGrafik.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// }
		// });

		btnGrafik = new JButton("Grafische Darstellung");
		btnGrafik.setBounds(366, 383, 165, 23);
		contentPaneRightButton.add(btnGrafik, BorderLayout.EAST);
		btnGrafik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// GrafikView grafik = new
				// GrafikView(GuiMainHandler.shortestPath,
				// GuiMainHandler.border);
				if (anzeigeWeg.isSelected()) {
					modusAnzeige = GrafikView.ANZEIGEMODUS_BEFAHREN;
				}
				if (anzeigeBearbeitet.isSelected()) {
					modusAnzeige = GrafikView.ANZEIGEMODUS_BEARBEITET;
				}

				GrafikView grafik = new GrafikView(GuiMainHandler.shortestPath,
						GuiMainHandler.border, GuiMainHandler.greenBpList,
						modusAnzeige);
				// grafik.karte = grafik.drawPanel.getGraphics();
				// grafik.setVisible(true);

			}
		});
		btnGrafik.setVisible(false);

		// data
		for (Row bhf : bhfRows) {
			cbo_zielHelper.addItem(bhf);
			cbo_startHelper.addItem(bhf);
		}

	}

	public static void getResult(List<ResultRow> bpResultRows) {

		// resultTableColumnNames = new String[] { "Bp-Abk.", " Bp-Bezeichnung",
		// " Bp-Typ", "Distanz" };

		// DefaultTableModel model = (DefaultTableModel) resultTable.getModel();

	}

	public static void refreshResultPane(List<ResultRow> bpResultRows,
			List<BpHelper> greenBpList) {
		bpResultRows = bpResultRows;
		// greenBpList = greenBpList;
		System.out.println("Grösse greenBpList in MainFrame = "
				+ greenBpList.size());
		// for (ResultRow bp : bpResultRows) {
		// result = (new String[] { (bp.getBpAbk()), bp.getBpName(),
		// bp.getBpTyp_s(), bp.getDistToNext_s() });
		// }
		DefaultTableModel model = null;
		model = (DefaultTableModel) resultTable.getModel();
		// DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		// zuerst Tabelleninhalt löschen (falls 'alte' Abfrageresultate
		// drinstehen
		model.setRowCount(0);
		for (ResultRow bp : bpResultRows) {
			model.addRow(new Object[] { (bp.getBpAbk()), bp.getBpName(),
					bp.getBpTyp(), bp.getDistToNext() });
		}

		resultTable.setModel(model);

		/*
		 * resultTable = new JTable(new DefaultTableModel(model, new Object[] {
		 * "Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz" })); // JScrollPane scroll
		 * = new JScrollPane(resultTable); // resultTable.setBounds(366, 26,
		 * 408, // 346);
		 */
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(resultTable);
		// contentPane.add(resultTable);

		resultTableColumnNames = new String[] { "Bp-Abk.", " Bp-Bezeichnung",
				" Bp-Typ", "Distanz" };

		boxAnzeige.setVisible(true);
		btnGrafik.setVisible(true);

	}

	public static void showDistanzStart(String gesamtDistanz) {
		distanzStart.setText("Die Länge des kürzesten Weges beträgt: "
				+ gesamtDistanz + " Kilometer.");
		distanzStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPaneRightTop.add(distanzStart);
		distanzStart.setEditable(false);
		distanzStart.setVisible(true);
		contentPane.revalidate();

	}

	public static void showRuntime(float getRunTime) {
		runTime.setText("Die Laufzeit der Wegsuche betrug: "
				+ (String.valueOf(getRunTime)) + " Millisekunden.");
		runTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPaneLeftTime.add(runTime, BorderLayout.WEST);
		runTime.setEditable(false);
		runTime.setVisible(true);
		contentPane.revalidate();

	}
	// public void itemStateChanged(ItemEvent sc) {
	// if (sc.getSource() == this.cbo_startHelper) {
	// Row selectedItem = (Row) cbo_startHelper.getSelectedItem();
	// startRow = selectedItem;
	// } else if (sc.getSource() == this.cbo_zielHelper) {
	// Row selectedItem = (Row) cbo_zielHelper.getSelectedItem();
	// zielRow = selectedItem;
	// }
	// }

}
