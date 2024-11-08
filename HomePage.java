
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
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
            Font font = new Font("Times New Roman", Font.PLAIN, 30);
            JButton homeButton = makeButton(Color.RED, Color.WHITE, "HIIIII", font);
            JLabel homeLabel = makeLabel(Color.ORANGE, "Howdy", font);

            //Build the panel with positioned components
            JPanel basicPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(homeLabel, BorderLayout.NORTH)
                    .addComponent(homeButton, BorderLayout.SOUTH)
                    .build();

            return basicPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Makes a button with the established decorators
     * @param backgroundColor Changes the background color of the component
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text Text on the cmp
     * @param font The font of the text on the cmp
     */
    private JButton makeButton(Color backgroundColor, Color foregroundColor, String text, Font font) {
        JButton button = new JButton(text);
        button = (JButton) new BackgroundColorDecorator(button, backgroundColor).getDecoratedComponent();
        button = (JButton) new ForegroundColorDecorator(button, foregroundColor).getDecoratedComponent();
        button = (JButton) new TextDecorator(button, text).getDecoratedComponent();
        button = (JButton) new FontDecorator(button, font).getDecoratedComponent();

        return button;
    }

    /**Makes a labe with the established decorators
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text Text on the cmp
     * @param font The font of the text on the cmp
     */
    private JLabel makeLabel(Color foregroundColor, String text, Font font) {
        JLabel label = new JLabel(text);
        label = (JLabel) new ForegroundColorDecorator(label, foregroundColor).getDecoratedComponent();
        label = (JLabel) new TextDecorator(label, text).getDecoratedComponent();
        label = (JLabel) new FontDecorator(label, font).getDecoratedComponent();

        return label;
    }
}
