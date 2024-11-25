package backend;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String cardNumber;
    private double creditBalance;
    private boolean isRegistered;
    private boolean annualFeePaid;
    private String lastPaymentDate;

    // Constructor matching the database fields
    public User(int id, String name, String email, String password, String cardNumber, 
                double creditBalance, boolean isRegistered, boolean annualFeePaid, String lastPaymentDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cardNumber = cardNumber;
        this.creditBalance = creditBalance;
        this.isRegistered = isRegistered;
        this.annualFeePaid = annualFeePaid;
        this.lastPaymentDate = lastPaymentDate;
    }

    // Getters and setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCreditBalance() {
        return creditBalance;
    }
    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isAnnualFeePaid() {
        return annualFeePaid;
    }
    public void setAnnualFeePaid(boolean annualFeePaid) {
        this.annualFeePaid = annualFeePaid;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }
    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }
}
