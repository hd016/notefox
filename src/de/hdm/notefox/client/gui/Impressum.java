package de.hdm.notefox.client.gui;

import com.google.gwt.user.client.ui.HTML;

/**
 * Impressum-Klasse des Projekts <b>Notefox</b>. Klasse Impressum erbt von der
 * Klasse HTML. Es wird ein <div> erzeugt und der jeweilige Inhalt des
 * Impressums definiert.
 * 
 * @author Neriman Kocak und Harun Dalici
 */

public class Impressum extends HTML {

	public Impressum() {
		this.setHTML((

		"<div class= 'Impressum'>" + "<b>" + "Angaben gemäß §5 Telemediengesetz </b>" + "</br>" + "</br>"
				+ "ITProjekt - Gruppe 05" + "</br></br>" + "Hochschule der Medien" + "</br>"
				+ "<b> Wirtschaftsinformatik und digitale Medien </b></br>" + "Nobelstrasse 10</br>"
				+ "70569 Stuttgart </br></br>" + "Kontakt</br>Telefon: +49 711 8923 10</br>"
				+ "<br><br> Der Studiengang Wirtschaftsinformatik und digitale"
				+ " Medien<br>wird rechtlich vertreten durch die Hochschule der Medien"
				+ " Stuttgart. <br>  <br><A HREF=\"https://www.hdm-stuttgart.de/"
				+ "Impressum\"TARGET=\"_blank\">Impressum der Hochschule </A>" + "</div>"));
	}

}
