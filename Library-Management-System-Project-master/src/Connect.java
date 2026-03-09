/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sudhir Kushwaha
 */
public class Connect {
    private static final Logger LOG = Logger.getLogger(Connect.class.getName());

    /**
     * Returns a new JDBC Connection. Callers must close the connection (try-with-resources recommended).
     * Reads DB_URL, DB_USER, DB_PASS from environment variables; falls back to sensible defaults.
     */
    public static Connection ConnectToDB() {
        String url = System.getenv("DB_URL");
        if (url == null || url.isBlank()) {
            url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
        }
        String user = System.getenv("DB_USER");
        if (user == null) user = "root";
        String pass = System.getenv("DB_PASS");
        if (pass == null) pass = "1234";

        try {
            // For modern MySQL drivers the driver is auto-registered. If you get ClassNotFoundException,
            // ensure the connector JAR is on the classpath and uncomment the next line:
            // Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to connect to DB: {0}", ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        try (Connection c = ConnectToDB()) {
            if (c != null && !c.isClosed()) {
                LOG.info("DB connection successful.");
                System.out.println("DB connection successful.");
            } else {
                LOG.severe("DB connection failed.");
                System.err.println("DB connection failed. Check logs for details.");
                System.exit(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error during DB test: {0}", e.getMessage());
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}
