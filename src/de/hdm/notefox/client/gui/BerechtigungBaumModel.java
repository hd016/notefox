package de.hdm.notefox.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

public class BerechtigungBaumModel implements TreeViewModel {

	private Berechtigung berechtigung;

	public BerechtigungBaumModel(Berechtigung berechtigung) {
		this.berechtigung = berechtigung;
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {

			List<Notizobjekt> notizobjekte = new ArrayList<>();
			Notizobjekt notizobjekt = new Notizbuch();
			notizobjekt.setTitel("Berechtigte Nutzer");

			List<Berechtigung> berechtigunglist = new ArrayList<>();

			Berechtigung berechtigung = new Berechtigung();
			berechtigung.setBerechtigungsart(Berechtigungsart.LESEN);
			berechtigunglist.add(berechtigung);

			Berechtigung berechtigung1 = new Berechtigung();
			berechtigung1.setBerechtigungsart(Berechtigungsart.EDITIEREN);
			berechtigunglist.add(berechtigung1);

			Berechtigung berechtigung2 = new Berechtigung();
			berechtigung2.setBerechtigungsart(Berechtigungsart.LOESCHEN);
			berechtigunglist.add(berechtigung2);

			notizobjekt.setBerechtigungen(berechtigunglist);

			notizobjekte.add(notizobjekt);

			return new DefaultNodeInfo<Notizobjekt>(new ListDataProvider<Notizobjekt>(notizobjekte),
					new AbstractCell<Notizobjekt>() {
						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Notizobjekt value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getTitel());
						}

					});

		} else if (value instanceof Notizobjekt) {

			final Notizobjekt notizobjekt = (Notizobjekt) value;
			return new DefaultNodeInfo<Berechtigung>(
					new ListDataProvider<Berechtigung>(notizobjekt.getBerechtigungen()),
					new AbstractCell<Berechtigung>("click") {

						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Berechtigung value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getBerechtigungsart().name());

						}

						@Override
						public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent,
								Berechtigung value, NativeEvent event, ValueUpdater<Berechtigung> valueUpdater) {
							if (value.getBerechtigungsart() == Berechtigungsart.LESEN) {
								Window.alert("LESEN!");
							} else if (value.getBerechtigungsart() == Berechtigungsart.EDITIEREN) {
								Window.alert("EDITIEREN!");
							} else if (value.getBerechtigungsart() == Berechtigungsart.LOESCHEN) {
								Window.alert("LÖSCHEN!");

							}

						}
					});
 
		}
		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		if (value instanceof Notizobjekt) {
			Notizobjekt notizobjekt = (Notizobjekt) value;
			return notizobjekt.getBerechtigungen().isEmpty();
		} else if (value instanceof Berechtigung) {
			return true;
		}

		return false;
	}

}
