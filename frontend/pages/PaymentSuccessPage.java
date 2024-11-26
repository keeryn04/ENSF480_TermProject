package frontend.pages;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import backend.DatabaseConfig;
import javax.swing.JPanel;

import backend.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;
import frontend.states.AppState;
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
        System.out.println("Found data for payment: movieId is" + movieId + " & showtimeId is " + showtimeId);
        
        //Store each ticket in the database
        for (String seat : selectedSeats) {
            System.out.println("Seat: " + seat);
            String rowPart = seat.replaceAll("[^A-Za-z]", ""); //Extract the alphabetical part
            String colPart = seat.replaceAll("[^0-9]", "");   //Extract the numerical part
            int seatRow = rowPart.charAt(0) - 'A' + 1; //Convert to integer
            int seatCol = Integer.parseInt(colPart); //Get integer from string

            //Store the ticket in the database
            addTicket(user, movieId, showtimeId, seatRow, seatCol);
        }

        showEmailPopup(selectedSeats);
    }

    //Store ticket in the database
    public void addTicket(User user, int movieId, int showtimeId, int seatRow, int seatCol) {
        String query = "INSERT INTO Tickets (user_id, movie_id, showtime_id, seat_row, seat_col) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
    
            statement.setInt(1, user.getID());
            statement.setInt(2, movieId);
            statement.setInt(3, showtimeId);
            statement.setInt(4, seatRow);
            statement.setInt(5, seatCol);
            statement.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
