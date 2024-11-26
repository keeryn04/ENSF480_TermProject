package frontend.panels;

import javax.swing.*;

import frontend.decorators.DecoratorHelpers;
import frontend.pages.PaymentSuccessPage;
import frontend.pages.Window;
import frontend.states.AppState;
import frontend.states.ErrorState;
import frontend.states.PaymentState;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FooterPanel extends JPanel {
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
        BUTTON_ACTIONS.put("movieTicket", e -> Window.getInstance().showPanel("SeatMapPage"));
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
        BUTTON_ACTIONS.put("editInfo", e -> Window.getInstance().showPanel("ProfileEditPage"));
        BUTTON_ACTIONS.put("paymentConfirm", e -> {
            Window.getInstance().showPanel("PaymentSuccessPage");
            PaymentSuccessPage.getInstance().processPaymentSuccess(AppState.getInstance().getCurrentUser());
        });
    }

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

        // Register as an ErrorObserver to update the footer dynamically
        ErrorState.getInstance().addErrorObserver(this::updateErrorLabel);
    }

    private JButton createBackButton(Font font) {
        JButton backButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, "Back to Home", font);
        backButton.addActionListener(e -> {
            Window.getInstance().showPanel("Home");
            ErrorState.getInstance().clearError();
        });
        
        return backButton;
    }

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

    private JLabel createErrorLabel() {
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return errorLabel;
    }

    private void updateErrorLabel(String errorMessage) {
        errorLabel.setText(errorMessage.isEmpty() ? "" : "Error Occurred: " + errorMessage);
    }
}