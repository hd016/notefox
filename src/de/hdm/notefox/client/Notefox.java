package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.NotizBaumModel;

public class Notefox implements EntryPoint {

	
	HorizontalPanel hPanel = new HorizontalPanel();
	Button loeschen = new Button("Löschen");
	Button editieren = new Button("Editieren");
	Button suchen = new Button("Suchen");
	Button freigeben = new Button("Freigeben");
	Label test = new Label("Klick erfolgreich!");
	
	VerticalPanel vPanel = new VerticalPanel();
	Button NotizBuch = new Button("Notizbuch");
	Button Nutzer = new Button("Nutzer");
	Button Profil = new Button("Profil");

	CellTree celltree = new CellTree(new NotizBaumModel(), null);


	
	
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
			
			RootPanel.get("gwtContainer").add(vPanel);
			
			
		
	}
	private class CellTreeClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("text").add(test);
			RootPanel.get("text").add(celltree);
			
			
		}

	
	
		
	}
	
	
}
	
 



