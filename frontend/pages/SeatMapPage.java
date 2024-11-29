package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.controllers.MoviePageController;
import frontend.controllers.PaymentPageController;
import frontend.controllers.SeatMapPageController;
import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.BorderDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.SeatMapObserver;
import frontend.panels.FooterPanel;
import backend.DatabaseAccessor;

public class SeatMapPage implements Page, SeatMapObserver {

    private SeatMapPage() { SeatMapPageController.getInstance().onLoad(); }

    /**Returns the single instance of SeatMapPage.*/
    public static SeatMapPage getInstance() {
        if (instance == null) {
            instance = new SeatMapPage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {            
            //Create header and footer
            JPanel headerPanel = DecoratorHelpers.createHeaderPanel();
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

    @Override
    public void onSeatMapUpdate(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onSeatMapUpdate'");
    }
}