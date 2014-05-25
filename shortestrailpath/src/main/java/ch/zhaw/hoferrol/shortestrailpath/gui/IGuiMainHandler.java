package ch.zhaw.hoferrol.shortestrailpath.gui;

import java.util.List;

import ch.zhaw.hoferrol.shortestrailpath.algorithm.BpHelper;

public interface IGuiMainHandler {

	/**
	 * Schnittstelle für GUI-Aufruf mit Definition von Uebergabeparametern.
	 * 
	 * @ Author: Roland Hofer 16.04.2014
	 **/

	// Abstrakte Methode zur Uebergabe der Betriebspunkte ins Gui
	// für die Auswahl des Start- bzw. Zielortes.
	public List<Row> getSortedHelperMap();

	public List<ResultRow> getResult(List<BpHelper> shortestPath);

}
