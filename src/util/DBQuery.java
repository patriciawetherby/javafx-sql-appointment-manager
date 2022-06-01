package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DBQuery {

    private static PreparedStatement pStatement;

    // Create Prepared Statement Object
    /**
     * Setter for the PreparedStatement used in the SQL Query
     * @param conn Connection to the database
     * @param sqlStatement SQL query inserted into the database
     * @throws SQLException SQL Exception
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        pStatement = conn.prepareStatement(sqlStatement);
    }

      // Return Prepared Statement Object
    /**
     * Getter for the PreparedStatement used in the SQL Query
     * @return PreparedStatement for SQL Query
     */
    public static PreparedStatement getPreparedStatement(){
        return pStatement;
    }
}
