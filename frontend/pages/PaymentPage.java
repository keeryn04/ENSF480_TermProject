package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import backend.User;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.MoviePageObserver;
import frontend.observers.PaymentPageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.AppState;
import frontend.states.MovieState;
import frontend.states.PaymentState;

public class PaymentPage implements Page, PaymentPageObserver, MoviePageObserver {
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
    private BufferedImage posterImage;

    //UI Components
    private JLabel itemPurchasedTitle;
    private JLabel itemPurchasedLabel;
    private JLabel ticketListTitle;
    private JLabel ticketListLabel;
    private JLabel amountTitle;
    private JLabel amountLabel;
    private JLabel priceTitle;
    private JLabel priceLabel;
    private JLabel posterLabel;

    //Payment inputs
    private JPanel cardNumPanel;
    private JPanel cardDatePanel;
    private JPanel cardCVVPanel; //3 Numbers on the back

    //Fonts
    private Font dataTitleFont;
    private Font dataFont;

    private PaymentPage() {
        itemPurchased = new String();
        price = 0; 
        amount = 0;
        ticketList = new String();
        ticketFlag = false;

        //GUI Initialization
        dataTitleFont = new Font("Times New Roman", Font.BOLD, 20);
        dataFont = new Font("Times New Roman", Font.PLAIN, 18);

        posterLabel = new JLabel();

        itemPurchasedTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Shopping Cart: ", dataTitleFont);
        itemPurchasedLabel = DecoratorHelpers.makeLabel(Color.BLACK, itemPurchased, dataFont);

        priceTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Price: ", dataTitleFont);
        priceLabel = DecoratorHelpers.makeLabel(Color.BLACK, "$" + String.valueOf(price), dataFont);
        
        amountTitle = DecoratorHelpers.makeLabel(Color.BLACK, "# of Tickets: ", dataTitleFont);
        amountLabel = DecoratorHelpers.makeLabel(Color.BLACK, String.valueOf(amount), dataFont);

        ticketListTitle = DecoratorHelpers.makeLabel(Color.BLACK, "Tickets Selected: ", dataTitleFont);
        ticketListLabel = DecoratorHelpers.makeLabel(Color.BLACK, ticketList, dataFont);

        cardNumPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Number: ", dataTitleFont, 20, null, null);
        cardDatePanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Expiration Date: ", dataTitleFont, 20,null, null);
        cardCVVPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card CVV: ", dataTitleFont, 3, null, null);

        PaymentState.getInstance().addPaymentObserver(this);
        MovieState.getInstance().addMovieObserver(this);
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
            if (AppState.getInstance().getCurrentUser() != null) {
                User currentUser = AppState.getInstance().getCurrentUser();
                if (currentUser != null) { //User is logged in, has data stored to autofill
                    cardNum = String.valueOf(currentUser.getCardNumber());
                    cardDate = currentUser.getCardExpiry();
                    cardCVV = currentUser.getCardCvv();

                    //Autofill fields
                    cardNumPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Number: ", dataTitleFont, 20, cardNum, null);
                    cardDatePanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card Expiration Date: ", dataTitleFont, 20, cardDate, null);
                    cardCVVPanel = DecoratorHelpers.makeLabeledField(Color.BLACK, "Credit / Debit Card CVV: ", dataTitleFont, 20, cardCVV, null);
                }
            }
            
            //Make header and footer
            JPanel headerPanel = new HeaderPanel();
            FooterPanel footerPanel = new FooterPanel("paymentConfirm");

            //Shopping cart panel
            JPanel purchaseInfoPanel = new JPanel(new GridLayout(2, 1, 10, 0));
            purchaseInfoPanel.add( createSectionPanel(
                new JLabel[] {itemPurchasedTitle, itemPurchasedLabel},
                FlowLayout.CENTER
            ));

            //Price panel
            purchaseInfoPanel.add( createSectionPanel(
                new JLabel[] {priceTitle, priceLabel},
                FlowLayout.CENTER
            ));

            //Ticket panel, only if tickets being purchased
            JPanel purchaseAndTicketPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            JPanel ticketInfoPanel = new JPanel(new GridLayout(2, 1, 10, 0));
            JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            if (ticketFlag) { //Check if tickets being purchased, add sections if so
                ticketInfoPanel.add(createSectionPanel(
                    new JLabel[] {amountTitle, amountLabel},
                    FlowLayout.CENTER
                ));
                ticketInfoPanel.add(createSectionPanel(
                    new JLabel[] {ticketListTitle, ticketListLabel},
                    FlowLayout.CENTER
                ));

                //Poster panel with movie poster
                posterPanel.add(posterLabel);
                 // Two rows: one for purchase info, one for ticket info
                purchaseAndTicketPanel.add(purchaseInfoPanel);
                purchaseAndTicketPanel.add(ticketInfoPanel);
            }

            //Payment input fields
            JPanel paymentDetailsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            paymentDetailsPanel.add(cardNumPanel);
            paymentDetailsPanel.add(cardDatePanel);
            paymentDetailsPanel.add(cardCVVPanel);

            //Main content panel, with gridbaglayout for custom grid placement
            JPanel contentPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); //Ad spacing between components

            //Poster panel (spanning 2 rows)
            gbc.gridx = 0; //Column 0 (left side)
            gbc.gridy = 0; //Row 0
            gbc.gridheight = 2; //Spans 2 rows
            gbc.fill = GridBagConstraints.BOTH; //Fill space both vertically and horizontally
            gbc.weightx = 0.4; //Allocate less horizontal space compared to the ticket panel
            gbc.weighty = 1.0; //Equal vertical weight
            contentPanel.add(posterPanel, gbc);

            //Ticket information panel (right side, row 0)
            gbc.gridx = 1; //Column 1 (right side)
            gbc.gridy = 0; //Row 0
            gbc.gridheight = 1; //Occupies only 1 row
            gbc.weightx = 0.6; //Allocate more horizontal space
            contentPanel.add(purchaseAndTicketPanel, gbc);

            //Payment details panel (right side, row 1)
            gbc.gridx = 1; //Column 1 (right side)
            gbc.gridy = 1; //Row 1
            gbc.gridheight = 1; //Occupies only 1 row
            contentPanel.add(paymentDetailsPanel, gbc);

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

    /**Handles updates to payment info, used for refreshing page info */
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

    /**For movie poster updating*/
    @Override
    public void onMovieSelected(String key, Object value) {
        switch (key) {
            case "moviePoster":
                posterImage = loadImage((String) value);
                updateContent();
                break;
            default:
                break;
        }
    }

    /**Helper function for loading image 
     * @param path The path of the image to be loaded
     * @return The image that was loaded from the path
    */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Update the page content based on observers */
    private void updateContent() {
        SwingUtilities.invokeLater(() -> {
           priceLabel.setText("$" + String.valueOf(price));
           amountLabel.setText(String.valueOf(amount));
           itemPurchasedLabel.setText(itemPurchased);
           ticketListLabel.setText(ticketList);
           if (posterImage != null) {
                Image scaledImage = posterImage.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                posterLabel.setText("Image not found");
                posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        });
    }

    /**For updating the page when swapping from registration to tickets */
    private void refreshPage() {
        JPanel newPage = createPage();
        Window.getInstance().addPanel("PaymentPage", newPage);  
    }

    /**Makes a titled label with placement, background and spacings
     * @param labels The components you want to add
     * @param alignment The alignment you want the returned panel to have
     * @return A formatted panel with title and label
     */
    private JPanel createSectionPanel(JLabel[] labels, int alignment) {
        JPanel sectionPanel = new JPanel(new FlowLayout(alignment, 10, 10)); 
        sectionPanel.setBackground(Color.WHITE);
        for (JLabel label : labels) {
            sectionPanel.add(label);
        }
        return sectionPanel;
    }
}