package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles database interactions */
public class DatabaseAccessor {

    /**
     * Fetches movie details by movieId.
     * 
     * @param movieId the ID of the movie to fetch.
     * @return a List containing title, genre, duration, rating, posterPath, and
     *         description.
     */
    public static Movie getMovieDetails(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description FROM Movies WHERE movie_id = ?";

        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    int duration = resultSet.getInt("duration");
                    double rating = resultSet.getDouble("rating");
                    String posterPath = resultSet.getString("poster_path");
                    String description = resultSet.getString("description");

                    return new Movie(title, genre, duration, rating, posterPath, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetches screen details by screenId.
     * 
     * @param screenId the ID of the creen to fetch.
     * @return a Screen instance containing rows, cols.
     */
    public static Screen getScreenDetails(int screenID) {
        String query = "SELECT screen_rows, screen_cols FROM Screens WHERE screen_id = ?";

        try (Connection conn = DatabaseConfig.connect();
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, screenID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int rows = resultSet.getInt("screen_rows");
                    int cols = resultSet.getInt("screen_cols");

                    return new Screen(rows, cols);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
                            resultSet.getInt("card_number"),
                            resultSet.getBoolean("is_registered"),
                            resultSet.getString("account_recharge"),
                            resultSet.getDouble("credit_balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}