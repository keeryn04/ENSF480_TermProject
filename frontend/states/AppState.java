package frontend.states;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.DatabaseAccessor;
import backend.Movie;
import backend.Screen;
import backend.Showtime;
import backend.User;

/**Used to store data that will be used in the system (Like a cache to store database queried data)*/
public class AppState {
    private static AppState instance; // Singleton instance

    private Map<Integer, Movie> movies;
    private Map<Integer, Screen> screens;
    private ArrayList<String> userEmails;
    private Map<Integer, Showtime> showtimes;

    DateTimeFormatter formatter;

    /**Initialize storage components for all data stored, and loads those containers with database data.*/
    private AppState() {
        movies = new HashMap<>();
        screens = new HashMap<>();
        showtimes = new HashMap<>();
        userEmails = new ArrayList<String>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        loadMovies();
        loadScreens();
        loadShowtimes();
        loadUserEmails();
    }

    /**Returns single instance of AppState*/
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    //Getters
    public Map<Integer, Movie> getMovies() {
        return movies;
    }

    public Map<Integer, Screen> getScreens() {
        return screens;
    }

    public Map<Integer, Showtime> getShowtimes() {
        return showtimes;
    }

    public ArrayList<String> getUserEmails() {
        return userEmails;
    }

    /**Loads movies from database into appstate.*/
    private void loadMovies() {
        int movieId = 1;
        Movie movie;
        while ((movie = DatabaseAccessor.getMovieDetails(movieId)) != null) {
            movies.put(movie.getMovieId(), movie);
            movieId++;
        }
    }

    /**Loads screens from database into appstate.*/
    private void loadScreens() {
        int screenId = 1;
        Screen screen;
        while ((screen = DatabaseAccessor.getScreenDetails(screenId)) != null) {
            screens.put(screen.getScreenId(), screen);
            screenId++;
        }
    }

    /**Loads emails from database into appstate.*/
    private void loadUserEmails() {
        int userId = 1;
        String email;
        while ((email = DatabaseAccessor.getUserEmail(userId)) != null) {
            userEmails.add(email);
            userId++;
        }
    }

    /**Loads showtimes from database into appstate.*/
    private void loadShowtimes() {
        int showtimeId = 1;
        Showtime showtime;
        while ((showtime = DatabaseAccessor.getShowtimeDetails(showtimeId)) != null) {
            showtimes.put(showtimeId, showtime);
            showtimeId++;
        }
    }
}