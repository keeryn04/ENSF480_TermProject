import java.util.ArrayList;
import java.util.Arrays;

import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        Window window = Window.getInstance();

        window.makePages();

        window.showWindow();

        //Test Data Setting REMOVE LATER
        ProfileState.getInstance().setName("Abby Anderson");
        ProfileState.getInstance().setAddress("123 Street St.");
        ProfileState.getInstance().setCardNum("1231118291");
        ProfileState.getInstance().setCardDate("11/27");
    }
}

//Compile
//javac Main.java frontend/pages/*.java frontend/decorators/*.java

//Run
//java Main