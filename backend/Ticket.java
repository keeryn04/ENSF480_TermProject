package backend;

public class Ticket {
    private int ticketId;
    private int userId;
    private int showtimeId;
    private String seatLabel;

    public Ticket(int ticketId, int userId, int showtimeId, String seatLabel) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatLabel = seatLabel;
    }

    // Getters
    public int getTicketId() { return ticketId; }
    public int getUserId() { return userId; }
    public int getShowtimeId() { return showtimeId; }
    public String getSeatLabel() { return seatLabel; }
}
