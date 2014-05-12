package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LegendView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7560220635817212974L;
	/**
	 * 
	 */

	private JFrame legendframe;
	private JPanel legendpanel;
	private JPanel bppanel;
	private JPanel startpanel;
	private JPanel zielpanel;
	private JPanel besuchtebppanel;
	private JPanel buttonpanel;

	private JLabel bpicon;
	private JLabel bp;
	private JLabel besbpicon;
	private JLabel besbp;
	private JLabel startbpicon;
	private JLabel startbp;
	private JLabel zielbpicon;
	private JLabel zielbp;
	private JLabel blank1;

	private JButton btnSchliessen;

	public LegendView() {

		legendframe = this;
		legendframe.setTitle("Legende zur grafischen Ausgabe");
		legendframe.setSize(320, 240);
		// legendframe.setLocation(200, 150);
		legendframe
				.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		createLegendFrame();
		// this.setVisible(true);

	}

	public void createLegendFrame() {

		legendpanel = new JPanel();
		this.add(legendpanel);
		legendpanel.setLayout(new BoxLayout(legendpanel, BoxLayout.Y_AXIS));
		bppanel = new JPanel();
		bppanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		startpanel = new JPanel();
		startpanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		zielpanel = new JPanel();
		zielpanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		besuchtebppanel = new JPanel();
		besuchtebppanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());

		bpicon = new JLabel();
		bpicon.setIcon(new ImageIcon(LegendView.class
				.getResource("/betriebspunkt.png")));
		bp = new JLabel("    Befahrener Betriebspunkt");

		startbpicon = new JLabel();
		startbpicon.setIcon(new ImageIcon(LegendView.class
				.getResource("/startpunkt_klein.png")));
		startbp = new JLabel("  Ausgangsbahnhof");

		zielbpicon = new JLabel();
		zielbpicon.setIcon(new ImageIcon(LegendView.class
				.getResource("/zielpunkt_klein.png")));
		zielbp = new JLabel(" Zielbahnhof");

		besbpicon = new JLabel();
		besbpicon.setIcon(new ImageIcon(LegendView.class
				.getResource("/bes_betriebspunkt.jpg")));
		besbp = new JLabel("     Durch Algorithmus besuchter Betriebspunkt");

		blank1 = new JLabel(" ");

		bppanel.add(bpicon);
		bppanel.add(bp);
		startpanel.add(startbpicon);
		startpanel.add(startbp);
		zielpanel.add(zielbpicon);
		zielpanel.add(zielbp);
		besuchtebppanel.add(besbpicon);
		besuchtebppanel.add(besbp);

		btnSchliessen = new JButton("Legende schliessen");
		buttonpanel.add(btnSchliessen);
		btnSchliessen.addActionListener(this);

		legendpanel.add(bppanel);
		legendpanel.add(startpanel);
		legendpanel.add(zielpanel);
		legendpanel.add(besuchtebppanel);
		legendpanel.add(blank1);
		legendpanel.add(buttonpanel);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent object) {
		legendframe.dispose();

	}

}
