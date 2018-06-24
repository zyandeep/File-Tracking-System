package bca.fts.dao;

import java.sql.*;
import bca.fts.model.BranchBean;
import bca.fts.util.*;
import java.util.ArrayList;
import org.apache.log4j.*;

public class BranchDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchDao.class);

    // create a new branch record
    public static int addBranch(BranchBean branch) {

        Connection con = null;
        PreparedStatement ps = null;
        int row = 0;

        String bn = branch.getOfficeName();
        String hn = branch.getHeadName();
        String un = branch.getUsername();
        String pass = branch.getPassword();

        String sql = "INSERT INTO branchdetails( branchName, incharge, username, password)"
                + "values( ?, ?, ?, ?) ";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, bn);
            ps.setString(2, hn);
            ps.setString(3, un);
            ps.setString(4, pass);

            row = ps.executeUpdate();

        } catch (Exception e) {
            logger.error(e);

            // DB error
            row = -1;
        } finally {

            // clean up database resources
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // retrieve a branch record through "id"
    public static BranchBean giveBranchBean(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        BranchBean branch = null;

        String sql = "SELECT * FROM branchdetails WHERE id = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {
                branch = new BranchBean();

                branch.setId(res.getInt(1));
                branch.setOfficeName(res.getString(2));
                branch.setHeadName(res.getString(3));
                branch.setUsername(res.getString(4));
                branch.setPassword(res.getString("password"));
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

    // retrieve a branch "id" from "branchName"
    private static int giveBranchID(String branchName) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT id FROM branchdetails WHERE branchName = ?";
        int branchID = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, branchName);

            res = ps.executeQuery();

            if (res.next()) {
                branchID = res.getInt(1);
            }
        } catch (Exception e) {
            logger.error(e);

        } finally {

            // clean up database resources
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return branchID;
    }

    public static int updateBranch(BranchBean branch) {

        Connection con = null;
        PreparedStatement ps = null;
        int row = 0;

        int id = branch.getId();
        String bn = branch.getOfficeName();
        String hn = branch.getHeadName();
        String un = branch.getUsername();
        String pass = branch.getPassword();

        String sql = "";

        if (pass.isEmpty()) {
            sql = "UPDATE branchdetails SET branchName=?, incharge=?, username=? WHERE id=?";
        } else {
            sql = "UPDATE branchdetails SET branchName=?, incharge=?, username=?, password=? WHERE id=?";
        }

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, bn);
            ps.setString(2, hn);
            ps.setString(3, un);

            // check for the password
            if (pass.isEmpty()) {
                ps.setInt(4, id);
            } else {
                ps.setString(4, pass);
                ps.setInt(5, id);
            }

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            // for duplicate usernames
            row = -1;
        } finally {

            // clean up database resources
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);

        }

        return row;
    }

    // delete a branch record through "branchName" AND "id"
    public static int deleteBranch(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        int row = 0;

        String sql = "DELETE FROM branchdetails WHERE id = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            // error occured
            row = 0;
        } finally {

            // clean up database resources
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // this method returns the list of registered branches
    public static ArrayList<String> giveBranchNames() {

        Connection con = null;
        ResultSet res = null;
        Statement st = null;

        ArrayList<String> branchList = new ArrayList<String>();

        String sql = "SELECT DISTINCT branchName FROM branchdetails ORDER BY branchName ASC";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                branchList.add(res.getString(1));
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            branchList = null;
        } finally {

            // cleane up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return branchList;
    }

    // this method will verify weather a branch is already registered or not 
    public static boolean checkUniqueBranchName(String branchName) {

        Connection con = null;
        ResultSet res = null;
        PreparedStatement pst = null;

        boolean ans = false;

        String sql = "SELECT * FROM branchdetails WHERE branchName = ?";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, branchName);

            res = pst.executeQuery();

            ans = res.next();

        } catch (Exception e) {
            logger.error(e);
        } finally {

            // cleane up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(pst);
            DatabaseConnector.freeConncetion(con);
        }

        return ans;
    }

    // this method will verify weather an username is already registered or not 
    public static boolean checkUniqueUsername(String username) {

        Connection con = null;
        ResultSet res = null;
        PreparedStatement pst = null;

        boolean ans = false;

        String sql = "SELECT * FROM branchdetails WHERE username = ?";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, username);

            res = pst.executeQuery();

            ans = res.next();

        } catch (Exception e) {
            logger.error(e);
        } finally {

            // cleane up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(pst);
            DatabaseConnector.freeConncetion(con);
        }

        return ans;
    }

}
