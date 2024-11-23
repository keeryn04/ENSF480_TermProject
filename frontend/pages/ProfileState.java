package frontend.pages;

import java.util.ArrayList;
import java.util.List;

import frontend.observers.ProfilePageObserver;

public class ProfileState {
    private static ProfileState instance;
    private final List<ProfilePageObserver> observersProfile = new ArrayList<>();

    //Data fields for user information
    private String name;
    private String address;
    private String cardNum;
    private String cardDate;

    //Singleton management
    public static ProfileState getInstance() {
        if (instance == null) {
            instance = new ProfileState();
        }
        return instance;
    }

    //Add ProfilePage objects as an observer for profile-related changes
    public void addProfileObserver(ProfilePageObserver observer) {
        observersProfile.add(observer);
    }

    //Notify on Profile data changes
    private void notifyProfileObservers(String key, Object value) {
        for (ProfilePageObserver observer : observersProfile) {
            observer.onProfileEdited(key, value);
            System.out.println("Notified Observer: " + observer + " about " + key + " & " + value);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyProfileObservers("name", name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyProfileObservers("address", address);
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
        notifyProfileObservers("cardNum", cardNum);
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
        notifyProfileObservers("cardDate", cardDate);
    }
}
