package frontend.pages;

import java.awt.*;

import javax.swing.*;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.ProfilePageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.UserState;

import backend.User;

public class ProfilePage implements Page, ProfilePageObserver {
    private static ProfilePage instance; // Singleton

    // Default Data
    private User user;
    // private String name = "John Doe";
    // private String address = "123 Address Ave.";
    // private String cardNum = "123456789";
    // private String cardDate = "01/01";

    // UI components
    private JLabel emailLabel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel creditBalanceLabel;
    private JLabel cardNumLabel;
    private JLabel cardDateLabel;
    private JPanel contentPanel;
    private JButton registerButton;

    private ProfilePage() {
        // Fonts and Labels
        Font nameFont = new Font("Times New Roman", Font.BOLD, 24);
        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

        emailLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Email: ", labelFont);
        nameLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Name: ", nameFont);
        addressLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Address: ", labelFont);
        creditBalanceLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Credit Balance: ", labelFont);
        cardNumLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Credit Card Number: ", labelFont);
        cardDateLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Exiration Date: ", labelFont);
        
        registerButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Register Your Account", labelFont);
        registerButton.addActionListener(e -> {Window.getInstance().showPanel("RegisterPage");});

        // Register with ProfileState
        UserState.getInstance().addProfilePageObserver(this);
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
            // Header and Footer Panels
            contentPanel = new JPanel();
            JPanel titlePanel = new HeaderPanel();
            FooterPanel footerPanel = new FooterPanel("editInfo");


            // Profile panel
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel = (JPanel) new BackgroundColorDecorator(contentPanel, Color.WHITE).getDecoratedComponent();
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            user = UserState.getInstance().getUser();
            if (user != null) {
                if (user.getRegisteredStatus() == false)    //What to show if the user isn't registered
                {
                    contentPanel.add(nameLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(emailLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(creditBalanceLabel);
                    contentPanel.add(Box.createVerticalStrut(50));
                    contentPanel.add(registerButton);
                }
                else //What to show if the uses is registered
                {
                    contentPanel.add(nameLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(emailLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(addressLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(creditBalanceLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(cardNumLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(cardDateLabel);
                }
            }
            // Add labels to the panel
            

            // Combine all panels in the main layout
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

    /** Update profile data based on ProfileState data */
    @Override
    public void onProfileEdited(String key, Object value) {
        // React to changes from AppState
        switch (key) {
            case "User":
                user = (User) value;
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
            if (UserState.getInstance().getUser() != null) {
                user = UserState.getInstance().getUser();
                if (user.getName() != null) {
                    nameLabel.setText("Hi " + user.getName() + "!");
                } else {
                    nameLabel.setText("Hi Guest!");
                }
                
                emailLabel.setText("Email: " + user.getEmail());
                addressLabel.setText("Address: " + user.getAddress());
                creditBalanceLabel.setText("Credit Balanace: $" + String.valueOf(user.getCreditBalance()));
                cardNumLabel.setText("Credit / Debit Card Number: " + user.getCardNumber());
                cardDateLabel.setText("Credit / Debit Expiration Date: " + user.getCardExpiry());

                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
    }
}