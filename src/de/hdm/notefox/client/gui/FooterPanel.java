package de.hdm.notefox.client.gui;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FooterPanel extends VerticalPanel {

	public FooterPanel() {

		Image footerImage = new Image("/images/notefoxeinbetten.png");
		footerImage.addStyleName("footerButton");
		footerImage.getElement().getStyle().setCursor(Cursor.POINTER);
		footerImage.addClickHandler(new NotizquelleClickHandler());

		Image footerHdM = new Image("/images/logohdm.png");
		footerHdM.addStyleName("footerHDM");

		Image footerWi = new Image("/images/logowi.png");
		footerWi.addStyleName("footerWi");

		HorizontalPanel footerPanel = new HorizontalPanel();
		footerPanel.add(footerImage);
		footerPanel.add(footerWi);
		footerPanel.add(footerHdM);
		RootPanel.get("footerPanel").add(footerPanel);
	}

	private class NotizquelleClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NotizquelleDialogBox dialogbox = new NotizquelleDialogBox();
			dialogbox.show();
		}
	}

}
