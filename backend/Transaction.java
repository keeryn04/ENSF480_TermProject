package backend;

import java.time.LocalDateTime;

public class Transaction {
    private Integer paymentID;
    private User performedBy;
    private Double paymentAmount;
    private LocalDateTime paymentTime;
    private String methodOfPayment;

    public Transaction() {}

    public Transaction(Integer paymentID, User performedBy, Double paymentAmount, LocalDateTime paymentTime, String methodOfPayment) {
        this.paymentID = paymentID;
        this.performedBy = performedBy;
        this.paymentAmount = paymentAmount;
        this.paymentTime = paymentTime;
        this.methodOfPayment = methodOfPayment;
    }

    //Setters and Getters
    public Integer getPaymentID() { return paymentID; }
    public void setPaymentID(Integer paymentID) { this.paymentID = paymentID; }
    public User getPerformedBy() { return performedBy; }
    public void setPerformedBy(User performedBy) { this.performedBy = performedBy; }
    public Double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(Double paymentAmount) { this.paymentAmount = paymentAmount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
    public String getMethodOfPayment() { return methodOfPayment; }
    public void setMethodOfPayment(String methodOfPayment) { this.methodOfPayment = methodOfPayment; }
}
