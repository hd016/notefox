package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginView extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel contentPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	NutzerMenu menuVonNutzer = new NutzerMenu();


	public LoginView(NutzerMenu menuVonNutzer){
		initWidget(this.vPanel); 
		this.menuVonNutzer = menuVonNutzer;
		LoginMenu menu = new LoginMenu(this);
		this.vPanel.add(menu);

	}
	
	public void menuAnzeigen(){
		//this.vPanel.add(contentPanel);
		//this.contentPanel.clear();
		//this.vPanel.clear();
		menuVonNutzer.zeigeMenuAn();
	}

}
