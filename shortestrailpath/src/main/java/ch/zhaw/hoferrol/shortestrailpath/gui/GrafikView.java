package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;

public class GrafikView extends JFrame {

	private JFrame grafikframe;
	private JPanel myPanel, buttonPanel;
	private JPanel drawPanel;
	private JButton resetButton;
	private Graphics karte;
	private int[] grafikSize = new int[4];
	private List<BpHelper> shortestPath = new ArrayList<BpHelper>();
	private int maxI;
	private int minI;
	private int maxJ;
	private int minJ;

	public GrafikView(List<BpHelper> shortestPath) {
		super("ShortestRailPath - Grafische Ausgabe des Suchresultates");
		this.shortestPath = shortestPath;
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		// this.pack();

		createGrafikFrame();
		System.out.println("Grösse Shortestpath in GrafikView: "
				+ shortestPath.size());

	}

	public void createGrafikFrame() {

		// javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
		// javax.swing.JFrame grafikframe = new javax.swing.JFrame(
		// "ShortestRailPath - Grafische Ausgabe des Suchresultates");
		// PW grafikframe = new
		// JFrame("ShortestRailPath - Grafische Ausgabe des Suchresultates");
		// PW
		// grafikframe.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		// PW grafikframe.pack();

		// PW grafikframe.setSize(1200, 800);
		// PW maxI = (grafikframe.getWidth() - 25);
		maxI = (this.getWidth() - 25);
		minI = 5;
		// PW maxJ = (grafikframe.getHeight() - 55);
		maxJ = (this.getHeight() - 55);
		minJ = 5;
		grafikSize[0] = maxI;
		grafikSize[1] = minI;
		grafikSize[2] = maxJ;
		grafikSize[3] = minJ;

		System.out.println("Grösse shortestPath GrafikView = "
				+ shortestPath.size());

		myPanel = new JPanel();
		myPanel.setLayout(new FlowLayout());
		resetButton = new JButton("Sicht wiederherstellen");
		// resetButton.addActionListener();
		setContentPane(myPanel);
		drawPanel = new GPanel();
		buttonPanel = new JPanel();
		drawPanel.setPreferredSize(new Dimension(maxI, maxJ));
		drawPanel.setBackground(Color.WHITE);

		buttonPanel.setPreferredSize(new Dimension(maxI, 20));
		myPanel.add(drawPanel);
		myPanel.add(buttonPanel);
		buttonPanel.add(resetButton);
		// Graphics karte = drawPanel.getGraphics();
		// karte = drawPanel.getGraphics();

		// System.out.println("Ausgabe Kartenwert: " + karte);
		this.setVisible(true);

		GrafikViewHandler grafikHandler = new GrafikViewHandler(shortestPath,
				grafikSize);
		// drawResult(karte);
	}

	class GPanel extends JPanel {
		public GPanel() {
			setPreferredSize(new Dimension(1200, 800));
			this.setBackground(Color.WHITE);
		}

		// public void drawResult(Graphics karte) {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);

			BpHelper helper = null;
			Color cBp1 = new Color(255, 0, 0);
			Color cBp2 = new Color(0, 255, 0);

			for (int i = 0; i < shortestPath.size(); i++) {
				helper = (BpHelper) shortestPath.get(i);
				System.out.println("Ausgabe Helper: "
						+ helper.getBp().getBezeichnung() + ", "
						+ helper.getIKoo() + ", " + helper.getJKoo());
				float ixKoo = (helper.getIKoo() + 0.5f);
				float jyKoo = (helper.getJKoo() + 0.5f);

				System.out.println("Ausgabe Helper ix und jy "
						+ helper.getBp().getBezeichnung() + ", " + (int) ixKoo
						+ ", " + (int) jyKoo);
				System.out.println((int) ixKoo + ", " + (int) jyKoo + ", 3, 3");

				System.out.println(g);
				g.setColor(Color.RED);
				// g.drawOval((int) ixKoo + i * 10, (int) jyKoo + i * 10, 3, 3);
				g.drawOval((int) ixKoo, (int) jyKoo, 3, 3);

			}

			// g.setColor(cBp1);
			// g.drawOval(helper.getIKoo(), helper.getJKoo(), 3, 3);
		}

		public int[] getGrafikPanelSize() {
			return grafikSize;
		}
	}
}
