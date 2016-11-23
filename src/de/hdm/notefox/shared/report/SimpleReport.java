package de.hdm.notefox.shared.report;

import java.util.Vector;

/**
 * Einfacher Report weist neben den Infos der Superklase auch eine
 * eine Tabelle mit Positionsdatwn auf. 
 */
public abstract class SimpleReport extends Report {

  /**
   * Unique IDentifier
   */
  private static final long serialVersionUID = 1L;

  /**
   * Tabelle mit Positionsdaten. 
   * in dem Vector wird die Tabelle zeilenweise abgelegt.
   */
  private Vector<Row> table = new Vector<Row>();

  /**
   * Eine Zeile hinzufügen.
   */
  public void addRow(Row r) {
    this.table.addElement(r);
  }

  /**
   * Eine Zeile entfernen.
   */
  public void removeRow(Row r) {
    this.table.removeElement(r);
  }

  /**
   * Alle Positionsdaten auslesen.
   */
  public Vector<Row> getRows() {
    return this.table;
  }
}