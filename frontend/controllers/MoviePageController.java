package frontend.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import backend.Movie;
import backend.Showtime;
import frontend.observers.MoviePageObserver;

public class MoviePageController implements PageController {
    private static MoviePageController instance;
    private final List<MoviePageObserver> observersMovie = new ArrayList<>();

    private Movie currentMovie;
    private Showtime currentShowtime;

    //Singleton management
    public static MoviePageController getInstance() {
        if (instance == null) {
            instance = new MoviePageController();
        }
        return instance;
    }

    public void addMovieObserver(MoviePageObserver observer) {
        observersMovie.add(observer);
    }

    private void notifyMovieObservers(Movie value) {
        for (MoviePageObserver observer : observersMovie) {
            observer.onMovieSelected(value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    //Setter and Getter
    public Movie getCurrentMovie() { return currentMovie; }
    public void setCurrentMovie(Movie currentMovie) { this.currentMovie = currentMovie; }

    public void onUpdate() {

    }

    public void onLoad() {
        notifyMovieObservers(currentMovie);
    }

    //Helper for making the showtimes dropdown
    public JComboBox<String> makeDropdownOfShowtimes() {
        JComboBox<String> timeDropdown = new JComboBox<>();
        Map<String, Showtime> showtimeMap = new HashMap<>();

        if (currentMovie.getShowtimes().isEmpty()) {
            timeDropdown.addItem("No Showtimes Available");
        } else {
            for (Showtime showtime : currentMovie.getShowtimes()) {
                LocalDateTime unformattedTime = showtime.getScreeningTime();
                Integer runtimeMinutes = currentMovie.getRuntime();
                String formattedTime = getFormattedScreeningTime(unformattedTime, runtimeMinutes);

                timeDropdown.addItem(formattedTime);
                showtimeMap.put(formattedTime, showtime); //Store map of String screeningTime to object Showtime
            }
        }

        timeDropdown.addActionListener(e -> {
            String selectedShowtime = (String) timeDropdown.getSelectedItem(); //Dropdown item selected
        
            if (selectedShowtime != null && !selectedShowtime.equals("No Showtimes Available")) {
                this.currentShowtime = showtimeMap.get(selectedShowtime);
            }
        });

        return timeDropdown;
    }

    //Helper for MoviePage dropdown formatting
    public String getFormattedScreeningTime(LocalDateTime screeningTime, Integer runtimeMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startTimeFormatted = screeningTime.format(formatter); 
        String endTimeFormatted = screeningTime.plusMinutes(runtimeMinutes).format(formatter);
        return startTimeFormatted + " to " + endTimeFormatted;
    }
}