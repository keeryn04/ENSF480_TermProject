package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import frontend.decorators.DecoratorHelpers;

public class EditProfilePage implements Page  {
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
            
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("confirmInfo");

            //Profile panel
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new FlowLayout());
            editPanel.setBorder(BorderFactory.createTitledBorder("Edit Profile"));
            JPanel nameField = DecoratorHelpers.makeLabeledField(Color.BLACK, "Name", labelFont, 20, null);
            JPanel addressField = DecoratorHelpers.makeLabeledField(Color.BLACK, "Address", labelFont, 20, null);
            JPanel paymentNumField = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Number: ", labelFont, 20, null);
            JPanel paymentDateField = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Expiration Date: ", labelFont, 20, null);

            //Add components to panel
            editPanel.add(nameField);
            editPanel.add(addressField);
            editPanel.add(paymentNumField);
            editPanel.add(paymentDateField);

            //Combine all panels in main layout
            JPanel mainPanel = new PageBuilder()
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
