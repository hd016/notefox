package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfuellen den Zweck unsere Objekte auf eine relationale Datenbank abzubilden. 
 * Durch die bereitgestellten Methoden kann man Objekte anlegen, editieren, l�schen, teilen 
 * und speichern. Objekte k�nnen auf diese Weise in Datenbankstrukturen umgewandelt werden. 
 * Datenbankstrukturen k�nnen umgekehrt auch in Objekte umgewandelt werden.
 * <p>
 * 
 * @see NotizMapper, NotizbuchMapper, NotizquelleMapper, NutzerMapper, DatumMapper
 */

public class BerechtigungMapper {
	/**
	 * 
	 * Die Klasse BerechtigungMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	
	 private static BerechtigungMapper berechtigungMapper = null;

	  /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   * 
	   */
	  protected BerechtigungMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>BerechtigungMapper.berechtigungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
	   * Instanz von <code>BerechtigungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> BerechtigungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>BerechtigungMapper</code>-Objekt.
	   * @see berechtigungMapper
	   */
	  
	  public static BerechtigungMapper berechtigungMapper() {
	    if (berechtigungMapper == null) {
	    	berechtigungMapper = new BerechtigungMapper();
	    }

	    return berechtigungMapper;
	  }
	  
	  
	  
	  /**
	   * Notiz nach Berechtigung suchen.   * 
	   * als return: Berechtigung-Objekt oder bei nicht vorhandener Id/DB-Tupel null.
	   */
	  public Berechtigung nachBerechtigungSuchen(int id) {
		// Es wird eine DB-Verbindung angeschafft 
	    Connection con = DBConnection.connection();

	    try {
	    //Es wird ein leeres SQL Statement von dem Connector (JDBC) angelegt
	      Statement stmt = con.createStatement();

	   // Das Statement wird ausgefuellt und an die Datebank verschickt
	      ResultSet rs = stmt.executeQuery("SELECT id, name  FROM berechtigung "
	          + "WHERE id=" + id + " ORDER BY id ASC");

	   /*
	    * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis vorliegt. 
	    * Man erh�lt maximal 1 Tupel, da es sich bei id um einen Prim�rschl�ssel handelt.
	    */
	      if (rs.next()) {
	    	//Das daraus ergebene Tupel muss in ein Objekt �berf�hrt werden.
	    	  Berechtigung be = new Berechtigung();
	    	  be.setBerechtigungId(rs.getInt("berechtigungId"));
	    	  be.setBerechtigungName(rs.getString("berechtigungName"));
	    	  
	    	  //TODO

	    	  
	        return be;
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	      return null;
	    }

	    return null;
	  }	  
	  
	  
	  
	  
	  public void berechtigungErteilen(Berechtigung be){
		  //TODO
	  }

	
	
	

}
