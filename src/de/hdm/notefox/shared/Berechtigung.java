package de.hdm.notefox.shared;

public class Berechtigung {
	
	public static enum Berechtigungsart{
		LESEN, EDITIEREN, LOESCHEN
	}


	  /**
	   * Variablen von Permission.
	  */

	private int berechtigungId;
	private Berechtigungsart berechtigungName;
	

	  
	/**Auslesen und Setzen der Variablen
	 */
	
	public int getBrechtigungId() {
		return berechtigungId;
	}
	
	public void setBerechtigungId(int berechtigungId) {
		this.berechtigungId = berechtigungId;
	}
	
	public Berechtigungsart getBerechtigungName() {
		return berechtigungName;
	}
	
	public void setBerechtigungName(Berechtigungsart berechtigungName) {
		this.berechtigungName = berechtigungName;
	}

}
