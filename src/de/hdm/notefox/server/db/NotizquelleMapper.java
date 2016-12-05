package de.hdm.notefox.server.db;




import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.server.db.NotizMapper;
import de.hdm.notefox.shared.Notizquelle;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * �Unsere Mapper-Klassen erf�llen den Zweck unsere Objekte auf eine relationale Datenbankabzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, l�schen, teilen 
 * und speichern.Objekte k�nnen auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen k�nnen umgekehrt auch in Objekte umgewandelt werden.�
 */

public class NotizquelleMapper {

	  /**
	   * Eimalige Instantierung der Klasse NotizquelleMapper (Singleton)
	   * Einmal f�r s�mtliche Instanzen dieser Klasse vorhanden, 
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
   * Notizquelle nach Notizquelle suchen.   * 
   * als return: Notizquelle-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notizquelle nachNotizquelleSuchen(int id) {
    // Es wird eine DB-Verbindung angeschafft 
    Connection con = DBConnection.connection();

    try {
      //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

      // Das Statement wird ausgef�llt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId, notizquelleName, url FROM notizquelle "
          + "WHERE notizquelleid=" + id + " ORDER BY notizquelleId ASC");

      /*
       * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
       * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
       */
      if (rs.next()) {
        //Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
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
  
  public Vector<Notizquelle> nachAllenNotizquellenSuchen() { //TODO
    Connection con = DBConnection.connection();

    // Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizquelleId" + "FROM notizquelle "
          + " ORDER BY notizquelleId");

      // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Notizquelle Objekt erstellt
      while (rs.next()) {
    	  Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("NotizquelleId"));
        nq.setNotizquelleName(rs.getString("notizquelleName"));
        nq.setUrl(rs.getString("url"));
   

        // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zur�ckgegeben
    return result;
  }
  
  /**
   * Auslesen aller Notizquellen eines durch Fremdschl�ssel (Id) gegebenen
   * Notizen.
   * 
   * @param notizId Schl�ssel der zugeh�rigen Notiz.
   * @return Ein Vektor mit Notizquellen-Objekten, die s�mtliche Notizquellen der
   *         betreffenden Notiz repr�sentieren. Bei evtl. Exceptions wird ein
   *         partiell gef�llter oder ggf. auch leerer Vetor zur�ckgeliefert.
   */
  public Vector<Notizquelle> nachAllenNotizquellenDerNotizSuchen(int id) {
    Connection con = DBConnection.connection();
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, notizquelleName, url, notizquelleId FROM notizquellen "
              + "WHERE notizquelleId=" + id + " ORDER BY id");

      // F�r jeden Eintrag im Suchergebnis wird nun ein Notizquelle-Objekt erstellt.
      while (rs.next()) {
        Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("notizquelleId"));
        nq.setNotizquelleName(rs.getString("name"));
        nq.setUrl(rs.getString("url"));

        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zur�ckgeben
    return result;
  }

  /**
   * Auslesen aller Notizquellen eines durch Fremdschl�ssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notizquelle> nachEigentuemerDerNotizquelleSuchen(int id) {
    Connection con = DBConnection.connection();
    Vector<Notizquelle> result = new Vector<Notizquelle>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizquelle.notizquelleId, notizquelle.notizquelleName, notizquelle.url, nutzer.nutzerId, nutzer.name FROM notizquelle, nutzer "
          + "WHERE nutzerId=" + id + " ORDER BY notizquelle.notizquelleId ASC");

      // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Notizquelle Objekt erstellt
      while (rs.next()) {
    	  Notizquelle nq = new Notizquelle();
        nq.setNotizquelleId(rs.getInt("NotizquelleId"));
        nq.setNotizquelleName(rs.getString("notizquelleName"));
        nq.setUrl(rs.getString("url"));

     // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(nq);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zur�ckgegeben

    return result;
  }

  /**
   * Auslesen aller Notizquellen eines Nutzers
   */
  public Vector<Notizquelle> nachEigentuemerDerNotizquelleSuchen(Nutzer eigentuemer) {

    
	  
	  
	  
    return nachEigentuemerDerNotizquelleSuchen(eigentuemer.getNutzerId());
  }

  /**
   * Anlegen einer Notizquelle.
   * 
   */
  public Notizquelle anlegenNotizquelle(Notizquelle nq) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      //Der h�chste Prim�rschl�sselwert wird �berpr�ft
      
      ResultSet rs = stmt.executeQuery("SELECT MAX(NotizquelleId) AS maxid "
          + "FROM notizquelle ");

      // Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * a kriegt nun den maximalen Prim�rschl�ssel, welcher mit dem Wert 1 inkrementiert wird
           */
        nq.setNotizquelleId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        //Hier erfolgt die entscheidende Einf�geoperation
        stmt.executeUpdate("INSERT INTO notizquelle (notizquelleId, notizquelleName, url) " + "VALUES("
        + nq.getNotizquelleId() + ", \"\",    " + nq.getNotizquelleName() + ", \"" + nq.getUrl());
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Notizquellen geben, so werden diese zur�ckgegeben
     * 
     * So besteht die M�glichkeit anzudeuten ob sich ein Objekt ver�ndert hat, w�hrend die Methode ausgef�hrt wurde
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
          + "\" " + nq.getNotizquelleName() +"\" " + nq.getUrl() +  "WHERE id=" + nq.getNotizquelleId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um �hnliche Strukturen wie zu anlegenNotizquelle(Notizquelle a) zu wahren, geben wir nun a zur�ck
    return nq;
  }

  /**
   * L�schen der Daten eines Notizquelle-Objekts aus der Datenbank.
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
   *L�schen s�mtlicher Notizquellen eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gel�scht wird)
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
   * Auslesen des zugeh�rigen Notiz-Objekts zu einem gegebenen
   * Notizquelle.
   */
  public Notiz getNotizId(Notizquelle nq) {
    return NotizMapper.notizMapper().nachNotizquelleDerNotizSuchen(nq.getNotizquelleId());
  }

}
