package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;

public class LoginPage implements Page, LoginPageObserver {
    private static LoginPage instance; // Singleton

    //Login Data
    private String email;
    private String password;

    private JPanel emailFieldPanel;
    JPanel passwordFieldPanel;

    private LoginPage() {
        //Initialize UI components
        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

        emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 20, null);
        passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 20, null);

        //Register with MovieState
        LoginState.getInstance().addLoginObserver(this);
    }

    /**Returns single instance of LoginPage */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {
            //Header and Footer            
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("confirmInfo");

            //Profile panel
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout());
            editPanel.setBorder(BorderFactory.createTitledBorder("Edit Profile"));

            //Add components to panel
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);

            //Combine all panels in main layout
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

    /**Check the login info with database / cached data */
    private Boolean checkForLogin() {return true;}

    /**Update login data based on LoginState data */
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
