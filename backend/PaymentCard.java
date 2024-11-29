package backend;

public class PaymentCard {
    private Integer cardNumber;
    private String cardExpDate;
    private String cardCVV;

    public PaymentCard() {}

    public PaymentCard(Integer cardNumber, String cardExpDate, String cardCVV) {
        this.cardNumber = cardNumber;
        this.cardExpDate = cardExpDate;
        this.cardCVV = cardCVV;
    }

    public Integer getCardNumber() { return cardNumber; }
    public void setCardNumber(Integer cardNumber) { this.cardNumber = cardNumber; }
    public String getCardExpDate() { return cardExpDate; }
    public void setCardExpDate(String cardExpDate) { this.cardExpDate = cardExpDate; }
    public String getCardCVV() { return cardCVV; }
    public void setCardCVV(String cardCVV) { this.cardCVV = cardCVV; }
}


