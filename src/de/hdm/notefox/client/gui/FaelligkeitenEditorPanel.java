package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class FaelligkeitenEditorPanel extends VerticalPanel{
	
	DatePicker date = new DatePicker();
	HTML uberschrift = new HTML("<h3>Fälligkeit</h3>");
	TextBox auswahl = new  TextBox();
	Button speichern = new Button("Speichern");
		
	public FaelligkeitenEditorPanel(){
		speichern.addStyleName("gwt-Green-Button");
		auswahl.setEnabled(false);
		date.addStyleName("datePicker");
		uberschrift.addStyleName("datePicker");
		auswahl.addStyleName("TextBox-readonly");
		speichern.addStyleName("datePicker");
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		add(uberschrift);
		add(date);
		add(auswahl);
		add(speichern);
		
	}
	
	void getHiglightedDate(){
		date.getHighlightedDate();
		
		
	}
	
	
	
	
	

}
