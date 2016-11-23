package de.hdm.notefox.shared.report;

import java.io.Serializable;
import java.util.Date;

/**
 * Mutterklasse aller Reports.
 * Reports können von dem Server an den Client gesendet werden, 
 * da sie als Serializable deklariert sind.
 * Nach der Bereitstellung der Reports erfolgt der Zugriff auf diese clientseitig lokal. 
 * 
 * Ein Report besitzt Standardelemente, die mit Attributen modelliert 
 * und dort dokumentiert werden.
 */
public abstract class Report implements Serializable {

  /**
   * Unique IDentifier
   */
  private static final long serialVersionUID = 1L;

  /**
   * Immpressum (falls man diesen brauchen sollte).
   */
  private Paragraph imprint = null;

  /**
   * Headerdaten des Berichts.
   */
  private Paragraph headerData = null;

  /**
   * Titel des Berichts
   */
  private String title = "Report";

  /**
   * Erstelldatum-Objekt des Berichts
   */
  private Date created = new Date();

  /**
   * Impressum auslesen.
   */
  public Paragraph getImprint() {
    return this.imprint;
  }

  /**
   * Impressum setzen.
   */
  public void setImprint(Paragraph imprint) {
    this.imprint = imprint;
  }

  /**
   * Headerdaten auslesen.
   */
  public Paragraph getHeaderData() {
    return this.headerData;
  }

  /**
   * Headerdaten setzen.
   */
  public void setHeaderData(Paragraph headerData) {
    this.headerData = headerData;
  }

  /**
   * Reporttitel auslesen.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Reporttitel setzen.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Erstelldatum auslesen.
   */
  public Date getCreated() {
    return this.created;
  }

  /**
   * Erstelldatum setzen.
   * (nicht zwingend erfoderlich, da jeder Report diesen automatisch festhält)
   */
  public void setCreated(Date created) {
    this.created = created;
  }

}
