package de.hdm.notefox.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 */
public class DBConnection {

    /**
     * Initialisieren eine neue Instanz der DbConnection-Klasse.
     * 
     */
    private static Connection con = null;

    
    
    /**
     * Die URL, mit deren Hilfe die Datenbank angesprochen wird. 
     */
    private static String googleUrl = "";
    private static String localUrl = "jdbc:mysql://localhost:3306/it-projekt?user=root";
    

    /**
     * Diese statische Methode kann durch "DBConnection connection()" aufgrufen werden durch
     * Sie stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
     * einzige Instanz von DBConnection existiert.
     */
    public static Connection connection() {
        // Wenn es bisher keine Connection zur DB gibt, ...
        if (con == null) {
            String url = null;
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    // Neu Klasse laden 
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                } else {
                    // Lokale MySQL-Instanz während der Entwicklung zu verwenden.
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }

        // Verbindung wird zurückgegeben
        return con;
    }

}
