import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import backend.DatabaseConfig;
import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.connectToServer()) { // Connect to server, not specific database
            System.out.println("Connected to MySQL server.");

            // Step 1: Check if the database exists
            boolean isNewDatabase = !doesDatabaseExist(connection, "AcmePlexDB");

            // Step 2: Create the database if it does not exist
            if (isNewDatabase) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("CREATE DATABASE AcmePlexDB");
                    System.out.println("Database created.");
                }
            } else {
                System.out.println("Database already exists.");
            }

            // Step 3: Use the database
            connection.setCatalog("AcmePlexDB");

            // Step 4: Ensure the schema is applied
            String schemaScriptPath = "SQL/AcmePlexDB.sql";
            executeSQLScript(connection, schemaScriptPath);

            // Step 5: If the database is new, run the initializer script
            if (isNewDatabase) {
                String initializerScriptPath = "SQL/InitializerDB.sql";
                executeSQLScript(connection, initializerScriptPath);
                System.out.println("Database initialized with sample data.");
            } else {
                System.out.println("Skipping initializer script to preserve existing data.");
            }

            System.out.println("Database setup complete.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Window window = Window.getInstance();

        window.makePages();

        window.showWindow();
    }

    // Helper method to check if the database exists
    private static boolean doesDatabaseExist(Connection connection, String dbName) {
        try (ResultSet resultSet = connection.getMetaData().getCatalogs()) {
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(dbName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking database existence: " + e.getMessage());
        }
        return false;
    }

    // Helper method to execute an SQL script (ensures idempotency)
    private static void executeSQLScript(Connection connection, String scriptPath) {
        try {
            // Read the script file
            String sqlScript = new String(Files.readAllBytes(Paths.get(scriptPath)));
            Statement statement = connection.createStatement();

            // Split and execute SQL commands
            for (String sqlCommand : sqlScript.split(";")) {
                if (!sqlCommand.trim().isEmpty()) {
                    statement.execute(sqlCommand.trim());
                }
            }
            System.out.println("SQL script executed successfully: " + scriptPath);
        } catch (Exception e) {
            System.err.println("Error executing SQL script: " + e.getMessage());
        }
    }
}