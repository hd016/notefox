package de.hdm.notefox.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.bo.Notizobjekt;
import de.hdm.notefox.shared.report.AlleNotizbuecherAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizbuecherDesNutzersReport;
import de.hdm.notefox.shared.report.AlleNotizenAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizenDesNutzersReport;


/**
 *Semiautomatisch vom Google Plugin erstellt. 
 *Gegenstück des asynchronen Interface (ReportGenerator).
 */
public interface ReportGeneratorAsync {
	
  void erstelleAlleNotizenAllerNutzerReport(Nutzer c,
      AsyncCallback<AlleNotizenAllerNutzerReport> callback);
		  
  void erstelleAlleNotizbuecherAllerNutzerReport(Nutzer c,
	  AsyncCallback<AlleNotizbuecherAllerNutzerReport> callback);

  void erstelleAlleNotizenDesNutzersReport(
      AsyncCallback<AlleNotizenDesNutzersReport> callback);
  
  void erstelleAlleNotizbuecherDesNutzersReport(
	      AsyncCallback<AlleNotizbuecherDesNutzersReport> callback);

  void initialisieren(AsyncCallback<Void> callback);

  void setNotizobjekt(Notizobjekt b, AsyncCallback<Void> callback);

}
