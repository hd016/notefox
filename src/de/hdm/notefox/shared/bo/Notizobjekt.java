package de.hdm.notefox.shared.bo;

import java.util.Date;
import java.util.List;

import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Nutzer;

public abstract class Notizobjekt extends BusinessObject {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variablen des Notizobjekts
	 */
	private Nutzer eigentuemer;
	
	private String titel = "";

	private String subtitel = "";
	
	private String inhalt = "";

	private Date erstelldatum;

	private Date modifikationsdatum;

	private List<Berechtigung> berechtigungen;

	/**
	 * Auslesen und Setzen der Variablen
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

	public Nutzer getEigentuemer() {
		return eigentuemer;
	}

	public void setEigentuemer(Nutzer eigentuemer) {
		this.eigentuemer = eigentuemer;
	}

	public List<Berechtigung> getBerechtigungen() {
		return berechtigungen;
	}

	public void setBerechtigungen(List<Berechtigung> berechtigungen) {
		this.berechtigungen = berechtigungen;
	}

}
