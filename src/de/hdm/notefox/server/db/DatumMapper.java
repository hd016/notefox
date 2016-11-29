package de.hdm.notefox.server.db;



import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Datum;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfüllen den Zweck unsere Objekte auf eine relationale Datenbank abzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, löschen, teilen 
 * und speichern. Objekte können auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen können umgekehrt auch in Objekte umgewandelt werden.
 * <p>
 * 
 * @see NotizMapper, NotizbuchMapper, NotizquelleMapper, NutzerMapper
 */

public class DatumMapper {

	/**
	 * 
	 * Die Klasse DatumMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	
  private static DatumMapper datumMapper = null;

  /**
   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
   * Instanzen dieser Klasse zu erzeugen.
   * 
   */
  protected DatumMapper() {
  }
  
  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>DatumMapper.datumMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>DatumMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> DatumMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>DatumMapper</code>-Objekt.
   * @see datumMapper
   */
  
  public static DatumMapper datumMapper() {
    if (datumMapper == null) {
      datumMapper = new DatumMapper();
    }

    return datumMapper;
  }

  /**
   * Datum nach FaelligkeitID suchen.   * 
   * Als return: Datum-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Datum nachFaelligkeitIdSuchen(int id) {
	// DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
    	// Leeres SQL-Statement (JDBC) anlegen
    	Statement stmt = con.createStatement();

    	// Statement ausfuellen und als Query an die DB schicken
    	ResultSet rs = stmt.executeQuery("SELECT faelligkeitId, status, faelligkeitsdatum FROM datum "
          + "WHERE faelligkeitId=" + id + " ORDER BY faelligkeitsdatum");

     /*
      * Da id Primaerschluessel ist, kann max. nur ein Tupel zurueckgegeben
      * werden. Pruefe, ob ein Ergebnis vorliegt.
      */
      if (rs.next()) {
    	// Ergebnis-Tupel in Objekt umwandeln
    	Datum d = new Datum();
        d.setFaelligkeitId(rs.getInt("FaelligkeitId"));
        d.setStatus(rs.getBoolean("Status"));
        d.setFaelligkeitsdatum(rs.getDate("Faelligkeitsdatum"));
        return d;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }

  
  /**
   * Auslesen aller Fälligkeiten eines durch FremdschlÃ¼ssel (NotizId) gegebener
   * Notiz.
   * 
   * @param notizId SchlÃ¼ssel der zugehÃ¶rigen Notiz.
   * @return Ein Vektor mit Datum-Objekten, die sÃ¤mtliche Fälligkeiten der
   *         betreffenden Notiz reprÃ¤sentieren. Bei evtl. Exceptions wird ein
   *         partiell gefÃ¼llter oder ggf. auch leerer Vetor zurÃ¼ckgeliefert.
   */
  public Vector<Datum> nachAllenFaelligkeitenDerNotizSuchen(int notizId) {
    Connection con = DBConnection.connection();
    Vector<Datum> result = new Vector<Datum>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, faelligkeitsdatum, status, faelligkeitId FROM faelligkeiten "
              + "WHERE faelligkeitId=" + notizId + " ORDER BY id");

      // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
        Datum d = new Datum();
        d.setFaelligkeitId(rs.getInt("faelligkeitid"));
        d.setFaelligkeitsdatum(rs.getDate("faelligkeitsdatum"));
        d.setStatus(rs.getBoolean("status"));

        // HinzufÃ¼gen des neuen Objekts zum Ergebnisvektor
        result.addElement(d);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zurÃ¼ckgeben
    return result;
  }  
  
  
  
  /**
   * Auslesen aller Datum-Objekte.
   * 
   */
  
  public Vector<Datum> nachAllenDatumObjektenSuchen() {
    Connection con = DBConnection.connection();

    // Ergebnisvektor vorbereiten
    Vector<Datum> result = new Vector<Datum>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT faelligkeitID" + "FROM datum "
          + " ORDER BY faelligkeitID");

      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
    	  Datum d = new Datum();
        d.setFaelligkeitId(rs.getInt("FaelligkeitID"));
   

        // Hinzufuegen des neuen Objekts zum Ergebnisvektor
        result.addElement(d);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurueckgeben
    return result;
  }

  /**
   * Anlegen eines Datum-Objekts.
   * 
   */
  public Datum anlegenDatum(Datum d) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       *  Der höchste Primärschlüsselwert wird überprüft
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM datum ");

      // Sollte etwas zurückgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * a erhält den bisher maximalen, nun um 1 inkrementierten
           * Primärschlüssel.
           */
        d.setFaelligkeitId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Hier erfolgt die entscheidende Einfügeoperation
        stmt.executeUpdate("INSERT INTO datum (id, faelligkeitID) " + "VALUES ("
        	+ "," + d.getFaelligkeitId() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Daten geben, so werden diese zurückgegeben
     * 
     * So besteht die Möglichkeit anzudeuten, ob sich ein Objekt verändert hat, 
     * während die Methode ausgeführt wurde
     */
    return d;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Datum aktualisierenDatum(Datum d) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE datum " + "SET faelligkeitID=\"" + d.getFaelligkeitId()
          + "\" " + "WHERE id=" + d.getFaelligkeitId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu anlegenDatum(Datum d) zu wahren, geben wir d zurück
    return d;
  }

  /**
   * Loeschen der Daten eines <code>Datum</code>-Objekts aus der Datenbank.
   * 
   * @param a das aus der DB zu loeschende "Objekt"
   */
  public void loeschenDatum(Datum d) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM datum " + "WHERE faelligkeitId=" + d.getFaelligkeitId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *Löschen sämtlicher Datum-Objekte eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gelöscht wird)
   */
  public void loeschenDatumVon(Nutzer n) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM datum " + "WHERE nutzerId=" + n.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }
  
  
  /**
   * Auslesen des zugehörigen Notiz-Objekts zu einem gegebenen
   * Datum.
   */
  public Notiz getNotizId(Datum d) {
    return NotizMapper.notizMapper().nachFaelligkeitIdSuchen(d.getFaelligkeitId());
  }

}
