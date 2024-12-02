package frontend.observers;

/**Handles changes that occur in ErrorState and update accordingly. */
public interface ErrorObserver {
    void onErrorUpdated(String errorMessage);
}
