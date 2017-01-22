package de.hdm.notefox.client.gui;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.notefox.shared.bo.Notiz;

public class FaelligkeitenEditorPanel extends VerticalPanel {

	HTML uberschrift = new HTML("<h3>Fälligkeit</h3>");
	DateBox datepicker = new DateBox();
    DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");


	public FaelligkeitenEditorPanel(final Notiz notiz) {

		datepicker.addStyleName("datePicker");
		datepicker.setFormat(new DateBox.DefaultFormat(dateFormat));
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		// setValue ins textbox, was vom user ausgewählt wird
		datepicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				notiz.setFaelligkeitsdatum(date);
			}
		});
		// Set the default value
		datepicker.setValue(notiz.getFaelligkeitsdatum(), true);

		HTML ampel = new HTML("<div></div>");
		ampel.addStyleName("ampel");
		
		long tagInMillisekunden = 24 * 60 * 60 * 1000;
		long faelligkeitInMillisekunden = notiz.getFaelligkeitsdatum().getTime();
		long aktuelleZeitInMillisekunden = System.currentTimeMillis();
		long millisekundenBisFaelligkeit = faelligkeitInMillisekunden - aktuelleZeitInMillisekunden;
		
		if(millisekundenBisFaelligkeit > tagInMillisekunden * 3){
			ampel.addStyleName("gruen");
		} else if (millisekundenBisFaelligkeit < tagInMillisekunden * 3 && millisekundenBisFaelligkeit > 0){
			ampel.addStyleName("gelb");
		} else {
			ampel.addStyleName("rot");
		}
		
		add(ampel);
		add(uberschrift);
		add(datepicker);
		addStyleName("vPanelRight");

	}

	public Date getFaelligkeitsdatum() {
		return datepicker.getValue();

	}

	public void setFaelligkeisdatum(Date faelligkeitsdatum) {
		datepicker.setValue(faelligkeitsdatum);
	}
}
