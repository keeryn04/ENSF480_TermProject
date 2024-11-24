package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.PaymentPageObserver;

public class PaymentState {
    private static PaymentState instance;
    private final List<PaymentPageObserver> observersPayment = new ArrayList<>();

    //Data stored in PaymentState for page-to-page use
    private String cardCVV; //CardNum and CardDate stored in ProfileState
    private String itemPurchased;
    private int amount;
    private double price;
    private String ticketList;

    //Singleton management
    public static PaymentState getInstance() {
        if (instance == null) {
            instance = new PaymentState();
        }
        return instance;
    }

    //Add PaymentPage objects as an observer for payment-related changes
    public void addPaymentObserver(PaymentPageObserver observer) {
        observersPayment.add(observer);
    }

    //Notify on Payment data changes
    private void notifyPaymentObservers(String key, Object value) {
        for (PaymentPageObserver observer : observersPayment) {
            observer.onPaymentConfirmed(key, value);
            System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    //Setters and Getters
    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
        notifyPaymentObservers("paymentCVV", cardCVV);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        notifyPaymentObservers("paymentAmount", amount);
    }

    public String getItemPurchased() {
        return itemPurchased;
    }

    public void setItemPurchased(String itemPurchased) {
        this.itemPurchased = itemPurchased;
        notifyPaymentObservers("paymentItemPurchased", itemPurchased);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyPaymentObservers("paymentPrice", price);
    }

    public String getTicketList() {
        return ticketList;
    }

    //Formats into single string of list of tickets
    public void setTicketList(ArrayList<String> ticketList) {
        if (ticketList.size() > 1) {
            this.ticketList = String.join(", ", ticketList);
        } else {
            this.ticketList = ticketList.get(0); //Only one item
        }

        notifyPaymentObservers("paymentTicketList", this.ticketList);
    }
}
