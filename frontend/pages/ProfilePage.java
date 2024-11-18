package frontend.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frontend.decorators.DecoratorHelpers;

public class ProfilePage extends DecoratorHelpers implements Page  {
    @Override
    public JPanel createPage() {
        try {
            //Create fonts
            Font titleFont = new Font("Times New Roman", Font.BOLD, 36);
            Font buttonFont = new Font("Times New Roman", Font.BOLD, 24);
            
            //Title panel
            JLabel titleLabel = makeLabel(Color.BLACK, "Profile Page", titleFont);
            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.add(titleLabel);

            //Profile panel
            JButton editInfoButton = makeButton(Color.GRAY, Color.WHITE, "Edit Info", buttonFont);
            JPanel editPanel = new JPanel(new FlowLayout());
            editPanel.add(editInfoButton);

            //Combine all panels in main layout
            JPanel mainPanel = new PageBuilder()
                    .setLayout(new BorderLayout())
                    .addComponent(titlePanel, BorderLayout.NORTH)
                    .addComponent(editPanel, BorderLayout.SOUTH)
                    .build();

            return mainPanel;
        } catch (Exception e) {
            System.out.printf("Error making Profile Page: %s%n", e.getMessage());
            return null;
        }
    }
}
