package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends Composite {
// Login 
	private VerticalPanel hPanel = new VerticalPanel();

	
	public Login() {
		initWidget(this.hPanel);

		HorizontalPanel hPanel = new HorizontalPanel();
		Image logo = new Image();
		logo.setStyleName("Logo");
		this.hPanel.add(logo);
	}
	
}

