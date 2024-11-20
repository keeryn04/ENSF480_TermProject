package frontend.decorators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
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

import frontend.pages.AppState;
import frontend.pages.MoviePage;
import frontend.pages.PageBuilder;
import frontend.pages.SeatMapPage;
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
    public static JPanel createMoviePanel(String movieTitle, String movieDesc, String imagePath, Color buttonColor, Font buttonFont) {
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
        JButton movieButton = makeButton(buttonColor, Color.WHITE, movieTitle, buttonFont);
        ActionListener listener = e -> {

            //Update Movie title, Movie poster path, Movie description
            MoviePage.getInstance().updateContent(movieTitle, movieDesc, imagePath);
            Window.getInstance().showPanel("MoviePage");
        };

        ActionListenerDecorator accountDecorator = new ActionListenerDecorator(movieButton, movieButton, listener);
        moviePanel.add(movieButton, BorderLayout.SOUTH);

        return moviePanel;
    }

    /**Makes the header that is used on every page. Has logo and profile button */
    @SuppressWarnings("unused")
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
     * confirmInfo for confirming edits of personal info
     * editInfo for going to the editing page of profile
     */
    @SuppressWarnings("unused")
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

            ActionListener listener = e -> {
                AppState appState = AppState.getInstance();

                //Create a new instance of SeatMapPage, API NEEDED
                String seatMapTitle = appState.getSeatMapTitle();
                int seatRows = appState.getSeatRows();
                int seatCols = appState.getSeatCols();
                String movieTitle = appState.getMovieTitle();

                //Create a new SeatMapPage instance for the selected movie
                SeatMapPage seatMapPage = new SeatMapPage(seatMapTitle, seatRows, seatCols);
                
                //Add the new SeatMapPage panel to the window and display it
                JPanel seatMapPanel = seatMapPage.createPage(); 
                Window.getInstance().addPanel(movieTitle, seatMapPanel);

                //Show the SeatMapPage
                Window.getInstance().showPanel(movieTitle);
            };

            //Add listener to button with the action decorator
            ActionListenerDecorator accountDecorator = new ActionListenerDecorator(rightButton, rightButton, listener);

        } else if (type.equals("confirmPurchase")) { //Used for final ticket purchase purposes
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Confirm Purchase", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("PurchaseSuccessPage")
            );
        } else if (type.equals("confirmInfo")) { //Used for confirming edited info
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Confirm", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("ProfilePage")
            );
        } else if (type.equals("editInfo")) { //Used for editing info
            rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Edit Info", buttonFont);
            ActionListenerDecorator accountDecoratorRight = new ActionListenerDecorator(
                rightButton, 
                rightButton, 
                e -> Window.getInstance().showPanel("ProfileEditPage")
            );
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

    /**Creates a seatmap panel with seat colour changing interactions
     * @param rows Used to specifiy the rows of theatre
     * @param cols Specifiy the columns of theatre
     */
    @SuppressWarnings("unused")
    public static JPanel createSeatMapPanel(int rows, int cols) {
        JButton[][] seats = new JButton[rows][cols];

        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(rows, cols, 10, 10));

        Font seatFont = new Font("Times New Roman", Font.PLAIN, 12);
        Font screenFont = new Font("Times New Roman", Font.BOLD, 20);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton seatButton = DecoratorHelpers.makeButton(Color.LIGHT_GRAY, Color.BLACK, "Seat " + (row * cols + col + 1), seatFont);

                //Add color changing to each seat
                ActionListener seatActionListener = e -> {
                    if (seatButton.getBackground().equals(Color.LIGHT_GRAY)) {
                        seatButton.setBackground(Color.GREEN); //Selected
                    } else {
                        seatButton.setBackground(Color.LIGHT_GRAY); //Deselected
                    }
                };
    
                ActionListenerDecorator actionListenerDecorator = new ActionListenerDecorator(seatButton, seatButton, seatActionListener);

                seats[row][col] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        resetSeats(seats);
        System.out.println("Seats Reset");

        JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(seatPanel, Color.DARK_GRAY).getDecoratedComponent();
        decoratedPanel = (JPanel) new BorderDecorator(decoratedPanel, Color.DARK_GRAY, 10).getDecoratedComponent();

        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(Color.BLACK); 
        JLabel screenLabel = DecoratorHelpers.makeLabel(Color.WHITE, "Screen", screenFont);
        screenLabel = (JLabel) new BackgroundColorDecorator(screenLabel, Color.BLACK).getDecoratedComponent();
        screenPanel.add(screenLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(decoratedPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /**Resets colour of seat buttons on seatmap
     * @param seats Vector of seatmap with JButton elements
     */
    public static void resetSeats(JButton[][] seats) { //WILL NEED TO BE UPDATED WITH API CALLS TO MARK TAKEN SPOTS
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] != null) {
                    seats[row][col].setBackground(Color.LIGHT_GRAY); //Reset the color to grey
                }
            }
        }
    }
}
