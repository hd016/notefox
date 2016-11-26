package de.hdm.notefox.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.
HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class Notefox implements EntryPoint {

	
	Button Test = new Button("Mein Profil");
	VerticalPanel vPanel = new VerticalPanel();

	
	public void onModuleLoad() {

		Test.addStyleName("gwt-Green-Button");
		vPanel.add(Test);
		RootPanel.get("gwtContainer").add(vPanel);
	}


 
}