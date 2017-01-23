package de.hdm.notefox.client.gui;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FooterPanel extends VerticalPanel {

	/**
	 * Die Klasse FooterPanel ist der unterste Abschnitt der Benutzeroberfläche.
	 * Sie stellt drei Logos zur Verfügung.
	 * 
	 * @author Neriman Kocak und Harun Dalici
	 */

	public FooterPanel() {

		/*
		 * Image für Notefox einbinden
		 */

		Image footerImage = new Image("/images/notefoxeinbetten.png");
		footerImage.addStyleName("footerButton");
		footerImage.getElement().getStyle().setCursor(Cursor.POINTER);
		footerImage.addClickHandler(new NotizquelleClickHandler());

		/*
		 * Image für HdM einbinden
		 */

		Image footerHdM = new Image("/images/logohdm.png");
		footerHdM.addStyleName("footerHDM");

		/*
		 * Image für Wirtschaftsinformatik einbinden
		 */

		Image footerWi = new Image("/images/logowi.png");
		footerWi.addStyleName("footerWi");

		/*
		 * Es wird ein neues HorizontalPanel erzeugt und die verschiedenen
		 * Images hinzugefügt.
		 * 
		 */

		HorizontalPanel footerPanel = new HorizontalPanel();
		footerPanel.add(footerImage);
		footerPanel.add(footerWi);
		footerPanel.add(footerHdM);
		RootPanel.get("footerPanel").add(footerPanel);
	}

	/**
	 * Der ClickHandler ist für das Erscheinen des NotizquelleDialogBoxes und
	 * für die Übernahme der Notizquelle einer fremden Webseite
	 */

	private class NotizquelleClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NotizquelleDialogBox dialogbox = new NotizquelleDialogBox();
			dialogbox.show();
		}
	}

}
