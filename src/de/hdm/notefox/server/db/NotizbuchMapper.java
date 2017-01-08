package de.hdm.notefox.server.db;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.*;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erf�llen den Zweck unsere Objekte auf eine relationale
 * Datenbank abzubilden. Durch die bereitgestellten Methoden kann man Objekte
 * anlegen, editieren, l�schen, teilen und speichern. Objekte k�nnen auf diese
 * Weise in Datenbankstrukturen umgewandelt werden. Datenbankstrukturen k�nnen
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
	private static Date erstelldatum;
	private static Date modifikationsdatum;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected NotizbuchMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
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
			ResultSet rs = stmt.executeQuery("SELECT notizbuch.*, nutzer.* FROM notizbuch LEFT JOIN nutzer ON notizbuch.eigentuemer = nutzer.nutzerId"
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
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));

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

			ResultSet rs = stmt
					.executeQuery("SELECT nutzer.*, notizbuch.* FROM notizbuch LEFT JOIN nutzer ON nutzer.nutzerId = notizbuch.eigentuemer"
							+ " ORDER BY id");

			// F�r jeden Eintrag im Suchergebnis wird nun ein Datum-Objekt
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
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));

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
	 * Auslesen aller Notizb�cher eines durch Fremdschl�ssel (NutzerId)
	 * gegebenen Nutzern.
	 */
	public List<Notizbuch> nachEigentuemerDerNotizbuecherSuchen(int id) { // TODO
		Connection con = DBConnection.connection();
		List<Notizbuch> result = new Vector<Notizbuch>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT  notizbuch.*, nutzer.*  FROM notizbuch LEFT JOIN nutzer ON notizbuch.eigentuemer = nutzer.nutzerId" // TODO
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
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));

				// Hinzuf�gen des neuen Objekts zur Ergebnisliste
				result.add(nb);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurueckgeben
		return result;
	}

	/**
	 * Auslesen aller Notizb�cher eines Nutzers
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
			 * Der h�chste Prim�rschl�sselwert wird �berpr�ft
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM notizbuch ");

			// Sollte etwas zur�ckgegeben werden, so kann dies nur einzeilig
			// sein
			if (rs.next()) {
				/*
				 * nb erh�lt den bisher maximalen, nun um 1 inkrementierten
				 * Prim�rschl�ssel.
				 */
				nb.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Hier erfolgt die entscheidende Einf�geoperation

				// Hier erfolgt die entscheidende Einf�geoperation
				String sql = "INSERT INTO notizbuch (id, eigentuemer, titel, subtitel, erstelldatum, modifikationsdatum ) "
						+ "VALUES ("
						+ nb.getId()
						+ ", "
						+ nb.getEigentuemer().getNutzerId()
						+ ", \""
						+ nb.getTitel()
						+ "\", \""
						+ nb.getSubtitel()
						+ "\" "
						+ ", NOW(), NOW())";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Sollte es korrigierte Daten geben, so werden diese zur�ckgegeben
		 * 
		 * So besteht die M�glichkeit anzudeuten, ob sich ein Objekt ver�ndert
		 * hat, w�hrend die Methode ausgef�hrt wurde
		 */
		return nb;
	}

	// public static void main(String[] args) {
	// Nutzer nutzer = NutzerMapper.nutzerMapper().nachNutzerIdSuchen(1000);
	//
	// Notizbuch notizbuch = new Notizbuch();
	// notizbuch.setEigentuemer(nutzer);
	// notizbuch.setTitel("Hallo");
	// notizbuch.setSubtitel("WI7");
	// notizbuch.setErstelldatum(new Date());
	// notizbuch.setModifikationsdatum(new Date());
	//
	// NotizbuchMapper notizbuchMapper = NotizbuchMapper.notizbuchMapper();
	// notizbuchMapper.anlegenNotizbuch(notizbuch);
	// }

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 */
	public Notizbuch update(Notizbuch nb) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE notizbuch " + "SET id=\"" + nb.getId()
					+ "\", titel=\"" + nb.getTitel() + "\", subtitel=\""
					+ nb.getSubtitel() + "\", modifikationsdatum=NOW() "
					+ "WHERE id=" + nb.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu anlegenDatum(Datum a) zu wahren, geben wir nb zur�ck
		return nb;
	}

	/**
	 * Loeschen der Daten eines <code>Notizbuch</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param a
	 *            das aus der DB zu loeschende "Objekt"
	 */
	public void loeschenNotizbuch(Notizbuch nb) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE id="
					+ nb.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * L�schen s�mtlicher Notizb�cher eines Nutzers (sollte dann aufgerufen
	 * werden, bevor ein Nutzer-Objekt gel�scht wird)
	 */
	public void loeschenNotizbuchVon(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notizbuch " + "WHERE nutzerId="
					+ n.getNutzerId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Auslesen des zugeh�rigen Nutzer-Objekts zu einem gegebenen Notizbuch.
	 */
	public Nutzer getNutzerId(Notizbuch nb) {
		return NutzerMapper.nutzerMapper().nachNutzerIdSuchen(nb.getId());
	}

}
