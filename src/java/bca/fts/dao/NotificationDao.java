package bca.fts.dao;

import java.sql.*;
import org.apache.log4j.*;

import bca.fts.util.*;

public class NotificationDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(NotificationDao.class);

    // this method counts the no of files to display as notification
    public static int giveNotification(String branch) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        int newFiles = 0;

        String sql = "SELECT COUNT(*) FROM files WHERE curLoc = ? AND ( status=\"PENDING\" OR status=\"REJECTED\")";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, branch);

            res = ps.executeQuery();

            // get the no of incoming files
            if (res.next()) {
                newFiles = res.getInt(1);
            }

        } catch (Exception e) {
            logger.error(e);

            newFiles = -1;          // database error
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return newFiles;
    }

}
