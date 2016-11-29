package de.hdm.notefox.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class NotizBaumModel implements TreeViewModel {

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			List<Notizbuch> notizbuchlist = new ArrayList<>();

			Notizbuch notizbuch = new Notizbuch();
			notizbuch.setTitel("Meine Notizbücher");
			notizbuchlist.add(notizbuch);

			Notizbuch notizbuch1 = new Notizbuch();
			notizbuch1.setTitel("Nutzer");
			notizbuchlist.add(notizbuch1);

			List<Notiz> notizlist = new ArrayList<>();
			Notiz notiz = new Notiz();
			notiz.setTitel("Meine Notizen");
			notizlist.add(notiz);
			notizbuch.setNotizen(notizlist);
			
			List<Nutzer> eigentuemerlist = new ArrayList<>();
			Nutzer eigentuemer = new Nutzer();
			eigentuemer.setName("Namen");
			eigentuemerlist.add(eigentuemer);
			eigentuemerlist.isEmpty();

		   
			
			

			return new DefaultNodeInfo<Notizbuch>(new ListDataProvider<Notizbuch>(notizbuchlist),
					new AbstractCell<Notizbuch>() {
						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Notizbuch value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getTitel());
						}
					});
		} else if (value instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) value;
			return new DefaultNodeInfo<Notiz>(new ListDataProvider<Notiz>(notizbuch.getNotizen()),
					new AbstractCell<Notiz>() {

						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Notiz value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getTitel());

						}
					});
		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		if (value instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) value;
			return notizbuch.getNotizen().isEmpty();
		} else if (value instanceof Notiz) {
			return true;
		}

		return false;
	}

}
