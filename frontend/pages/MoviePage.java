package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import frontend.decorators.DecoratorHelpers;

public class MoviePage implements Page  {
    private static MoviePage instance; //Singleton

    JLabel titleLabel;
    JLabel posterLabel;
    JTextArea descriptionArea;

    private MoviePage() {
        titleLabel = new JLabel();
        posterLabel = new JLabel();
        descriptionArea = new JTextArea();
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
            Font descriptionFont = new Font("Times New Roman", Font.BOLD, 18);
            
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("movieTicket");

            //Create default components
            titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Title", titleFont);
            posterLabel = new JLabel(); // For the poster image
            descriptionArea = new JTextArea("Movie Description");
            descriptionArea.setFont(descriptionFont);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);

            //Layout setup
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(titleLabel, BorderLayout.NORTH);
            contentPanel.add(posterLabel, BorderLayout.CENTER);
            contentPanel.add(new JScrollPane(descriptionArea), BorderLayout.SOUTH);

            //Use builder to add all panels in main layout
            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Movie Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**
     * Updates the MoviePage with the selected movie's details.
     */
    public void updateContent(String title, String description, String posterPath) {
        titleLabel.setText(title);

        //Load the image
        try {
            BufferedImage movieImage = ImageIO.read(new File(posterPath));
            Image scaledImage = movieImage.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            posterLabel = new JLabel("Image not found", SwingConstants.CENTER);
        }

        descriptionArea.setText(description);
    }
}
