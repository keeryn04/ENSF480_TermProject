//Main file for GUI displaying
import javax.swing.*;
import java.awt.*;

/**Singleton class that is used with all pages. Gets updated with PageBuilder and PanelDecorator*/
public class Window {
    private static Window instance;
    private JFrame frame;

    /**Returns a window that can be changed for each window layout*/
    public static Window getInstance() {
        //No instance of the window made yet
        if (Window.instance == null) {
            Window.instance = new Window();
        }
        
        return Window.instance;
    }

    /**Changes the window to the specified page. 
     * First, it clears the page, then it refreshes and repaints with the new page
     * */
    public void setPage(Page page) {
        frame.getContentPane().removeAll(); 
        frame.getContentPane().add(page.createPage());
        frame.revalidate(); 
        frame.repaint();
    }

    /**Makes a window that starts on HomePage*/
    public void makeWindow() {
        //Make frame if it hasn't been made yet, starts on homepage
        if (frame == null) { 
            frame = new JFrame("Acmeplex");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(600, 800);
            frame.setLocationRelativeTo(null);

            setPage(new HomePage());
        }
    }

    /**Display the window on the screen*/
    public void showWindow() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        }
    }
}
