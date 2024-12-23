package frontend.panels;

import javax.swing.*;

import frontend.decorators.DecoratorHelpers;
import frontend.pages.EditProfilePage;
import frontend.pages.PaymentPage;
import frontend.pages.PaymentSuccessPage;
import frontend.pages.Window;
import frontend.states.ErrorState;
import frontend.states.MovieState;
import frontend.states.PaymentState;
import frontend.states.UserState;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**Handles the creation of the footer for each page, made dynamic by entering the type of footer required. */
public class FooterPanel extends JPanel{
    private JButton backButton;
    private JButton rightButton;
    private JLabel errorLabel;

    private static final Map<String, String> BUTTON_LABELS = new HashMap<>();
    private static final Map<String, ActionListener> BUTTON_ACTIONS = new HashMap<>();

    static {
        // Populate button labels
        BUTTON_LABELS.put("movieTicket", "Purchase a Ticket");
        BUTTON_LABELS.put("continuePurchase", "Continue to Payment");
        BUTTON_LABELS.put("confirmInfo", "Confirm");
        BUTTON_LABELS.put("editInfo", "Edit Info");
        BUTTON_LABELS.put("paymentConfirm", "Confirm Purchase");
        BUTTON_LABELS.put("default", null);

        // Populate button actions
        BUTTON_ACTIONS.put("movieTicket", e -> {
            try {
                // Attempt to check the registered status
                if(UserState.getInstance().isUserRegistered() == true){
                    Window.getInstance().showPanel("SeatMapPage");
                }
                else{
                    LocalDate currentDate = LocalDate.now();
                    LocalDate storedDate = LocalDate.parse(MovieState.getInstance().getReleaseDate());

                    if(currentDate.isBefore(storedDate)){
                        ErrorState.getInstance().setError("Tickets Unavailable: Movie Not Released");
                    }
                    else{
                        Window.getInstance().showPanel("SeatMapPage");
                    }
                }
            } catch (Exception f) {
                System.out.printf("User Not Logged In: %s%n", f.getMessage());
                ErrorState.getInstance().setError("Tickets Unavailable: Please Log In");
            }
            if(UserState.getInstance().isUserRegistered() == true)
            Window.getInstance().showPanel("SeatMapPage");
        });
        BUTTON_ACTIONS.put("continuePurchase", e -> {
            PaymentState paymentState = PaymentState.getInstance();
            if (paymentState.getTicketAmount() == 0) {
                ErrorState.getInstance().setError("No Tickets Selected");
            } else {
                PaymentState.getInstance().submitTicketConfirm();
                ErrorState.getInstance().clearError();
                Window.getInstance().showPanel("PaymentPage");
            }
        });
        BUTTON_ACTIONS.put("confirmInfo", e -> Window.getInstance().showPanel("ProfilePage"));
        BUTTON_ACTIONS.put("editInfo", e -> {EditProfilePage.getInstance().refreshPage(); Window.getInstance().showPanel("ProfileEditPage");});
        BUTTON_ACTIONS.put("paymentConfirm", e -> {
            if (PaymentPage.getInstance().confirmPaymentInfo()) {
                Window.getInstance().showPanel("PaymentSuccessPage");
                PaymentSuccessPage.getInstance().processPaymentSuccess(UserState.getInstance().getUser());
            }
        });
    }

    /**Makes the footer panel, dynamic based on type
     * @param type The name of the type required, changes button behaviour accordingly.
     */
    public FooterPanel(String type) {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

        backButton = createBackButton(buttonFont);
        rightButton = createRightButton(type, buttonFont);
        errorLabel = createErrorLabel();

        add(backButton, BorderLayout.WEST);
        add(rightButton, BorderLayout.EAST);
        add(errorLabel, BorderLayout.CENTER);

        //Register as an ErrorObserver to update the footer dynamically with errors
        ErrorState.getInstance().addErrorObserver(this::updateErrorLabel);
    }

    /**Makes the back button for the program footer, constant on every page to retun to home.
     * @param font The font of the button, can be customized on creation
     * @return Returns a JButton that is added to the footer.
     */
    private JButton createBackButton(Font font) {
        JButton backButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Back to Home", font);

        backButton.addActionListener(e -> {
            Window.getInstance().showPanel("Home");
            ErrorState.getInstance().clearError();
        });
        
        return backButton;
    }

    /**Makes the right button for the program footer, dynamic based on the page instance
     * @param type Specifies the behavior of the button, and the text displayed on the button.
     * @param font The font of the button, can be customized on creation.
     * @return Returns a JButton that is added to the footer.
     */
    private JButton createRightButton(String type, Font font) {
        String label = BUTTON_LABELS.getOrDefault(type, ""); // Default to an empty string if type is not found
        ActionListener action = BUTTON_ACTIONS.get(type); // May be null if type is not found

        JButton rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, label, font);
        if (action != null) {
            rightButton.addActionListener(action);
        } else {
            rightButton.setEnabled(false); // Disable button if action is not defined
        }
        return rightButton;
    }

    /**Makes the Error statement to be displayed on the footer.
     * @return Returns a JLabel with basic decoration (Colouring, alignment).
     */
    private JLabel createErrorLabel() {
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return errorLabel;
    }

    /**Changes the text displayed on the error message in the footer.
     * @param errorMessage The text displayed in the error label.
     */
    private void updateErrorLabel(String errorMessage) {
        errorLabel.setText(errorMessage.isEmpty() ? "" : "Error Occurred: " + errorMessage);
    }
}