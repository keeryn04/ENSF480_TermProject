package frontend.observers;

/**Handles changes that occur in UserState (Profile) and update accordingly. */
public interface ProfilePageObserver {
    void onProfileEdited(String key, Object value);
}