package Model;

import Interfaces.DBAccess;
import Interfaces.DeleteUser;

import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-16
 */
public class DBAccessImpl implements DeleteUser, DBAccess {
    @Override
    public Bike insertNewBike(Bike newBike) {
        return AccessBike.insertNewBike(newBike);
    }

    @Override
    public Bikes selectAvailableBikes() {
       return AccessBike.selectAvailableBikes();
    }

    @Override
    public ArrayList<BikeUser> searchUserByWildcard(String search) {
        return null;
    }

    @Override
    public ArrayList<Bike> selectBikeByTypeBrandColor(String brand, String color, BikeType type) {
        return null;
    }

    @Override
    public BikeUser alterUser(String userName, String passw, BikeUser updatedUser) {
        return null;
    }

    @Override
    public ArrayList<String> getAllUserNames() {
        return null;
    }

    @Override
    public boolean deleteBike(int bikeID) {
        return AccessBike.deleteBike(bikeID);
    }

    @Override
    public int averageLoanPerUser() {
        return 0;
    }

    @Override
    public BikeUser logIn(String userName, String passW) throws SQLException  {
      BikeUser LoginUser = null;
        LoginUser = AccessUser.loginUser(userName, passW);
      return LoginUser;
    }

    @Override
    public boolean DeleteUser(String fname, String lname, int memberlevel, String email, int phone, String username, String passw) {
        return false;
    }

  @Override
  public boolean isUserAvalible(String userName) throws SQLException{

    return AccessUser.isUserAvalible(userName);
  }

    @Override
  public boolean insertNewUser(String fname, String lname, int memberlevel, Year year, String email, int phone, String username, String gender, String passw)  {
        return AccessUser.insertNewUser(fname, lname, memberlevel, year, email, phone, username, gender, passw);
      }

    @Override
    public int insertPrestandaMesaurment(PrestandaMeasurement prestandaMeasurement) {
        return AcccesPrestandaMesaurment.insertMesaurment(prestandaMeasurement);

    }

    @Override
  public ArrayList<Bike> getAllBikes() {
    return AccessBike.getAllBikes();
  }

  @Override
  public boolean UpdateUser(String fName, String lName, int in_memberlevel, String email, int phone, String userName, String gender, String password) throws SQLException {
    return AccessUser.UpdateUser(fName, lName, in_memberlevel, email, phone, userName, gender, password);
  }

  @Override
  public Bike executeBikeLoan(int bikeID, int userID) {
    return AccessBike.executeBikeLoan(bikeID, userID);
  }

  @Override
  public Map<String, Integer> getSearchValue(String text) {
    return AccessBike.getSearchValue(text);
  }

  @Override
  public Bike getBikeByID(int bikeID) {
    return AccessBike.getBikeByID(bikeID);
  }

  @Override
  public ArrayList<Bike> getUsersCurrentBikes(int userID) {
    return AccessRentbridge.getUsersCurrnetBikes(userID);
  }

  @Override
  public ArrayList<Integer> getUsersTotalLoan(int userID) {
    return AccessRentbridge.getUsersTotalLoan(userID);
  }

    @Override
    public ArrayList<Bike> getCurrentBikesByUserID(int userID) {
        return AccessBike.getCurrentBikesByUserID(userID);
    }

    @Override
    public boolean startSession(String auth, int userID) {
        return AccessUser.startSession(auth, userID);
    }

    @Override
    public String readSessionToken(int userID) {
        return AccessUser.readSessionToken(userID);
    }

    @Override
    public void closeSession(int userID) {
        AccessUser.closeSession(userID);
    }

    @Override
    public boolean isSessionOpen(int userID) {
        return AccessUser.isSessionOpen(userID);
    }

    @Override
    public int getTotalNumOfbikes() {
        return AccessBike.getTotalNumOfBikes();
    }

    @Override
    public int getNumOfCurrentAvailableBikes() {
        return AccessBike.getNumOfCurrentAvailableBikes();
    }

}
