package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;


/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erf�llen den Zweck unsere Objekte auf eine relationale Datenbank abzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, l�schen, teilen 
 * und speichern. Objekte k�nnen auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen k�nnen umgekehrt auch in Objekte umgewandelt werden.
 * <p>
 * 
 * @see DatumMapper, NotizMapper, NotizquelleMapper, NutzerMapper
 */

public class NotizbuchMapper {

/**
 * 
 * Die Klasse NotizbuchMapper wird nur einmal instantiiert. Man spricht hierbei
 * von einem sogenannten <b>Singleton</b>.
 * <p>
 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
 * einzige Instanz dieser Klasse.
 * 
 */
	
  private static NotizbuchMapper notizbuchMapper = null;

 /**
  * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
  * Instanzen dieser Klasse zu erzeugen.
  * 
  */
  protected NotizbuchMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>NotizbuchMapper.notizbuchMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>NotizbuchMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> NotizbuchMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>NotizbuchMapper</code>-Objekt.
   * @see notizbuchMapper
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
  public Notizbuch nachNotizbuchIdSuchen(int notizbuchId) {
	// DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
    // Leeres SQL-Statement (JDBC) anlegen
    Statement stmt = con.createStatement();

    // Statement ausfuellen und als Query an die DB schicken
     ResultSet rs = stmt.executeQuery("SELECT notizbuchId FROM notizbuch "
          + "WHERE notizbuchId=" + notizbuchId + " ORDER BY notizbuchId");

     /*
      * Da id Primaerschluessel ist, kann max. nur ein Tupel zurueckgegeben
      * werden. Pruefe, ob ein Ergebnis vorliegt.
      */
      if (rs.next()) {
    	  // Ergebnis-Tupel in Objekt umwandeln
    	  Notizbuch nb = new Notizbuch();
    	  nb.setId(rs.getInt("id"));
    	  nb.setNotizbuchId(rs.getInt("notizbuchId"));
    	  return nb;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Notizb�cher.
   */
  
  public Vector<Notizbuch> nachAllenNotizbuechernSuchen() {
    Connection con = DBConnection.connection();

    // Ergebnisvektor vorbereiten
    Vector<Notizbuch> result = new Vector<Notizbuch>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizbuchId" + "FROM notizbuch "
          + " ORDER BY notizbuchId");

      // F�r jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
    	  Notizbuch nb = new Notizbuch();
    	  nb.setNotizbuchId(rs.getInt("NotizbuchId"));
   

    	// Hinzufuegen des neuen Objekts zum Ergebnisvektor
        result.addElement(nb);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurueckgeben
    return result;
  }

  /**
   * Auslesen aller Notizb�cher eines durch Fremdschl�ssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notizbuch> nachEigentuemerSuchen(int notizbuchId) {
    Connection con = DBConnection.connection();
    Vector<Notizbuch> result = new Vector<Notizbuch>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizbuchId, eigentuermer"
    	+ "FROM notizbuch " + "INNER JOIN notizobjekt"
        + "WHERE notizbuchId" + notizbuchId + " ORDER BY notizbuchId");

      // Fuer jeden Eintrag im Suchergebnis wird nun ein Notizbuch-Objekt
      // erstellt.
      while (rs.next()) {
    	  Notizbuch nb = new Notizbuch();
        nb.setId(rs.getInt("id"));
        nb.setNotizbuchId(rs.getInt("NotizbuchId"));

        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
        result.addElement(nb);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurueckgeben
    return result;
  }

  /**
   * Auslesen aller Notizb�cher eines Nutzers
   */
  public Vector<Notizbuch> nachEigentuemerSuchen(Nutzer eigentuemer) {

    /*
     * Wir lesen einfach die Kundennummer (Primärschlüssel) des Customer-Objekts
     * aus und delegieren die weitere Bearbeitung an findByOwner(int ownerID).
     */
    return nachEigentuemerSuchen(eigentuemer.getNutzerId());
  }

  /**
   * Anlegen einer Notizbuch.
   * 
   */
  public Notizbuch anlegenNotizbuch(Notizbuch nb) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Der h�chste Prim�rschl�sselwert wird �berpr�ft
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM notizbuch ");

      // Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * nb erh�lt den bisher maximalen, nun um 1 inkrementierten
           * Prim�rschl�ssel.
           */
        nb.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Hier erfolgt die entscheidende Einf�geoperation
        stmt.executeUpdate("INSERT INTO notizbuch (id, notizbuchId) " + "VALUES ("
            + nb.getId() + "," + nb.getNotizbuchId() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Daten geben, so werden diese zur�ckgegeben
     * 
     * So besteht die M�glichkeit anzudeuten, ob sich ein Objekt ver�ndert hat, 
     * w�hrend die Methode ausgef�hrt wurde
     */
    return nb;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Notizbuch update(Notizbuch nb) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE notizbuch " + "SET notizbuchId=\"" + nb.getNotizbuchId()
          + "\" " + "WHERE id=" + nb.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu anlegenDatum(Datum a) zu wahren, geben wir nb zur�ck
    return nb;
  }

  /**
   * Loeschen der Daten eines <code>Notizbuch</code>-Objekts aus der Datenbank.
   * 
   * @param a das aus der DB zu loeschende "Objekt"
   */
  public void loeschenNotizbuch(Notizbuch nb) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE notizbuchId=" + nb.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *L�schen s�mtlicher Notizb�cher eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gel�scht wird)
   */
  public void loeschenNotizbuchVon(Nutzer n) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE nutzerId=" + n.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugeh�rigen Nutzer-Objekts zu einem gegebenen
   * Notizbuch.
   */
  public Nutzer getNutzerId(Notizbuch nb) {
    return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(nb.getNotizbuchId());
  }

}
