package frontend.pages;
import frontend.decorators.ActionListenerDecorator;
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

    /**Creates the Homepage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);
            
            //Movie data (title, poster path, description) UPDATE WITH API CALL
            Map<String, String[]> movies = new HashMap<>();
            movies.put("Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Venom"});
            movies.put("Other Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Other Venom"});
            movies.put("This Venom", new String[]{"./frontend/images/Venom.jpg", "Description for This Venom"});
            movies.put("That Venom", new String[]{"./frontend/images/Venom.jpg", "Description for That Venom"});

            //Panels
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel contentPanel = new JPanel(new BorderLayout());
            JPanel movieSelectionPanel = new JPanel(new FlowLayout());
            movieSelectionPanel.setBackground(Color.WHITE);

            //Loop through movies and create panels
            for (Map.Entry<String, String[]> entry : movies.entrySet()) {
                String movieTitle = entry.getKey();
                String[] movieDetails = entry.getValue();

                //Create movie panel
                JPanel moviePanel = DecoratorHelpers.createMoviePanel(movieDetails[0], movieTitle, Color.DARK_GRAY, buttonFont);

                //Get the button from the moviePanel and add an action listener
                JButton movieButton = (JButton) ((BorderLayout) moviePanel.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
                ActionListener listener = e -> {
                    MoviePage.getInstance().updateContent(movieTitle, movieDetails[0], movieDetails[1]);
                    Window.getInstance().showPanel("MoviePage");
                };

                ActionListenerDecorator accountDecorator = new ActionListenerDecorator(movieButton, movieButton, listener);

                //Add to the movie selection panel
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
