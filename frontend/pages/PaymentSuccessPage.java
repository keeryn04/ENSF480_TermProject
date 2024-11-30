package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

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
import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.MovieState;
import frontend.states.SeatMapState;

public class PaymentSuccessPage implements Page {
    private static PaymentSuccessPage instance; //Singleton
    
    private PaymentSuccessPage() {}

    public static PaymentSuccessPage getInstance() {
        if (instance == null) {
            instance = new PaymentSuccessPage();
        }
        return instance;
    }

    //Show a popup message
    public static void showEmailPopup(ArrayList<String> selectedSeats) {
        StringBuilder messageBuilder = new StringBuilder("Success! Seats selected: \n");

        for (String seat : selectedSeats) {
            messageBuilder.append(seat).append("\n");
        }

        //Show the message in a popup dialog
        JOptionPane.showMessageDialog(null, messageBuilder.toString(), "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    //Process the successful payment and display the ticket
    public void processPaymentSuccess(User user) {
        //Get the selected ticket information
        int movieId = MovieState.getInstance().getMovieId();
        int showtimeId = MovieState.getInstance().getShowtimeId();
        ArrayList<String> selectedSeats = SeatMapState.getInstance().getSelectedSeats(); 
        
        //Store each ticket in the database
        for (String seat : selectedSeats) {
            DatabaseAccessor.addTicket(user, showtimeId, seat);
        }
        showEmailPopup(selectedSeats);
    }

    @Override
    public JPanel createPage() {
        // Create the header panel (e.g., contains navigation or logo)
        JPanel headerPanel = new HeaderPanel();
        
        // Create a styled success message
        JLabel successLabel = new JLabel("Payment Successful! Your tickets are confirmed.", JLabel.CENTER);
        successLabel.setFont(new Font("Arial", Font.BOLD, 18));
        successLabel.setForeground(new Color(34, 139, 34)); // Green success color
        successLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Add additional success information (optional)
        JLabel ticketInfoLabel = new JLabel("Check your email for the ticket details.", JLabel.CENTER);
        ticketInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ticketInfoLabel.setForeground(Color.DARK_GRAY);
        ticketInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        // Add a decorative success icon (optional)
        JLabel successIcon = new JLabel();
        successIcon.setIcon(new ImageIcon("path/to/success_icon.png")); // Provide path to your success icon
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