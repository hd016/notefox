package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.bo.*;

/**
 * 
 * Unsere Mapper-Klassen erfüllen den Zweck unsere Objekte auf eine relationale Datenbank abzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, löschen, teilen 
 * und speichern. Objekte können auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen können umgekehrt auch in Objekte umgewandelt werden. 
 * 
 *
 */

public class DatumMapper {

	  /**
	   * 
	   * Eimalige Instantierung der Klasse DatumMapper (Singleton)
	   * Einmal für sämtliche Instanzen dieser Klasse vorhanden, 
	   * speichert die eizige Instanz dieser Klasse
	   * 
	   */
	
  private static DatumMapper datumMapper = null;

  /**
   * 
   * Konstruktor verhindert durch protected weitere Instanzen aus dieser Klasse zu erzeugen
   * 
   */
  protected DatumMapper() {
  }

  /**
   * DatumMapper-Objekt
   * Die statische Methode verhindert, 
   * dass von einer Klasse mehr als ein Objekt gebildet werden kann. 
   * (Bewahrung der Singleton-Eigenschaft)
   */
  
  public static DatumMapper datumMapper() {
    if (datumMapper == null) {
      datumMapper = new DatumMapper();
    }

    return datumMapper;
  }

  /**
   * Datum nach FaelligkeitID suchen.   * 
   * als return: Datum-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Datum nachFaelligkeitIdSuchen(int id) {
    // Es wird eine DB-Verbindung hergestellt 
    Connection con = DBConnection.connection();

    try {
      // Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

      // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT id, faelligkeitID FROM datum "
          + "WHERE id=" + id + " ORDER BY faelligkeitID");

      /*
       * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
       * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
       */
      if (rs.next()) {
        // Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Datum a = new Datum();
        a.setFaelligkeitIdSuchen(rs.getInt("FaelligkeitIdSuchen"));
        return a;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Datum-Objekte.
   * 
   */
  
  public Vector<Datum> nachAllenDatumObjektenSuchen() {
    Connection con = DBConnection.connection();

    // Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Datum> result = new Vector<Datum>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT faelligkeitID" + "FROM datum "
          + " ORDER BY faelligkeitID");

      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
    	  Datum a = new Datum();
        a.setFaelligkeitId(rs.getInt("FaelligkeitID"));
   

        // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
        result.addElement(a);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zurückgegeben
    return result;
  }

  /**
   * Auslesen aller Datum-Objekte eines durch Fremdschlüssel (NutzerId) gegebenen
   * Nutzern.
   */
//  public Vector<Datum> nachEigentuemerSuchen(int notizId) {
//    Connection con = DBConnection.connection();
//    Vector<Datum> result = new Vector<Datum>();
//
//    try {
//      Statement stmt = con.createStatement();
//
//      ResultSet rs = stmt.executeQuery("SELECT id, eigentuemer FROM datum "
//          + "WHERE owner=" + notizId + " ORDER BY id");
//
//      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
//      while (rs.next()) {
//    	  Datum a = new Datum();
//        a.setFaelligkeitId(rs.getInt("FaelligkeitID"));
//
//        // Hinzufügen des neuen Objekts zum Ergebnisvektor
//        result.addElement(a);
//      }
//    }
//    catch (SQLException e2) {
//      e2.printStackTrace();
//    }
//
//    // Der Ergebnisvektor wird zurückgegeben
//    return result;
//  }
//
//  /**
//   * Auslesen aller Datum-Objekte eines Nutzers
//   */
//  public Vector<Datum> nachEigentuemerSuchen(Nutzer eigentuemer) {
//
//   
//	  
//	  
//    return nachEigentuemerSuchen(eigentuemer.getNutzerId());
//  }

  /**
   * Anlegen eines Datum-Objekts.
   * 
   */
  public Datum anlegenDatum(Datum a) {
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
        a.setFaelligkeitId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Hier erfolgt die entscheidende Einfügeoperation
        stmt.executeUpdate("INSERT INTO datum (id, faelligkeitID) " + "VALUES ("
            + a.getFaelligkeitId() + ")");
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
    return a;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Datum update(Datum a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE datum " + "SET faelligkeitID=\"" + a.getFaelligkeitId()
          + "\" " + "WHERE id=" + a.getFaelligkeitId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu anlegenDatum(Datum a) zu wahren, geben wir a zurück
    return a;
  }

  /**
   * Löschen der Daten eines Datum-Objekts aus der Datenbank.
   */
  public void loeschenDatum(Datum a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM datum " + "WHERE id=" + a.getFaelligkeitId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *Löschen sämtlicher Datum-Objekte eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gelöscht wird)
   */
  public void loeschenDatumVon(Nutzer c) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM datum " + "WHERE nutzerId=" + c.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugehörigen Notiz-Objekts zu einem gegebenen
   * Datum.
   */
  public Notiz getNotizId(Datum a) {
    return NotizMapper.notizMapper().nachFaelligkeitIdSuchen(a.getFaelligkeitId());
  }

}
