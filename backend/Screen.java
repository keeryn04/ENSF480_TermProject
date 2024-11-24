package backend;

public class Screen {
    private Integer rows;
    private Integer cols;

    public Screen(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    // Getters and setters
    public Integer getRows() { return rows; }
    public Integer getCols() { return cols; }
}