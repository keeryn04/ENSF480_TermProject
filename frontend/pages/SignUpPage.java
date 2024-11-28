package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;
import frontend.states.UserState;

public class SignUpPage implements Page {
    private static SignUpPage instance; // Singleton

    // Signup Data
    private String email;
    private String password;
    private String password_ReEntry;

    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel passwordReEntryFieldPanel;

    private SignUpPage() {
        // Initialize UI components
        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

        emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 20, null);
        passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 20, null);
        passwordReEntryFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "ReEnter Password", labelFont, 20,
                null);

        // Register with MovieState
        // UserState.getInstance().addSignupPageObserver(this);
    }

    /** Returns single instance of LoginPage */
    public static SignUpPage getInstance() {
        if (instance == null) {
            instance = new SignUpPage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {
            // Header and Footer
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            // JPanel footerPanel = DecoratorHelpers.createFooterPanel("confirmInfo");

            // Profile panel
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout());
            editPanel.setBorder(BorderFactory.createTitledBorder("Edit Profile"));

            // Add components to panel
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);

            // Combine all panels in main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(editPanel, BorderLayout.CENTER)
                    // .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Login Page: %s%n", e.getMessage());
            return null;
        }
    }

    /** Check the login info with database / cached data */
    private Boolean checkForLogin() {
        return true;
    }

    /** Update login data based on LoginState data */
    /*
     * @Override
     * public void onLoginChange(String key, Object value) {
     * switch (key) {
     * case "email":
     * email = (String) value;
     * break;
     * case "password":
     * password = (String) value;
     * break;
     * default:
     * break;
     * }
     * }
     */
}
