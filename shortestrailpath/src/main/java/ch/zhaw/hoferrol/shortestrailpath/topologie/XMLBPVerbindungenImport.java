package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class XMLBPVerbindungenImport {

	private List<BetriebspunktVerbindungen> bpVerbList = new ArrayList<BetriebspunktVerbindungen>();
	// private static final String BP_VERB_FILE_NAME =
	// "betriebspunktverbindungen.xml";

	private static final Logger LOG = Logger
			.getLogger(XMLBPVerbindungenImport.class);

	private final URL importVerbUrl;

	public XMLBPVerbindungenImport(URL url) {
		LOG.info("BP-Verbindungen-Import initialisiert mit URL " + url);
		importVerbUrl = url;
	}

	// public static void main(String[] args) {

	public List<BetriebspunktVerbindungen> xmlBpVerbindungImport(
			Map<Long, Betriebspunkt> hashBp) {

		Document doc = null;
		List<Element> alleKinder;

		// String fileName = System.getProperty("bpFile");
		// File f = new File("d:\\uno_betriebspunkt.xml");
		// File f = new File("betriebspunktverbindungen.xml");

		// List<BetriebspunktVerbindungen> bpVerbList = new
		// ArrayList<BetriebspunktVerbindungen>();
		//
		// File f = new File(BP_VERB_FILE_NAME);
		//
		// if (!f.canRead()) {
		// throw new IllegalArgumentException(
		// "datei "
		// + BP_VERB_FILE_NAME
		// +
		// " zum einlesen der betriebspunkt verbindungen wurde nicht gefunden!");
		// }

		try {
			// Das Dokument erstellen
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(importVerbUrl);
			XMLOutputter fmt = new XMLOutputter();

			// komplettes Dokument ausgeben
			// fmt.output(doc, System.out);

			// Wurzelelement ausgeben
			Element element = doc.getRootElement();
			// System.out.println("\nWurzelelement: " + element);

			// Wurzelelementnamen ausgeben
			// System.out.println("Wurzelelementname: " + element.getName());

			// Eine Liste aller direkten Kindelemente eines Elementes erstellen
			alleKinder = element.getChildren();
			// System.out.println("Erstes Kindelement: "
			// + ((Element) alleKinder.get(0)).getName());
			// System.out.println("Liste aller Kinder: " + alleKinder.size());

			// Attribut ausgeben
			Element kind = element.getChild("ROW");
			List<Element> columns = kind.getChildren();
			// for (Element col : columns) {
			// System.out.println(col.getAttributeValue("NAME") + ": "
			// + col.getValue());

			// }
			// Iteration über alle Attribute
			int i;
			for (i = 0; i < alleKinder.size(); i++) {
				Element kind2 = ((Element) alleKinder.get(i));
				List<Element> columns2 = kind2.getChildren();
				for (Element col2 : columns2) {
					LOG.debug(col2.getAttributeValue("NAME") + ": "
							+ col2.getValue());
				}
				Long bpVon = Long.valueOf(columns2.get(1).getText());
				Long bpBis = Long.valueOf(columns2.get(2).getText());
				Betriebspunkt bpStart = hashBp.get(bpVon);
				Betriebspunkt bpZiel = hashBp.get(bpBis);
				Long distanz = Long.valueOf(columns2.get(3).getText());
				int anzGl = Integer.parseInt(columns2.get(4).getText());
				Long id = Long.valueOf(columns2.get(0).getText());
				// .getAttributeValue("ID_BETRIEBSPUNKT"));
				BetriebspunktVerbindungen bpVerb = new BetriebspunktVerbindungen(
						id, bpVon, bpBis, distanz, anzGl, bpStart, bpZiel);
				// Konsolenausgbe zu Debugzwecken
				// System.out.println(id + ", " + bpVon + ", " + bpBis + ", "
				// + distanz + ", " + anzGl);
				bpVerbList.add(bpVerb);

				//
				LOG.debug("size: " + bpVerbList.size());
				LOG.debug("----------");
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bpVerbList;

	}

	// public List<BetriebspunktVerbindungen> getBpVerbList() {
	// return bpVerbList;
	//
	// }

}
