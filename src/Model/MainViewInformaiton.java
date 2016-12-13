package Model;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class MainViewInformaiton {
    private BikeUser currentUser;
    private int totalBikes;
    private int availableBikes;
    private String searchValue;
    private int singleBikeID;
    private int bikeToRentID;
    private BikeUser oldUser;
    private BikeUser alteredUser;
    private Bike newBike;


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

    public int getBikeToRentID() {
        return bikeToRentID;
    }

    public void setBikeToRentID(int bikeToRentID) {
        this.bikeToRentID = bikeToRentID;
    }

    public BikeUser getOldUser() {
        return oldUser;
    }

    public void setOldUser(BikeUser oldUser) {
        this.oldUser = oldUser;
    }

    public BikeUser getAlteredUser() {
        return alteredUser;
    }

    public void setAlteredUser(BikeUser alteredUser) {
        this.alteredUser = alteredUser;
    }

    public Bike getNewBike() {
        return newBike;
    }

    public void setNewBike(Bike newBike) {
        this.newBike = newBike;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }
}


