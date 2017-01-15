package de.hdm.notefox.client.gui;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.client.Notefox;
import de.hdm.notefox.shared.LoginInfo;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.NutzerAusnahme;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

public class NotizEditorPanel extends HorizontalPanel {

	NotizobjektAdministrationAsync notizobjektverwaltung = ClientsideSettings.getNotizobjektVerwaltung();
	HTML notizEditor = new HTML("<h3>Notiz</h3>");

	Panel faelligkeiten = new VerticalPanel();
	Panel eigentuemer = new VerticalPanel();

	Notiz ausgewahltesNotiz = null;
	// NotizObjektTree = null;

	Label Notiztitel = new Label("Titel");
	Label eigentuemerTitel = new Label("Eigentümer");
	Label notizbuchSubtitel = new Label("Subtitel");
	RichTextArea area = new RichTextArea();
	RichTextToolbar Rich = new RichTextToolbar(area);
	TextBox titel = new TextBox();
	TextBox subtitel = new TextBox();
	DialogBox bx = new DialogBox();

	private Notizobjekt notizobjekt;

	VerticalPanel vPanel = new VerticalPanel();

	private Notefox notefox;
	private LoginInfo loginInfo;

	public NotizEditorPanel(Notefox notefox, LoginInfo loginInfo) {
		this.notefox = notefox;
		this.loginInfo = loginInfo;
		vPanel.add(notizEditor);
		vPanel.add(Notiztitel);
		vPanel.add(titel);
		vPanel.add(notizbuchSubtitel);
		vPanel.add(subtitel);
		vPanel.add(eigentuemerTitel);
		vPanel.add(eigentuemer);
		vPanel.add(Rich);
		vPanel.add(area);
		this.add(vPanel);
		this.add(faelligkeiten);

		area.addStyleName("textarea");

		HorizontalPanel hPanel = new HorizontalPanel();
		vPanel.add(hPanel);

		Button speichern = new Button("Speichern");
		speichern.addClickHandler(new speichernClickHandler());
		speichern.addStyleName("gwt-Green-Button");
		hPanel.add(speichern);

		Button loeschen = new Button("Löschen");
		loeschen.addStyleName("gwt-Green-Button");
		loeschen.addClickHandler(new loeschenClickHandler());
		hPanel.add(loeschen);

		Button freigeben = new Button("Freigeben");
		freigeben.addStyleName("gwt-Green-Button");
		freigeben.addClickHandler(new freigebenClickHandler());
		hPanel.add(freigeben);

	}

	public void setNotizobjekt(final Notizobjekt notizobjekt) {
		this.notizobjekt = notizobjekt;
		titel.setValue(notizobjekt.getTitel());
		area.setHTML(notizobjekt.getInhalt());
		if (notizobjekt instanceof Notiz) {
			Notiz notiz = (Notiz) notizobjekt;
			Rich.setVisible(true);
			area.setVisible(true);
			notizbuchSubtitel.setVisible(false);
			subtitel.setVisible(false);
			faelligkeiten.clear();
			faelligkeiten.add(new FaelligkeitenEditorPanel(notiz));
		} else {
			notizEditor.setHTML("<h3>Notizbuch</h3>");
			faelligkeiten.setVisible(false);
			Rich.setVisible(false);
			area.setVisible(false);
			notizbuchSubtitel.setVisible(true);
			subtitel.setVisible(true);
		}

		final ListBox besitzerListBox = new ListBox();
		besitzerListBox.setEnabled(loginInfo.getNutzer().equals(notizobjekt.getEigentuemer()));
		besitzerListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int nutzerId = Integer.valueOf(besitzerListBox.getSelectedValue());
				notizobjektverwaltung.nachNutzerIdSuchen(nutzerId, new AsyncCallback<Nutzer>() {

					@Override
					public void onSuccess(Nutzer result) {
						notizobjekt.setEigentuemer(result);
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});

		notizobjektverwaltung.nachAllenNutzernSuchen(new AsyncCallback<List<Nutzer>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Nutzer> result) {
				int i = 0;
				for (Nutzer nutzer : result) {
					besitzerListBox.addItem(nutzer.getEmail(), nutzer.getNutzerId() + "");
					if (nutzer.equals(notizobjekt.getEigentuemer())) {
						besitzerListBox.setSelectedIndex(i);
					}
					i++;
				}
			}
		});

		eigentuemer.clear();
		eigentuemer.add(besitzerListBox);
	}

	private class speichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			notizobjekt.setTitel(titel.getValue());
			notizobjekt.setInhalt(area.getHTML());

			if (notizobjekt instanceof Notiz) {
				Notiz notiz = (Notiz) notizobjekt;
				notizobjektverwaltung.speichern(notiz, new NotizSpeichernAsyncCallback());
			} else if (notizobjekt instanceof Notizbuch) {
				Notizbuch notizbuch = (Notizbuch) notizobjekt;
				notizbuch.setSubtitel(subtitel.getText());
				notizobjektverwaltung.speichern(notizbuch, new NotizbuchSpeichernAsyncCallback());
			}
		}

	}

	private class NotizSpeichernAsyncCallback implements AsyncCallback<Notiz> {

		@Override
		public void onFailure(Throwable caught) {
			if (caught instanceof NutzerAusnahme) {
				NutzerAusnahme nutzerAusnahme = (NutzerAusnahme) caught;
				Window.alert(nutzerAusnahme.getMessage());
				Window.alert("Notiz wurde nicht gespeichert");

			}
		}

		@Override
		public void onSuccess(Notiz result) {
			Window.alert("Notiz gespeichert");
			notefox.baumNeuOeffnen();
		}

	}

	private class NotizbuchSpeichernAsyncCallback implements AsyncCallback<Notizbuch> {

		@Override
		public void onFailure(Throwable caught) {
			if (caught instanceof NutzerAusnahme) {
				NutzerAusnahme nutzerAusnahme = (NutzerAusnahme) caught;
				Window.alert(nutzerAusnahme.getMessage());
				Window.alert("Notizbuch wurde nicht gespeichert");

			}
		}

		@Override
		public void onSuccess(Notizbuch result) {
			Window.alert("Notizbuch wurde gespeichert");
			notefox.baumNeuOeffnen();
		}

	}

	private class loeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Sind Sie sich sicher, ob Sie die Notiz löschen möchten?")) {
				if (notizobjekt instanceof Notiz) {
					Notiz notiz = (Notiz) notizobjekt;
					notizobjektverwaltung.loeschenNotiz(notiz, new loeschenAsyncCallback());
				} else if (notizobjekt instanceof Notizbuch) {
					Notizbuch notizbuch = (Notizbuch) notizobjekt;
					notizobjektverwaltung.loeschenNotizbuch(notizbuch, new loeschenAsyncCallback());
				}
			}
		}
	}

	private class loeschenAsyncCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			if (caught instanceof NutzerAusnahme) {
				NutzerAusnahme nutzerAusnahme = (NutzerAusnahme) caught;
				Window.alert(nutzerAusnahme.getMessage());
				Window.alert("Löschen nicht erfolgreich");
			}
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Löschen erfolgreich");
			notefox.schlieseInhalt();
			notefox.ersetzeBaum(null);
		}

	}

	private class freigebenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			FreigebenDialogBox dialogbox = new FreigebenDialogBox(notizobjekt);
			int left = Window.getClientWidth() / 2;
			int top = Window.getClientHeight() / 2;
			dialogbox.addStyleName("gwt-DialogBox");
			dialogbox.setPopupPosition(left, top);
			dialogbox.show();

		}

	}

}
