package frontend.pages;

import java.awt.*;

import javax.swing.*;
import javax.xml.crypto.Data;

import backend.DatabaseAccessor;
import backend.User;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.LoginPageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.AppState;
import frontend.states.UserState;

public class EditProfilePage implements Page{
    private static EditProfilePage instance; // Singleton

    // Signup Data
    private String name;
    private String email;
    private String password;
    private String password_ReEntry;
    private String address;
    private String creditCardNum;
    private String creditCardExpDate;

    private String oldName;
    private String oldEmail;
    private String oldPassword;
    private String oldAddress;
    private String oldCreditCardNum;
    private String oldCreditCardExpDate;

    private JPanel nameFieldPanel;
    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel passwordReEntryFieldPanel;
    private JPanel addressFieldPanel;
    private JPanel creditCardNumFieldPanel;
    private JPanel creditCardExpDateFieldPanel;

    private JButton updateButton;

    private Font labelFont;

    EditProfilePage() {
        // Initialize UI components
        labelFont = new Font("Times New Roman", Font.BOLD, 18);
        updateButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Update", labelFont);
        updateButton.addActionListener(e -> updateUser());
    }

        /** Returns single instance of LoginPage */
    public static EditProfilePage getInstance() {
        if (instance == null) {
            instance = new EditProfilePage();
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
            
            oldName = null;
            oldEmail = null;
            oldPassword = null;
            oldAddress = null;
            oldCreditCardNum = null;
            oldCreditCardExpDate = null;

            if (UserState.getInstance().getUser() != null) 
            {
                User u = UserState.getInstance().getUser();
                oldName = u.getName();
                oldEmail = u.getEmail();
                oldPassword = u.getPassword();
                oldAddress = u.getAddress();
                oldCreditCardNum = String.valueOf(u.getCardNumber());
                oldCreditCardExpDate = u.getCardExpiry();
            }

            nameFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Name", labelFont, 40, oldName, new Dimension(10, 1));
            emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 40, oldEmail, new Dimension(10, 1));
            passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 40, oldPassword, new Dimension(10, 1));
            passwordReEntryFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "ReEnter Password", labelFont, 40, null, new Dimension(10, 1));
            addressFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Address", labelFont, 40, oldAddress, new Dimension(10, 1));
            creditCardNumFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit Card Number", labelFont, 40, oldCreditCardNum, new Dimension(10, 1));
            creditCardExpDateFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Expiration Date", labelFont, 40, oldCreditCardExpDate, new Dimension(10, 1));


            // Add components to panel
            editPanel.add(nameFieldPanel);
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);
            editPanel.add(passwordReEntryFieldPanel);
            editPanel.add(addressFieldPanel);
            editPanel.add(creditCardNumFieldPanel);
            editPanel.add(creditCardExpDateFieldPanel);
            editPanel.add(updateButton);

            JPanel mainPanel = new PanelBuilder()
                .setLayout(new BorderLayout())
                .addComponent(titlePanel, BorderLayout.NORTH)
                .addComponent(editPanel, BorderLayout.CENTER)
                .addComponent(footerPanel, BorderLayout.SOUTH)
                .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Edit Profile Page: %s%n", e.getMessage());
            return null;
        }
    }

    /** Check the login info with database / cached data */
    private Boolean updateUser() {
        Component[] namePanelComponents = nameFieldPanel.getComponents();
        Component[] emailPanelComponents = emailFieldPanel.getComponents();
        Component[] passwordPanelComponents = passwordFieldPanel.getComponents();
        Component[] passwordReEntryPanelComponents = passwordReEntryFieldPanel.getComponents();
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

        if (!password.equals(password_ReEntry)) {
            System.err.println("Passwords dont match");
            System.err.println(password);
            System.err.println(password_ReEntry);
        }
        else if (AppState.getInstance().getUserEmails().contains(email))
            System.err.println("User email already exists");
        else {
            DatabaseAccessor.updateUser(name, email, password, address, Long.valueOf(creditCardNum), creditCardExpDate);
            Window.getInstance().showPanel("Home");
        }
        return true;
    }
    
    public void refreshPage() {
        JPanel page = createPage();
        Window.getInstance().addPanel("ProfileEditPage", page);
    }
}
