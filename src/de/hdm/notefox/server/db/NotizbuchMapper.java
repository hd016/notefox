package de.hdm.notefox.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfuellen den Zweck unsere Objekte auf eine relationale
 * Datenbank abzubilden. Durch die bereitgestellten Methoden kann man Objekte
 * anlegen, editieren, loeschen, teilen und speichern. Objekte koennen auf diese
 * Weise in Datenbankstrukturen umgewandelt werden. Datenbankstrukturen koennen
 * umgekehrt auch in Objekte umgewandelt werden.
 * <p>
 * 
 * @see DatumMapper, NotizMapper, NotizquelleMapper, NutzerMapper
 */

public class NotizbuchMapper {

	/**
	 * 
	 * Die Klasse NotizbuchMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static NotizbuchMapper notizbuchMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected NotizbuchMapper() {
	}

	/**
	 * Diese statische Methode kann aufgerufen werden durch
	 * <code>NotizbuchMapper.notizbuchMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>NotizbuchMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> NotizbuchMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>NotizbuchMapper</code>-Objekt.
	 * @see notizbuchMapper
	 */

	public static NotizbuchMapper notizbuchMapper() {
		if (notizbuchMapper == null) {
			notizbuchMapper = new NotizbuchMapper();
		}

		return notizbuchMapper;
	}

	/**
	 * Notizbuch nach NotizbuchId suchen. Als return: Notizbuch-Objekt oder bei
	 * nicht vorhandener Id/DB-Tupel null.
	 */
	public Notizbuch nachNotizbuchIdSuchen(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT notizbuch.*, nutzer.* FROM notizbuch LEFT JOIN nutzer ON notizbuch.eigentuemer = nutzer.nutzerId"
							+ " WHERE id=" + id);

			/*
			 * Da id Primaerschluessel ist, kann max. nur ein Tupel
			 * zurueckgegeben werden. Pruefe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				nb.setEigentuemer(nutzer);
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs.getDate("notizbuch.modifikationsdatum"));

				return nb;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Auslesen aller Notizbuecher.
	 */

	public List<Notizbuch> nachAllenNotizbuechernSuchen() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Notizbuch> result = new Vector<Notizbuch>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT nutzer.*, notizbuch.* FROM notizbuch LEFT JOIN nutzer ON nutzer.nutzerId = notizbuch.eigentuemer"
							+ " ORDER BY id");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt
			// erstellt.
			while (rs.next()) {
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				nb.setEigentuemer(nutzer);
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs.getDate("notizbuch.modifikationsdatum"));

				// Hinzufuegen des neuen Objekts zur Ergebnisliste
				result.add(nb);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurueckgeben
		return result;
	}

	/**
	 * Auslesen aller Notizbuecher eines durch Fremdschluessel (NutzerId)
	 * gegebenen Nutzern.
	 */
	public List<Notizbuch> nachEigentuemerDerNotizbuecherSuchen(int id) {
		Connection con = DBConnection.connection();
		List<Notizbuch> result = new Vector<Notizbuch>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT  notizbuch.*, nutzer.*  FROM notizbuch LEFT JOIN nutzer ON notizbuch.eigentuemer = nutzer.nutzerId" // TODO
							+ " WHERE nutzer.nutzerId = " + id);

			// Fuer jeden Eintrag im Suchergebnis wird nun ein Notizbuch-Objekt
			// erstellt.
			while (rs.next()) {
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				nb.setEigentuemer(nutzer);
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs.getDate("notizbuch.modifikationsdatum"));

				// Hinzufuegen des neuen Objekts zur Ergebnisliste
				result.add(nb);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurueckgeben
		return result;
	}

	/**
	 * Auslesen aller Notizbuecher eines Nutzers
	 */
	public List<Notizbuch> nachEigentuemerSuchen(Nutzer eigentuemer) {

		/*
		 * Wir lesen einfach die NutzerId (Primärschlüssel) des Nutzer-Objekts
		 * aus und delegieren die weitere Bearbeitung an
		 * nachEigentuemerSuchen(eigentuemer.getNutzerId()).
		 */
		return nachEigentuemerDerNotizbuecherSuchen(eigentuemer.getNutzerId());
	}

	/**
	 * Anlegen einer Notizbuch.
	 * 
	 */
	public Notizbuch anlegenNotizbuch(Notizbuch nb) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Der hoechste Primaerschluesselwert wird ueberprueft
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM notizbuch ");

			// Sollte etwas zurueckgegeben werden, so kann dies nur einzeilig
			// sein
			if (rs.next()) {
				/*
				 * nb erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				nb.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Hier erfolgt die entscheidende Einfuegeoperation

				// Hier erfolgt die entscheidende Einfuegeoperation
				String sql = "INSERT INTO notizbuch (id, eigentuemer, titel, subtitel, erstelldatum, modifikationsdatum ) "
						+ "VALUES (" + nb.getId() + ", " + nb.getEigentuemer().getNutzerId() + ", \"" + nb.getTitel()
						+ "\", \"" + nb.getSubtitel() + "\" " + ", NOW(), NOW())";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Sollte es korrigierte Daten geben, so werden diese zurueckgegeben
		 * 
		 * So besteht die Moeglichkeit anzudeuten, ob sich ein Objekt veraendert
		 * hat, waehrend die Methode ausgefuehrt wurde
		 */
		return nb;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 */
	public Notizbuch update(Notizbuch nb) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE notizbuch SET titel=\"" + nb.getTitel() + "\", subtitel=\"" + nb.getSubtitel()
					+ "\", modifikationsdatum=NOW(), eigentuemer=" + nb.getEigentuemer().getNutzerId() + " WHERE id="
					+ nb.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu anlegenDatum(Datum a) zu wahren, geben wir nb zurueck
		return nb;
	}

	/**
	 * Loeschen der Daten eines <code>Notizbuch</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param das
	 *            aus der DB zu loeschende "Objekt"
	 */
	public void loeschenNotizbuch(Notizbuch nb) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE id=" + nb.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Loeschen saemtlicher Notizbuecher eines Nutzers (sollte dann aufgerufen
	 * werden, bevor ein Nutzer-Objekt geloescht wird)
	 */
	public void loeschenNotizbuchVon(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE nutzerId=" + n.getNutzerId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen des zugehoerigen Nutzer-Objekts zu einem gegebenen Notizbuch.
	 */
	public Nutzer getNutzerId(Notizbuch nb) {
		return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(nb.getId());
	}

}
