package backend;

public class Screen {
    private Integer rows;
    private Integer cols;
    private Integer screenId;

    public Screen(int screenId, int cols) {
        this.screenId = screenId;
        this.rows = 10;
        this.cols = cols;
    }

    // Getters and setters
    public Integer getRows() { return rows; }
    public Integer getCols() { return cols; }
    public Integer getScreenId() { return screenId; }
}