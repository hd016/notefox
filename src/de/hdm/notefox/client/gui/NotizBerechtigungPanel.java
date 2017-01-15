package de.hdm.notefox.client.gui;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.Cell.Context;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.notefox.client.ClientsideSettings;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;
import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;

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

	private Notizobjekt notizobjekt;

	private CellTable<Berechtigung> table;

	public NotizBerechtigungPanel(Notizobjekt notizobjekt) {
		this.notizobjekt = notizobjekt;
		table = new CellTable<Berechtigung>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// add email spalte
		TextColumn<Berechtigung> emailColumn = new TextColumn<Berechtigung>() {
			@Override
			public String getValue(Berechtigung object) {
				return object.getBerechtigter().getEmail();
			}
		};
		table.addColumn(emailColumn, "Email");

		// Add berechtigung spalte
		TextColumn<Berechtigung> berechtigungsartColumn = new TextColumn<Berechtigung>() {
			@Override
			public String getValue(Berechtigung object) {
				return object.getBerechtigungsart().name();
			}
		};
		table.addColumn(berechtigungsartColumn, "Berechtigungsart");

		// Add loschen spalte
		Column<Berechtigung, String> loschenColumn = new Column<Berechtigung, String>(new ClickableTextCell()) {

			@Override
			public String getValue(Berechtigung object) {
				return "X";
			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Berechtigung object, NativeEvent event) {

				super.onBrowserEvent(context, elem, object, event);
				if (CLICK.equals(event.getType())) {
					if (Window.confirm("Möchten Sie es wirklich löschen?")){
					notizobjektadministration.loeschenBerechtigung(object, new BerechtigungLoeschenAsnyCallback());
				}
			}
			}
		};
		table.addColumn(loschenColumn, "Löschen");
		table.getElement().getStyle().setCursor(Cursor.POINTER); 
		
		// Add a selection model für user auswahl
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

		if (notizobjekt instanceof Notiz) {
			Notiz notiz = (Notiz) notizobjekt;
			notizobjektadministration.nachAllenBerechtigungenDerNotizSuchen(notiz,
					new BerechtigungAsyncCallback());

		} else if (notizobjekt instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) notizobjekt;
			notizobjektadministration.nachAllenBerechtigungenDesNotizbuchesSuchen(notizbuch,
					new BerechtigungAsyncCallback());
		}

		VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(1);
		panel.setWidth("400");
		panel.add(table);
		add(panel);
		
		
		
	}
	private class BerechtigungLoeschenAsnyCallback implements AsyncCallback<Berechtigung>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Berechtigung result) {
				if (notizobjekt instanceof Notiz) {
					Notiz notiz = (Notiz) notizobjekt;
					notizobjektadministration.nachAllenBerechtigungenDerNotizSuchen(notiz,
							new BerechtigungAsyncCallback());

				} else if (notizobjekt instanceof Notizbuch) {
					Notizbuch notizbuch = (Notizbuch) notizobjekt;
					notizobjektadministration.nachAllenBerechtigungenDesNotizbuchesSuchen(notizbuch,
							new BerechtigungAsyncCallback());
				}
			}
			
			
		
	}
}