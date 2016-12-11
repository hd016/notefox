package de.hdm.notefox.shared.bo;

import java.util.Date;

public class Notiz extends Notizobjekt {

	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	
	  /** Konstruktor
	   */
	public Notiz() {
	}

	public Date getFaelligkeitsdatum() {
		return faelligkeitsdatum;
	}


	public void setFaelligkeitsdatum(Date faelligkeitsdatum) {
		this.faelligkeitsdatum = faelligkeitsdatum;
	}

	public String getNotizquelle() {
		return notizquelle;
	}

	public void setNotizquelle(String notizquelle) {
		this.notizquelle = notizquelle;
	}

	/**
	 * Faelligkeitsdatum der Notiz
	 */
	private Date faelligkeitsdatum;
	
	
	/**
	 * Objekt der verbundenen Klasse Notizquelle als Attribut deklariert 
	 */
	private String notizquelle;

	
	/** Setzen und Auslesen der Variablen
	 */


	
}
