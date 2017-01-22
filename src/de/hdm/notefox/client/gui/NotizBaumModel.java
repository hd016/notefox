package de.hdm.notefox.client.gui;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.notefox.client.Notefox;
import de.hdm.notefox.shared.NotizobjektAdministration;
import de.hdm.notefox.shared.NotizobjektAdministrationAsync;
import de.hdm.notefox.shared.bo.Notiz;
import de.hdm.notefox.shared.bo.Notizbuch;

public class NotizBaumModel implements TreeViewModel {

	private Notefox notefox;
	private Notizbuch geoeffnet;

	final NotizobjektAdministrationAsync administration = GWT
			.create(NotizobjektAdministration.class);

	
	public NotizBaumModel( Notefox notefox, Notizbuch geoeffnet) {
		this.notefox = notefox;
		this.geoeffnet = geoeffnet;
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {

			AsyncDataProvider<Notizbuch> notizbuchProvider = new AsyncDataProvider<Notizbuch>() {

				@Override
				protected void onRangeChanged(HasData<Notizbuch> display) {
					
					administration.nachAllenNotizbuechernSuchen(
						new AsyncCallback<List<Notizbuch>>() {

								@Override
								public void onSuccess(List<Notizbuch> result) {
									Notizbuch dummy = new Notizbuch();
									dummy.setId(-1);
									result.add(dummy);
									
									updateRowCount(result.size(), true);
									updateRowData(0, result);
									
									if (geoeffnet != null) {

										for (int i = 0; i < result.size(); i++) {
											if (result.get(i).equals(geoeffnet)) {
												notefox.getCelltree().getRootTreeNode().setChildOpen(i, true);
											}
										}
									}
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
							if (value.getId() == -1){
								sb.append(new SafeHtml() {
									
									private static final long serialVersionUID = 1L;

									@Override
									public String asString() {
										return "<button class=\"buttonNeuesNotizbuch\" style=\"vertical-align:middle\"><span>Neues Notizbuch</span></button>";
									}
								});
							}	else {
							sb.appendEscaped(value.getTitel());
							}
						}

						@Override
						public void onBrowserEvent(
								com.google.gwt.cell.client.Cell.Context context,
								Element parent, Notizbuch value,
								NativeEvent event,
								ValueUpdater<Notizbuch> valueUpdater) {
							if(value.getId() == -1){
								notefox.neuesNotizbuch();
							} else {
								notefox.zeigeNotizbuch(value);
							}
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
						sb.append(new SafeHtml() {
							
							private static final long serialVersionUID = 1L;

							@Override
							public String asString() {
								return "<button class=\"button\" style=\"vertical-align:middle\"><span>Neue Notiz</span></button>";
							}
						});
					} else {
						sb.appendEscaped(value.getTitel());
					}
				}

				public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, Notiz value,
						NativeEvent event, ValueUpdater<Notiz> valueUpdater) {
					if (value.getId() == -1) {
						notefox.neueNotiz(notizbuch);
					} else {
						notefox.zeigeNotiz(value);
					}
				}

			});

		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {

		if (value instanceof Notizbuch) {
			Notizbuch notizbuch = (Notizbuch) value;
			return notizbuch.getId() == -1;
		} else if (value instanceof Notiz) {
			return true;
		}

		return false;
	}
	
}
