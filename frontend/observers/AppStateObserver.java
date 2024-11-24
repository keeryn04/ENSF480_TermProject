package frontend.observers;

public interface AppStateObserver {
    void onAppStateChange(String key, Object value);
}