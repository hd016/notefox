package de.hdm.notefox.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notefox.server.db.BerechtigungMapper;
import de.hdm.notefox.server.db.NotizMapper;
import de.hdm.notefox.server.db.NotizbuchMapper;
import de.hdm.notefox.server.db.NutzerMapper;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.Filterobjekt;
import de.hdm.notefox.shared.LoginService;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.NutzerAusnahme;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * <p>
 * Implementierungsklasse des Interface <code>NotizobjektAdministration</code>.
 * Diese Klasse ist <em>die</em> Klasse, die neben {@link ReportGeneratorImpl}
 * sämtliche Applikationslogik (oder engl. Business Logic) aggregiert. Sie ist
 * wie eine Spinne, die saemtliche Zusammenhänge in ihrem Netz (in unserem Fall
 * die Daten der Applikation) überblickt und für einen geordneten Ablauf und
 * dauerhafte Konsistenz der Daten und Abläufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * laesst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgeführt werden, die das System
 * von einem konsistenten Zustand in einen anderen, auch wieder konsistenten
 * Zustand überführen. Wenn dies zwischenzeitig scheitern sollte, dann ist das
 * jeweilige Transaction Script dafür verwantwortlich, eine Fehlerbehandlung
 * durchzuführen.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link NotizobjektAdministration}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfügung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link NotizobjektAdministrationAsync}:
 * <code>NotizobjektVerwaltungImpl</code> und
 * <code>NotizobjektAdministration</code> bilden nur die Server-seitige Sicht
 * der Applikationslogik ab. Diese basiert vollständig auf synchronen
 * Funktionsaufrufen. Wir müssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusaetzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstützt. Weitere Informationen unter
 * {@link NotizobjektAdministrationAsync}.</li>
 * <li>{@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig über GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis für die Anbindung von <code>NotizobjektVerwaltungImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehören der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #loeschenNutzer(Nutzer)} Einzig nachvollziehbares
 * Argument fuer einen solchen Ansatz ist die Steigerung der Performance
 * umfangreicher Datenbankoperationen. Doch auch dieses Argument zieht nur dann,
 * wenn wirklich große Datenmengen zu handhaben sind. In einem solchen Fall
 * würde man jedoch eine entsprechend erweiterte Architektur realisieren, die
 * wiederum sämtliche Applikationslogik in der Applikationsschicht isolieren
 * würde. Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern
 * dies auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass saemtliche Methoden, die mittels GWT RPC aufgerufen werden
 * koennen ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden dürfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions koennen
 * z.B. Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 * <p>
 * Es gibt sicherlich noch viel mehr über diese Klasse zu schreiben. Weitere
 * Infos erhalten Sie in der Lehrveranstaltung.
 * </p>
 * 
 * @see NotizobjektAdministration
 * @see NotizobjektAdministrationAsync
 * @see RemoteServiceServlet
 * @author Thies
 */
@SuppressWarnings("serial")
public class NotizobjektAdministrationImpl extends RemoteServiceServlet implements NotizobjektAdministration {

	private LoginService loginService;

	/**
	 * Referenz auf den DatenbankMapper, der Nutzerobjekte mit der Datenbank
	 * abgleicht.
	 */
	private NutzerMapper nuMapper;

	/**
	 * Referenz auf den DatenbankMapper, der Notizobjekte mit der Datenbank
	 * abgleicht.
	 */
	private NotizMapper noMapper;

	/**
	 * Referenz auf den DatenbankMapper, der Notizbuchobjekte mit der Datenbank
	 * abgleicht.
	 */
	private NotizbuchMapper nbMapper;

	private BerechtigungMapper bMapper;

	/*
	 * Da diese Klasse ein gewisse Größe besitzt - dies ist eigentlich ein
	 * Hinweise, dass hier eine weitere Gliederung sinnvoll ist - haben wir zur
	 * besseren übersicht Abschnittskomentare eingefügt. Sie leiten ein Cluster
	 * in irgeneinerweise zusammengehöriger Methoden ein. Ein entsprechender
	 * Kommentar steht am Ende eines solchen Clusters.
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Initialisierung
	 * *************************************************************************
	 * **
	 */
	/**
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
	 * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines
	 * anderen Konstruktors ist durch die Client-seitige Instantiierung durch
	 * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
	 * möglich.
	 * </p>
	 * <p>
	 * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	 * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	 * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	 * </p>
	 * 
	 * @see #initialisieren()
	 */
	public NotizobjektAdministrationImpl() throws IllegalArgumentException {
		/*
		 * Eine weitergehende Funktion muss der No-Argument-Constructor nicht
		 * haben. Er muss einfach vorhanden sein.
		 */
	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #ReportGeneratorImpl()}. Diese Methode
	 * muss für jede Instanz von <code>NotizobjektVerwaltungImpl</code>
	 * aufgerufen werden.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die NotizobjektAdministration einen
		 * vollstä2ndigen Satz von Mappern besitzt, mit deren Hilfe sie dann mit
		 * der Datenbank kommunizieren kann.
		 */
		this.nuMapper = NutzerMapper.nutzerMapper();
		this.noMapper = NotizMapper.notizMapper();
		this.nbMapper = NotizbuchMapper.notizbuchMapper();
		this.bMapper = BerechtigungMapper.berechtigungMapper();
		this.loginService = LoginServiceImpl.loginService();

	}

	/**
	 * Auslesen aller Berechtigungen der übergebenen Notiz.
	 */
	@Override
	public List<Berechtigung> nachAllenBerechtigungenDerNotizSuchen(Notiz no) throws IllegalArgumentException {
		return this.bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(no);
	}

	/**
	 * Auslesen aller Berechtigungen des übergebenen Notizbuches.
	 */
	@Override
	public List<Berechtigung> nachAllenBerechtigungenDesNotizbuchesSuchen(Notizbuch nb)
			throws IllegalArgumentException {
		return this.bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(nb);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Initialisierung
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden für Nutzer-Objekte
	 * *************************************************************************
	 * **
	 */
	/**
	 * <p>
	 * Anlegen eines neuen Nutzers. Dies fuehrt implizit zu einem Speichern des
	 * neuen Nutzers in der Datenbank.
	 * </p>
	 * 
	 * <p>
	 * <b>HINWEIS:</b> Änderungen an Nutzer-Objekten muessen stets durch Aufruf
	 * von {@link #speichern(Nutzer c)} in die Datenbank transferiert werden.
	 * </p>
	 * 
	 * @see speichern(Nutzer c)
	 */
	@Override
	public Nutzer anlegenNutzer(int nutzerId, String email) throws IllegalArgumentException {
		Nutzer n = new Nutzer();
		n.setNutzerId(nutzerId);
		n.setEmail(email);

		/**
		 * Setzen einer vorläufigen NutzerId Der anlegen-Aufruf liefert dann ein
		 * Objekt, dessen Id mit der Datenbank konsistent ist.
		 */
		n.setNutzerId(1);

		/**
		 * Objekt in der DB speichern.
		 */
		return this.nuMapper.anlegenNutzer(n);
	}

	/**
	 * Auslesen aller Nutzer, die den uebergebenen Namen besitzen.
	 */
	@Override
	public Nutzer nachNutzerEmailSuchen(String email) throws IllegalArgumentException {
		return this.nuMapper.nachNutzerEmailSuchen(email);
	}

	/**
	 * Auslesen eines Nutzers anhand seiner NutzerId.
	 */
	@Override
	public Nutzer nachNutzerIdSuchen(int nutzerId) throws IllegalArgumentException {
		return this.nuMapper.nachNutzerIdSuchen(nutzerId);
	}

	/**
	 * Auslesen aller Nutzer.
	 */
	@Override
	public List<Nutzer> nachAllenNutzernSuchen() throws IllegalArgumentException {
		return this.nuMapper.nachAllenNutzernSuchen();
	}

	/**
	 * Speichern eines Nutzers.
	 */
	@Override
	public Nutzer speichern(Nutzer n) throws IllegalArgumentException {
		return this.nuMapper.update(n);
	}

	/**
	 * Löschen eines Nutzers. Natürlich würde ein reales System zur Verwaltung
	 * von Notizobjektnutzer ein Löschen allein schon aus Gründen der
	 * Dokumentation nicht bieten, sondern deren Status z.B von "aktiv" in
	 * "ehemalig" ändern. Wir wollen hier aber dennoch zu Demonstrationszwecken
	 * eine Löschfunktion vorstellen.
	 */
	@Override
	public void loeschenNutzer(Nutzer n) throws IllegalArgumentException {
		/*
		 * Zunächst werden sämtl. Notizen und Notizbuecher des Nutzers aus der
		 * DB entfernt.
		 * 
		 * Dies wird auf der Ebene der Applikationslogik, konkret in der Klasse
		 * NotizobjektVerwaltungImpl, durchgeführt. Grund: In der Klasse
		 * NotizobjektVerwaltungImpl ist die Verflechtung sämtlicher Klassen
		 * bzw. ihrer Objekte bekannt. Nur hier kann sinnvoll ein umfassender
		 * Verwaltungsakt wie z.B. dieser Löschvorgang realisiert werden.
		 * 
		 * Natuerlich koennte man argumentieren, dass dies auch auf
		 * Datenbankebene (sprich: mit SQL) effizienter möglich ist. Das
		 * Gegenargument ist jedoch eine dramatische Verschlechterung der
		 * Wartbarkeit Ihres Gesamtsystems durch einen zu niedrigen
		 * Abstraktionsgrad und der Verortung von Aufgaben an einer Stelle
		 * (Datenbankschicht), die die zuvor genannte Verflechtung nicht
		 * umfänglich kennen kann.
		 */
		List<Notiz> notizen = this.nachAllenNotizenDesNutzersSuchen(n);
		List<Notizbuch> notizbuecher = this.nachAllenNotizbuechernDesNutzersSuchen(n);

		if (notizen != null) {
			for (Notiz no : notizen) {
				this.loeschenNotiz(no);
			}
		}

		if (notizbuecher != null) {
			for (Notizbuch nb : notizbuecher) {
				this.loeschenNotizbuch(nb);
			}
		}

		// Anschließend den Nutzers entfernen
		this.nuMapper.loeschenNutzer(n);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden für Nutzer-Objekte
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Methoden für Notiz-Objekte
	 * *************************************************************************
	 * **
	 */
	/**
	 * Auslesen saemtlicher Notizen dieses Systems.
	 */
	@Override
	public List<Notiz> nachAllenNotizenSuchen() throws IllegalArgumentException {
		List<Notiz> notizen = this.noMapper.nachAllenNotizenDesNutzerSuchen();
		return berechtigungAnwenden(notizen, Berechtigungsart.LESEN);
	}

	/**
	 * Auslesen aller Notizen des uebergeben Nutzers.
	 */
	@Override
	public List<Notiz> nachAllenNotizenDesNutzersSuchen(Nutzer n) throws IllegalArgumentException {
		return this.noMapper.nachEigentuemerSuchen(n);
	}

	/**
	 * Auslesen der Notiz mit einer bestimmten Id
	 */
	@Override
	public Notiz nachNotizIdSuchen(int id) throws IllegalArgumentException {
		return berechtigungAnwenden(noMapper.nachNotizIdSuchen(id), Berechtigungsart.LESEN);
	}

	/**
	 * Löschen der übergebenen Notiz. Beachten Sie bitte auch die Anmerkungen zu
	 * {@link #loeschenNutzer(Nutzer)}. Beim Löschen der Notiz werden sämtliche
	 * damit in Verbindung stehenden Notizquellen-Objekte und Datum-Objekte
	 * gelöscht.
	 * 
	 * @see #loeschenNutzer(Nutzer)
	 */
	@Override
	public void loeschenNotiz(Notiz no) throws IllegalArgumentException {
		if (berechtigungAnwenden(no, Berechtigungsart.LOESCHEN) != null) {

			/*
			 * Löschen aller Berechtigungen der Notiz
			 */
			List<Berechtigung> alleBerechtigungen = bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(no);
			for (Berechtigung berechtigung : alleBerechtigungen) {
				bMapper.berechtigungVerweigern(berechtigung);
			}

			// Notiz aus der DB entfernen
			this.noMapper.loeschenNotiz(no);
		} else {
			throw new NutzerAusnahme("Keine Berechtigung vorhanden.");
		}
	}

	// Berechtigung Methoden

	public Berechtigung anlegenBerechtigung(Berechtigung berechtigung) {

		if (berechtigung.getBerechtigungsart() == Berechtigungsart.EDITIEREN
				|| berechtigung.getBerechtigungsart() == Berechtigungsart.LOESCHEN) {
			Berechtigung berechtigung2 = new Berechtigung();
			berechtigung2.setBerechtigter(berechtigung.getBerechtigter());
			berechtigung2.setNotiz(berechtigung.getNotiz());
			berechtigung2.setNotizbuch(berechtigung.getNotizbuch());
			berechtigung2.setBerechtigungsart(Berechtigungsart.LESEN);

			anlegenBerechtigung(berechtigung2);
		}

		if (!bMapper.exisitiertBerechtigung(berechtigung)) {
			return bMapper.anlegenBerechtigung(berechtigung);
		} else {
			return berechtigung;
		}
	}

	/**
	 * Externe Webseiten auslesen und in Notiz anlegen
	 */
	public Notiz anlegenNotiz(String url) throws IllegalArgumentException {

		Nutzer currentNutzer = loginService.getCurrentNutzer();
		List<Notizbuch> notizbuecher = nachAllenNotizbuechernDesNutzersSuchen(currentNutzer);
		Notizbuch externeWebseitenNotizbuch = null;
		for (Notizbuch notizbuch : notizbuecher) {
			if (notizbuch.getTitel().equals("Externe Webseiten")) {
				externeWebseitenNotizbuch = notizbuch;
				break;
			}
		}

		if (externeWebseitenNotizbuch == null) {
			externeWebseitenNotizbuch = anlegenNotizbuecherFuer(currentNutzer);
			externeWebseitenNotizbuch.setTitel("Externe Webseiten");
			speichern(externeWebseitenNotizbuch);
		}

		String webseite = leseWebseite(url);
		Notiz notiz = anlegenNotiz(externeWebseitenNotizbuch);
		notiz.setEigentuemer(currentNutzer);
		notiz.setSubtitel(url);
		notiz.setInhalt(webseite);

		// Speicherung von Titel aus der Webseite und speichern in den Notiz
		Pattern p = Pattern.compile("<title>(.*?)</title>");
		Matcher m = p.matcher(webseite);
		if (m.find()) {
			notiz.setTitel((m.group(1)));
		} else {
			notiz.setTitel(url);
		}

		speichern(notiz);

		return notiz;
	}

	private String leseWebseite(String url) {
		Scanner scanner;
		try {
			scanner = new Scanner(new URL(url).openStream());
		} catch (IOException e) {
			return "Kann Webseite nicht lesen!";
		}
		StringBuilder stringBuilder = new StringBuilder();
		while (scanner.hasNextLine()) {
			stringBuilder.append(scanner.nextLine());
		}
		scanner.close();
		return stringBuilder.toString();
	}

	@Override
	public Notiz anlegenNotiz(Notizbuch notizbuch) throws IllegalArgumentException {
		return anlegenNotiz(notizbuch, new Notiz());
	}

	/**
	 * Anlegen einer neuen Notiz. Dies fuehrt implizit zu einem Speichern der
	 * neuen, leeren Notiz in der Datenbank.
	 * <p>
	 * 
	 * <b>HINWEIS:</b> Änderungen an Notiz-Objekten müssen stets durch Aufruf
	 * von {@link #speichern(Notiz)} in die Datenbank transferiert werden.
	 * 
	 * @see speichern(Notiz a)
	 */

	public Notiz anlegenNotiz(Notizbuch notizbuch, Notiz notiz) throws IllegalArgumentException {
		notiz.setEigentuemer(loginService.getCurrentNutzer());
		notiz.setNotizbuch(notizbuch);
		notiz.setFaelligkeitsdatum(new Date());

		if (notiz.getTitel() == null || notiz.getTitel().isEmpty()) {
			notiz.setTitel("unbenannt");
		}

		if (berechtigungAnwenden(notizbuch, Berechtigungsart.EDITIEREN) != null) {

			// Objekt in der DB speichern.
			return this.noMapper.anlegenNotiz(notiz);
		} else {
			return null;
		}
	}

	/**
	 * Speichern einer Notiz.
	 */
	@Override
	public Notiz speichern(Notiz no) throws NutzerAusnahme {
		if (no.getId() > 0) {
			if (berechtigungAnwenden(no, Berechtigungsart.EDITIEREN) != null) {
				return this.noMapper.update(no);
			} else {
				throw new NutzerAusnahme("Keine Berechtigung vorhanden.");
			}
		} else {
			return anlegenNotiz(no.getNotizbuch(), no);
		}
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden für Notiz-Objekte
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * * ABSCHNITT, Beginn: Methoden für Notizobjekt-Objekte
	 * *************************************************************************
	 * **
	 */

	/**
	 * Auslesen sämtlicher Notizbuecher dieses Systems.
	 */
	@Override
	public List<Notizbuch> nachAllenNotizbuechernSuchen() throws IllegalArgumentException {
		List<Notizbuch> notizbuecher = berechtigungAnwenden(nbMapper.nachAllenNotizbuechernSuchen(),
				Berechtigungsart.LESEN);
		for (Notizbuch notizbuch : notizbuecher) {
			notizbuch.setNotizen(berechtigungAnwenden(noMapper.nachAllenNotizenDesNotizbuchesSuchen(notizbuch.getId()),
					Berechtigungsart.LESEN));
		}
		return notizbuecher;
	}

	/**
	 * Auslesen aller Notizbuecher des übergeben Nutzers.
	 */
	@Override
	public List<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer n) throws IllegalArgumentException {
		List<Notizbuch> notizbuecher = this.nbMapper.nachEigentuemerSuchen(n);
		for (Notizbuch notizbuch : notizbuecher) {
			notizbuch.setNotizen(berechtigungAnwenden(noMapper.nachAllenNotizenDesNotizbuchesSuchen(notizbuch.getId()),
					Berechtigungsart.LESEN));
		}
		return notizbuecher;
	}

	/**
	 * Auslesen des Notizbuches mit einer bestimmten Id.
	 */
	@Override
	public Notizbuch nachNotizbuchIdSuchen(int id) throws IllegalArgumentException {
		return berechtigungAnwenden(nbMapper.nachNotizbuchIdSuchen(id), Berechtigungsart.LESEN);
	}

	/**
	 * Loeschen des übergebenen Notizbuches. Beachten Sie bitte auch die
	 * Anmerkungen zu {@link #loeschenNutzer(Nutzer)}. Beim Löschen des
	 * Notizbuches werden sämtliche damit in Verbindung stehenden Notizen
	 * geleoscht.
	 * 
	 * @see #loeschenNutzer(Nutzer)
	 */
	@Override
	public void loeschenNotizbuch(Notizbuch nb) throws NutzerAusnahme {
		if (berechtigungAnwenden(nb, Berechtigungsart.LOESCHEN) != null) {

			/*
			 * Löschen aller Berechtigungen des Notizbuches
			 */
			List<Berechtigung> alleBerechtigungen = bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(nb);
			for (Berechtigung berechtigung : alleBerechtigungen) {
				bMapper.berechtigungVerweigern(berechtigung);
			}

			/*
			 * Zunnachst werden saemtl. Notizen des Nutzers aus der DB entfernt.
			 */
			List<Notiz> notizen = this.nachAllenNotizenDesNotizbuchesSuchen(nb);

			if (notizen != null) {
				for (Notiz no : notizen) {
					this.loeschenNotiz(no);
				}
			}

			// Notizbuch aus der DB entfernen
			this.nbMapper.loeschenNotizbuch(nb);
		} else {
			throw new NutzerAusnahme("Keine Berechtigungen vorhanden.");
		}
	}

	@Override
	public Notizbuch anlegenNotizbuecherFuer(Nutzer n) throws IllegalArgumentException {
		return anlegenNotizbuecherFuer(n, new Notizbuch());
	}

	/**
	 * Anlegen eines neuen Notizbuches für den übergebenen Nutzers. Dies führt
	 * implizit zu einem Speichern des neuen, leeren Notizbuches in der
	 * Datenbank.
	 * <p>
	 * 
	 * <b>HINWEIS:</b> Änderungen an Notizbuch-Objekten müssen stets durch
	 * Aufruf von {@link #speichern(Notizbuch)} in die Datenbank transferiert
	 * werden.
	 * 
	 * @see speichern(Notizbuch a)
	 */
	public Notizbuch anlegenNotizbuecherFuer(Nutzer n, Notizbuch nb) throws IllegalArgumentException {
		nb.setEigentuemer(n);

		if (nb.getTitel() == null || nb.getTitel().isEmpty()) {
			nb.setTitel("unbenannt");
		}

		/**
		 * Setzen einer vorläufigen NotizbuchId. Der anlegenNotizbuch-Aufruf
		 * liefert dann ein Objekt, dessen Id mit der Datenbank konsistent ist.
		 */
		nb.setId(1);

		/** Objekt in der DB speichern. */

		return this.nbMapper.anlegenNotizbuch(nb);
	}

	/**
	 * <p>
	 * Auslesen saemtlicher mit diesem Nutzer in Verbindung stehenden Notizen.
	 * 
	 * @param k
	 *            der Nutzer, dessen Notizquellen wir bekommen wollen.
	 * @return eine Liste aller Notizquellen
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Notiz> nachAllenNotizenDesNotizbuchesSuchen(Notizbuch nb) throws IllegalArgumentException {
		return berechtigungAnwenden(noMapper.nachAllenNotizenDesNotizbuchesSuchen(nb.getId()), Berechtigungsart.LESEN);
	}

	/**
	 * Speichern eines Notizbuches.
	 */
	@Override
	public Notizbuch speichern(Notizbuch nb) throws NutzerAusnahme {
		if (nb.getId() > 0) {
			if (berechtigungAnwenden(nb, Berechtigungsart.EDITIEREN) != null) {
				return this.nbMapper.update(nb);
			} else {
				throw new NutzerAusnahme("Keine Berechtigung vorhanden");
			}
		} else {
			return anlegenNotizbuecherFuer(nb.getEigentuemer(), nb);
		}
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Methoden fuer Notizbuch-Objekts
	 * *************************************************************************
	 * **
	 */

	private <T extends Notizobjekt> T berechtigungAnwenden(T notizobjekt, Berechtigungsart berechtigungsart) {
		if (notizobjekt == null) {
			return null;
		}

		Nutzer aktuellerNutzer = loginService.getCurrentNutzer();
		List<Berechtigung> berechtigungen = bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(notizobjekt,
				aktuellerNutzer);

		if (aktuellerNutzer == null) {
			return null;
		}

		if (pruefeBerechtigung(berechtigungen, aktuellerNutzer, notizobjekt, berechtigungsart)) {
			return notizobjekt;

		} else {
			return null;
		}
	}

	private <T extends Notizobjekt> List<T> berechtigungAnwenden(List<T> notizobjekte,
			Berechtigungsart berechtigungsart) {
		Nutzer aktuellerNutzer = loginService.getCurrentNutzer();

		if (aktuellerNutzer == null) {
			return null;
		}

		List<T> ergebnis = new ArrayList<>();
		for (T notizobjekt : notizobjekte) {
			List<Berechtigung> berechtigungen = bMapper.nachAllenBerechtigungenDerNotizobjekteSuchen(notizobjekt,
					aktuellerNutzer);
			if (pruefeBerechtigung(berechtigungen, aktuellerNutzer, notizobjekt, berechtigungsart)) {
				ergebnis.add(notizobjekt);
			}
		}
		return ergebnis;
	}

	private boolean pruefeBerechtigung(List<Berechtigung> berechtigungen, Nutzer aktuellerNutzer,
			Notizobjekt notizobjekt, Berechtigungsart berechtigungsart) {

		if (notizobjekt.getId() > 0) {
			if (notizobjekt instanceof Notiz) {
				Notiz notiz = (Notiz) notizobjekt;
				Notiz notizAusDB = noMapper.nachNotizIdSuchen(notiz.getId());
				if (notizAusDB.getEigentuemer().equals(aktuellerNutzer)) {
					return true;
				}
			} else if (notizobjekt instanceof Notizbuch) {
				Notizbuch notizbuch = (Notizbuch) notizobjekt;
				Notizbuch notizbuchAusDB = nbMapper.nachNotizbuchIdSuchen(notizbuch.getId());
				if (notizbuchAusDB.getEigentuemer().equals(aktuellerNutzer)) {
					return true;
				}
			}
		}

		for (Berechtigung berechtigung : berechtigungen) {
			if (berechtigung.getBerechtigungsart() == berechtigungsart) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Berechtigung loeschenBerechtigung(Berechtigung be) {

		bMapper.berechtigungVerweigern(be);
		return be;
	}

	@Override
	public List<Notiz> nachNotizenDesFilterSuchen(Filterobjekt filter) {
		return noMapper.nachNotizenDesFilterSuchen(filter);
	}

}
