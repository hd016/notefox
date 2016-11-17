package de.hdm.notefox.shared.bo;

import java.io.Serializable;

/**
 * �BusinessObject.java ist die Superklasse aller relevanten Klassen im Objekt. 
 * Die Business Objekts repr�sentieren reale betriebswirtschaftliche Objekte, 
 * die f�r die Umsetzung des Fachkonzepts wichtig sind. Jedes Businessobjekt 
 * hat einen Prim�rschl�ssel, die das Objekt in einer relationalen 
 * Datenbankeindeutig identifizieren. 
 * Sie implementiert das Interface >>Serializable<<, dadurch k�nnen die Objekte 
 * in eine textuelle Form �berf�hrt werden. Dies wird ben�tigt, 
 * damit der Client und der Server Objekte unter sich austauschen kann.�
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
   * Inhaltlicher Gleichheit feststellen (in diesem Fall �ber die ID) 
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
         * ggb. false zur�ckgeben
         */
        return false;
      }
    }
    /*
     * keine Gleichheit, dann false zur�ckgeben
     */
    return false;
  }
  
  /**
   * HashCode-Mehtode erzeugt eine ganze Zahl, die f�r das BusinessObject 
   * charakteristisch ist. 
   */
  
public int hashCode() {
	  return this.id;
  }

}
