package de.hdm.notefox.shared.report;

import java.io.Serializable;
import java.util.*;

/**
 * Report der aus mehrere Teil-Reports zusammengesetzt ist.
 */
public abstract class CompositeReport extends Report implements Serializable {

	/**
	 * Unique IDentifier
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Anzahl der Teil-Reports
	 */
	private Vector<Report> subReports = new Vector<Report>();

	/**
	 * Teil-Report wird hinzugefügt.
	 */
	public void addSubReport(Report r) {
		this.subReports.addElement(r);
	}

	/**
	 * Loeschen des Teil-Reports
	 */
	public void removeSubReport(Report r) {
		this.subReports.removeElement(r);
	}

	/**
	 * Die Anzahl von Teil-Reports werden ausgelesen.
	 */
	public int getNumSubReports() {
		return this.subReports.size();
	}

	/**
	 * Einzelnes Teil-Report wird ausgelesen.
	 */
	public Report getSubReportAt(int i) {
		return this.subReports.elementAt(i);
	}
}