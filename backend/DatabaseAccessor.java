package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Handles database interactions */
public class DatabaseAccessor {

    /**
     * Fetches movie details by movieId.
     * @param movieId the ID of the movie to fetch.
     * @return a List containing title, genre, duration, rating, posterPath, and description.
     */
    public List<String> getMovieDetails(int movieId) {
        String query = "SELECT title, genre, duration, rating, poster_path, description FROM Movies WHERE movie_id = ?";
        List<String> movieDetails = new ArrayList<>();

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    movieDetails.add(resultSet.getString("title"));
                    movieDetails.add(resultSet.getString("genre"));
                    movieDetails.add(resultSet.getString("duration"));
                    movieDetails.add(resultSet.getString("rating"));
                    movieDetails.add(resultSet.getString("poster_path"));
                    movieDetails.add(resultSet.getString("description"));
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
