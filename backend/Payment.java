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

    // Getters
    public int getPaymentId() { return paymentId; }
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public String getMethod() { return method; }
}
