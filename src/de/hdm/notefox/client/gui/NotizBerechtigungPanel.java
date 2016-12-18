package de.hdm.notefox.client.gui;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class NotizBerechtigungPanel extends CellTable {

	HTML uberschrift = new HTML("<h3>Berechtigungen</h3>");

	private static class Berechtigungen {
		private final String email;
		private final String berechtigungsart;
		private final String loeschen;

		public Berechtigungen(String email, String berechtigungsart, String loeschen) {
			this.email = email;
			this.berechtigungsart = berechtigungsart;
			this.loeschen = loeschen;
		}
	}

	/**
	 * Dummy Data set
	 */

	private static final List<Berechtigungen> Berechtigung = Arrays.asList(
			new Berechtigungen("harundalici@gmail.com", "Editieren", "X"),
			new Berechtigungen("mansurcuma@gmail.com", "Löschen", "X"),
			new Berechtigungen("neriko@gmail.com", "Löschen", "X"));
	

	public NotizBerechtigungPanel() {
		CellTable<Berechtigungen> table = new CellTable<Berechtigungen>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// add email spalte
		TextColumn<Berechtigungen> emailColumn = new TextColumn<Berechtigungen>() {
			@Override
			public String getValue(Berechtigungen object) {
				return object.email;
			}
		};
		table.addColumn(emailColumn, "Email");

		// Add berechtigung spalte 
		TextColumn<Berechtigungen> berechtigungsartColumn = new TextColumn<Berechtigungen>() {
			@Override
			public String getValue(Berechtigungen object) {
				return object.berechtigungsart;
			}
		};
		table.addColumn(berechtigungsartColumn, "Berechtigungsart");

		// Add loschen spalte
		TextColumn<Berechtigungen> loschenColumn = new TextColumn<Berechtigungen>() {
			@Override
			public String getValue(Berechtigungen object) {
				return object.loeschen;
			}
		};
		table.addColumn(loschenColumn, "Löschen");

	   // Add a selection model für user auswahl
	      final SingleSelectionModel<Berechtigungen> selectionModel = new SingleSelectionModel<Berechtigungen>();
	      table.setSelectionModel(selectionModel);
	      selectionModel.addSelectionChangeHandler(
	    	      new SelectionChangeEvent.Handler() {
	    	          public void onSelectionChange(SelectionChangeEvent event) {
	    	        	  Berechtigungen selected = selectionModel.getSelectedObject();
	    	        	  if (selected != null) {
	    	                  Window.alert("Sie haben folgende User ausgewählt " + selected.email);

	    	        	  }
	    	          }
	    	      });
	    	        		  
	      table.setRowCount(Berechtigung.size(), true);
	      table.setRowData(0, Berechtigung);

	      VerticalPanel panel = new VerticalPanel();
	      panel.setBorderWidth(1);	    
	      panel.setWidth("400");
	      panel.add(table);		
	
	}
	    	      
}