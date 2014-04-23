package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class XMLBetriebspunkteImport {

	private static final Logger LOG = Logger
			.getLogger(XMLBetriebspunkteImport.class);

	private final URL importUrl;

	public XMLBetriebspunkteImport(URL url) {
		LOG.info("BP Import initialisiert mit URL " + url);
		importUrl = url;
	}

	// public static void main(String[] args) {
	public List<Betriebspunkt> xmlBpImport() {

		List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();

		Document doc = null;
		List<Element> alleKinder;

		// String fileName = System.getProperty("bpFile");
		// File f = new File("d:\\uno_betriebspunkt.xml");

		// File f = new File(DEFAULT_BP_FILE_NAME);
		// URL fileUrl = f.toURI().toURL();
		//
		// if (!f.canRead()) {
		// throw new IllegalArgumentException("datei " + DEFAULT_BP_FILE_NAME
		// + " zum einlesen der betriebspunkte wurde nicht gefunden!");
		// }
		//
		// URL url = this.getClass().getResource("/" + DEFAULT_BP_FILE_NAME);

		try {
			// Das Dokument erstellen
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(importUrl);
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
			Float xKoo = null;
			Float yKoo = null;
			int bpTyp;
			int uic = 80;
			for (i = 0; i < alleKinder.size(); i++) {
				Element kind2 = ((Element) alleKinder.get(i));
				List<Element> columns1 = kind2.getChildren();
				for (Element col : columns1) {
					LOG.debug(col.getAttributeValue("NAME") + ": "
							+ col.getValue());
				}
				String abkuerzung = columns1.get(2).getText();
				String name = columns1.get(3).getText();
				if ((columns1.get(1).getText()) != "") {
					bpTyp = Integer.parseInt(columns1.get(1).getText());
				} else {
					bpTyp = 9;
				}

				Long idBP = Long.valueOf(columns1.get(0).getText());
				if ((columns1.get(4).getText()) != "") {
					xKoo = Float.parseFloat(columns1.get(4).getText());
				} else {
					xKoo = 1.1f;
				}

				if ((columns1.get(5).getText()) != "") {
					yKoo = Float.parseFloat(columns1.get(5).getText());
				} else {
					yKoo = 1.1f;
				}
				if ((columns1.get(6).getText()) != "") {
					uic = Integer.parseInt(columns1.get(6).getText());
				} else {
					uic = 80;
				}

				// .getAttributeValue("ID_BETRIEBSPUNKT"));
				Betriebspunkt bp = new Betriebspunkt(abkuerzung, name, idBP,
						bpTyp, xKoo, yKoo, uic);
				bpList.add(bp);

				//
				LOG.debug("size: " + bpList.size());
				LOG.debug("----------");
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bpList;
	}

}
