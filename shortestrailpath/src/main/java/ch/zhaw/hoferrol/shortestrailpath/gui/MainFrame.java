package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;

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
	private static JLabel resultDistanz;
	List<Row> bhfRows = new ArrayList<Row>();
	private static JTable resultTable;
	private static String[] resultTableColumnNames;
	private static Object[][] model;
	static String result[] = new String[] { null };
	private Row startRow = null;
	private Row zielRow = null;
	private JComboBox<Row> cbo_startHelper = new JComboBox<Row>();
	private JComboBox<Row> cbo_zielHelper = new JComboBox<Row>();

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
	public MainFrame(List<Row> bhfRows, List<BpHelper> shortestPath) {
		// public MainFrame(List<Row> bhfRows) {

		this.bhfRows = bhfRows;
		// this.shortestPath = shortestPath;

		setTitle("shortestRailPath - Navigator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 540);

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
		// BoxLayout boxLayoutTop = new BoxLayout(contentPaneLeftTop,
		// BoxLayout.Y_AXIS);
		// BoxLayout boxLayoutStart = new BoxLayout(contentPaneLeftStart,
		// BoxLayout.Y_AXIS);
		// BoxLayout boxLayoutZiel = new BoxLayout(contentPaneLeftZiel,
		// BoxLayout.Y_AXIS);

		// contentPaneLeftTop.setLayout(new BoxLayout(contentPaneLeftTop,
		// BoxLayout.Y_AXIS));
		// contentPaneLeftStart.setLayout(new BoxLayout(contentPaneLeftStart,
		// BoxLayout.X_AXIS));
		// contentPaneLeftZiel = new JPanel();
		// contentPaneLeftZiel.setLayout(new BoxLayout(contentPaneLeftZiel,
		// BoxLayout.X_AXIS));
		// contentPaneLeftModus = new JPanel();
		// contentPaneLeftModus.setLayout(new BoxLayout(contentPaneLeftModus,
		// BoxLayout.X_AXIS));
		// contentPaneLeftButton = new JPanel();
		// contentPaneLeftButton.setLayout(new BoxLayout(contentPaneLeftButton,
		// BoxLayout.X_AXIS));

		contentPaneLeftTop.setLayout(new GridLayout(3, 2));
		contentPaneLeftStart.setLayout(new GridLayout(3, 2));
		contentPaneLeftZiel = new JPanel();
		contentPaneLeftZiel.setLayout(new GridLayout(3, 2));
		contentPaneLeftModus = new JPanel();
		contentPaneLeftModus.setLayout(new GridLayout(3, 2));
		contentPaneLeftButton = new JPanel();
		contentPaneLeftButton.setLayout(new GridLayout(3, 2));

		contentPaneLeft.add(contentPaneLeftTop);
		contentPaneLeft.add(contentPaneLeftStart);
		contentPaneLeft.add(contentPaneLeftZiel);
		contentPaneLeft.add(contentPaneLeftModus);
		contentPaneLeft.add(contentPaneLeftButton);

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

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(321, 11, 1, 410);
		contentPaneLeft.add(verticalStrut);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(310, 11, 1, 376);
		contentPaneLeft.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 299, 300, 2);
		contentPaneLeft.add(separator_1);

		JButton btnGo = new JButton("Suche starten");
		btnGo.setBounds(134, 247, 114, 23);
		contentPaneLeftButton.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GuiMainHandler handler = new GuiMainHandler();
				handler.getWork(startRow.getId(), zielRow.getId());
			}
		});

		// resultTable = new JTable();
		// Object o[] = new Object[] { "Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz"
		// };
		// resultTable.setModel(new DefaultTableModel());
		//
		// resultTable.setBounds(366, 26, 408, 346);
		// contentPane.add(new JScrollPane(resultTable));

		resultTable = new JTable(new DefaultTableModel(model, new Object[] {
				"Bp-Abk.", "Bp-Name", "Bp-Typ", "Distanz" }));
		resultTable.setBounds(366, 26, 300, 346);
		// resultTable.setBounds(366, 26, 408, 346);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scroll = new JScrollPane(resultTable);
		TableColumnAdjuster tca = new TableColumnAdjuster(resultTable);
		contentPaneRight.add(scroll);
		/* PW contentPane.add(resultTable); */

		JButton btnGrafik = new JButton("Grafische Darstellung");
		btnGrafik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btnGrafik.setBounds(366, 383, 165, 23);
		contentPaneRight.add(btnGrafik);
		btnGrafik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GrafikView grafik = new GrafikView(GuiMainHandler.shortestPath);
				// grafik.karte = grafik.drawPanel.getGraphics();
				// grafik.setVisible(true);
				System.out.println("Grösse shortestPath bei Button Grafik: "
						+ GuiMainHandler.shortestPath.size());
			}
		});
		System.out.println("Grösse shortestPath VOR Klick auf Button: "
				+ shortestPath.size());

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

	public static void refreshResultPane(List<ResultRow> bpResultRows) {

		for (ResultRow bp : bpResultRows) {
			result = (new String[] { (bp.getBpAbk()), bp.getBpName(),
					bp.getBpTyp_s(), bp.getDistToNext_s() });
		}

		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
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

		// resultTable = new JTable(new DefaultTableModel());

		// model.addRow(new Object[])

		// Object[][] tableResultDaten = null;
		// tableResultDaten = {
		// for (ResultRow bp : bpResultRows) {
		// {(bp.getBpName()), (bp.getBpAbk()), (bp.getBpTyp())}
		// };
		// }

		// txtPaneResult
		// .setText("Der Weg von Start nach Ziel führt über folgende Betriebspunkte:");
		// String inh = "";
		// for (ResultRow bp : bpResultRows) {
		// inh = inh + (bp.getBpName()) + "   Abk: " + (bp.getBpAbk()) + "\n";
		// }
		// txtPaneResult.setText(inh);
		// txtPaneResult.i

		// }
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
