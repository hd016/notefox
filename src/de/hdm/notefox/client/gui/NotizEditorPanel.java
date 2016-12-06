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
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;

public class NotizEditorPanel extends VerticalPanel {

	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);

	Button speichern = new Button("Speichern");
	RichTextArea area = new RichTextArea();
	RichTextToolbar Rich = new RichTextToolbar(area);
	TextBox titel = new TextBox();
	DialogBox bx = new DialogBox();
	
	
	public NotizEditorPanel(final Notiz notiz) {
		add(titel);
		add(Rich);
		add(area);
		add(speichern);
		SafeHtmlBuilder html = new SafeHtmlBuilder();
		
		area.addStyleName("textarea");
		speichern.addStyleName("gwt-Green-Button");
		
		
		speichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			notiz.setInhalt(area.getHTML());
			notiz.setTitel(titel.getText());
			Window.alert("Titel: " + titel.getText() + "\n Inhalt: " + area.getHTML());
			
			
			/*	administration.speichern(notiz, new AsyncCallback<Void>() {

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
		*/
	}
		
});
		
	}}