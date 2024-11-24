package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.BorderDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.SeatMapObserver;

public class SeatMapPage implements Page, SeatMapObserver {
    private static SeatMapPage instance; // Singleton instance

    private Integer rows;
    private Integer cols;

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
            JPanel headerPanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("continuePurchase");

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

    /**Creates a seat map panel with seat color-changing interactions.*/
    private JPanel createSeatMapPanel(int rows, int cols) {
        this.seats = new JButton[rows][cols];
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(rows, cols, 10, 10));

        Font seatFont = new Font("Times New Roman", Font.PLAIN, 12);
        Font screenFont = new Font("Times New Roman", Font.BOLD, 20);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton seatButton = DecoratorHelpers.makeButton(
                        Color.LIGHT_GRAY,
                        Color.BLACK,
                        "Seat " + (row * cols + col + 1),
                        seatFont
                );

                //Add color-changing functionality to each seat
                seatButton.addActionListener(e -> {
                    if (seatButton.getBackground().equals(Color.LIGHT_GRAY)) {
                        seatButton.setBackground(Color.GREEN); //Selected
                    } else {
                        seatButton.setBackground(Color.LIGHT_GRAY); //Deselected
                    }
                });

                seats[row][col] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        resetSeats(seats); //Initialize seat colors

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

    /**Resets the seat colors on the seat map.*/
    public void resetSeats(JButton[][] seats) {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] != null) {
                    seats[row][col].setBackground(Color.LIGHT_GRAY); //Reset the color to default
                }
            }
        }
    }
    
    /**Update screen data based on SeatMapState data */
    @Override
    public void onSeatMapUpdate(String key, Object value) {
        //React to changes from AppState
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