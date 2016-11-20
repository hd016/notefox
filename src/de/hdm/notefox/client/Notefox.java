package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.Login;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Notefox implements EntryPoint {

	
	public void onModuleLoad() {
		Login hauptlogin = new Login();
		RootPanel.get().add(hauptlogin);
	}
	
}
