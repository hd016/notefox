package de.hdm.notefox.shared.report;

import java.io.Serializable;

/**
 * Einzelne Absaetze werden dargestellt. Der Inhalt der Absaetze werden as String
 * gespeichert.
 */
public class SimpleParagraph extends Paragraph implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Absatzinhalt.
	 */
	private String text = "";

	/**
	 * No-Argument-Konstruktor
	 */
	public SimpleParagraph() {
	}

	/**
	 * Ermoeglicht den Inhalt des SimpleParagraph-Objekten bereits bei der
	 * Instantiierung anzugeben.
	 */
	public SimpleParagraph(String value) {
		this.text = value;
	}

	/**
	 * Inhalt wird als String ausgelesen.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Inhalt wird ueberschrieben.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * SimpleParagraph-Objekts wird in einen String umgewandelt.
	 */
	public String toString() {
		return this.text;
	}
}
