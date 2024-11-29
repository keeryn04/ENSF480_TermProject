package backend;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int userId;
    private double amount;
    private LocalDateTime paymentTime;
    private String method;

    public Payment(int paymentId, int userId, double amount, LocalDateTime paymentTime, String method) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.method = method;
    }

    //Getters and setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}

