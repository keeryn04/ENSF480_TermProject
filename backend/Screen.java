package backend;

public class Screen {
    private int screenId;  // Unique ID for the screen
    private int screenCols;  // Number of columns (seats per row)

    // Constructor
    public Screen(int screenId, int screenCols) {
        this.screenId = screenId;
        this.screenCols = screenCols;
    }

    // Getter for screenId
    public int getScreenId() {
        return screenId;
    }

    // Setter for screenId
    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    // Getter for screenCols
    public int getScreenCols() {
        return screenCols;
    }

    // Setter for screenCols
    public void setScreenCols(int screenCols) {
        this.screenCols = screenCols;
    }
}