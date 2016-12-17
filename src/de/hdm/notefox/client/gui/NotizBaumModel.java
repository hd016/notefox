package de.hdm.notefox.client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.Nutzer;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class NotizBaumModel implements TreeViewModel {

	private VerticalPanel vPanel = new VerticalPanel();
	private NotizEditorPanel notizEditorPanel;
	private final Nutzer nutzer;

	public NotizBaumModel(NotizEditorPanel notizEditorPanel, Nutzer nutzer) {
		this.notizEditorPanel = notizEditorPanel;
		this.nutzer = nutzer;
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {

			AsyncDataProvider<Notizbuch> notizbuchProvider = new AsyncDataProvider<Notizbuch>() {

				@Override
				protected void onRangeChanged(HasData<Notizbuch> display) {
					final NotizobjektAdministrationAsync administration = GWT
							.create(NotizobjektAdministration.class);
					administration.nachAllenNotizbuechernDesNutzersSuchen(
							nutzer, new AsyncCallback<List<Notizbuch>>() {

								@Override
								public void onSuccess(List<Notizbuch> result) {
									updateRowCount(result.size(), true);
									updateRowData(0, result);
								}

								@Override
								public void onFailure(Throwable caught) {
								}
							});
				}
			};

			return new DefaultNodeInfo<Notizbuch>(notizbuchProvider,
					new AbstractCell<Notizbuch>("click") {
						@Override
						public void render(
								com.google.gwt.cell.client.Cell.Context context,
								Notizbuch value, SafeHtmlBuilder sb) {
							sb.appendEscaped(value.getTitel());
						}

						@Override
						public void onBrowserEvent(
								com.google.gwt.cell.client.Cell.Context context,
								Element parent, Notizbuch value,
								NativeEvent event,
								ValueUpdater<Notizbuch> valueUpdater) {
							Window.alert("OK!");
						}

					});
		} else if (value instanceof Notizbuch) {
			final Notizbuch notizbuch = (Notizbuch) value;
			List<Notiz> notizen = notizbuch.getNotizen();
			Notiz dummyNotiz = new Notiz();
			dummyNotiz.setId(-1);
			notizen.add(dummyNotiz);
			return new DefaultNodeInfo<Notiz>(new ListDataProvider<Notiz>(notizen), new AbstractCell<Notiz>("click") {

				@Override
				public void render(com.google.gwt.cell.client.Cell.Context context, Notiz value, SafeHtmlBuilder sb) {
					if (value.getId() == -1) {
						sb.appendEscaped("Neue Notiz");
					} else {
						sb.appendEscaped(value.getTitel());
					}
				}

				public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, Notiz value,
						NativeEvent event, ValueUpdater<Notiz> valueUpdater) {
					if (value.getId() == -1) {
						notizEditorPanel.neueNotiz(notizbuch);
					} else {
						notizEditorPanel.setNotizobjekt(value);
					}
				}

			});

		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		if (value instanceof Notizbuch) {
			return false;
		} else if (value instanceof Notiz) {
			return true;
		}

		return false;
	}
	
}
