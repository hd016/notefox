package de.hdm.notefox.shared.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notizbuch extends BusinessObject {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variablen des Notizbuches
	 */
	
	private int eigentuemerId;
	
	private String titel;

	private String subtitel;

	private Date erstelldatum;

	private Date modifikationsdatum;


	private List<Notiz> notizen = new ArrayList<Notiz>();

	/**
	 * Auslesen und Setzen der Variablen
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
