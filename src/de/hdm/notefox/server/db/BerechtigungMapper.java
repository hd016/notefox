package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.List;

import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfuellen den Zweck unsere Objekte auf eine relationale
 * Datenbank abzubilden. Durch die bereitgestellten Methoden kann man Objekte
 * anlegen, editieren, l�schen, teilen und speichern. Objekte k�nnen auf
 * diese Weise in Datenbankstrukturen umgewandelt werden. Datenbankstrukturen
 * k�nnen umgekehrt auch in Objekte umgewandelt werden.
 * <p>
 * 
 * @see NotizMapper, NotizbuchMapper, NotizquelleMapper, NutzerMapper,
 *      DatumMapper
 */

public class BerechtigungMapper {
	/**
	 * 
	 * Die Klasse BerechtigungMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
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
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>BerechtigungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BerechtigungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
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
	 * Notiz nach Berechtigung suchen. * als return: Berechtigung-Objekt oder
	 * bei nicht vorhandener Id/DB-Tupel null.
	 */
	public Berechtigung nachBerechtigungIdSuchen(int id) {
		// Es wird eine DB-Verbindung angeschafft
		Connection con = DBConnection.connection();

		try {
			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
			// angelegt
			Statement stmt = con.createStatement();

			// Das Statement wird ausgefuellt und an die Datebank verschickt
			ResultSet rs = stmt
					.executeQuery("SELECT berechtigungId, berechtigungsart  FROM berechtigung "
							+ "WHERE berechtigungId=" + id + " ORDER BY berechtigungId ASC");

			/*
			 * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis
			 * vorliegt. Man erh�lt maximal 1 Tupel, da es sich bei id um
			 * einen Prim�rschl�ssel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt �berf�hrt
				// werden.
				Berechtigung be = new Berechtigung();
				be.setBerechtigungId(rs.getInt("berechtigungId"));
				be.setBerechtigungsart(Berechtigungsart.valueOf(rs
						.getString("berechtigungsart")));

				return be;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}
	
	public void nachAllenBerechtigungenDerNotizobjekteSuchen(
			BusinessObject notizobjekt) {
		nachAllenBerechtigungenDerNotizobjekteSuchen(notizobjekt, null);
	}

	public void nachAllenBerechtigungenDerNotizobjekteSuchen(
			BusinessObject notizobjekt, Nutzer nutzer) {
		int id = notizobjekt.getId();

		// Es wird eine DB-Verbindung angeschafft
		Connection con = DBConnection.connection();

		try {
			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
			// angelegt
			Statement stmt = con.createStatement();

			// Das Statement wird ausgefuellt und an die Datebank verschickt
			ResultSet rs = stmt
					.executeQuery("SELECT berechtigungId, berechtigungsArt FROM berechtigung "
							+ "WHERE "
							+ ((notizobjekt instanceof Notiz) ? "notiz"
									: "notizbuch") + "=" + id + (nutzer != null ? "AND berechtigter =" + nutzer.getNutzerId(): ""));

			/*
			 * An dieser Stelle kann man pr�fen ob bereits ein Ergebnis
			 * vorliegt. Man erh�lt maximal 1 Tupel, da es sich bei id um
			 * einen Prim�rschl�ssel handelt.
			 */
			if (rs.next()) {
				/*
				 * Das daraus ergebene Tupel muss in ein Objekt �berf�hrt
				 * werden.
				 */
				Berechtigung be = new Berechtigung();
				be.setBerechtigungId(rs.getInt("berechtigungId"));
				be.setBerechtigungsart(Berechtigungsart.valueOf(rs
						.getString("berechtigungsart")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public Berechtigung anlegenBerechtigung(Berechtigung be) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      /*
	       * Der h�chste Prim�rschl�sselwert wird �berpr�ft
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(berechtigungId) AS maxid "
	          + "FROM berechtigung ");

	      // Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig sein
	      if (rs.next()) {
	    	   /*
	           * nb erh�lt den bisher maximalen, nun um 1 inkrementierten
	           * Prim�rschl�ssel.
	           */
	        be.setBerechtigungId(rs.getInt("maxid") + 1);

	        stmt = con.createStatement();

	        // Hier erfolgt die entscheidende Einf�geoperation
	     
			// Hier erfolgt die entscheidende Einf�geoperation
			String sql = "INSERT INTO berechtigung (berechtigungId, berechtigungsart, notiz, notizbuch, berechtigter ) "
					+ "VALUES ("
					+ be.getBrechtigungId()
					+ ", \""
					+ be.getBerechtigungsart()
					+ "\", "
					+ (be.getNotiz() !=null ? be.getNotiz().getId():null)
					+ ", "
					+ (be.getNotizbuch() != null ? be.getNotizbuch().getId() : null)
					+ ", "+be.getBerechtigter().getNutzerId()+") ";
			System.out.println(sql);
			stmt.executeUpdate(sql);
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
	    return be;
	  }
	
	
//	public static void main(String[] args) {
////	Berechtigung berechtigung = BerechtigungMapper.berechtigungMapper().nachBerechtigungSuchen(id)
//		
//		NutzerMapper nutzerMapper = NutzerMapper.nutzerMapper();
//		Nutzer nutzer = nutzerMapper.nachNutzerIdSuchen(1000);
//		
//		NotizMapper notizMapper = NotizMapper.notizMapper();
//		Notiz notiz = notizMapper.nachNotizIdSuchen(1);
//		
//		NotizbuchMapper notizbuchMapper = NotizbuchMapper.notizbuchMapper();
//		Notizbuch notizbuch = notizbuchMapper.nachNotizbuchIdSuchen(2002);
//		
//		Berechtigung berechtigung = new Berechtigung();
//		berechtigung.getBrechtigungId();
//		berechtigung.setBerechtigungsart(Berechtigungsart.EDITIEREN);	
//		berechtigung.setNotiz(notiz);
//		berechtigung.setNotizbuch(notizbuch);
//		berechtigung.setBerechtigter(nutzer);
//		
//
//	
//	BerechtigungMapper berechtigungMapper = BerechtigungMapper.berechtigungMapper();
//	berechtigungMapper.anlegenBerechtigung(berechtigung);
//
//	}
	
	
	
	
	  public void berechtigungVerweigern(Berechtigung be) {
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM berechtigung " + "WHERE berechtigungId=" + be.getBrechtigungId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
		  }


}
