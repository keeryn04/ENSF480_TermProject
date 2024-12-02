package frontend.observers;

/**Handles changes that occur in UserState (Login) and update accordingly. */
public interface LoginPageObserver {
    void onLoginChange(String key, Object value);
}