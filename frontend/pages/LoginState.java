package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.LoginPageObserver;

public class LoginState {
     private static LoginState instance;
    private final List<LoginPageObserver> observersLogin = new ArrayList<>();

    //Movie info
    private String email;
    private String password;

    public static LoginState getInstance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    //Add LoginPage objects as an observer for movie-related changes
    public void addLoginObserver(LoginPageObserver observer) {
        observersLogin.add(observer);
    }

    //Notify on Login data changes
    private void notifyLoginObservers(String key, Object value) {
        for (LoginPageObserver observer : observersLogin) {
            observer.onLoginChange(key, value);
            System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyLoginObservers("email", email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyLoginObservers("password", password);
    }
}
