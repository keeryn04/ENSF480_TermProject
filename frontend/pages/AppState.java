package frontend.pages;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.DatabaseAccessor;
import backend.Movie;

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
        
        screens = new HashMap<>();
        screens.put(1, new Integer[]{5, 5});
        screens.put(2, new Integer[]{1, 1});
        screens.put(3, new Integer[]{10, 1});
        screens.put(4, new Integer[]{8, 8});
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
