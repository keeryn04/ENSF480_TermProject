package frontend.pages;

import java.awt.*;

import javax.swing.*;
import javax.xml.crypto.Data;

import backend.DatabaseAccessor;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.AppState;
import frontend.states.UserState;

public class RegisterPage implements Page{
    private static RegisterPage instance; // Singleton

    // Signup Data
    private String name;
    private String address;
    private String creditCardNum;
    private String creditCardExpDate;

    private JPanel nameFieldPanel;
    private JPanel addressFieldPanel;
    private JPanel creditCardNumFieldPanel;
    private JPanel creditCardExpDateFieldPanel;

    private JButton registerButton;

    private Font labelFont;

    RegisterPage() {
        // Initialize UI components
        labelFont = new Font("Times New Roman", Font.BOLD, 18);
        nameFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Name", labelFont, 40, null, new Dimension(10, 1));
        addressFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Address", labelFont, 40, null, new Dimension(10, 1));
        creditCardNumFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit Card Number", labelFont, 40, null, new Dimension(10, 1));
        creditCardExpDateFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Expiration Date", labelFont, 40, null, new Dimension(10, 1));
        registerButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Register", labelFont);
        registerButton.addActionListener(e -> registerUser());
    }

        /** Returns single instance of LoginPage */
    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {            
            // Header and Footer
            JPanel titlePanel = new HeaderPanel();
            JPanel footerPanel = new FooterPanel("default");
            
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            
            // Add components to panel
            editPanel.add(nameFieldPanel);
            editPanel.add(addressFieldPanel);
            editPanel.add(creditCardNumFieldPanel);
            editPanel.add(creditCardExpDateFieldPanel);
            editPanel.add(registerButton);

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

    /** Check the login info with database / cached data */
    private Boolean registerUser() {
        Component[] namePanelComponents = nameFieldPanel.getComponents();
        Component[] addressPanelComponents = addressFieldPanel.getComponents();
        Component[] creditCardNumPanelComponents = creditCardNumFieldPanel.getComponents();
        Component[] creditCardExpDatePanelComponents = creditCardExpDateFieldPanel.getComponents();
        
        
        for (Component component : namePanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Name")) {
                JTextField j = (JTextField) component;
                name = j.getText();
                break;
            }
        }

        for (Component component : addressPanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Address")) {
                JTextField j = (JTextField) component;
                address = j.getText();
                break;
            }
        }

        for (Component component : creditCardNumPanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Credit Card Number")) {
                JTextField j = (JTextField) component;
                creditCardNum = j.getText();
                break;
            }
        }

        for (Component component : creditCardExpDatePanelComponents) {
            if (component instanceof JTextField && ((JTextField) component).getName().equals("Expiration Date")) {
                JTextField j = (JTextField) component;
                creditCardExpDate = j.getText();
                break;
            }
        }

        DatabaseAccessor.registerUser(UserState.getInstance().getUser().getEmail(), name, address, Long.valueOf(creditCardNum), creditCardExpDate);
        Window.getInstance().showPanel("Home");
        return true;
    }
}
