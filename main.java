import javax.swing.JPanel;

import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        Window window = Window.getInstance();

        HomePage home = new HomePage();
        JPanel homePanel = home.createPage();
        window.addPanel("Home", homePanel);
        window.showPanel("Home");  

        ProfilePage profile = new ProfilePage();
        JPanel profilePanel = profile.createPage();
        window.addPanel("ProfilePage", profilePanel);

        EditProfilePage editProfile = new EditProfilePage();
        JPanel editProfilePanel = editProfile.createPage();
        window.addPanel("ProfileEditPage", editProfilePanel);

        MoviePage movie = MoviePage.getInstance();
        JPanel moviePanel = movie.createPage();
        window.addPanel("MoviePage", moviePanel);      

        window.showWindow();
    }
}

//Compile
//javac Main.java frontend/pages/*.java frontend/decorators/*.java

//Run
//java Main