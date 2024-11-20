package frontend.pages;
//Temp database class
public class AppState {
    private static AppState instance;

    //Theatre info
    private String screenNum;

    //Movie info
    private String movieTitle;
    private String movieDetails;
    private String moviePoster;

    //Seatmap info
    private String seatMapTitle;
    private int seatRows;
    private int seatCols;

    private AppState() {
        screenNum = "1";
        movieTitle = "title";
        movieDetails = "details";
        seatMapTitle = "map title";
        seatRows = 5;
        seatCols = 5;
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public String getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(String screenNum) {
        this.screenNum = screenNum;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(String movieDetails) {
        this.movieDetails = movieDetails;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String imagePath) {
        this.moviePoster = imagePath;
    }

    public String getSeatMapTitle() {
        return seatMapTitle;
    }

    public void setSeatMapTitle(String seatMapTitle) {
        this.seatMapTitle = seatMapTitle;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatCols() {
        return seatCols;
    }

    public void setSeatCols(int seatCols) {
        this.seatCols = seatCols;
    }
}
