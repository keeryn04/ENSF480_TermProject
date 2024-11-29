package backend;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Showtime {
    private Integer showtimeId;
    private Screen screen;
    private ArrayList<Seat> seatmap;
    private LocalDateTime screeningTime;

    public Showtime() {}

    public Showtime(Integer showtimeId, Screen screen, LocalDateTime screeningTime, ArrayList<Seat> seatmap) {
        this.showtimeId = showtimeId;
        this.screen = screen;
        this.screeningTime = screeningTime;
        this.seatmap = seatmap;
    }

    //Setters and Getters
    public Integer getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Integer showtimeId) { this.showtimeId = showtimeId; }
    public Screen getScreen() { return screen; }
    public void setScreen(Screen screen) { this.screen = screen; }
    public LocalDateTime getScreeningTime() { return screeningTime; }
    public void setScreeningTime(LocalDateTime screeningTime) { this.screeningTime = screeningTime; }
    public ArrayList<Seat> getSeatMap() { return seatmap; }
    public void setSeatMap(ArrayList<Seat> seatmap) { this.seatmap = seatmap; }
}

