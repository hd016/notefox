package de.hdm.notefox.server.db;


import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * �Unsere Mapper-Klassen erf�llen den Zweck unsere Objekte auf eine relationale Datenbankabzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, l�schen, teilen 
 * und speichern.Objekte k�nnen auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen k�nnen umgekehrt auch in Objekte umgewandelt werden.�
 */


public class NotizMapper {

	  /**
	   * Eimalige Instantierung der Klasse NotizMapper (Singleton)
	   * Einmal f�r s�mtliche Instanzen dieser Klasse vorhanden, 
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

   // Das Statement wird ausgef�llt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT id, notizId FROM notiz "
          + "WHERE id=" + id + " ORDER BY notizId");

   /*
    * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
    * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
    */
      if (rs.next()) {
    	//Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
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

   // Das Statement wird ausgef�llt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId FROM notizquelle "
          + "WHERE notizquelleId=" + id + " ORDER BY notizquelleId");

      /*
       * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
       * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
       */
      if (rs.next()) {
    //Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
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

   // Das Statement wird ausgef�llt und an die Datebank verschickt
      ResultSet rs = stmt.executeQuery("SELECT notizquelleId FROM notizquelle "
          + "WHERE notizquelleId=" + id + " ORDER BY notizquelleId");

      /*
       * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
       * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
       */
      if (rs.next()) {
    //Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
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

   // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Notiz Objekt erstellt
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
   

   // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Der Ergebnisvektor wird zur�ckgegeben
    return result;
  }
  
  /**
   * Auslesen aller Notizen eines durch Fremdschlüssel (NotizbuchId) gegebenen
   * Notizbuches.
   * 
   * @param notizbuchId Schlüssel des zugehörigen Notizbuches.
   * @return Ein Vektor mit Notiz-Objekten, die sämtliche Notizen des
   *         betreffenden Notizbuches repräsentieren. Bei evtl. Exceptions wird ein
   *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
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

      // Für jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt erstellt.
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
        

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zurückgeben
    return result;
  }

  /**
   * Auslesen aller Notizen eines durch Fremdschl�ssel (NutzerId) gegebenen
   * Nutzern.
   */
  public Vector<Notiz> nachEigentuemerSuchen(int notizId) {
    Connection con = DBConnection.connection();
    Vector<Notiz> result = new Vector<Notiz>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT id, owner FROM notiz "
          + "WHERE owner=" + notizId + " ORDER BY id");

   // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Notiz Objekt erstellt
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

    // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(no);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }


    // Der Ergebnisvektor wird zur�ckgegeben
    return result;
  }

  /**
   * Auslesen aller Notizen eines Nutzers
   */
  public Vector<Notiz> nachEigentuemerSuchen(Nutzer eigentuemer) {

    //Die Id des Notiz Objekts wird ausgelesen, und die Methode ist f�r die weitere Bearbeitung zust�ndig.
     
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

    //Der h�chste Prim�rschl�sselwert wird �berpr�ft
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM notiz ");

    //Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    //a kriegt nun den maximalen Prim�rschl�ssel, welcher mit dem Wert 1 inkrementiert wird
        no.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

     //Hier erfolgt die entscheidende Einf�geoperation
        stmt.executeUpdate("INSERT INTO notiz (id, notizId) " + "VALUES ("
            + no.getId() + "," + no.getNotizId() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Sollte es korrigierte Notizen geben, so werden diese zur�ckgegeben
     * 
     *So besteht die M�glichkeit anzudeuten ob sich ein Objekt ver�ndert hat, w�hrend die Methode ausgef�hrt wurde
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

  //Um �hnliche Strukturen wie zu anlegenNotiz(Notiz a) zu wahren, geben wir nun a zur�ck
    return no;
  }

  /**
   * L�schen der Daten eines Notiz-Objekts aus der Datenbank.
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
   *L�schen s�mtlicher Notizen eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gel�scht wird)
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
   * Auslesen des zugeh�rigen Nutzer-Objekts zu einem gegebenen
   * Notiz.
   */
  public Nutzer getNutzerId(Notiz no) {
    return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(no.getNotizId());
  }

}
