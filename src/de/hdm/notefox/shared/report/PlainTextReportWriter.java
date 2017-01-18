package de.hdm.notefox.shared.report;

import java.util.Vector;

/**
 * ReportWriter, der Reports mit Plain Text formatiert.
 */
public class PlainTextReportWriter extends ReportWriter {

	/**
	 * Belegung der Variable mit dem Ergebnis einer Umwandlung. Format: Text
	 */

	private String reportText = "";

	/**
	 * reportText-Varibale wird zur�ckgesetzt.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Produzieren des Headertextes.
	 */
	public String getHeader() {
		return "";
	}

	/**
	 * Produzieren des Trailer-Textes.
	 */
	public String getTrailer() {
		// Einfache Trennlinie, um das Report-Ende zu markieren.
		return "___________________________________________";
	}

	/**
	 * Übergebenes Report und Ablage im Zielformat prozessieren. Auslesen des
	 * Ergebinus mittels getReportText().
	 */
	public void process(AlleNotizenDesNutzersReport r) {

		// Löschen der Ergebnisse vorhergehender Prozessierungen.
		this.resetReportText();

		/*
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, während der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das Übersetzen in
		 * Text-Form. (Schritt für Schritt).
		 */
		result.append("*** " + r.getTitle() + " ***\n\n");
		result.append(r.getHeaderData() + "\n");
		result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");
		Vector<Row> rows = r.getRows();

		for (Row row : rows) {
			for (int k = 0; k < row.getNumColumns(); k++) {
				result.append(row.getColumnAt(k) + "\t ; \t");
			}

			result.append("\n");
		}

		result.append("\n");
		result.append(r.getImprint() + "\n");

		/*
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das ermöglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * Übergebenes Report und Ablage im Zielformat prozessieren. Auslesen des
	 * Ergebinus mittels getReportText().
	 */
	public void process(AlleNotizenAllerNutzerReport r) {

		// Löschen der Ergebnisse vorhergehender Prozessierungen.
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, während der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das übersetzen in
		 * Text-Form. (Schritt für Schritt).
		 */
		result.append("*** " + r.getTitle() + " ***\n\n");

		if (r.getHeaderData() != null)
			result.append(r.getHeaderData() + "\n");

		result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");

		/*
		 * r enthält eine Menge von Teil-Reports des Typs
		 * AlleNotizenDesNutzersReport, da AlleNotizenAllerNutzerReport ein
		 * CompositeReport ist. processAlleNotizbuecherDesNutzersReport wird für
		 * jeden dieser Teil-Reports aufgerufen. Dem Buffer wird das Ergebnis
		 * des jew. Aufrufs hinzugefügt.
		 */
		for (int i = 0; i < r.getNumSubReports(); i++) {

			/*
			 * Voraussertzung des AlleNotizenDesNutzersReports als Typ der
			 * SubReports
			 */
			AlleNotizenDesNutzersReport subReport = (AlleNotizenDesNutzersReport) r.getSubReportAt(i);

			this.process(subReport);

			result.append(this.reportText + "\n\n\n\n\n");

			/**
			 * Ergebnisvariable zurücksetzen.
			 */
			this.resetReportText();
		}

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable. Das ermöglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	public void process(AlleNotizbuecherDesNutzersReport r) {

		// Löschen der Ergebnisse vorhergehender Prozessierungen.
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, wÄhrend der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das Übersetzen in
		 * Text-Form. (Schritt für Schritt).
		 */
		result.append("*** " + r.getTitle() + " ***\n\n");
		result.append(r.getHeaderData() + "\n");
		result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");
		Vector<Row> rows = r.getRows();

		for (Row row : rows) {
			for (int k = 0; k < row.getNumColumns(); k++) {
				result.append(row.getColumnAt(k) + "\t ; \t");
			}

			result.append("\n");
		}

		result.append("\n");
		result.append(r.getImprint() + "\n");

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das ermöglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	public void process(AlleNotizbuecherAllerNutzerReport r) {

		// Löschen der Ergebnisse vorhergehender Prozessierungen.
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, während der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das Übersetzen in
		 * Text-Form. (Schritt für Schritt).
		 */
		result.append("*** " + r.getTitle() + " ***\n\n");

		if (r.getHeaderData() != null)
			result.append(r.getHeaderData() + "\n");

		result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");

		/*
		 * r enthält eine Menge von Teil-Reports des Typs
		 * AlleNotizbuecherDesNutzersReport, da
		 * AlleNotizbuecherAllerNutzerReport ein CompositeReport ist.
		 * processAlleNotizbuecherDesNutzersReport wird für jeden dieser
		 * Teil-Reports aufgerufen. Dem Buffer wird das Ergebnis des jew.
		 * Aufrufs hinzugef�gt.
		 */
		for (int i = 0; i < r.getNumSubReports(); i++) {

			/*
			 * Voraussetzung des AlleNotizbuecherDesNutzersReports als Typ der
			 * SubReports
			 */
			AlleNotizbuecherDesNutzersReport subReport = (AlleNotizbuecherDesNutzersReport) r.getSubReportAt(i);

			this.process(subReport);

			result.append(this.reportText + "\n\n\n\n\n");

			/**
			 * Ergebnisvariable zurücksetzen.
			 */
			this.resetReportText();
		}

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das erm�glicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * Das Ergebnis der zuletzt aufgerufenen Prozessierungsmethode auslesen.
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}
}