package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse XMLBetriebspunkteImport liest das File betriebspunkte.xml bzw.
 * betriebspunkte_internal.xml (wird mit url aus Main-Klasse übergeben).
 * Das File wird durchiteriert und jede Spalte ausgelesen. 
 * Anschliessend wird für jedes Kind-Element ein neuer Betriebspunkt
 * angelegt. Alle Betriebspunkte werden in eine Liste aufgenommen.
 * 
 * @author Roland Hofer, V1.2 - 21.04.2014
 * 
 */

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

	public List<Betriebspunkt> xmlBpImport() {

		List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();

		Document doc = null;
		List<Element> alleKinder;

		try {
			// Das Dokument erstellen
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(importUrl);
			XMLOutputter fmt = new XMLOutputter();

			// Wurzelelement ausgeben
			Element element = doc.getRootElement();

			// Eine Liste aller direkten Kindelemente eines Elementes erstellen
			alleKinder = element.getChildren();

			// Attribut ausgeben
			Element kind = element.getChild("ROW");
			List<Element> columns = kind.getChildren();

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
