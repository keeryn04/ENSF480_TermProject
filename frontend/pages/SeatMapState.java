package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.SeatMapObserver;

public class SeatMapState {
    private static SeatMapState instance;
    private final List<SeatMapObserver> observersSeatMap = new ArrayList<>();

    //Seatmap info
    private int seatRows;
    private int seatCols;

    //Singleton management
    public static SeatMapState getInstance() {
        if (instance == null) {
            instance = new SeatMapState();
        }
        return instance;
    }

    //Add SeatMap objects as an observer for seatmap-related changes
    public void addSeatMapObserver(SeatMapObserver observer) {
        observersSeatMap.add(observer);
    }

    //Notify on Seatmap data changes
    private void notifySeatMapObservers(String key, Object value) {
        for (SeatMapObserver observer : observersSeatMap) {
            observer.onSeatMapUpdate(key, value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    // Getters and setters with notifications
    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
        notifySeatMapObservers("seatRows", seatRows);
    }

    public int getSeatCols() {
        return seatCols;
    }

    public void setSeatCols(int seatCols) {
        this.seatCols = seatCols;
        notifySeatMapObservers("seatCols", seatCols);
    }
}