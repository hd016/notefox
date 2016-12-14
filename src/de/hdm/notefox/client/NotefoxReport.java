package de.hdm.notefox.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NotefoxReport implements EntryPoint {
	
	Label notizbucherLabel = new Label("Alle Notizbücher aller Nutzer");
	Button notizbucherButton = new Button("Report");
	Label notizenLabel = new Label("Alle Notizen aller Nutzer");
	Button notizenButton = new Button("Report");
	HorizontalPanel main = new HorizontalPanel();
	VerticalPanel vPanelNotizen = new VerticalPanel();
	VerticalPanel vPanelNotizbucher = new VerticalPanel();

	
	@Override
	public void onModuleLoad() {
		vPanelNotizen.add(notizenLabel);
		vPanelNotizen.add(notizenButton);
		
		vPanelNotizbucher.add(notizbucherLabel);
		vPanelNotizbucher.add(notizbucherButton);
		
		notizenButton.addStyleName("gwt-Green-Button");
		notizbucherButton.addStyleName("gwt-Green-Button");
		
		notizenButton.addClickHandler(new notizReportClickHandler());
		notizbucherButton.addClickHandler(new notizbuchReportClickHandler());
	
		vPanelNotizbucher.addStyleName("ReportLabel");
		vPanelNotizen.addStyleName("ReportLabel");;
		
		main.addStyleName("ReportMain");
		main.add(vPanelNotizbucher);
		main.add(vPanelNotizen);
		RootPanel.get("nav").add(main);
		
	}

	
	private class notizReportClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class notizReportAsyncCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class notizbuchReportAsyncCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Object result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	private class notizbuchReportClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
}
