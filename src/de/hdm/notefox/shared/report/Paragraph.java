package de.hdm.notefox.shared.report;

import java.io.Serializable;

/**
 * Reports ermöglichen Texte strukturiert abspeichern zu können. Durch den
 * ReportWriter kann dieser Text in unterschiedliche Zielformate umgewandelt
 * werden.
 * 
 * Objekte dieser Klasse können durch das Netzwerk übertragen werden, da
 * Paragraph Serializable ist.
 */
public abstract class Paragraph implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

}
