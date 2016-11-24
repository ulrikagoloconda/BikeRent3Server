package Model;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-22
 */
public class AccessRentbridge {

  public static ArrayList<Integer> getUsersCurrnetBikes(int userID) {
    ArrayList<Integer> bikes = new ArrayList<>();
    DBType dataBase = null;
    Connection conn = null;
    Date dayOfReturn = null;
    String returnSring = "";
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try {
      conn = DBUtil.getConnection(dataBase);
      conn.setAutoCommit(false);
      String sql = "CALL users_current_bikes(?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, userID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        bikes.add(rs.getInt("bikeID"));
      }
      conn.commit();

    } catch (Exception e) {
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return bikes;
  }

  public static ArrayList<Integer> getUsersTotalLoan(int userID) {
    ArrayList<Integer> bikes = new ArrayList<>();
    DBType dataBase = null;
    Connection conn = null;
    Date dayOfReturn = null;
    String returnSring = "";
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try {
      conn = DBUtil.getConnection(dataBase);
      conn.setAutoCommit(false);
      String sql = "CALL users_total_loan(?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, userID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        bikes.add(rs.getInt("bikeID"));
      }
      conn.commit();
    } catch (Exception e) {
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return bikes;
  }
}
