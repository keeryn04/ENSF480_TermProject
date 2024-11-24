import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import backend.DatabaseConfig;
import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.connect()) {
            System.out.println("Connected to MySQL server.");

            // Step 1: Verify database exists or create it
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS AcmePlexDB");
                System.out.println("Database verified.");
            }

            // Step 2: Use the database
            connection.setCatalog("AcmePlexDB");

            // Step 3: Ensure all necessary tables exist
            String sqlScriptPath = "SQL/AcmePlexDB.sql";
            executeSQLScript(connection, sqlScriptPath);

            System.out.println("Database schema ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Window window = Window.getInstance();

        window.makePages();

        window.showWindow();
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
            System.out.println("SQL script executed successfully.");
        } catch (Exception e) {
            System.err.println("Error executing SQL script: " + e.getMessage());
        }
    }
}

//Compile
//javac -cp lib/mysql-connector-j-9.1.0.jar Main.java frontend/pages/*.java frontend/decorators/*.java frontend/observers/*.java backend/*.java

//Run
//java -cp ".;lib\mysql-connector-j-9.1.0.jar"Main