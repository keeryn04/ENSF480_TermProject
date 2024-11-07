
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**Makes the HomePage to be displayed with Window*/
public class HomePage implements Page {

    /**Creates the Homepage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {
            //Decorate button how you want it with decorator, then add with builder
            JButton homeButton = new JButton("Home");
            homeButton = new ButtonColorDecorator(homeButton, Color.BLUE);
            homeButton = new ButtonSizeDecorator(homeButton, 10, 20);

            //Decorate label how you want it, then add with builder
            JLabel homeLabel = new JLabel("Home");
            homeLabel = new LabelTextDecorator(homeLabel, "Howdy");
            
            //Use builder to add premade elements to the page
            JPanel basicPanel = new PageBuilder() 
                    .addLabel(homeLabel)
                    .addButton(homeButton)
                    .build();
            
            //Panel decorators don't need builder as it applies directly to page
            JPanel decoratedPanel = new BackgroundDecorator(basicPanel, Color.BLUE);
            decoratedPanel = new BorderDecorator(decoratedPanel, Color.RED, 10);

            return decoratedPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }
}
