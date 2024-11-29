package frontend.controllers;

import java.util.ArrayList;
import java.util.List;

import backend.Transaction;
import backend.User;
import frontend.observers.PaymentPageObserver;

public class PaymentPageController implements PageController {
    private static PaymentPageController instance;
    private final List<PaymentPageObserver> observersPayment = new ArrayList<>();

    private Transaction currentTransaction;
    private User currentUser;
    private final Double ticketPrice = 5.5;
    private final Integer registerUserPrice = 20;
    
    private String itemPurchased;
    private int ticketAmount;
    private String ticketList;
    private Boolean ticketFlag;
    private double ticketCost;

    //Singleton management
    public static PaymentPageController getInstance() {
        if (instance == null) {
            instance = new PaymentPageController();
        }
        return instance;
    }

    private PaymentPageController() {}

    public void addPaymentObserver(PaymentPageObserver observer) {
        observersPayment.add(observer);
    }

    private void notifyPaymentObservers(Object value) {
        for (PaymentPageObserver observer : observersPayment) {
            observer.onPaymentConfirmed(value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    //Setters and Getters
    public Transaction getCurrentTransaction() { return currentTransaction; }
    public void setCurrentTransaction(Transaction currentTransaction) { this.currentTransaction = currentTransaction; }
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUpdate() {

    }
}