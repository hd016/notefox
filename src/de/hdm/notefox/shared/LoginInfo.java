package de.hdm.notefox.shared;

import java.io.Serializable;

import de.hdm.notefox.shared.Nutzer;

/**
 *TODO
 * 
 * @author Harun Dalici & Muhammed Simsek
 */
public class LoginInfo implements Serializable {

	/**
	  * 
	  */
	private static final long serialVersionUID = 1L;

	/**
	* Aktueller Loginzustand
	*/
	private boolean loggedIn = false;

	/**
	* Die Login-URL
	*/
	private String loginUrl;

	/**
	* Die Logout-URL
	*/
	private String logoutUrl;

	/**
	* Die Email Adresse des eingeloggten Nutzers
	*/
	private String email;

	/**
	 *  eingeloggter Nutzer
	 */
	private Nutzer nutzer;


	/**
	* Auslesen des Login-Status
	*/
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	* Setzen des Login-Status
	*/
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	* Auslesen der Login URL
	*/
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	* Setzen der Login URL
	*/
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	* Auslesen der Logout URL
	*/
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	* Setzen der Logout URL
	*/
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	* Auslesen der Email-Adresse
	*/
	public String getEmail() {
		return email;
	}

	/**
	* Setzen der Email-Adresse
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	* Auslesen des Nutzers
	*/
	public Nutzer getNutzer() {
		return nutzer;
	}

	/**
	* Setzen des Nutzers
	*/
	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
}