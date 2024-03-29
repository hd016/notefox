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
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt). Die ClientsideSettings
 * Klasse beinhaltet Eigenschaften und Dienste, die fuer alle Client-seitigen
 * Klassen relevant sind.
 * 
 * @author Thies
 * @version 1.0
 * @since 28.02.2012
 * 
 * @author Neriman Kocak und Harun Dalici
 * 
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen
	 * Dienst, namens <code>NotizobjektAdministrationAsync</code>.
	 */

	private static NotizobjektAdministrationAsync notizobjektVerwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen
	 * Dienst, namens <code>ReportGeneratorAsync</code>.
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
	 * Bitte auf <em>angemessene Log Levels</em> achten! Server und Info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfuegen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen kuenftig
	 * sämtliches Logging im Code und können ohne abermaliges Kompilieren den
	 * Log Level "von aussen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmäßig vorgegebene Log Level ist <code>WARN</code>. Dies
	 * wuerde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl aber
	 * <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn Sie
	 * also auch Log des Levels <code>INFO</code> wollten, muessten Sie in
	 * dieser Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz fuer die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen
	 * NotizobjektAdministrationAsync. Diese Methode erstellt die
	 * NotizobjektAdministrationAsync, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurueckgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client.
	 * 
	 * @return eindeutige Instanz des Typs
	 *         <code>NotizobjektAdministrationAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static NotizobjektAdministrationAsync getNotizobjektVerwaltung() {
		/**
		 * Gab es bislang noch keine NotizobjektVerwaltung-Instanz, dann...
		 */

		if (notizobjektVerwaltung == null) {

			/**
			 * Zunächst instantiieren wir NotizobjektVerwaltung
			 */

			notizobjektVerwaltung = GWT.create(NotizobjektAdministration.class);
		}

		/**
		 * So, nun brauchen wir die NotizobjektVerwaltung nur noch
		 * zurückzugeben.
		 */

		return notizobjektVerwaltung;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurueckgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client
	 * 
	 * @return eindeutige Instanz des Typs <code>ReportGeneratorAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static ReportGeneratorAsync getReportGenerator() {
		/**
		 * Gab es bislang noch keine ReportGenerator-Instanz, dann...
		 */

		if (reportGenerator == null) {

			/**
			 * ZunÃ¤chst instantiieren wir den ReportGenerator
			 */

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

		/**
		 * So, nun brauchen wir den ReportGenerator nur noch zurueckzugeben.
		 */
		return reportGenerator;
	}

}
