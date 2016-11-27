package de.hdm.notefox.client;

import com.google.gwt.core.client.GWT;

import de.hdm.notefox.shared.CommonSettings;
//import de.hdm.notefox.shared.LoginService;
//import de.hdm.notefox.shared.LoginServiceAsync;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.ReportGenerator;
import de.hdm.notefox.shared.ReportGeneratorAsync;

/**
 * Klasse mit Eigenschaften und Diensten, die fuer alle Client-seitigen Klassen
 * relevant sind
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst von NoteFox.
	 */
	private static NotizobjektAdministrationAsync NotizobjektAdministration = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens ReportGenerator.
	 */
	private static ReportGeneratorAsync reportGenerator = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens LoginService.
	 */
	
	//private static LoginServiceAsync loginService = null;
	

	/**

	 * 
	 * @return NotizobjektAdministrationAsync
	 */
	public static NotizobjektAdministrationAsync getNotizobjektAdministrationAsync() {

		if (NotizobjektAdministration == null) {
			NotizobjektAdministration = GWT.create(NotizobjektAdministration.class);
		}
		return NotizobjektAdministration;
	}

	/**
	 * 
	 * @return Instanz des Typs ReportGeneratorAsync
	 */
	public static ReportGeneratorAsync getReportGenerator() {

		if (reportGenerator == null) {
			reportGenerator = GWT.create(ReportGenerator.class);
		}
		return reportGenerator;

	}
	
	/**
	 *
	 * 
	 * @return Instanz des Typs LoginServiceAsync
	 */
	
	/*public static LoginServiceAsync getLoginService() {

		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
*/
	}
	

