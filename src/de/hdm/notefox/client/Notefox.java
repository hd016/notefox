package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.NotizBaumModel;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;

public class Notefox implements EntryPoint {

	
	HorizontalPanel hPanel = new HorizontalPanel();
	VerticalPanel vPanel_inhalt = new VerticalPanel();
	Button loeschen = new Button("Löschen");
	Button editieren = new Button("Editieren");
	Button suchen = new Button("Suchen");
	Button freigeben = new Button("Freigeben");
	Label test = new Label("Notizbuch Klick erfolgreich!");
	Label test_nutzer = new Label("Nutzer Klick erfolgreich!");

	
	VerticalPanel vPanel = new VerticalPanel();
	Button NotizBuch = new Button("Notizbuch");
	Button Nutzer = new Button("Nutzer");
	Button Profil = new Button("Profil");

	CellTree celltree = new CellTree(new NotizBaumModel(), null);


	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);
	
	
	@Override
	public void onModuleLoad() {
		
		
		
			vPanel.add(NotizBuch);
			vPanel.add(Nutzer);
			vPanel.add(Profil);
			NotizBuch.addStyleName("gwt-Green-Button");
			Nutzer.addStyleName("gwt-Green-Button");
			Profil.addStyleName("gwt-Green-Button");
			
			// landscape1Btn.addClickHandler(new LS1ClickHandler());
			
			NotizBuch.addClickHandler(new CellTreeClickHandler());
			Nutzer.addClickHandler(new CellTreeClickHandler_Nutzer());
			
			RootPanel.get("gwtContainer").add(vPanel);
			
			Button button = new Button("Click me");
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Nutzer nutzer = new Nutzer();
					nutzer.setName("Testuser");
					
					administration.anlegenNotizFuer(nutzer, new AsyncCallback<Notiz>() {
						
						@Override
						public void onSuccess(Notiz result) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
			vPanel.add(button);
		
	}
	private class CellTreeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			vPanel_inhalt.clear();
			vPanel_inhalt.addStyleName("vPanel");
			vPanel_inhalt.add(test);
			vPanel_inhalt.add(celltree);
			RootPanel.get("text").add(vPanel_inhalt);

			
			
		}

	
	
		
	}
	private class CellTreeClickHandler_Nutzer implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
				vPanel_inhalt.clear();
				vPanel_inhalt.add(test_nutzer);
			
		}
		
		
	}
	
	
}
	
 



