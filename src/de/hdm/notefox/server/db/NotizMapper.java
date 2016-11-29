package de.hdm.notefox.server.db;


import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * „Unsere Mapper-Klassen erfüllen den Zweck unsere Objekte auf eine relationale Datenbankabzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, löschen, teilen 
 * und speichern.Objekte können auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen können umgekehrt auch in Objekte umgewandelt werden.“
 */


public class NotizMapper {

	  /**
	   * Eimalige Instantierung der Klasse NotizMapper (Singleton)
	   * Einmal für sämtliche Instanzen dieser Klasse vorhanden, 
	   * speichert die eizige Instanz dieser Klasse
	   */
	
  private static NotizMapper notizMapper = null;

  /**
   * Konstruktor verhindert durch protected weitere Instanzen aus dieser Klasse zu erzeugen
   */
  protected NotizMapper() {
  }

  /**
   * NotizMapper-Objekt
   * Die statische Methode verhindert, 
   * dass von einer Klasse mehr als ein Objekt gebildet werden kann. 
   * (Bewahrung der Singleton-Eigenschaft)
   */
  
  public static NotizMapper notizMapper() {
    if (notizMapper == null) {
      notizMapper = new NotizMapper();
    }

    return notizMapper;
  }

  /**
   * Notiz nach NotizId suchen.   * 
   * als return: Notiz-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notiz nachNotizIdSuchen(int id) {
	// Es wird eine DB-Verbindung angeschafft 
    Connection con = DBConnection.connection();

    try {
    //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

   // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT id, notizId FROM notiz "
          + "WHERE id=" + id + " ORDER BY notizId");

   /*
    * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
    * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
    */
      if (rs.next()) {
    	//Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Notiz no = new Notiz();
    	  no.setId(rs.getInt("id"));
          no.setNotizId(rs.getInt("NotizId"));
          no.setEigentuemer(rs.getString("eigentuemer"));
          no.setErstelldatum(rs.getDate("erstelldatum"));
          no.setInhalt(rs.getString("inhalt"));
          no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
          no.setSubtitel(rs.getString("subtitel"));
          no.setTitel(rs.getString("titel"));
        return no;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }
  
  /**
   * Notiz nach NotizquelleId suchen.   * 
   * als return: Notiz-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notiz nachNotizquelleIdSuchen(int id) {
	// Es wird eine DB-Verbindung angeschafft 
    Connection con = DBConnection.connection();

    try {
    //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

   // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId FROM notizquelle "
          + "WHERE notizquelleId=" + id + " ORDER BY notizquelleId");

      /*
       * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
       * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
       */
      if (rs.next()) {
    //Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Notiz no = new Notiz();
        no.setId(rs.getInt("id"));
        no.setNotizId(rs.getInt("NotizId"));
        no.setEigentuemer(rs.getString("eigentuemer"));
        no.setErstelldatum(rs.getDate("erstelldatum"));
        no.setInhalt(rs.getString("inhalt"));
        no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
        no.setSubtitel(rs.getString("subtitel"));
        no.setTitel(rs.getString("titel"));
        return no;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }
  
  /**
   * Notiz nach FaelligkeitId suchen.   * 
   * als return: Notiz-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
   */
  public Notiz nachFaelligkeitIdSuchen(int id) {
	// Es wird eine DB-Verbindung angeschafft
    Connection con = DBConnection.connection();

    try {
    //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

   // Das Statement wird ausgefüllt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId FROM notizquelle "
          + "WHERE notizquelleId=" + id + " ORDER BY notizquelleId");

      /*
       * An dieser Stelle kann man prüfen ob bereits ein Ergebnis vorliegt. 
       * Man erhält maximal 1 Tupel, da es sich bei id um einen Primärschlüssel handelt.
       */
      if (rs.next()) {
    //Das daraus ergebene Tupel muss in ein Objekt überführt werden.
    	  Notiz no = new Notiz();
        no.setId(rs.getInt("id"));
        no.setNotizId(rs.getInt("NotizId"));
        no.setEigentuemer(rs.getString("eigentuemer"));
        no.setErstelldatum(rs.getDate("erstelldatum"));
        no.setInhalt(rs.getString("inhalt"));
        no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
        no.setSubtitel(rs.getString("subtitel"));
        no.setTitel(rs.getString("titel"));
        return no;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }



  /**
   * Auslesen aller Notizen.
   * 
   */
  
  public Vector<Notiz> nachAllenNotizenSuchen() {
    Connection con = DBConnection.connection();

  //Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Notiz> result = new Vector<Notiz>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT notizId" + "FROM notiz "
          + " ORDER BY notizId");

   // Jetzt werden die Einträge durchsucht und für jedes gefundene ein Notiz Objekt erstellt
      while (rs.next()) {
    	  Notiz no = new Notiz();
    	  no.setId(rs.getInt("id"));
          no.setNotizId(rs.getInt("NotizId"));
          no.setEigentuemer(rs.getString("eigentuemer"));
          no.setErstelldatum(rs.getDate("erstelldatum"));
          no.setInhalt(rs.getString("inhalt"));
          no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
          no.setSubtitel(rs.getString("subtitel"));
          no.setTitel(rs.getString("titel"));
   

   // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zurückgegeben
    return result;
  }
  
  /**
   * Auslesen aller Notizen eines durch FremdschlÃ¼ssel (NotizbuchId) gegebenen
   * Notizbuches.
   * 
   * @param notizbuchId SchlÃ¼ssel des zugehÃ¶rigen Notizbuches.
   * @return Ein Vektor mit Notiz-Objekten, die sÃ¤mtliche Notizen des
   *         betreffenden Notizbuches reprÃ¤sentieren. Bei evtl. Exceptions wird ein
   *         partiell gefÃ¼llter oder ggf. auch leerer Vetor zurÃ¼ckgeliefert.
   */
  public Vector<Notiz> nachAllenNotizenDesNotizbuchesSuchen(int notizbuchId) {
    Connection con = DBConnection.connection();
    Vector<Notiz> result = new Vector<Notiz>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, eigentuemer, erstelldatum, inhalt, modifikationsdatum,"
          		+ " subtitel, titel, notizId FROM notizen "
              + "WHERE notizId=" + notizbuchId + " ORDER BY id");

      // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt erstellt.
      while (rs.next()) {
        Notiz no = new Notiz();
        no.setId(rs.getInt("id"));
        no.setNotizId(rs.getInt("notizid"));
        no.setEigentuemer(rs.getString("eigentuemer"));
        no.setErstelldatum(rs.getDate("erstelldatum"));
        no.setInhalt(rs.getString("inhalt"));
        no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
        no.setSubtitel(rs.getString("subtitel"));
        no.setTitel(rs.getString("titel"));
        

        // HinzufÃ¼gen des neuen Objekts zum Ergebnisvektor
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zurÃ¼ckgeben
    return result;
  }

  /**
   * Auslesen aller Notizen eines durch Fremdschlüssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notiz> nachEigentuemerSuchen(int notizId) {
    Connection con = DBConnection.connection();
    Vector<Notiz> result = new Vector<Notiz>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT id, owner FROM notiz "
          + "WHERE owner=" + notizId + " ORDER BY id");

   // Jetzt werden die Einträge durchsucht und für jedes gefundene ein Notiz Objekt erstellt
      while (rs.next()) {
    	  Notiz no = new Notiz();
    	  no.setId(rs.getInt("id"));
          no.setNotizId(rs.getInt("NotizId"));
          no.setEigentuemer(rs.getString("eigentuemer"));
          no.setErstelldatum(rs.getDate("erstelldatum"));
          no.setInhalt(rs.getString("inhalt"));
          no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
          no.setSubtitel(rs.getString("subtitel"));
          no.setTitel(rs.getString("titel"));

    // Dem Ergebnisvektor wird ein neues Objekt hinzugefügt
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }


    // Der Ergebnisvektor wird zurückgegeben
    return result;
  }

  /**
   * Auslesen aller Notizen eines Nutzers
   */
  public Vector<Notiz> nachEigentuemerSuchen(Nutzer eigentuemer) {

    //Die Id des Notiz Objekts wird ausgelesen, und die Methode ist für die weitere Bearbeitung zuständig.
     
    return nachEigentuemerSuchen(eigentuemer.getNutzerId());
  }

  /**
   * Anlegen einer Notiz.
   * 
   */
  public Notiz anlegenNotiz(Notiz no) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

    //Der höchste Primärschlüsselwert wird überprüft
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM notiz ");

    //Sollte etwas zurückgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    //a kriegt nun den maximalen Primärschlüssel, welcher mit dem Wert 1 inkrementiert wird
        no.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

     //Hier erfolgt die entscheidende Einfügeoperation
        stmt.executeUpdate("INSERT INTO notiz (id, notizId) " + "VALUES ("
            + no.getId() + "," + no.getNotizId() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Notizen geben, so werden diese zurückgegeben
     * 
     *So besteht die Möglichkeit anzudeuten ob sich ein Objekt verändert hat, während die Methode ausgeführt wurde
     */
    return no;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Notiz update(Notiz no) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE notiz " + "SET notizId=\"" + no.getNotizId()
          + "\" " + "WHERE id=" + no.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

  //Um ähnliche Strukturen wie zu anlegenNotiz(Notiz a) zu wahren, geben wir nun a zurück
    return no;
  }

  /**
   * Löschen der Daten eines Notiz-Objekts aus der Datenbank.
   */
  public void loeschenNotiz(Notiz no) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notiz " + "WHERE id=" + no.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   *Löschen sämtlicher Notizen eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gelöscht wird)
   */
  public void loeschenNotizVon(Nutzer n) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM notiz " + "WHERE nutzerId=" + n.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugehörigen Nutzer-Objekts zu einem gegebenen
   * Notiz.
   */
  public Nutzer getNutzerId(Notiz no) {
    return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(no.getNotizId());
  }

}
