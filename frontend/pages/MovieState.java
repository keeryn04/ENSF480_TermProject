package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.MoviePageObserver;

public class MovieState {
    private static MovieState instance;
    private final List<MoviePageObserver> observersMovie = new ArrayList<>();

    //Movie info
    private String movieTitle;
    private String movieDetails;
    private String moviePoster;
    private Integer screenNum;

    //Singleton management
    public static MovieState getInstance() {
        if (instance == null) {
            instance = new MovieState();
        }
        return instance;
    }

    //Add MoviePage objects as an observer for movie-related changes
    public void addMovieObserver(MoviePageObserver observer) {
        observersMovie.add(observer);
    }

    //Notify on Movie data changes
    private void notifyMovieObservers(String key, Object value) {
        for (MoviePageObserver observer : observersMovie) {
            observer.onMovieSelected(key, value);
            System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        notifyMovieObservers("movieTitle", movieTitle);
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(String movieDetails) {
        this.movieDetails = movieDetails;
        notifyMovieObservers("movieDetails", movieDetails);
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
        notifyMovieObservers("moviePoster", moviePoster);
    }

    public int getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(int screenNum) {
        this.screenNum = screenNum;
        notifyMovieObservers("screenNum", screenNum);
    }
    
}
