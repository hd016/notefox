package de.hdm.notefox.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.notefox.shared.Nutzer;

/**
 * Synchrone Schnittstelle fuer die Klasse <code>LoginServiceImpl</code>.
 * 
 * @author Harun Dalici & Muhammed Simsek
 */

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	/**
	 * Ausfuehren des Logins und ablegen aller relevanten Nutzer Informationen in
	 * einem LoginInfo Objekt
	 * 
	 * @param requestUri
	 *            Die Basis URL der aufrufenden Seite (ermittelt ueber
	 *            GWT.getHostPageBaseURL())
	 * @return
	 */
	public LoginInfo login(String requestUri);

	/**
	 * Auslesen des aktuellen Profils
	 */
	Nutzer getCurrentNutzer();

}