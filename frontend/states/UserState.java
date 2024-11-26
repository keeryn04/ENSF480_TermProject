package frontend.states;

import java.util.ArrayList;
import java.util.List;

import backend.DatabaseAccessor;
import backend.User;
import frontend.observers.LoginPageObserver;
import frontend.observers.ProfilePageObserver;

public class UserState {
    private static UserState instance;
    private final List<LoginPageObserver> observersLogin = new ArrayList<>();
    private final List<ProfilePageObserver> observersProfile = new ArrayList<>();

    // Data fields for user information
    private User user;

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    // Add LoginPage objects as an observer for movie-related changes
    public void addLoginPageObserver(LoginPageObserver observer) {
        observersLogin.add(observer);
    }

    public void addProfilePageObserver(ProfilePageObserver observer) {
        observersProfile.add(observer);
    }

    // Notify on Login data changes
    private void notifyUserObservers(String key, Object value) {
        for (LoginPageObserver observer : observersLogin) {
            observer.onLoginChange(key, value);
            // System.out.println("Notified Observer: " + observer + " about " + key + " & "
            // + value);
        }
        for (ProfilePageObserver observer : observersProfile) {
            observer.onProfileEdited(key, value);
        }
    }

    public User getUser() {
        return user;
    }

    public void logInUser(String email, String password) {
        user = DatabaseAccessor.loginUser(email, password);
        notifyUserObservers("User", user);
    }

    public void logOutUser() {
        user = null;
        notifyUserObservers("User", user);
    }
}