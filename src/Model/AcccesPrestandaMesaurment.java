package Model;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by Goloconda on 2017-04-06.
 */
public class AcccesPrestandaMesaurment {

    public static int insertMesaurment(PrestandaMeasurement prestandaMeasurement) {

        DBType dataBase = null;
        Connection conn = null;
        int returnInt = 0;
        if (helpers.PCRelated.isThisNiklasPC()) {
            dataBase = DBType.Niklas;
        } else {
            dataBase = DBType.Ulrika;
        }
        try {
            conn = DBUtil.getConnection(dataBase);
            String sql = "CALL insert_prestanda_measurement(?,?,?,?,?,?,?,?,?,?)";
            CallableStatement cs = conn.prepareCall(sql);
            LocalDate ld = LocalDate.now();
            cs.setDouble(1, prestandaMeasurement.getTotalTimeSec());
            cs.setDouble(2, prestandaMeasurement.getPerceivedTimeAvailableBikesSec());
            cs.setDouble(3, prestandaMeasurement.getDbProcedureSec());
            cs.setDouble(4, prestandaMeasurement.getReadFromDbJdbcSec());
            cs.setDouble(5, prestandaMeasurement.getGsonToJsonSec());
            cs.setDouble(6, prestandaMeasurement.getExecuteSec());
            cs.setDouble(7, prestandaMeasurement.getGsonFromJsonSec());
            cs.setDouble(8, prestandaMeasurement.getReadOneBike());
            cs.setString(9, prestandaMeasurement.getComment());
         cs.registerOutParameter(10, Types.INTEGER);
         cs.execute();
        returnInt = cs.getInt(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnInt;
    }
}
