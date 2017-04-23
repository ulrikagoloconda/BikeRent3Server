package Model;

import helpers.AuthHelper;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;

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

    public static BikeUser loginUser(String userName, String tryPassW) {
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
            if (userID > 0) {
                if (rs.next()) {
                  System.out.println(rs.getInt("userID"));
                  returnUser.setUserID(rs.getInt("userID"));
                    returnUser.setfName(rs.getString("fname"));
                    returnUser.setlName(rs.getString("lname"));
                    returnUser.setUserName(rs.getString("username"));
                    returnUser.setEmail(rs.getString("email"));
                    returnUser.setMemberLevel(rs.getInt("memberlevel"));
                    returnUser.setPhone(rs.getInt("phone"));
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


    public static boolean insertNewUser(String fname, String lname, int memberlevel, Year year, String email, int phone, String username, String gender, String passw) {
        //in_fname varchar(50),in_lname varchar(11),in_memberlevel varchar(11), in_birth_year YEAR, in_email varchar(50),in_phone varchar(11),
        // in_username varchar(11), in_gender ENUM('Male', 'Female', 'Other'), salt_in VARCHAR(40),in_passw varchar(50))
        String SQLInsertUser = "SELECT insert_new_user(?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        ResultSet rs = null;
        DBType dataBase = null;
        String genderSQL = null;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try ( //only in java 7 and later!!
              Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
              PreparedStatement stmt = conn.prepareStatement(SQLInsertUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ) {
            //in_fname varchar(50),in_lname varchar(11),in_memberlevel varchar(11), in_birth_year YEAR, in_email varchar(50),in_phone varchar(11),
            // in_username varchar(11), in_gender ENUM('Male', 'Female', 'Other'), salt_in VARCHAR(40),in_passw varchar(50))

            if(gender.equals("Kvinna")){
                genderSQL = "Female";
            }else if (gender.equals("Man")){
                genderSQL = "Male";
            } else {
                genderSQL = "Other";
            }
            String salt = AuthHelper.generateValidationToken();
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setInt(3, memberlevel);
            LocalDate ld = LocalDate.of(year.getValue(),10,1);
            stmt.setDate(4,Date.valueOf(ld));
            stmt.setString(5, email);
            stmt.setInt(6, phone);
            stmt.setString(7, username);
            stmt.setString(8, genderSQL);
            stmt.setString(9,salt);
            stmt.setString(10, passw);
            rs = stmt.executeQuery();
            int nrFound = 0;
            while (rs.next()) {
                boolean isAddOK = rs.getBoolean(1);
                int intisadd = rs.getInt(1);
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


    public static boolean UpdateUser(String fname, String lname, int memberlevel, String email, int phone, String username, String gender, String passw) {
        String SQLInsertUser = "CALL update_user(?, ?, ?, ?, ?, ?, ?,?)";
        System.out.println("Gender i access User " + gender);
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
            stmt.setString(7, gender);
            stmt.setString(8, passw);

           /* IN `in_fname`  VARCHAR(50), IN `in_lname` VARCHAR(50), IN `in_memberlevel` VARCHAR(50),
                    IN `in_email`  VARCHAR(50), IN `in_phone` VARCHAR(50), IN `in_username` VARCHAR(50),
                    IN `in_gender` VARCHAR(50), IN `in_passw` VARCHAR(50)*/

            rs = stmt.executeQuery();
            int nrFound = 0;
            while (rs.next()) {
                boolean isAddOK = false;
                int confirm = rs.getInt("confirm");
                if(confirm>0){
                    isAddOK = true;
                }
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


    public static boolean startSession(String auth, int userID) {
        boolean returnBool = false;
        DBType dataBase = null;
        Connection conn = null;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try {
            String sql = "INSERT INTO active_session (session_token, userID, opened) VALUES (?,?,now())";
            conn = DBUtil.getConnection(dataBase);
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, auth);
            stmt.setInt(2, userID);
            LocalDate ld = LocalDate.now();
            Date d = Date.valueOf(ld);
            returnBool = stmt.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        }
        return returnBool;
    }

    public static String readSessionToken(int userID) {
        String returnString = null;
        DBType dataBase = null;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try {
            String sql = "SELECT session_token FROM active_session WHERE userID=? AND closed is null";
            Connection conn = DBUtil.getConnection(dataBase);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                returnString = rs.getString("session_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static boolean closeSession(int userID) {
        boolean returnBool = false;
        DBType dataBase = null;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try {
            String sql = "UPDATE active_session SET closed=now() WHERE userID=? AND closed IS NULL";
            Connection conn = DBUtil.getConnection(dataBase);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            returnBool = stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnBool;
    }

    public static boolean isSessionOpen(int userID) {
        boolean returnBool = false;
        DBType dataBase = null;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try {
            String sql = "SELECT session_token FROM active_session WHERE userID=? AND closed is null";
            Connection conn = DBUtil.getConnection(dataBase);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
          ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                returnBool= true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnBool;
    }
}
