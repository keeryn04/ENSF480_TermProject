package frontend.pages;

import backend.DatabaseAccessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import frontend.decorators.DecoratorHelpers;

/** Displays the details of a selected movie */
public class MoviePage implements Page {

    private static MoviePage instance; // Singleton instance
    private DatabaseAccessor dbAccessor;

    private JLabel titleLabel;
    private JLabel posterLabel;
    private JTextArea descriptionArea;

    private MoviePage() {
        this.dbAccessor = new DatabaseAccessor();
    }

    /**
     * Returns the single instance of MoviePage.
     */
    public static MoviePage getInstance() {
        if (instance == null) {
            instance = new MoviePage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font descriptionFont = new Font("Times New Roman", Font.PLAIN, 18);

            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();

            // Create default components
            titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Title", titleFont);
            posterLabel = new JLabel();
            descriptionArea = new JTextArea("Movie Description");
            descriptionArea.setFont(descriptionFont);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);

            // Layout setup
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(titleLabel, BorderLayout.NORTH);
            contentPanel.add(posterLabel, BorderLayout.CENTER);
            contentPanel.add(new JScrollPane(descriptionArea), BorderLayout.SOUTH);

            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error creating Movie Page: %s%n", e.getMessage());
            return new JPanel();
        }
    }

    /**
     * Updates the MoviePage with the selected movie's details.
     */
    public void updateContent(int movieId) {
        try {
            // Fetch movie details from the database
            List<String> movieDetails = dbAccessor.getMovieDetails(movieId);
            if (movieDetails.size() < 6) {
                throw new RuntimeException("Incomplete movie details fetched from the database.");
            }

            // Update UI components
            titleLabel.setText(movieDetails.get(0));
            descriptionArea.setText(movieDetails.get(5));

            // Load the image
            try {
                BufferedImage movieImage = ImageIO.read(new File(movieDetails.get(4)));
                Image scaledImage = movieImage.getScaledInstance(300, 500, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                posterLabel.setText("Image not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            titleLabel.setText("Error fetching movie details");
            descriptionArea.setText("Unable to load movie details from the database.");
        }
    }
}
