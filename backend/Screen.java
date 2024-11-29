package backend;

public class Screen {
    private Integer cols;
    private Integer screenId;

    public Screen() {}

    public Screen(Integer screenId, Integer cols) {
        this.screenId = screenId;
        this.cols = cols;
    }

    //Getters and setters
    public Integer getScreenId() { return screenId; }
    public void setScreenId(Integer screenId) { this.screenId = screenId; }
    public Integer getCols() { return cols; }
    public void setCols(Integer cols) { this.cols = cols; }
}
