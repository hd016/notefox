package de.hdm.notefox.server;

import java.util.logging.Logger;

import de.hdm.notefox.shared.CommonSettings;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * <p>
 * Klasse mit Eigenschaften und Diensten, die fuer alle Server-seitigen Klassen
 * relevant sind.
 * </p>
 * <p>
 * In ihrem aktuellen Entwicklungsstand bietet die Klasse eine rudimentaere
 * Unterstuetzung der Logging-Funkionalität unter Java. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * <code>ServerSideSettings.getLogger()</code> genutzt werden kann.
 * </p>
 * 
 * @author thies
 * @version 1.0
 * @since 28.02.2012
 * 
 */
public class ServersideSettings extends CommonSettings {
	private static final String LOGGER_NAME = "ITProjekt Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Server-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ServerSideSettings.getLogger();
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
	 * logger.info(&quot;Lege neuen Nutzer an.&quot;);
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! <em>severe</em> und
	 * <em>info</em> sind nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfuegen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen kuenftig
	 * saemtliches Logging im Code und koennen ohne abermaliges Kompilieren den
	 * Log Level "von aussen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in dem <code>war/WEB-INF</code>-Ordner
	 * Ihres Projekts. Der dort standardmaessig vorgegebene Log Level ist
	 * <code>WARN</code>. Dies wuerde bedeuten, dass Sie keine <code>INFO</code>
	 * -Meldungen wohl aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen
	 * erhielten. Wenn Sie also auch Log des Levels <code>INFO</code> wollten,
	 * muessten Sie in dieser Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz fuer die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

}
