package Model;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class MainViewInformaiton {
    private BikeUser currentUser;
    private int totalBikes;
    private int rentedBikes;
    private String searchValue;
    private int singleBikeID;



    public MainViewInformaiton() {

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

    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getSingleBikeID() {
        return singleBikeID;
    }

    public void setSingleBikeID(int singleBikeID) {
        this.singleBikeID = singleBikeID;
    }
}

