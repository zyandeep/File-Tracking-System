// this class gives the last ID of the table 'branchdetails' 
package bca.fts.dao;

import java.sql.*;
import org.apache.log4j.*;

import bca.fts.util.*;

public class IDDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(IDDao.class);

    public static int getLastID() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        int lastID = 0;

        String sql = "SELECT MAX(id) FROM branchdetails";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                lastID = rs.getInt(1);
            }

        } catch (Exception e) {
            logger.error(e);

            lastID = -1;                // database error
        } finally {

            // cleane up database resources
            DatabaseConnector.closeResultSet(rs);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return lastID;
    }

}
