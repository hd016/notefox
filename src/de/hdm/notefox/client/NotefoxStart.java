package de.hdm.notefox.client;

import com.google.apphosting.utils.config.BackendsXml.Entry;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.notefox.client.gui.NotizBaumModel;

public class NotefoxStart extends VerticalPanel { 

	public NotefoxStart() {
		CellTree celltree = new CellTree(new NotizBaumModel(), null);
		add(celltree);	
	}
	
}
