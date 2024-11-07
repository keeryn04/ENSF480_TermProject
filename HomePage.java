
import java.awt.BorderLayout;
import java.awt.Color;
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
            JButton homeButton = new JButton("Home");
            JLabel homeLabel = new JLabel("Home");

            //Decorate elements how you want it with decorator, then add with builder
            homeButton = (JButton) new BackgroundColorDecorator(homeButton, Color.RED).getDecoratedComponent();
            homeButton = (JButton) new ForegroundColorDecorator(homeButton, Color.WHITE).getDecoratedComponent();
            homeButton = (JButton) new SizeDecorator(homeButton, 50, 50).getDecoratedComponent();
            homeLabel = (JLabel) new TextDecorator(homeLabel, "Howdy").getDecoratedComponent();
            homeLabel = (JLabel) new SizeDecorator(homeLabel, 50, 50).getDecoratedComponent();

            //Build the panel with positioned components
            JPanel basicPanel = new PageBuilder()
                    .addComponent(homeLabel, BorderLayout.NORTH)
                    .addComponent(homeButton, BorderLayout.SOUTH)
                    .build();

            return basicPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }
}
