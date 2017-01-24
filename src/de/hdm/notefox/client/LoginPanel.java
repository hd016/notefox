package de.hdm.notefox.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.notefox.shared.LoginInfo;

/**
 * Login-Klasse des Projekts <b>Notefox</b>. Die Klasse LoginPanel erbt von der
 * Klasse VerticalPanel. Zunächst wird ein neues Label erzeugt und definiert.
 * Diese Klasse ist somit die erste Starteseite, bevor man auf das eigentliche
 * Notizssystem zugreifen kann.
 * @author Harun Dalici, Muhammed Simsek

 */

public class LoginPanel extends VerticalPanel {

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Bitte melden Sie sich an um auf Notefox zuzugreifen.");
	private Anchor signInLink = new Anchor("Anmelden");

	public LoginPanel(LoginInfo loginInfo) {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		add(loginPanel);
	}

}
