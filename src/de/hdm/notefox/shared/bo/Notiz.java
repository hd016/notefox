package de.hdm.notefox.shared.bo;

import java.util.Date;

import de.hdm.notefox.shared.Datum;
import de.hdm.notefox.shared.Notizquelle;

public class Notiz extends BusinessObject {

	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	  /** Variablen der Notiz
	   */
	private int eigentuemerId;
	
	private String titel;

	private String subtitel;
	
	private String inhalt;

	private Date erstelldatum;

	private Date modifikationsdatum;

	
	  /** Konstruktor
	   */
	public Notiz() {
	}

	/**
	 * Objekt der verbundenen Klasse Datum als Attribut deklariert
	 */
	private Datum datum;
	
	
	/**
	 * Objekt der verbundenen Klasse Notizquelle als Attribut deklariert 
	 */
	private Notizquelle notizquelle;

	
	/** Setzen und Auslesen der Variablen
	 */

	public int getEigentuemerId() {
		return eigentuemerId;
	}


	public void setEigentuemerId(int eigentuemerId) {
		this.eigentuemerId = eigentuemerId;
	}


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

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
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

	
}
