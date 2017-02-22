package Model;
/**
 * @author Niklas Karlsson
 * <h1>DBUtil</h1>
 * {@docRoot}
 * just a  for the SQL-Uter/Pass and path..
 * Created by NIK1114 on 2016-10-16.
 * @version 1.0
 * @param
 * @throws : no
 * @since 2016-10-16
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
private static Map<String,String> credMap;
  private static String USERNAME_Ulrika;
  private static String PASSWORD_Ulrika;
  private static String CONN_STRING_Ulrika;

  private static String USERNAME_Niklas;
  private static String PASSWORD_Niklas;
  private static String CONN_STRING_Niklas;

static {
    credMap = new HashMap<>();
  try {
    FileReader fr = new FileReader("C:/Users/Goloconda/GitHub/BikeRent3Server/img/dbCreds.txt");
    //FileReader fr = new FileReader("Q:/JavaEE_Server_Client/BikeRent3Se/img/dbCreds.txt");
    BufferedReader br = new BufferedReader(fr);
    String temp;
    while ((temp = br.readLine()) != null){
        String [] parts = temp.split("=");
       if(parts.length == 2) {
           credMap.put(parts[0], parts[1]);
       }
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
}


    public static Connection getConnection(DBType dbType) throws SQLException {
    System.out.println("in model/dbutil");
    if (helpers.PCRelated.isThisNiklasPC()) {
      dbType = DBType.Niklas;
    } else {
      dbType = DBType.Ulrika;
    }
    switch (dbType) {
      case Ulrika:
        System.out.println("Ulrikas inloggning");
          try {
              Class.forName("com.mysql.jdbc.Driver");
              return DriverManager.getConnection(credMap.get("CONN_STRING_Ulrika"), credMap.get("USERNAME_Ulrika"), credMap.get("PASSWORD_Ulrika"));

          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }

      case Niklas:
        System.out.println("Niklas inloggning");
        try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
        return DriverManager.getConnection(credMap.get("CONN_STRING_Niklas"), credMap.get("USERNAME_Niklas"), credMap.get("PASSWORD_Niklas"));
      default:
        System.out.println("No database user...");
        return null;
    }
  }

  public static void processException(SQLException e) { //catch SQL fault :-)
    System.err.println("\n-------------------------------------------------------------------------------");
    System.err.println("error message: " + e.getMessage());
    System.err.println("error codee: " + e.getErrorCode());
    System.err.println("SQL state: " + e.getSQLState());
    System.err.println("-------------------------------------------------------------------------------\n");

  }
}
