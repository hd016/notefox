package de.hdm.notefox.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 *Semiautomatisch vom Google Plugin erstellt. 
 *Gegenstück des asynchronen Interface (NotizobjektAdministration).
 */
public interface NotizobjektAdministrationAsync {

void anlegenNutzer(int nutzerId, String name, AsyncCallback<Nutzer> callback);

void anlegenNotiz( Notizbuch notizbuch, AsyncCallback<Notiz> callback);

void anlegenNotizbuecherFuer(Nutzer n, AsyncCallback<Notizbuch> callback);

void nachAllenNotizenDesNotizbuchesSuchen(Notizbuch nb, AsyncCallback<List<Notiz>> callback);


void getNotiz(AsyncCallback<Notiz> callback);

void setNotiz(Notiz no, AsyncCallback<Void> callback);

void getNotizbuch(AsyncCallback<Notizbuch> callback);

void setNotizbuch(Notizbuch nb, AsyncCallback<Void> callback);

void nachAllenNotizenDesNutzersSuchen(Nutzer n, AsyncCallback<List<Notiz>> callback);

void nachAllenNotizbuechernDesNutzersSuchen(Nutzer n, AsyncCallback<List<Notizbuch>> callback);

void nachNotizIdSuchen(int id, AsyncCallback<Notiz> callback);

void nachNotizbuchIdSuchen(int id, AsyncCallback<Notizbuch> callback);

void nachNutzerEmailSuchen(String email, AsyncCallback<Nutzer> callback);

void nachNutzerIdSuchen(int nutzerid, AsyncCallback<Nutzer> callback);

void nachAllenNutzernSuchen(AsyncCallback<List<Nutzer>> callback);

void nachAllenNotizenSuchen(AsyncCallback<List<Notiz>> callback);

void nachAllenNotizbuechernSuchen(AsyncCallback<List<Notizbuch>> callback);

void speichern(Notiz no, AsyncCallback<Notiz> callback);

void speichern(Notizbuch nb, AsyncCallback<Notizbuch> callback);

void speichern(Nutzer n, AsyncCallback<Nutzer> callback);

void loeschenNotiz(Notiz no, AsyncCallback<Void> callback);

void loeschenNotizbuch(Notizbuch nb, AsyncCallback<Void> callback);

void loeschenNutzer(Nutzer n, AsyncCallback<Void> callback);

void anlegenNotiz(String url, AsyncCallback<Notiz> callback);

}
