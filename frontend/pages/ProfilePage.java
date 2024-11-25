package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.ProfilePageObserver;
import frontend.panels.FooterPanel;

public class ProfilePage implements Page, ProfilePageObserver {
    private static ProfilePage instance; // Singleton

    //Default Data
    private String name = "John Doe";
    private String address = "123 Address Ave.";
    private String cardNum = "123456789";
    private String cardDate = "01/01";

    //UI components
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel cardNumLabel;
    private JLabel cardDateLabel;
    private JPanel contentPanel;

    private ProfilePage() {
        //Fonts and Labels
        Font nameFont = new Font("Times New Roman", Font.BOLD, 24);
        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

        nameLabel = DecoratorHelpers.makeLabel(Color.BLACK, "name", nameFont);
        addressLabel = DecoratorHelpers.makeLabel(Color.BLACK, "address", labelFont);
        cardNumLabel = DecoratorHelpers.makeLabel(Color.BLACK, "cardNum", labelFont);
        cardDateLabel = DecoratorHelpers.makeLabel(Color.BLACK, "cardDate", labelFont);

        contentPanel = new JPanel();

        //Register with ProfileState
        ProfileState.getInstance().addProfileObserver(this);
    }

    /**
     * Returns the single instance of ProfilePage.
     */
    public static ProfilePage getInstance() {
        if (instance == null) {
            instance = new ProfilePage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {
            //Header and Footer Panels
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            FooterPanel footerPanel = new FooterPanel("editInfo");

            //Profile panel
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
            contentPanel = (JPanel) new BackgroundColorDecorator(contentPanel, Color.WHITE).getDecoratedComponent();  
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            //Add labels to the panel
            contentPanel.add(nameLabel);
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(addressLabel);
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(cardNumLabel);
            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(cardDateLabel);

            //Combine all panels in the main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Profile Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Update profile data based on ProfileState data */
    @Override
    public void onProfileEdited(String key, Object value) {
        //React to changes from AppState
        switch (key) {
            case "name":
                name = (String) value;
                updateContent();
                break;
            case "address":
                address = (String) value;
                updateContent();
                break;
            case "cardNum":
                cardNum = (String) value;
                updateContent();
                break;
            case "cardDate":
                cardDate = (String) value;
                updateContent();
                break;
            default:
                break;
        }
    }

    /**
     * Updates the ProfilePage data and UI components.
     */
    public void updateContent() {
        SwingUtilities.invokeLater(() -> {

            nameLabel.setText("Hi " + name + "!");
            addressLabel.setText("Address: " + address);
            cardNumLabel.setText("Credit / Debit Card Number: " + cardNum);
            cardDateLabel.setText("Credit / Debit Expiration Date: " + cardDate);

            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }
}