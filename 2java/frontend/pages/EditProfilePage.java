package frontend.pages;

import java.awt.*;

import javax.swing.*;
import backend.DatabaseAccessor;
import backend.User;
import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.AppState;
import frontend.states.ErrorState;
import frontend.states.UserState;

/**Instance of the EditProfilePage which handles user data changing and updating the database accordingly.*/
public class EditProfilePage implements Page{
    private static EditProfilePage instance; //Singleton

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String CARD_NUM_REGEX = "^\\d{16}$";
    private static final String EXP_DATE_REGEX = "^(0[1-9]|1[0-2])\\/\\d{2}$";

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

    //UI components
    private JPanel nameFieldPanel;
    private JPanel emailFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel passwordReEntryFieldPanel;
    private JPanel addressFieldPanel;
    private JPanel creditCardNumFieldPanel;
    private JPanel creditCardExpDateFieldPanel;

    private JButton updateButton;

    private Font labelFont;

    /**Initialize the UI components for updating user data. */
    EditProfilePage() {
        // Initialize UI components
        labelFont = new Font("Times New Roman", Font.BOLD, 18);
        updateButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Update", labelFont);
        updateButton.addActionListener(e -> updateUser());
    }

    /**Returns single instance of EditProfilePage*/
    public static EditProfilePage getInstance() {
        if (instance == null) {
            instance = new EditProfilePage();
        }
        return instance;
    }

    /**Creates the EditProfilePage elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {            
            // Header and Footer
            JPanel titlePanel = new HeaderPanel();
            JPanel footerPanel = new FooterPanel("default");
            
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            
            //Reset old data
            oldName = null;
            oldEmail = null;
            oldPassword = null;
            oldAddress = null;
            oldCreditCardNum = null;
            oldCreditCardExpDate = null;

            //Get current user and autofill old data
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

            //Make input elements
            nameFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Name", labelFont, 40, oldName, new Dimension(10, 1));
            emailFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Email", labelFont, 40, oldEmail, new Dimension(10, 1));
            passwordFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Password", labelFont, 40, oldPassword, new Dimension(10, 1));
            passwordReEntryFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "ReEnter Password", labelFont, 40, null, new Dimension(10, 1));
            addressFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Address", labelFont, 40, oldAddress, new Dimension(10, 1));
            creditCardNumFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit Card Number", labelFont, 40, oldCreditCardNum, new Dimension(10, 1));
            creditCardExpDateFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Expiration Date", labelFont, 40, oldCreditCardExpDate, new Dimension(10, 1));


            //Add components to panel
            editPanel.add(nameFieldPanel);
            editPanel.add(emailFieldPanel);
            editPanel.add(passwordFieldPanel);
            editPanel.add(passwordReEntryFieldPanel);
            editPanel.add(addressFieldPanel);
            editPanel.add(creditCardNumFieldPanel);
            editPanel.add(creditCardExpDateFieldPanel);
            editPanel.add(updateButton);

            //Build main panel
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

    /**Check the user info with database / cached data. Handles input error checking
     * @return Status of user info (True is valid)
    */
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

        //Error handling
        if (!email.matches(EMAIL_REGEX)) {
            ErrorState.getInstance().setError("Invalid Email");
            return false;
        } else if (!creditCardNum.matches(CARD_NUM_REGEX)) {
            ErrorState.getInstance().setError("Invalid Card Number");
            return false;
        } else if (!creditCardExpDate.matches(EXP_DATE_REGEX)) {
            ErrorState.getInstance().setError("Invalid Card Expiration Date");
            return false;
        } else if (name.isEmpty() || address.isEmpty() || password.isEmpty()) {
            ErrorState.getInstance().setError("Empty Field");
            return false;
        } 
        else if (!password.equals(password_ReEntry)) {
            ErrorState.getInstance().setError("Passwords Don't Match");
            return false;
        }
        else if (AppState.getInstance().getUserEmails().contains(email) && !oldEmail.equals(email)) {
            ErrorState.getInstance().setError("User Already Exists");
            return false;
        } else {
            ErrorState.getInstance().clearError();
            DatabaseAccessor.updateUser(name, email, password, address, Long.valueOf(creditCardNum), creditCardExpDate);
            Window.getInstance().showPanel("Home");
        }
        return true;
    }
    
    /**Refresh page with new entered data.*/
    public void refreshPage() {
        JPanel page = createPage();
        Window.getInstance().addPanel("ProfileEditPage", page);
    }
}