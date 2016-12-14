package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

//import de.hdm.notefox.client.gui.BerechtigungBaumModel;
import de.hdm.notefox.client.gui.FaelligkeitenEditorPanel;
import de.hdm.notefox.client.gui.NotizBaumModel;
import de.hdm.notefox.client.gui.NotizEditorPanel;
import de.hdm.notefox.client.gui.NotizbuchEditorPanel;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.LoginInfo;
import de.hdm.notefox.shared.LoginService;
import de.hdm.notefox.shared.LoginServiceAsync;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;

public class Notefox implements EntryPoint {

	// NotizobjektAdministrationAsync notizVerwaltung =
	// ClientsideSettings.getNotizobjektAdministrationAsync();

	HorizontalPanel hPanel = new HorizontalPanel();
	VerticalPanel vPanel_inhalt = new VerticalPanel();
	Button loeschen = new Button("Löschen");
	Button editieren = new Button("Editieren");
	Button suchen = new Button("Suchen");
	Button freigeben = new Button("Freigeben");
	Label meineNotizBuecher = new Label("Meine Notizbücher");
	// Label meineBerechtigungen = new Label("Meine Berechtigungen");
	Label titel = new Label("Titel");

	HTML br = new HTML("<br>");
	
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel vBrowser = new VerticalPanel();

	Button NotizBuch = new Button("Notizbuch");
	Button Nutzer = new Button("Nutzer");
	Button Profil = new Button("Profil");

	Button neuesNotizBuch = new Button("+ Notizbuch");
	Button neuesNotiz = new Button("Erstelle Notiz");
	
	//NotizObjektTree net = new NotizObjektTree();
	NotizbuchEditorPanel notizbucheditorpanel = new NotizbuchEditorPanel();
	NotizEditorPanel notizeditorpanel = new NotizEditorPanel();
	FaelligkeitenEditorPanel faelligkeiten = new FaelligkeitenEditorPanel();
	Berechtigung berechtigung;

	CellTree celltree = new CellTree(new NotizBaumModel(notizeditorpanel), null);
	//CellTree celltree2 = new CellTree(new BerechtigungBaumModel(berechtigung), null);

	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);

	@Override
	public void onModuleLoad() {

	/*	LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) {
	      }

	      public void onSuccess(LoginInfo loginInfo) {
	        if(loginInfo.isLoggedIn()) {
	        	onModuleLoadLoggedIn();
	        } else {
	        	RootPanel.get("gwtContainer").clear();
	        	RootPanel.get("gwtContainer").add(new LoginPanel(loginInfo));
	        }
	      }
	    });

	*/
	
		onModuleLoadLoggedIn();
	}
	
	private void onModuleLoadLoggedIn(){
		
		
		HorizontalPanel hPanelNotizNotizbuch = new HorizontalPanel();
		VerticalPanel vPanelRight = new VerticalPanel();
		hPanelNotizNotizbuch.add(notizeditorpanel);
		hPanelNotizNotizbuch.add(vPanelRight);
		
		vPanelRight.add(notizbucheditorpanel);
		vPanelRight.add(faelligkeiten);
		vPanelRight.addStyleName("vPanelRight");
		
		vPanel.add(meineNotizBuecher);
		vPanel.add(celltree);
		//vPanel.add(NotizBuch);
		//vPanel.add(Nutzer);
		//vPanel.add(Profil);
		vPanel.add(br);
		vPanel.add(br);
		vPanel.add(br);
		//vPanel.add(meineBerechtigungen);
		// vPanel.add(celltree2);
		NotizBuch.addStyleName("gwt-Green-Button");
		Nutzer.addStyleName("gwt-Green-Button");
		Profil.addStyleName("gwt-Green-Button");

		// landscape1Btn.addClickHandler(new LS1ClickHandler());

		NotizBuch.addClickHandler(new CellTreeClickHandler());
		Nutzer.addClickHandler(new CellTreeClickHandler_Nutzer());

		RootPanel.get("gwtContainer").clear();
		RootPanel.get("gwtContainer").add(vPanel);
		RootPanel.get("text").add(hPanelNotizNotizbuch);

		NotizBuch.addStyleName("gwt-Green-Button");
		Nutzer.addStyleName("gwt-Green-Button");
		Profil.addStyleName("gwt-Green-Button");
		neuesNotiz.addStyleName("gwt-Green-Button");
		neuesNotizBuch.addStyleName("gwt-Green-Button");

		// landscape1Btn.addClickHandler(new LS1ClickHandler());

		NotizBuch.addClickHandler(new CellTreeClickHandler());
		Nutzer.addClickHandler(new CellTreeClickHandler_Nutzer());

	
	}

	private class CellTreeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vPanel_inhalt.clear();
			vPanel_inhalt.addStyleName("vPanel");
			vPanel_inhalt.add(celltree);

			neuesNotiz.addClickHandler(new NeuesNotizClickHandler());
			vPanel_inhalt.add(neuesNotiz);
			vPanel_inhalt.add(neuesNotizBuch);
			
			neuesNotizBuch.addClickHandler(new NeuesNotizBuchClickHandler());
			RootPanel.get("text").add(vPanel_inhalt);
			
			

		}

	}

	private class CellTreeClickHandler_Nutzer implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vPanel_inhalt.clear();
		//	vPanel_inhalt.add(celltree2);

		}

	}

	private class NeuesNotizClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vPanel_inhalt.clear();
			HorizontalPanel hPanelNotizNotizbuch = new HorizontalPanel();
			hPanelNotizNotizbuch.add(notizeditorpanel);
			hPanelNotizNotizbuch.add(notizbucheditorpanel);
			

			vPanel_inhalt.add(titel);
			vPanel_inhalt.add(hPanelNotizNotizbuch);
			

		}

	}
		private class NeuesNotizBuchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vPanel_inhalt.clear();
			vPanel_inhalt.add(notizbucheditorpanel);
			
		}
		
	}

	
}