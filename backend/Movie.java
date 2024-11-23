package backend;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int duration;
    private double rating;

    public Movie(int id, String title, String genre, int duration, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
}
