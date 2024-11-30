package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.BorderDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.SeatMapObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.MovieState;
import frontend.states.PaymentState;
import frontend.states.SeatMapState;
import backend.DatabaseAccessor;

public class SeatMapPage implements Page, SeatMapObserver {
    private static SeatMapPage instance; // Singleton instance

    private Integer rows;
    private Integer cols;

    //Date today
    LocalDate currentDate = LocalDate.now();

    //UI components
    private JPanel seatmapPanel;
    private JButton[][] seats; // To track seat buttons for interactions
    private JPanel contentPanel;

    private SeatMapPage() {
        //Default seatmap
        rows = 1;
        cols = 1;
        
        seatmapPanel = createSeatMapPanel(rows, cols);

        contentPanel = new JPanel();

        String showtimeId = String.valueOf(MovieState.getInstance().getShowtimeId());
        if (showtimeId == null || showtimeId.isEmpty()) {
            // Handle case where showtimeId is not available yet
            System.out.println("Showtime ID is not available. Wait until it's fetched.");
            return; // Or show a loading spinner, or retry after a delay
        }

        //Register with SeatMapState
        SeatMapState.getInstance().addSeatMapObserver(this); 
    }

    /**Returns the single instance of SeatMapPage.*/
    public static SeatMapPage getInstance() {
        if (instance == null) {
            instance = new SeatMapPage();
        }
        return instance;
    }

    /**Creates the SeatMapPage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {            
            //Create header and footer
            JPanel headerPanel = new HeaderPanel();
            FooterPanel footerPanel = new FooterPanel("continuePurchase");

            //Layout setup
            contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(seatmapPanel, BorderLayout.CENTER);

            //Use builder to assemble the main layout
            return new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(headerPanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

        } catch (Exception e) {
            System.out.printf("Error making Seat Map Page: %s%n", e.getMessage());
            return null;
        }
    }

    /** Creates a seat map panel with interactive seats and color management.*/
    private JPanel createSeatMapPanel(int rows, int cols) {
        this.seats = new JButton[rows][cols];
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(rows, cols, 10, 10));

        Font seatFont = new Font("Times New Roman", Font.PLAIN, 12);
        Font screenFont = new Font("Times New Roman", Font.BOLD, 20);

        ArrayList<String> takenSeats = DatabaseAccessor.getTakenSeats(MovieState.getInstance().getShowtimeId());

        //Initialize or reset seats
        initializeOrResetSeats(rows, cols, seatFont, takenSeats);

        //Add seats to the seat panel
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (seats[row][col] != null) {
                    seatPanel.add(seats[row][col]);
                }
            }
        }

        //Decorate the seat panel
        JPanel decoratedPanel = (JPanel) new BackgroundColorDecorator(seatPanel, Color.DARK_GRAY).getDecoratedComponent();
        decoratedPanel = (JPanel) new BorderDecorator(decoratedPanel, Color.DARK_GRAY, 10).getDecoratedComponent();

        //Create a screen label
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

    /**Initializes or resets the seats with appropriate colors and interactions.*/
    private void initializeOrResetSeats(int rows, int cols, Font seatFont, ArrayList<String> takenSeats) {
        String releaseDate = MovieState.getInstance().getReleaseDate();
        LocalDate storedDate = LocalDate.parse(releaseDate);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //Seat label (Ex. A2)
                String seatLabel = "" + (char) ('A' + row) + (col + 1);

                //Check seat availability
                Color seatColor;
                if (currentDate.isBefore(storedDate) && row != 4) {
                    seatColor = Color.GRAY; //Reserved Row
                } else {
                    seatColor = takenSeats.contains(seatLabel) ? Color.GRAY : Color.LIGHT_GRAY;
                }

                //Create or update the seat button
                if (seats[row][col] == null) {
                    JButton seatButton = DecoratorHelpers.makeButton(seatColor, Color.BLACK, seatLabel, seatFont);

                    //Add color-changing functionality
                    seatButton.addActionListener(e -> {
                        if (seatButton.getBackground().equals(Color.LIGHT_GRAY)) {
                            seatButton.setBackground(Color.GREEN); // Selected
                            SeatMapState.getInstance().addSelectedSeat(seatLabel);
                        } else if (seatButton.getBackground().equals(Color.GREEN)) {
                            seatButton.setBackground(Color.LIGHT_GRAY); // Deselected
                            SeatMapState.getInstance().removeSelectedSeat(seatLabel);
                        }
                    });

                    seats[row][col] = seatButton;
                } else {
                    seats[row][col].setBackground(seatColor); //Reset color
                }
            }
        }

        PaymentState.getInstance().clearTicketsAndSeats(); //Update info stored for payment
    }
    
    /**Update screen data based on SeatMapState data */
    @Override
    public void onSeatMapUpdate(String key, Object value) {
        //React to changes from SeatMapState
        switch (key) {
            case "seatRows":
                rows = (Integer) value;
                updateContent();
                break;
            case "seatCols":
                cols = (Integer) value;
                updateContent();
                break;
            default:
                break;
        }
    }

    public void updateContent() {
        SwingUtilities.invokeLater(() -> {
            //Make new SeatMap with new data
            JPanel newSeatMapPanel = createSeatMapPanel(rows, cols);
            
            //Remove old seatmap
            if (this.seatmapPanel != null) {
                contentPanel.remove(this.seatmapPanel); 
            }
    
            //Update reference to new panel
            this.seatmapPanel = newSeatMapPanel;
    
            //Add new seat map panel to parent panel
            contentPanel.add(this.seatmapPanel, BorderLayout.CENTER);
    
            //Revalidate and repaint parent container, reflect new changes
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }
}