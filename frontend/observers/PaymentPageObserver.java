package frontend.observers;

public interface PaymentPageObserver {
    void onPaymentConfirmed(String key, Object value);
}