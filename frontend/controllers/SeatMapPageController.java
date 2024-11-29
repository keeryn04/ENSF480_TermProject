package frontend.controllers;

import java.util.ArrayList;
import java.util.List;

import backend.Showtime;
import frontend.observers.SeatMapObserver;

public class SeatMapPageController implements PageController {
    private static SeatMapPageController instance;
    private final List<SeatMapObserver> observersSeatMap = new ArrayList<>();

    private Showtime currentShowtime;

    //Singleton management
    public static SeatMapPageController getInstance() {
        if (instance == null) {
            instance = new SeatMapPageController();
        }
        return instance;
    }

    public Showtime getCurrentShowtime() { return currentShowtime; }
    public void setCurrentShowtime(Showtime currentShowtime) { this.currentShowtime = currentShowtime; }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUpdate() {

    }
}