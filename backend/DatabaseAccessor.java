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

        return movieDetails;
    }
}
