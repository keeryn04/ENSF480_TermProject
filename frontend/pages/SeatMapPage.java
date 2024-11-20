package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import frontend.decorators.DecoratorHelpers;

public class SeatMapPage implements Page {

    private JLabel titleLabel; 
    private JPanel seatmapPanel;

    // Constructor to initialize the page with specific data
    public SeatMapPage(String title, int rows, int cols) {
        titleLabel = new JLabel(title);
        seatmapPanel = DecoratorHelpers.createSeatMapPanel(rows, cols);
    }

    @Override
    public JPanel createPage() {
        try {
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);

            // Create panels
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("confirmPurchase");

            // Customize the title
            titleLabel.setFont(titleFont);
            titleLabel.setForeground(Color.BLACK);

            // Layout setup
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(titleLabel, BorderLayout.NORTH);
            contentPanel.add(seatmapPanel, BorderLayout.CENTER);

            // Use builder to assemble the main layout
            return new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

        } catch (Exception e) {
            System.out.printf("Error making Seat Map Page: %s%n", e.getMessage());
            return null;
        }
    }
}