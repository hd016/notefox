package de.hdm.notefox.shared;

import java.io.Serializable;
import java.util.Date;

public class Filterobjekt implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Variablen des Filterobjekts.
	 */

	private String titel = "";
	private Date erstellDatumVon;
	private Date erstellDatumBis;
	private Date modifikationsDatumVon;
	private Date modifikationsDatumBis;
	private Date faelligkeitsDatumVon;
	private Date faelligkeitsDatumBis;
	private String nutzer = "";
	private boolean leseBerechtigungen;
	private boolean schreibBerechtigungen;
	private boolean loeschenBerechtigungen;

	/*
	 * Auslesen und Setzen der Variablen
	 */
	
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Date getErstellDatumVon() {
		return erstellDatumVon;
	}

	public void setErstellDatumVon(Date erstellDatumVon) {
		this.erstellDatumVon = erstellDatumVon;
	}

	public Date getErstellDatumBis() {
		return erstellDatumBis;
	}

	public void setErstellDatumBis(Date erstellDatumBis) {
		this.erstellDatumBis = erstellDatumBis;
	}

	public Date getModifikationsDatumVon() {
		return modifikationsDatumVon;
	}

	public void setModifikationsDatumVon(Date modifikationsDatumVon) {
		this.modifikationsDatumVon = modifikationsDatumVon;
	}

	public Date getModifikationsDatumBis() {
		return modifikationsDatumBis;
	}

	public void setModifikationsDatumBis(Date modifikationsDatumBis) {
		this.modifikationsDatumBis = modifikationsDatumBis;
	}

	public Date getFaelligkeitsDatumVon() {
		return faelligkeitsDatumVon;
	}

	public void setFaelligkeitsDatumVon(Date faelligkeitsDatumVon) {
		this.faelligkeitsDatumVon = faelligkeitsDatumVon;
	}

	public Date getFaelligkeitsDatumBis() {
		return faelligkeitsDatumBis;
	}

	public void setFaelligkeitsDatumBis(Date faelligkeitsDatumBis) {
		this.faelligkeitsDatumBis = faelligkeitsDatumBis;
	}

	public String getNutzer() {
		return nutzer;
	}

	public void setNutzer(String nutzer) {
		this.nutzer = nutzer;
	}

	public boolean isLeseBerechtigungen() {
		return leseBerechtigungen;
	}

	public void setLeseBerechtigungen(boolean leseBerechtigungen) {
		this.leseBerechtigungen = leseBerechtigungen;
	}

	public boolean isSchreibBerechtigungen() {
		return schreibBerechtigungen;
	}

	public void setSchreibBerechtigungen(boolean schreibBerechtigungen) {
		this.schreibBerechtigungen = schreibBerechtigungen;
	}

	public boolean isLoeschenBerechtigungen() {
		return loeschenBerechtigungen;
	}

	public void setLoeschenBerechtigungen(boolean loeschenBerechtigungen) {
		this.loeschenBerechtigungen = loeschenBerechtigungen;
	}

}
