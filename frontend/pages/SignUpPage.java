package frontend.pages;

import java.awt.*;

import javax.swing.*;
import backend.DatabaseAccessor;
import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.AppState;
import frontend.states.ErrorState;

/**Instance of the SignupPage which handles creating new users and updating the database accordingly.*/
public class SignUpPage implements Page {
    private static SignUpPage instance; // Singleton

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Signup Data
    private String email;
    private String password;
    private String password_ReEntry;

    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel passwordReEntryFieldPanel;

    private Font labelFont;

    /**Make default entry labels for the page inputs.*/
    private SignUpPage() {
        // Initialize UI components
        labelFont = new Font("Times New Roman", Font.BOLD, 18);
        emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 40, null, new Dimension(10, 1));
        passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 40, null, new Dimension(10, 1));
        passwordReEntryFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "ReEnter Password", labelFont, 40, null, new Dimension(10, 1));
    }

    /**Returns single instance of SignupPage*/
    public static SignUpPage getInstance() {
        if (instance == null) {
            instance = new SignUpPage();
        }
        return instance;
    }

    /**Creates an instance of the SignupPage, with GUI elements that can be changed dynamically.*/
    @Override
    public JPanel createPage() {
        try {
            // Header and Footer
            JPanel titlePanel = new HeaderPanel();
            JPanel footerPanel = new FooterPanel("default");

            // Profile panel
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            
            // Add components to panel
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);
            editPanel.add(passwordReEntryFieldPanel);
            editPanel.add(createSignUpButton());

            // Combine all panels in main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(editPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Signup Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Makes a Button for signing up a new user, with features to verify the login information of the user.
     * @return A button with login implementation.
     */
    private JButton createSignUpButton() {
        JButton signUpButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Sign Up", labelFont);
        signUpButton.addActionListener(e -> validateSignUp());
        return signUpButton;
    }


    /**Check the login info with database / cached data. Handles error checking for invalid inputs.
     * @return A boolean whether the user's info is verified or not.
    */
    private Boolean validateSignUp() {
        Component[] emailPanelComponents = emailFieldPanel.getComponents();
        Component[] passwordPanelComponents = passwordFieldPanel.getComponents();
        Component[] passwordReEntryPanelComponents = passwordReEntryFieldPanel.getComponents();

        for (Component component : emailPanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Email")) {
                JTextField j = (JTextField) component;
                email = j.getText();
                break;
            }
        }

        for (Component component : passwordPanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Password")) {
                JTextField j = (JTextField) component;
                password = j.getText();
                break;
            }
        }

        for (Component component : passwordReEntryPanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("ReEnter Password")) {
                JTextField j = (JTextField) component;
                password_ReEntry = j.getText();
                break;
            }
        }

        if (!email.matches(EMAIL_REGEX)) {
            ErrorState.getInstance().setError("Invalid Email");
            return false;
        } else if (password.isEmpty()) {
            ErrorState.getInstance().setError("Invalid Password");
            return false;
        }

        if (!password.equals(password_ReEntry)) {
            ErrorState.getInstance().setError("Passwords Don't Match");
            return false;
        }
        else if (AppState.getInstance().getUserEmails().contains(email)) {
            ErrorState.getInstance().setError("User Already Exists");
            return false;
        } else {
            ErrorState.getInstance().clearError();
            DatabaseAccessor.addNewUser(email, password);
            Window.getInstance().showPanel("Home");
            return true;
        }
    }
}
