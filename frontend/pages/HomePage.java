package frontend.pages;
import frontend.decorators.DecoratorHelpers;
import frontend.states.AppState;
import frontend.states.MovieState;
import frontend.states.SeatMapState;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import backend.Movie;
import backend.Screen;

/**Makes the HomePage to be displayed with Window*/
public class HomePage implements Page {
    JPanel movieSelectionPanel;

    public HomePage() {
        movieSelectionPanel = new JPanel(new FlowLayout());
        movieSelectionPanel.setBackground(Color.WHITE);
    }

    /**Creates the Homepage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @SuppressWarnings("unused")
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

            //Panels
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel contentPanel = new JPanel(new BorderLayout());

            //Assign data to pages based on movie chosen
            populateMovieData(buttonFont);

            //Use builder to add all panels in main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(movieSelectionPanel, BorderLayout.CENTER)
                    .addComponent(contentPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Creates movie panels for HomePage based on data in Appstate */
    private void populateMovieData(Font buttonFont) {
        movieSelectionPanel.removeAll(); //Clear previous panels

        AppState appState = AppState.getInstance();

        //Get movie data from AppState
        Map<Integer, Movie> movies = appState.getMovies();
        Map<Integer, Screen> screens = appState.getScreens();
        Map<Integer, Integer> movieScreenMapping = new HashMap<>();
        Integer screenNumber = 1;

        //Map movies to screens
        for (Map.Entry<Integer, Movie> entry : movies.entrySet()) {
            Integer movieId = entry.getKey();
            movieScreenMapping.put(movieId, screenNumber); 
            screenNumber++;
        }


        //Make movie panel and seatmap for each movie / screen
        for (Map.Entry<Integer, Movie> entry : movies.entrySet()) {
            Integer movieId = entry.getKey();
            Movie movieDetails = entry.getValue();
            String movieTitle = movieDetails.getTitle();
            String imagePath = movieDetails.getPosterPath();
            String movieDesc = movieDetails.getDescription();
            String movieGenre = movieDetails.getGenre();
            String movieRating = movieDetails.getRating();
            String movieRuntime = movieDetails.getDuration();

            //Create movie panel
            JPanel moviePanel = MoviePage.createMoviePanel(movieTitle, movieDesc, imagePath, Color.DARK_GRAY, buttonFont);
            JButton movieButton = (JButton) moviePanel.getComponent(1);
            ActionListener listener = e -> {
                //Update Movie Data in Movie State
                MovieState.getInstance().setMovieId(movieId);
                MovieState.getInstance().setMovieTitle(movieTitle);
                MovieState.getInstance().setMovieDetails(movieDesc);
                MovieState.getInstance().setMoviePoster(imagePath);
                MovieState.getInstance().setMovieGenre(movieGenre);
                MovieState.getInstance().setMovieRating(movieRating);
                MovieState.getInstance().setMovieRuntime(movieRuntime);

                Integer screenNum = movieScreenMapping.get(movieId); //Screen assigned to each movie
                if (screenNum != null) {
                    Screen screenDetails = screens.get(screenNum);
                    Integer rows = screenDetails.getRows();
                    Integer cols = screenDetails.getCols();
                    
                    //Set the seat rows and columns in SeatMapState
                    SeatMapState.getInstance().setScreenId(screenNum);
                    SeatMapState.getInstance().setSeatRows(rows);
                    SeatMapState.getInstance().setSeatCols(cols);
                }
            };
        
            movieButton.addActionListener(listener);

            //Add movie panel to the UI
            movieSelectionPanel.add(moviePanel);
        }

        //Remake the panel
        movieSelectionPanel.revalidate();
        movieSelectionPanel.repaint();
    }
}