package de.hdm.notefox.server.report;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notefox.server.NotizobjektAdministrationImpl;
import de.hdm.notefox.shared.bo.*;
import de.hdm.notefox.shared.report.*;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.ReportGenerator;

/**
 * ReportGenerator-Interface wird implementiert.
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet
    implements ReportGenerator {

  /**
   * Zugriff auf die NotizobjektAdministration ist für den ReportGenerator notwendig,
   * da diese die essentiellen Methoden für die Koexistenz von Datenobjekten bietet.
   */
  private NotizobjektAdministration administration = null;

  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
   * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
   * mÃ¶glich.
   * </p>
   * <p>
   * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
   * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
   * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
   * </p>
   * (nlhe
   */
  public ReportGeneratorImpl() throws IllegalArgumentException {
  }

  /**
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
   * 
   * @see #ReportGeneratorImpl()
   */
  public void initialisieren() throws IllegalArgumentException {
    /*
     * Ein ReportGeneratorImpl-Objekt instantiiert fÃ¼r seinen Eigenbedarf eine
     * NotizobjektVerwaltungImpl-Instanz.
     */
    NotizobjektAdministrationImpl a = new NotizobjektAdministrationImpl();
    a.initialisieren();
    this.administration = a;
  }

/**
 * Auslesen der zugehörigen NotizobektAdministration (interner Gebrauch).
 *
 *@return das NotizobjektVerwaltungsobjekt
 */
  
 protected NotizobjektAdministration getNotizobjektVerwaltung() {
	 return this.administration;
	 
 }

/**
 * Setzen des zugehÃ¶rigen Notizobjekt-Objekts.
 */

 public void setNotizobjekt (Notizobjekt nobj) {
	 this.administration.setNotizobjekt(nobj);
 }}
 
