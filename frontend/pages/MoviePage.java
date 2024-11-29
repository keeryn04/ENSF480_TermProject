package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import backend.Movie;
import backend.Showtime;
import frontend.controllers.AppState;
import frontend.controllers.MoviePageController;
import frontend.controllers.SeatMapPageController;
import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.MoviePageObserver;
import frontend.observers.SeatMapObserver;
import frontend.panels.FooterPanel;

public class MoviePage implements Page, MoviePageObserver, SeatMapObserver {
    private static MoviePage instance; // Singleton

    //Data fields for movie details
    private Integer movieId = 1;
    private String movieTitle = "Movie Title";
    private String movieDescription = "Movie Description";
    private BufferedImage posterImage;
    private String movieGenre = "Comedy";
    private String movieRating = "3.6";
    private String movieRuntime = "120";
    private Integer screenId = 1;
    private String releaseDate = "2020-10-31";

    //Date today
    LocalDate currentDate = LocalDate.now();

    //UI components
    private JLabel titleLabel;
    private JLabel posterLabel;
    private JTextArea descriptionArea;
    private JLabel screenTitle;
    private JLabel screenLabel;
    private JLabel timeTitle;
    private JComboBox<String> timeDropdown;
    private JLabel genreTitle;
    private JLabel genreLabel;
    private JLabel ratingTitle;
    private JLabel ratingLabel;
    private JLabel runtimeTitle;
    private JLabel runtimeLabel;

    private MoviePage() {
        //Initialize UI components
        Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
        Font dataTitleFont = new Font("Times New Roman", Font.BOLD, 20);
        Font dataFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font descriptionFont = new Font("Times New Roman", Font.BOLD, 18);

        titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieTitle, titleFont);

        posterLabel = new JLabel(); //Poster image

        descriptionArea = new JTextArea(movieDescription);
        descriptionArea.setFont(descriptionFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        screenTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Screen: ", dataTitleFont);
        screenLabel = DecoratorHelpers.makeLabel(Color.BLACK, String.valueOf(screenId), dataFont);

        timeTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Screening Time: ", dataTitleFont);
        timeDropdown = MoviePageController.getInstance().makeDropdownOfShowtimes();
        
        genreTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Genre: ", dataTitleFont);
        genreLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieGenre, dataFont);

        ratingTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Rating: ", dataTitleFont);
        ratingLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieRating, dataFont);

        runtimeTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Runtime: ", dataTitleFont);
        runtimeLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieRuntime, dataFont);
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
            JPanel headerPanel = DecoratorHelpers.createHeaderPanel();

            FooterPanel footerPanel;

            //ParsedReleaseDate
            LocalDate storedDate = LocalDate.parse(releaseDate);

            if(currentDate.isBefore(storedDate)){
                footerPanel = new FooterPanel("heldMovieTicket");
            }
            else{
                footerPanel = new FooterPanel("movieTicket");
            }

            //Title panel with titleLabel
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.add(titleLabel);

            //Poster panel with posterLabel
            JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            posterPanel.add(posterLabel);

            JPanel screenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            screenPanel.add(screenTitle);
            screenPanel.add(screenLabel);

            JPanel timesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            timesPanel.add(timeTitle);
            timesPanel.add(timeDropdown);

            //Rating panel (title and label)
            JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ratingPanel.add(ratingTitle);
            ratingPanel.add(ratingLabel);

            //Runtime panel (title and label)
            JPanel runtimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            runtimePanel.add(runtimeTitle);
            runtimePanel.add(runtimeLabel);

            //Genre panel (title and label)
            JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            genrePanel.add(genreTitle);
            genrePanel.add(genreLabel);

            //Add all detail panels to the detailsPanel
            JPanel detailsPanel = new PanelBuilder()
                    .setLayout(new GridLayout(6, 1))
                    .addComponent(screenPanel, null)
                    .addComponent(timesPanel, null)
                    .addComponent(ratingPanel, null)
                    .addComponent(runtimePanel, null)
                    .addComponent(genrePanel, null)
                    .addComponent(new JScrollPane(descriptionArea), null)
                    .build();

            //Add titlePanel, posterPanel, and detailsPanel to the main contentPanel
            JPanel contentPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(posterPanel, BorderLayout.CENTER)
                    .addComponent(detailsPanel, BorderLayout.SOUTH)
                    .build();

            //Use builder to add all panels in the main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(headerPanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Movie Page: %s%n", e.getMessage());
            return null;
        }
    }
}