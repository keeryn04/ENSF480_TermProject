package frontend.pages;

import java.awt.BorderLayout;
import java.util.ArrayList;

import backend.DatabaseAccessor;
import javax.swing.JPanel;

import backend.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import frontend.controllers.MoviePageController;
import frontend.controllers.SeatMapPageController;
import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;

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
        int movieId = MoviePageController.getInstance().getMovieId();
        int showtimeId = MoviePageController.getInstance().getShowtimeId();
        ArrayList<String> selectedSeats = SeatMapPageController.getInstance().getSelectedSeats(); 
        System.out.println("Found data for payment: movieId is" + movieId + " & showtimeId is " + showtimeId);
        
        //Store each ticket in the database
        for (String seat : selectedSeats) {
            System.out.println("Seat: " + seat);
            String seatRow = seat.replaceAll("[^A-Za-z]", ""); //Extract the alphabetical part
            String colPart = seat.replaceAll("[^0-9]", "");   //Extract the numerical part
            int seatCol = Integer.parseInt(colPart); //Get integer from string

            //Store the ticket in the database
            DatabaseAccessor.addTicket(user, showtimeId, seatRow, seatCol);
        }

        showEmailPopup(selectedSeats);
    }

    //Create the success page UI
    @Override
    public JPanel createPage() {
        JPanel headerPanel = DecoratorHelpers.createHeaderPanel();
        FooterPanel footerPanel = new FooterPanel("default");

        JLabel successLabel = new JLabel("Payment Successful! Your tickets are confirmed.");
        JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(headerPanel, BorderLayout.NORTH)
                    .addComponent(successLabel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();
        return mainPanel;
    }
}