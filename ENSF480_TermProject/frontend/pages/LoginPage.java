package frontend.pages;

import java.awt.*;

import javax.swing.*;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.ErrorState;
import frontend.states.UserState;

/**Instance of the LoginPage which handles user account login and updating the pages accordingly.*/
public class LoginPage implements Page, LoginPageObserver {
    private static LoginPage instance; // Singleton

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Login Data
    private String email;
    private String password;

    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel editPanel;
    private JButton loginButton;

    private Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

    /**Initializes the Login Button.*/
    private LoginPage() {
        // Initialize UI components
        loginButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Login", labelFont);
    }

    /** Returns single instance of LoginPage */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }

    /**Creates the LoginPage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {
            // Header and Footer
            emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 20, null, new Dimension(10, 1));
            passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 20, null, new Dimension(10, 1));
        
            JPanel titlePanel = new HeaderPanel();
            JPanel footerPanel = new FooterPanel("login");

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
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Login Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Makes login button that has authentication logic set up.
     * @return Button with authentication checking.
     */
    private JButton createLoginButton() {
        JButton loginButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Login", labelFont);
        loginButton.addActionListener(e -> authenticate());
        return loginButton;
    }

    /**Check the login info with database / cached data. Handle error checking with user inputs.*/
    private void authenticate() {
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

        //Error checking
        if (!email.matches(EMAIL_REGEX)) {
            ErrorState.getInstance().setError("Invalid Email");
            return;
        } else {
            ErrorState.getInstance().clearError();
        }

        boolean loggedIn = UserState.getInstance().logInUser(email, password);
        if (loggedIn) {
            ErrorState.getInstance().clearError();
            ProfilePage.getInstance().updateContent();
            Window.getInstance().refreshPages();
            Window.getInstance().showPanel("ProfilePage");
            
        } else {
            ErrorState.getInstance().setError("Incorrect credentials");
        }
    }

    /**Update the page with the new content. Recreate the page and add it to the window. */
    public void refreshPage() {
        JPanel page = createPage();
        Window.getInstance().addPanel("LoginPage", page);
    }

    /**Update login data based on LoginState data through observer.
     * @param key The type of the object passed.
     * @param value The actual value being passed.
    */
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