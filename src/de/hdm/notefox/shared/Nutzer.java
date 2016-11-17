package de.hdm.notefox.shared;

public class Nutzer {

  /**
   * Variablen des Nutzers.
  */

private int nutzerid;
private String name = "";
private String passwort = "";
  
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
