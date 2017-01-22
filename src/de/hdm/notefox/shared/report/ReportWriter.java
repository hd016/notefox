package de.hdm.notefox.shared.report;

/**
 * Wird benoetigt, um vom Server zur Verfuegung gestellten Report auf dem Client
 * ein menschenlesbares Format zu verwandeln.
 */
public abstract class ReportWriter {

	/**
	 * Das AlleNotizbuecherDesNutzersReport wird in das Zielformat uebersetzt.
	 */
	public abstract void process(AlleNotizbuecherDesNutzersReport r);

	/**
	 * Das AlleNotizenDesNutzersReport wird in das Zielformat uebersetzt.
	 */
	public abstract void process(AlleNotizenDesNutzersReport r);

	/**
	 * Das AlleNotizbuecherAllerNutzerReport wird in das Zielformat uebersetzt.
	 */
	public abstract void process(AlleNotizbuecherAllerNutzerReport r);

	/**
	 * Das AlleNotizenAllerNutzerReport wird in das Zielformat uebersetzt.
	 */
	public abstract void process(AlleNotizenAllerNutzerReport r);
}
