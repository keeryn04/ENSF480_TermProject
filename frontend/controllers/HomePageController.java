package frontend.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.DatabaseAccessor;
import backend.Movie;
import backend.User;
import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.pages.Window;

public class HomePageController implements PageController {
    private static HomePageController instance;

    private ArrayList<Movie> allMovies;
    private User currentUser;

    public HomePageController() { onLoad(); }

    //Singleton management
    public static HomePageController getInstance() {
        if (instance == null) {
            instance = new HomePageController();
        }
        return instance;
    }

    //Setters and Getters
    public ArrayList<Movie> getMovies() { return allMovies; }
    public void setMovies(ArrayList<Movie> allMovies) { this.allMovies = allMovies; }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }

    //When page is loaded
    @Override
    public void onLoad() {
        Movie movie;
        int movieNum = 1;
        while ((movie = DatabaseAccessor.getMovie(movieNum)) != null) {
            allMovies.add(movie);
            movieNum++;
        }
    }

    //When page data is updated
    @Override
    public void onUpdate() {} //No updates to homepage

    //Helper function
    public JPanel makeMovieSelectionPanel() {
        JPanel movieSelectionPanel = new JPanel();
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);

        for (Movie movie : allMovies) {
            JPanel moviePanel = createMoviePanel(movie.getTitle(), movie.getDescription(), movie.getPosterPath(), Color.DARK_GRAY, buttonFont);
            JButton movieButton = (JButton) moviePanel.getComponent(1);
            ActionListener listener = e -> {
                MoviePageController.getInstance().setCurrentMovie(movie);
            };

            movieButton.addActionListener(listener);

            movieSelectionPanel.add(moviePanel);
        }

        return movieSelectionPanel;
    }

    @SuppressWarnings("unused")
    private JPanel createMoviePanel(String movieTitle, String movieDesc, String imagePath, Color buttonColor, Font buttonFont) {
        JPanel moviePanel = new JPanel(new BorderLayout());

        try {
            BufferedImage movieImage = ImageIO.read(new File(imagePath));
            Image scaledImage = movieImage.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            moviePanel.add(picLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Image not found", SwingConstants.CENTER);
            moviePanel.add(errorLabel, BorderLayout.CENTER);
        }

        JButton movieButton = DecoratorHelpers.makeButton(buttonColor, Color.WHITE, movieTitle, buttonFont);
        ActionListener listener = e -> {
            Window.getInstance().showPanel("MoviePage");
        };

        ActionListenerDecorator accountDecorator = new ActionListenerDecorator(movieButton, movieButton, listener);
        moviePanel.add(movieButton, BorderLayout.SOUTH);

        return moviePanel;
    }    
}
