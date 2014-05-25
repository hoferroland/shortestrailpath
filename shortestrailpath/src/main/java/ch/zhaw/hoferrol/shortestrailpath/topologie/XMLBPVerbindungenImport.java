package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse XMLBPVerbindungenImport liest das File betriebspunktverbindungen.xml bzw.
 * betriebspunktverbindungen_internal.xml (wird mit url aus Main-Klasse übergeben).
 * Das File wird durchiteriert und jede Spalte ausgelesen. 
 * Anschliessend wird für jedes Kind-Element eine neue BetriebspunktVerbindung
 * angelegt. Alle BetriebspunktVerbindungen werden in eine Liste aufgenommen.
 * 
 * @author Roland Hofer, V1.3 - 28.04.2014
 * 
 */

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

	private static final Logger LOG = Logger
			.getLogger(XMLBPVerbindungenImport.class);

	private final URL importVerbUrl;

	public XMLBPVerbindungenImport(URL url) {
		LOG.info("BP-Verbindungen-Import initialisiert mit URL " + url);
		importVerbUrl = url;
	}

	public List<BetriebspunktVerbindungen> xmlBpVerbindungImport(
			Map<Long, Betriebspunkt> hashBp) {

		Document doc = null;
		List<Element> alleKinder;

		try {
			// Das Dokument erstellen
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(importVerbUrl);
			XMLOutputter fmt = new XMLOutputter();

			// Wurzelelement ausgeben
			Element element = doc.getRootElement();

			// Eine Liste aller direkten Kindelemente eines Elementes erstellen
			alleKinder = element.getChildren();

			// Attribut ausgeben
			Element kind = element.getChild("ROW");
			List<Element> columns = kind.getChildren();

			int i;
			for (i = 0; i < alleKinder.size(); i++) {
				Element kind2 = ((Element) alleKinder.get(i));

				List<Element> columns2 = kind2.getChildren();
				for (Element col2 : columns2) {
					LOG.debug(col2.getAttributeValue("NAME") + ": "
							+ col2.getValue());
				}
				Long bpVon = Long.valueOf(columns2.get(1).getText());
				Betriebspunkt bpStart = hashBp.get(bpVon);

				Long bpBis = Long.valueOf(columns2.get(2).getText());
				Betriebspunkt bpZiel = hashBp.get(bpBis);

				Long distanz = Long.valueOf(columns2.get(3).getText());

				int anzGl = Integer.parseInt(columns2.get(4).getText());

				Long id = Long.valueOf(columns2.get(0).getText());

				BetriebspunktVerbindungen bpVerb = new BetriebspunktVerbindungen(
						id, bpVon, bpBis, distanz, anzGl, bpStart, bpZiel);

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

}
