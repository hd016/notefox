package de.hdm.notefox.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 * Zur Verwaltung des Notizobjekts eine synchrone Schnittstelle fuer eine
 * RPC-faehige Klasse.
 */
@RemoteServiceRelativePath("notizobjektadministration")
public interface NotizobjektAdministration extends RemoteService {

	/**
	 * Einen Nutzer anlegen.
	 */
	public Nutzer anlegenNutzer(int nutzerId, String name) throws IllegalArgumentException;

	/**
	 * Eine neue Notiz anlegen.
	 */
	public Notiz anlegenNotiz(Notizbuch notizbuch) throws IllegalArgumentException;

	/**
	 * Eine neues Notizbuch fuer einen gegebenen Nutzer anlegen.
	 */
	public Notizbuch anlegenNotizbuecherFuer(Nutzer n) throws IllegalArgumentException;

	/**
	 * <p>
	 * Auslesen saemtlicher mit diesem Notizbuch in Verbindung stehenden
	 * Notizen.
	 * </p>
	 * 
	 * @param nb
	 *            das Notizbuch, dessen Notiz wir bekommen wollen.
	 * @return eine Liste aller Notizen
	 * @throws IllegalArgumentException
	 */
	List<Notiz> nachAllenNotizenDesNotizbuchesSuchen(Notizbuch nb) throws IllegalArgumentException;

	/**
	 * Alle Notizen eines Nutzers auslesen.
	 */
	public List<Notiz> nachAllenNotizenDesNutzersSuchen(Nutzer n) throws IllegalArgumentException;

	/**
	 * Alle Notizbuecher eines Nutzers auslesen.
	 */
	public List<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer n) throws IllegalArgumentException;

	/**
	 * Suchen eines Notiz-Objekts mit gegebener NotizId.
	 */
	public Notiz nachNotizIdSuchen(int id) throws IllegalArgumentException;

	/**
	 * Suchen eines Notizbuch-Objekts mit gegebener NotizbuchId.
	 */
	public Notizbuch nachNotizbuchIdSuchen(int id) throws IllegalArgumentException;

	/**
	 * Suchen eines Nutzer-Objekts mit gegebenem Email.
	 */
	public Nutzer nachNutzerEmailSuchen(String email) throws IllegalArgumentException;

	/**
	 * Suchen eines Nutzers nach NutzerId.
	 */
	public Nutzer nachNutzerIdSuchen(int nutzerid) throws IllegalArgumentException;

	/**
	 * Alle Nutzer auslesen.
	 */
	public List<Nutzer> nachAllenNutzernSuchen() throws IllegalArgumentException;

	/**
	 * Alle Notizen auslesen.
	 */
	public List<Notiz> nachAllenNotizenSuchen() throws IllegalArgumentException;

	/**
	 * Alle Notizbuecher auslesen.
	 */
	public List<Notizbuch> nachAllenNotizbuechernSuchen() throws IllegalArgumentException;

	/**
	 * Speichern eines Notiz-Objekts in der Datenbank.
	 */
	public Notiz speichern(Notiz no) throws NutzerAusnahme;

	/**
	 * Speichern eines Notizbuch-Objekts in der Datenbank.
	 */
	public Notizbuch speichern(Notizbuch nb) throws NutzerAusnahme;

	/**
	 * Speichern eines Nutzer-Objekts in der Datenbank.
	 */
	public Nutzer speichern(Nutzer n) throws IllegalArgumentException;

	/**
	 * Loeschen der Daten eines Uebergegebenen Notiz-Objekts.
	 */
	public void loeschenNotiz(Notiz no) throws NutzerAusnahme;

	/**
	 * Loeschen der Daten eines uebergegebenen Notizbuch-Objekts.
	 */
	public void loeschenNotizbuch(Notizbuch nb) throws NutzerAusnahme;

	/**
	 * Löschen der Daten eines übergegebenen Nutzer-Objekts.
	 */

	public void loeschenNutzer(Nutzer n) throws IllegalArgumentException;

	Notiz anlegenNotiz(String url) throws IllegalArgumentException;

	List<Berechtigung> nachAllenBerechtigungenDerNotizSuchen(Notiz no) throws IllegalArgumentException;

	List<Berechtigung> nachAllenBerechtigungenDesNotizbuchesSuchen(Notizbuch nb) throws IllegalArgumentException;

	Berechtigung anlegenBerechtigung(Berechtigung berechtigung);

	Berechtigung loeschenBerechtigung(Berechtigung be);

	List<Notiz> nachNotizenDesFilterSuchen(Filterobjekt filter);

}
