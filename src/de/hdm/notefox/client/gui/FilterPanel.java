package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class FilterPanel extends HorizontalPanel{

	
	
	 public FilterPanel(){
		 add(new Label("Hallo"));
		 Label titelLabel = new Label("Titel");
		 TextBox titelTextBox = new TextBox();
		 Label erstellDatumVonLabel = new Label("Erstelldatum von");
		 DateBox erstellDatumVonDateBox = new DateBox();
		 Label erstellDatumBisLabel = new Label("Erstelldatum bis");
		 DateBox erstellDatumBisDateBox = new DateBox();
		 Label modifikationsVonLabel = new Label("Modifikationsdatum von");
		 DateBox modifikationsVonDateBox = new DateBox();
		 Label modifikationsDatumBisLabel = new Label("Modifikationsdatum bis");
		 DateBox modifikationsBisDateBox = new DateBox();
		 Label faelligkeitsDatumVonLabel = new Label("FälligkeitsDatum von");
		 DateBox faelligkeitsDatumVonDateBox = new DateBox();
		 Label faelligkeitsDatumBisLabel = new Label("FälligkeitsDatum bis");
		 DateBox faelligkeitsDatumBisDateBox = new DateBox();
		 Label nutzerLabel = new Label("Nutzer");
		 Label berechtigungLabel = new Label("Berechtigung");
		 Label leseBerechtigungLabel = new Label("Leseberechtigung");
		 Label schreibBerechtitgungLabel = new Label("Schreibberechtigung");
		 Label loeschBerechtigungLabel = new Label("Löschberechtigung");
		 TextBox nutzerTextBox = new TextBox();
		 CheckBox berechtigungCheckBox = new CheckBox();
		 
		 VerticalPanel spalte1 = new VerticalPanel();
		 VerticalPanel spalte2 = new VerticalPanel();
		 VerticalPanel spalte3 = new VerticalPanel();
		 VerticalPanel spalte4 = new VerticalPanel();
		 VerticalPanel spalte5 = new VerticalPanel();
		 VerticalPanel spalte6 = new VerticalPanel();
		 add(spalte1);
		 add(spalte2);
		 add(spalte3);
		 add(spalte4);
		 add(spalte5);
		 add(spalte6);
		 
		 spalte1.add(titelLabel);
		 spalte1.add(titelTextBox);
		 
		 spalte2.add(erstellDatumVonLabel);
		 spalte2.add(erstellDatumVonDateBox);
		 
		 spalte2.add(erstellDatumBisLabel);
		 spalte2.add(erstellDatumBisDateBox);
		 
		 spalte3.add(modifikationsVonLabel);
		 spalte3.add(modifikationsVonDateBox);
		 
		 spalte3.add(modifikationsDatumBisLabel);
		 spalte3.add(modifikationsBisDateBox);
		 
		 spalte4.add(faelligkeitsDatumVonLabel);
		 spalte4.add(faelligkeitsDatumVonDateBox);
		 
		 spalte4.add(faelligkeitsDatumBisLabel);
		 spalte4.add(faelligkeitsDatumBisDateBox);
		 
		 spalte5.add(nutzerLabel);
		 spalte5.add(nutzerTextBox);
		 
		 spalte6.add(berechtigungLabel);
		 
		 spalte6.add(leseBerechtigungLabel);
		 spalte6.add(berechtigungCheckBox);
		 
		 spalte6.add(schreibBerechtitgungLabel);
		 spalte6.add(berechtigungCheckBox);
		 
		 spalte6.add(loeschBerechtigungLabel);
		 spalte6.add(berechtigungCheckBox);
		 
		 
		 

	
		 
	 }
}
