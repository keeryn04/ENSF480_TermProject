package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles database interactions */
public class DatabaseAccessor {

    // Retrieve a Movie by movieId
    public static Movie getMovie(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description, release_date FROM Movies WHERE movie_id = ?";
        ArrayList<Showtime> showtimes = getShowtimes(movieId);
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Movie(
                            movieId,
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getInt("duration"),
                            resultSet.getDouble("rating"),
                            resultSet.getString("poster_path"),
                            resultSet.getString("description"),
                            resultSet.getString("release_date"), 
                            showtimes
                            );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Showtime> getShowtimes(int movieId) {
        String query = "SELECT showtime_id, screen_id, screening_time FROM Showtime WHERE movie_id = ?";
        ArrayList<Showtime> showtimes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.connect(); 
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int screenId = resultSet.getInt("screen_id");  
                    int showtimeId = resultSet.getInt("showtime_id");
                    Screen screen = getScreen(screenId);
                    ArrayList<Seat> seatmap = getSeatmap(showtimeId, screenId);
                    Showtime showtime = new Showtime(
                            showtimeId,
                            screen,
                            resultSet.getTimestamp("screening_time").toLocalDateTime(),
                            seatmap
                    );

                    showtimes.add(showtime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    public static Screen getScreen(int screenId) {
        String query = "SELECT screen_cols FROM Screens WHERE screen_id = ?";
        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, screenId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Screen(screenId, resultSet.getInt("screen_cols"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Seat> getSeatmap(int showtimeId, int screenId) {
        ArrayList<Seat> seatmap = new ArrayList<>();
        int screenCols = getScreen(screenId).getCols();
        int screenRows = 10; //10 rows for each screen

        //Initialize seatmap with empty Seat objects
        for (int row = 0; row < screenRows; row++) {
            for (int col = 0; col < screenCols; col++) {
                seatmap.add(new Seat(String.valueOf((char) ('A' + row)), col + 1, false));
            }
        }

        //Update booked seats from seatmap
        String query = "SELECT seat_label FROM Tickets WHERE showtime_id = ? AND screen_id = ?";
        try (Connection conn = DatabaseConfig.connect(); 
            PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, showtimeId);
            statement.setInt(2, screenId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String seatLabel = resultSet.getString("seat_label");
                    String row = seatLabel.substring(0, 1); 
                    Integer column = Integer.parseInt(seatLabel.substring(1)); 

                    boolean booked = resultSet.getString("status").equals("booked"); 

                    seatmap.add(new Seat(row, column, booked));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatmap;
    }

    /*
    // Retrieve Tickets by userId
    public static List<Ticket> getTicketsByUser(int userId) {
        String query = "SELECT ticket_id, showtime_id, seat_row, seat_col FROM Tickets WHERE user_id = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(
                            resultSet.getInt("ticket_id"),
                            userId,
                            resultSet.getInt("showtime_id"),
                            resultSet.getString("seat_row"),
                            resultSet.getInt("seat_col")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Retrieve Tickets by showtimeId
    public static List<Ticket> getTicketsByShowtime(int showtimeId) {
        String query = "SELECT ticket_id, user_id, seat_row, seat_col FROM Tickets WHERE showtime_id = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, showtimeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(
                            resultSet.getInt("ticket_id"),
                            resultSet.getInt("user_id"),
                            showtimeId,
                            resultSet.getString("seat_row"),
                            resultSet.getInt("seat_col")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Retrieve Payments by userId
    public static List<Payment> getPaymentsByUser(int userId) {
        String query = "SELECT payment_id, amount, payment_time, method FROM Payments WHERE user_id = ?";
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    payments.add(new Payment(
                            resultSet.getInt("payment_id"),
                            userId,
                            resultSet.getDouble("amount"),
                            resultSet.getTimestamp("payment_time").toLocalDateTime(),
                            resultSet.getString("method")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public static String getUserEmail(int userID) {
        String query = "SELECT email FROM Users WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User loginUser(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PaymentCard paymentCard = new PaymentCard(resultSet.getInt("card_number"),
                    resultSet.getString("card_exp_date"), 
                    resultSet.getString("card_cvv")
                    );
                    
                    return new User(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("address"),
                            resultSet.getInt("card_number"),
                            resultSet.getString("card_exp_date"),
                            resultSet.getString("card_cvv"),
                            resultSet.getBoolean("is_registered"),
                            resultSet.getString("last_payment_date"),
                            resultSet.getDouble("credit_balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Store ticket in the database
    public static void addTicket(User user, int showtimeId, String seatRow, int seatCol) {
        String query = "INSERT INTO Tickets (user_id, showtime_id, seat_row, seat_col) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, user.getID());
            statement.setInt(2, showtimeId);
            statement.setString(3, seatRow);
            statement.setInt(4, seatCol);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfSeatIsTaken(Integer showtimeId, String seatLabel) {
        String query = "SELECT COUNT(*) FROM tickets WHERE showtime_id = ? AND seat_label = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, showtimeId);
            statement.setString(2, seatLabel);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if at least one ticket exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false if there's an error or no ticket found
    }
    */
}