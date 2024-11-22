package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.MoviePageObserver;

public class MoviePage implements Page, MoviePageObserver {
    private static MoviePage instance; // Singleton

    //Data fields for movie details
    private String movieTitle = "Movie Title";
    private String movieDescription = "Movie Description";
    private BufferedImage posterImage;
    private Integer screenNum = 1; //Stored for ticket display purposes

    //UI components
    private JLabel titleLabel;
    private JLabel posterLabel;
    private JTextArea descriptionArea;

    private MoviePage() {
        //Initialize UI components
        Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
        Font descriptionFont = new Font("Times New Roman", Font.BOLD, 18);

        titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieTitle, titleFont);
        posterLabel = new JLabel(); //Poster image
        descriptionArea = new JTextArea(movieDescription);
        descriptionArea.setFont(descriptionFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        //Register with MovieState
        MovieState.getInstance().addMovieObserver(this);
    }

    /**Returns single instance of MoviePage */
    public static MoviePage getInstance() {
        if (instance == null) {
            instance = new MoviePage();
        }
        return instance;
    }

    /**Creates the MoviePage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {
            //Make header and footer
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("movieTicket");

            //Layout setup
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(titleLabel, BorderLayout.NORTH);
            contentPanel.add(posterLabel, BorderLayout.CENTER);
            contentPanel.add(new JScrollPane(descriptionArea), BorderLayout.SOUTH);

            //Use builder to add all panels in the main layout
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

    /**Update movie data based on MovieState data */
    @Override
    public void onMovieSelected(String key, Object value) {
        //React to changes from AppState
        switch (key) {
            case "movieTitle":
                movieTitle = (String) value;
                updateContent();
                break;
            case "movieDetails":
                movieDescription = (String) value;
                updateContent();
                break;
            case "moviePoster":
                posterImage = loadImage((String) value);
                updateContent();
                break;
            case "screenNum":
                screenNum = (Integer) value;
                updateContent();
                break;
            default:
                break;
        }
    }

    /**Change the aspects of the MoviePage based on AppState data retrieved by observer */
    private void updateContent() {
        SwingUtilities.invokeLater(() -> {
            titleLabel.setText(movieTitle);
            descriptionArea.setText(movieDescription);

            if (posterImage != null) {
                Image scaledImage = posterImage.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                posterLabel.setText("Image not found");
                posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        });
    }

    /**Helper function for loading image */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**Make a movie panel to add to the main content*/
    @SuppressWarnings("unused")
    public static JPanel createMoviePanel(String movieTitle, String movieDesc, String imagePath, Color buttonColor, Font buttonFont) {
        JPanel moviePanel = new JPanel(new BorderLayout());

        //Load the image
        try {
            BufferedImage movieImage = ImageIO.read(new File(imagePath));
            Image scaledImage = movieImage.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            moviePanel.add(picLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Image not found", SwingConstants.CENTER);
            moviePanel.add(errorLabel, BorderLayout.CENTER);
        }

        //Create button with specified color and font
        JButton movieButton = DecoratorHelpers.makeButton(buttonColor, Color.WHITE, movieTitle, buttonFont);
        ActionListener listener = e -> {
            MoviePage.getInstance().updateContent();
            Window.getInstance().showPanel("MoviePage");
        };

        ActionListenerDecorator accountDecorator = new ActionListenerDecorator(movieButton, movieButton, listener);
        moviePanel.add(movieButton, BorderLayout.SOUTH);

        return moviePanel;
    }
}