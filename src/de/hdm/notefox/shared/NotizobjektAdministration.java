package de.hdm.notefox.shared;

import java.util.ArrayList;
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
  
  /**
   * Einen Nutzer anlegen.
   */
  public Nutzer anlegenNutzer(int nutzerId, String name) throws IllegalArgumentException;

  /**
   * Eine neue Notiz f�r einen gegebenen Nutzer anlegen.
   */
  public Notiz anlegenNotizFuer(Nutzer n) throws IllegalArgumentException;
  
  /**
   * Eine neues Notizbuch f�r einen gegebenen Nutzer anlegen.
   */
  public Notizbuch anlegenNotizbuecherFuer(Nutzer n) throws IllegalArgumentException;
  
  /**
   * Eine neue Notizquelle f�r eine gegebene Notiz anlegen.
   */
  public Notizquelle anlegenNotizquelleFuer(Notiz no) throws IllegalArgumentException;
  
  /**
   * Eine neue Faelligkeit f�r eine gegebene Notiz anlegen.
   */
  public Datum anlegenFaelligkeitFuer(Notiz no) throws IllegalArgumentException;
  
  /**
   * <p>
   * Auslesen sämtlicher mit dieser Notiz in Verbindung stehenden
   * Notizquellen. 
   * </p>
   * 
   * @param no die Notiz, dessen Notizquellen wir bekommen wollen.
   * @return eine Liste aller Notizquellen
   * @throws IllegalArgumentException
   */
  public ArrayList<Notizquelle> nachAllenNotizquellenDesNutzersSuchen(Notiz no)
      throws IllegalArgumentException;
  
  /**
   * <p>
   * Auslesen sämtlicher mit dieser Notiz in Verbindung stehenden
   * Faelligkeiten. 
   * </p>
   * 
   * @param k die Notiz, dessen Faelligkeiten wir bekommen wollen.
   * @return eine Liste aller Faelligkeiten
   * @throws IllegalArgumentException
   */
  public ArrayList<Datum> nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(Notiz no)
      throws IllegalArgumentException;
  
  /**
   * <p>
   * Auslesen sämtlicher mit diesem Notizbuch in Verbindung stehenden
   * Notizen. 
   * </p>
   * 
   * @param nb das Notizbuch, dessen Notiz wir bekommen wollen.
   * @return eine Liste aller Notizen
   * @throws IllegalArgumentException
   */
  ArrayList<Notiz> nachAllenNotizenDesNutzersSuchen(Notizbuch nb)
			throws IllegalArgumentException;
  

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
   * Alle Notizb�cher eines Nutzers auslesen.
   */
  public Vector<Notizbuch> nachAllenNotizbuechernDesNutzersSuchen(Nutzer n)
      throws IllegalArgumentException;

  /**
   * Suchen eines Notiz-Objekts mit gegebener NotizId.
   */
  public Notiz nachNotizIdSuchen(int id) throws IllegalArgumentException;
  
  /**
   * Suchen eines Notizbuch-Objekts mit gegebener NotizbuchId.
   */
  public Notizbuch nachNotizbuchIdSuchen(int id) throws IllegalArgumentException;

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
  public void speichern(Notiz no) throws IllegalArgumentException;
  
  /**
   * Speichern eines Notizbuch-Objekts in der Datenbank.
   */
  public void speichern(Notizbuch nb) throws IllegalArgumentException;

  /**
   * Speichern eines Nutzer-Objekts in der Datenbank.
   */
  public void speichern(Nutzer n) throws IllegalArgumentException;

  /**
   * L�schen der Daten eines �bergegebenen Notiz-Objekts.
   */
  public void loeschenNotiz(Notiz no) throws IllegalArgumentException;
  
  /**
   * L�schen der Daten eines �bergegebenen Notizbuch-Objekts.
   */
  public void loeschenNotizbuch(Notizbuch nb) throws IllegalArgumentException;

  /**
   * L�schen der Daten eines �bergegebenen Nutzer-Objekts.
   */
  public void loeschenNutzer(Nutzer n) throws IllegalArgumentException;
  
  /**
   * Löschen der übergebenen Notizquelle.
   * 
   * @param t die zu löschende Notizquelle
   * @throws IllegalArgumentException
   */
  public void loeschenNotizquelleVon(Notizquelle nq) throws IllegalArgumentException;
  
  /**
   * Löschen der übergebenen Faelligkeiten.
   * 
   * @param t die zu löschende Faelligkeit
   * @throws IllegalArgumentException
   */
  public void loeschenDatumVon(Datum d) throws IllegalArgumentException;

}
