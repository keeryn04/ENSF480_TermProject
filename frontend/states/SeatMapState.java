package frontend.states;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.SeatMapObserver;

/**Manages seat map-related data and notifies observers about changes.*/
public class SeatMapState {
    private static SeatMapState instance;
    private final List<SeatMapObserver> observersSeatMap = new ArrayList<>();
    private final ArrayList<String> selectedSeatList = new ArrayList<>();

    // SeatMap dimensions
    private int screenId;
    private int seatRows = 0;
    private int seatCols = 0;

    private SeatMapState() {}

    /**Returns the single instance of SeatMapState.*/
    public static SeatMapState getInstance() {
        if (instance == null) {
            instance = new SeatMapState();
        }
        return instance;
    }

    /**Add Seatmap-related things as an observer for seatmap-related changes.
     * @param observer The observer to add.
    */    
    public void addSeatMapObserver(SeatMapObserver observer) {
        observersSeatMap.add(observer);
    }

    /**Notify Observers of seatmap data changes.
     * @param key The type of the object passed.
     * @param value The actual value being passed.
    */
    private void notifySeatMapObservers(String key, Object value) {
        for (SeatMapObserver observer : observersSeatMap) {
            observer.onSeatMapUpdate(key, value);
        }
    }

    //Seat selection management
    public ArrayList<String> getSelectedSeats() {
        return selectedSeatList;
    }

    /**Add a seat selected in the seatmap to the current seatmap data, and update payment accordingly.
     * @param seat The seat label of the seat that was selected.
     */
    public void addSelectedSeat(String seat) {
        if (!selectedSeatList.contains(seat)) {
            selectedSeatList.add(seat);
            PaymentState.getInstance().setTicketList(selectedSeatList);
            PaymentState.getInstance().addTicket();
        }
    }

    /**Remove a seat selected in the seatmap to the current seatmap data, and update payment accordingly.
     * @param seat The seat label of the seat that was selected.
     */
    public void removeSelectedSeat(String seat) {
        selectedSeatList.remove(seat);
        PaymentState.getInstance().setTicketList(selectedSeatList);
        PaymentState.getInstance().removeTicket();
    }

    /**Remove all seats that were selected by the user (Cancelled purchase).*/
    public void clearSelectedSeats() {
        selectedSeatList.clear();
    }

    //Setters and Getters
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

    public int getScreenId() {
        return screenId;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public int getSeatCols() {
        return seatCols;
    }
}