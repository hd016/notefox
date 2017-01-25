package de.hdm.notefox.server.report;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notefox.server.NotizobjektAdministrationImpl;
import de.hdm.notefox.shared.bo.*;
import de.hdm.notefox.shared.report.*;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Filterobjekt;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.ReportGenerator;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt) Der
 * ReportGenerator-Interface wird implementiert.
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * Fuer den Report Generator ist der Zugriff auf die
	 * NotizobjektAdministration notwendig, da diese die essentiellen Methoden
	 * fuer die Koexistenz von Datenobjekten bietet.
	 */

	private NotizobjektAdministration administration = null;

	/**
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
	 * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines
	 * anderen Konstruktors ist durch die Client-seitige Instantiierung durch
	 * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
	 * moeglich.
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
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	public void initialisieren() throws IllegalArgumentException {
		/**
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine NotizobjektVerwaltungImpl-Instanz.
		 */
		NotizobjektAdministrationImpl a = new NotizobjektAdministrationImpl();
		a.init();
		this.administration = a;
	}

	/**
	 * Auslesen der zugehoerigen NotizobjektAdministration (interner Gebrauch).
	 * 
	 * @return das NotizobjektVerwaltungsobjekt
	 */
	protected NotizobjektAdministration getNotizobjektVerwaltung() {
		return this.administration;
	}

	/**
	 * Hinzufuegen des Report-Impressums. Diese Methode ist aus den
	 * <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
	 * diese Taetigkeiten redundant auszufuehren haette. Stattdessen rufen die
	 * <code>create...</code>-Methoden diese Methode auf.
	 * 
	 * @param r
	 *            der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {

		/*
		 * Das Impressum soll mehrzeilig sein.
		 */
		CompositeParagraph imprint = new CompositeParagraph();

		imprint.addSubParagraph(new SimpleParagraph("Notefox Team"));

		// Das eigentliche Hinzufügen des Impressums zum Report.
		r.setImprint(imprint);

	}

	/**
	 * @see NotizobjektAdministrationImpl#nachAllenNutzernSuchen()
	 */
	public List<Nutzer> nachAllenNutzernSuchen() throws IllegalArgumentException {
		return administration.nachAllenNutzernSuchen();
	}

	/**
	 * Erstellen von <code>AlleNotizenDesNutzersReport</code>-Objekten.
	 * 
	 * @param n
	 *            das Nutzerobjekt bzgl. dessen der Report erstellt werden soll.
	 * @return der fertige Report
	 */
	public AlleNotizenDesNutzersReport erstelleAlleNotizenDesNutzersReport(Nutzer n) throws IllegalArgumentException {

		if (this.getNotizobjektVerwaltung() == null)
			return null;

		/*
		 * Zunaechst legen wir uns einen leeren Report an.
		 */
		AlleNotizenDesNutzersReport result = new AlleNotizenDesNutzersReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle Notizen aller Nutzer");

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Email des Nutzers aufnehmen
		header.addSubParagraph(new SimpleParagraph(n.getEmail()));

		// Hinzufuegen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von Notiz-Informationen.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Notiz-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit einer Spalte in der Tabelle erzeugen. In der
		 * Spalte schreiben wir die jeweilige NutzerId. In der Kopfzeile legen
		 * wir also entsprechende Ueberschriften ab.
		 */

		headline.addColumn(new Column("Titel"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Faelligkeitsdatum"));
		headline.addColumn(new Column("Berechtigungen"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);

		/*
		 * Nun werden saemtliche Notizen des Nutzers ausgelesen und deren
		 * NotizId und sukzessive in die Tabelle eingetragen.
		 */
		List<Notiz> notizen = this.administration.nachAllenNotizenDesNutzersSuchen(n);

		for (Notiz no : notizen) {
			// Eine leere Zeile anlegen.
			Row notizRow = new Row();

			// Erste Spalte: Kontonummer hinzufuegen
			notizRow.addColumn(new Column(no.getTitel()));
			notizRow.addColumn(new Column(String.valueOf(no.getErstelldatum())));
			notizRow.addColumn(new Column(String.valueOf(no.getModifikationsdatum())));
			notizRow.addColumn(new Column(String.valueOf(no.getFaelligkeitsdatum())));

			String berechtigungsTabelle = "Nutzer / Berechtigungsart";
			List<Berechtigung> berechtigungen = this.administration.nachAllenBerechtigungenDerNotizSuchen(no);
			for (Berechtigung be : berechtigungen) {
				berechtigungsTabelle += "\n " + be.getBerechtigter().getEmail() + " / " + be.getBerechtigungsart();
			}
			notizRow.addColumn(new Column(berechtigungsTabelle));

			// und schliesslich die Zeile dem Report hinzufuegen.
			result.addRow(notizRow);
		}

		/*
		 * Zum Schluss muessen wir noch den fertigen Report zurueckgeben.
		 */
		return result;
	}

	public AlleNotizenDesNutzersReport erstelleGefilterteNotizenReport(Filterobjekt f) throws IllegalArgumentException {

		if (this.getNotizobjektVerwaltung() == null)
			return null;

		/*
		 * Zunaechst legen wir uns einen leeren Report an.
		 */
		AlleNotizenDesNutzersReport result = new AlleNotizenDesNutzersReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle Notizen aller Nutzer");

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Email des Nutzers aufnehmen
		header.addSubParagraph(new SimpleParagraph(""));

		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von Notiz-Informationen.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Notiz-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit einer Spalte in der Tabelle erzeugen. In der
		 * Spalte schreiben wir die jeweilige NutzerId. In der Kopfzeile legen
		 * wir also entsprechende Ueberschriften ab.
		 */
		headline.addColumn(new Column("Titel"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Faelligkeitsdatum"));
		headline.addColumn(new Column("Berechtigungen"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);

		/*
		 * Nun werden saemtliche Notizen des Nutzers ausgelesen und deren
		 * NotizId und sukzessive in die Tabelle eingetragen.
		 */
		List<Notiz> notizen = this.administration.nachNotizenDesFilterSuchen(f);

		for (Notiz no : notizen) {
			// Eine leere Zeile anlegen.
			Row notizRow = new Row();

			// Erste Spalte: Kontonummer hinzufuegen
			notizRow.addColumn(new Column(no.getTitel()));
			notizRow.addColumn(new Column(String.valueOf(no.getErstelldatum())));
			notizRow.addColumn(new Column(String.valueOf(no.getModifikationsdatum())));
			notizRow.addColumn(new Column(String.valueOf(no.getFaelligkeitsdatum())));

			String berechtigungsTabelle = "Nutzer / Berechtigungsart";
			List<Berechtigung> berechtigungen = this.administration.nachAllenBerechtigungenDerNotizSuchen(no);
			for (Berechtigung be : berechtigungen) {
				berechtigungsTabelle += "\n " + be.getBerechtigter().getEmail() + " / " + be.getBerechtigungsart();
			}
			notizRow.addColumn(new Column(berechtigungsTabelle));

			// und schliesslich die Zeile dem Report hinzufuegen.
			result.addRow(notizRow);
		}

		/*
		 * Zum Schluss muessen wir noch den fertigen Report zurueckgeben.
		 */
		return result;
	}

	/**
	 * Erstellen von <code>AlleNotizenAllerNutzerReport</code>-Objekten.
	 * 
	 * @return der fertige Report
	 */
	public AlleNotizenAllerNutzerReport erstelleAlleNotizenAllerNutzerReport() throws IllegalArgumentException {

		if (this.getNotizobjektVerwaltung() == null)
			return null;

		/*
		 * Zunaechst legen wir uns einen leeren Report an.
		 */
		AlleNotizenAllerNutzerReport result = new AlleNotizenAllerNutzerReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle Notizen aller Nutzer");

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Da AlleNotizenAllerNutzerReport-Objekte aus einer Sammlung von
		 * AlleNotizenDesNutzersReport-Objekten besteht, benoetigen wir keine
		 * Kopfdaten fuer diesen Report-Typ. Wir geben einfach keine Kopfdaten
		 * an...
		 */

		/*
		 * Nun muessen saemtliche Nutzer-Objekte ausgelesen werden.
		 * Anschliessend wir fuer jedes Nutzerobjekt n ein Aufruf von
		 * erstelleAlleNotizenDesNutzersReport(n) durchgefuehrt und somit
		 * jeweils ein AlleNotizenDesNutzersReport-Objekt erzeugt. Diese Objekte
		 * werden sukzessive der result-Variable hinzugefuegt. Sie ist vom Typ
		 * AlleNotizenAllerNutzerReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */
		List<Nutzer> alleNutzer = this.administration.nachAllenNutzernSuchen();

		for (Nutzer n : alleNutzer) {
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufuegen zum Gesamt-Report.
			 */
			result.addSubReport(this.erstelleAlleNotizenDesNutzersReport(n));
		}

		/*
		 * Zu guter Letzt muessen wir noch den fertigen Report zurueckgeben.
		 */
		return result;
	}

	/**
	 * Erstellen von <code>AlleNotizbuecherDesNutzersReport</code>-Objekten.
	 * 
	 * @param n
	 *            das Nutzerobjekt bzgl. dessen der Report erstellt werden soll.
	 * @return der fertige Report
	 */
	public AlleNotizbuecherDesNutzersReport erstelleAlleNotizbuecherDesNutzersReport(Nutzer n)
			throws IllegalArgumentException {

		if (this.getNotizobjektVerwaltung() == null)
			return null;

		/*
		 * Zunaechst legen wir uns einen leeren Report an.
		 */
		AlleNotizbuecherDesNutzersReport result = new AlleNotizbuecherDesNutzersReport();

		result.setTitle("Alle Notizbücher des Nutzers");

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Email des Nutzers aufnehmen
		header.addSubParagraph(new SimpleParagraph(n.getEmail()));

		// Imressum hinzufuegen
		this.addImprint(result);

		// Hinzufuegen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von
		 * Notizbuch-Informationen.
		 */

		/**
		 * Zunaechst legen wir eine Kopfzeile fuer die Notizbuch-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit eine Spalte in der Tabelle erzeugen. In der
		 * Spalte schreiben wir die jeweilige NutzerId. In der K
		 * 
		 * Kopfzeile legen wir also entsprechende Ueberschriften ab.
		 */
		headline.addColumn(new Column("Titel"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Berechtigungen"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);

		/*
		 * Alle Notizbuecher des Nutzers auslesen und sukzessives Eintragen der
		 * NotizbuchId in die Tabelle.
		 */
		List<Notizbuch> notizbuecher = this.administration.nachAllenNotizbuechernDesNutzersSuchen(n);

		for (Notizbuch nb : notizbuecher) {
			// Leere Zeile
			Row notizbuchRow = new Row();

			// Erste Spalte: NotizbuchId hinzufuegen.
			notizbuchRow.addColumn(new Column(nb.getTitel()));
			notizbuchRow.addColumn(new Column(String.valueOf(nb.getErstelldatum())));
			notizbuchRow.addColumn(new Column(String.valueOf(nb.getModifikationsdatum())));

			String berechtigungsTabelle = "Nutzer / Berechtigungsart";
			List<Berechtigung> berechtigungen = this.administration.nachAllenBerechtigungenDesNotizbuchesSuchen(nb);
			for (Berechtigung be : berechtigungen) {
				berechtigungsTabelle += "\n " + be.getBerechtigter().getEmail() + " / " + be.getBerechtigungsart();
			}
			notizbuchRow.addColumn(new Column(berechtigungsTabelle));

			// Dem Report die Zeile hinzufuegen
			result.addRow(notizbuchRow);
		}

		/*
		 * Report zurueckgeben
		 */
		return result;
	}

	/**
	 * <code>AlleNotizbuecherAllerNutzerReport</code>-Objekte werden erstellt.
	 * 
	 * @return fertige Report
	 */
	public AlleNotizbuecherAllerNutzerReport erstelleAlleNotizbuecherAllerNutzerReport()
			throws IllegalArgumentException {

		if (this.getNotizobjektVerwaltung() == null)
			return null;

		/**
		 * leeren Report anlegen
		 */
		AlleNotizbuecherAllerNutzerReport result = new AlleNotizbuecherAllerNutzerReport();

		// Reporttitel.
		result.setTitle("Alle Notizbuecher aller Nutzer");

		// Hinzufuegen des Impressums
		this.addImprint(result);

		/**
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Da AlleNotizbuecherAllerNutzerReport-Objekte aus einer Sammlung von
		 * AlleNotizbuecherDesNutzersReport-Objekten besteht, benoetigen wir
		 * keine Kopfdaten fuer diesen Report-Typ. Wir geben einfach keine
		 * Kopfdaten an...
		 */

		/*
		 * Nun muessen saemtliche Nutzer-Objekte ausgelesen werden.
		 * Anschliessend wir fuer jedes Nutzerobjekt n ein Aufruf von
		 * erstelleAlleNotizbuecherDesNutzersReport(c) durchgefuehrt und somit
		 * jeweils ein AlleNotizbuecherDesNutzersReport-Objekt erzeugt. Diese
		 * Objekte werden sukzessive der result-Variable hinzugefuegt. Sie ist
		 * vom Typ AlleNotizbuecherAllerNutzersReport, welches eine Subklasse
		 * von CompositeReport ist.
		 */
		List<Nutzer> alleNutzer = this.administration.nachAllenNutzernSuchen();

		for (Nutzer n : alleNutzer) {
			/*
			 * Erstellen des Teil-Reports und hizufuegen zum Gesamt-Report
			 */
			result.addSubReport(this.erstelleAlleNotizbuecherDesNutzersReport(n));
		}

		/*
		 * Report zurückgeben
		 */
		return result;
	}

}
