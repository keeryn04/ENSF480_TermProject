package backend;

import java.util.ArrayList;

public class Movie {
    //Movie Data
    private Integer movieId;
    private String title;
    private String genre;
    private Integer runtime;
    private Double rating;
    private String posterPath;
    private String description;
    private String releaseDate;
    private Integer screenNum; //Stores Screen ID for database access
    private ArrayList<Showtime> showtimes;

    public Movie(int movieId, String title, String genre, int runtime, double rating, String posterPath, String description, String releaseDate, Integer screenNum, ArrayList<Showtime> showtimes) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
        this.rating = rating;
        this.posterPath = "./frontend" + posterPath;
        this.description = description;
        this.releaseDate = releaseDate;
        this.screenNum = screenNum;
        this.showtimes = showtimes;
    }
    
    // Getters
    public Integer getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Integer getRuntime() { return runtime; }
    public Double getRating() { return rating; }
    public String getPosterPath() { return posterPath; }
    public String getDescription() { return description; }
    public String getReleaseDate() { return releaseDate; }
    public ArrayList<Showtime> getShowtimes() { return showtimes; }

    //Setters
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.title = movieTitle; }
    public void setMovieDetails(String movieDescription) { this.description = movieDescription; }
    public void setMoviePoster(String moviePosterPath) { this.posterPath = moviePosterPath;}
    public void setMovieGenre(String movieGenre) { this.genre = movieGenre; }
    public void setMovieRating(Double movieRating) { this.rating = movieRating; }
    public void setMovieRuntime(Integer movieRuntime) { this.runtime = movieRuntime; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setShowtimes(ArrayList<Showtime> showtimes) { this.showtimes = showtimes; }

    //Methods
    public void addShowtime(Showtime newShowtime) {
        showtimes.add(newShowtime);
    }

    public void removeShowtime(Showtime deletedShowtime) {
        if (showtimes.contains(deletedShowtime)) {
            showtimes.remove(deletedShowtime);
        }
    }
}