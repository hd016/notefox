package de.hdm.notefox.server;

import java.util.logging.Logger;

import de.hdm.notefox.shared.CommonSettings;

/**
 * <p>
 * Klasse mit Eigenschaften und Diensten, die für alle Server-seitigen Klassen
 * relevant sind.
 * </p>
 * <p>
 * In ihrem aktuellen Entwicklungsstand bietet die Klasse eine rudimentäre
 * Unterstützung der Logging-Funkionalität unter Java. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * <code>ServerSideSettings.getLogger()</code> genutzt werden kann.
 * </p>
 * 
 *Anlehnung an Herr Thies & Herr Rathke
 * @version 1.0
 * @since 28.02.2012
 * 
 */
public class ServersideSettings extends CommonSettings {
  private static final String LOGGER_NAME = "Notefox Server";
  private static final Logger log = Logger.getLogger(LOGGER_NAME);

  