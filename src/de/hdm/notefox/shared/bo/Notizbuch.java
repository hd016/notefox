package de.hdm.notefox.bo;

public class Notizbuch extends Notizobjekt {
	
	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	  /** Variablen des Notizbuches
	   */
	private int notizbuchId;
	
	

	  /** Auslesen und Setzen der Variablen
	   */
	public int getNotizbuchId() {
		return notizbuchId;
	}

	public void setNotizbuchId(int notizbuchId) {
		this.notizbuchId = notizbuchId;
	}
}
