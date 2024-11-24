package backend;

import java.sql.*;

/** Handles database interactions */
public class DatabaseAccessor {

    /**
     * Fetches movie details by movieId.
     * @param movieId the ID of the movie to fetch.
     * @return a Movie instance containing title, genre, duration, rating, posterPath, and description.
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
}
