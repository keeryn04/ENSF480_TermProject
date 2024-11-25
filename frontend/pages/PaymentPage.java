package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frontend.decorators.DecoratorHelpers;
import frontend.observers.PaymentPageObserver;
import frontend.panels.FooterPanel;

public class PaymentPage implements Page, PaymentPageObserver {
    private static PaymentPage instance; // Singleton

    //Stored info (For signed in user)
    private String cardNum;
    private String cardDate;
    private String cardCVV;

    //Update depending on what purchased
    private String itemPurchased;
    private double price; 
    private int amount; //If 0, shows registration purchase page
    private String ticketList;
    private Boolean ticketFlag;

    //UI Components
    private JLabel itemPurchasedTitle;
    private JLabel itemPurchasedLabel;
    private JLabel ticketListTitle;
    private JLabel ticketListLabel;
    private JLabel amountTitle;
    private JLabel amountLabel;
    private JLabel priceTitle;
    private JLabel priceLabel;

    //Payment inputs
    private JPanel cardNumPanel;
    private JPanel cardDatePanel;
    private JPanel cardCVVPanel; //3 Numbers on the back

    private PaymentPage() {
        itemPurchased = new String();
        price = 0; 
        amount = 0;
        ticketList = new String();
        ticketFlag = false;

        //GUI Initialization
        Font dataTitleFont = new Font("Times New Roman", Font.BOLD, 20);
        Font dataFont = new Font("Times New Roman", Font.PLAIN, 18);

        itemPurchasedTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Shopping Cart: ", dataTitleFont);
        itemPurchasedLabel = DecoratorHelpers.makeLabel(Color.BLACK, itemPurchased, dataFont);
        
        amountTitle = DecoratorHelpers.makeLabel(Color.BLACK, "# of Tickets: ", dataTitleFont);
        amountLabel = DecoratorHelpers.makeLabel(Color.BLACK, String.valueOf(amount), dataFont);

        ticketListTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Tickets Selected: ", dataTitleFont);
        ticketListLabel = DecoratorHelpers.makeLabel(Color.BLACK, ticketList, dataFont);

        priceTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Price: ", dataTitleFont);
        priceLabel = DecoratorHelpers.makeLabel(Color.BLACK, "$" + String.valueOf(price), dataFont);

        cardNumPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Number: ", dataTitleFont, 20, null);
        cardDatePanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Expiration Date: : ", dataTitleFont, 20, null);
        cardCVVPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card CVV: ", dataTitleFont, 3, null);

        PaymentState.getInstance().addPaymentObserver(this);
    }

    /**Returns single instance of PaymentPage */
    public static PaymentPage getInstance() {
        if (instance == null) {
            instance = new PaymentPage();
        }
        return instance;
    }

    @Override
    public JPanel createPage() {
        try {
            //Make header and footer
            JPanel headerPanel = DecoratorHelpers.createHeaderPanel();
            FooterPanel footerPanel = new FooterPanel("paymentConfirm");

            JPanel itemPurchasedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            itemPurchasedPanel.add(itemPurchasedTitle);
            itemPurchasedPanel.add(itemPurchasedLabel);

            JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pricePanel.add(priceTitle);
            pricePanel.add(priceLabel);

            JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            amountPanel.add(amountTitle);
            amountPanel.add(amountLabel);

            JPanel ticketListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ticketListPanel.add(ticketListTitle);
            ticketListPanel.add(ticketListLabel);

            JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            paymentPanel.add(cardNumPanel);
            paymentPanel.add(cardDatePanel);
            paymentPanel.add(cardCVVPanel);

            JPanel contentPanel = new PanelBuilder()
                    .setLayout(new GridLayout(7, 1))
                    .addComponent(itemPurchasedPanel, null)
                    .addComponent(pricePanel, null)
                    .addComponent(paymentPanel, null)
                    .build();
            
            if (ticketFlag) { //Ticket purchasing
                contentPanel.add(amountPanel);
                contentPanel.add(ticketListPanel);
            } 

            //Use builder to add all panels in the main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(headerPanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Payment Page: %s%n", e.getMessage());
            return null;
        }
    }

    @Override
    public void onPaymentConfirmed(String key, Object value) {
        //React to changes from AppState
        switch (key) {
            case "paymentCVV":
                cardCVV = (String) value;
                break;
            case "paymentAmount":
                amount = (Integer) value;
                updateContent();
                break;
            case "paymentItemPurchased":
                itemPurchased = (String) value;
                updateContent();
                break;
            case "paymentPrice":
                price = (Double) value;
                updateContent();
                break;
            case "paymentTicketList":
                ticketList = (String) value;
                updateContent();
                break;
            case "paymentTicketFlag":
                ticketFlag = (Boolean) value;
                if (ticketFlag == true) { //Handle if it should be registration or ticket purchasing
                    itemPurchased = "Tickets";
                    refreshPage(); 
                } else {
                    itemPurchased = "Registration";
                    price = 20;
                    refreshPage(); 
                }
                updateContent();
                break;
            default:
                break;
        }
    }

    
    private void updateContent() {
        SwingUtilities.invokeLater(() -> {
           priceLabel.setText("$" + String.valueOf(price));
           amountLabel.setText(String.valueOf(amount));
           itemPurchasedLabel.setText(itemPurchased);
           ticketListLabel.setText(ticketList);
        });
    }

    private void refreshPage() {
        JPanel newPage = createPage();
        Window.getInstance().addPanel("PaymentPage", newPage);  
    }
}
