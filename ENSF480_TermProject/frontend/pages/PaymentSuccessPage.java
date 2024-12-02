package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import backend.DatabaseAccessor;
import javax.swing.JPanel;

import backend.User;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.BorderDecorator;
import frontend.observers.ProfilePageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.MovieState;
import frontend.states.SeatMapState;

/**Instance of the PaymentSuccessPage which handles ticket booking and updating the database accordingly.*/
public class PaymentSuccessPage implements Page {
    private static PaymentSuccessPage instance; //Singleton
    private final List<ProfilePageObserver> observersProfile = new ArrayList<>(); //Store observers to update ticket data elsewhere
    
    private PaymentSuccessPage() {}

    /**Returns the single instance of PaymentSuccessPage.*/
    public static PaymentSuccessPage getInstance() {
        if (instance == null) {
            instance = new PaymentSuccessPage();
        }
        return instance;
    }

    /**Show popup of selected seats, confirming booking.
     * @param selectedSeats The seats that the user booked.
     */
    public static void showEmailPopup(ArrayList<String> selectedSeats) {
        StringBuilder messageBuilder = new StringBuilder("Success! Seats selected: \n");

        for (String seat : selectedSeats) {
            messageBuilder.append(seat).append("\n");
        }

        //Show the message in a popup dialog
        JOptionPane.showMessageDialog(null, messageBuilder.toString(), "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    /**Add observer to update ticket data elsewhere.
     * @param observer The observer to add to watch payment success' data changes.
     */
    public void addProfileObserver(ProfilePageObserver observer) {
        observersProfile.add(observer);
    }

    /**Send out notification to update listeners of changed ticket confirmation data.
     * @param key The type of the data.
     * @param value The actual value of the data passed.
     */
    private void notifyProfileObservers(String key, Object value) {
        for (ProfilePageObserver observer : observersProfile) {
            observer.onProfileEdited(key, value);
        }
    }

    /**Processes the booking of the user tickets, updates database with new ticket purchases and handles email popup.
     * @param user The user that booked the ticket.
     */
    public void processPaymentSuccess(User user) {
        //Get the selected ticket information
        int showtimeId = MovieState.getInstance().getShowtimeId();
        ArrayList<String> selectedSeats = SeatMapState.getInstance().getSelectedSeats(); 
        
        //Store each ticket in the database
        for (String seat : selectedSeats) {
            DatabaseAccessor.addTicket(user, showtimeId, seat);
        }
        notifyProfileObservers("Tickets Bought", null);
        showEmailPopup(selectedSeats);
    }

    /**Creates the PaymentSuccessPage with required elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Panel, Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        // Create the header panel
        JPanel headerPanel = new HeaderPanel();
        
        //Create styled success message
        JLabel successLabel = new JLabel("Payment Successful! Your tickets are confirmed.", JLabel.CENTER);
        successLabel.setFont(new Font("Arial", Font.BOLD, 18));
        successLabel.setForeground(new Color(34, 139, 34)); // Green success color
        successLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        //Additional success information
        JLabel ticketInfoLabel = new JLabel("Check your email for the ticket details.", JLabel.CENTER);
        ticketInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ticketInfoLabel.setForeground(Color.DARK_GRAY);
        ticketInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        //Add a decorative success icon (optional)
        JLabel successIcon = new JLabel();
        successIcon.setIcon(new ImageIcon("frontend\\images\\checkmark.png")); // Provide path to your success icon
        successIcon.setHorizontalAlignment(JLabel.CENTER);
        successIcon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create the footer panel
        FooterPanel footerPanel = new FooterPanel("default");

        // Main panel setup
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE); // Clean background
        contentPanel.add(successIcon);
        contentPanel.add(successLabel);
        contentPanel.add(ticketInfoLabel);
        
        // Decorate content panel (optional)
        JPanel decoratedContent = (JPanel) new BackgroundColorDecorator(contentPanel, Color.WHITE).getDecoratedComponent();
        decoratedContent = (JPanel) new BorderDecorator(decoratedContent, Color.LIGHT_GRAY, 10).getDecoratedComponent();

        // Assemble the main page
        JPanel mainPanel = new PanelBuilder()
            .setLayout(new BorderLayout())
            .addComponent(headerPanel, BorderLayout.NORTH)
            .addComponent(decoratedContent, BorderLayout.CENTER)
            .addComponent(footerPanel, BorderLayout.SOUTH)
            .build();
        
        return mainPanel;
    }
}