package frontend.pages;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import frontend.decorators.DecoratorHelpers;
import frontend.panels.FooterPanel;

/**Page where user edits their personal info */
public class EditProfilePage implements Page  {
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
            
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            FooterPanel footerPanel = new FooterPanel("confirmInfo");

            //Profile panel
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout());
            editPanel.setBorder(BorderFactory.createTitledBorder("Edit Profile"));
            JPanel nameFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Name", labelFont, 20, null);
            JPanel addressFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Address", labelFont, 20, null);
            JPanel paymentNumFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Number: ", labelFont, 20, null);
            JPanel paymentDateFieldPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Expiration Date: ", labelFont, 20, null);
            /*
            JTextField nameField = (JTextField) nameFieldPanel.getComponent(1);  // Get the JTextField from the JPanel
            JTextField addressField = (JTextField) addressFieldPanel.getComponent(1);
            JTextField paymentNumField = (JTextField) paymentNumFieldPanel.getComponent(1);
            JTextField paymentDateField = (JTextField) paymentDateFieldPanel.getComponent(1);

            // To retrieve the text entered by the user:
            String enteredName = nameField.getText();
            String enteredAddress = addressField.getText();
            String enteredPaymentNum = paymentNumField.getText();
            String enteredPaymentDate = paymentDateField.getText();

            // Now, you can use the entered data, e.g., set it in ProfileState or elsewhere
            ProfileState.getInstance().setName(enteredName);
            ProfileState.getInstance().setAddress(enteredAddress);
            ProfileState.getInstance().setCardNum(enteredPaymentNum);
            ProfileState.getInstance().setCardDate(enteredPaymentDate);
            */

            //Add components to panel
            editPanel.add(nameFieldPanel);
            editPanel.add(addressFieldPanel);
            editPanel.add(paymentNumFieldPanel);
            editPanel.add(paymentDateFieldPanel);

            //Combine all panels in main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(editPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Profile Page: %s%n", e.getMessage());
            return null;
        }
    }
}