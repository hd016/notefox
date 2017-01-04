package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.FaelligkeitenEditorPanel;
import de.hdm.notefox.client.gui.FooterPanel;
import de.hdm.notefox.client.gui.NotizBaumModel;
import de.hdm.notefox.client.gui.NotizBerechtigungPanel;
import de.hdm.notefox.client.gui.NotizEditorPanel;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.LoginInfo;
import de.hdm.notefox.shared.LoginService;
import de.hdm.notefox.shared.LoginServiceAsync;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class Notefox implements EntryPoint {

	HorizontalPanel hPanel = new HorizontalPanel();
	VerticalPanel vPanel_inhalt = new VerticalPanel();
	Label meineNotizBuecher = new Label("Meine Notizbücher");

	HTML br = new HTML("<br>");

	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel vBrowser = new VerticalPanel();

	NotizEditorPanel notizeditorpanel = new NotizEditorPanel(this);
	Berechtigung berechtigung;
	
	CellTree celltree;

	FooterPanel footerPanel = new FooterPanel();

	LoginInfo loginInfo;
	
	public final Anchor logoutLink = new Anchor("Abmelden");


	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);

	@Override
	public void onModuleLoad() {

		schlieseInhalt();

		LoginServiceAsync loginService = GWT.create(LoginService.class);

		String urlParameter = Window.Location.getParameter("url");
		
		loginService.login(GWT.getHostPageBaseURL() + (urlParameter != null ? "?url=" + urlParameter : ""), new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo loginInfo) {
				Notefox.this.loginInfo = loginInfo;
				if (loginInfo.isLoggedIn()) {
					onModuleLoadLoggedIn();
				} else {
					RootPanel.get("gwtContainer").clear();
					RootPanel.get("gwtContainer").add(new LoginPanel(loginInfo));
				}
			}
		});

	}

	private void onModuleLoadLoggedIn() {

		String urlParameter = Window.Location.getParameter("url");
		if(urlParameter != null){
			administration.anlegenNotiz(urlParameter, new AsyncCallback<Notiz>() {
				
				@Override
				public void onSuccess(Notiz result) {
					zeigeNotiz(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("FEHLER! tut mir leid");
				}
			});
		}
		
		ersetzeBaum();
		Label welcomeLabel = new Label();
		welcomeLabel.setText("Herzlich Willkommen: " +  loginInfo.getNutzer().getEmail().split("@")[0] + " auf NoteFox!"); 
		
		logoutLink.addStyleName("Abmelden");
		logoutLink.setHref(loginInfo.getLogoutUrl());

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(welcomeLabel);
		headerPanel.add(logoutLink);
		
		RootPanel.get("head").add(headerPanel);

		
		HorizontalPanel hPanelNotizNotizbuch = new HorizontalPanel();
		VerticalPanel vPanelRight = new VerticalPanel();
		hPanelNotizNotizbuch.add(notizeditorpanel);
		hPanelNotizNotizbuch.add(vPanelRight);

		RootPanel.get("gwtContainer").clear();
		RootPanel.get("gwtContainer").add(vPanel);
		RootPanel.get("text").add(hPanelNotizNotizbuch);

	}

	public void zeigeInhalt(Panel panel) {
		notizeditorpanel.setVisible(false);
		if (panel != null) {
			panel.setVisible(true);
		}
	}

	public void schlieseInhalt() {
		zeigeInhalt(null);
	}

	public void neueNotiz(Notizbuch notizbuch) {
		administration.anlegenNotiz(notizbuch, new AsyncCallback<Notiz>() {

			@Override
			public void onSuccess(Notiz result) {
				zeigeNotiz(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public void neuesNotizbuch() {
		administration.anlegenNotizbuecherFuer(loginInfo.getNutzer(), new AsyncCallback<Notizbuch>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Notizbuch result) {
				zeigeNotizbuch(result);
				ersetzeBaum();
			}
		});

	}

	public void zeigeNotiz(Notiz notiz) {
		zeigeInhalt(notizeditorpanel);
		notizeditorpanel.setNotizobjekt(notiz);
	}

	public void zeigeNotizbuch(Notizbuch notizbuch) {
		zeigeInhalt(notizeditorpanel);
		notizeditorpanel.setNotizobjekt(notizbuch);
	}

	public void ersetzeBaum() {
		celltree = new CellTree(new NotizBaumModel(this, loginInfo.getNutzer()), null);

		vPanel.clear();
		vPanel.add(meineNotizBuecher);
		vPanel.add(celltree);
		vPanel.add(br);
		vPanel.add(br);
		vPanel.add(br);
	}

}
