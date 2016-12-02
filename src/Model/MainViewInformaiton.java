package Model;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class MainViewInformaiton {
    private BikeUser currentUser;
    private String authToken;
    private int totalBikes;
    private int rentedBikes;

    public MainViewInformaiton(){

    }

    public BikeUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(BikeUser currentUser) {
        this.currentUser = currentUser;
    }

    public int getTotalBikes() {
        return totalBikes;
    }

    public void setTotalBikes(int totalBikes) {
        this.totalBikes = totalBikes;
    }

    public int getRentedBikes() {
        return rentedBikes;
    }

    public void setRentedBikes(int rentedBikes) {
        this.rentedBikes = rentedBikes;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

