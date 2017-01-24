package de.hdm.notefox.shared.report;

import java.io.Serializable;

/**
 * Reports ermoeglichen Texte strukturiert abspeichern zu koennen. Durch den
 * ReportWriter kann dieser Text in unterschiedliche Zielformate umgewandelt
 * werden.
 * 
 * Objekte dieser Klasse koennen durch das Netzwerk uebertragen werden, da
 * Paragraph Serializable ist.
 */
public abstract class Paragraph implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

}
