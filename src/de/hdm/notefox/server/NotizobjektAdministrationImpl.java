package de.hdm.notefox.server;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notefox.server.db.DatumMapper;
import de.hdm.notefox.server.db.NotizMapper;
import de.hdm.notefox.server.db.NotizbuchMapper;
import de.hdm.notefox.server.db.NotizquelleMapper;
import de.hdm.notefox.server.db.NutzerMapper;
import de.hdm.notefox.server.report.ReportGeneratorImpl;
import de.hdm.notefox.shared.Datum;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Notizquelle;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt) 
 * <p>
 * Implementierungsklasse des Interface <code>NotizobjektAdministration</code>. Diese
 * Klasse ist <em>die</em> Klasse, die neben {@link ReportGeneratorImpl}
 * sämtliche Applikationslogik (oder engl. Business Logic) aggregiert. Sie ist
 * wie eine Spinne, die sämtliche Zusammenhänge in ihrem Netz (in unserem Fall
 * die Daten der Applikation) überblickt und für einen geordneten Ablauf und
 * dauerhafte Konsistenz der Daten und Abläufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * lässt schon vermuten, dass hier analog zu Datenbanktransaktion pro
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
 * <li>{@link NotizobjektAdministrationAsync}: <code>NotizobjektVerwaltungImpl</code> und
 * <code>NotizobjektAdministration</code> bilden nur die Server-seitige Sicht der
 * Applikationslogik ab. Diese basiert vollständig auf synchronen
 * Funktionsaufrufen. Wir müssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusätzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstützt. Weitere Informationen unter
 * {@link NotizobjektAdministrationAsync}.</li>
 * <li> {@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig über GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis für die Anbindung von <code>NotizobjektVerwaltungImpl</code> an die Runtime
 * des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehören der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #loeschenNutzer(Nutzer)} Einzig nachvollziehbares
 * Argument für einen solchen Ansatz ist die Steigerung der Performance
 * umfangreicher Datenbankoperationen. Doch auch dieses Argument zieht nur dann,
 * wenn wirklich große Datenmengen zu handhaben sind. In einem solchen Fall
 * würde man jedoch eine entsprechend erweiterte Architektur realisieren, die
 * wiederum sämtliche Applikationslogik in der Applikationsschicht isolieren
 * würde. Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern
 * dies auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass sämtliche Methoden, die mittels GWT RPC aufgerufen werden
 * können ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden dürfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions können z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
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
public class NotizobjektAdministrationImpl extends RemoteServiceServlet
    implements NotizobjektAdministration {

  /**
   * Referenz auf das zugehörige Notizobjekt-Objekt.
   */
  private Notizobjekt notizobjekt = null;

  /**
   * Referenz auf den DatenbankMapper, der Nutzerobjekte mit der Datenbank
   * abgleicht.
   */
  private NutzerMapper nuMapper = null; 

  /**
   * Referenz auf den DatenbankMapper, der Notizobjekte mit der Datenbank
   * abgleicht.
   */
  private NotizMapper noMapper = null;
  
  /**
   * Referenz auf den DatenbankMapper, der Notizbuchobjekte mit der Datenbank
   * abgleicht.
   */
  private NotizbuchMapper nbMapper = null;
  
  /**
   * Referenz auf den NotizquelleMapper, der Notizquelleobjekte mit der Datenbank
   * abgleicht.
   */
  private NotizquelleMapper nqMapper = null;
  
  /**
   * Referenz auf den DatumMapper, der Datumobjekte mit der Datenbank
   * abgleicht.
   */
  private DatumMapper dMapper = null;


  /*
   * Da diese Klasse ein gewisse Größe besitzt - dies ist eigentlich ein
   * Hinweise, dass hier eine weitere Gliederung sinnvoll ist - haben wir zur
   * besseren Übersicht Abschnittskomentare eingefügt. Sie leiten ein Cluster in
   * irgeneinerweise zusammengehöriger Methoden ein. Ein entsprechender
   * Kommentar steht am Ende eines solchen Clusters.
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Initialisierung
   * ***************************************************************************
   */
  /**
   * <p>
   * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
   * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
   * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch
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
     * Eine weitergehende Funktion muss der No-Argument-Constructor nicht haben.
     * Er muss einfach vorhanden sein.
     */
  }

  /**
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor
   * {@link #ReportGeneratorImpl()}. Diese Methode muss für jede Instanz von
   * <code>NotizobjektVerwaltungImpl</code> aufgerufen werden.
   * 
   * @see #ReportGeneratorImpl()
   */
  @Override
public void initialisieren() throws IllegalArgumentException {
    /*
     * Ganz wesentlich ist, dass die NotizobjektAdministration einen vollständigen Satz
     * von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
     * kommunizieren kann.
     */
    this.nuMapper = NutzerMapper.nutzerMapper();
    this.noMapper = NotizMapper.notizMapper();
    this.nbMapper = NotizbuchMapper.notizbuchMapper();
  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Initialisierung
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Nutzer-Objekte
   * ***************************************************************************
   */
  /**
   * <p>
   * Anlegen eines neuen Nutzers. Dies führt implizit zu einem Speichern des
   * neuen Nutzers in der Datenbank.
   * </p>
   * 
   * <p>
   * <b>HINWEIS:</b> Änderungen an Nutzer-Objekten müssen stets durch Aufruf
   * von {@link #speichern(Nutzer c)} in die Datenbank transferiert werden.
   * </p>
   * 
   * @see speichern(Nutzer c)
   */
  @Override
public Nutzer anlegenNutzer(int nutzerId, String name)
	      throws IllegalArgumentException {
    Nutzer n = new Nutzer();
    n.setNutzerId(nutzerId);
    n.setName(name);

    /*
     * Setzen einer vorläufigen NutzerId Der anlegen-Aufruf liefert dann ein
     * Objekt, dessen Id mit der Datenbank konsistent ist.
     */
    n.setNutzerId(1);

    // Objekt in der DB speichern.
    return this.nuMapper.anlegenNutzer(n);
  }

  /**
   * Auslesen aller Nutzer, die den übergebenen Namen besitzen.
   */
  @Override
public Vector<Nutzer> nachNutzerNamenSuchen(String name)
      throws IllegalArgumentException {

    return this.nuMapper.nachNutzerNamenSuchen(name);
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
public Vector<Nutzer> nachAllenNutzernSuchen() throws IllegalArgumentException {
    return this.nuMapper.nachAllenNutzernSuchen();
  }

  /**
   * Speichern eines Nutzers.
   */
  @Override
public void speichern(Nutzer n) throws IllegalArgumentException {
    nuMapper.update(n);
  }

  /**
   * Löschen eines Nutzers. Natürlich würde ein reales System zur Verwaltung von
   * Notizobjektnutzer ein Löschen allein schon aus Gründen der Dokumentation nicht
   * bieten, sondern deren Status z.B von "aktiv" in "ehemalig" ändern. Wir
   * wollen hier aber dennoch zu Demonstrationszwecken eine Löschfunktion
   * vorstellen.
   */
  @Override
public void loeschenNutzer(Nutzer n) throws IllegalArgumentException {
    /*
     * Zunächst werden sämtl. Notizen und Notizbuecher des Nutzers aus der DB entfernt.
     * 
     * Beachten Sie, dass wir dies auf Ebene der Applikationslogik, konkret: in
     * der Klasse NotizobjektVerwaltungImpl, durchführen. Grund: In der Klasse
     * NotizobjektVerwaltungImpl ist die Verflechtung sämtlicher Klassen bzw. ihrer
     * Objekte bekannt. Nur hier kann sinnvoll ein umfassender Verwaltungsakt
     * wie z.B. dieser Löschvorgang realisiert werden.
     * 
     * Natürlich könnte man argumentieren, dass dies auch auf Datenbankebene
     * (sprich: mit SQL) effizienter möglich ist. Das Gegenargument ist jedoch
     * eine dramatische Verschlechterung der Wartbarkeit Ihres Gesamtsystems
     * durch einen zu niedrigen Abstraktionsgrad und der Verortung von Aufgaben
     * an einer Stelle (Datenbankschicht), die die zuvor genannte Verflechtung
     * nicht umfänglich kennen kann.
     */
    Vector<Notiz> notizen = this.nachAllenNotizenDesNutzersSuchen(n);
    Vector<Notizbuch> notizbuecher = this.nachAllenNotizbuechernDesNutzersSuchen(n);

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
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Nutzer-Objekte
   * ***************************************************************************
   */

  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Notiz-Objekte
   * ***************************************************************************
   */
  /**
   * Auslesen sämtlicher Notizen dieses Systems.
   */
  @Override
public Vector<Notiz> nachAllenNotizenSuchen() throws IllegalArgumentException {
    return this.noMapper.nachAllenNotizenSuchen();
  }

  /**
   * Auslesen aller Notizen des übergeben Nutzers.
   */
  @Override
public Vector<Notiz> nachAllenNotizenDesNutzersSuchen(Nutzer n)
      throws IllegalArgumentException {
    return this.noMapper.nachEigentuemerSuchen(n);
  }
  
  /**
   * Auslesen der Notiz mit einer bestimmten Id
   */
  @Override
public Notiz nachNotizIdSuchen(int id) throws IllegalArgumentException {
	  return noMapper.nachNotizIdSuchen(id);
  }

  /**
   * Löschen der übergebenen Notiz. Beachten Sie bitte auch die Anmerkungen zu
   * {@link #loeschenNutzer(Nutzer)}. Beim Löschen der Notiz werden sämtliche damit
   * in Verbindung stehenden Notizquellen-Objekte und Datum-Objekte gelöscht.
   * 
   * @see #loeschenNutzer(Nutzer)
   */
  @Override
public void loeschenNotiz(Notiz no) throws IllegalArgumentException {
    /*
     * Zunächst werden sämtl. Notizquellen-Objekte und Datum-Objekte des Nutzers aus der DB entfernt.
     */
    ArrayList<Notizquelle> notizquellen = this.nachAllenNotizquellenDesNutzersSuchen(no);
    ArrayList<Datum> faelligkeiten = this.nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(no);
   

    if (notizquellen != null) {
      for (Notizquelle nq : notizquellen) {
        this.loeschenNotizquelleVon(nq);
      }
    }
    
    if (faelligkeiten != null) {
        for (Datum d : faelligkeiten) {
          this.loeschenDatumVon(d);
        }
      }

    // Notiz aus der DB entfernen
    this.noMapper.loeschenNotiz(no);
  }
  
  /**
   * Anlegen einer neuen Notiz für den übergebenen Nutzer. Dies führt implizit
   * zu einem Speichern der neuen, leeren Notiz in der Datenbank.
   * <p>
   * 
   * <b>HINWEIS:</b> Änderungen an Notiz-Objekten müssen stets durch Aufruf
   * von {@link #speichern(Notiz)} in die Datenbank transferiert werden.
   * 
   * @see speichern(Notiz a)
   */
  @Override
public Notiz anlegenNotizFuer(Nutzer n) throws IllegalArgumentException {
    Notiz no = new Notiz();

    /*
     * Setzen einer vorläufigen NotizId. Der anlegenNotiz-Aufruf liefert dann ein
     * Objekt, dessen Id mit der Datenbank konsistent ist.
     */
    no.setId(1);

    // Objekt in der DB speichern.
    return this.noMapper.anlegenNotiz(no);
  }
  
  /**
   * Auslesen sämtlicher mit diesem Nutzer in Verbindung stehenden
   * Notizquellen. 
   * 
   * @param k der Nutzer, dessen Notizquellen wir bekommen wollen.
   * @return eine Liste aller Notizquellen
   * @throws IllegalArgumentException
   */
  @Override
  public ArrayList<Notizquelle> nachAllenNotizquellenDesNutzersSuchen(Notiz n)
	      throws IllegalArgumentException {
	    ArrayList<Notizquelle> result = new ArrayList<Notizquelle>();

	    if (n != null && this.nqMapper != null) {
	      Vector<Notizquelle> notizquellen = this.nqMapper.nachAllenNotizquellenDerNotizSuchen(n
	          .getId());
	      if (notizquellen != null) {
	        result.addAll(notizquellen);
      }
    }

    return result;
  }
  
  /**
   * Auslesen sämtlicher mit dieser Notiz in Verbindung stehenden
   * Faelligkeiten. 
   * 
   * @param k der Notiz, dessen Faelligkeiten wir bekommen wollen.
   * @return eine Liste aller Faelligkeiten
   * @throws IllegalArgumentException
   */
  @Override
  public ArrayList<Datum> nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(Notiz n)
	      throws IllegalArgumentException {
	    ArrayList<Datum> result = new ArrayList<Datum>();

	    if (n != null && this.dMapper != null) {
	      Vector<Datum> faelligkeiten = this.dMapper.nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(n
	          .getId());
	      if (faelligkeiten != null) {
	        result.addAll(faelligkeiten);
      }
    }

    return result;
  }

  /**
   * Speichern einer Notiz.
   */
  @Override
public void speichern(Notiz no) throws IllegalArgumentException {
    noMapper.update(no);
  }
  
  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Notiz-Objekte
   * ***************************************************************************
   */

  /*
   * **************************************************************************
   * ABSCHNITT, Beginn: Methoden für Notizobjekt-Objekte
   * ***************************************************************************
   */

  
  /**
   * Auslesen sämtlicher Notizbuecher dieses Systems.
   */
  @Override
public Vector<Notizbuch> nachAllenNotizbuechernSuchen() throws IllegalArgumentException {
    return this.nbMapper.nachAllenNotizbuechernSuchen();
  }

  /**
   * Auslesen aller Notizbuecher des übergeben Nutzers.
   */
  @Override
public Vector<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer n)
      throws IllegalArgumentException {
    return this.nbMapper.nachEigentuemerSuchen(n);
  }
  
  /**
   * Auslesen des Notizbuches mit einer bestimmten Id.
   */
  @Override
public Notizbuch nachNotizbuchIdSuchen(int id) throws IllegalArgumentException {
	  return nbMapper.nachNotizbuchIdSuchen(id);
  }

  /**
   * Löschen des übergebenen Notizbuches. Beachten Sie bitte auch die Anmerkungen zu
   * {@link #loeschenNutzer(Nutzer)}. Beim Löschen des Notizbuches werden sämtliche damit
   * in Verbindung stehenden Notizen gelöscht.
   * 
   * @see #loeschenNutzer(Nutzer)
   */
  @Override
public void loeschenNotizbuch(Notizbuch nb) throws IllegalArgumentException {
    /*
     * Zunächst werden sämtl. Notizen des Nutzers aus der DB entfernt.
     */
    ArrayList<Notiz> notizen = this.nachAllenNotizenDesNutzersSuchen(nb);

    if (notizen != null) {
      for (Notiz no : notizen) {
        this.loeschenNotiz(no);
      }
    }

    // Notizbuch aus der DB entfernen
    this.nbMapper.loeschenNotizbuch(nb);
  }
  

  /**
   * Anlegen eines neuen Notizbuches für den übergebenen Nutzers. Dies führt implizit
   * zu einem Speichern des neuen, leeren Notizbuches in der Datenbank.
   * <p>
   * 
   * <b>HINWEIS:</b> Änderungen an Notizbuch-Objekten müssen stets durch Aufruf
   * von {@link #speichern(Notizbuch)} in die Datenbank transferiert werden.
   * 
   * @see speichern(Notizbuch a)
   */
  @Override
public Notizbuch anlegenNotizbuecherFuer(Nutzer n) throws IllegalArgumentException {
    Notizbuch nb = new Notizbuch();
    nb.setId(n.getNutzerId());

    /*
     * Setzen einer vorläufigen NotizbuchId. Der anlegenNotizbuch-Aufruf liefert dann ein
     * Objekt, dessen Id mit der Datenbank konsistent ist.
     */
    nb.setId(1);

    // Objekt in der DB speichern.
    return this.nbMapper.anlegenNotizbuch(nb);
  }
  
  /**
   * <p>
   * Auslesen sämtlicher mit diesem Nutzer in Verbindung stehenden
   * Notizen. 
   * 
   * @param k der Nutzer, dessen Notizquellen wir bekommen wollen.
   * @return eine Liste aller Notizquellen
   * @throws IllegalArgumentException
   */
  @Override
  public ArrayList<Notiz> nachAllenNotizenDesNutzersSuchen(Notizbuch nb)
	      throws IllegalArgumentException {
	    ArrayList<Notiz> result = new ArrayList<Notiz>();

	    if (nb != null && this.noMapper != null) {
	      Vector<Notiz> notizen = this.noMapper.nachAllenNotizenDesNotizbuchesSuchen(nb
	          .getId());
	      if (notizen != null) {
	        result.addAll(notizen);
      }
    }

    return result;
  }
      
  /**
   * Speichern eines Notizbuches.
   */
  @Override
public void speichern(Notizbuch nb) throws IllegalArgumentException {
    nbMapper.update(nb);
  }

 
  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Notizbuch-Objekts
   * ***************************************************************************
   */
  
  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Methoden für Notizquellen-Objekte und Datum-Objekte
   * ***************************************************************************
   */
  /**
   * Erstellen einer neuen  Notizquelle.
   * 
   */
  @Override
public Notizquelle anlegenNotizquelleFuer(Notiz no) throws IllegalArgumentException {

    /*
     * Wir legen eine neue, leere Buchung an.
     */
    Notizquelle nq = new Notizquelle();

    /*
     * Setzen einer vorläufigen NotizquelleId Der anlegenNotizquelle-Aufruf liefert dann ein
     * Objekt, dessen Id mit der Datenbank konsistent ist.
     */
    nq.setNotizquelleId(1);

    // Objekt in der DB speichern.
    return this.nqMapper.anlegenNotizquelle(nq);
  }

  /**
   * Löschen der übergebenen Notizquellen. 
   */
  @Override
public void loeschenNotizquelleVon(Notizquelle nq) throws IllegalArgumentException {
    this.nqMapper.loeschenNotizquelle(nq);
  }
  
  /**
   * Erstellen einer neuen Faelligkeit.
   */
  @Override
public Datum anlegenFaelligkeitFuer(Notiz no) throws IllegalArgumentException {

    /*
     * Wir legen eine neue, leere Buchung an.
     */
    Datum d = new Datum();

    /*
     * Setzen einer vorläufigen FaelligkeitId. Der anlegenDatum-Aufruf liefert dann ein
     * Objekt, dessen Id mit der Datenbank konsistent ist.
     */
    d.setFaelligkeitId(1);

    // Objekt in der DB speichern.
    return this.dMapper.anlegenDatum(d);
  }

  /**
   * Löschen der übergebenen Faellgikeiten.
   */
  @Override
public void loeschenDatumVon(Datum t) throws IllegalArgumentException {
    this.dMapper.loeschenDatum(t);
  }

  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Methoden für Notizquellen-Objekte und Datum-Objekte
   * ***************************************************************************
   */
  
  /*
   * ***************************************************************************
   * ABSCHNITT, Beginn: Verschiedenes
   * ***************************************************************************
   */
  /**
   * Auslesen der Notizobjekt für die diese Notizobjektverwaltung gewissermaßen tätig ist.
   */
  @Override
public Notizobjekt getNotizobjekt() throws IllegalArgumentException {
    return this.notizobjekt;
  }

  /**
   * Setzen der Notizobjekt für die diese Notizobjektverwaltung tätig ist.
   */
  @Override
public void setNotizobjekt(Notizobjekt n) throws IllegalArgumentException {
    this.notizobjekt = n;
  }
  /*
   * ***************************************************************************
   * ABSCHNITT, Ende: Verschiedenes
   * ***************************************************************************
   */

}
