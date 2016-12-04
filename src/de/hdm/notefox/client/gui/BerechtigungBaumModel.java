package de.hdm.notefox.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.shared.Berechtigung;
import de.hdm.notefox.shared.Berechtigung.Berechtigungsart;
import de.hdm.notefox.shared.bo.Notizbuch;
import de.hdm.notefox.shared.bo.Notizobjekt;

public class BerechtigungBaumModel implements TreeViewModel {

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {

			List<Notizobjekt> notizobjekte = new ArrayList<>();
			Notizobjekt notizobjekt = new Notizbuch();
			notizobjekt.setTitel("Berechtigte Nutzer");

			List<Berechtigung> berechtigunglist = new ArrayList<>();

			Berechtigung berechtigung = new Berechtigung();
			berechtigung.setBerechtigungName(Berechtigungsart.LESEN);
			berechtigunglist.add(berechtigung);
			
			Berechtigung berechtigung1 = new Berechtigung();
			berechtigung1.setBerechtigungName(Berechtigungsart.EDITIEREN);
			berechtigunglist.add(berechtigung1);

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

			Notizobjekt notizobjekt = (Notizobjekt) value;
			return new DefaultNodeInfo<Berechtigung>(
					new ListDataProvider<Berechtigung>(notizobjekt.getBerechtigungen()),
					new AbstractCell<Berechtigung>() {

						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Berechtigung value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getBerechtigungName().name());

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
