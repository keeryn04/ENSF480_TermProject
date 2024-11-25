package frontend.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import backend.DatabaseAccessor;
import backend.Movie;
import backend.Screen;
import backend.User;

/**
 * Used to store data that will be used in the system (Like a cache to store
 * database queried data)
 */
public class AppState {
    private static AppState instance; // Singleton instance

    private Map<String, String[]> movies;
    private Map<Integer, Integer[]> screens;
    private ArrayList<String> userEmails;
    private User currentUser;

    private AppState() {
        movies = new HashMap<>();
        screens = new HashMap<>();
        userEmails = null;
        currentUser = null; // If null, user is not logged in

        loadMovies();
        loadScreens();
        loadUserEmails();
    }

    // Singleton management
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Map<String, String[]> getMovies() {
        return movies;
    }

    public Map<Integer, Integer[]> getScreens() {
        return screens;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logInUser(String email, String password) {
        currentUser = DatabaseAccessor.loginUser(email, password);
    }

    public void logOutUser() {
        currentUser = null;
    }

    private void loadMovies() {
        int movieId = 1;
        Movie movie;
        while ((movie = DatabaseAccessor.getMovieDetails(movieId)) != null) {
            movies.put(movie.getTitle(),
                    new String[] {
                            "./frontend" + movie.getPosterPath(),
                            movie.getDescription(),
                            movie.getGenre(),
                            movie.getRating(),
                            movie.getDuration()
                    });
            movieId++;
        }
    }

    private void loadScreens() {
        int screenId = 1;
        Screen screen;
        while ((screen = DatabaseAccessor.getScreenDetails(screenId)) != null) {
            screens.put(screenId,
                    new Integer[] { screen.getRows(), screen.getCols() });
            screenId++;
        }
    }

    private void loadUserEmails() {
        int userId = 1;
        String email;
        while ((email = DatabaseAccessor.getUserEmail(userId)) != null) {
            userEmails.add(email);
        }
    }
}