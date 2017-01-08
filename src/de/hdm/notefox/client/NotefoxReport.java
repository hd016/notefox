package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.FilterPanel;
import de.hdm.notefox.shared.ReportGeneratorAsync;
import de.hdm.notefox.shared.report.AlleNotizbuecherAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizenAllerNutzerReport;
import de.hdm.notefox.shared.report.AlleNotizenDesNutzersReport;
import de.hdm.notefox.shared.report.HTMLReportWriter;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt) Entry-Point-Klasse des
 * Projekts <b>notefox</b>.
 */
public class NotefoxReport implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;
	Label notizbucherLabel = new Label("Alle Notizbücher aller Nutzer");
	Button notizbucherButton = new Button("Report");
	Label notizenLabel = new Label("Alle Notizen aller Nutzer");
	Button notizenButton = new Button("Report");
	FilterPanel filterPanel = new FilterPanel();
	HorizontalPanel main = new HorizontalPanel();
	VerticalPanel vPanelNotizen = new VerticalPanel();
	VerticalPanel vPanelNotizbucher = new VerticalPanel();

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benÃ¶tigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		/*
		 * ZunÃ¤chst weisen wir dem Report-Generator eine Notizobjekt-Instanz zu, die
		 * fÃ¼r die Darstellung der Adressdaten des Kreditinstituts benÃ¶tigt
		 * wird.
		 */
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}

		vPanelNotizen.add(notizenLabel);
		vPanelNotizen.add(filterPanel);
		vPanelNotizen.add(notizenButton);

		vPanelNotizbucher.add(notizbucherLabel);
		vPanelNotizbucher.add(notizbucherButton);

		notizenButton.addStyleName("gwt-Green-Button");
		notizbucherButton.addStyleName("gwt-Green-Button");

		notizenButton.addClickHandler(new notizReportClickHandler());
		notizbucherButton.addClickHandler(new notizbuchReportClickHandler());

		vPanelNotizbucher.addStyleName("ReportLabel");
		vPanelNotizen.addStyleName("ReportLabel");
		

		main.addStyleName("ReportMain");
		//main.add(vPanelNotizbucher);
		main.add(vPanelNotizen);
		RootPanel.get("nav").add(main);

	}

	/*
	 * Die Reportanwendung besteht aus einem "Navigationsteil" mit der
	 * Schaltfläche zum Auslesen der Reportgenerierung und einem "Datenteil"
	 * für die HTML-Version des Reports.
	 */

	private class notizbuchReportClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			reportGenerator
					.erstelleAlleNotizbuecherAllerNutzerReport(new erstelleAlleNotizbuecherAllerNutzerReportCallback());

		}

	}

	/*
	 * Die Reportanwendung besteht aus einem "Navigationsteil" mit der
	 * Schaltfläche zum Auslesen der Reportgenerierung und einem "Datenteil"
	 * für die HTML-Version des Reports.
	 */

	private class notizReportClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			reportGenerator.erstelleGefilterteNotizenReport(filterPanel.erstelleFilterobjekt(), new erstelleAlleNotizenAllerNutzerReportCallback());

		}

	}
}

/**
 * Diese Nested Class wird als Callback fÃ¼r das Erzeugen des
 * AllAccountOfAllCustomersReport benÃ¶tigt.
 * 
 * @author rathke
 * @version 1.0
 */
class erstelleAlleNotizbuecherAllerNutzerReportCallback implements
		AsyncCallback<AlleNotizbuecherAllerNutzerReport> {
	@Override
	public void onFailure(Throwable caught) {
		/*
		 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
		 */
		ClientsideSettings.getLogger().severe(
				"Erzeugen des Reports fehlgeschlagen!");

	}

	@Override
	public void onSuccess(AlleNotizbuecherAllerNutzerReport report) {
		if (report != null) {
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(report);
			RootPanel.get("text").clear();
			RootPanel.get("text").add(new HTML(writer.getReportText()));
		}
	}
}

/**
 * Diese Nested Class wird als Callback fÃ¼r das Erzeugen des
 * AllAccountOfAllCustomersReport benÃ¶tigt.
 * 
 * @author rathke
 * @version 1.0
 */
class erstelleAlleNotizenAllerNutzerReportCallback implements
		AsyncCallback<AlleNotizenDesNutzersReport> {
	@Override
	public void onFailure(Throwable caught) {
		/*
		 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
		 */
		ClientsideSettings.getLogger().severe(
				"Erzeugen des Reports fehlgeschlagen!");

	}

	@Override
	public void onSuccess(AlleNotizenDesNutzersReport report) {
		if (report != null) {
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(report);
			RootPanel.get("text").clear();
			RootPanel.get("text").add(new HTML(writer.getReportText()));
		}
	}
}
