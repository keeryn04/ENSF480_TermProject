package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.time.LocalDate;

import backend.Movie;
import frontend.controllers.MoviePageController;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.MoviePageObserver;
import frontend.panels.FooterPanel;

public class MoviePage implements Page, MoviePageObserver {
    private static MoviePage instance; // Singleton

    //Date today
    LocalDate currentDate = LocalDate.now();

    private JPanel mainPanel;
    private JPanel timesPanel;

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

    private JPanel headerPanel;
    private JPanel footerPanel;

    private MoviePage() { 
        MoviePageController.getInstance().addMovieObserver(this); //Tie MoviePage to MoviePageController
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
            //Fonts
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font dataTitleFont = new Font("Times New Roman", Font.BOLD, 20);
            Font dataFont = new Font("Times New Roman", Font.PLAIN, 18);
            Font descriptionFont = new Font("Times New Roman", Font.BOLD, 18);

            //Make header and footer
            headerPanel = DecoratorHelpers.createHeaderPanel();
            footerPanel = new JPanel();
    
            titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Title", titleFont);
    
            posterLabel = new JLabel(); //Poster image
    
            descriptionArea = new JTextArea("Movie Description");
            descriptionArea.setFont(descriptionFont);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);
    
            screenTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Screen: ", dataTitleFont);
            screenLabel = DecoratorHelpers.makeLabel(Color.BLACK, "0", dataFont);
    
            timeTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Screening Time: ", dataTitleFont);
            timeDropdown = new JComboBox<>();
            
            genreTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Genre: ", dataTitleFont);
            genreLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Genre", dataFont);
    
            ratingTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Rating: ", dataTitleFont);
            ratingLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Rating", dataFont);
    
            runtimeTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Runtime: ", dataTitleFont);
            runtimeLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Movie Runtime", dataFont);

            //Title panel with titleLabel
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.add(titleLabel);

            //Poster panel with posterLabel
            JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            posterPanel.add(posterLabel);

            //Screen Panel
            JPanel screenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            screenPanel.add(screenTitle);
            screenPanel.add(screenLabel);

            //Time Dropdown Panel
            timesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
            mainPanel = new PanelBuilder()
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

    @Override
    public void onMovieSelected(Object value) {
        Movie currentMovie = (Movie) value;
        JComboBox<String> newDropdown = MoviePageController.getInstance().makeDropdownOfShowtimes(); //Make dropdown based on movie selected
        timesPanel.remove(timeDropdown); 
        timesPanel.add(newDropdown);    
        timeDropdown = newDropdown; //Update dropdown accordingly

        titleLabel.setText(currentMovie.getTitle());
        descriptionArea.setText(currentMovie.getDescription());
        posterLabel.setIcon(new ImageIcon(currentMovie.getPosterPath()));
        genreLabel.setText(currentMovie.getGenre());
        ratingLabel.setText(String.valueOf(currentMovie.getRating()));
        runtimeLabel.setText(String.valueOf(currentMovie.getRuntime()));

        LocalDate storedDate = LocalDate.parse(currentMovie.getReleaseDate());

        if(currentDate.isBefore(storedDate)){
            footerPanel = new FooterPanel("heldMovieTicket");
        }
        else{
            footerPanel = new FooterPanel("movieTicket");
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}