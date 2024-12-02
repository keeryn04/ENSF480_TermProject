package frontend.observers;

/**Handles changes that occur in PaymentState and update accordingly. */
public interface PaymentPageObserver {
    void onPaymentConfirmed(String key, Object value);
}
