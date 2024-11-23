package backend;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private String duration;
    private String rating;
    private String posterPath;
    private String description;

    public Movie(int id, String title, String genre, int duration, double rating, String posterPath, String desc) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = String.valueOf(duration);
        this.rating = String.valueOf(rating);
        this.posterPath = posterPath;
        this.description = desc;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getRating() { return rating; }
    public String getPosterPath() { return posterPath; }
    public String getDescription() { return description; }
}
