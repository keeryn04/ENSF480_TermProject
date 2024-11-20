package frontend.pages;
import frontend.decorators.DecoratorHelpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**Makes the HomePage to be displayed with Window*/
public class HomePage implements Page {

    /**Creates the Homepage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators to add more functionality to those aspects.
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
            JPanel movieSelectionPanel = new JPanel(new FlowLayout());
            movieSelectionPanel.setBackground(Color.WHITE);
            
            //BELOW DATA REPLACES API, NOT FULLY WORKING BUT IMPLEMENTATION WITH COMPONENTS IS CORRECT AND WOULD WORK WITH API
            //Movie data (title, poster path, description) UPDATE WITH API CALL
            Map<String, String[]> movies = new HashMap<>();
            movies.put("Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Venom"});
            movies.put("Other Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Other Venom"});
            movies.put("This Venom", new String[]{"./frontend/images/Venom.jpg", "Description for This Venom"});
            movies.put("That Venom", new String[]{"./frontend/images/Venom.jpg", "Description for That Venom"});

            Map<String, String[]> screens = new HashMap<>();
            screens.put("Venom", new String[]{"5", "10"});
            screens.put("Other Venom", new String[]{"1", "1"});
            screens.put("This Venom", new String[]{"10", "10"});
            screens.put("That Venom", new String[]{"8", "8"});

            // Loop through movies to create panels
            for (Map.Entry<String, String[]> entry : movies.entrySet()) {
                String movieTitle = entry.getKey();
                String[] movieDetails = entry.getValue();

                //Create movie panel
                JPanel moviePanel = DecoratorHelpers.createMoviePanel(movieTitle, movieDetails[1], movieDetails[0], Color.DARK_GRAY, buttonFont);

                //Check if this movie has a screen configuration
                if (screens.containsKey(movieTitle)) {
                    String[] screenDetails = screens.get(movieTitle);
                    int rows = Integer.parseInt(screenDetails[0]);
                    int cols = Integer.parseInt(screenDetails[1]);

                    //TEMP IMPLEMENTATION, NOT WORKING, API NEEDED (Currently resets variables in temp database with last in for loop)
                    AppState appState = AppState.getInstance();
                    appState.setSeatMapTitle(movieTitle);
                    appState.setMovieTitle(movieTitle);
                    appState.setSeatRows(rows);
                    appState.setSeatCols(cols);
                }

                //Add movie panel to the UI
                movieSelectionPanel.add(moviePanel);
            }

            //Use builder to add all panels in main layout
            JPanel mainPanel = new PageBuilder()
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
}
