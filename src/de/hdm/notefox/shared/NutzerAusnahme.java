package de.hdm.notefox.shared;

import java.io.Serializable;

public class NutzerAusnahme extends RuntimeException implements Serializable {

	public NutzerAusnahme() {
	}

	public NutzerAusnahme(String message) {
		super(message);
	}

}
