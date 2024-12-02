package frontend.observers;

/**Handles changes that occur in Appstate and update accordingly. */
public interface AppStateObserver {
    void onAppStateChange(String key, Object value);
}