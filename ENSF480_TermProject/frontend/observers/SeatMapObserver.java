package frontend.observers;

/**Handles changes that occur in SeatMapState and update accordingly. */
public interface SeatMapObserver {
    void onSeatMapUpdate(String key, Object value);
}
