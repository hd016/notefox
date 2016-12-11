package de.hdm.notefox.client.gui;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class NotizBaumModel implements TreeViewModel {

	
	VerticalPanel vPanel = new VerticalPanel();
	
	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			List<Notizbuch> notizbuchlist = new ArrayList<>();

			Notizbuch notizbuch = new Notizbuch();
			notizbuch.setTitel("Hobby");
			notizbuchlist.add(notizbuch);
			
			Notizbuch notizbuch1 = new Notizbuch();
			notizbuch1.setTitel("Studium");
			notizbuchlist.add(notizbuch1);
			
			Notizbuch notizbuch2 = new Notizbuch();
			notizbuch2.setTitel("Arbeit");
			notizbuchlist.add(notizbuch2);
			
			Notizbuch notizbuch3 = new Notizbuch();
			notizbuch3.setTitel("Familie");
			notizbuchlist.add(notizbuch3);
			

			List<Notiz> notizlistStudium = new ArrayList<>();
			Notiz notiz = new Notiz();
			notiz.setTitel("Abgaben");
			
			Notiz notiz1 = new Notiz();
			notiz1.setTitel("Projekte");
			
			Notiz notiz2 = new Notiz();
			notiz2.setTitel("Klausuren");
			
			notizlistStudium.add(notiz);
			notizlistStudium.add(notiz1);
			notizlistStudium.add(notiz2);
			notizbuch1.setNotizen(notizlistStudium);
			
			List<Notiz> notizlistHobbys = new ArrayList<>();
			
			Notiz notiz3 = new Notiz();
			notiz3.setTitel("Termine");
			notizlistHobbys.add(notiz3);
			//notizbuch.setNotizen(notizlistHobbys);
			
			List<Notiz> notizlistFamilie = new ArrayList<>();
			
			Notiz notiz4 = new Notiz();
			notiz3.setTitel("Einkaufen");
			notizlistFamilie.add(notiz4);
			//notizbuch3.setNotizen(notizlistFamilie);


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
			//return new DefaultNodeInfo<Notiz>(new ListDataProvider<Notiz>(notizbuch.getNotizen()),
					new AbstractCell<Notiz>() {

						@Override
						public void render(com.google.gwt.cell.client.Cell.Context context, Notiz value,
								SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getTitel());

						}
					};//);

		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		if (value instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) value;
			//return notizbuch.getNotizen().isEmpty();
		} else if (value instanceof Notiz) {
			return true;
		}

		return false;
	}

}
