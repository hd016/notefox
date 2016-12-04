package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Datum;
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
  public Datum nachFaelligkeitDesNutzerSuchen(int id) {
	// DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
    	// Leeres SQL-Statement (JDBC) anlegen
    	Statement stmt = con.createStatement();

    	// Statement ausfuellen und als Query an die DB schicken
    	ResultSet rs = stmt.executeQuery("SELECT nutzer.nutzerId, datum.faelligkeitId, datum.status, datum.faelligkeitsdatum FROM nutzer, datum "
          + "WHERE faelligkeitId=" + id + " ORDER BY faelligkeitsdatum ASC");

     /*
      * Da id Primaerschluessel ist, kann max. nur ein Tupel zurueckgegeben
      * werden. Pruefe, ob ein Ergebnis vorliegt.
      */
      if (rs.next()) {
    	// Ergebnis-Tupel in Objekt umwandeln
    	Datum d = new Datum();
        d.setFaelligkeitId(rs.getInt("faelligkeitId"));
        d.setStatus(rs.getBoolean("status"));
        d.setFaelligkeitsdatum(rs.getDate("faelligkeitsdatum"));
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
   * Auslesen aller F�lligkeiten eines durch Fremdschlüssel (Id) gegebener
   * Notiz.
   * 
   * @param Id Schlüssel der zugehörigen Notiz.
   * @return Ein Vektor mit Datum-Objekten, die sämtliche F�lligkeiten der
   *         betreffenden Notiz repräsentieren. Bei evtl. Exceptions wird ein
   *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
   */
  public Vector<Datum> nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(int id) {
    Connection con = DBConnection.connection();
    Vector<Datum> result = new Vector<Datum>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT nutzer.id, notiz.id, datum.faelligkeitId, datum.status, datum.faelligkeitsdatum FROM nutzer, notiz "
              + "LEFT JOIN datum ON datum.faelligkeitId = notiz.id" + id + " ORDER BY datum.faelligkeitsdatum ASC");
      
      // Für jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
      while (rs.next()) {
        Datum d = new Datum();
//      d.setId(rs.getInt("Id")); --> muss noch gekl�rt werden!
        d.setFaelligkeitId(rs.getInt("faelligkeitId"));
        d.setStatus(rs.getBoolean("status"));
        d.setFaelligkeitsdatum(rs.getDate("faelligkeitsdatum"));


        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(d);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
    // Ergebnisvektor zurückgeben
    return result;
  }  
  
  
  
  /**
   * Auslesen aller Datum-Objekte.
   * 
   */
  
  public Vector<Datum> nachAllenDatumObjektenDesNutzerSuchen() {
    Connection con = DBConnection.connection();

    // Ergebnisvektor vorbereiten
    Vector<Datum> result = new Vector<Datum>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT faelligkeitID" + "FROM datum "
          + " ORDER BY faelligkeitID");

      // F�r jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt erstellt.
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
       *  Der h�chste Prim�rschl�sselwert wird �berpr�ft
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM datum ");

      // Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
      if (rs.next()) {
    	   /*
           * a erh�lt den bisher maximalen, nun um 1 inkrementierten
           * Prim�rschl�ssel.
           */
        d.setFaelligkeitId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Hier erfolgt die entscheidende Einf�geoperation
        stmt.executeUpdate("INSERT INTO datum (faelligkeitId, status, faelligkeitsdatum) " + "VALUES ("
        	+ "," + d.getFaelligkeitId() + d.isStatus() + d.getFaelligkeitsdatum() + ")");
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

      stmt.executeUpdate("UPDATE datum " + "SET faelligkeit=\"" + d.getFaelligkeitId()
          + "\" " + "WHERE faelligkeitId=" + d.getFaelligkeitId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu anlegenDatum(Datum d) zu wahren, geben wir d zur�ck
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
   *L�schen s�mtlicher Datum-Objekte eines Nutzers 
   *(sollte dann aufgerufen werden, bevor ein Nutzer-Objekt gel�scht wird)
   */
  public void loeschenDatumVon(Nutzer n) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM datum " + "WHERE id=" + n.getNutzerId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }
  
  
  /**
   * Auslesen des zugeh�rigen Notiz-Objekts zu einem gegebenen
   * Datum.
   */
  public Notiz getId(Datum d) {
    return NotizMapper.notizMapper().nachFaelligkeitIdSuchen(d.getFaelligkeitId());
  }


}
