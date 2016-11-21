package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class NutzerMenu extends Composite {
	
	private HorizontalPanel hPanel = new HorizontalPanel();	
	private HorizontalPanel cPanel = new HorizontalPanel();

	public NutzerMenu(){

		initWidget(this.hPanel); 
		
		
		

}

	public void zeigeMenuAn(){
		Button meineNotizen = new Button("Meine Notizen");
		Button meineNotizBuecher = new Button("Meine Notizbücher");
		Button meineBerechtigungen = new Button("Meine Berechtigungen");
		Button meineFalligkeiten = new Button("Meine Fälligkeiten");
		
		this.hPanel.add(meineNotizen);
		this.hPanel.add(meineNotizBuecher);
		this.hPanel.add(meineBerechtigungen);
		this.hPanel.add(meineFalligkeiten);
		RootPanel.get().add(hPanel);
	}
	
}