package backend;

public class Ticket {
    private int ticketId;
    private int userId;
    private int showtimeId;
    private String row;
    private int column;

    public Ticket(int ticketId, int userId, int showtimeId, String row, int column) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.row = row;
        this.column = column;
    }

    // Getters
    public int getTicketId() { return ticketId; }
    public int getUserId() { return userId; }
    public int getShowtimeId() { return showtimeId; }
    public String getRow() { return row; }
    public int getColumn() { return column; }
}
