package frontend.pages;

import java.awt.*;

import javax.swing.*;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;
import frontend.states.UserState;

public class LoginPage implements Page, LoginPageObserver {
    private static LoginPage instance; // Singleton

    // Login Data
    private String email;
    private String password;

    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel editPanel;
    private JButton loginButton;

    private Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

    private LoginPage() {
        // Initialize UI components
        emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 20, null);
        passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 20, null);
        loginButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Login", labelFont);
        // Register with MovieState
        // UserState.getInstance().addLoginPageObserver(this);
    }

    /** Returns single instance of LoginPage */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
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
            editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout());
            editPanel.setBorder(BorderFactory.createTitledBorder("Login"));

            // Add components to panel
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);
            editPanel.add(createLoginButton());

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

    private JButton createLoginButton() {
        JButton loginButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Login", labelFont);
        loginButton.addActionListener(e -> checkForLogin());
        return loginButton;
    }

    /** Check the login info with database / cached data */
    private Boolean checkForLogin() {
        Component[] emailPanelComponents = emailFieldPanel.getComponents();
        Component[] passwordPanelComponents = passwordFieldPanel.getComponents();

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

        System.out.println(UserState.getInstance().logInUser(email, password));
        return true;
    }

    /** Update login data based on LoginState data */
    @Override
    public void onLoginChange(String key, Object value) {
        switch (key) {
            case "email":
                email = (String) value;
                break;
            case "password":
                password = (String) value;
                break;
            default:
                break;
        }
    }
}