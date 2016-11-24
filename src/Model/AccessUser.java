package Model;

import java.sql.*;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class AccessUser {

  public static BikeUser loginUser1(String userName, String passW) throws SQLException {

    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;

    }
    BikeUser logedInBikeUser = new BikeUser();
    logedInBikeUser = getUserinfo(userName, dataBase);
    return logedInBikeUser;

  }

  public static BikeUser loginUser(String userName, String tryPassW)  {
    BikeUser returnUser = new BikeUser();
    int userID = 0;
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }

    try {
      Connection conn = DBUtil.getConnection(dataBase);
      String sql = "CALL check_password_get_bikeuser(?,?,?)";
      CallableStatement cs = conn.prepareCall(sql);
      cs.setString(1, userName);
      cs.setString(2, tryPassW);
      cs.registerOutParameter(3, Types.INTEGER);
      ResultSet rs = cs.executeQuery();
      userID = cs.getInt(3);
        if(userID >0){
            if(rs.next()){
                returnUser.setUserID(rs.getInt("userID"));
                returnUser.setfName(rs.getString("fname"));
                returnUser.setlName(rs.getString("lname"));
                returnUser.setUserName(rs.getString("username"));
                returnUser.setEmail(rs.getString("email"));
                returnUser.setMemberLevel(rs.getInt("memberlevel"));
            }
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnUser;
  }


  public static BikeUser getBikeUserByID(int id) {
    BikeUser returnUser = new BikeUser();
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try {
      Connection conn = DBUtil.getConnection(dataBase);
      String sql = "SELECT fname,lname,memberlevel,email,phone,username FROM bikeuser WHERE userID=?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        returnUser.setUserName(rs.getString("username"));
        returnUser.setEmail(rs.getString("email"));
        returnUser.setlName(rs.getString("lname"));
        returnUser.setfName(rs.getString("fname"));
        returnUser.setMemberLevel(rs.getInt("memberlevel"));
        returnUser.setPhone(rs.getInt("phone"));
        returnUser.setUserID(id);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnUser;
  }


  public static boolean isUserAvalible(String userName) throws SQLException {
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    String SQLQuerygetUserinfo = "{call getUserFromUserName(?)}";
    ResultSet rs = null;
    try ( //only in java 7 and later!!
          Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
          CallableStatement stmt = conn.prepareCall(SQLQuerygetUserinfo);
    ) {
      stmt.setString(1, userName);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        System.out.println("ledig användare");
        return true;
      } else {
        System.out.println("UPPTAGEN användare");
        return false;
      }
    } finally {
      if (rs != null) rs.close();
    }

  }

  private static BikeUser getUserinfo(String userName, DBType dataBase) throws SQLException {
    BikeUser logedInBikeUser = new BikeUser();
    String SQLQuerygetUserinfo = "{call getUserFromUserName(?)}";
    ResultSet rs = null;
    try ( //only in java 7 and later!!
          Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
          CallableStatement stmt = conn.prepareCall(SQLQuerygetUserinfo);
    ) {
      stmt.setString(1, userName);
      rs = stmt.executeQuery();
      rs.next();
      logedInBikeUser.setUserID(rs.getInt("userID"));
      logedInBikeUser.setfName(rs.getString("fname"));
      logedInBikeUser.setlName(rs.getString("lname"));
      logedInBikeUser.setEmail(rs.getString("email"));
      logedInBikeUser.setPhone(rs.getInt("phone"));
      logedInBikeUser.setMemberLevel(rs.getInt("memberlevel"));
      logedInBikeUser.setMemberSince(rs.getDate("membersince").toLocalDate());
      logedInBikeUser.setUserName(rs.getString("username"));
    } finally {
      if (rs != null) rs.close();
    }
    return logedInBikeUser;
  }


  public static boolean insertNewUser(String fname, String lname, int memberlevel, String email, int phone, String username, String passw) {
    String SQLInsertUser = "SELECT insert_new_user(?, ?, ?, ?, ?, ?, ?)";
    ResultSet rs = null;
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try ( //only in java 7 and later!!
          Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
          PreparedStatement stmt = conn.prepareStatement(SQLInsertUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    ) {

      stmt.setString(1, fname);
      stmt.setString(2, lname);
      stmt.setInt(3, memberlevel);
      stmt.setString(4, email);
      stmt.setInt(5, phone);
      stmt.setString(6, username);
      stmt.setString(7, passw);
      rs = stmt.executeQuery();
      int nrFound = 0;
      while (rs.next()) {
        boolean isAddOK = rs.getBoolean(1);
        return isAddOK;
      }
    } catch (SQLException e) {
      DBUtil.processException(e);
      return false;
    } finally {
      if (rs != null) try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return false;
  }


  public static boolean UpdateUser(String fname, String lname, int memberlevel, String email, int phone, String username, String passw) {
    String SQLInsertUser = "SELECT update_user(?, ?, ?, ?, ?, ?, ?)";
    ResultSet rs = null;
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try ( //only in java 7 and later!!
          Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
          PreparedStatement stmt = conn.prepareStatement(SQLInsertUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    ) {
      stmt.setString(1, fname);
      stmt.setString(2, lname);
      stmt.setInt(3, memberlevel);
      stmt.setString(4, email);
      stmt.setInt(5, phone);
      stmt.setString(6, username);
      stmt.setString(7, passw);
      rs = stmt.executeQuery();
      int nrFound = 0;
      while (rs.next()) {
        boolean isAddOK = rs.getBoolean(1);
        return isAddOK;
      }

    } catch (SQLException e) {
      DBUtil.processException(e);
      return false;
    } finally {
      if (rs != null) try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return false;
  }
}

