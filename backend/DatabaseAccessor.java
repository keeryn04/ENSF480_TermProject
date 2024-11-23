package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles database interactions */
public class DatabaseAccessor {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/AcmePlexDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "30170031";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Fetches movie details by movieId.
     * @param movieId the ID of the movie to fetch.
     * @return a List containing title, genre, duration, rating, posterPath, and description.
     */
    public static Movie getMovieDetails(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description FROM Movies WHERE movie_id = ?";

        try (Connection conn = connect();
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

                    return new Movie(movieId, title, genre, duration, rating, posterPath, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
