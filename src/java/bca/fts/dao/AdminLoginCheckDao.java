package bca.fts.dao;

import bca.fts.model.*;
import bca.fts.util.*;
import java.sql.*;
import org.apache.log4j.*;

public class AdminLoginCheckDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminLoginCheckDao.class);

    public static int regisAdmin(String username, String password) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO adminlogin (username, password) VALUES (?, ?)";
        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

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

    // this method will verify the existance of the admin account
    public static int checkAdminAcc() {

        Connection con = null;
        ResultSet res = null;
        Statement st = null;

        int ans = 0;
        String sql = "SELECT * FROM adminlogin";

        try {

            con = DatabaseConnector.getConnection();
            st = con.createStatement();
            res = st.executeQuery(sql);

            if (res.next()) {
                ans = 1;
            }
        } catch (Exception e) {

            // if DB connectivity error than Connection will be null and it will be logged by Database connector 
            logger.error(e);

            // database-related error
            ans = -1;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return ans;
    }

    // this method will verify admin username and password
    public static int authenticate(AdminBean admin) {

        Connection con = null;
        ResultSet res = null;
        PreparedStatement ps = null;

        int result = 0;

        String un = admin.getUserName();
        String pass = admin.getPassword();
        String sql = "SELECT * FROM adminlogin WHERE username=? AND password=?";

        try {

            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, un);
            ps.setString(2, pass);
            res = ps.executeQuery();

            if (res.next()) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (Exception e) {

            // if DB connectivity error than Connection will be null and it will be logged by Database connector 
            logger.error(e);

            // database-related error
            result = -1;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return result;
    }

    // change admin password
    public static int changePassword(String newPass) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE adminlogin SET password = ?";
        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, newPass);

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
