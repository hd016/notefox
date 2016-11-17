package de.hdm.notefox.shared;

public class Berechtigung {
	


	  /**
	   * Variablen von Permission.
	  */

	private int berechtigungId;
	private String berechtigungName = "";
	

	  
	/**Auslesen und Setzen der Variablen
	 */
	
	public int getBrechtigungId() {
		return berechtigungId;
	}
	
	public void setBerechtigungId(int berechtigungId) {
		this.berechtigungId = berechtigungId;
	}
	
	public String getBerechtigungName() {
		return berechtigungName;
	}
	
	public void setBerechtigungName(String berechtigungName) {
		this.berechtigungName = berechtigungName;
	}

}
