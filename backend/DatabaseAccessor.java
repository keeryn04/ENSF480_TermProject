package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles database interactions */
public class DatabaseAccessor {

    // Retrieve a Movie by movieId
    public static Movie getMovieDetails(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description FROM Movies WHERE movie_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Movie(
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getInt("duration"),
                            resultSet.getDouble("rating"),
                            resultSet.getString("poster_path"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve a Screen by screenId
    public static Screen getScreenDetails(int screenId) {
        String query = "SELECT screen_cols FROM Screens WHERE screen_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, screenId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Screen(10, resultSet.getInt("screen_cols")); // Rows are fixed at 10
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve a Showtime by showtimeId
    public static Showtime getShowtimeDetails(int showtimeId) {
        String query = "SELECT movie_id, screen_id, screening FROM Showtimes WHERE showtime_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, showtimeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Showtime(
                            showtimeId,
                            resultSet.getInt("movie_id"),
                            resultSet.getInt("screen_id"),
                            resultSet.getTimestamp("screening").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve Tickets by userId
    public static List<Ticket> getTicketsByUser(int userId) {
        String query = "SELECT ticket_id, showtime_id, row, column FROM Tickets WHERE user_id = ?";
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
        String query = "SELECT ticket_id, user_id, row, column FROM Tickets WHERE user_id = ?";
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
                    return new User(resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("address"),
                            resultSet.getInt("card_number"),
                            resultSet.getBoolean("is_registered"),
                            resultSet.getDouble("credit_balance"),
                            resultSet.getString("last_payment_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
