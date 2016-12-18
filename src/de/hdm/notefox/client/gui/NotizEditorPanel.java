package de.hdm.notefox.client.gui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;
import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.client.Notefox;

public class NotizEditorPanel extends HorizontalPanel {

	HTML notizEditor = new HTML("<h3>Notiz</h3>");

	
	NotizobjektAdministrationAsync notizobjektverwaltung = ClientsideSettings.getNotizobjektAdministrationAsync();

	FaelligkeitenEditorPanel faelligkeiten = new FaelligkeitenEditorPanel();
	
	Notiz ausgewahltesNotiz = null;
	// NotizObjektTree = null;

	Label Notiztitel = new Label("Titel");
	Label notizbuchSubtitel = new Label("Subtitel");
	RichTextArea area = new RichTextArea();
	RichTextToolbar Rich = new RichTextToolbar(area);
	TextBox titel = new TextBox();
	TextBox subtitel = new TextBox();
	DialogBox bx = new DialogBox();

	private Notizobjekt notizobjekt;

	VerticalPanel vPanel = new VerticalPanel();
	
	private Notefox notefox;

	public NotizEditorPanel(Notefox notefox) {
		this.notefox = notefox;
		vPanel.add(notizEditor);
		vPanel.add(Notiztitel);
		vPanel.add(titel);
		vPanel.add(notizbuchSubtitel);
		vPanel.add(subtitel);
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

	public void setNotizobjekt(Notizobjekt notizobjekt) {
		this.notizobjekt = notizobjekt;
		titel.setValue(notizobjekt.getTitel());
		area.setHTML(notizobjekt.getInhalt());
		if(notizobjekt instanceof Notiz){
			Notiz notiz = (Notiz) notizobjekt;
			faelligkeiten.setVisible(true);
			Rich.setVisible(true);
			area.setVisible(true);
			notizbuchSubtitel.setVisible(false);
			subtitel.setVisible(false);
			faelligkeiten.setFaelligkeisdatum(notiz.getFaelligkeitsdatum());
			
		}
		else {
			notizEditor.setHTML("<h3>Notizbuch</h3>");
			faelligkeiten.setVisible(false);
			Rich.setVisible(false);
			area.setVisible(false);
			notizbuchSubtitel.setVisible(true);
			subtitel.setVisible(true);
		}
	}

	private class speichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("OKEY!");
			notizobjekt.setTitel(titel.getValue());
			notizobjekt.setInhalt(area.getHTML());


			if (notizobjekt instanceof Notiz) {
				Notiz notiz = (Notiz) notizobjekt;
				notiz.setFaelligkeitsdatum(faelligkeiten.getFaelligkeitsdatum());
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
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Notiz result) {
			Window.alert("Ok");
			setNotizobjekt(result);
		}

	}

	private class NotizbuchSpeichernAsyncCallback implements AsyncCallback<Notizbuch> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Notizbuch result) {
			setNotizobjekt(result);
		}

	}

	private class loeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(notizobjekt instanceof Notiz){
				Notiz notiz = (Notiz) notizobjekt;
				notizobjektverwaltung.loeschenNotiz(notiz, new loeschenAsyncCallback());
			} else if(notizobjekt instanceof Notizbuch){
				Notizbuch notizbuch = (Notizbuch) notizobjekt;
				notizobjektverwaltung.loeschenNotizbuch(notizbuch, new loeschenAsyncCallback());
			}
			

		}

	}

	private class loeschenAsyncCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Löschen erfolgreich");
			notefox.schlieseInhalt();
			notefox.ersetzeBaum();
		}

	}

	private class freigebenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			FreigebenDialogBox dialogbox = new FreigebenDialogBox();
			int left = Window.getClientWidth() / 2;
			int top = Window.getClientHeight() / 2;
			dialogbox.addStyleName("gwt-DialogBox");
			dialogbox.setPopupPosition(left, top);
			dialogbox.show();

		}

	}

	private class freigebenAsyncCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub

		}

	}

}

/*
 * public NotizEditorPanel(final Notiz notiz) { add(titel); add(Rich);
 * add(area); add(hPanel);
 * 
 * area.addStyleName("textarea"); speichern.addStyleName("gwt-Green-Button");
 * 
 * 
 * speichern.addClickHandler(new ClickHandler() {
 * 
 * @Override public void onClick(ClickEvent event) {
 * notiz.setInhalt(area.getHTML()); notiz.setTitel(titel.getText()); //
 * Window.alert("Titel: " + titel.getText() + "\n Inhalt: " + area.getHTML());
 * 
 * 
 * notizobjektverwaltung.speichern(notiz, new AsyncCallback<Void>() {
 * 
 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated
 * method stub
 * 
 * }
 * 
 * @Override public void onSuccess(Void result) { // TODO Auto-generated method
 * stub
 * 
 * }
 * 
 * }); } });
 * 
 * }}
 */