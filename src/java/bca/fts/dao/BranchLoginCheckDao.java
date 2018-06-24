package bca.fts.dao;

import bca.fts.model.*;
import bca.fts.util.*;
import java.sql.*;
import org.apache.log4j.*;

public class BranchLoginCheckDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchLoginCheckDao.class);

    public static BranchBean authenticate(String username, String password) {

        Connection con = null;
        ResultSet res = null;
        PreparedStatement ps = null;

        String sql = "SELECT * FROM branchdetails WHERE username=? AND password=?";
        BranchBean branch = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            res = ps.executeQuery();

            // save the branch info in a bean, don't load the password
            if (res.next()) {
                branch = new BranchBean();

                branch.setId(res.getInt(1));
                branch.setOfficeName(res.getString(2));
                branch.setHeadName(res.getString(3));
                branch.setUsername(res.getString(4));
            } else {

                // no registered branch; empty branch bean  
                branch = new BranchBean();
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            branch = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return branch;
    }

    // change branch account password
    public static int changePassword(String username, String newPass) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE branchdetails SET password = ? WHERE username = ?";
        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, newPass);
            ps.setString(2, username);

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            row = -1;        // error while updating
        } finally {

            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

}
