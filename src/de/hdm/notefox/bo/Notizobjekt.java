package de.hdm.notefox.bo;

import java.util.Date;

public abstract class Notizobjekt extends BusinessObject {
  
	/** Unique IDentifier
	 */
  private static final long serialVersionUID = 1L;
  
  /** Variablen des Notizobjekts
   */
  private String titel;

  private String subtitel;

  private Date erstelldatum;

  private Date modifikationsdatum;
  
  private String inhalt;
  
  private String eigentuemer;

  /**
   * Konstruktur
   */
  public Notizobjekt() {
    super();
  }

  
  /** Auslesen und Setzen der Variablen
   */
  
public String getTitel() {
	return titel;
}

public void setTitel(String titel) {
	this.titel = titel;
}

public String getSubtitel() {
	return subtitel;
}

public void setSubtitel(String subtitel) {
	this.subtitel = subtitel;
}

public Date getErstelldatum() {
	return erstelldatum;
}

public void setErstelldatum(Date erstelldatum) {
	this.erstelldatum = erstelldatum;
}

public Date getModifikationsdatum() {
	return modifikationsdatum;
}

public void setModifikationsdatum(Date modifikationsdatum) {
	this.modifikationsdatum = modifikationsdatum;
}


public String getInhalt() {
	return inhalt;
}


public void setInhalt(String inhalt) {
	this.inhalt = inhalt;
}


public String getEigentuemer() {
	return eigentuemer;
}


public void setEigentuemer(String eigentuemer) {
	this.eigentuemer = eigentuemer;
}

 

}
