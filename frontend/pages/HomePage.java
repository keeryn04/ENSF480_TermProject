package frontend.pages;
import frontend.decorators.DecoratorHelpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

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
        Map<String, String[]> movies = appState.getMovies();
        Map<Integer, Integer[]> screens = appState.getScreens();
        Map<String, Integer> movieScreenMapping = new HashMap<>();
        Integer screenNumber = 1;

        //Map movies to screens
        for (Map.Entry<String, String[]> entry : movies.entrySet()) {
            String movieTitle = entry.getKey();
            movieScreenMapping.put(movieTitle, screenNumber); //Movie is on screen
            screenNumber++;
        }

        //Make movie panel and seatmap for each movie / screen
        for (Map.Entry<String, String[]> entry : movies.entrySet()) {
            String movieTitle = entry.getKey();
            String[] movieDetails = entry.getValue();
            String imagePath = movieDetails[0];
            String movieDesc = movieDetails[1];
            String movieGenre = movieDetails[2];
            String movieRating = movieDetails[3];
            String movieRuntime = movieDetails[4];

            //Create movie panel
            JPanel moviePanel = MoviePage.createMoviePanel(movieTitle, movieDesc, imagePath, Color.DARK_GRAY, buttonFont);
            JButton movieButton = (JButton) moviePanel.getComponent(1);
            ActionListener listener = e -> {
                //Update Movie Data in Movie State
                MovieState.getInstance().setMovieTitle(movieTitle);
                MovieState.getInstance().setMovieDetails(movieDesc);
                MovieState.getInstance().setMoviePoster(imagePath);
                MovieState.getInstance().setMovieGenre(movieGenre);
                MovieState.getInstance().setMovieRating(movieRating);
                MovieState.getInstance().setMovieRuntime(movieRuntime);

                Integer screenNum = movieScreenMapping.get(movieTitle); //Screen assigned to each movie
                if (screenNum != null) {
                    Integer[] screenDetails = screens.get(screenNum);
                    Integer rows = screenDetails[0];
                    Integer cols = screenDetails[1];
                    
                    //Set the seat rows and columns in SeatMapState
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
