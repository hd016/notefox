package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * ï¿½Unsere Mapper-Klassen erfï¿½llen den Zweck unsere Objekte auf eine
 * relationale Datenbankabzubilden. Durch die bereitgestellten Methoden kann man
 * Objekte anlegen, editieren, lï¿½schen, teilen und speichern.Objekte kï¿½nnen
 * auf diese Weise in Datenbankstrukturen umgewandelt werden.
 * Datenbankstrukturen kï¿½nnen umgekehrt auch in Objekte umgewandelt werden.ï¿½
 */

public class NotizMapper {

	/**
	 * Eimalige Instantierung der Klasse NotizMapper (Singleton) Einmal fï¿½r
	 * sï¿½mtliche Instanzen dieser Klasse vorhanden, speichert die eizige
	 * Instanz dieser Klasse
	 */

	private static NotizMapper notizMapper = null;
	private static Date erstelldatum;
	private static Date modifikationsdatum;

	/**
	 * Konstruktor verhindert durch protected weitere Instanzen aus dieser
	 * Klasse zu erzeugen
	 */
	protected NotizMapper() {
	}

	/**
	 * NotizMapper-Objekt Die statische Methode verhindert, dass von einer
	 * Klasse mehr als ein Objekt gebildet werden kann. (Bewahrung der
	 * Singleton-Eigenschaft)
	 */

	public static NotizMapper notizMapper() {
		if (notizMapper == null) {
			notizMapper = new NotizMapper();
		}

		return notizMapper;
	}

	/**
	 * Notiz nach NotizTitel suchen. * als return: Notiz-Objekt oder bei nicht
	 * vorhandener Id/DB-Tupel null.
	 */
	public Notiz nachNotizIdSuchen(int id) {
		// Es wird eine DB-Verbindung angeschafft
		Connection con = DBConnection.connection();

		try {
			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
			// angelegt
			Statement stmt = con.createStatement();

			// Das Statement wird ausgefuellt und an die Datebank verschickt
			ResultSet rs = stmt
					.executeQuery("SELECT*  FROM notiz " 
							+ "WHERE id=" + id);

			/*
			 * An dieser Stelle kann man prï¿½fen ob bereits ein Ergebnis
			 * vorliegt. Man erhï¿½lt maximal 1 Tupel, da es sich bei id um
			 * einen Primï¿½rschlï¿½ssel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt ï¿½berfï¿½hrt
				// werden.
				Notiz no = new Notiz();
				no.setId(rs.getInt("id"));
				no.setSubtitel(rs.getString("subtitel"));
				no.setTitel(rs.getString("titel"));
				return no;
				// no.setEigentuemer(null); 
				// no.setErstelldatum(rs.getDate("erstelldatum"));
				// no.setInhalt(rs.getString("inhalt"));
				// no.setModifikationsdatum(rs.getDate("modifikationsdatum"));

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Notiz nach NotizquelleId suchen. * als return: Notiz-Objekt oder bei
	 * nicht vorhandener Id/DB-Tupel null.
	 */
	public Notiz nachNotizquelleDerNotizSuchen(int id) {
		// Es wird eine DB-Verbindung angeschafft
		Connection con = DBConnection.connection();

		try {
			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
			// angelegt
			Statement stmt = con.createStatement();

			// Das Statement wird ausgefï¿½llt und an die Datebank verschickt
			ResultSet rs = stmt
					.executeQuery("SELECT notiz.*, notizquelle.* FROM notiz LEFT JOIN notiz ON notiz.id = notizquelle.notizquelleId "
							+ " ORDER BY id");

			/*
			 * An dieser Stelle kann man prï¿½fen ob bereits ein Ergebnis
			 * vorliegt. Man erhï¿½lt maximal 1 Tupel, da es sich bei id um
			 * einen Primï¿½rschlï¿½ssel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt ï¿½berfï¿½hrt
				// werden.
				Notiz no = new Notiz();
				no.setId(rs.getInt("id"));
				Nutzer nutzer = new Nutzer();
		    	nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
		    	nutzer.setEmail(rs.getString("nutzer.email"));
		        no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("titel"));
				no.setSubtitel(rs.getString("subtitel"));
				no.setErstelldatum(rs.getDate("erstelldatum"));
				no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				no.setInhalt(rs.getString("inhalt"));
				return no;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}
	
	
	/**
	  * Loeschen der Faelligkeitsdatum eines <code>Notiz</code>-Objekts aus der Datenbank.
	  * 
	  */
	   public void loeschenFaelligkeitsdatum(Notiz no) {
		     Connection con = DBConnection.connection();
		 
		     try {
		       Statement stmt = con.createStatement();
		 
		       stmt.execute("DELETE FROM notiz " + "WHERE faelligkeitsdatum=" + no.getFaelligkeitsdatum());
		 
		     }
		     catch (SQLException e2) {
		       e2.printStackTrace();
		     }
		   }

	   
		/**
		  * Faelligkeitsstatus eines <code>Notiz</code>-Objekts aus der Datenbank prüfen.
		  * 
		  */
	   public void pruefenFaelligkeitsdatum(Notiz no){
			// Es wird eine DB-Verbindung angeschafft
			Connection con = DBConnection.connection();
			Boolean fälligkeit = false;
			 try {
			       Statement stmt = con.createStatement();
			 
//			       stmt.executeQuery("SELECT faelligkeitsstatus FROM notiz"); 
//					rs = stmt.executeQuery(query);
//					while (rs.next()) {
//						fälligkeit = rs.getString("faelligkeitsstatus");
//					}
					
					
					if(fälligkeit==true&&no.getFaelligkeitsstatus()==true)
					{
						
					}
			     }
			 
			 
			     catch (SQLException e2) {
			       e2.printStackTrace();
			     }
			   }
			
	   
	   
//	/**
//	 * Notiz nach FaelligkeitId suchen. * als return: Notiz-Objekt oder bei
//	 * nicht vorhandener Id/DB-Tupel null.
//	 */
//	public Notiz nachFaelligkeitIdSuchen(int id) {
//		// Es wird eine DB-Verbindung angeschafft
//		Connection con = DBConnection.connection();
//
//		try {
//			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
//			// angelegt
//			Statement stmt = con.createStatement();
//
//			// Das Statement wird ausgefï¿½llt und an die Datebank verschickt
//			ResultSet rs = stmt
//					.executeQuery("SELECT notiz*, datum.* FROM notiz ON notiz.notiz.Id = datum.faelligkeitId "
//							+ " ORDER BY id");
//
//			/*
//			 * An dieser Stelle kann man prï¿½fen ob bereits ein Ergebnis
//			 * vorliegt. Man erhï¿½lt maximal 1 Tupel, da es sich bei id um
//			 * einen Primï¿½rschlï¿½ssel handelt.
//			 */
//			if (rs.next()) {
//				// Das daraus ergebene Tupel muss in ein Objekt ï¿½berfï¿½hrt
//				// werden.
//				Notiz no = new Notiz();
//				no.setId(rs.getInt("id"));
//				Nutzer nutzer = new Nutzer();
//		    	nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
//		    	nutzer.setEmail(rs.getString("nutzer.email"));
//		        no.setEigentuemer(nutzer);
//				no.setTitel(rs.getString("titel"));
//				no.setSubtitel(rs.getString("subtitel"));
//				no.setErstelldatum(rs.getDate("erstelldatum"));
//				no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
//				no.setInhalt(rs.getString("inhalt"));
//				return no;
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//			return null;
//		}
//
//		return null;
//	}

	/**
	 * Auslesen aller Notizen.
	 * 
	 */

	public List<Notiz> nachAllenNotizenDesNutzerSuchen() {
		Connection con = DBConnection.connection();

		// Die Liste der das Ergebnis bereitstellen soll wird vorbereitet
		List<Notiz> result = new Vector<Notiz>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT nutzer.*, notiz.*, FROM notiz LEFT JOIN nutzer ON  nutzer.nutzerId = notiz.id "
							+ " ORDER BY nutzerId");

			// Jetzt werden die Eintrï¿½ge durchsucht und fï¿½r jedes gefundene
			// ein Notiz Objekt erstellt
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("id"));
				Nutzer nutzer = new Nutzer();
		    	nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
		    	nutzer.setEmail(rs.getString("nutzer.email"));
		        no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("titel"));
				no.setSubtitel(rs.getString("subtitel"));
				no.setErstelldatum(rs.getDate("erstelldatum"));
				no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				no.setInhalt(rs.getString("inhalt"));

				// Der Ergebnisliste wird ein neues Objekt hinzugefï¿½gt
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Die Ergebnisliste wird zurï¿½ckgegeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines durch FremdschlÃ¼ssel (Id) gegebenen
	 * Notizbuches.
	 * 
	 * @param notizbuchId
	 *            SchlÃ¼ssel des zugehÃ¶rigen Notizbuches.
	 * @return Eine Liste mit Notiz-Objekten, die sÃ¤mtliche Notizen des
	 *         betreffenden Notizbuches reprÃ¤sentieren. Bei evtl. Exceptions
	 *         wird ein partiell gefÃ¼llter oder ggf. auch leerer Vetor
	 *         zurÃ¼ckgeliefert.
	 */
	public List<Notiz> nachAllenNotizenDesNotizbuchesSuchen(int id) {
		Connection con = DBConnection.connection();
		List<Notiz> result = new Vector<Notiz>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, eigentuemer, erstelldatum, inhalt, modifikationsdatum,"
							+ " subtitel, titel FROM notiz "
							+ "WHERE id="
							+ id
							+ " ORDER BY id");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt
			// erstellt.
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("id"));
				Nutzer nutzer = new Nutzer();
		    	nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
		    	nutzer.setEmail(rs.getString("nutzer.email"));
		        no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("titel"));
				no.setSubtitel(rs.getString("subtitel"));
				no.setErstelldatum(rs.getDate("erstelldatum"));
				no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				no.setInhalt(rs.getString("inhalt"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines durch Fremdschlï¿½ssel (NutzerId) gegebenen
	 * Nutzern.
	 */
	public List<Notiz> nachEigentuemerDerNotizSuchen(int id) {
		Connection con = DBConnection.connection();
		List<Notiz> result = new Vector<Notiz>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT notiz*, nutzer.* FROM nutzer LEFT JOIN notiz ON nutzer.id = nutzer.nutzerId"
							+ " ORDER BY nid");

			// Jetzt werden die Eintrï¿½ge durchsucht und fï¿½r jedes gefundene
			// ein Notiz Objekt erstellt
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("id"));
				Nutzer nutzer = new Nutzer();
		    	nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
		    	nutzer.setEmail(rs.getString("nutzer.email"));
		        no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("titel"));
				no.setSubtitel(rs.getString("subtitel"));
				no.setErstelldatum(rs.getDate("erstelldatum"));
				no.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				no.setInhalt(rs.getString("inhalt"));

				// Der Ergebnisliste wird ein neues Objekt hinzugefï¿½gt
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Die Ergebnisliste wird zurï¿½ckgegeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines Nutzers //TODO
	 */
	public List<Notiz> nachEigentuemerSuchen(Nutzer eigentuemer) { // TODO

		// Die Id des Notiz Objekts wird ausgelesen, und die Methode ist fï¿½r
		// die weitere Bearbeitung zustï¿½ndig.

		return nachEigentuemerDerNotizSuchen(eigentuemer.getNutzerId());
	}

	
	/**
	   * Auslesen des zugehörigen <code>Notizbuch</code>-Objekts zu einem gegebenen
	   * Notiz.
	   * 
	   * @param no die Notiz, dessen Notizbuch wir auslesen möchten
	   * @return ein Objekt, das das Notizbuch der Notiz darstellt
	   */
	  public Notizbuch nachZugehoerigemNotizbuchSuchen(Notiz no) {
	    /*
	     * Wir bedienen uns hier einfach des NotizbuchMapper. Diesem geben wir einfach
	     * den in dem Notiz-Objekt enthaltenen Fremdschlüssel für das Notizbuch.
	     * Der NotizbuchMapper löst uns dann diese ID in ein Objekt auf.
	     */
	    return NotizbuchMapper.notizbuchMapper().nachNotizbuchIdSuchen(no.getNotizbuchId());
	  }


	/**
	 * Anlegen einer Notiz.
	 * 
	 */
	public Notiz anlegenNotiz(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Der hï¿½chste Primï¿½rschlï¿½sselwert wird ï¿½berprï¿½ft
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM notiz ");

			// Sollte etwas zurï¿½ckgegeben werden, so kann dies nur einzeilig
			// sein
			if (rs.next()) {
				// a kriegt nun den maximalen Primï¿½rschlï¿½ssel, welcher mit
				// dem Wert 1 inkrementiert wird
				no.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Hier erfolgt die entscheidende Einfï¿½geoperation
				String sql = "INSERT INTO notiz (id, eigentuemer, titel, subtitel, inhalt, erstelldatum, modifikationsdatum, notizbuch ) "
						+ "VALUES ("
						+ no.getId()
						+ ", "
						+ no.getEigentuemer().getNutzerId()
						+ ", \""
						+ no.getTitel()
						+ "\", \""
						+ no.getSubtitel()
						+ "\", \""
						+ no.getInhalt()
						+ "\""
						+ ", NOW(), NOW()"
						+ ", "
						+ no.getNotizbuchId()
						+ " )";
				System.out.println(sql);
				stmt.executeUpdate(sql);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Sollte es korrigierte Notizen geben, so werden diese zurï¿½ckgegeben
		 * 
		 * So besteht die Mï¿½glichkeit anzudeuten ob sich ein Objekt
		 * verï¿½ndert hat, wï¿½hrend die Methode ausgefï¿½hrt wurde
		 */
		return no;
	}

//	public static void main(String[] args) {
//		Nutzer nutzer = NutzerMapper.nutzerMapper().nachNutzerIdSuchen(1000);
//		
//		Notiz notiz = new Notiz();
//		notiz.setEigentuemer(nutzer);
//		notiz.setTitel("Hallo");
//		notiz.setSubtitel("WI7");
//		notiz.setInhalt("hier_ist_inhalt");
//		notiz.setErstelldatum(new Date());
//		notiz.setModifikationsdatum(new Date());
//		
//		NotizMapper notizMapper = NotizMapper.notizMapper();
//		notizMapper.anlegenNotiz(notiz);
//	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 */
	public Notiz update(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE notiz " + "SET id=\"" + no.getId()
					+ "\", titel=\"" + no.getTitel() + "\", inhalt=\""
					+ no.getInhalt() + "\", modifikationsdatum=NOW() "
					+ "WHERE id=" + no.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um ï¿½hnliche Strukturen wie zu anlegenNotiz(Notiz a) zu wahren,
		// geben wir nun a zurï¿½ck
		return no;
	}

	/**
	 * Lï¿½schen der Daten eines Notiz-Objekts aus der Datenbank.
	 */
	public void loeschenNotiz(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notiz " + "WHERE id=" + no.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Lï¿½schen sï¿½mtlicher Notizen eines Nutzers (sollte dann aufgerufen
	 * werden, bevor ein Nutzer-Objekt gelï¿½scht wird)
	 */
	public void loeschenNotizVon(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notiz " + "WHERE nutzerId="
					+ n.getNutzerId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen des zugehï¿½rigen Nutzer-Objekts zu einem gegebenen Notiz.
	 */
	public Nutzer getNutzerId(Notiz no) {
		return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(no.getId());
	}

}
