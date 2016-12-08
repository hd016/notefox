package de.hdm.notefox.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 *Semiautomatisch vom Google Plugin erstellt. 
 *Gegenstück des asynchronen Interface (NotizobjektAdministration).
 */
public interface NotizobjektAdministrationAsync {

void initialisieren(AsyncCallback<Void> callback);

void anlegenNutzer(int nutzerId, String name, AsyncCallback<Nutzer> callback);

void anlegenNotizFuer(Nutzer c, AsyncCallback<Notiz> callback);

void anlegenNotizbuecherFuer(Nutzer n, AsyncCallback<Notizbuch> callback);

void anlegenNotizquelleFuer(Notiz no, AsyncCallback<Notizquelle> callback);

void anlegenFaelligkeitFuer(Notiz no, AsyncCallback<Datum> callback);

void nachAllenNotizquellenDesNutzersSuchen(Notiz no, AsyncCallback<ArrayList<Notizquelle>> callback);

void nachAllenFaelligkeitenDerNotizenDesNutzerSuchen(Notiz no, AsyncCallback<ArrayList<Datum>> callback);

void nachAllenNotizenDesNutzersSuchen(Notizbuch nb, AsyncCallback<ArrayList<Notiz>> callback);


void getNotiz(AsyncCallback<Notiz> callback);

void setNotiz(Notiz no, AsyncCallback<Void> callback);

void getNotizbuch(AsyncCallback<Notizbuch> callback);

void setNotizbuch(Notizbuch nb, AsyncCallback<Void> callback);

void nachAllenNotizenDesNutzersSuchen(Nutzer n, AsyncCallback<Vector<Notiz>> callback);

void nachAllenNotizbuechernDesNutzersSuchen(Nutzer n, AsyncCallback<Vector<Notizbuch>> callback);

void nachNotizIdSuchen(int id, AsyncCallback<Notiz> callback);

void nachNotizbuchIdSuchen(int id, AsyncCallback<Notizbuch> callback);

void nachNutzerNamenSuchen(String name, AsyncCallback<Vector<Nutzer>> callback);

void nachNutzerIdSuchen(int nutzerid, AsyncCallback<Nutzer> callback);

void nachAllenNutzernSuchen(AsyncCallback<Vector<Nutzer>> callback);

void nachAllenNotizenSuchen(AsyncCallback<Vector<Notiz>> callback);

void nachAllenNotizbuechernSuchen(AsyncCallback<Vector<Notizbuch>> callback);

void speichern(Notiz no, AsyncCallback<Void> callback);

void speichern(Notizbuch nb, AsyncCallback<Void> callback);

void speichern(Nutzer n, AsyncCallback<Void> callback);

void loeschenNotiz(Notiz no, AsyncCallback<Void> callback);

void loeschenNotizbuch(Notizbuch nb, AsyncCallback<Void> callback);

void loeschenNutzer(Nutzer n, AsyncCallback<Void> callback);

void loeschenNotizquelleVon(Notizquelle nq, AsyncCallback<Void> callback);

void loeschenDatumVon(Datum d, AsyncCallback<Void> callback);

}
