package de.hdm.notefox.shared;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class Nutzer implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Variablen des Nutzers.
	 */

	private int nutzerId;
	private String email = "";
	private String name;

	/**
	 * ArrayList mit Objekten der verbundenen Klasse
	 */
	private ArrayList<Notiz> notizliste = new ArrayList<Notiz>();

	private ArrayList<Notizbuch> notizbuchliste = new ArrayList<Notizbuch>();

	/**
	 * Auslesen und Setzen der Variablen
	 */
	public int getNutzerId() {
		return this.nutzerId;
	}

	public void setNutzerId(int nutzerid) {
		this.nutzerId = nutzerid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Notiz> getNotizliste() {
		return notizliste;
	}

	public void setNotizliste(ArrayList<Notiz> notizliste) {
		this.notizliste = notizliste;
	}

	public ArrayList<Notizbuch> getNotizbuchliste() {
		return notizbuchliste;
	}

	public void setNotizbuchliste(ArrayList<Notizbuch> notizbuchliste) {
		this.notizbuchliste = notizbuchliste;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
