package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NotizbuchEditorPanel extends VerticalPanel {

	
	HTML uberschrift = new HTML("<h3>Notizbuch</h3>");
	TextBox titel = new TextBox();
	TextBox subtitel = new TextBox();
	HorizontalPanel hPanelButton = new HorizontalPanel();
	//Button erstellen = new Button("Speichern");
	//Button erstellenUndNotiz = new Button("Speichern und neues Notiz erstellen");
	//Button notizHinzufugen = new Button("Speichern und Notizen hinzufügen");
	Label ltitel = new Label("Titel");
	Label lsubtitel = new Label("Subtitel");
//	HTML uberschriftVorhanden = new HTML("<h3>Vorhandene Notizbücher auswählen</h3>");
//	RadioButton Notizbuch1 = new RadioButton("Notizbuch", "Notizbuch1");
//	RadioButton Notizbuch2 = new RadioButton("Notizbuch", "Notizbuch2");
//	RadioButton Notizbuch3 = new RadioButton("Notizbuch", "Notizbuch3");
//	VerticalPanel radioPanel = new VerticalPanel();
//	

	 
	
	
	
	public NotizbuchEditorPanel(){
		add(uberschrift);
		add(ltitel);
		add(titel);
		add(lsubtitel);
		add(subtitel);
		//erstellen.addStyleName("gwt-Green-Button");
		//erstellenUndNotiz.addStyleName("gwt-Green-Button-big");
		//notizHinzufugen.addStyleName("gwt-Green-Button-big");
		//hPanelButton.add(erstellen);
		//hPanelButton.add(erstellenUndNotiz);
		//hPanelButton.add(notizHinzufugen);
		add(hPanelButton);
//		radioPanel.add(Notizbuch1);
//		radioPanel.add(Notizbuch2);
//		radioPanel.add(Notizbuch3);
//		add(uberschriftVorhanden);
//		add(radioPanel);
		
		
		
	}
}
