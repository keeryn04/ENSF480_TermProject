package frontend.pages;

import frontend.controllers.HomePageController;
import frontend.controllers.MoviePageController;
import frontend.panels.HeaderPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;

/** Makes the HomePage to be displayed with Window */
public class HomePage implements Page {
    JPanel movieSelectionPanel;

    public HomePage() {
        movieSelectionPanel = new JPanel(new FlowLayout());
        movieSelectionPanel.setBackground(Color.WHITE);
    }

    @Override
    public JPanel createPage() {
        try {
            //Panels
            JPanel headerPanel = new HeaderPanel();
            JPanel movieSelectionPanel = HomePageController.getInstance().makeMovieSelectionPanel();
            JPanel contentPanel = new JPanel(new BorderLayout());

            //Use builder to add all panels in main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(headerPanel, BorderLayout.NORTH)
                    .addComponent(movieSelectionPanel, BorderLayout.CENTER)
                    .addComponent(contentPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Home Page: %s%n", e.getMessage());
            return null;
        }
    }
}
