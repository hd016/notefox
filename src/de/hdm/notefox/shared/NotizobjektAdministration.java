package de.hdm.notefox.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Zur Verwaltung des Notizobjekts eine synchrone Schnittstelle für eine RPC-fähige Klasse.
 */
@RemoteServiceRelativePath("bankadministration")
public interface NotizobjektAdministration extends RemoteService {

	/**
	   * Objekt wird initialisiert.
	   */
  public void initialisieren() throws IllegalArgumentException;

  void anlegenNutzer(int id, String name, String passwort);

  /**
   * Eine neue Notiz für einen gegebenen Nutzer anlegen.
   */
  public Notiz anlegenNotizFür(Nutzer n) throws IllegalArgumentException;
  
  /**
   * Eine neues Notizbuch für einen gegebenen Nutzer anlegen.
   */
  public Notizbuch anlegenNotizbuecherFür(Nutzer n) throws IllegalArgumentException;

  /**
   * Auslesen und Setzen der zugeordneten Notizobjekte.
   */
  public Notizobjekt getNotizobjekt() throws IllegalArgumentException;
  
  public void setNotizobjekt(Notizobjekt nobj) throws IllegalArgumentException;

  /**
   * Alle Notizen eines Nutzers auslesen.
   */
  public Vector<Notiz> nachAllenNotizenDesNutzersSuchen(Nutzer n)
      throws IllegalArgumentException;
  
  /**
   * Alle Notizbücher eines Nutzers auslesen.
   */
  public Vector<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer n)
      throws IllegalArgumentException;

  /**
   * Suchen eines Notiz-Objekts mit gegebener NotizId.
   */
  public Notiz nachNotizId(int id) throws IllegalArgumentException;
  
  /**
   * Suchen eines Notizbuch-Objekts mit gegebener NotizbuchId.
   */
  public Notizbuch nachNotizbuchId(int id) throws IllegalArgumentException;

  void nachNutzerNamenSuchen(String name);

  /**
   * Suchen eines Nutzers nach NutzerId.
   */
  public Nutzer nachNutzerIdSuchen(int nutzerid) throws IllegalArgumentException;

  void nachAllenNutzernSuchen();

  /**
   * Alle Notizen auslesen.
   */
  public Vector<Notiz> nachAllenNotizenSuchen() throws IllegalArgumentException;
  
  /**
   * Alle Notizbücher auslesen.
   */
  public Vector<Notizbuch> nachAllenNotizbuechernSuchen() throws IllegalArgumentException;

  /**
   * Speichern eines Notiz-Objekts in der Datenbank.
   */
  public void speichern(Notiz n) throws IllegalArgumentException;
  
  /**
   * Speichern eines Notizbuch-Objekts in der Datenbank.
   */
  public void speichern(Notizbuch nb) throws IllegalArgumentException;

  /**
   * Speichern eines Nutzer-Objekts in der Datenbank.
   */
  public void speichern(Nutzer c) throws IllegalArgumentException;

  /**
   * Löschen der Daten eines übergegebenen Notiz-Objekts.
   */
  public void loeschenNotiz(Notiz no) throws IllegalArgumentException;
  
  /**
   * Löschen der Daten eines übergegebenen Notizbuch-Objekts.
   */
  public void loeschenNotizbuch(Notizbuch nb) throws IllegalArgumentException;

  /**
   * Löschen der Daten eines übergegebenen Nutzer-Objekts.
   */
  public void loeschenNutzer(Nutzer n) throws IllegalArgumentException;

}
