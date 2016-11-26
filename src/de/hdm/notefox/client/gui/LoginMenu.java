package de.hdm.notefox.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;


public class LoginMenu extends Composite {
	private HorizontalPanel hPanel = new HorizontalPanel();
	private LoginView main;


public LoginMenu(LoginView main){
	initWidget(this.hPanel);
	this.main = main;
	Label landingText = new Label();
	landingText.addStyleName("leftmenu");
	this.hPanel.add(landingText);
	Button anmeldeButton = new Button("Anmelden");
	anmeldeButton.addStyleName("Button");
	
	
	
	
	anmeldeButton.addClickHandler(new AnmeldenClickHandler());
	this.hPanel.add(anmeldeButton);
	
	Button registerButton = new Button("Registrieren");
	registerButton.addClickHandler(new RegisterClickHandler());
	this.hPanel.add(registerButton);
	
	
	}
private class AnmeldenClickHandler implements ClickHandler{
	public void onClick(ClickEvent event) {
		main.menuAnzeigen();
	}
}

private class RegisterClickHandler implements ClickHandler{

	public void onClick(ClickEvent event) {
		
	}
	
}
}


