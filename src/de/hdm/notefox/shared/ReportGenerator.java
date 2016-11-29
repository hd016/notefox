package de.hdm.notefox.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notefox.shared.bo.Notizobjekt;
import de.hdm.notefox.shared.report.AlleNotizbuecherAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizbuecherDesNutzersReport;
import de.hdm.notefox.shared.report.AlleNotizenAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizenDesNutzersReport;


/**
 * Zur Erstellung von Reports eine synchrone Schnittstelle für eine RPC-fähige Klasse.
 */

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

  /**
   * Objekt wir initialisiert.
   */
  public void initialisieren() throws IllegalArgumentException;

  /**
   * Setzen des zugeordneten Notizobjekts.
   */
  public void setNotizobjekt(Notizobjekt nobj) throws IllegalArgumentException;

  /**
   * Erstellen eines alleNotizenDesNutzersReport-Reports. 
   * Alle Notizen des Nutzers werden mit diesem Report-Typ dargestellt.
   */
  public abstract AlleNotizenDesNutzersReport erstelleAlleNotizenDesNutzersReport(
      Nutzer n) throws IllegalArgumentException;
  
  /**
   * Erstellen eines alleNotizbuecherDesNutzersReport-Reports. 
   * Alle Notizbuecher des Nutzers werden mit diesem Report-Typ dargestellt.
   */
  public abstract AlleNotizbuecherDesNutzersReport erstelleAlleNotizbuecherDesNutzersReport(
      Nutzer n) throws IllegalArgumentException;

  /**
   * Erstellen eines alleNotizenAllerNutzerReport-Reports. Alle Notizen aller Nutzer werden 
   * mit diesem Report-Typ dargestellt.
   */
  public abstract AlleNotizenAllerNutzerReport erstelleAlleNotizenAllerNutzerReport()
		  throws IllegalArgumentException;

  /**
   * Erstellen eines alleNotizbuecherAllerNutzerReport-Reports. Alle Notizbuecher aller Nutzer werden 
   * mit diesem Report-Typ dargestellt.
   */
  public abstract AlleNotizbuecherAllerNutzerReport erstelleAlleNotizbuecherAllerNutzerReport() 
		  throws IllegalArgumentException;
}