package frontend.controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.DatabaseAccessor;
import backend.Screen;
import backend.Seat;
import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.BorderDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.SeatMapObserver;

public class SeatMapPageController implements PageController {
    private static SeatMapPageController instance;
    private final List<SeatMapObserver> observersSeatMap = new ArrayList<>();

    private Screen currentScreen;
    private ArrayList<Seat> seats;

    //Singleton management
    public static SeatMapPageController getInstance() {
        if (instance == null) {
            instance = new SeatMapPageController();
        }
        return instance;
    }

    public Screen getCurrentScreen() { return currentScreen; }
    public void setCurrentScreen(Screen currentScreen) { this.currentScreen = currentScreen; }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUpdate() {

    }

    public void resetSeats(JButton[][] seats) {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] != null) {
                    // Seat label (e.g., A1, B2)
                    String seatLabel = "" + (char) ('A' + row) + (col + 1);

                    // Check if a ticket exists for this seat and showtime
                    boolean isSeatTaken = DatabaseAccessor.checkIfSeatIsTaken(MoviePageController.getInstance().getShowtimeId(), seatLabel);

                    // Set seat color based on availability
                    Color seatColor = isSeatTaken ? Color.GRAY : Color.LIGHT_GRAY;
                    seats[row][col].setBackground(seatColor); //Reset the color to default
                }
            }
        }

        PaymentPageController.getInstance().clearTicketsAndSeats(); //Update info stored for payment
    }

    private JPanel createSeatMapPanel(int cols) {
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(10, cols, 10, 10));


        Font seatFont = new Font("Times New Roman", Font.PLAIN, 12);
        Font screenFont = new Font("Times New Roman", Font.BOLD, 20);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < cols; col++) {
                //Seat label (e.g., A1, B2)
                String seatLabel = "" + (char) ('A' + row) + (col + 1);

                //Check if a ticket exists for this seat and showtime
                boolean isSeatTaken = DatabaseAccessor.checkIfSeatIsTaken(MoviePageController.getInstance().getShowtimeId(), seatLabel);

                // Set seat color based on availability
                Color seatColor = isSeatTaken ? Color.GRAY : Color.LIGHT_GRAY;

                JButton seatButton = DecoratorHelpers.makeButton(
                        seatColor,
                        Color.BLACK,
                        "" + (char) ('A' + row) + (col + 1),
                        seatFont
                );

                //Add color-changing functionality to each seat
                seatButton.addActionListener(e -> {
                    if (seatButton.getBackground().equals(Color.LIGHT_GRAY)) {
                        seatButton.setBackground(Color.GREEN); //Selected
                        SeatMapPageController.getInstance().addSelectedSeat(seatButton.getText());
                    } else if (seatButton.getBackground().equals(Color.GREEN)) {
                        seatButton.setBackground(Color.LIGHT_GRAY); //Deselected
                        SeatMapPageController.getInstance().removeSelectedSeat(seatButton.getText());
                    }
                });

                seats[row][col] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        //Decorate the seat panel
        JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(seatPanel, Color.DARK_GRAY).getDecoratedComponent();
        decoratedPanel = (JPanel) new BorderDecorator(decoratedPanel, Color.DARK_GRAY, 10).getDecoratedComponent();

        //Screen panel
        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(Color.BLACK);
        JLabel screenLabel = DecoratorHelpers.makeLabel(Color.WHITE, "Screen", screenFont);
        screenLabel.setBackground(Color.BLACK);
        screenPanel.add(screenLabel);

        //Combine screen and seat panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(decoratedPanel, BorderLayout.CENTER);

        return mainPanel;
    }
    */
}