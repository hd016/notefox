package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.List;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfuellen den Zweck unsere Objekte auf eine relationale
 * Datenbankabzubilden. Durch die bereitgestellten Methoden kann man Objekte
 * anlegen, editieren, loeschen, teilen und speichern.Objekte koennen auf diese
 * Weise in Datenbankstrukturen umgewandelt werden. Datenbankstrukturen koennen
 * umgekehrt auch in Objekte umgewandelt werden.
 */

public class NutzerMapper {
	
	
// Zwischen Connector wegen Connection Socket Fehler
	// Auskommentieren um den blockierten Socket wieder freizugeben
//	public static void main(String[] args) {
//		NutzerMapper.nutzerMapper().nachNutzerEmailSuchen("ppppp@example.com");
//	}

	/**
	 * Eimalige Instantiierung der Klasse NutzerMapper (Singleton) Einmal fuer
	 * saemtliche Instanzen dieser Klasse vorhanden, speichert die einzige
	 * Instanz dieser Klasse
	 */

	private static NutzerMapper nutzerMapper = null;

	/**
	 * Konstruktor verhindert durch protected weitere Instanzen aus dieser
	 * Klasse zu erzeugen
	 */
	protected NutzerMapper() {
	}

	/**
	 * NutzerMapper-Objekt Die statische Methode verhindert, dass von einer
	 * Klasse mehr als ein Objekt gebildet werden kann. (Bewahrung der
	 * Singleton-Eigenschaft)
	 */

	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}

		return nutzerMapper;
	}

	/**
	 * Nutzer nach NutzerId suchen. * als return: Nutzer-Objekt oder bei nicht
	 * vorhandener NutzerId/DB-Tupel null.
	 */
	public Nutzer nachNutzerIdSuchen(int nutzerId) {
		// Es wird eine DB-Verbindung angeschafft
		Connection con = DBConnection.connection();

		try {
			// Es wird ein leeres SQL Statement von dem Connector (JDBC)
			// angelegt
			Statement stmt = con.createStatement();

			// Das Statement wird ausgefï¿½llt und an die Datebank verschickt
			ResultSet rs = stmt
					.executeQuery("SELECT nutzerId, name, email FROM nutzer "
							+ "WHERE nutzerId=" + nutzerId);

			/*
			 * An dieser Stelle kann man pruefen ob bereits ein Ergebnis
			 * vorliegt. Man erhï¿½lt maximal 1 Tupel, da es sich bei id um einen
			 * Primaerschluessel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt ueberfuehrt
				// werden.
				Nutzer n = new Nutzer();
				n.setNutzerId(rs.getInt("nutzerId"));
				n.setEmail(rs.getString("email"));
				n.setName(rs.getString("name"));

				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Auslesen aller Nutzer.
	 * 
	 */
	public List<Nutzer> nachAllenNutzernSuchen() {
		Connection con = DBConnection.connection();

		// Die Liste die das Ergebnis bereitstellen soll wird vorbereitet
		List<Nutzer> result = new Vector<Nutzer>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT nutzerId, name, email "
					+ "FROM nutzer " + "ORDER BY name");

			// Jetzt werden die Eintraege durchsucht und fuer jedes gefundene ein
			// Nutzer Objekt erstellt

			while (rs.next()) {
				Nutzer n = new Nutzer();
				n.setNutzerId(rs.getInt("nutzerId"));
				n.setEmail(rs.getString("email"));
				n.setName(rs.getString("name"));


				// Es wird ein neues Objekt hinzugefuegt
				result.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Die Ergebnisliste wird zurueckgegeben
		return result;
	}

	/**
	 * Auslesen aller Nutzer-Objekte mit gegebenem Email
	 * 
	 */
	public Nutzer nachNutzerEmailSuchen(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT nutzerId, name, email "
					+ "FROM nutzer " + "WHERE email LIKE '" + name
					+ "' ORDER BY email");

			// Jetzt werden die Eintraege durchsucht und fuer jedes gefundene ein
			// Nutzer Objekt erstellt

			while (rs.next()) {
				Nutzer n = new Nutzer();
				n.setNutzerId(rs.getInt("nutzerId"));
				n.setEmail(rs.getString("email"));
				n.setName(rs.getString("name"));


				// Es wird ein neues Objekt hinzugefuegt
				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// null wird zurueckgegegeben
		return null;
	}

	/**
	 * Anlegen eines Nutzers.
	 * 
	 */
	public Nutzer anlegenNutzer(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Der hoechste Primïaerschluesselwert wird ueberprueft

			ResultSet rs = stmt.executeQuery("SELECT MAX(nutzerId) AS maxid "
					+ "FROM nutzer ");

			// Sollte etwas zurueckgegeben werden, so kann dies nur einzeilig
			// sein
			if (rs.next()) {
				/*
				 * c kriegt nun den maximalen Primaerschluessel, welcher mit dem
				 * Wert 1 inkrementiert wird
				 */
				n.setNutzerId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Hier erfolgt die entscheidende Einfuegeoperation
				String sql = "INSERT INTO nutzer (nutzerId, email, name) "
						+ "VALUES (" + n.getNutzerId() + ", \"" + n.getEmail()
						+ "\", \"" + n.getName() + "\")";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Sollte es korrigierte Nutzer geben, so werden diese zurueckgegeben
		 * 
		 * So besteht die Moeglichkeit anzudeuten ob sich ein Objekt veraendert
		 * hat, waehrend die Methode ausgefuehrt wurde
		 */
		return n;
	}

//	public static void main(String[] args) {
//		// Nutzer nutzer = NutzerMapper.nutzerMapper().nachNutzerIdSuchen(1000);
//
//		Nutzer nutzer = new Nutzer();
//		nutzer.getNutzerId();
//		nutzer.setName("muhammed");
//		nutzer.setEmail("muhammed1@gmail.com");
//
//		NutzerMapper nutzerMapper = NutzerMapper.nutzerMapper();
//		nutzerMapper.anlegenNutzer(nutzer);
//
//	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 */
	public Nutzer update(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE nutzer " + "SET email=\"" + n.getEmail() + n.getName()
					+ "\", " + "\" " + "WHERE NutzerId=" + n.getNutzerId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um aehnliche Strukturen wie zu anlegenNutzer(Nutzer c) zu wahren,
		// geben wir nun c zurueck
		return n;
	}

	/**
	 * Loeschen der Daten eines Nutzer-Objekts aus der Datenbank.
	 */
	public void loeschenNutzer(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM nutzer " + "WHERE NutzerId="
					+ n.getNutzerId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auslesen der zugehoerigen Notizen-Objekte zu einem gegebenen Nutzer.
	 */

	public List<Notiz> getNotizOf(Nutzer n) {
		return NotizMapper.notizMapper().nachEigentuemerSuchen(n);
	}

	/**
	 * Auslesen der zugehoerigen Notizbuecher-Objekte zu einem gegebenen Nutzer.
	 */
	public List<Notizbuch> getNotizbuchOf(Nutzer c) {
		return NotizbuchMapper.notizbuchMapper().nachEigentuemerSuchen(c);
	}
}
