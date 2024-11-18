import javax.swing.JPanel;

import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        Window window = Window.getInstance();

        HomePage home = new HomePage();
        JPanel homePanel = home.createPage();

        ProfilePage profile = new ProfilePage();
        JPanel profilePanel = profile.createPage();

        window.addPanel("Home", homePanel);
        window.showPanel("Home");

        window.addPanel("Profile", profilePanel);

        window.showWindow();
    }
}

//Compile
//javac Main.java frontend/pages/*.java frontend/decorators/*.java

//Run
//java Main