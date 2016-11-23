package de.hdm.notefox.shared.report;

import java.io.Serializable;

/**
 * Column-Objekte sind Spalten eines Row-Objekts.
 * Damit können Kopien z.B. vom Server an den Clienten übertragen werden,
 * da das Interface Serializable implementiert wurde.
 */
public class Column implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Spaltenobjekt-Wert entspricht dem Zelleneintrag einer Tabelle.
   * Realisierung eines einfachen textuellen Wertes.
   */
  private String value = "";

  /**
   * No-Argument-Konstruktor besitzen. Ist kein Konstruktor
   */
  public Column() {
  }

  /**
   * Durch den Konstruktor wird die Angabe eines Spalteneintrags (Wert)erzwung.
   * 
   */
  public Column(String s) {
    this.value = s;
  }

  /**
   * Der Spaltenwert wird ausgelesen.
   */
  public String getValue() {
    return value;
  }

  /**
   * Der aktuelle Spaltenwert wird überschrieben.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Column-Objekt in einen String umwandeln.
   */
public String toString() {
    return this.value;
  }
}
