package ch.zhaw.hoferrol.shortestrailpath.topologie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class XMLBetriebspunkteImport {

	static List<Betriebspunkt> bpList = new ArrayList<Betriebspunkt>();

	// public static void main(String[] args) {
	public static void xmlBpImport() {

		Document doc = null;
		List alleKinder;

		// String fileName = System.getProperty("bpFile");
		// File f = new File("d:\\uno_betriebspunkt.xml");
		File f = new File("betriebspunkte.xml");

		try {
			// Das Dokument erstellen
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(f);
			XMLOutputter fmt = new XMLOutputter();

			// komplettes Dokument ausgeben
			// fmt.output(doc, System.out);

			// Wurzelelement ausgeben
			Element element = doc.getRootElement();
			// System.out.println("\nWurzelelement: " + element);

			// Wurzelelementnamen ausgeben
			// System.out.println("Wurzelelementname: " + element.getName());

			// Eine Liste aller direkten Kindelemente eines Elementes erstellen
			alleKinder = (List) element.getChildren();
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
				List<Element> columns1 = kind2.getChildren();
				for (Element col : columns1) {
					System.out.println(col.getAttributeValue("NAME") + ": "
							+ col.getValue());
				}
				String abkuerzung = columns1.get(2).getText();
				String name = columns1.get(3).getText();

				Long idBP = Long.valueOf(columns1.get(0).getText());
				// .getAttributeValue("ID_BETRIEBSPUNKT"));
				Betriebspunkt bp = new Betriebspunkt(abkuerzung, name, idBP, 1,
						1.1f, 2.1f);
				bpList.add(bp);

				//
				System.out.println("size: " + bpList.size());
				System.out.println("----------");
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Betriebspunkt> getBpList() {
		return bpList;

	}

}
