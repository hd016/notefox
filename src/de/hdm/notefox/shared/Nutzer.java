package de.hdm.notefox.shared;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdm.notefox.shared.bo.Notizobjekt;

public class Nutzer implements Serializable {

  /**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
/**
   * Variablen des Nutzers.
  */

private int nutzerId;
private String name = "";

/**
 * ArrayList mit Objekten der verbundenen Klasse 
 */
private ArrayList<Notizobjekt> notizobjektliste = new ArrayList <Notizobjekt>(); 
  

/**Auslesen und Setzen der Variablen
 */
  public int getNutzerId() {
    return this.nutzerId;
  }

  public void setNutzerId(int nutzerid) {
    this.nutzerId = nutzerid;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
