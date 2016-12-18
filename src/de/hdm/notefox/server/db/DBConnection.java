package de.hdm.notefox.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.google.appengine.api.utils.SystemProperty;

/**
 * Anlehnung an Herr Thies & Herr Rathke (Bankprojekt)
 * 
 * Verwalten einer Verbindung zur Datenbank.
 * <p>
 * <b>Vorteil:</b> Sehr einfacher Verbindungsaufbau zur Datenbank.
 * <p>
 * <b>Nachteil:</b> Durch die Singleton-Eigenschaft der Klasse kann nur auf eine
 * fest vorgegebene Datenbank zugegriffen werden.
 * <p>
 * In der Praxis kommen die meisten Anwendungen mit einer einzigen Datenbank
 * aus. Eine flexiblere Variante fuer mehrere gleichzeitige
 * Datenbank-Verbindungen waere sicherlich leistungsfaehiger. Dies wuerde
 * allerdings den Rahmen dieses Projekts sprengen bzw. die Software unnoetig
 * verkomplizieren, da dies fuer diesen Anwendungsfall nicht erforderlich ist.
 * 
 * @author Thies
 */
public class DBConnection {

    /**
     * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
     * von einem sogenannten <b>Singleton</b>.
     * <p>
     * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
     * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
     * speichert die einzige Instanz dieser Klasse.
     * 
     * @see NotizMapper.notizMapper()
     * @see NotizbuchMapper.notizbuchMapper()
     * @see NutzerMapper.nutzerMapper()
     */
    private static Connection con = null;

    /**
     * Die URL, mit deren Hilfe die Datenbank angesprochen wird. In einer
     * professionellen Applikation wuerde diese Zeichenkette aus einer
     * Konfigurationsdatei eingelesen oder ueber einen Parameter von aussen
     * mitgegeben, um bei einer Veraenderung dieser URL nicht die gesamte
     * Software neu komilieren zu muessen.
     */
    private static String googleUrl = "jdbc:mysql://173.194.104.160:3306/notefox?user=admin&password=test1";
    private static String localUrl = "jdbc:mysql://localhost:3306/notefox?user=root&password=414159426";
    
    /**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>DBConnection.connection()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
     * einzige Instanz von <code>DBConnection</code> existiert.
     * <p>
     * 
     * <b>Fazit:</b> DBConnection sollte nicht mittels <code>new</code>
     * instantiiert werden, sondern stets durch Aufruf dieser statischen
     * Methode.
     * <p>
     * 
     * <b>Nachteil:</b> Bei Zusammenbruch der Verbindung zur Datenbank - dies
     * kann z.B. durch ein unbeabsichtigtes Herunterfahren der Datenbank
     * ausgelöst werden - wird keine neue Verbindung aufgebaut, so dass die in
     * einem solchen Fall die gesamte Software neu zu starten ist. In einer
     * robusten Loesung wuerde man hier die Klasse dahingehend modifizieren, dass
     * bei einer nicht mehr funktionsfaehigen Verbindung stets versucht wuerde,
     * eine neue Verbindung aufzubauen. Dies wuerde allerdings ebenfalls den
     * Rahmen dieses Projekts sprengen.
     * 
     * @return DAS <code>DBConncetion</code>-Objekt.
     * @see con
     */
    public static Connection connection() {
        // Wenn es bisher keine Connection zur DB gibt, ...
        if (con == null) {
            String url = null;
            try {
//                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                	// Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
//                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
//                } else {
                	// Local MySQL instance to use during development.
                    Class.forName("com.mysql.jdbc.Driver");
//                    url = localUrl;
//                }
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

        // Verbindung wird zur�ckgegeben
        return con;
    }

}
