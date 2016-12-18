package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.FaelligkeitenEditorPanel;
import de.hdm.notefox.client.gui.FooterPanel;
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

	HorizontalPanel hPanel = new HorizontalPanel();
	VerticalPanel vPanel_inhalt = new VerticalPanel();
	Label meineNotizBuecher = new Label("Meine Notizbücher");

	HTML br = new HTML("<br>");

	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel vBrowser = new VerticalPanel();

	NotizbuchEditorPanel notizbucheditorpanel = new NotizbuchEditorPanel();
	NotizEditorPanel notizeditorpanel = new NotizEditorPanel();
	FaelligkeitenEditorPanel faelligkeiten = new FaelligkeitenEditorPanel();
	Berechtigung berechtigung;

	CellTree celltree;

	FooterPanel footerPanel = new FooterPanel();

	NotizobjektAdministrationAsync administration = GWT.create(NotizobjektAdministration.class);

	@Override
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);

		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo loginInfo) {
				if (loginInfo.isLoggedIn()) {
					onModuleLoadLoggedIn(loginInfo);
				} else {
					RootPanel.get("gwtContainer").clear();
					RootPanel.get("gwtContainer").add(new LoginPanel(loginInfo));
				}
			}
		});

	}

	private void onModuleLoadLoggedIn(LoginInfo loginInfo) {

		celltree = new CellTree(new NotizBaumModel(notizeditorpanel, loginInfo.getNutzer()), null);

		HorizontalPanel hPanelNotizNotizbuch = new HorizontalPanel();
		VerticalPanel vPanelRight = new VerticalPanel();
		hPanelNotizNotizbuch.add(notizeditorpanel);
		hPanelNotizNotizbuch.add(vPanelRight);

		vPanelRight.add(notizbucheditorpanel);
		vPanelRight.add(faelligkeiten);
		vPanelRight.addStyleName("vPanelRight");

		vPanel.add(meineNotizBuecher);
		vPanel.add(celltree);
		vPanel.add(br);
		vPanel.add(br);
		vPanel.add(br);

		RootPanel.get("gwtContainer").clear();
		RootPanel.get("gwtContainer").add(vPanel);
		RootPanel.get("text").add(hPanelNotizNotizbuch);

	}

}