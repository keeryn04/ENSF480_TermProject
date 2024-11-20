package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frontend.decorators.DecoratorHelpers;

/**Page where user sees their personal info */
public class ProfilePage implements Page  {
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font nameFont = new Font("Times New Roman", Font.BOLD, 24);
            Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
            
            JPanel titlePanel = DecoratorHelpers.createHeaderPanel();
            JPanel footerPanel = DecoratorHelpers.createFooterPanel("editInfo");

            String name = "John"; //GET FROM API, ALONG WITH OTHER INFO
            String address = "123 mystreet ave"; //GET FROM API, ALONG WITH OTHER INFO
            String cardNum = "12345678"; //GET FROM API, ALONG WITH OTHER INFO
            String cardDate = "11/28"; //GET FROM API, ALONG WITH OTHER INFO

            //Profile panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBorder(BorderFactory.createTitledBorder("Edit Profile"));
            JLabel nameField = DecoratorHelpers.makeLabel(Color.BLACK, "Hi " + name + "!", nameFont);
            JLabel addressField = DecoratorHelpers.makeLabel(Color.BLACK, "Address: " + address, labelFont);
            JLabel paymentNumField = DecoratorHelpers.makeLabel(Color.BLACK, "Credit / Debit Card Number: " + cardNum, labelFont);
            JLabel paymentDateField = DecoratorHelpers.makeLabel(Color.BLACK, "Credit / Debit Expiration Date: " + cardDate, labelFont);

            //Add components to panel
            infoPanel.add(nameField);
            infoPanel.add(addressField);
            infoPanel.add(paymentNumField);
            infoPanel.add(paymentDateField);

            //Combine all panels in main layout
            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(infoPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Profile Page: %s%n", e.getMessage());
            return null;
        }
    }
}

