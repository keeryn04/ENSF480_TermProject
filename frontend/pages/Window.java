package frontend.pages;
//Main file for GUI displaying
import javax.swing.*;

import java.awt.*;
import java.util.HashMap;

/**Singleton class that is used with all pages. Gets updated with PageBuilder and PanelDecorator*/
public class Window {
    private static Window instance;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HashMap<String, JPanel> panels;

    private Window() {
        //Make frame if it hasn't been made yet, starts on homepage
        if (frame == null) { 
            frame = new JFrame("Acmeplex");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);
            panels = new HashMap<>();
            frame.add(cardPanel);
        }
    }

    /**Returns a window that can be changed for each window layout*/
    public static Window getInstance() {
        //No instance of the window made yet
        if (Window.instance == null) {
            Window.instance = new Window();
        }
        
        return Window.instance;
    }
    
    /**Add new panel to card list 
     * @param name Name of the panel
     * @param panel Panel object to add
    */
    public void addPanel(String name, JPanel panel) {
        panels.put(name, panel);
        cardPanel.add(panel, name);
    }

    /**Shows the page specified on the window. 
     * @param name Page name to set the stage to
     * */
    public void showPanel(String name) {
        if (panels.containsKey(name)) {
            cardLayout.show(cardPanel, name);
        } else {
            System.err.println("Panel " + name + " does not exist.");
        }
    }

    /**Display the window on the screen*/
    public void showWindow() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        }
    }
}