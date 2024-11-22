package frontend.decorators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

    /**Makes the header that is used on every page. Has logo and profile button */
    @SuppressWarnings("unused")
    public static JPanel createHeaderPanel() { //
            //Fonts
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

            //Main Title Label
            JLabel titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Acmeplex", titleFont);
            titleLabel = (JLabel) new BackgroundColorDecorator(titleLabel, Color.LIGHT_GRAY).getDecoratedComponent();

            //Profile Button
            JButton profileButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "My Profile", buttonFont);
            ActionListener listener = e -> {Window.getInstance().showPanel("ProfilePage");};
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(profileButton, profileButton, listener);

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
     * confirmInfo for confirming edits of personal info
     * editInfo for going to the editing page of profile
     */
    @SuppressWarnings("unused")
    public static JPanel createFooterPanel(String type) {
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

        JButton rightButton = new JButton(); //Customizable button for various footers
        JButton backButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Back to Home", buttonFont); //Return to home
        ActionListener listenerHome = e -> {Window.getInstance().showPanel("Home");};
        ActionListenerDecorator accountDecoratorHome = new ActionListenerDecorator(backButton, backButton, listenerHome);

        if (type.equals("movieTicket")) { //Used on MoviePage, traverse to SeatMapPage
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Purchase a Ticket", buttonFont);
            ActionListener listener = e -> {Window.getInstance().showPanel("SeatMapPage");};
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(rightButton, rightButton, listener);

        } else if (type.equals("continuePurchase")) { //Used on SeatMapPage, traverse to PaymentPage
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Continue to Payment", buttonFont);
            ActionListener listener = e -> {Window.getInstance().showPanel("PaymentPage");};
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(rightButton, rightButton, listener);

        } else if (type.equals("confirmInfo")) { //Used for confirming edited info
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Confirm", buttonFont);
            ActionListener listener = e -> {Window.getInstance().showPanel("ProfilePage");};
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(rightButton, rightButton, listener);

        } else if (type.equals("editInfo")) { //Used to edit profile info
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Edit Info", buttonFont);
            ActionListener listener = e -> {Window.getInstance().showPanel("ProfileEditPage");};
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(rightButton, rightButton, listener);
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