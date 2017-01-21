package de.hdm.notefox.client;

import java.util.logging.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.notefox.shared.CommonSettings;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.ReportGenerator;
import de.hdm.notefox.shared.ReportGeneratorAsync;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt) Klasse mit Eigenschaften
 * und Diensten, die fÃ¼r alle Client-seitigen Klassen relevant sind.
 * 
 * @author thies
 * @version 1.0
 * @since 28.02.2012
 * 
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>BankAdministration</code>.
	 */

	private static NotizobjektAdministrationAsync notizobjektVerwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>ReportGenerator</code>.
	 */
	private static ReportGeneratorAsync reportGenerator = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "notefox Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ClientSideSettings.getLogger();
	 * </pre>
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * <pre>
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * </pre>
	 * 
	 * oder
	 * 
	 * <pre>
	 * logger.info(&quot;Lege neuen Kunden an.&quot;);
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! Severe und info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfügen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen künftig
	 * sämtliches Logging im Code und können ohne abermaliges Kompilieren den
	 * Log Level "von außen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmäßig vorgegebene Log Level ist <code>WARN</code>. Dies
	 * würde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl aber
	 * <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn Sie
	 * also auch Log des Levels <code>INFO</code> wollten, müssten Sie in dieser
	 * Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen BankAdministration.
	 * Diese Methode erstellt die BankAdministration, sofern sie noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>BankAdministrationAsync bankVerwaltung = ClientSideSettings.getBankVerwaltung()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>BankAdministrationAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static NotizobjektAdministrationAsync getNotizobjektVerwaltung() {
		// Gab es bislang noch keine BankAdministration-Instanz, dann...
		if (notizobjektVerwaltung == null) {
			// ZunÃ¤chst instantiieren wir BankAdministration
			notizobjektVerwaltung = GWT.create(NotizobjektAdministration.class);
		}

		// So, nun brauchen wir die BankAdministration nur noch zurÃ¼ckzugeben.
		return notizobjektVerwaltung;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>ReportGeneratorAsync reportGenerator = ClientSideSettings.getReportGenerator()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>BankAdministrationAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static ReportGeneratorAsync getReportGenerator() {
		// Gab es bislang noch keine ReportGenerator-Instanz, dann...
		if (reportGenerator == null) {
			// ZunÃ¤chst instantiieren wir ReportGenerator
			reportGenerator = GWT.create(ReportGenerator.class);

			final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportGenerator.initialisieren(initReportGeneratorCallback);
		}

		// So, nun brauchen wir den ReportGenerator nur noch zurÃ¼ckzugeben.
		return reportGenerator;
	}

}
