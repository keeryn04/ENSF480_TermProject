package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/AcmePlexDB";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "password";

    /**
     * Establishes and returns a connection to the database.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
