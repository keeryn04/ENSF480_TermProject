package backend;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private long card_number;
    private String card_exp_date;
    private boolean is_registered;
    private double credit_balance;
    private String last_payment_date;

    public User(int id, 
            String name, 
            String email, 
            String password, 
            String address, 
            long card_number,
            String card_exp_date,
            boolean is_registered,
            String account_recharge,
            double credit_balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.card_number = card_number;
        this.card_exp_date = card_exp_date;
        this.is_registered = is_registered;
        this.credit_balance = credit_balance;
        this.last_payment_date = last_payment_date;
    }

    // Getters and setters
    public int getID() {
        return id;
    }

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

    public long getCardNumber() {
        return card_number;
    }

    public String getCardExpiry() {
        return card_exp_date;
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