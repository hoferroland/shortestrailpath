package ch.zhaw.hoferrol.shortestrailpath.main;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.hoferrol.shortestrailpath.topologie.Betriebspunkt;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BetriebspunktVerbindungen;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.BpVerbKonverter;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBPVerbindungenImport;
import ch.zhaw.hoferrol.shortestrailpath.topologie.XMLBetriebspunkteImport;

public class Main {

	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();
	static List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLBetriebspunkteImport xmlBpImp = new XMLBetriebspunkteImport();
		xmlBpImp.xmlBpImport();
		XMLBPVerbindungenImport xmlBpVerbImp = new XMLBPVerbindungenImport();
		xmlBpVerbImp.xmlBpVerbindungImport();
		XMLBPVerbindungenImport.xmlBpVerbindungImport();
		XMLBetriebspunkteImport.xmlBpImport();
		bpList = xmlBpImp.getBpList();
		bpVerbList = xmlBpVerbImp.getBpVerbList();
		BpKonverter bpKonv = new BpKonverter();
		bpKonv.convertBP(bpList);
		BpVerbKonverter bpVerbKonv = new BpVerbKonverter();
		bpVerbKonv.convertBpVerb(bpVerbList);

	}

}
