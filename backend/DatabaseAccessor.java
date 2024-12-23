package backend;

import java.time.LocalDate;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import frontend.states.UserState;

/** Handles database interactions */
public class DatabaseAccessor {

    // Retrieve a Movie by movieId
    public static Movie getMovieDetails(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description, release_date FROM Movies WHERE movie_id = ?";
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
                            resultSet.getString("release_date"));
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
                    return new Screen(screenId, resultSet.getInt("screen_cols")); // Rows are fixed at 10
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
            // public Showtime(Integer showtimeId, Integer movieId, Integer screenId,
            // LocalDateTime screeningTime)
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

    public static void removeTicketById(int id) {
        String query = "DELETE FROM tickets WHERE ticket_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserCreditByUserId(double newCredit, int id) {
        String query = "UPDATE users SET credit_balance = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDouble(1, newCredit);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User u = UserState.getInstance().getUser();
        UserState.getInstance().logInUser(u.getEmail(), u.getPassword());
    }

    // Retrieve Tickets by userId
    public static List<Ticket> getTicketsByUser(int userId) {
        String query = "SELECT ticket_id, showtime_id, seat_label FROM Tickets WHERE user_id = ?";
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
                            resultSet.getString("seat_label")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Retrieve Tickets by showtimeId
    public static List<Ticket> getTicketsByShowtime(int showtimeId) {
        String query = "SELECT ticket_id, user_id, seat_label FROM Tickets WHERE showtime_id = ?";
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
                            resultSet.getString("seat_label")));
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
                    return new User(resultSet.getInt("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("address"),
                            resultSet.getLong("card_number"),
                            resultSet.getString("card_exp_date"),
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

    public static boolean addNewUser(String email, String password) {
        String query = "INSERT INTO Users (email, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void registerUser(String email, String name, String address, Long creditCardNum, String creditCardExpDate) {
        String query = "UPDATE users SET name = ?, address = ?, card_number = ?, is_registered = ?, card_exp_date = ?, last_payment_date = ? WHERE email = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setLong(3, creditCardNum);
            statement.setBoolean(4, true);
            statement.setString(5, creditCardExpDate);
            statement.setDate(6, Date.valueOf(LocalDate.now()));
            statement.setString(7, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserState.getInstance().logInUser(email, UserState.getInstance().getUser().getPassword());
    }

    public static void updateUser(String name, String email, String password, String address, Long creditCardNum, String creditCardExpDate) {
        String query = "UPDATE users SET name = ?, email = ?, password = ?, address = ?, card_number = ?, card_exp_date = ? WHERE email = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, address);
            statement.setLong(5, creditCardNum);
            statement.setString(6, creditCardExpDate);
            statement.setString(7, UserState.getInstance().getUser().getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserState.getInstance().logInUser(email, password);
    }

    // Store ticket in the database
    public static void addTicket(User user, int showtimeId, String seat_label) {
        String query = "INSERT INTO Tickets (user_id, showtime_id, seat_label) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, user.getID());
            statement.setInt(2, showtimeId);
            statement.setString(3, seat_label);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getTakenSeats(Integer showtimeId) {
        ArrayList<String> takenSeats = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE showtime_id = ?";
        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, showtimeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    takenSeats.add(resultSet.getString("seat_label"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return takenSeats;
    }
}