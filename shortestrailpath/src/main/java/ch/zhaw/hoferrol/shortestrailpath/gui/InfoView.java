package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class InfoView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3402851154685325234L;
	// private JFrame infoframe;
	private JPanel infopanel;
	private JPanel toptextpanel;
	private JPanel picturepanel;
	// private JPanel buttonpanel;
	private JLabel appName;
	private JLabel appProgrammierer;
	private JLabel appDatum;
	private JLabel topoEisenbahn;
	private JLabel topoGrenze;
	private Icon logo;
	private JLabel bildLabel;
	private JButton btnOk;
	private JFrame infoframe;
	private JSeparator separator1;
	private JLabel blank1;
	private JLabel blank2;
	private JLabel blank3;
	private JLabel blank4;
	private JSeparator separator2;

	public InfoView() {

		infoframe = this;
		infoframe.setTitle("Info über ShortestRailPath");
		infoframe.setSize(450, 500);
		infoframe.setLocation(200, 150);
		infoframe.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		createInfoFrame();
		// this.setVisible(true);

	}

	public void createInfoFrame() {

		infopanel = new JPanel();
		this.add(infopanel);
		BorderLayout borderLayout = new BorderLayout();
		infopanel.setLayout(borderLayout);
		toptextpanel = new JPanel();
		picturepanel = new JPanel();
		// buttonpanel = new JPanel();

		toptextpanel.setLayout(new BoxLayout(toptextpanel, BoxLayout.Y_AXIS));
		infopanel.add(toptextpanel, BorderLayout.NORTH);

		appName = new JLabel("ShortestsRailPath - Version 0.0.2");
		appName.setFont(new Font("Tahoma", Font.BOLD, 16));
		blank1 = new JLabel(" ");
		appProgrammierer = new JLabel("© by Roland Hofer - zhaw Zürich");
		appProgrammierer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		appDatum = new JLabel("Deployt am: 09.05.2014");
		appDatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blank2 = new JLabel(" ");
		separator1 = new JSeparator();
		separator1.setForeground(Color.GRAY);
		blank3 = new JLabel(" ");
		topoEisenbahn = new JLabel(
				"Topologiedaten von SBB-Informatik (UNO) bezogen");
		topoEisenbahn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topoGrenze = new JLabel(
				"Grenzpunkte von www.swisstopo.admin.ch bezogen");
		topoGrenze.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blank4 = new JLabel(" ");
		separator2 = new JSeparator();
		separator2.setForeground(Color.GRAY);

		toptextpanel.add(appName);
		toptextpanel.add(blank1);
		toptextpanel.add(appDatum);
		toptextpanel.add(appProgrammierer);
		toptextpanel.add(blank2);
		toptextpanel.add(separator1);
		toptextpanel.add(blank3);
		toptextpanel.add(topoEisenbahn);
		toptextpanel.add(topoGrenze);
		toptextpanel.add(blank4);
		toptextpanel.add(separator2);

		// JLabel label = new JLabel("New label");
		// label.setIcon(new ImageIcon(Startmaske.class
		// .getResource("/Pic/BigEvent.jpg")));

		// logo = new ImageIcon("/Logo.jpg");
		bildLabel = new JLabel();
		bildLabel
				.setIcon(new ImageIcon(InfoView.class.getResource("/Logo.jpg")));
		infopanel.add(bildLabel, BorderLayout.CENTER);

		btnOk = new JButton("Ok");
		infopanel.add(btnOk, BorderLayout.SOUTH);
		btnOk.addActionListener(this);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent object) {
		infoframe.dispose();

	}

}