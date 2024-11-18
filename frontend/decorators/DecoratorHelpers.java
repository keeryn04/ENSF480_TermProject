package frontend.decorators;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingConstants;

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
}
