package de.hdm.notefox.shared.bo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Notizbuch extends Notizobjekt {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variablen des Notizbuches
	 */
	private int notizbuchId;

	private List<Notiz> notizen = new ArrayList<Notiz>();

	/**
	 * Auslesen und Setzen der Variablen
	 */
	public int getNotizbuchId() {
		return notizbuchId;
	}

	public void setNotizbuchId(int notizbuchId) {
		this.notizbuchId = notizbuchId;
	}

	public List<Notiz> getNotizen() {
		return notizen;
	}

	public void setNotizen(List<Notiz> notizen) {
		this.notizen = notizen;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
