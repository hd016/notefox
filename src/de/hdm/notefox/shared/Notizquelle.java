package de.hdm.notefox.shared;

import java.io.Serializable;

public class Notizquelle implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Variablen der Notizquelle
	 */

	private int notizquelleId;

	private String notizquelleName;

	private String url;

	/**
	 * Auslesen und Setzen der Variablen
	 */
	public int getNotizquelleId() {
		return notizquelleId;
	}

	public void setNotizquelleId(int notizquelleId) {
		this.notizquelleId = notizquelleId;
	}

	public String getNotizquelleName() {
		return notizquelleName;
	}

	public void setNotizquelleName(String notizquelleName) {
		this.notizquelleName = notizquelleName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
