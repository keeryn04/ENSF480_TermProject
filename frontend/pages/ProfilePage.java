package frontend.pages;

import java.util.Arrays;
import java.util.List;

import java.awt.*;

import javax.swing.*;

import frontend.decorators.BackgroundColorDecorator;
import frontend.decorators.DecoratorHelpers;
import frontend.observers.ProfilePageObserver;
import frontend.panels.FooterPanel;
import frontend.panels.HeaderPanel;
import frontend.states.UserState;

import backend.User;
import backend.DatabaseAccessor;
import backend.Movie;
import backend.Showtime;
import backend.Ticket;

/**Instance of the ProfilePage which handles editing user data and updating the database accordingly.*/
public class ProfilePage implements Page, ProfilePageObserver {
    private static ProfilePage instance; // Singleton

    private User user;

    // UI components
    private JLabel emailLabel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel creditBalanceLabel;
    private JLabel cardNumLabel;
    private JLabel cardDateLabel;
    private JPanel contentPanel;
    private JButton registerButton;
    private JButton cancelTicketButton;

    // User stored data
    private List<Ticket> userTickets;
    private JList<String> ticketList;
    private JScrollPane listScroller;

    /**Make default entry labels for the page inputs. Add buttons for registering and cancelling selected tickets.*/
    private ProfilePage() {
        // Fonts and Labels
        Font nameFont = new Font("Times New Roman", Font.BOLD, 24);
        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);

        emailLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Email: ", labelFont);
        nameLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Name: ", nameFont);
        addressLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Address: ", labelFont);
        creditBalanceLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Credit Balance: ", labelFont);
        cardNumLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Credit Card Number: ", labelFont);
        cardDateLabel = DecoratorHelpers.makeLabel(Color.BLACK, "Exiration Date: ", labelFont);
        
        registerButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Register Your Account", labelFont);
        registerButton.addActionListener(e -> {Window.getInstance().showPanel("RegisterPage");});

        cancelTicketButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Cancel Selected Tickets", labelFont);
        cancelTicketButton.addActionListener(e -> {cancelSelectedTickets();});

        ticketList = new JList<String>(); //data has type Object[]

        // Register with UserState and PaymentSuccessPage
        UserState.getInstance().addProfilePageObserver(this);
        PaymentSuccessPage.getInstance().addProfileObserver(this);
    }

    /**Returns the single instance of ProfilePage.*/
    public static ProfilePage getInstance() {
        if (instance == null) {
            instance = new ProfilePage();
        }
        return instance;
    }

    /**Creates the ProfilePage with required elements. 
     * Uses PageBuilder to create the different aspects of the page (Ex. Panel, Label, Button, etc.),
     * and uses Decorators in DecoratiorHelpers to add more functionality to those aspects.
    */
    @Override
    public JPanel createPage() {
        try {
            // Header and Footer Panels
            contentPanel = new JPanel();
            JPanel titlePanel = new HeaderPanel();
            FooterPanel footerPanel = new FooterPanel("editInfo");
            

            // Profile panel
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel = (JPanel) new BackgroundColorDecorator(contentPanel, Color.WHITE).getDecoratedComponent();
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            user = UserState.getInstance().getUser();
            if (user != null) {
                if (user.getRegisteredStatus() == false)    //What to show if the user isn't registered
                {
                    contentPanel.add(nameLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(emailLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(creditBalanceLabel);
                    contentPanel.add(Box.createVerticalStrut(50));
                    contentPanel.add(registerButton);
                }
                else //What to show if the uses is registered
                {
                    contentPanel.add(nameLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(emailLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(addressLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(creditBalanceLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(cardNumLabel);
                    contentPanel.add(Box.createVerticalStrut(10));
                    contentPanel.add(cardDateLabel);
                }
            }
            contentPanel.add(cancelTicketButton);
            // Add labels to the panel
            

            // Combine all panels in the main layout
            JPanel mainPanel = new PanelBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(contentPanel, BorderLayout.CENTER)
                    .addComponent(footerPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Profile Page: %s%n", e.getMessage());
            return null;
        }
    }

    /**Update profile data based on UserState data using observer.*/
    @Override
    public void onProfileEdited(String key, Object value) {
        //React to changes from UserState
        switch (key) {
            case "User":
                user = (User) value;
                updateContent();
                break;
            case "Tickets Bought":
                updateContent();
                break;
            default:
                break;
        }
    }

    /**Updates the ProfilePage data and UI components.*/
    public void updateContent() {
        SwingUtilities.invokeLater(() -> {
            if (UserState.getInstance().getUser() != null) {
                user = UserState.getInstance().getUser();
                if (user.getName() != null) {
                    nameLabel.setText("Hi " + user.getName() + "!");
                } else {
                    nameLabel.setText("Hi Guest!");
                }
                
                emailLabel.setText("Email: " + user.getEmail());
                addressLabel.setText("Address: " + user.getAddress());
                creditBalanceLabel.setText("Credit Balanace: $" + String.valueOf(user.getCreditBalance()));
                cardNumLabel.setText("Credit / Debit Card Number: " + user.getCardNumber());
                cardDateLabel.setText("Credit / Debit Expiration Date: " + user.getCardExpiry());

                updateTicketList();

                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
    }

    /**Retrieves ticket data from the database and updates the users' ticket list accordingly.*/
    private void updateTicketList() {
        userTickets = DatabaseAccessor.getTicketsByUser(UserState.getInstance().getUser().getID());
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (Ticket t : userTickets) {
            Showtime s = DatabaseAccessor.getShowtimeDetails(t.getShowtimeId());
            Movie m = DatabaseAccessor.getMovieDetails(s.getMovieId());
            
            listModel.addElement(m.getTitle() + 
            " | Duration: " + s.getFormattedScreeningTime("yyyy-MM-dd HH:mm", Integer.valueOf(m.getDuration())) + 
            " | Screen: " + s.getScreenId() + 
            " | Seat: " + t.getSeatLabel());
        }
        if (listScroller != null)
            contentPanel.remove(listScroller);
        ticketList = new JList<String>(listModel);
        
        ticketList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ticketList.setLayoutOrientation(JList.VERTICAL);
        ticketList.setVisibleRowCount(-1);

        listScroller = new JScrollPane(ticketList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        contentPanel.add(listScroller);
    }

    /**Cancels the selected ticket, updating the GUI and the database accordingly.*/
    private void cancelSelectedTickets() {
        int[] indexes = ticketList.getSelectedIndices(); // Get selected indices from the list
        double amountOfCreditToAdd = UserState.getInstance().getUser().getCreditBalance();
        // Sort indexes in descending order to avoid shifting indices when removing items
        Arrays.sort(indexes);
        for (int i = indexes.length - 1; i >= 0; i--) {
            int index = indexes[i]; // Get the current index to remove
            DatabaseAccessor.removeTicketById(userTickets.get(index).getTicketId()); // Remove from database
            if (UserState.getInstance().getUser().getRegisteredStatus()) {
                amountOfCreditToAdd += 10.5;
            } else {
                amountOfCreditToAdd += (10.5 * 0.85);
            }
        }
        DatabaseAccessor.updateUserCreditByUserId(amountOfCreditToAdd, UserState.getInstance().getUser().getID());
        updateContent(); // Refresh the UI or content
    }
}