package de.hdm.notefox.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Composite stellt eine Anzahl von Absätzen dar. 
 * Als Unterabschnitte werden diese in einem Vector verwaltet.
 */
public class CompositeParagraph extends Paragraph implements Serializable {

  /**
   * Unique IDentifier
   */
  private static final long serialVersionUID = 1L;

  /**
   * Unterabschnitte werden hier gespeichert.
   */
  private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();

  /**
   * Hinzufügen eines Unterabschnitts.
   */
  public void addSubParagraph(SimpleParagraph p) {
    this.subParagraphs.addElement(p);
  }

  /**
   * Löschen eines Unterabschnitts.
   */
  public void removeSubParagraph(SimpleParagraph p) {
    this.subParagraphs.removeElement(p);
  }

  /**
   * Alle Unterabschnitte werden ausgelesen.
   */
  public Vector<SimpleParagraph> getSubParagraphs() {
    return this.subParagraphs;
  }

  /**
   * Die Anzahl der Unterabschnitte werden ausgelesen.
   */
  public int getNumParagraphs() {
    return this.subParagraphs.size();
  }

  /**
   * Einzelne Unterabschnitts werden ausgelesen.
   */
  public SimpleParagraph getParagraphAt(int i) {
    return this.subParagraphs.elementAt(i);
  }

  /**
   * CompositeParagraph wird damit in einen String umgewandelt.
   */

public String toString() {
    /**
     * Erstellen eines leeren Buffers, alle sukzessive String-Darstellungen 
     * der Unterabschnitte werden eingetragen.
     */
    StringBuffer result = new StringBuffer();

    /** Über alle Unterabschnitte eine Schleife */
    
    for (int i = 0; i < this.subParagraphs.size(); i++) {
      SimpleParagraph p = this.subParagraphs.elementAt(i);

      /**
       * Jeweiliger Unterabschnitt wird in einen String umgewandelt 
       * und anschließend an den Buffer angehängt.
       */
      result.append(p.toString() + "\n");
    }

    /**
     * Umwandlung des Buffers in einen String + Rückgabe dieser String
     */
    return result.toString();
  }
}
