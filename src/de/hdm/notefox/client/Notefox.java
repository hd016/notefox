package de.hdm.notefox.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.client.gui.NotizBaumModel;
//import de.hdm.notefox.shared.LoginInfo;
//import de.hdm.notefox.shared.LoginServiceAsync;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

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
	
