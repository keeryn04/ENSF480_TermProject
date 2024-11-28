package frontend.states;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.SeatMapObserver;

/**
 * Manages seat map-related data and notifies observers about changes.
 */
public class SeatMapState {
    private static SeatMapState instance;
    private final List<SeatMapObserver> observersSeatMap = new ArrayList<>();
    private final ArrayList<String> selectedSeatList = new ArrayList<>();

    // SeatMap dimensions
    private int screenId;
    private int seatRows = 0;
    private int seatCols = 0;

    // Singleton management
    private SeatMapState() {}

    public static SeatMapState getInstance() {
        if (instance == null) {
            instance = new SeatMapState();
        }
        return instance;
    }

    // Observer management
    public void addSeatMapObserver(SeatMapObserver observer) {
        observersSeatMap.add(observer);
    }

    private void notifySeatMapObservers(String key, Object value) {
        for (SeatMapObserver observer : observersSeatMap) {
            observer.onSeatMapUpdate(key, value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    // Seat selection management
    public ArrayList<String> getSelectedSeats() {
        return selectedSeatList;
    }

    public void addSelectedSeat(String seat) {
        if (!selectedSeatList.contains(seat)) {
            selectedSeatList.add(seat);
            PaymentState.getInstance().setTicketList(selectedSeatList);
            PaymentState.getInstance().addTicket();
        }
    }

    public void removeSelectedSeat(String seat) {
        selectedSeatList.remove(seat);
        PaymentState.getInstance().setTicketList(selectedSeatList);
        PaymentState.getInstance().removeTicket();
    }

    public void clearSelectedSeats() {
        selectedSeatList.clear();
    }

    // Seat map configuration
    public void setScreenId(int screenId) {
        this.screenId = screenId;
        notifySeatMapObservers("screenId", screenId);
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
        notifySeatMapObservers("seatRows", seatRows);
    }

    public void setSeatCols(int seatCols) {
        this.seatCols = seatCols;
        notifySeatMapObservers("seatCols", seatCols);
    }
}