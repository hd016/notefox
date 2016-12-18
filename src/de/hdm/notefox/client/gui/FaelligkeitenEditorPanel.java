package de.hdm.notefox.client.gui;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class FaelligkeitenEditorPanel extends VerticalPanel {

	HTML uberschrift = new HTML("<h3>Fälligkeit</h3>");
	DatePicker datepicker = new DatePicker();
	final TextBox auswahl = new TextBox();

	public FaelligkeitenEditorPanel() {

		auswahl.setReadOnly(true);
		auswahl.addStyleName("TextBox-readonly");
		datepicker.addStyleName("datePicker");
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		// setValue ins textbox, was vom user ausgewählt wird
		datepicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(date);
				auswahl.setText(dateString);
			}
		});
		// Set the default value
		datepicker.setValue(new Date(), true);

		add(uberschrift);
		add(datepicker);
		add(auswahl);

	}
}
