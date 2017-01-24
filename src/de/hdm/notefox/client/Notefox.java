package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.FooterPanel;
import de.hdm.notefox.client.gui.Impressum;
import de.hdm.notefox.client.gui.NotizBaumModel;
import de.hdm.notefox.client.gui.NotizEditorPanel;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.LoginInfo;
import de.hdm.notefox.shared.LoginService;
import de.hdm.notefox.shared.LoginServiceAsync;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

/**
 * Entry-Point-Klasse des Projekts <b>Notefox</b>.
 * 
 * @author Harun Dalici, Neriman Kocak, Muhammed Simsek
 */

public class Notefox implements EntryPoint {

	/**
	 * Es werden Panels erzeugt und jeweils zugewiesen.
	 */

	HorizontalPanel hPanel = new HorizontalPanel();
	VerticalPanel vPanel_inhalt = new VerticalPanel();
	HTML meineNotizBuecher = new HTML("<h3>Meine Notizbücher</h3>");

	HTML br = new HTML("<br>");

	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel vBrowser = new VerticalPanel();

	Panel notizeditorpanel = new VerticalPanel();
	Berechtigung berechtigung;

	CellTree celltree;

	FooterPanel footerPanel = new FooterPanel();

	LoginInfo loginInfo;

	/**
	 * Anchor Widgets für den Header Bereich
	 */

	public final Anchor logoutLink = new Anchor("Abmelden");

	public final Anchor impressumLink = new Anchor("Impressum");

	public final Anchor startseiteLink = new Anchor("Startseite");

	Impressum impressum = new Impressum();

	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		schlieseInhalt();

		LoginServiceAsync loginService = GWT.create(LoginService.class);

		String urlParameter = Window.Location.getParameter("url");

		loginService.login(GWT.getHostPageBaseURL() + (urlParameter != null ? "?url=" + urlParameter : ""),
				new AsyncCallback<LoginInfo>() {

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

	/**
	 * Die weiterführende Methode der onModuleLoad() Methode nach dem
	 * erfolgreichen Einloggen.
	 */
	private void onModuleLoadLoggedIn() {

		notizeditorpanel = new NotizEditorPanel(this, loginInfo);
		schlieseInhalt();

		/**
		 * Überprüfung der Parameter über die Übernahme eines Fremdinhalts auf
		 * einer Webseite.
		 */

		String urlParameter = Window.Location.getParameter("url");
		if (urlParameter != null) {
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

		ersetzeBaum(null);

		/**
		 * Anzeigen des Nutzernamens nach dem Einloggen. Der Nutzername besteht
		 * aus einem String vor dem '@'- Zeichen bei der Anmeldungsemail.
		 */
		HTML welcomeLabel = new HTML();
		welcomeLabel.setHTML("Herzlich Willkommen: " + "<b>" + loginInfo.getNutzer().getEmail().split("@")[0] + "</b>"
				+ " auf NoteFox!");

		logoutLink.addStyleName("Abmelden-Link");
		impressumLink.addStyleName("Impressum-Link");
		startseiteLink.addStyleName("Startseite-Link");

		logoutLink.setHref(loginInfo.getLogoutUrl());

		impressumLink.addClickHandler(new ImpressumClickHandler());

		startseiteLink.setHref(GWT.getHostPageBaseURL());

		/**
		 * Anchor Zuweisung zu den Panels
		 */

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.add(welcomeLabel);
		headerPanel.add(startseiteLink);
		headerPanel.add(impressumLink);
		headerPanel.add(logoutLink);

		headerPanel.addStyleName("headerPanel");
		RootPanel.get("head").add(headerPanel);

		HorizontalPanel hPanelNotizNotizbuch = new HorizontalPanel();
		VerticalPanel vPanelRight = new VerticalPanel();
		hPanelNotizNotizbuch.add(notizeditorpanel);
		hPanelNotizNotizbuch.add(vPanelRight);

		RootPanel.get("gwtContainer").clear();
		RootPanel.get("gwtContainer").add(vPanel);
		RootPanel.get("text").add(hPanelNotizNotizbuch);

		HTML uberschrift = new HTML("<h3>Mein Profil</h3>");
		Label labelName = new Label();
		Label labelEmail = new Label();

		VerticalPanel vPanel = new VerticalPanel();

		Anchor profilLoeschen = new Anchor("Mein Profil löschen");
		labelName.setText("Name: " + loginInfo.getNutzer().getEmail().split("@")[0]);
		labelEmail.setText("Email: " + loginInfo.getNutzer().getEmail());

		uberschrift.addStyleName("meinProfil-labels");
		labelName.addStyleName("meinProfil-labels");
		labelEmail.addStyleName("meinProfilemail-labels");
		profilLoeschen.addStyleName("meinProfil-labels");
		vPanel.add(uberschrift);
		vPanel.add(labelName);
		vPanel.add(labelEmail);
		vPanel.add(profilLoeschen);
		RootPanel.get("nav").add(vPanel);

		profilLoeschen.addClickHandler(new NutzerLoeschenClickHandler());

	}

	/**
	 * Zeigt das Panel im Content Bereich an
	 */
	public void zeigeInhalt() {
		notizeditorpanel.setVisible(true);

	}

	/**
	 * Schließt den Content Panel
	 */
	public void schlieseInhalt() {
		notizeditorpanel.setVisible(false);
	}

	/**
	 * Legt die neue Notiz an.
	 * 
	 * @param notizbuch
	 */
	public void neueNotiz(final Notizbuch notizbuch) {
		Notiz notiz = new Notiz();
		notiz.setNotizbuch(notizbuch);
		notiz.setEigentuemer(loginInfo.getNutzer());

		zeigeNotiz(notiz);
	}

	/**
	 * Legt ein neues Notizbuch an.
	 */
	public void neuesNotizbuch() {
		Notizbuch notizbuch = new Notizbuch();
		notizbuch.setEigentuemer(loginInfo.getNutzer());

		zeigeNotizbuch(notizbuch);
	}

	/**
	 * Zeigt die jeweilige Notiz an.
	 * 
	 * @param notiz
	 */
	public void zeigeNotiz(Notiz notiz) {
		zeigeInhalt();
		((NotizEditorPanel) notizeditorpanel).setNotizobjekt(notiz);
	}

	/**
	 * Zeigt das jeweilige Notizbuch an.
	 * 
	 * @param notizbuch
	 */
	public void zeigeNotizbuch(Notizbuch notizbuch) {
		zeigeInhalt();
		((NotizEditorPanel) notizeditorpanel).setNotizobjekt(notizbuch);
	}

	/**
	 * Gibt die CellTree Komponente zurück.
	 * 
	 * @return
	 */
	public CellTree getCelltree() {
		return celltree;
	}

	/**
	 * Erneuert den CellTree.
	 */
	public void ersetzeBaum(Notizbuch notizbuch) {
		NotizBaumModel viewModel = new NotizBaumModel(this, notizbuch);
		celltree = new CellTree(viewModel, null);

		vPanel.clear();
		vPanel.add(meineNotizBuecher);
		vPanel.add(celltree);
		vPanel.add(br);
		vPanel.add(br);
		vPanel.add(br);
	}

	/**
	 * Aktuallisiert den aktuellen Zweig von dem CellTree.
	 */
	public void baumNeuOeffnen() {
		TreeNode rootTreeNode = celltree.getRootTreeNode();
		for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
			if (rootTreeNode.isChildOpen(i)) {
				/*
				 * Nach einer neuen Notiz oder neues Notizbuch wird die CellTree
				 * zuerst geschlossen und danach wieder geöffnet. Dies dient
				 * dann als eine sog. refresh() Methode.
				 * 
				 */
				rootTreeNode.setChildOpen(i, false);
				rootTreeNode.setChildOpen(i, true);
			}
		}
	}

	/**
	 * ClickHandler für das Anzeigen der Impressum.
	 */
	private class ImpressumClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("text").clear();
			RootPanel.get("gwtContainer").clear();
			RootPanel.get("text").add(impressum);

		}

	}

	/**
	 * ClickHandler für die Möglichkeit um ein Nutzer Account zu löschen.
	 */
	private class NutzerLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			boolean confirm = Window.confirm("Sollte Ihr Account unwiderruflich gelöscht werden?");
			if (confirm) {
				administration.loeschenNutzer(loginInfo.getNutzer(), new NutzerLoeschenAsyncCallback());
			} else {
				Window.alert("Ihr Account wurde nicht gelöscht.");
			}

		}

	}

	/**
	 * Das AsnycCallback für den NutzerLoeschenClickHandler.
	 *
	 */
	private class NutzerLoeschenAsyncCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Ein Fehler ist aufgetreten!");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Auf Wiedersehen!");
			Window.Location.replace(loginInfo.getLogoutUrl());
		}

	}

}
