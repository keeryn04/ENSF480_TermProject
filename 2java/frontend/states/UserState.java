package frontend.states;

import java.util.ArrayList;
import java.util.List;

import backend.DatabaseAccessor;
import backend.User;
import frontend.observers.LoginPageObserver;
import frontend.observers.ProfilePageObserver;

/**Manages User-related data and notifies observers about changes.*/
public class UserState {
    private static UserState instance;
    private final List<LoginPageObserver> observersLogin = new ArrayList<>();
    private final List<ProfilePageObserver> observersProfile = new ArrayList<>();

    //Data fields for user information
    private User user;

    /**Returns the single instance of UserState.*/
    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    /**Add Profile-related things as an observer for seatmap-related changes.
     * @param observer The observer to add.
    */    
    public void addProfilePageObserver(ProfilePageObserver observer) {
        observersProfile.add(observer);
    }

    /**Notify Observers of login and profile data changes.
     * @param key The type of the object passed.
     * @param value The actual value being passed.
    */
    private void notifyUserObservers(String key, Object value) {
        for (LoginPageObserver observer : observersLogin) {
            observer.onLoginChange(key, value);

        }
        for (ProfilePageObserver observer : observersProfile) {
            observer.onProfileEdited(key, value);
        }
    }

    public User getUser() {
        return user;
    }

    /**Database access for user logging in. Update info with observers
     * @param email The email of the user
     * @param password The password of the user
     * @return The status of logging in (True is successful login)
     */
    public boolean logInUser(String email, String password) {
        user = DatabaseAccessor.loginUser(email, password);
        notifyUserObservers("User", user);
        if (user == null)
            return false; //If the login attempt failed return false
        else
            return true;
    }

    /**Remove login status and notify observers*/
    public void logOutUser() {
        user = null;
        notifyUserObservers("User", user);
    }

    /**Check registered status of user.
     * @return Status of user registration (True is registered).
    */
    public boolean isUserRegistered() {
        if(user.getRegisteredStatus() == true){
            return true;
        }
        else{
            return false;
        }
    }
}