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
import de.hdm.notefox.shared.report.AlleNotizenDesNutzersReport;
import de.hdm.notefox.shared.report.HTMLReportWriter;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt) 
 * NotefoxReport ist die Entry-Point-Klasse des
 * Projekts <b>notefox</b> für den Report Generator.
 * @author Harun Dalici, Neriman Kocak, Muhammed Simsek

 */
public class NotefoxReport implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;

	Label notizbucherLabel = new Label("Alle Notizbücher aller Nutzer");
	Button notizbucherButton = new Button("Notizbücher");
	Label notizenLabel = new Label("Alle Notizen aller Nutzer");
	Button notizenButton = new Button("Notizen");

	FilterPanel filterPanel = new FilterPanel();

	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel erstPanel = new VerticalPanel();
	VerticalPanel zweitPanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benoetigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	@Override
	public void onModuleLoad() {

		/**
		 * Zunaechst weisen wir dem Report-Generator eine Notizobjekt-Instanz
		 * zu, die für die Darstellung benoetigt wird.
		 */

		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}

		erstPanel.add(filterPanel);
		buttonPanel.add(notizenButton);
		buttonPanel.add(notizbucherButton);
		mainPanel.add(erstPanel);
		mainPanel.add(zweitPanel);
		mainPanel.add(buttonPanel);

		notizenButton.addStyleName("Report-Button");
		notizbucherButton.addStyleName("Report-Button");

		notizenButton.addClickHandler(new notizReportClickHandler());
		notizbucherButton.addClickHandler(new notizbuchReportClickHandler());

		mainPanel.addStyleName("ReportMain");
		RootPanel.get("nav").add(mainPanel);

	}

	/**
	 * Die Reportanwendung besteht aus einem "Navigationsteil" mit der
	 * Schaltflaeche zum Auslesen der Reportgenerierung und einem "Datenteil"
	 * für die HTML-Version des Reports.
	 */

	private class notizbuchReportClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			reportGenerator
					.erstelleAlleNotizbuecherAllerNutzerReport(new erstelleAlleNotizbuecherAllerNutzerReportCallback());

		}

	}

	/**
	 * Die Reportanwendung besteht aus einem "Navigationsteil" mit der
	 * Schaltflaeche zum Auslesen der Reportgenerierung und einem "Datenteil"
	 * fuer die HTML-Version des Reports.
	 */

	private class notizReportClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			reportGenerator.erstelleGefilterteNotizenReport(filterPanel.erstelleFilterobjekt(),
					new erstelleAlleNotizenAllerNutzerReportCallback());

		}

	}
}

/**
 * Diese Nested Class wird als Callback für das Erzeugen des Report benötigt.
 * Anlehnung an das Bankprojekt.
 * @author rathke
 * @version 1.0
 */
class erstelleAlleNotizbuecherAllerNutzerReportCallback implements AsyncCallback<AlleNotizbuecherAllerNutzerReport> {
	@Override
	public void onFailure(Throwable caught) {

		/*
		 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
		 */

		ClientsideSettings.getLogger().severe("Erzeugen des Reports fehlgeschlagen!");

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
 * Diese Nested Class wird als Callback fuer das Erzeugen des Report benoetigt.
 * Anlehnung an das Bankprojekt.
 * @author rathke
 * @version 1.0
 */
class erstelleAlleNotizenAllerNutzerReportCallback implements AsyncCallback<AlleNotizenDesNutzersReport> {
	@Override
	public void onFailure(Throwable caught) {

		/**
		 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
		 */

		ClientsideSettings.getLogger().severe("Erzeugen des Reports fehlgeschlagen!");

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
