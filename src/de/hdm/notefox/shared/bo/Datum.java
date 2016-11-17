package de.hdm.notefox.shared.bo;

import java.util.Date;

public class Datum {
	
	/**Variablen der Klasse Datum.
	 */

	private int faelligkeitId;
	
	private boolean status;
	
	private Date faelligkeitsdatum;
	
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
