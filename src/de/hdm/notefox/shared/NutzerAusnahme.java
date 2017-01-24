package de.hdm.notefox.shared;

import java.io.Serializable;

/**
 * Ausnahme um Meldungen vom Backend ans Frontend zu kommunizieren. Danach werden die Meldungen
 * mit Window.alert() angezeigt.
 * @author DHarun
 *
 */
public class NutzerAusnahme extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public NutzerAusnahme() {
	}

	public NutzerAusnahme(String message) {
		super(message);
	}

}
