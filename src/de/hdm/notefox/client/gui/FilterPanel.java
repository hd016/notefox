package de.hdm.notefox.client.gui;

import java.util.List;



import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.shared.Filterobjekt;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.ReportGeneratorAsync;

/**
 * Diese Klasse stellt für die Report GUI die Filter Objekte zur Verfügung. Die Instanz dieser
 * Klasse wird in der Klasse NotefoxReport instanziiert.
 * 
 * @author Harun Dalici
 *
 */

public class FilterPanel extends HorizontalPanel {

	/**
	 * Die Klasse Filterpanel ist für das Filtern von Buttons und Datepicker der
	 * Notizobjekte
	 * 
	 * @author Neriman Kocak und Harun Dalici
	 */

	ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();

	private TextBox titelTextBox;
	private DateBox erstellDatumVonDateBox;
	private DateBox erstellDatumBisDateBox;
	private DateBox modifikationsVonDateBox;
	private DateBox modifikationsBisDateBox;
	private DateBox faelligkeitsDatumVonDateBox;
	private DateBox faelligkeitsDatumBisDateBox;
	private ListBox nutzerListBox;
	private CheckBox lesenCheckBox;
	private CheckBox schreibenCheckBox;
	private CheckBox loeschenCheckBox;

	public FilterPanel() {
		Label titelLabel = new Label("Titel");
		titelTextBox = new TextBox();
		Label erstellDatumVonLabel = new Label("Erstelldatum von");
		erstellDatumVonDateBox = new DateBox();
		Label erstellDatumBisLabel = new Label("bis");
		erstellDatumBisDateBox = new DateBox();
		Label modifikationsVonLabel = new Label("Modifikationsdatum von");
		modifikationsVonDateBox = new DateBox();
		Label modifikationsDatumBisLabel = new Label("bis");
		modifikationsBisDateBox = new DateBox();
		Label faelligkeitsDatumVonLabel = new Label("Fälligkeitsdatum von");
		faelligkeitsDatumVonDateBox = new DateBox();
		Label faelligkeitsDatumBisLabel = new Label("bis");
		faelligkeitsDatumBisDateBox = new DateBox();
		Label nutzerLabel = new Label("Nutzer");
		Label berechtigungLabel = new Label("Berechtigung");
		Label lesenBerechtigungLabel = new Label("Leseberechtigung");
		Label schreibenBerechtitgungLabel = new Label("Schreibberechtigung");
		Label loeschenBerechtigungLabel = new Label("Löschberechtigung");
		nutzerListBox = new ListBox();
		lesenCheckBox = new CheckBox();
		schreibenCheckBox = new CheckBox();
		loeschenCheckBox = new CheckBox();

		reportGenerator.nachAllenNutzernSuchen(new AsyncCallback<List<Nutzer>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler");
			}

			@Override
			public void onSuccess(List<Nutzer> result) {
				nutzerListBox.addItem("", "");
				for (Nutzer nutzer : result) {
					nutzerListBox.addItem(nutzer.getEmail(), nutzer.getNutzerId() + "");
				}
			}
		});

		/*
		 * Instanziieren und deklarieren der VerticalPanels
		 */

		VerticalPanel spalte1 = new VerticalPanel();
		VerticalPanel spalte2 = new VerticalPanel();
		VerticalPanel spalte3 = new VerticalPanel();
		VerticalPanel spalte4 = new VerticalPanel();
		VerticalPanel spalte5 = new VerticalPanel();
		VerticalPanel spalte6 = new VerticalPanel();
		add(spalte1);
		add(spalte2);
		add(spalte3);
		add(spalte4);
		add(spalte5);
		add(spalte6);

		spalte1.add(titelLabel);
		spalte1.add(titelTextBox);

		spalte2.add(erstellDatumVonLabel);
		spalte2.add(erstellDatumVonDateBox);

		spalte2.add(erstellDatumBisLabel);
		spalte2.add(erstellDatumBisDateBox);

		spalte3.add(modifikationsVonLabel);
		spalte3.add(modifikationsVonDateBox);

		spalte3.add(modifikationsDatumBisLabel);
		spalte3.add(modifikationsBisDateBox);

		spalte4.add(faelligkeitsDatumVonLabel);
		spalte4.add(faelligkeitsDatumVonDateBox);

		spalte4.add(faelligkeitsDatumBisLabel);
		spalte4.add(faelligkeitsDatumBisDateBox);

		spalte5.add(nutzerLabel);
		spalte5.add(nutzerListBox);

		spalte6.add(berechtigungLabel);

		spalte6.add(lesenBerechtigungLabel);
		spalte6.add(lesenCheckBox);

		spalte6.add(schreibenBerechtitgungLabel);
		spalte6.add(schreibenCheckBox);

		spalte6.add(loeschenBerechtigungLabel);
		spalte6.add(loeschenCheckBox);

	}

	/**
	 * Instanziieren der Filterobjekte
	 * 
	 * @return filterobjekt
	 */

	public Filterobjekt erstelleFilterobjekt() {
		Filterobjekt filterobjekt = new Filterobjekt();
		filterobjekt.setTitel(titelTextBox.getValue());
		filterobjekt.setNutzer(nutzerListBox.getSelectedItemText());
		filterobjekt.setErstellDatumVon(erstellDatumVonDateBox.getValue());
		filterobjekt.setErstellDatumBis(erstellDatumBisDateBox.getValue());
		filterobjekt.setFaelligkeitsDatumVon(faelligkeitsDatumVonDateBox.getValue());
		filterobjekt.setFaelligkeitsDatumBis(faelligkeitsDatumBisDateBox.getValue());
		filterobjekt.setModifikationsDatumVon(modifikationsVonDateBox.getValue());
		filterobjekt.setModifikationsDatumBis(modifikationsBisDateBox.getValue());
		filterobjekt.setLeseBerechtigungen(lesenCheckBox.getValue());
		filterobjekt.setSchreibBerechtigungen(schreibenCheckBox.getValue());
		filterobjekt.setLoeschenBerechtigungen(loeschenCheckBox.getValue());

		return filterobjekt;
	}
}
