package frontend.pages;
import frontend.decorators.ActionListenerDecorator;
import frontend.decorators.DecoratorHelpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
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
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);
            
            //Title panel
            JLabel titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Welcome to Acmeplex!", titleFont);
            JButton profileButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "My Profile", buttonFont);
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(
                profileButton, 
                profileButton, 
                e -> Window.getInstance().showPanel("Profile")
            );

            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.add(titleLabel);
            titlePanel.add(profileButton);
            
            //Movie selection panel
            JPanel movieSelectionPanel = new JPanel(new FlowLayout());
            movieSelectionPanel.add(DecoratorHelpers.createMoviePanel("./frontend/images/Venom.jpg", "Venom", Color.DARK_GRAY, buttonFont));
            movieSelectionPanel.add(DecoratorHelpers.createMoviePanel("./frontend/images/Venom.jpg", "Other Venom", Color.DARK_GRAY, buttonFont));
            movieSelectionPanel.add(DecoratorHelpers.createMoviePanel("./frontend/images/Venom.jpg", "This Venom", Color.DARK_GRAY, buttonFont));
            movieSelectionPanel.add(DecoratorHelpers.createMoviePanel("./frontend/images/Venom.jpg", "That Venom", Color.DARK_GRAY, buttonFont));

            //Combine all panels in main layout
            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(movieSelectionPanel, BorderLayout.CENTER)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }
}
