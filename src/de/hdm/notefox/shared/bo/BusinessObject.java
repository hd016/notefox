package de.hdm.notefox.shared.bo;

import java.io.Serializable;

/**
 * „BusinessObject.java ist die Superklasse aller relevanten Klassen im Objekt. 
 * Die Business Objekts repräsentieren reale betriebswirtschaftliche Objekte, 
 * die für die Umsetzung des Fachkonzepts wichtig sind. Jedes Businessobjekt 
 * hat einen Primärschlüssel, die das Objekt in einer relationalen 
 * Datenbankeindeutig identifizieren. 
 * Sie implementiert das Interface >>Serializable<<, dadurch können die Objekte 
 * in eine textuelle Form überführt werden. Dies wird benötigt, 
 * damit der Client und der Server Objekte unter sich austauschen kann.“
 */
public abstract class BusinessObject implements Serializable {

	/** Unique IDentifier
	 */
  private static final long serialVersionUID = 1L;

  /**
   * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
   */
  private int id = 0;

  /**
   * Auslesen und Setzen der Variablen.
   */
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /**
   * textuelle Darstellung der jeweiligen Instanz
   */
  @Override
public String toString() {
    /*
     * Darstellung: Klassennamen + ID vom Objekt
     */
    return this.getClass().getName() + " #" + this.id;
  }

  /**
   * Inhaltlicher Gleichheit feststellen (in diesem Fall über die ID) 
   */

public boolean equals(Object o) {
    /*
     * Abfrage: Objekt ungleich NULL + Testen ob Objekt gecastet (Typumwandlung)
     * werden kann
     */
    if (o != null && o instanceof BusinessObject) {
      BusinessObject bo = (BusinessObject) o;
      try {
        if (bo.getId() == this.id)
          return true;
      }
      catch (IllegalArgumentException e) {
        /*
         * ggb. false zurückgeben
         */
        return false;
      }
    }
    /*
     * keine Gleichheit, dann false zurückgeben
     */
    return false;
  }
  
  /**
   * HashCode-Mehtode erzeugt eine ganze Zahl, die für das BusinessObject 
   * charakteristisch ist. 
   */
  
public int hashCode() {
	  return this.id;
  }

}
