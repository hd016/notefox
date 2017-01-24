package de.hdm.notefox.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Die Klasse NotizquelleDialogbox, stellt einen Dialogbox zur Verfügung. Davon
 * kann der Nutzer, den Javascript Code für die Einbettung der
 * Notizquellenübernahme auf seiner eigenen Website, übermitteln
 * 
 * @author Neriman Kocak und Harun Dalici
 */

public class NotizquelleDialogBox extends DialogBox {

	public NotizquelleDialogBox() {

		setText("NoteFox auf deiner Webseite einbetten..");
		setAnimationEnabled(true);
		setGlassEnabled(true);

		addStyleName("gwt-DialogBox-Notizquelle");
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel hPanelTextBox = new HorizontalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();

		Label einleitung = new Label("Bitte Kopiere das HTML Code und füge es auf deiner Webseite ein.");

		TextBox output = new TextBox();
		Button schliessenButton = new Button("X");
		schliessenButton.addStyleName("TopRightSchliessenButton");

		/**
		 * Für das Schließen der Dialogbox.
		 */
		schliessenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}

		});
		output.setReadOnly(isGlassEnabled());
		output.addStyleName("gwt-TextBox-Dialogbox");

		/*
		 * Javascript Code für die Einbettung der Notefox+ Button
		 */

		output.setText("<a id=\"note-fox-link\" href=\"#\">Link</a><script>var notefoxUrl = \""
				+ GWT.getHostPageBaseURL()
				+ "\";document.getElementById(\"note-fox-link\").setAttribute(\"href\", notefoxUrl + \"?url=\" + window.location);</script>");
		output.setText(
				"<p><strong>Teile diesen Inhalt auf Notefox!</strong></p><p>&nbsp;</p><p>&nbsp;</p><a id=\"note-fox-link\" href=\"#\"><img src=\"http://leqsico.de/notefox/notefox+.png\" alt=\"\" width=\"500\" height=\"300\" /></a><script>var notefoxUrl ="
						+ GWT.getHostPageBaseURL()
						+ ";document.getElementById(\"note-fox-link\").setAttribute(\"href\", notefoxUrl + \"?url=\" + window.location);</script>");
		hPanel.add(einleitung);
		hPanel.add(schliessenButton);
		hPanelTextBox.addStyleName("gwt-DialogBox");
		hPanelTextBox.add(output);

		/**
		 * main panel = panel
		 */

		panel.add(hPanel);
		panel.add(hPanelTextBox);
		panel.setSpacing(10);

		setWidget(panel);

	}

	/**
	 * Die Methode beschreibt das Schließen des Dialogboxes
	 */

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