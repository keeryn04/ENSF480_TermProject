import frontend.pages.*;

public class Main {
    public static void main(String[] args) {
        Window window = Window.getInstance();

        window.makePages();

        window.showWindow();
    }
}

//Compile
//javac Main.java frontend/pages/*.java frontend/decorators/*.java

//Run
//java Main