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
import frontend.pages.PanelBuilder;
import frontend.pages.Window;

public class DecoratorHelpers {
    /**
     * Makes a button with the established decorators
     * 
     * @param backgroundColor Changes the background color of the component
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text            Text on the cmp
     * @param font            The font of the text on the cmp
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

    /**
     * Makes a label with the established decorators
     * 
     * @param foregroundColor Changes the foreground color of cmp (Text color)
     * @param text            Text on the cmp
     * @param font            The font of the text on the cmp
     */
    public static JLabel makeLabel(Color foregroundColor, String text, Font font) {
        JLabel label = new JLabel(text);
        label = (JLabel) new ForegroundColorDecorator(label, foregroundColor).getDecoratedComponent();
        label = (JLabel) new TextDecorator(label, text).getDecoratedComponent();
        label = (JLabel) new FontDecorator(label, font).getDecoratedComponent();

        return label;
    }

    /**
     * Makes a textfield with the established decorators
     * 
     * @param size The size of the editable text area
     * @param font The font of the text in the cmp
     * @param text The text you want to autofill the field with
     */
    public static JTextField makeTextField(int size, Font font, String text) {
        JTextField textField = new JTextField(size);
        textField = (JTextField) new FontDecorator(textField, font).getDecoratedComponent();
        if (text != null) {
            textField.setText(text);
        }
        return textField;
    }

    /**
     * Makes a textfield with the established decorators, and a label for that text
     * field with decorators
     * 
     * @param labelColor       The color of the label for the text area
     * @param labelText        The text of the label for the text area
     * @param font             The font of the label and text in the cmp
     * @param textFieldColumns The size of the editable text area
     * @param autofillText     If you want text to appear in the input area, can be
     *                         null
     * @param preferredSize    If you want a specific size of the labeled field
     */

    public static JPanel makeLabeledField(Color labelColor, String labelText, Font font, int textFieldColumns,
            String autofillText, Dimension preferredSize) {
        JLabel label = makeLabel(labelColor, labelText, font);
        JTextField textField = makeTextField(textFieldColumns, font, autofillText);
        textField.setName(labelText);
        if (preferredSize != null) {
            textField.setMaximumSize(preferredSize);
        }
        if (autofillText != null) {
            textField.setText(autofillText);
        }
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    /** Makes the header that is used on every page. Has logo and profile button */
    @SuppressWarnings("unused")
    public static JPanel createHeaderPanel() { //
        // Fonts
        Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

        // Main Title Label
        JLabel titleLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Acmeplex", titleFont);
        titleLabel = (JLabel) new BackgroundColorDecorator(titleLabel, Color.LIGHT_GRAY).getDecoratedComponent();

        // Profile Button
        JButton profileButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "My Profile", buttonFont);
        ActionListener listener = e -> {
            Window.getInstance().showPanel("ProfilePage");
        };
        ActionListenerDecorator accountDecorator = new ActionListenerDecorator(profileButton, profileButton, listener);

        // Use builder to add all panels in main layout
        JPanel titlePanel = new PanelBuilder()
                .setLayout(new BorderLayout())
                .addComponent(titleLabel, BorderLayout.CENTER)
                .addComponent(profileButton, BorderLayout.EAST)
                .build();

        JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(titlePanel, Color.LIGHT_GRAY)
                .getDecoratedComponent();

        return decoratedPanel;
    }
}