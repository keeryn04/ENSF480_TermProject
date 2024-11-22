package frontend.pages;

import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JPanel;

/** Displays the HomePage with a list of movies fetched from the database */
public class HomePage implements Page {

    /**
     * Creates the Homepage elements.
     * Fetches movie details from the database and dynamically creates UI components.
     */
    @Override
    public JPanel createPage() {
        try {
            // Create fonts
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

            // Panels
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel movieSelectionPanel = new JPanel(new FlowLayout());
            movieSelectionPanel.setBackground(Color.WHITE);

            // Database connection details
            String dbUrl = "jdbc:mysql://localhost:3306/acmeplexdb";
            String dbUser = "root";
            String dbPassword = "password";

            // Fetch movie details from the database
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement statement = connection.prepareStatement("SELECT movie_id, title, poster_path FROM Movies");
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Extract movie details
                    int movieId = resultSet.getInt("movie_id");
                    String movieTitle = resultSet.getString("title");
                    String posterPath = resultSet.getString("poster_path");

                    // Create movie panel
                    JPanel moviePanel = DecoratorHelpers.createMoviePanel(posterPath, movieTitle, Color.DARK_GRAY, buttonFont);

                    // Get the button from the moviePanel and add an action listener
                    JButton movieButton = (JButton) ((BorderLayout) moviePanel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
                    ActionListener listener = e -> {
                        MoviePage.getInstance().updateContent(movieId);
                        Window.getInstance().showPanel("MoviePage");
                    };

                    ActionListenerDecorator accountDecorator = new ActionListenerDecorator(movieButton, movieButton, listener);
                    accountDecorator.applyDecoration(listener);

                    // Add to the movie selection panel
                    movieSelectionPanel.add(moviePanel);
                }
            }

            // Use builder to add all panels in main layout
            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(movieSelectionPanel, BorderLayout.CENTER)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return new JPanel(); // Return an empty panel on failure
        }
    }
}
