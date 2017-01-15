package de.hdm.notefox.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.hdm.notefox.shared.Filterobjekt;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.bo.*;

/*
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Unsere Mapper-Klassen erfuellen den Zweck unsere Objekte auf eine relationale
 * Datenbankabzubilden. Durch die bereitgestellten Methoden kann man Objekte
 * anlegen, editieren, loeschen, teilen und speichern.Objekte koennen auf diese
 * Weise in Datenbankstrukturen umgewandelt werden. Datenbankstrukturen koennen
 * umgekehrt auch in Objekte umgewandelt werden.
 */

public class NotizMapper {

	/**
	 * Eimalige Instantierung der Klasse NotizMapper (Singleton) Einmal fuer
	 * saemtliche Instanzen dieser Klasse vorhanden, speichert die eizige
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
					.executeQuery("SELECT notiz.*, nutzer.*, notizbuch.* FROM notiz LEFT JOIN notizbuch ON notiz.notizbuch = notizbuch.id LEFT JOIN nutzer ON notiz.eigentuemer = nutzer.nutzerId "
							+ " WHERE notiz.id=" + id);

			/*
			 * An dieser Stelle kann man pruefen ob bereits ein Ergebnis
			 * vorliegt. Man erhaelt maximal 1 Tupel, da es sich bei id um einen
			 * Primaerschluessel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt ueberfuehrt
				// werden.
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setFaelligkeitsdatum(rs.getDate("notiz.faelligkeitsdatum"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
			
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));
				no.setNotizbuch(nb);

				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				no.setEigentuemer(nutzer);
				return no;

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

			// Das Statement wird ausgefuellt und an die Datebank verschickt
			ResultSet rs = stmt.executeQuery(
					"SELECT notiz.*, notizquelle.* FROM notiz LEFT JOIN notiz ON notiz.id = notizquelle.notizquelleId "
							+ " ORDER BY id");

			/*
			 * An dieser Stelle kann man pruefen ob bereits ein Ergebnis
			 * vorliegt. Man erhaelt maximal 1 Tupel, da es sich bei id um einen
			 * Primaerschluessel handelt.
			 */
			if (rs.next()) {
				// Das daraus ergebene Tupel muss in ein Objekt ueberfuehrt
				// werden.
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				return no;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Loeschen der Faelligkeitsdatum eines <code>Notiz</code>-Objekts aus der
	 * Datenbank.
	 * 
	 */
	public void loeschenFaelligkeitsdatum(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.execute("DELETE FROM notiz " + "WHERE faelligkeitsdatum=" + no.getFaelligkeitsdatum());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

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
					.executeQuery("SELECT nutzer.*, notiz.*, notizbuch.* FROM notiz LEFT JOIN notizbuch ON notiz.notizbuch = notizbuch.id LEFT JOIN nutzer ON  nutzer.nutzerId = notiz.id "
							+ " ORDER BY nutzerId");

			// Jetzt werden die Eintraege durchsucht und fuer jedes gefundene
			// ein Notiz Objekt erstellt
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
				
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));
				no.setNotizbuch(nb);
				
				no.setFaelligkeitsdatum(rs.getDate("notiz.faelligkeitsdatum"));

				// Der Ergebnisliste wird ein neues Objekt hinzugefuegt
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Die Ergebnisliste wird zurueckgegeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines durch Fremdschluessel (Id) gegebenen
	 * Notizbuches.
	 * 
	 * @param notizbuchId
	 *            Schluessel des zugehoerigen Notizbuches.
	 * @return Eine Liste mit Notiz-Objekten, die sämtliche Notizen des
	 *         betreffenden Notizbuches repräsentieren. Bei evtl. Exceptions
	 *         wird ein partiell gefaellter oder ggf. auch leerer Vetor
	 *         zurueckgeliefert.
	 */
	public List<Notiz> nachAllenNotizenDesNotizbuchesSuchen(int id) {
		Connection con = DBConnection.connection();
		List<Notiz> result = new Vector<Notiz>();

		try {
			Statement stmt = con.createStatement();


			ResultSet rs = stmt
					.executeQuery("SELECT notiz.*, nutzer.*, notizbuch.* FROM notiz LEFT JOIN notizbuch ON notiz.notizbuch = notizbuch.id LEFT JOIN nutzer ON nutzer.nutzerId = notiz.eigentuemer"
							+ " WHERE notiz.notizbuch=" + id);

			// Fuer jeden Eintrag im Suchergebnis wird nun ein Notiz-Objekt
			// erstellt.
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				Nutzer n = new Nutzer();
				n.setNutzerId(rs.getInt("nutzer.nutzerId"));
				n.setEmail(rs.getString("nutzer.email"));
				n.setName(rs.getString("nutzer.name"));
				no.setEigentuemer(n);
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
				
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));
				no.setNotizbuch(nb);
				
				no.setFaelligkeitsdatum(rs.getDate("notiz.faelligkeitsdatum"));

				// Hinzufuegen des neuen Objekts zur Ergebnisliste
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Ergebnisliste zurueckgeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines durch Fremdschluessel (NutzerId) gegebenen
	 * Nutzern.
	 */
	public List<Notiz> nachEigentuemerDerNotizSuchen(int id) {
		Connection con = DBConnection.connection();
		List<Notiz> result = new Vector<Notiz>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT notiz.*, nutzer.*, notizbuch.* FROM notiz LEFT JOIN notizbuch ON notiz.notizbuch = notizbuch.id LEFT JOIN nutzer ON nutzer.nutzerId = notiz.eigentuemer"
							+ " WHERE notiz.eigentuemer= "
							+ id
							+ " ORDER BY id");

			// Jetzt werden die Eintraege durchsucht und fuer jedes gefundene
			// ein Notiz Objekt erstellt
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
				
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));
				no.setNotizbuch(nb);
				
				no.setFaelligkeitsdatum(rs.getDate("notiz.faelligkeitsdatum"));

				// Der Ergebnisliste wird ein neues Objekt hinzugefuegt
				result.add(no);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Die Ergebnisliste wird zurueckgegeben
		return result;
	}

	/**
	 * Auslesen aller Notizen eines Nutzers
	 */
	public List<Notiz> nachEigentuemerSuchen(Nutzer eigentuemer) {

		// Die Id des Notiz Objekts wird ausgelesen, und die Methode ist fuer
		// die weitere Bearbeitung zustaendig.

		return nachEigentuemerDerNotizSuchen(eigentuemer.getNutzerId());
	}

	/**
	 * Auslesen des zugehoerenden <code>Notizbuch</code>-Objekts zu einer
	 * gegebenen Notiz.
	 * 
	 * @param no
	 *            die Notiz, dessen Notizbuch wir auslesen muessen
	 * @return ein Objekt, das das Notizbuch der Notiz darstellt
	 */
	public Notizbuch nachZugehoerigemNotizbuchSuchen(Notiz no) {
		/*
		 * Wir bedienen uns hier einfach an dem NotizbuchMapper. Diesem geben
		 * wir einfach den in dem Notiz-Objekt enthaltenen Fremdschluessel fuer
		 * das Notizbuch. Der NotizbuchMapper loest uns dann diese ID in ein Objekt
		 * auf.
		 */
		return NotizbuchMapper.notizbuchMapper().nachNotizbuchIdSuchen(
				no.getNotizbuch().getId());
	}

	/**
	 * Anlegen einer Notiz.
	 * 
	 */
	public Notiz anlegenNotiz(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Der hoechste Primaerschluesselwert wird ueberprueft
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM notiz ");

			// Sollte etwas zurueckgegeben werden, so kann dies nur einzeilig
			// sein
			if (rs.next()) {
				// a kriegt nun den maximalen Primaerschluessel, welcher mit
				// dem Wert 1 inkrementiert wird
				no.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Hier erfolgt die entscheidende Einfuegeoperation
				String sql = "INSERT INTO notiz (id, eigentuemer, titel, subtitel, inhalt, erstelldatum, modifikationsdatum, notizbuch, faelligkeitsdatum ) "
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
						+ no.getNotizbuch().getId()
						+ ", \""
						+ new SimpleDateFormat("yyyy-MM-dd").format(no
								.getFaelligkeitsdatum()) + "\")";
				System.out.println(sql);
				stmt.executeUpdate(sql);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Sollte es korrigierte Notizen geben, so werden diese zurueckgegeben
		 * 
		 * So besteht die Moeglichkeit anzudeuten ob sich ein Objekt
		 * veraendert hat, waehrend die Methode ausgefuehrt wurde
		 */
		return no;
	}

	// public static void main(String[] args) {
	// Nutzer nutzer = NutzerMapper.nutzerMapper().nachNutzerIdSuchen(1000);
	//
	// Notiz notiz = new Notiz();
	// notiz.setEigentuemer(nutzer);
	// notiz.setTitel("Hallo");
	// notiz.setSubtitel("WI7");
	// notiz.setInhalt("hier_ist_inhalt");
	// notiz.setErstelldatum(new Date());
	// notiz.setModifikationsdatum(new Date());
	//
	// NotizMapper notizMapper = NotizMapper.notizMapper();
	// notizMapper.anlegenNotiz(notiz);
	// }

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 */
	public Notiz update(Notiz no) {
		Connection con = DBConnection.connection();

		try {
			String sql = "UPDATE notiz " + "SET titel=\"" + no.getTitel() + "\", inhalt=?"
					+ ", modifikationsdatum=NOW(), faelligkeitsdatum=?" + ", eigentuemer="
					+ no.getEigentuemer().getNutzerId() + " WHERE id=" + no.getId();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, no.getInhalt());
			statement.setDate(2, new java.sql.Date(no.getFaelligkeitsdatum().getTime()));
			statement.executeUpdate();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um aehnliche Strukturen wie zu anlegenNotiz(Notiz a) zu wahren,
		// geben wir nun a zurueck
		return no;
	}

	/**
	 * Loeschen der Daten eines Notiz-Objekts aus der Datenbank.
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
	 * Loeschen saemtlicher Notizen eines Nutzers (sollte dann aufgerufen
	 * werden, bevor ein Nutzer-Objekt geloescht wird)
	 */
	public void loeschenNotizVon(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notiz " + "WHERE nutzerId=" + n.getNutzerId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public List<Notiz> nachNotizenDesFilterSuchen(Filterobjekt filter) {
		Connection con = DBConnection.connection();

		try {
			Date erstellDatumVon = filter.getErstellDatumVon();
			Date erstellDatumBis = filter.getErstellDatumBis();
			Date modifikationsDatumVon = filter.getModifikationsDatumVon();
			Date modifikationsDatumBis = filter.getModifikationsDatumBis();
			Date faelligkeitsDatumVon = filter.getFaelligkeitsDatumVon();
			Date faelligkeitsDatumBis = filter.getFaelligkeitsDatumBis();

			String sql = ("SELECT notiz.*, nutzer.*, notizbuch.* FROM notiz LEFT JOIN notizbuch ON notiz.notizbuch = notizbuch.id LEFT JOIN nutzer ON notiz.eigentuemer = nutzer.nutzerId LEFT JOIN berechtigung ON notiz.id = berechtigung.notiz "
					+ " WHERE notiz.titel LIKE ? AND nutzer.email LIKE ? AND notiz.erstelldatum BETWEEN ? AND ? AND notiz.modifikationsdatum BETWEEN ? AND ? AND notiz.faelligkeitsdatum BETWEEN ? AND ?"
					+ (filter.isLeseBerechtigungen() ? " AND berechtigung.berechtigungsart = 'LESEN'" : "")
					+ (filter.isLoeschenBerechtigungen() ? " AND berechtigung.berechtigungsart = 'LOESCHEN'" : "")
					+ (filter.isSchreibBerechtigungen() ? " AND berechtigung.berechtigungsart = 'EDITIEREN'" : ""));
			PreparedStatement statement = con.prepareStatement(sql);

			int i = 1;
			statement.setString(i++, "%" + filter.getTitel() + "%");
			statement.setString(i++, "%" + filter.getNutzer() + "%");
			statement.setDate(i++, standardDatumVon(erstellDatumVon));
			statement.setDate(i++, standardDatumBis(erstellDatumBis));
			statement.setDate(i++, standardDatumVon(modifikationsDatumVon));
			statement.setDate(i++, standardDatumBis(modifikationsDatumBis));
			statement.setDate(i++, standardDatumVon(faelligkeitsDatumVon));
			statement.setDate(i++, standardDatumBis(faelligkeitsDatumBis));
			ResultSet rs = statement.executeQuery();

			List<Notiz> result = new ArrayList<>();
			while (rs.next()) {
				Notiz no = new Notiz();
				no.setId(rs.getInt("notiz.id"));
				Nutzer nutzer = new Nutzer();
				nutzer.setNutzerId(rs.getInt("nutzer.nutzerId"));
				nutzer.setEmail(rs.getString("nutzer.email"));
				no.setEigentuemer(nutzer);
				no.setTitel(rs.getString("notiz.titel"));
				no.setSubtitel(rs.getString("notiz.subtitel"));
				no.setInhalt(rs.getString("notiz.inhalt"));
				no.setErstelldatum(rs.getDate("notiz.erstelldatum"));
				no.setModifikationsdatum(rs.getDate("notiz.modifikationsdatum"));
				
				Notizbuch nb = new Notizbuch();
				nb.setId(rs.getInt("notizbuch.id"));
				nb.setTitel(rs.getString("notizbuch.titel"));
				nb.setSubtitel(rs.getString("notizbuch.subtitel"));
				nb.setErstelldatum(rs.getDate("notizbuch.erstelldatum"));
				nb.setModifikationsdatum(rs
						.getDate("notizbuch.modifikationsdatum"));
				no.setNotizbuch(nb);
				
				no.setFaelligkeitsdatum(rs.getDate("notiz.faelligkeitsdatum"));

				// Der Ergebnisliste wird ein neues Objekt hinzugefügt
				result.add(no);
			}
			return result;
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return new ArrayList<>();

	}

	private java.sql.Date standardDatumVon(Date date) {
		/*
		 * Datum in ferner Vergangenheit (sollte immer zutreffen)
		 */
		return standardDatum(date, new Date(0));
	}

	private java.sql.Date standardDatumBis(Date date) {
		/*
		 * Datum in ferner Zukunft (sollte immer zutreffen)
		 */
		return standardDatum(date, new Date(3999, 0, 1));
	}

	private java.sql.Date standardDatum(Date date, Date defaultValue) {
		return new java.sql.Date((date != null ? date : defaultValue).getTime());
	}

}
