package Model;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by Goloconda on 2017-04-06.
 */
public class AcccesPrestandaMesaurment {

    public static int insertMesaurment(PrestandaMesaurment prestandaMesaurment) {

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
           /* date,
           total_time_sec,
           perceived_time_available_bikes_sec,
                    db_procedure_sec,
                    read_from_db_jdbc_sec,
                    gson_to_json_sec ,
                    execute_sec,
                    gson_from_json_sec,
                    read_one_bike,
                    comment,
                    total_size_data_mb*/
            String sql = "CALL insert_prestanda_measurement(?,?,?,?,?,?,?,?,?,?)";
            System.out.println("i acces prestanda meaurment " + prestandaMesaurment.getGsonFromJsonSec());
            System.out.println(prestandaMesaurment.getTotalTimeSec());
            CallableStatement cs = conn.prepareCall(sql);
            LocalDate ld = LocalDate.now();
            cs.setDouble(1,prestandaMesaurment.getTotalTimeSec());
            cs.setDouble(2,prestandaMesaurment.getPerceivedTimeAvailableBikesSec());
            cs.setDouble(3, prestandaMesaurment.getDbProcedureSec());
            cs.setDouble(4,prestandaMesaurment.getReadFromDbJdbcSec());
            cs.setDouble(5,prestandaMesaurment.getGsonToJsonSec());
            cs.setDouble(6,prestandaMesaurment.getExecuteSec());
            cs.setDouble(7,prestandaMesaurment.getGsonFromJsonSec());
            cs.setDouble(8, prestandaMesaurment.getReadOneBike());
            cs.setString(9, prestandaMesaurment.getComment());
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
