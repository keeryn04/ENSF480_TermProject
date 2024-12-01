package frontend.pages;
//Main file for GUI displaying
import javax.swing.*;

import java.awt.*;
import java.util.HashMap;

/**Singleton class that is used to display each page*/
public class Window {
    private static Window instance;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HashMap<String, JPanel> panels;

    /**Initializes instance of window, with the title, size and behaviour of the window.*/
    private Window() {
        //Make frame if it hasn't been made yet, starts on homepage
        if (frame == null) { 
            frame = new JFrame("Acmeplex");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);
            panels = new HashMap<>();
            frame.add(cardPanel);
        }
    }

    /**Returns the window instance that can be changed for each window layout*/
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
        if (panels.containsKey(name)) {
            panels.remove(name);  
        }
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
            frame.revalidate();
            frame.setVisible(true);
        }
    }

    /**Makes instances of all the pages for the lifetime of the program, and adds them to the card list for the window. */
    public void makePages() {
        Window window = Window.getInstance();

        HomePage home = new HomePage();
        JPanel homePanel = home.createPage();
        window.addPanel("Home", homePanel);
        window.showPanel("Home"); //Default starting page

        LoginPage login = LoginPage.getInstance();
        JPanel loginPanel = login.createPage();
        window.addPanel("LoginPage", loginPanel);  

        SignUpPage signUp = SignUpPage.getInstance();
        JPanel signUpPanel = signUp.createPage();
        window.addPanel("SignUpPage", signUpPanel);

        ProfilePage profile = ProfilePage.getInstance();
        JPanel profilePanel = profile.createPage();
        window.addPanel("ProfilePage", profilePanel);

        RegisterPage register = RegisterPage.getInstance();
        JPanel registerPanel = register.createPage();
        window.addPanel("RegisterPage", registerPanel);

        EditProfilePage profileEdit = new EditProfilePage();
        JPanel profileEditPanel = profileEdit.createPage();
        window.addPanel("ProfileEditPage", profileEditPanel);

        MoviePage movie = MoviePage.getInstance();
        JPanel moviePanel = movie.createPage();
        window.addPanel("MoviePage", moviePanel);      

        SeatMapPage seatmap = SeatMapPage.getInstance();
        JPanel seatPanel = seatmap.createPage();
        window.addPanel("SeatMapPage", seatPanel);        

        PaymentPage payment = PaymentPage.getInstance();
        JPanel paymentPanel = payment.createPage();
        window.addPanel("PaymentPage", paymentPanel);   

        PaymentSuccessPage paymentSuccess = PaymentSuccessPage.getInstance();
        JPanel paymentSuccessPanel = paymentSuccess.createPage();
        window.addPanel("PaymentSuccessPage", paymentSuccessPanel);  
    }

    /**Refreshes all the pages, used with changing login / registered status.*/
    public void refreshPages() {
        Window window = Window.getInstance();

        HomePage home = new HomePage();
        JPanel homePanel = home.createPage();
        window.addPanel("Home", homePanel);

        LoginPage login = LoginPage.getInstance();
        JPanel loginPanel = login.createPage();
        window.addPanel("LoginPage", loginPanel);  
        
        SignUpPage signUp = SignUpPage.getInstance();
        JPanel signUpPanel = signUp.createPage();
        window.addPanel("SignUpPage", signUpPanel);

        ProfilePage profile = ProfilePage.getInstance();
        JPanel profilePanel = profile.createPage();
        window.addPanel("ProfilePage", profilePanel);

        RegisterPage register = RegisterPage.getInstance();
        JPanel registerPanel = register.createPage();
        window.addPanel("RegisterPage", registerPanel);

        EditProfilePage profileEdit = new EditProfilePage();
        JPanel profileEditPanel = profileEdit.createPage();
        window.addPanel("ProfileEditPage", profileEditPanel);

        MoviePage movie = MoviePage.getInstance();
        JPanel moviePanel = movie.createPage();
        window.addPanel("MoviePage", moviePanel);      

        SeatMapPage seatmap = SeatMapPage.getInstance();
        JPanel seatPanel = seatmap.createPage();
        window.addPanel("SeatMapPage", seatPanel);        

        PaymentPage payment = PaymentPage.getInstance();
        JPanel paymentPanel = payment.createPage();
        window.addPanel("PaymentPage", paymentPanel);   

        PaymentSuccessPage paymentSuccess = PaymentSuccessPage.getInstance();
        JPanel paymentSuccessPanel = paymentSuccess.createPage();
        window.addPanel("PaymentSuccessPage", paymentSuccessPanel);  
    }
}