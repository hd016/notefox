package de.hdm.notefox.shared.report;

import java.io.Serializable;

/**
 * Reports erm�glichen Texte strukturiert abspeichern zu k�nnen.
 * Durch den ReportWriter kann dieser Text in unterschiedliche Zielformate 
 * umgewandelt werden.
 * 
 * Objekte dieser Klasse k�nnen durch das Netzwerk �bertragen werden, da
 * Paragraph Serializable ist.
 */
public abstract class Paragraph implements Serializable {

  /**
   * Unique IDentifier
   */
  private static final long serialVersionUID = 1L;

}
