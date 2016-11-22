package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.notefox.client.gui.Login;
import de.hdm.notefox.client.gui.LoginView;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Notefox implements EntryPoint {

	
	public void onModuleLoad() {
		LoginView login = new LoginView();
		RootPanel.get().add(login);
	}
	
}
