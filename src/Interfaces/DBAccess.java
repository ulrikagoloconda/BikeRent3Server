package Interfaces;

import Model.*;


import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-16
 */
public interface DBAccess{
    Bike insertNewBike(Bike newBike);
    Bikes selectAvailableBikes();
    ArrayList<BikeUser> searchUserByWildcard(String search);
    ArrayList<Bike> selectBikeByTypeBrandColor(String brand, String color, BikeType type);
    BikeUser alterUser(String userName, String passw, BikeUser updatedUser);
    ArrayList<String> getAllUserNames();
    boolean deleteBike(int bikeID);
    int averageLoanPerUser();
    BikeUser logIn(String userName, String passW) throws SQLException;
    boolean isUserAvalible(String userName) throws SQLException;
    ArrayList<Bike> getAllBikes();
    boolean UpdateUser(String fName, String lName, int in_memberlevel, String email, int phone, String userName, String gender, String password)throws SQLException;
    Bike executeBikeLoan(int bikeID, int userID);
    Map<String,Integer> getSearchValue(String text);
    Bike getBikeByID(int bikeID);
    ArrayList<Bike> getUsersCurrentBikes(int userID);
    ArrayList<Integer> getUsersTotalLoan(int userID);
    ArrayList<Bike>getCurrentBikesByUserID(int userID);
    boolean startSession(String auth, int userID);
    String readSessionToken(int userID);
    void closeSession(int userID);
    boolean isSessionOpen(int userID);
    int getTotalNumOfbikes();
    int getNumOfCurrentAvailableBikes();
    boolean insertNewUser(String fname, String lname, int memberlevel, Year year, String email, int phone, String username, String gender, String passw);
    int insertPrestandaMesaurment(PrestandaMeasurement prestandaMeasurement);
}
