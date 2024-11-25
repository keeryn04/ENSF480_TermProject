package backend;

import java.time.LocalDateTime;

public class Showtime {
    private int showtimeId;
    private int movieId;
    private int screenId;
    private LocalDateTime screeningTime;

    public Showtime(int showtimeId, int movieId, int screenId, LocalDateTime screeningTime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.screenId = screenId;
        this.screeningTime = screeningTime;
    }

    // Getters
    public int getShowtimeId() { return showtimeId; }
    public int getMovieId() { return movieId; }
    public int getScreenId() { return screenId; }
    public LocalDateTime getScreeningTime() { return screeningTime; }
}
