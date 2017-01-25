package de.hdm.notefox.shared.bo;

import java.util.ArrayList;
import java.util.List;

/**
 *  Das BusinessObjekt: Notizbuch. Erbt vom Notizobjekt. 
 *
 */
public class Notizbuch extends Notizobjekt {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	private List<Notiz> notizen = new ArrayList<Notiz>();

	public List<Notiz> getNotizen() {
		return notizen;
	}

	public void setNotizen(List<Notiz> notizen) {
		this.notizen = notizen;
	}

}
