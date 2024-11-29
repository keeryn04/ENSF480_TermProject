package backend;

public class Ticket {
    private Integer ticketId;
    private User user;
    private Showtime showtime;
    private Seat seat;

    public Ticket() {}

    public Ticket(Integer ticketId, User user, Showtime showtime, Seat seat) {
        this.ticketId = ticketId;
        this.user = user;
        this.showtime = showtime;
        this.seat = seat;
    }

    //Setters and Getters
    public Integer getTicketId() { return ticketId; }
    public void setTicketId(Integer ticketId) { this.ticketId = ticketId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Showtime getShowtime() { return showtime; }
    public void setShowtime(Showtime showtime) { this.showtime = showtime; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

}
