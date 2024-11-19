package frontend.decorators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import frontend.pages.PageBuilder;
import frontend.pages.Window;

public class DecoratorHelpers {
    /**Makes a button with the established decorators
     * @param backgroundColor Changes the background color of the component
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text Text on the cmp
     * @param font The font of the text on the cmp
     */
    public static JButton makeButton(Color backgroundColor, Color foregroundColor, String text, Font font) {
        JButton button = new JButton(text);
        button = (JButton) new BackgroundColorDecorator(button, backgroundColor).getDecoratedComponent();
        button = (JButton) new ForegroundColorDecorator(button, foregroundColor).getDecoratedComponent();
        button = (JButton) new TextDecorator(button, text).getDecoratedComponent();
        button = (JButton) new FontDecorator(button, font).getDecoratedComponent();
        button = (JButton) new ActionListenerDecorator(button, button, null).getDecoratedComponent();

        return button;
    }

    /**Makes a label with the established decorators
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text Text on the cmp
     * @param font The font of the text on the cmp
     */
    public static JLabel makeLabel(Color foregroundColor, String text, Font font) {
        JLabel label = new JLabel(text);
        label = (JLabel) new ForegroundColorDecorator(label, foregroundColor).getDecoratedComponent();
        label = (JLabel) new TextDecorator(label, text).getDecoratedComponent();
        label = (JLabel) new FontDecorator(label, font).getDecoratedComponent();

        return label;
    }

    /**Makes a textfield with the established decorators
     * @param size The size of the editable text area
     * @param font The font of the text in the cmp
     */
    public static JTextField makeTextField(int size, Font font) {
        JTextField textField = new JTextField(size);
        textField = (JTextField) new FontDecorator(textField, font).getDecoratedComponent();
        return textField;
    }

    /**Makes a textfield with the established decorators, and a label for that text field with decorators
     * @param labelColor The color of the label for the text area
     * @param labelText The text of the label for the text area
     * @param font The font of the label and text in the cmp
     * @param textFieldColumns The size of the editable text area
     */
    public static JPanel makeLabeledField(Color labelColor, String labelText, Font font, int textFieldColumns, Dimension preferredSize) {
        JLabel label = makeLabel(labelColor, labelText, font);
        JTextField textField = makeTextField(textFieldColumns, font);
        if (preferredSize != null) {
            textField.setMaximumSize(preferredSize);
        }
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    /**Make a movie panel to add to the main content
     * @param imagePath The file path of the movie poster
     * @param title The text to add to the button with the movie
     * @param buttonColor Color of the button
     * @param buttonFont Font of the button
    */
    public static JPanel createMoviePanel(String imagePath, String title, Color buttonColor, Font buttonFont) {
        JPanel moviePanel = new JPanel(new BorderLayout());

        //Load the image
        try {
            BufferedImage movieImage = ImageIO.read(new File(imagePath));
            Image scaledImage = movieImage.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            moviePanel.add(picLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Image not found", SwingConstants.CENTER);
            moviePanel.add(errorLabel, BorderLayout.CENTER);
        }

        //Create button with specified color and font
        JButton movieButton = makeButton(buttonColor, Color.WHITE, title, buttonFont);
        moviePanel.add(movieButton, BorderLayout.SOUTH);

        return moviePanel;
    }

    public static JPanel createHeaderPanel() {
            //Title panel
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);
            JLabel titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Acmeplex", titleFont);
            titleLabel = (JLabel) new BackgroundColorDecorator(titleLabel, Color.LIGHT_GRAY).getDecoratedComponent();
            JButton profileButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "My Profile", buttonFont);
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator( //CHANGE TO HANDLE SIGN IN STATUS, REDIRECT ACCORDINGLY
                profileButton, 
                profileButton, 
                e -> Window.getInstance().showPanel("ProfilePage")
            );

            //Use builder to add all panels in main layout
            JPanel titlePanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titleLabel, BorderLayout.WEST)
                    .addComponent(profileButton, BorderLayout.EAST)
                    .build();
            
            JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(titlePanel, Color.LIGHT_GRAY).getDecoratedComponent();

            return decoratedPanel;
    }

    /**Creates a footer panel that can be used on various pages
     * @param type Used to specifiy the type of footer required. 
     * Options: movieTicket for Purchase a Ticket,
     * confirmPurchase for final purchase confirmation,
     * confirmInfo for editing personal info confirmation
     */
    public static JPanel createFooterPanel(String type) {
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);
        JButton rightButton = new JButton();
        JButton backButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Back to Home", buttonFont);
        ActionListenerDecorator accountDecoratorHome = new ActionListenerDecorator(
            backButton, 
            backButton, 
            e -> Window.getInstance().showPanel("Home")
        );

        if (type.equals("movieTicket")) { //Used on movie page to go to ticket buying
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Purchase a Ticket", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("TicketPage")
            );

        } else if (type.equals("confirmPurchase")) { //Used for final ticket purchase purposes
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Confirm Purchase", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("PurchaseSuccessPage")
            );
        } else if (type.equals("confirmInfo")) { //Used for confirming seat position, edited info, etc.
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Confirm", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("ProfilePage")
            );
        } else if (type.equals("editInfo")) { //Used for confirming seat position, edited info, etc.
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Edit Info", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("ProfileEditPage")
            );
        } else { //No right button required, default
            JPanel titlePanel = new PageBuilder()
            .setLayout(new BorderLayout())
            .addComponent(backButton, BorderLayout.WEST)
            .build();

            JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(titlePanel, Color.LIGHT_GRAY).getDecoratedComponent();

            return decoratedPanel;
        }

        //Use builder to add all panels in main layout
        JPanel titlePanel = new PageBuilder()
                .setLayout(new BorderLayout())
                .addComponent(backButton, BorderLayout.WEST)
                .addComponent(rightButton, BorderLayout.EAST)
                .build();
        
        JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(titlePanel, Color.LIGHT_GRAY).getDecoratedComponent();

        return decoratedPanel;
    }
}
