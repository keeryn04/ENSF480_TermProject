package backend;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private int card_number;
    private String card_exp_date;
    private String card_cvv;
    private boolean is_registered;
    private String account_recharge;
    private double credit_balance;

    public User(int id, String name, String email, String password, String address, int card_number, String card_exp_date,
            String card_cvv,
            boolean is_registered,
            String account_recharge,
            double credit_balance
            ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.card_number = card_number;
        this.card_exp_date = card_exp_date;
        this.card_cvv = card_cvv;
        this.is_registered = is_registered;
        this.account_recharge = account_recharge;
        this.credit_balance = credit_balance;
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

    public int getCardNumber() {
        return card_number;
    }

    public String getCardExpiry() {
        return card_exp_date;
    }

    public String getCardCvv() {
        return card_cvv;
    }

    public boolean getRegisteredStatus() {
        return is_registered;
    }

    public String getAccountRecharge() {
        return account_recharge;
    }

    public double getCreditBalance() {
        return credit_balance;
    }
}