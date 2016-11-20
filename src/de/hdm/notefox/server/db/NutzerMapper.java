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

public class NutzerMapper {

  /**
   * Eimalige Instantiierung der Klasse NutzerMapper (Singleton)
   * Einmal f�r s�mtliche Instanzen dieser Klasse vorhanden, 
   * speichert die einzige Instanz dieser Klasse
   */
	
  private static NutzerMapper nutzerMapper = null;

  /**
   * Konstruktor verhindert durch protected weitere Instanzen aus dieser Klasse zu erzeugen
   */
  protected NutzerMapper() {
  }

  /**
   * NutzerMapper-Objekt
   * Die statische Methode verhindert, 
   * dass von einer Klasse mehr als ein Objekt gebildet werden kann. 
   * (Bewahrung der Singleton-Eigenschaft)
   */
  
  public static NutzerMapper nutzerMapper() {
    if (nutzerMapper == null) {
      nutzerMapper = new NutzerMapper();
    }

    return nutzerMapper;
  }

  /**
   * Nutzer nach NutzerId suchen.   * 
   * als return: Nutzer-Objekt oder bei nicht vorhandener NutzerId/DB-Tupel null.
   */
  public Nutzer nachNutzerIdSuchen(int nutzerId) {
	// Es wird eine DB-Verbindung angeschafft 
    Connection con = DBConnection.connection();

    try {
    //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
      Statement stmt = con.createStatement();

   // Das Statement wird ausgef�llt und an die Datebank verschickt
      ResultSet rs = stmt
          .executeQuery("SELECT nutzerId, name FROM nutzer "
              + "WHERE nutzerId=" + nutzerId + " ORDER BY name");

      /*
       * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
       * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
       */
      if (rs.next()) {
    	//Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
        Nutzer c = new Nutzer();
        c.setNutzerId(rs.getInt("NutzerId"));
        c.setName(rs.getString("name"));

        return c;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Nutzer.
   * 
   */
  public Vector<Nutzer> nachAllenNutzernSuchen() {
    Connection con = DBConnection.connection();
    
 // Der Vektor der das Ergebnis bereitstellen soll wird vorbereitet
    Vector<Nutzer> result = new Vector<Nutzer>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT nutzerId, name "
          + "FROM nutzer " + "ORDER BY name");

   // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Nutzer Objekt erstellt
      
      while (rs.next()) {
    	Nutzer c = new Nutzer();
        c.setNutzerId(rs.getInt("nutzerId"));
        c.setName(rs.getString("name"));

     // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(c);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    // Der Ergebnisvektor wird zur�ckgegeben
    return result;
  }

  /**
   * Auslesen aller Nutzer-Objekte mit gegebenem Namen
   * 
   */
  public Vector<Nutzer> nachNutzerNamenSuchen(String name) {
    Connection con = DBConnection.connection();
    Vector<Nutzer> result = new Vector<Nutzer>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT nutzerId, name "
          + "FROM nutzer " + "WHERE name LIKE '" + name
          + "' ORDER BY name");

   // Jetzt werden die Eintr�ge durchsucht und f�r jedes gefundene ein Nutzer Objekt erstellt
      
      while (rs.next()) {
        Nutzer c = new Nutzer();
        c.setNutzerId(rs.getInt("NutzerId"));
        c.setName(rs.getString("name"));

     // Dem Ergebnisvektor wird ein neues Objekt hinzugef�gt
        result.addElement(c);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    // Der Ergebnisvektor wird zur�ckgegeben
    return result;
  }

  /**
   * Anlegen eines Nutzers.
   * 
   */
  public Nutzer anlegenNutzer(Nutzer c) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

    //Der h�chste Prim�rschl�sselwert wird �berpr�ft
      
      ResultSet rs = stmt.executeQuery("SELECT MAX(NutzerId) AS maxid "
          + "FROM nutzer ");

   //Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	  /*
           * c kriegt nun den maximalen Prim�rschl�ssel, welcher mit dem Wert 1 inkrementiert wird
           */
        c.setNutzerId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

      //Hier erfolgt die entscheidende Einf�geoperation
        stmt.executeUpdate("INSERT INTO nutzer (NutzerId, name) "
            + "VALUES (" + c.getNutzerId() + ",'" + c.getName() + "')");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    /*
     * Sollte es korrigierte Nutzer geben, so werden diese zur�ckgegeben
     * 
     * So besteht die M�glichkeit anzudeuten ob sich ein Objekt ver�ndert hat, w�hrend die Methode ausgef�hrt wurde
     */
    return c;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   */
  public Nutzer update(Nutzer c) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE nutzer " + "SET name=\""
          + c.getName() + "\", " + "\" "
          + "WHERE NutzerId=" + c.getNutzerId());

    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    // Um �hnliche Strukturen wie zu anlegenNotizquelle(Notizquelle a) zu wahren, geben wir nun a zur�ck
    return c;
  }

  /**
   * L�schen der Daten eines Nutzer-Objekts aus der Datenbank.
   */
  public void loeschenNutzer(Nutzer c) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM nutzer " + "WHERE NutzerId=" + c.getNutzerId());
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Auslesen der zugeh�rigen Notizen-Objekte zu einem gegebenen
   * Nutzer.
   */
  
  public Vector<Notiz> getNotizOf(Nutzer c) {
    return NotizMapper.notizMapper().nachEigentuemerSuchen(c);
  }
  
  /**
   * Auslesen der zugeh�rigen Notizb�cher-Objekte zu einem gegebenen
   * Nutzer.
   */
  public Vector<Notizbuch> getNotizbuchOf(Nutzer c) {
	return NotizbuchMapper.notizbuchMapper().nachEigentuemerSuchen(c);
	  }
}
