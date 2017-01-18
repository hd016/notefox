package de.hdm.notefox.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Zeile einer Tabelle eines SimpleReport-Objekts. Damit können Kopien z.B. vom
 * Server an den Clienten übertragen werden, da das Interface Serializable
 * implementiert wurde.
 */
public class Row implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Speicherplatz (Spalten der Zeile).
	 */
	private Vector<Column> columns = new Vector<Column>();

	/**
	 * Eine Spalte hinzufügen.
	 */
	public void addColumn(Column c) {
		this.columns.addElement(c);
	}

	/**
	 * Eine benannte Spalte entfernen.
	 */
	public void removeColumn(Column c) {
		this.columns.removeElement(c);
	}

	/**
	 * Alle Spalten auslesen.n
	 */
	public Vector<Column> getColumns() {
		return this.columns;
	}

	/**
	 * Anzahl aller Spalten auslesen.
	 */
	public int getNumColumns() {
		return this.columns.size();
	}

	/**
	 * Einzelne Spalten-Objekts werden ausgelesen. Index i der auszulesenden
	 * Spalte und n für die Anzahl der Spalten.
	 */
	public Column getColumnAt(int i) {
		return this.columns.elementAt(i);
	}
}
