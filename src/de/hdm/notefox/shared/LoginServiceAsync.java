package de.hdm.notefox.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.notefox.shared.Nutzer;

/**
 * @author Harun Dalici & Muhammed Simsek
 */
public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

	void getCurrentNutzer(AsyncCallback<Nutzer> callback);

}