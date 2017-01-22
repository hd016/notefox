package de.hdm.notefox.shared.report;

import java.util.Vector;

/**
 * Reports werden mit dem ReportWriter mittels HTML formatiert.
 */
public class HTMLReportWriter extends ReportWriter {

	/**
	 * Belegung der Variable mit dem Ergebnis einer Umwandlung. Format:
	 * HTML-Text
	 */
	private String reportText = "";

	/**
	 * reportText-Varibale wird zurueckgesetzt.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * ParagraphObjekt in HTML umwandeln.t
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * CompositeParagraph-Objekt in HTML umwandeln.
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * SimpleParagraph-Objekt in HTML umwandeln.
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * Produzierung des HTML-Header-Textes.
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * Produzierung des HTML-Trailer-Textes.
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	/**
	 * uebergebener Report und Ablage im Zielformat.
	 */

	public void process(AlleNotizenDesNutzersReport r) {
		// Löschen der Ergebnisse vorhergehender Prozessierungen. 
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, waehrend der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das uebersetzen in
		 * HTML-Form. (Schritt fuer Schritt).
		 */
		result.append("<H1>" + r.getTitle() + "</H1>");
		result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		result.append("<td valign=\"top\"><b>" + paragraph2HTML(r.getHeaderData()) + "</b></td>");
		result.append("<td valign=\"top\">" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:400px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver\">" + row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das ermöglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * uebergebener Report und Ablage im Zielformat.
	 */

	public void process(AlleNotizbuecherDesNutzersReport r) {
		// Loeschen der Ergebnisse vorhergehender Prozessierungen.
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, waehrend der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das uebersetzen in
		 * HTML-Form. (Schritt fuer Schritt).
		 */
		result.append("<H1>" + r.getTitle() + "</H1>");
		result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		result.append("<td valign=\"top\"><b>" + paragraph2HTML(r.getHeaderData()) + "</b></td>");
		result.append("<td valign=\"top\">" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:400px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver\">" + row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das ermoeglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * uebergebener Report und Ablage im Zielformat.
	 */
	public void process(AlleNotizenAllerNutzerReport r) {
		/** Loeschen der Ergebnisse vorhergehender Prozessierungen. */
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, 
		 *  waehrend der Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das uebersetzen in
		 * HTML-Form. (Schritt fuer Schritt).
		 */
		result.append("<H1>" + r.getTitle() + "</H1>");
		result.append("<table><tr>");

		if (r.getHeaderData() != null) {
			result.append("<td>" + paragraph2HTML(r.getHeaderData()) + "</td>");
		}

		result.append("<td>" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		/*
		 * r enthaelt eine Menge von Teil-Reports des Typs
		 * AlleNotizenDesNutzersReport, da AlleNotizenAllerNutzerReport ein
		 * CompositeReport ist. processAlleNotizenDesNutzersReport wird fuer
		 * jeden dieser Teil-Reports aufgerufen. Dem Buffer wird das Ergebnis
		 * des jew. Aufrufs hinzugefuegt.
		 */
		for (int i = 0; i < r.getNumSubReports(); i++) {
			/*
			 * Voraussertzung des AlleNotizenDesNutzersReports als Typ der
			 * SubReports
			 */
			AlleNotizenDesNutzersReport subReport = (AlleNotizenDesNutzersReport) r.getSubReportAt(i);

			this.process(subReport);

			result.append(this.reportText + "\n");

			/**
			 * Ergebnisvariable zuruecksetzen.
			 */
			this.resetReportText();
		}

		/*
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable. Das ermoeglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * uebergebener Report und Ablage im Zielformat.
	 */
	public void process(AlleNotizbuecherAllerNutzerReport r) {
		/** Loeschen der Ergebnisse vorhergehender Prozessierungen.*/
		this.resetReportText();

		/**
		 * Sukzessives Schreiben der Ergebnisse in diesen Buffer, waehrend der
		 * Prozessierung
		 */
		StringBuffer result = new StringBuffer();

		/*
		 * Auslesen der einzelnen Bestandteile des Reports und das uebersetzen in
		 * HTML-Form. (Schritt fuer Schritt).
		 */
		result.append("<H1>" + r.getTitle() + "</H1>");
		result.append("<table><tr>");

		if (r.getHeaderData() != null) {
			result.append("<td>" + paragraph2HTML(r.getHeaderData()) + "</td>");
		}

		result.append("<td>" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		/*
		 * r enthaelt eine Menge von Teil-Reports des Typs
		 * AlleNotizbuecherDesNutzersReport, da
		 * AlleNotizbuecherAllerNutzerReport ein CompositeReport ist.
		 * processAlleNotizbuecherDesNutzersReport wird fuer jeden dieser
		 * Teil-Reports aufgerufen. Dem Buffer wird das Ergebnis des jew.
		 * Aufrufs hinzugefuegt.
		 */
		for (int i = 0; i < r.getNumSubReports(); i++) {
			/*
			 * Voraussertzung des AlleNotizbuecherDesNutzersReports als Typ der
			 * SubReports
			 */
			AlleNotizbuecherDesNutzersReport subReport = (AlleNotizbuecherDesNutzersReport) r.getSubReportAt(i);

			this.process(subReport);

			result.append(this.reportText + "\n");

			/**
			 * Ergebnisvariable zurücksetzen.
			 */
			this.resetReportText();
		}

		/**
		 * Umwandeln des Arbeits-Buffers in einen String + Zuweisung der
		 * reportText-Variable Das ermöglicht das Ergebnis mit getReportText()
		 * auszulesen.
		 */
		this.reportText = result.toString();
	}

	/**
	 * Das Ergebnis der zuletzt aufgerufenen Prozessierungsmethode wird
	 * ausgelesen.
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}

}