package backend;

public class Seat {
    private String row;
    private Integer column;
    private boolean booked;

    public Seat() {}

    public Seat(String row, Integer column, boolean booked) {
        this.row = row;
        this.column = column;
        this.booked = booked;
    }

    public String getRow() { return row; }
    public void setRow(String row) { this.row = row; }
    public Integer getColumn() { return column; }
    public void setColumn(Integer column) { this.column = column; }
    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }
}
