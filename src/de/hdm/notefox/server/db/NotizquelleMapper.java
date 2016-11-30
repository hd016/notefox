package de.hdm.notefox.server.db;




import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.server.db.NotizMapper;
import de.hdm.notefox.shared.Notizquelle;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * „Unsere Mapper-Klassen erfüllen den Zweck unsere Objekte auf eine relationale Datenbankabzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, löschen, teilen 
 * und speichern.Objekte können auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen können umgekehrt auch in Objekte umgewandelt werden.“
 */

public class NotizquelleMapper {

	  /**
	   * Eimalige Instantierung der Klasse NotizquelleMapper (Singleton)
	   * Einmal für sämtliche Instanzen dieser Klasse vorhanden, 
	   * speichert die eizige Instanz dieser Klasse
	   */
	
  private static NotizquelleMapper notizquelleMapper = null;

  /**
   * Konstruktor verhindert durch den protected weitere Instanzen aus dieser Klasse zu erzeugen
   */
  protected NotizquelleMapper() {
  }

  /**
   * NotizquelleMapper-Objekt
   * Die statische Methode verhindert, 
   * dass von einer Klasse mehr als ein Objekt gebildet werden kann. 
   * (Bewahrung der Singleton-Eigenschaft)
   */
  
  public static NotizquelleMapper notizquelleMapper() {
    if (notizquelleMapper == null) {
      notizquelleMapper = new NotizquelleMapper();
    }

    return notizquelleMapper;
  }

  /**
   * Notizquelle nach NotizquelleId suchen.   * 
   * als return: Notizquelle-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notizquelle nachNotizquelleId(int id) {
    // Es wird eine DB-Verbindung angeschafft 
    Connection con = DBConnection.connection();

    try {
      //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

      // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId FROM notizquelle "
          + "WHERE id=" + id + " ORDER BY notizquelleId");

      /*
       * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
       * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
       */
      if (rs.next()) {
        //Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("NotizquelleId"));
        nq.setNotizquelleName(rs.getString("notizquelleName"));
        nq.setUrl(rs.getString("url"));
        return nq;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Notizquellen.
   * 
   */
  
  public Vector<Notizquelle> nachAllenNotizquellenSuchen() {
    Connection con = DBConnection.connection();

    // Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizquelleId" + "FROM notizquelle "
          + " ORDER BY notizquelleId");

      // Jetzt werden die Einträge durchsucht und für jedes gefundene ein Notizquelle Objekt erstellt
      while (rs.next()) {
    	  Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("NotizquelleId"));
        nq.setNotizquelleName(rs.getString("notizquelleName"));
        nq.setUrl(rs.getString("url"));
   

        // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zurückgegeben
    return result;
  }
  
  /**
   * Auslesen aller Notizquellen eines durch Fremdschlüssel (NotizId) gegebenen
   * Notizen.
   * 
   * @param notizId Schlüssel der zugehörigen Notiz.
   * @return Ein Vektor mit Notizquellen-Objekten, die sämtliche Notizquellen der
   *         betreffenden Notiz repräsentieren. Bei evtl. Exceptions wird ein
   *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
   */
  public Vector<Notizquelle> nachAllenNotizquellenDerNotizSuchen(int notizId) {
    Connection con = DBConnection.connection();
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, notizquelleName, url, notizquelleId FROM notizquellen "
              + "WHERE notizquelleId=" + notizId + " ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Notizquelle-Objekt erstellt.
      while (rs.next()) {
        Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("notizquelleId"));
        nq.setNotizquelleName(rs.getString("name"));
        nq.setUrl(rs.getString("url"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zurückgeben
    return result;
  }

  /**
   * Auslesen aller Notizquellen eines durch Fremdschlüssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notizquelle> nachEigentuemerSuchen(int notizId) {
    Connection con = DBConnection.connection();
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT NotizquelleId, owner FROM notizquelle "
          + "WHERE owner=" + notizId + " ORDER BY NotizquelleId");

      // Jetzt werden die Einträge durchsucht und für jedes gefundene ein Notizquelle Objekt erstellt
      while (rs.next()) {
    	  Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("NotizquelleId"));
        nq.setNotizquelleName(rs.getString("notizquelleName"));
        nq.setUrl(rs.getString("url"));

     // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zurückgegeben

    return result;
  }

  /**
   * Auslesen aller Notizquellen eines Nutzers
   */
  public Vector<Notizquelle> nachEigentuemerSuchen(Nutzer eigentuemer) {

    
	  
	  
	  
    return nachEigentuemerSuchen(eigentuemer.getNutzerId());
  }

  /**
   * Anlegen einer Notizquelle.
   * 
   */
  public Notizquelle anlegenNotizquelle(Notizquelle nq) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      //Der höchste Primärschlüsselwert wird überprüft
      
      ResultSet rs = stmt.executeQuery("SELECT MAX(NotizquelleId) AS maxid "
          + "FROM notizquelle ");

      // Sollte etwas zurückgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * a kriegt nun den maximalen Primärschlüssel, welcher mit dem Wert 1 inkrementiert wird
           */
        nq.setNotizquelleId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        //Hier erfolgt die entscheidende Einfügeoperation
        stmt.executeUpdate("INSERT INTO notizquelle (id, notizquelleId) " + "VALUES ("
            + nq.getNotizquelleId() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Notizquellen geben, so werden diese zurückgegeben
     * 
     * So besteht die Möglichkeit anzudeuten ob sich ein Objekt verändert hat, während die Methode ausgeführt wurde
     */
    return nq;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Notizquelle update(Notizquelle nq) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE notizquelle " + "SET notizquelleId=\"" + nq.getNotizquelleId()
          + "\" " + "WHERE id=" + nq.getNotizquelleId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um ähnliche Strukturen wie zu anlegenNotizquelle(Notizquelle a) zu wahren, geben wir nun a zurück
    return nq;
  }

  /**
   * Löschen der Daten eines Notizquelle-Objekts aus der Datenbank.
   */
  public void loeschenNotizquelle(Notizquelle nq) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizquelle " + "WHERE id=" + nq.getNotizquelleId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *Löschen sämtlicher Notizquellen eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gelöscht wird)
   */
  public void loeschenNotizquelleVon(Nutzer n) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notizquelle " + "WHERE nutzerId=" + n.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugehörigen Notiz-Objekts zu einem gegebenen
   * Notizquelle.
   */
  public Notiz getNotizId(Notizquelle nq) {
    return NotizMapper.notizMapper().nachNotizquelleIdSuchen(nq.getNotizquelleId());
  }

}
