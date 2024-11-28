package frontend.states;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.ErrorObserver;

public class ErrorState {
    private static ErrorState instance;
    private String errorMessage;
    private final List<ErrorObserver> observers;

    private ErrorState() {
        errorMessage = ""; // Default to no error
        observers = new ArrayList<>();
    }

    public static ErrorState getInstance() {
        if (instance == null) {
            instance = new ErrorState();
        }
        return instance;
    }

    public void addErrorObserver(ErrorObserver observer) {
        observers.add(observer);
    }

    public void removeErrorObserver(ErrorObserver observer) {
        observers.remove(observer);
    }

    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyObservers();
    }

    public void clearError() {
        setError(""); // Clear the error
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void notifyObservers() {
        for (ErrorObserver observer : observers) {
            observer.onErrorUpdated(errorMessage);
        }
    }
}
