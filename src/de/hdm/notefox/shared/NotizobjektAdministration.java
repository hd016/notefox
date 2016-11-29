package de.hdm.notefox.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Zur Verwaltung des Notizobjekts eine synchrone Schnittstelle f�r eine RPC-f�hige Klasse.
 */
@RemoteServiceRelativePath("notizobjektadministration")
public interface NotizobjektAdministration extends RemoteService {

	/**
	   * Objekt wird initialisiert.
	   */
  public void initialisieren() throws IllegalArgumentException;

  void anlegenNutzer(int id, String name, String passwort);

  /**
   * Eine neue Notiz f�r einen gegebenen Nutzer anlegen.
   */
  public Notiz anlegenNotizFür(Nutzer c) throws IllegalArgumentException;
  
  /**
   * Eine neues Notizbuch f�r einen gegebenen Nutzer anlegen.
   */
  public Notizbuch anlegenNotizbuecherFür(Nutzer c) throws IllegalArgumentException;

  /**
   * Auslesen und Setzen der zugeordneten Notizobjekte.
   */
  public Notizobjekt getNotizobjekt() throws IllegalArgumentException;
  
  public void setNotizobjekt(Notizobjekt b) throws IllegalArgumentException;

  /**
   * Alle Notizen eines Nutzers auslesen.
   */
  public Vector<Notiz> nachAllenNotizenDesNutzersSuchen(Nutzer c)
      throws IllegalArgumentException;
  
  /**
   * Alle Notizb�cher eines Nutzers auslesen.
   */
  public Vector<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer c)
      throws IllegalArgumentException;

  /**
   * Suchen eines Notiz-Objekts mit gegebener NotizId.
   */
  public Notiz nachNotizId(int id) throws IllegalArgumentException;
  
  /**
   * Suchen eines Notizbuch-Objekts mit gegebener NotizbuchId.
   */
  public Notizbuch nachNotizbuchId(int id) throws IllegalArgumentException;

  /**
   * Suchen eines Nutzer-Objekts mit gegebenem Namen.
   */
  public Vector<Nutzer> nachNutzerNamenSuchen(String name)
      throws IllegalArgumentException;

  /**
   * Suchen eines Nutzers nach NutzerId.
   */
  public Nutzer nachNutzerIdSuchen(int nutzerid) throws IllegalArgumentException;

  /**
   * Alle Nutzer auslesen.
   */
  public Vector<Nutzer> nachAllenNutzernSuchen() throws IllegalArgumentException;

  /**
   * Alle Notizen auslesen.
   */
  public Vector<Notiz> nachAllenNotizenSuchen() throws IllegalArgumentException;
  
  /**
   * Alle Notizb�cher auslesen.
   */
  public Vector<Notizbuch> nachAllenNotizbuechernSuchen() throws IllegalArgumentException;

  /**
   * Speichern eines Notiz-Objekts in der Datenbank.
   */
  public void speichern(Notiz a) throws IllegalArgumentException;
  
  /**
   * Speichern eines Notizbuch-Objekts in der Datenbank.
   */
  public void speichern(Notizbuch a) throws IllegalArgumentException;

  /**
   * Speichern eines Nutzer-Objekts in der Datenbank.
   */
  public void speichern(Nutzer c) throws IllegalArgumentException;

  /**
   * L�schen der Daten eines �bergegebenen Notiz-Objekts.
   */
  public void loeschenNotiz(Notiz a) throws IllegalArgumentException;
  
  /**
   * L�schen der Daten eines �bergegebenen Notizbuch-Objekts.
   */
  public void loeschenNotizbuch(Notizbuch a) throws IllegalArgumentException;

  /**
   * L�schen der Daten eines �bergegebenen Nutzer-Objekts.
   */
  public void loeschenNutzer(Nutzer c) throws IllegalArgumentException;

}
