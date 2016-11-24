package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class AccessErrorLog {


  public static boolean insertNewError(int userId, String errorMess) {
    String SQLInsertUser = "SELECT insert_new_ErrorEvent(?, ?)";
    ResultSet rs = null;
    DBType dataBase = null;
    if (helpers.PCRelated.isThisNiklasPC()) {
      dataBase = DBType.Niklas;
    } else {
      dataBase = DBType.Ulrika;
    }
    try ( //only in java 7 and later!!
          Connection conn = DBUtil.getConnection(dataBase); //database_user type like ENUM and get PW :-);
          PreparedStatement stmt = conn.prepareStatement(SQLInsertUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
    ) {
      stmt.setInt(1, userId);
      stmt.setString(2, errorMess);
      //date is in he database..
      rs = stmt.executeQuery();
      int nrFound = 0;
      while (rs.next()) {
        boolean isAddOK = rs.getBoolean(1);
        System.out.println("isAddErrorLogOK : " + isAddOK);
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

  private static String getTimestamp() {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    java.util.Date today = Calendar.getInstance().getTime();
    String logTimestamp = df.format(today);
    return logTimestamp;
  }


}

