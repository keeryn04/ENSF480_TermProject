package backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Showtime {
    private Integer showtimeId;
    private Integer movieId;
    private Integer screenId;
    private LocalDateTime screeningTime;

    public Showtime(Integer showtimeId, Integer movieId, Integer screenId, LocalDateTime screeningTime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.screenId = screenId;
        this.screeningTime = screeningTime;
    }

    // Getters
    public Integer getShowtimeId() { return showtimeId; }
    public Integer getMovieId() { return movieId; }
    public Integer getScreenId() { return screenId; }

    //Helper for MoviePage dropdown formatting
    public String getFormattedScreeningTime(String pattern, Integer runtimeMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String startTimeFormatted = screeningTime.format(formatter); // Format screeningTime
        String endTimeFormatted = screeningTime.plusMinutes(runtimeMinutes).format(formatter);
        return startTimeFormatted + " to " + endTimeFormatted;
    }
}
