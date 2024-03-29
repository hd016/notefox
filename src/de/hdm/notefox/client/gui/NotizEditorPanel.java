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

/**
 * Die Klasse NotizEditorPanel behinhaltet den Texteditor für die Notizen und
 * das Formular für die Notizbücher. Die Entscheidung über die Formulare folgt
 * über einer Abfrage des Notizobjektes über die Methode instanceof.
 * 
 * @author Neriman Kocak und Harun Dalici
 *
 */
public class NotizEditorPanel extends HorizontalPanel {

	NotizobjektAdministrationAsync notizobjektverwaltung = ClientsideSettings.getNotizobjektVerwaltung();
	HTML notizEditor = new HTML("<h3>Notiz</h3>");

	Panel faelligkeiten = new VerticalPanel();
	Panel eigentuemer = new VerticalPanel();

	Notiz ausgewahltesNotiz = null;

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
		vPanel.addStyleName("notizeditorpanelVpanel");
		this.add(vPanel);
		this.add(faelligkeiten);

		area.addStyleName("textarea");

		HorizontalPanel hPanel = new HorizontalPanel();
		vPanel.add(hPanel);

		/*
		 * Buttons für den TextEditor erzeugen
		 */

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

	/**
	 * Nach der Entscheidung über das Notizobjekt ändert sich der Editor. Bei
	 * Notiz wird das Texteditor angezeigt. Bei Notizbuch wird das Formular für
	 * die Verwaltung des Notizbuches angezeigt.
	 * 
	 * @param notizobjekt
	 */

	public void setNotizobjekt(final Notizobjekt notizobjekt) {
		this.notizobjekt = notizobjekt;
		titel.setValue(notizobjekt.getTitel());
		subtitel.setValue(notizobjekt.getSubtitel());
		area.setHTML(notizobjekt.getInhalt());
		if (notizobjekt instanceof Notiz) {
			notizEditor.setHTML("<h3>Notiz Editor</h3>");
			Notiz notiz = (Notiz) notizobjekt;
			Rich.setVisible(true);
			area.setVisible(true);
			notizbuchSubtitel.setVisible(false);
			subtitel.setVisible(false);
			faelligkeiten.clear();
			faelligkeiten.add(new FaelligkeitenEditorPanel(notiz));
		} else {
			notizEditor.setHTML("<h3>Notizbuch</h3>");
			faelligkeiten.clear();
			Rich.setVisible(false);
			area.setVisible(false);
			notizbuchSubtitel.setVisible(true);
			subtitel.setVisible(true);
		}

		/**
		 * Erstellen der Listbox für die Auswahl der Eigentümer des Notizbuchs
		 */

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

	/**
	 * ClickHandler und AsnycCallback für das Speichern der Notizobjekte
	 * 
	 */

	private class speichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			notizobjekt.setTitel(titel.getText());
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
			Notiz vorhandendeNotiz = (Notiz) notizobjekt;

			vorhandendeNotiz.setId(result.getId());
			vorhandendeNotiz.setFaelligkeitsdatum(result.getFaelligkeitsdatum());
			vorhandendeNotiz.setEigentuemer(result.getEigentuemer());
			vorhandendeNotiz.setInhalt(result.getInhalt());
			vorhandendeNotiz.setModifikationsdatum(result.getModifikationsdatum());
			vorhandendeNotiz.setNotizbuch(result.getNotizbuch());
			vorhandendeNotiz.setSubtitel(result.getSubtitel());
			vorhandendeNotiz.setErstelldatum(result.getErstelldatum());
			vorhandendeNotiz.setTitel(result.getTitel());

			faelligkeiten.clear();
			faelligkeiten.add(new FaelligkeitenEditorPanel(vorhandendeNotiz));
			notefox.ersetzeBaum(result.getNotizbuch());

			Window.alert("Notiz gespeichert");
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
			notizobjekt.setId(result.getId());

			notizobjekt.setId(result.getId());
			notizobjekt.setEigentuemer(result.getEigentuemer());
			notizobjekt.setInhalt(result.getInhalt());
			notizobjekt.setModifikationsdatum(result.getModifikationsdatum());
			notizobjekt.setSubtitel(result.getSubtitel());
			notizobjekt.setErstelldatum(result.getErstelldatum());
			notizobjekt.setTitel(result.getTitel());

			notefox.ersetzeBaum(result);

			Window.alert("Notizbuch wurde gespeichert");
		}

	}

	/**
	 * ClickHandler und AsnycCallback für das Löschen der Notizobjekte
	 * 
	 */

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

	/**
	 * ClickHandler und AsnycCallback für das Freigeben der Notizobjekte
	 * 
	 */

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
