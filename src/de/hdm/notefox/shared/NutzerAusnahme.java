package de.hdm.notefox.shared;

import java.io.Serializable;

public class NutzerAusnahme extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public NutzerAusnahme() {
	}

	public NutzerAusnahme(String message) {
		super(message);
	}

}
