package de.hdm.notefox.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Dialogbox für das Freigeben der Notizbücher und Notizen. Beinhaltet die
 * Radiobuttons 'Lesen, Editieren, Löschen'. Die Klasse beinhaltet den Panel für
 * den NotizBerechtigungPanel
 * 
 * @author Neriman Kocak und Harun Dalici
 */

public class FreigebenDialogBox extends DialogBox {

	private NotizobjektAdministrationAsync notizobjektadministration = ClientsideSettings.getNotizobjektVerwaltung();

	private final TextBox emailBox = new TextBox();

	/*
	 * Es werden Radiobuttons für 'Lesen, Editieren, Loeschen' erzeugt
	 */

	private final RadioButton lesen = new RadioButton("berechtigung", "Lesen");
	private final RadioButton editieren = new RadioButton("berechtigung", "Editieren");
	private final RadioButton loeschen = new RadioButton("berechtigung", "Löschen");

	private Notizobjekt notizobjekt;

	private NotizBerechtigungPanel nbPanel;

	FreigebenDialogBox(Notizobjekt notizobjekt) {

		lesen.setValue(true);

		this.notizobjekt = notizobjekt;
		nbPanel = new NotizBerechtigungPanel(notizobjekt);

		setText("Freigeben für..");
		setAnimationEnabled(true);
		setGlassEnabled(true);

		Button abbruch = new Button("Abbrechen");
		abbruch.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				FreigebenDialogBox.this.hide();
			}

		});

		emailBox.addStyleName("gwt-TextBox");
		Button freigeben = new Button("Freigeben");

		freigeben.addClickHandler(new FreigebenClickHandler());

		freigeben.addStyleName("gwt-Green-Button");
		abbruch.addStyleName("gwt-Green-Button");

		/**
		 * Nun werden Radio Buttons fuer die Auswahl der Berechtigungsart,
		 * definiert
		 */

		HorizontalPanel hPanelRadios = new HorizontalPanel();
		hPanelRadios.add(lesen);
		hPanelRadios.add(editieren);
		hPanelRadios.add(loeschen);

		// Schließen Button und ClickHandler mit hide()
		Button schliessenButton = new Button("X");
		schliessenButton.addStyleName("TopRightSchliessenButtonFreigebenDialogBoxKlasse");
		schliessenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}

		});

		Label emailBoxLabel = new Label("Email: ");
		Label Berechtigung = new Label("");
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		HorizontalPanel hPanelDown = new HorizontalPanel();

		/**
		 * main panel = panel
		 */

		panel.setHeight("300");
		panel.setWidth("500");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		/**
		 * emailtitel + emailinput = hpanel
		 */

		hPanel.add(emailBoxLabel);
		hPanel.add(emailBox);
		hPanel.add(schliessenButton);
		panel.add(hPanel);

		/**
		 * hPanelDown = button panel
		 */

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

	/**
	 * Nun wird der ClickHandler für das Freigeben erzeugt
	 *
	 */
	private class FreigebenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			notizobjektadministration.nachNutzerEmailSuchen(emailBox.getValue(), new AbfrageAsnyCallback());

		}

	}

	private class freigebenAsyncCallback implements AsyncCallback<Berechtigung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("[FEHLER] Berechtigung wurde nicht angelegt");

		}

		@Override
		public void onSuccess(Berechtigung result) {
			Window.alert("Berechtigung wurde angelegt");
			nbPanel.refresh();
		}

	}

	/**
	 * Es wird der AbfrageAsnycCallback für das Abfragen der Nutzer der Einträge
	 * in der Datenbank, erzeugt
	 *
	 */

	private class AbfrageAsnyCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler");

		}

		@Override
		public void onSuccess(Nutzer result) {

			if (result != null) {
				Berechtigung berechtigung = new Berechtigung();
				berechtigung.setBerechtigter(result);

				if (notizobjekt instanceof Notiz) {
					Notiz notiz = (Notiz) notizobjekt;
					berechtigung.setNotiz(notiz);
				} else if (notizobjekt instanceof Notizbuch) {
					Notizbuch notizbuch = (Notizbuch) notizobjekt;
					berechtigung.setNotizbuch(notizbuch);
				}

				Berechtigungsart berechtigungsart = null;
				if (editieren.getValue()) {
					berechtigungsart = Berechtigungsart.EDITIEREN;
				} else if (loeschen.getValue()) {
					berechtigungsart = Berechtigungsart.LOESCHEN;
				} else if (lesen.getValue()) {
					berechtigungsart = Berechtigungsart.LESEN;
				}
				berechtigung.setBerechtigungsart(berechtigungsart);
				notizobjektadministration.anlegenBerechtigung(berechtigung, new freigebenAsyncCallback());
			} else {
				Window.alert("Nutzer nicht gefunden!");
			}
		}
	}
}
