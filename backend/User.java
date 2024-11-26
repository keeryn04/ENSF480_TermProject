package backend;

public class User {
    private String name;
    private String email;
    private String password;
    private String address;
    private int card_number;
    private boolean is_registered;
    private double credit_balance;
    private String last_payment_date;

    public User(String name, String email, String password, String address, int card_number,
            boolean is_registered, double credit_balance,
            String last_payment_date) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.card_number = card_number;
        this.is_registered = is_registered;
        this.credit_balance = credit_balance;
        this.last_payment_date = last_payment_date;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public int getCardNumber() {
        return card_number;
    }

    public boolean getRegisteredStatus() {
        return is_registered;
    }

    public String getLastPaymentDate() {
        return last_payment_date;
    }

    public double getCreditBalance() {
        return credit_balance;
    }
}