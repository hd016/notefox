package de.hdm.notefox.shared.bo;

import java.util.ArrayList;
import java.util.List;

public class Notizbuch extends Notizobjekt {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variablen des Notizbuches
	 */

	private List<Notiz> notizen = new ArrayList<Notiz>();

	/**
	 * Auslesen und Setzen der Variablen
	 */

	public List<Notiz> getNotizen() {
		return notizen;
	}

	public void setNotizen(List<Notiz> notizen) {
		this.notizen = notizen;
	}

}
