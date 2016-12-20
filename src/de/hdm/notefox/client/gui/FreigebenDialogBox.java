package de.hdm.notefox.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FreigebenDialogBox extends DialogBox {

	
	
	
	FreigebenDialogBox() {
	
		NotizBerechtigungPanel nbPanel = new NotizBerechtigungPanel();
		
		setText("Notiz Freigeben für..");
		setAnimationEnabled(true);
		setGlassEnabled(true);

		Button abbruch = new Button("Abbrechen");
		abbruch.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				FreigebenDialogBox.this.hide();
			}

		});

		Label uberschrift = new Label("Diese Notiz freigeben für: ");
		TextBox emailBox = new TextBox();
		emailBox.addStyleName("gwt-TextBox");
		Button freigeben = new Button("Freigeben");
		freigeben.addStyleName("gwt-Green-Button");
		abbruch.addStyleName("gwt-Green-Button");
		// Radio Buttons für Auswahl der Berechtigungsart
		RadioButton editieren = new RadioButton("berechtigung", "Editieren");
		RadioButton loeschen = new RadioButton("berechtigung", "Löschen");
		RadioButton erstellen = new RadioButton("berechtigung", "Erstellen");
		HorizontalPanel hPanelRadios = new HorizontalPanel();
		hPanelRadios.add(editieren);
		hPanelRadios.add(loeschen);
		hPanelRadios.add(erstellen);
		Label emailBoxLabel = new Label("Email: ");
		Label Berechtigung = new Label("Berechtigungsart:");
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		HorizontalPanel hPanelDown = new HorizontalPanel();
		// main panel = panel
		panel.setHeight("300");
		panel.setWidth("500");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(uberschrift);
		// emailtitel + emailinput = hpanel
		hPanel.add(emailBoxLabel);
		hPanel.add(emailBox);
		panel.add(hPanel);
		// hPanelDown = button panel
		hPanelDown.add(freigeben);
		hPanelDown.add(abbruch);
		panel.add(Berechtigung);
		panel.add(hPanelRadios);
		panel.add(hPanelDown);
		panel.add(nbPanel);
		setWidget(panel);

	}

	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		super.onPreviewNativeEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONKEYDOWN:
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
				hide();
			}

			break;
		}

	}
}
