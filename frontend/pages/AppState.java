package frontend.pages;

import java.util.HashMap;
import java.util.Map;

/**Used to store data that will be used in the system (Like a cache to store database queried data)*/
public class AppState {
    private static AppState instance; //Singleton instance

    private Map<String, String[]> movies;
    private Map<Integer, Integer[]> screens;

    private AppState() {
        movies = new HashMap<>();
        movies.put("Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Venom"});
        movies.put("Other Venom", new String[]{"./frontend/images/Venom.jpg", "Description for Other Venom"});
        movies.put("This Venom", new String[]{"./frontend/images/Venom.jpg", "Description for This Venom"});
        movies.put("That Venom", new String[]{"./frontend/images/Venom.jpg", "Description for That Venom"});

        screens = new HashMap<>();
        screens.put(1, new Integer[]{5, 5});
        screens.put(2, new Integer[]{1, 1});
        screens.put(3, new Integer[]{10, 1});
        screens.put(4, new Integer[]{8, 8});
    }

    //Singleton management
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public Map<String, String[]> getMovies() {
        return movies;
    }

    public Map<Integer, Integer[]> getScreens() {
        return screens;
    }
    
}
