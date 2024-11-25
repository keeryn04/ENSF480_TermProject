package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.PaymentPageObserver;

public class PaymentState {
    private static PaymentState instance;
    private final List<PaymentPageObserver> observersPayment = new ArrayList<>();

    //Data stored in PaymentState for page-to-page use
    private String cardCVV;
    private String itemPurchased;
    private int ticketAmount;
    private double totalPrice;
    private String ticketList;
    private Boolean ticketFlag;
    private double ticketCost;

    //Singleton management
    public static PaymentState getInstance() {
        if (instance == null) {
            instance = new PaymentState();
        }
        return instance;
    }

    private PaymentState() {
        cardCVV = ""; //CardNum and CardDate stored in ProfileState
        itemPurchased = ""; 
        ticketAmount = 0;
        totalPrice = 0; 
        ticketList = "";
        ticketFlag = false;
        ticketCost = 10.50;
    }

    //Add PaymentPage objects as an observer for payment-related changes
    public void addPaymentObserver(PaymentPageObserver observer) {
        observersPayment.add(observer);
    }

    //Notify on Payment data changes
    private void notifyPaymentObservers(String key, Object value) {
        for (PaymentPageObserver observer : observersPayment) {
            observer.onPaymentConfirmed(key, value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
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

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int amount) {
        this.ticketAmount = amount;
        notifyPaymentObservers("paymentAmount", amount);
    }

    public String getItemPurchased() {
        return itemPurchased;
    }

    public void setItemPurchased(String itemPurchased) {
        this.itemPurchased = itemPurchased;
        notifyPaymentObservers("paymentItemPurchased", itemPurchased);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        notifyPaymentObservers("paymentPrice", totalPrice);
    }

    public String getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<String> tickets) {
        this.ticketList = String.join(", ", tickets);
        notifyPaymentObservers("paymentTicketList", ticketList);
    }

    public void setTicketFlag(Boolean ticketFlag) {
        this.ticketFlag = ticketFlag;
        notifyPaymentObservers("paymentTicketFlag", ticketFlag);
    }

    public Boolean getTicketFlag() {
        return ticketFlag;
    }

    // Ticket management
    public void addTicket() {
        setTotalPrice(totalPrice + ticketCost);
        setTicketAmount(ticketAmount + 1);
    }

    public void removeTicket() {
        setTotalPrice(totalPrice - ticketCost);
        setTicketAmount(ticketAmount - 1);
    }

    public void clearTicketsAndSeats() {
        setTicketList(new ArrayList<>());
        setTicketAmount(0);
        setTotalPrice(0.0);
        SeatMapState.getInstance().clearSelectedSeats(); // Clear related SeatMapState
    }

    public void submitTicketConfirm() {
        if (ticketAmount > 0) {
            setTicketFlag(true); // Tickets selected, flag is true
        } else {
            setTicketFlag(false); // No tickets selected
        }
    }
}
