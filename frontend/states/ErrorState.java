package frontend.states;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.ErrorObserver;

/**Used to display dynamic errors on footer of each page.*/
public class ErrorState {
    private static ErrorState instance;
    private String errorMessage;
    private final List<ErrorObserver> observers;

    /**Make initial error message and list of observers.*/
    private ErrorState() {
        errorMessage = ""; // Default to no error
        observers = new ArrayList<>();
    }

    /**Returns single instance of ErrorState.*/
    public static ErrorState getInstance() {
        if (instance == null) {
            instance = new ErrorState();
        }
        return instance;
    }

    /**Add observer to list of Error Observers.
     * @param observer The observer to add
     */
    public void addErrorObserver(ErrorObserver observer) {
        observers.add(observer);
    }

    /**Remove observer to list of Error Observers.
     * @param observer The observer to remove
     */
    public void removeErrorObserver(ErrorObserver observer) {
        observers.remove(observer);
    }

    /**Changes the error message stored.
     * @param errorMessage The message to display.
    */
    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyObservers();
    }

    /**Removes the error message stored.*/
    public void clearError() {
        setError(""); // Clear the error
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**Notification to observers on error data change.*/
    private void notifyObservers() {
        for (ErrorObserver observer : observers) {
            observer.onErrorUpdated(errorMessage);
        }
    }
}
