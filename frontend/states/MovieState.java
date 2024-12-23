package frontend.states;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.MoviePageObserver;

/**Used to store movie data that will be used in the system (Like a cache to store current movie data)*/
public class MovieState {
    private static MovieState instance;
    private final List<MoviePageObserver> observersMovie = new ArrayList<>();

    //Movie info
    private Integer movieId;
    private Integer showtimeId;
    private String movieTitle;
    private String movieDetails;
    private String moviePoster;
    private String movieGenre;
    private String movieRating;
    private String movieRuntime;
    private Integer screenNum;
    private String releaseDate;

    /**Returns the single instance of MovieState.*/
    public static MovieState getInstance() {
        if (instance == null) {
            instance = new MovieState();
        }
        return instance;
    }

    /**Default Movie Data (Avoid Null Checks)*/
    private MovieState() {
        movieId = 1;
        showtimeId = 1;
        movieTitle = "Movie Title";
        movieDetails = "Movie Description";
        moviePoster = "Movie Poster";
        movieGenre = "Genre";
        movieRating = "Rating";
        movieRuntime = "123";
        screenNum = 1;
        releaseDate = "2020-10-31";
    }

    /**Add MoviePage objects as an observer for movie-related changes.
     * @param observer The observer to add.
    */
    public void addMovieObserver(MoviePageObserver observer) {
        observersMovie.add(observer);
    }

    /**Notify Observers of movie data changes.
     * @param key The type of the object passed.
     * @param value The actual value being passed.
    */
    private void notifyMovieObservers(String key, Object value) {
        for (MoviePageObserver observer : observersMovie) {
            observer.onMovieSelected(key, value);
            //System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    //Setters and Getters
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
        notifyMovieObservers("movieId", movieId);
    }

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
        notifyMovieObservers("showtimeId", showtimeId);
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

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
        notifyMovieObservers("movieGenre", movieGenre);
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
        notifyMovieObservers("movieRating", movieRating);
    }

    public String getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(String movieRuntime) {
        this.movieRuntime = movieRuntime;
        notifyMovieObservers("movieRuntime", movieRuntime);
    }

    public int getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(int screenNum) {
        this.screenNum = screenNum;
        notifyMovieObservers("screenNum", screenNum);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        notifyMovieObservers("releaseDate", releaseDate);
    }
}