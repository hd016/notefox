package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Notefox implements EntryPoint {

	// first Page -> Login Page
	
	private VerticalPanel loginPanel = new VerticalPanel();
	private HorizontalPanel menuPanel = new HorizontalPanel();
	private Label loginLabel = new Label("Um NoteFox benutzen zu können müssen Sie sich anmelden oder Registrieren");
	
	private Anchor signInLink = new Anchor("Anmelden");
	private final Button loginButton = new Button("Anmelden");
	private final Anchor logOutLink = new Anchor("Abmelden");
	private Button logOutButton = new Button("Abmelden");
	
	
	@Override
	//Startmethode
	public void onModuleLoad() {
		
		loginPanel.add(loginButton);
		loginPanel.add(logOutButton);
		RootPanel.get().add(loginPanel);
		
	}

	
}
