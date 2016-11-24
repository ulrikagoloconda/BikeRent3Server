package Interfaces;

import Model.Bike;
import Model.BikeType;
import Model.BikeUser;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-16
 */
public interface DBAccess {
    int insertNewBike(Bike newBike);
    ArrayList<Bike> selectAvailableBikes();
    ArrayList<BikeUser> searchUserByWildcard(String search);
    ArrayList<Bike> selectBikeByTypeBrandColor(String brand, String color, BikeType type);
    BikeUser alterUser(String userName, String passw, BikeUser updatedUser);
    ArrayList<String> getAllUserNames();
    boolean deleteBike(int bikeID);
    int averageLoanPerUser();
    BikeUser logIn(String userName, String passW) throws SQLException;
    boolean isUserAvalible(String userName) throws SQLException;
    boolean InsertNewUser(String fname, String lname, int memberlevel, String email, int phone, String username, String passw) throws SQLException;
    ArrayList<Bike> getAllBikes();
    boolean UpdateUser(String fName, String lName, int in_memberlevel, String email, int phone, String userName, String password)throws SQLException;
    String executeBikeLoan(int bikeID, int userID);
    Map<String,Integer> getSearchValue(String text);
    Bike getBikeByID(int bikeID);
    ArrayList<Integer> getUsersCurrentBikes(int userID);
    ArrayList<Integer> getUsersTotalLoan(int userID);
    ArrayList<Bike>getCurrentBikesByUserID(int userID);
}
