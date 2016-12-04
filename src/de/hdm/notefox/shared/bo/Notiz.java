package de.hdm.notefox.shared.bo;

import de.hdm.notefox.shared.Datum;
import de.hdm.notefox.shared.Notizquelle;

public class Notiz extends Notizobjekt {

	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	  /** Variablen der Notiz
	   */
	
	  /** Konstruktor
	   */
	public Notiz() {
	}
	

	/**
	 * Objekt der verbundenen Klasse Datum als Attribut deklariert
	 */
	private Datum datum;
	
	
	/**
	 * Objekt der verbundenen Klasse Notizquelle als Attribut deklariert 
	 */
	private Notizquelle notizquelle;

	
}
