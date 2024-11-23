import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

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

        // Initialize the application UI
        Window window = Window.getInstance();

        HomePage home = new HomePage();
        JPanel homePanel = home.createPage();
        window.addPanel("Home", homePanel);
        window.showPanel("Home");

        ProfilePage profile = new ProfilePage();
        JPanel profilePanel = profile.createPage();
        window.addPanel("ProfilePage", profilePanel);

        EditProfilePage editProfile = new EditProfilePage();
        JPanel editProfilePanel = editProfile.createPage();
        window.addPanel("ProfileEditPage", editProfilePanel);

        MoviePage movie = MoviePage.getInstance();
        JPanel moviePanel = movie.createPage();
        window.addPanel("MoviePage", moviePanel);

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
