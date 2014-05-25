package ch.zhaw.hoferrol.shortestrailpath.topologie;

/**
 * Klasse CHBordersImport liest das File CHBorders.txt und erstellt
 * aus jeder 8.Zeile einen BorderPoint mit x- und y-Koordinaten
 * 
 * @author Roland Hofer, V1.3 - 11.05.2014
 * 
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CHBordersImport {

	private static final Logger LOG = Logger.getLogger(CHBordersImport.class);
	// REGEX zur Erkennung der LIPT-Zeilen
	private static final Pattern REGEX_LIPT = Pattern
			.compile("^LIPT\\s(\\d+\\.\\d+)\\s(\\d+\\.\\d+)\\s(\\d+\\.\\d+)");

	private final String filePath;
	private List<BorderPoint> border = new ArrayList<BorderPoint>();

	public CHBordersImport(String filePath) {
		LOG.info("CHBordersImport initialisiert mit File " + filePath);
		this.filePath = filePath;
	}

	public List<BorderPoint> importBorders() {

		InputStream is = this.getClass().getResourceAsStream(filePath);
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			String zeile = null;
			int i = 0;

			while ((zeile = reader.readLine()) != null) {

				Matcher matcher = REGEX_LIPT.matcher(zeile);
				if (matcher.matches()) {

					String first = matcher.group(1);
					String second = matcher.group(2);
					// z-Koordinate, wird nicht weiterverwendt
					String third = matcher.group(3);
					// aus jeder 8.LIPT-Zeile erstelle einen BorderPoint
					if ((i % 8) == 0) {
						BorderPoint borderPoint = new BorderPoint(i,
								(Float.parseFloat(first)),
								(Float.parseFloat(second)));

						border.add(borderPoint);

						LOG.debug("BorderPoint: " + borderPoint.getId()
								+ ", KooX: " + borderPoint.getKooX()
								+ ", KooY: " + borderPoint.getKooY()
								+ " hinzugefügt");

					}
					i++;
				}

			}
			reader.close();

		} catch (Exception ex) {
			LOG.error("Datei konnte nicht gelesen werden.", ex);
			throw new IllegalStateException(
					"Datei nicht gefunden, sollte aber da sein.", ex);
		}
		LOG.info("Die Groesse der BorderPoint List 'border' betraegt: "
				+ border.size());

		return border;
	}

}
