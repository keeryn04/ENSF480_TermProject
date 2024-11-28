package backend;

public class Movie {
    private Integer movieId;
    private String title;
    private String genre;
    private String duration;
    private String rating;
    private String posterPath;
    private String description;
    private String releaseDate;

    public Movie(int movieId, String title, String genre, int duration, double rating, String posterPath, String description, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = String.valueOf(duration);
        this.rating = String.valueOf(rating);
        this.posterPath = "./frontend" + posterPath;
        this.description = description;
        this.releaseDate = releaseDate;
    }
    
    // Getters and setters
    public Integer getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public Integer getDurationInt() { return Integer.parseInt(duration); }
    public String getRating() { return rating; }
    public Double getRatingDouble() { return Double.parseDouble(rating); }
    public String getPosterPath() { return posterPath; }
    public String getDescription() { return description; }
    public String getReleaseDate() { return releaseDate; }
}