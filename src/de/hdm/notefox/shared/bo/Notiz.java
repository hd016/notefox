package de.hdm.notefox.shared.bo;

import java.util.Date;

/**
 * Das BusinessObjekt: Notiz. Erbt vom Notizobjekt. 
 * 
 *
 */

public class Notiz extends Notizobjekt {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor
	 */
	public Notiz() {
	}

	/**
	 * Variablen der Notiz
	 */
	private Date faelligkeitsdatum = new Date();
	private String notizquelle;
	private Notizbuch notizbuch;

	/**
	 * Setzen und Auslesen der Variablen
	 */

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

	public Notizbuch getNotizbuch() {
		return notizbuch;
	}

	public void setNotizbuch(Notizbuch notizbuch) {
		this.notizbuch = notizbuch;
	}

}
