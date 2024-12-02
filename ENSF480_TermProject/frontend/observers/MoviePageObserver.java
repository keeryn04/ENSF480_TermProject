package frontend.observers;

/**Handles changes that occur in MovieState and update accordingly. */
public interface MoviePageObserver {
    void onMovieSelected(String key, Object value);
}
