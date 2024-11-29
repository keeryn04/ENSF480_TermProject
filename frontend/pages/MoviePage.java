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
import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.MoviePageObserver;
import frontend.observers.SeatMapObserver;
import frontend.panels.FooterPanel;
import frontend.states.AppState;
import frontend.states.UserState;
import frontend.states.MovieState;
import frontend.states.SeatMapState;

public class MoviePage implements Page, MoviePageObserver, SeatMapObserver {
    private static MoviePage instance; // Singleton

    boolean isUserRegistered = false;

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
        timeDropdown = new JComboBox<>();
        
        genreTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Genre: ", dataTitleFont);
        genreLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieGenre, dataFont);

        ratingTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Rating: ", dataTitleFont);
        ratingLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieRating, dataFont);

        runtimeTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Runtime: ", dataTitleFont);
        runtimeLabel = DecoratorHelpers.makeLabel(Color.BLACK, movieRuntime, dataFont);

        //Register with MovieState and SeatMapState
        MovieState.getInstance().addMovieObserver(this);
        SeatMapState.getInstance().addSeatMapObserver(this);
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
            boolean falseUserInfo = false;

            //ParsedReleaseDate
            LocalDate storedDate = LocalDate.parse(releaseDate);
            
            try {
                // Attempt to check the registered status
                isUserRegistered = UserState.getInstance().isUserRegistered();
            } catch (Exception e) {
                // Handle the exception and log it
                System.out.println("Error checking user registered status: " + e.getMessage());
                // Fallback to default value
                falseUserInfo = true;
            }

            if(currentDate.isBefore(storedDate) && (isUserRegistered == false)){
                footerPanel = new FooterPanel("heldMovieTicket");
            }
            else if (currentDate.isAfter(storedDate) && (falseUserInfo == false)) {
                footerPanel = new FooterPanel("movieTicket");
            }
            else {
                footerPanel = new FooterPanel("missingAccount");
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

            //Times dropdown panel (start and end), load data then add component
            loadShowtimesDropdowns(movieId);

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

    /**Update movie data based on MovieState data */
    @Override
    public void onMovieSelected(String key, Object value) {
        //React to changes from AppState
        switch (key) {
            case "movieId":
                movieId = (Integer) value;
                updateContent();
                updateDropdown();
                break;
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
            case "movieGenre":
                movieGenre = (String) value;
                updateContent();
                break;
            case "movieRating":
                movieRating = (String) value;
                updateContent();
                break;
            case "movieRuntime":
                movieRuntime = (String) value;
                updateContent();
                break;
            case "releaseDate":
                releaseDate = (String) value;
                updateContent();
                break;
            default:
                break;
        }
    }

    /**Change the aspects of the MoviePage based on App data retrieved by observer */
    private void updateContent() {
        SwingUtilities.invokeLater(() -> {
            titleLabel.setText(movieTitle);
            descriptionArea.setText(movieDescription);
            screenLabel.setText(String.valueOf(screenId));

            if (posterImage != null) {
                Image scaledImage = posterImage.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                posterLabel.setText("Image not found");
                posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }

            genreLabel.setText(movieGenre);
            ratingLabel.setText(movieRating);
            runtimeLabel.setText(movieRuntime);
        });
    }

    //Separate update for heavy processing in comparison to changing text
    private void updateDropdown() {
        loadShowtimesDropdowns(movieId);
    }

    @Override
    public void onSeatMapUpdate(String key, Object value) {
        switch (key) {
            case "screenId":
                screenId = (Integer) value;
                updateContent();
                break;
            default:
                break;
        }
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
            Image scaledImage = movieImage.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
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

    private List<String> getShowtimesForMovie(Integer movieId) {
        Map<Integer, Showtime> showtimes = AppState.getInstance().getShowtimes();
        List<String> filteredShowtimes = new ArrayList<>();

        Movie movie = AppState.getInstance().getMovies().get(movieId);
        Integer runtimeMinutes = movie != null ? movie.getDurationInt() : 0;

        for (Showtime showtime : showtimes.values()) {
            if (showtime.getMovieId().equals(movieId)) {
                // Format start and end times for the dropdown
                filteredShowtimes.add(showtime.getFormattedScreeningTime("yyyy-MM-dd HH:mm", runtimeMinutes));
            }
        }

        return filteredShowtimes;
    }


    private void loadShowtimesDropdowns(Integer movieId) {
        List<String> showtimes = getShowtimesForMovie(movieId);
    
        timeDropdown.removeAllItems();
    
        if (showtimes.isEmpty()) {
            timeDropdown.addItem("No Showtimes Available");
        } else {
            for (String showtime : showtimes) {
                timeDropdown.addItem(showtime);
            }
        }

        timeDropdown.addActionListener(e -> {
            //Get the selected item
            String selectedShowtime = (String) timeDropdown.getSelectedItem();
        
            //Ensure it's not null or the placeholder text
            if (selectedShowtime != null && !selectedShowtime.equals("No Showtimes Available")) {
                handleDropdownChange(selectedShowtime);
            }
        });
    }

    private void handleDropdownChange(String selectedShowtime) {
        //Extract details if the showtime includes runtime or screen info
        System.out.println("Selected Showtime: " + selectedShowtime);
    
        // Optionally, update the screenId or other movie-related data
        Showtime selectedShowtimeData = findShowtimeData(selectedShowtime);
        if (selectedShowtimeData != null) {
            screenId = selectedShowtimeData.getScreenId();
            int showtimeId = selectedShowtimeData.getShowtimeId(); //Local as its not displayed anywhere, just stored for db access
            MovieState.getInstance().setShowtimeId(showtimeId);
            updateContent(); //Refresh content on the page
        }
    }
    
    private Showtime findShowtimeData(String selectedShowtime) {
        Map<Integer, Showtime> showtimes = AppState.getInstance().getShowtimes();
    
        // Loop through showtimes to find the matching one
        for (Showtime showtime : showtimes.values()) {
            String formattedTime = showtime.getFormattedScreeningTime("yyyy-MM-dd HH:mm", movieRuntime != null ? Integer.parseInt(movieRuntime) : 0);
            if (formattedTime.equals(selectedShowtime)) {
                return showtime;
            }
        }
    
        return null; // No matching showtime found
    }
    
}