package backend;

public class User {
    private int id;
    private String name;
    private String email;
    private String address;
    private int card_number;
    private boolean is_registered;
    private String account_recharge;
    private double credit_balance;

    public User(int id, String name, String email, int card_number, boolean is_registered, String account_recharge, double credit_balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.card_number = card_number;
        this.is_registered = is_registered;
        this.account_recharge = account_recharge;
        this.credit_balance = credit_balance;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getAddress() { return address; }
}
