package de.hdm.notefox.shared;

import java.util.ArrayList;

import de.hdm.notefox.shared.bo.Notizobjekt;

public class Nutzer {

  /**
   * Variablen des Nutzers.
  */

private int nutzerid;
private String name = "";
private String passwort = "";


/**
 * ArrayList mit Objekten der verbundenen Klasse 
 */
private ArrayList<Notizobjekt> notizobjektliste = new ArrayList <Notizobjekt>(); 
  

/**Auslesen und Setzen der Variablen
 */
  public int getNutzerId() {
    return this.nutzerid;
  }

  public void setNutzerId(int nutzerid) {
    this.nutzerid = nutzerid;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getPasswort() {
    return this.passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }


}
