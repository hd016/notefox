package de.hdm.notefox.shared.bo;

import java.util.Date;

public class Notiz extends Notizobjekt {

	/** Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;
	
	  /** Konstruktor
	   */
	public Notiz() {
	}

	/**
	 * Variablen der Notiz
	 */
	private Date faelligkeitsdatum;
	private boolean faelligkeitsstatus;
	private String notizquelle;
	private int notizbuchId;
	

	
	/** Setzen und Auslesen der Variablen
	 */

	public Date getFaelligkeitsdatum() {
		return faelligkeitsdatum;
	}


	public void setFaelligkeitsdatum(Date faelligkeitsdatum) {
		this.faelligkeitsdatum = faelligkeitsdatum;
	}

	public String getNotizquelle() {
		return notizquelle;
	}

	public void setNotizquelle(String notizquelle) {
		this.notizquelle = notizquelle;
	}


	public int getNotizbuchId() {
		return notizbuchId;
	}


	public void setNotizbuchId(int notizbuchID) {
		this.notizbuchId = notizbuchID;
	}


//	public boolean isFaelligkeitsstatus() {
//		return faelligkeitsstatus;
//	}


	public void setFaelligkeitsstatus(boolean faelligkeitsstatus) {
		this.faelligkeitsstatus = faelligkeitsstatus;
	}


	public Boolean getFaelligkeitsstatus() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
