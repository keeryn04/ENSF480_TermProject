package frontend.pages;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.DatabaseAccessor;
import backend.Movie;
import backend.Screen;

/**Used to store data that will be used in the system (Like a cache to store database queried data)*/
public class AppState {
    private static AppState instance; //Singleton instance

    private Map<String, String[]> movies;
    private Map<Integer, Integer[]> screens;

    private AppState() {
        movies = new HashMap<>();
        screens = new HashMap<>();

        int movieId = 1;
        Movie movie;
        while ((movie = DatabaseAccessor.getMovieDetails(movieId)) != null) {
            movies.put(movie.getTitle(), 
                new String[]{"./frontend" + movie.getPosterPath(), 
                movie.getDescription(), 
                movie.getGenre(), 
                movie.getRating(),
                movie.getDuration()});
            movieId++;
        }
        
        int screenId = 1;
        Screen screen;
        while ((screen = DatabaseAccessor.getScreenDetails(screenId)) != null) {
            screens.put(screenId, 
                new Integer[]{screen.getRows(), screen.getCols()});
            screenId++;
        }
    }

    //Singleton management
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
    
}
