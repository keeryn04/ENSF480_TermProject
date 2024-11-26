package frontend.panels;

import javax.swing.*;

import frontend.decorators.DecoratorHelpers;
import frontend.pages.Window;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HeaderPanel extends JPanel {
    private JButton leftButton = null;
    private JButton rightButton = null;

    private static final Map<String, String> LEFT_BUTTON_LABELS = new HashMap<>();
    private static final Map<String, String> RIGHT_BUTTON_LABELS = new HashMap<>();

    private static final Map<String, ActionListener> LEFT_BUTTON_ACTIONS = new HashMap<>();
    private static final Map<String, ActionListener> RIGHT_BUTTON_ACTIONS = new HashMap<>();

    static {
        // Labels for when the user is not signed in
        LEFT_BUTTON_LABELS.put("notSignedIn", "Sign Up");
        RIGHT_BUTTON_LABELS.put("notSignedIn", "Login");

        // Labels for when the user is signed in
        LEFT_BUTTON_LABELS.put("signedIn", "Profile");
        RIGHT_BUTTON_LABELS.put("signedIn", "Logout");

        // CHANGE THE SHOW PANEL NAMES WHEN PAGES ARE ADDED! THIS IS FOR TESTING
        // PURPOSES
        // Actions buttons when not signed in
        LEFT_BUTTON_ACTIONS.put("notSignedIn", e -> Window.getInstance().showPanel("LoginPage"));
        RIGHT_BUTTON_ACTIONS.put("notSignedIn", e -> Window.getInstance().showPanel("LoginPage"));

        // Actions for buttons when signed in
        LEFT_BUTTON_ACTIONS.put("signedIn", e -> Window.getInstance().showPanel("LoginPage"));
        RIGHT_BUTTON_ACTIONS.put("signedIn", e -> Window.getInstance().showPanel("LoginPage"));

    }

    public HeaderPanel(String type) {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 24);

        leftButton = createLeftButton(type, buttonFont);
        rightButton = createRightButton(type, buttonFont);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(leftButton, BorderLayout.WEST);
        buttonPanel.add(rightButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.EAST);
        // add(rightButton, BorderLayout.EAST);
    }

    private JButton createLeftButton(String type, Font font) {
        String label = LEFT_BUTTON_LABELS.getOrDefault(type, "");
        ActionListener action = LEFT_BUTTON_ACTIONS.get(type);

        JButton leftButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, label, font);
        if (action != null) {
            leftButton.addActionListener(action);
        } else {
            leftButton.setEnabled(false);
        }
        return leftButton;
    }

    private JButton createRightButton(String type, Font font) {
        String label = RIGHT_BUTTON_LABELS.getOrDefault(type, "");
        ActionListener action = RIGHT_BUTTON_ACTIONS.get(type);

        JButton rightButton = DecoratorHelpers.makeButton(Color.DARK_GRAY, Color.WHITE, label, font);
        if (action != null) {
            rightButton.addActionListener(action);
        } else {
            rightButton.setEnabled(false);
        }
        return rightButton;
    }
}
