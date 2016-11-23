package de.hdm.notefox.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 *Semiautomatisch vom Google Plugin erstellt. 
 *Gegenstück des asynchronen Interface (NotizobjektAdministration).
 */
public interface NotizobjektAdministrationAsync {

void initialisieren(AsyncCallback<Void> callback);

void anlegenNutzer(int id, String name, String passwort, AsyncCallback<Void> callback);

void anlegenNotizFür(Nutzer c, AsyncCallback<Notiz> callback);

void anlegenNotizbuecherFür(Nutzer c, AsyncCallback<Notizbuch> callback);

void getNotizobjekt(AsyncCallback<Notizobjekt> callback);

void setNotizobjekt(Notizobjekt b, AsyncCallback<Void> callback);

void nachAllenNotizenDesNutzersSuchen(Nutzer c, AsyncCallback<Vector<Notiz>> callback);

void nachAllenNotizbuechernDesNutzersSuchen(Nutzer c, AsyncCallback<Vector<Notizbuch>> callback);

void nachNotizId(int id, AsyncCallback<Notiz> callback);

void nachNotizbuchId(int id, AsyncCallback<Notizbuch> callback);

void nachNutzerNamenSuchen(String name, AsyncCallback<Void> callback);

void nachNutzerIdSuchen(int nutzerid, AsyncCallback<Nutzer> callback);

void nachAllenNutzernSuchen(AsyncCallback<Void> callback);

void nachAllenNotizenSuchen(AsyncCallback<Vector<Notiz>> callback);

void nachAllenNotizbuechernSuchen(AsyncCallback<Vector<Notizbuch>> callback);

void speichern(Notiz a, AsyncCallback<Void> callback);

void speichern(Notizbuch a, AsyncCallback<Void> callback);

void speichern(Nutzer c, AsyncCallback<Void> callback);

void loeschenNotiz(Notiz a, AsyncCallback<Void> callback);

void loeschenNotizbuch(Notizbuch a, AsyncCallback<Void> callback);

void loeschenNutzer(Nutzer c, AsyncCallback<Void> callback);

}
