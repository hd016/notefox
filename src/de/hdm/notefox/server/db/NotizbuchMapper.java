package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * 
 * Unsere Mapper-Klassen erfüllen den Zweck unsere Objekte auf eine relationale Datenbank abzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, löschen, teilen 
 * und speichern. Objekte können auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen können umgekehrt auch in Objekte umgewandelt werden. 
 * 
 */

public class NotizbuchMapper {

	  /**
	   * Eimalige Instantierung der Klasse NotizbuchMapper (Singleton)
	   * Einmal für sämtliche Instanzen dieser Klasse vorhanden, 
	   * speichert die eizige Instanz dieser Klasse
	   * 
	   */
	
  private static NotizbuchMapper notizbuchMapper = null;

  /**
   * Konstruktor verhindert durch protected weitere Instanzen aus dieser Klasse zu erzeugen
   */
  protected NotizbuchMapper() {
  }

  /**
   * NotizbuchMapper-Objekt
   * Die statische Methode verhindert, 
   * dass von einer Klasse mehr als ein Objekt gebildet werden kann. 
   * (Bewahrung der Singleton-Eigenschaft)
   */
  
  public static NotizbuchMapper notizbuchMapper() {
    if (notizbuchMapper == null) {
      notizbuchMapper = new NotizbuchMapper();
    }

    return notizbuchMapper;
  }

  /**
   * Notizbuch nach NotizbuchId suchen.   
   * Als return: Notizbuch-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notizbuch nachNotizbuchId(int id) {
	// Es wird eine DB-Verbindung hergestellt
    Connection con = DBConnection.connection();

    try {
    // Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
    Statement stmt = con.createStatement();

      // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT id, notizbuchId FROM notizbuch "
          + "WHERE id=" + id + " ORDER BY notizbuchId");

      /*
       * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
       * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
       */
      if (rs.next()) {
    	// Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Notizbuch a = new Notizbuch();
        a.setId(rs.getInt("id"));
        a.setNotizbuchId(rs.getInt("NotizbuchId"));
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
   * Auslesen aller Notizbücher.
   */
  
  public Vector<Notizbuch> nachAllenNotizbuecherSuchen() {
    Connection con = DBConnection.connection();

    // Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Notizbuch> result = new Vector<Notizbuch>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizbuchId" + "FROM notizbuch "
          + " ORDER BY notizbuchId");

      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
    	  Notizbuch a = new Notizbuch();
        a.setNotizbuchId(rs.getInt("NotizbuchId"));
   

        // Das neue Objekt Ergebnisvektor
        result.addElement(a);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
    return result;
  }

  /**
   * Auslesen aller Notizbücher eines durch Fremdschlüssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notizbuch> nachEigentuemerSuchen(int notizbuchId) {
    Connection con = DBConnection.connection();
    Vector<Notizbuch> result = new Vector<Notizbuch>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT id, owner FROM notizbuch "
          + "WHERE owner=" + notizbuchId + " ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
    	  Notizbuch a = new Notizbuch();
        a.setId(rs.getInt("id"));
        a.setNotizbuchId(rs.getInt("NotizbuchId"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
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
   * Auslesen aller Notizbücher eines Nutzers
   */
  public Vector<Notizbuch> nachEigentuemerSuchen(Nutzer eigentuemer) {

    /*
     * Wir lesen einfach die Kundennummer (PrimÃ¤rschlÃ¼ssel) des Customer-Objekts
     * aus und delegieren die weitere Bearbeitung an findByOwner(int ownerID).
     */
    return nachEigentuemerSuchen(eigentuemer.getNutzerId());
  }

  /**
   * Anlegen einer Notizbuch.
   * 
   */
  public Notizbuch anlegenNotiz(Notizbuch a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Der höchste Primärschlüsselwert wird überprüft
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM notizbuch ");

      // Sollte etwas zurückgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * a erhält den bisher maximalen, nun um 1 inkrementierten
           * Primärschlüssel.
           */
        a.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Hier erfolgt die entscheidende Einfügeoperation
        stmt.executeUpdate("INSERT INTO notizbuch (id, notizbuchId) " + "VALUES ("
            + a.getId() + "," + a.getNotizbuchId() + ")");
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
  public Notizbuch update(Notizbuch a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE notizbuch " + "SET notizbuchId=\"" + a.getNotizbuchId()
          + "\" " + "WHERE id=" + a.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu anlegenDatum(Datum a) zu wahren, geben wir a zurück
    return a;
  }

  /**
   * Löschen der Daten eines Notizbuch-Objekts aus der Datenbank.
   */
  public void loeschenNotiz(Notizbuch a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE id=" + a.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *Löschen sämtlicher Notizbücher eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gelöscht wird)
   */
  public void loeschenNotizVon(Nutzer c) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE nutzerId=" + c.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugehörigen Nutzer-Objekts zu einem gegebenen
   * Notizbuch.
   */
  public Nutzer getNutzerId(Notizbuch a) {
    return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(a.getNotizbuchId());
  }

}
