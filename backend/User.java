package backend;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private Integer userID;
    private String name;
    private String email;
    private String password;
    private String address;
    private PaymentCard paymentInfo;
    private boolean isRegistered;
    private LocalDateTime lastPaymentDate;
    private Double creditBalance;
    private ArrayList<Ticket> purchasedTickets;
    private ArrayList<Transaction> transactionHistory;

    public User() {}

    public User(Integer userID, 
                String name, 
                String email, 
                String password, 
                String address, 
                PaymentCard paymentInfo,
                boolean isRegistered,
                ArrayList<Ticket> purchasedTickets,
                ArrayList<Transaction> transactionHistory,
                LocalDateTime lastPaymentDate,
                Double creditBalance) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.paymentInfo = paymentInfo;
        this.isRegistered = isRegistered;
        this.purchasedTickets = purchasedTickets;
        this.transactionHistory = transactionHistory;
        this.creditBalance = creditBalance;
        this.lastPaymentDate = lastPaymentDate;
    }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public PaymentCard getPaymentInfo() { return paymentInfo; }
    public void setPaymentInfo(PaymentCard paymentInfo) { this.paymentInfo = paymentInfo; }
    public boolean isRegistered() { return isRegistered; }
    public void setRegistered(boolean isRegistered) { this.isRegistered = isRegistered; }
    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public void setLastPaymentDate(LocalDateTime lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; }
    public Double getCreditBalance() { return creditBalance; }
    public void setCreditBalance(Double creditBalance) { this.creditBalance = creditBalance; }
    public ArrayList<Ticket> getPurchasedTickets() { return purchasedTickets; }
    public void setPurchasedTickets(ArrayList<Ticket> purchasedTickets) { this.purchasedTickets = purchasedTickets; }
    public ArrayList<Transaction> getTransactionHistory() { return transactionHistory; }
    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) { this.transactionHistory = transactionHistory; }

    private void addTicket(Ticket newTicket) {purchasedTickets.add(newTicket); }
    private void removeTicket(Ticket deletedTicket) {purchasedTickets.remove(deletedTicket); }

    private void addTransaction(Transaction newTransaction) {transactionHistory.add(newTransaction); }
    private void removeTransaction(Transaction deletedTicket) {transactionHistory.remove(deletedTicket); }
}
