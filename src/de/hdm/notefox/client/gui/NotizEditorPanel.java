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
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.client.ClientsideSettings;

public class NotizEditorPanel extends VerticalPanel {

	NotizobjektAdministrationAsync notizobjektverwaltung = ClientsideSettings.getNotizobjektAdministrationAsync();

	Notiz ausgewahltesNotiz = null;
	//NotizObjektTree = null;
	
	
	HTML notizEditor = new HTML("<h3>Notiz Editor</h3>");
	Label Notiztitel = new Label("Titel");
	RichTextArea area = new RichTextArea();
	RichTextToolbar Rich = new RichTextToolbar(area);
	TextBox titel = new TextBox();
	DialogBox bx = new DialogBox();
	
	
	public NotizEditorPanel(){
		this.add(notizEditor);
		this.add(Notiztitel);
		this.add(titel);
		this.add(Rich);
		this.add(area);
		
		area.addStyleName("textarea");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		this.add(hPanel);
		
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
		
		Button neu = new Button("Neu");
		neu.addStyleName("gwt-Green-Button");
		neu.addClickHandler(new neuClickHandler());
		hPanel.add(neu);
		

		
	}
	private class speichernClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("OKEY!");
		}
		
	}
	
	private class speichernAsyncCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class loeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("OKEY!");
			
		}
		
	}
	
	private class loeschenAsyncCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
		}

	
	
	}
	
	private class freigebenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			FreigebenDialogBox dialogbox = new FreigebenDialogBox();
			int left = Window.getClientWidth()/ 2;
            int top = Window.getClientHeight()/ 2;
            dialogbox.addStyleName("gwt-DialogBox");
            dialogbox.setPopupPosition(left, top);
            dialogbox.show();
			
			
		}

	
	}


	private class freigebenAsyncCallback implements AsyncCallback{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
		}
		
	}


	private class neuClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("OKEY!");
			
		}
		
	}
	
	private class neuAsyncCallback implements AsyncCallback{

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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public NotizEditorPanel(final Notiz notiz) {
		add(titel);
		add(Rich);
		add(area);
		add(hPanel);
		
		area.addStyleName("textarea");
		speichern.addStyleName("gwt-Green-Button");
		
		
		speichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			notiz.setInhalt(area.getHTML());
			notiz.setTitel(titel.getText());
			// Window.alert("Titel: " + titel.getText() + "\n Inhalt: " + area.getHTML());
			
			
			notizobjektverwaltung.speichern(notiz, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}

				});
			}
		});
		
	}}*/