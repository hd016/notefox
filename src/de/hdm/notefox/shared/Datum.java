package de.hdm.notefox.shared;

import java.util.ArrayList;
import java.util.Date;

import de.hdm.notefox.shared.bo.Notiz;


public class Datum {
	
	/**Variablen der Klasse Datum.
	 */

	private int faelligkeitId;
	
	private boolean status;
	
	private Date faelligkeitsdatum;
	
	
	/**
	 * ArrayList mit Objekten der verbundenen Klasse + 
	 * übergeben Liste im Konstruktor 
	 */
	private ArrayList<Notiz> notizliste; 
	public Datum(ArrayList<Notiz> nListe){ 
		this.notizliste = nListe; 
	} 
	
	
/**Auslesen und Setzen der Variablen.
 */
	public int getFaelligkeitId() {
		return faelligkeitId;
	}

	public void setFaelligkeitId(int faelligkeitId) {
		this.faelligkeitId = faelligkeitId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getFaelligkeitsdatum() {
		return faelligkeitsdatum;
	}

	public void setFaelligkeitsdatum(Date faelligkeitsdatum) {
		this.faelligkeitsdatum = faelligkeitsdatum;
	} 
}
