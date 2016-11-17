package de.hdm.notefox.shared.bo;

public class Notiz extends Notizobjekt {

	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	
	private Notizquelle notizquelle;
	private Datum datum;
	

	  /** Variablen der Notiz
	   */
	private int notizId;

	  /** Auslesen und Setzen der Variablen
	   */
	public int getNotizId() {
		return notizId;
	}


	public void setNotizId(int notizId) {
		this.notizId = notizId;
	}
	
}
