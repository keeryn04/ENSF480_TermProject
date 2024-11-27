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

/**
 * Used to store data that will be used in the system (Like a cache to store
 * database queried data)
 */
public class AppState {
    private static AppState instance; // Singleton instance

    private Map<Integer, Movie> movies;
    private Map<Integer, Screen> screens;
    private ArrayList<String> userEmails;
    private User currentUser;
    private Map<Integer, Showtime> showtimes;

    DateTimeFormatter formatter;

    private AppState() {
        movies = new HashMap<>();
        screens = new HashMap<>();
        showtimes = new HashMap<>();
        userEmails = null;
        currentUser = null; // If null, user is not logged in
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        currentUser = new User(0, "Apple", "Mapple", "123", "123 Street St.", 123413, "04/28", "734", false, null, 0);

        loadMovies();
        loadScreens();
        loadShowtimes();
        //loadUserEmails();
    }

    // Singleton management
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Map<Integer, Movie> getMovies() {
        return movies;
    }

    public Map<Integer, Screen> getScreens() {
        return screens;
    }

    public Map<Integer, Showtime> getShowtimes() {
        return showtimes;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    /*
    public void logInUser(String email, String password) {
        currentUser = DatabaseAccessor.loginUser(email, password);
    }
    */
    public void logOutUser() {
        currentUser = null;
    }

    private void loadMovies() {
        int movieId = 1;
        Movie movie;
        while ((movie = DatabaseAccessor.getMovieDetails(movieId)) != null) {
            movies.put(movie.getMovieId(), movie);
            movieId++;
        }
    }

    private void loadScreens() {
        int screenId = 1;
        Screen screen;
        while ((screen = DatabaseAccessor.getScreenDetails(screenId)) != null) {
            screens.put(screen.getScreenId(), screen);
            screenId++;
        }
    }
    /*
    private void loadUserEmails() {
        int userId = 1;
        String email;
        while ((email = DatabaseAccessor.getUserEmail(userId)) != null) {
            userEmails.add(email);
        }
    }
        */

    private void loadShowtimes() {
        int showtimeId = 1;
        Showtime showtime;
        while ((showtime = DatabaseAccessor.getShowtimeDetails(showtimeId)) != null) {
            showtimes.put(showtimeId, showtime);
            showtimeId++;
        } 
    }
}