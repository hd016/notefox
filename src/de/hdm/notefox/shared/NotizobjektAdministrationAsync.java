package de.hdm.notefox.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/*
 * Wird Semiautomatisch vom Google Plugin erstellt. 
 *Gegenstueck des asynchronen Interface (NotizobjektAdministration).
 */
public interface NotizobjektAdministrationAsync {

	void anlegenNutzer(int nutzerId, String name, AsyncCallback<Nutzer> callback);

	void anlegenNotiz(Notizbuch notizbuch, AsyncCallback<Notiz> callback);

	void anlegenNotizbuecherFuer(Nutzer n, AsyncCallback<Notizbuch> callback);

	void nachAllenNotizenDesNotizbuchesSuchen(Notizbuch nb, AsyncCallback<List<Notiz>> callback);

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

	void nachAllenBerechtigungenDerNotizSuchen(Notiz no, AsyncCallback<List<Berechtigung>> callback);

	void nachAllenBerechtigungenDesNotizbuchesSuchen(Notizbuch nb, AsyncCallback<List<Berechtigung>> callback);

	void anlegenBerechtigung(Berechtigung berechtigung, AsyncCallback<Berechtigung> callback);

	void loeschenBerechtigung(Berechtigung be, AsyncCallback<Berechtigung> callback);

	void nachNotizenDesFilterSuchen(Filterobjekt filter, AsyncCallback<List<Notiz>> callback);

}
