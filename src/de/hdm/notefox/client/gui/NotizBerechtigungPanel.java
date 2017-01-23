package de.hdm.notefox.client.gui;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;
import java.util.List;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

/**
 * Die Klasse NotizBerechtigungPanel beinhaltet einen CellTable, für die
 * Darstellung der Berechtigungen, die erteilt wurden. Der CellTable ist im
 * Objekt der FreigebenDialogbox, platziert.
 * 
 * @author Neriman Kocak und Harun Dalici
 */
public class NotizBerechtigungPanel extends VerticalPanel {

	private final class BerechtigungAsyncCallback implements AsyncCallback<List<Berechtigung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler");
		}

		@Override
		public void onSuccess(List<Berechtigung> result) {
			table.setRowCount(result.size(), true);
			table.setRowData(0, result);
		}
	}

	NotizobjektAdministrationAsync notizobjektadministration = ClientsideSettings.getNotizobjektVerwaltung();

	HTML uberschrift = new HTML("<h3>Berechtigungen</h3>");

	/**
	 * Instanziierung des Notizobjekts und des CellTables
	 */

	private Notizobjekt notizobjekt;

	private CellTable<Berechtigung> table;

	public NotizBerechtigungPanel(Notizobjekt notizobjekt) {
		this.notizobjekt = notizobjekt;
		table = new CellTable<Berechtigung>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		/**
		 * Emailspalte für die Auflistung der Nutzer
		 */

		TextColumn<Berechtigung> emailColumn = new TextColumn<Berechtigung>() {
			@Override
			public String getValue(Berechtigung object) {
				return object.getBerechtigter().getEmail();
			}
		};
		table.addColumn(emailColumn, "Email");

		/**
		 * Berechtigungspalte für die Auflistung der Berechtigungsarten
		 */

		TextColumn<Berechtigung> berechtigungsartColumn = new TextColumn<Berechtigung>() {
			@Override
			public String getValue(Berechtigung object) {
				return object.getBerechtigungsart().name();
			}
		};
		table.addColumn(berechtigungsartColumn, "Berechtigungsart");

		/**
		 * Löschenspalte für das Entfernen der Berechtigungen
		 */

		Column<Berechtigung, String> loschenColumn = new Column<Berechtigung, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Berechtigung object) {
				return "X";
			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Berechtigung object, NativeEvent event) {

				super.onBrowserEvent(context, elem, object, event);
				if (CLICK.equals(event.getType())) {
					if (Window.confirm("Möchten Sie es wirklich löschen?")) {
						notizobjektadministration.loeschenBerechtigung(object, new BerechtigungLoeschenAsnyCallback());
					}
				}
			}
		};
		table.addColumn(loschenColumn, "Löschen");

		/**
		 * Es wird ein Cursor.Pointer gesetzt. Dadurch hat man eine Verbesserung
		 * bei der Auswahl der Spalten Spalten
		 */

		table.getElement().getStyle().setCursor(Cursor.POINTER);

		/**
		 * SelectionModel für die Rückgabe der ausgewählten Nutzer/Email, wird
		 * definiert
		 */

		final SingleSelectionModel<Berechtigung> selectionModel = new SingleSelectionModel<Berechtigung>();
		table.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Berechtigung selected = selectionModel.getSelectedObject();
				if (selected != null) {
					Window.alert("Sie haben folgende User ausgewählt " + selected.getBerechtigter().getEmail());

				}
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(1);
		panel.setWidth("400");
		panel.add(table);
		add(panel);

		refresh();
	}

	/**
	 * Die Methode refresh() wird für das Neuladen des CellTables nach Anlegen
	 * einer Berechtigung, genutzt
	 */

	public void refresh() {
		if (notizobjekt instanceof Notiz) {
			Notiz notiz = (Notiz) notizobjekt;
			notizobjektadministration.nachAllenBerechtigungenDerNotizSuchen(notiz, new BerechtigungAsyncCallback());

		} else if (notizobjekt instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) notizobjekt;
			notizobjektadministration.nachAllenBerechtigungenDesNotizbuchesSuchen(notizbuch,
					new BerechtigungAsyncCallback());
		}
	}

	private class BerechtigungLoeschenAsnyCallback implements AsyncCallback<Berechtigung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Berechtigung wurde nicht angelegt.");
		}

		@Override
		public void onSuccess(Berechtigung result) {
			refresh();
		}
	}
}