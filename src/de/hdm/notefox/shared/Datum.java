package de.hdm.notefox.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Die Klasse Datum für die Umsetzung der Funktion Fälligkeiten bei Notizen.
 *
 */

public class Datum implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Variablen der Klasse Datum.
	 */

	private int faelligkeitId;

	private boolean status;

	private Date faelligkeitsdatum;

	/*
	 * Auslesen und Setzen der Variablen.
	 */
	public int getFaelligkeitId() {
		return faelligkeitId;
	}

	public void setFaelligkeitId(int faelligkeitId) {
		this.faelligkeitId = faelligkeitId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getFaelligkeitsdatum() {
		return faelligkeitsdatum;
	}

	public void setFaelligkeitsdatum(Date faelligkeitsdatum) {
		this.faelligkeitsdatum = faelligkeitsdatum;
	}
}
