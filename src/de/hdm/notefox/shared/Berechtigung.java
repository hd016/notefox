package de.hdm.notefox.shared;

import java.io.Serializable;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 * 
 * Die Klasse Berechtigung implementiert die Serialisierung. Außerdem stellt es durch ein Enumeration
 * folgende Berechtiungsarten zur Verfügung: LESEN, EDITIEREN, LÖSCHEN.
 *
 */
public class Berechtigung implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Berechtigungsart {
		LESEN, EDITIEREN, LOESCHEN
	}

	/*
	 * Variablen von Permission.
	 */

	private int berechtigungId;
	private Berechtigungsart berechtigungsart;
	private Notiz notiz;
	private Notizbuch notizbuch;
	private Nutzer berechtigter;

	public Berechtigung() {
	}

	/*
	 * Auslesen und Setzen der Variablen.
	 */

	public int getBrechtigungId() {
		return berechtigungId;
	}

	public void setBerechtigungId(int berechtigungId) {
		this.berechtigungId = berechtigungId;
	}

	public Berechtigungsart getBerechtigungsart() {
		return berechtigungsart;
	}

	public void setBerechtigungsart(Berechtigungsart berechtigungsart) {
		this.berechtigungsart = berechtigungsart;
	}

	public Notiz getNotiz() {
		return notiz;
	}

	public void setNotiz(Notiz notiz) {
		this.notiz = notiz;
	}

	public Notizbuch getNotizbuch() {
		return notizbuch;
	}

	public void setNotizbuch(Notizbuch notizbuch) {
		this.notizbuch = notizbuch;
	}

	public Nutzer getBerechtigter() {
		return berechtigter;
	}

	public void setBerechtigter(Nutzer berechtigter) {
		this.berechtigter = berechtigter;
	}

}
