package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BorderPoint;

/**
 * Klasse GrafikView - Ist zuständig für die grafische Anzeige des
 * Suchresultates und wird über einen Button im Mainframe aufgerufen. Dabei wird
 * ein Anzeigemodus übergeben, welcher steuert, ob nur die gefundene Verbindung
 * oder auch alle durch den Algorithmus besuchten Betriebspunkte geplottet
 * werden sollen.
 * 
 * @author Roland Hofer, V1.0 - 10.05.2014
 * 
 */

public class GrafikView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4657107180500861400L;
	// Variabeln
	private static final Logger LOG = Logger.getLogger(GrafikView.class);
	private JPanel myPanel, buttonPanel;
	private JPanel drawPanel;
	private JButton legendeButton;
	private JButton schliessenButton;
	// private Graphics karte;
	private int[] grafikSize = new int[4];
	private List<BpHelper> shortestPath = new ArrayList<BpHelper>();
	private List<BpHelper> greenBpList = new ArrayList<BpHelper>();
	private List<BorderPoint> border = new ArrayList<BorderPoint>();
	private int maxI;
	private int minI;
	private int maxJ;
	private int minJ;
	int anzeigeModus;
	public static final int ANZEIGEMODUS_BEFAHREN = 0;
	public static final int ANZEIGEMODUS_BEARBEITET = 1;

	// Konstruktor
	public GrafikView(List<BpHelper> shortestPath, List<BorderPoint> border,
			List<BpHelper> greenBpList, int anzeigeModus) {
		super("ShortestRailPath - Grafische Ausgabe des Suchresultates");
		this.shortestPath = shortestPath;
		this.border = border;
		this.greenBpList = greenBpList;
		this.anzeigeModus = anzeigeModus;
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);

		createGrafikFrame();

	}

	// Methode um das Fenster zusammen zu bauen
	public void createGrafikFrame() {

		// Festlegen der Zeichenkoordinaten
		maxI = (this.getWidth() - 25);
		minI = 5;
		maxJ = (this.getHeight() - 90);
		minJ = 5;
		grafikSize[0] = maxI;
		grafikSize[1] = minI;
		grafikSize[2] = maxJ;
		grafikSize[3] = minJ;

		myPanel = new JPanel();
		myPanel.setLayout(new FlowLayout());

		legendeButton = new JButton("Legende anzeigen");
		legendeButton.addActionListener(this);
		schliessenButton = new JButton("Grafik schliessen");
		schliessenButton.addActionListener(this);
		setContentPane(myPanel);
		drawPanel = new GPanel();
		buttonPanel = new JPanel();

		drawPanel.setPreferredSize(new Dimension(maxI, maxJ));
		drawPanel.setBackground(Color.WHITE);
		buttonPanel.setPreferredSize(new Dimension(maxI, 35));
		myPanel.add(drawPanel);
		myPanel.add(buttonPanel);
		buttonPanel.add(legendeButton);
		buttonPanel.add(schliessenButton);

		this.setVisible(true);

		// Aufruf des GrafikViewHandlers um die effektiven Koordinaten in
		// Bildschirmkoordinaten umzurechnen
		GrafikViewHandler grafikHandler = new GrafikViewHandler(shortestPath,
				greenBpList, border, grafikSize);
	}

	class GPanel extends JPanel {

		private static final long serialVersionUID = 5967727182155656352L;

		public GPanel() {
			setPreferredSize(new Dimension(maxI, maxJ));
			this.setBackground(Color.WHITE);
		}

		// Methode um die Karte zu plotten
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponents(g);

			// Bild für Startpunkt laden
			BpHelper helper = null;
			ClassLoader clStart = this.getClass().getClassLoader();
			URL urlStart = clStart.getResource("startpunkt_klein.png");
			Image start = null;
			try {
				start = ImageIO.read(urlStart);
			} catch (IOException eStart) {
				// Auto-generated catch block
				eStart.printStackTrace();
			}

			// Bild für Zielpunkt laden
			ClassLoader clZiel = this.getClass().getClassLoader();
			URL urlZiel = clZiel.getResource("zielpunkt_klein.png");
			Image ziel = null;
			try {
				ziel = ImageIO.read(urlZiel);
			} catch (IOException eZiel) {
				// Auto-generated catch block
				eZiel.printStackTrace();
			}

			// Hintergrundfarbe für die Bilder 'start' und 'ziel' transparent
			Color bgc = new Color(0f, 0f, 0f, .01f);

			// Schweizerkarte mit den Gemeindegrenzen zeichnen
			BorderPoint bpoint = null;

			// Schlaufe für das Zeichnen der Schweizer-Karte
			for (int iborder = 0; iborder < border.size(); iborder++) {
				bpoint = (BorderPoint) border.get(iborder);
				float ixKoo = (bpoint.getKooI() + 0.5f);
				float jyKoo = (bpoint.getKooJ() + 0.5f);

				g.setColor(new Color(182, 182, 182));
				g.drawOval((int) ixKoo, (int) jyKoo, 1, 1);

			}

			// Schleife für das Zeichnen der besuchten Betriebspunkte
			if (anzeigeModus == ANZEIGEMODUS_BEARBEITET) {
				for (int i = 0; i < greenBpList.size(); i++) {
					helper = (BpHelper) greenBpList.get(i);
					float ixKoo = (helper.getIKoo() + 0.5f);
					float jyKoo = (helper.getJKoo() + 0.5f);

					g.setColor(Color.GREEN);
					g.drawRect((int) ixKoo, (int) jyKoo, 3, 3);
				}
			}

			// Schleife für das Zeichnen der gefundenen Verbindung
			for (int i = 0; i < shortestPath.size(); i++) {
				helper = (BpHelper) shortestPath.get(i);
				LOG.debug("Ausgabe Helper: " + helper.getBp().getBezeichnung()
						+ ", " + helper.getIKoo() + ", " + helper.getJKoo());
				float ixKoo = (helper.getIKoo() + 0.5f);
				float jyKoo = (helper.getJKoo() + 0.5f);

				LOG.debug("Ausgabe Helper ix und jy "
						+ helper.getBp().getBezeichnung() + ", " + (int) ixKoo
						+ ", " + (int) jyKoo);

				g.setColor(Color.RED);
				g.drawOval((int) ixKoo, (int) jyKoo, 4, 4);
				g.fillOval((int) ixKoo, (int) jyKoo, 4, 4);

				// Startpunkt mit Namen beschriften sowie Bild setzen
				if (i == 0) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("default", Font.BOLD, 12));
					g.drawString(helper.getBp().getBezeichnung(),
							(int) (helper.getIKoo() - 3),
							(int) (helper.getJKoo() - 10));
					g.drawImage(start, (int) helper.getIKoo(),
							(int) helper.getJKoo(), bgc, this);
				}
				// Zielpunkt mit Namen beschriften sowie Bild setzen
				if (i == (shortestPath.size() - 1)) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("default", Font.BOLD, 12));
					g.drawString(helper.getBp().getBezeichnung(),
							(int) (helper.getIKoo() - 3),
							(int) (helper.getJKoo() - 5));
					g.drawImage(ziel, (int) (helper.getIKoo() - 30),
							(int) (helper.getJKoo() - 31), bgc, this);
				}
			}

		}

		public int[] getGrafikPanelSize() {
			return grafikSize;
		}
	}

	// ActionListener-Methode
	@Override
	public void actionPerformed(ActionEvent object) {
		if (object.getSource() == legendeButton) {
			LegendView legende = new LegendView();
		}
		if (object.getSource() == schliessenButton) {
			this.dispose();
		}
	}

}
