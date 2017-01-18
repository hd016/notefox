package de.hdm.notefox.shared.report;

import java.io.Serializable;

/*
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * <p>
 * Alle Notizbücher aller Nutzer werden in diesem Report dargestellt.
 * Die Klasse trägt keine weiteren Attribute- und Methoden, 
 * da alles notwendige schon in den Superklassen vorliegt, Ihre Existenz ist 
 * dennoch wichtig, um bestimmte Typen von Reports deklarieren und mit Ihnen 
 * objektorientiert umgehen zu können.
 * 
 *  
 */
public class AlleNotizbuecherAllerNutzerReport extends CompositeReport implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

}