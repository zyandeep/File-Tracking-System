package bca.fts.dao;

import java.util.ArrayList;
import java.sql.*;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.util.*;

public class BranchReportDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchReportDao.class);

    // this will return list of registered files of a particular branch
    public static ArrayList<FileBean> giveFiles(String branch) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        ArrayList<FileBean> bean = new ArrayList<FileBean>();
        FileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, description, status, curLoc FROM files WHERE owner = ? "
                + "ORDER BY fileId DESC";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, branch);
            res = pst.executeQuery();

            if (res.next() == false) {
                // no records found 
                bean = null;
            } else {

                do {
                    file = new FileBean();

                    file.setId(res.getInt(1));
                    file.setNo(res.getString(2));
                    file.setName(res.getString(3));
                    file.setType(res.getString(4));
                    file.setDescription(res.getString(5));
                    file.setStatus(res.getString(6));
                    file.setCurLoc(res.getString(7));

                    bean.add(file);
                } while (res.next());
            }
        } catch (Exception e) {
            logger.error(e);

            // error occured
            bean = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(pst);
            DatabaseConnector.freeConncetion(con);
        }

        return bean;
    }

    // this method returns the list of sent files of a branch
    public static ArrayList<FileBean> giveSentFiles(String branch) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        ArrayList<FileBean> bean = new ArrayList<FileBean>();
        FileBean file = null;

        String sql = " SELECT fileId, fileNo, fileName, sendDates, path, status, curLoc, sender, sendTimes FROM files "
                + "WHERE owner = ? AND status != \"NEW\" AND curLoc IS NOT NULL";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, branch);
            res = pst.executeQuery();

            if (res.next() == false) {
                // no records found 
                bean = null;
            } else {

                do {
                    file = new FileBean();

                    file.setId(res.getInt(1));
                    file.setNo(res.getString(2));
                    file.setName(res.getString(3));

                    // extract only the first token from "sendDates" and  the first token from "sendTimes"
                    file.setDateOfSend(res.getString(4).split("[,]+")[0] + " at " + res.getString(9).split("[,]+")[0]);

                    // extract only the second token from "path"
                    file.setForwardBranch(res.getString(5).split("[,]+")[1]);

                    file.setStatus(res.getString(6));
                    file.setCurLoc(res.getString(7));
                    file.setSender(res.getString(8));

                    // set the Date
                    file.setDate();

                    bean.add(file);
                } while (res.next());
            }
        } catch (Exception e) {
            logger.error(e);

            // error occured
            bean = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(pst);
            DatabaseConnector.freeConncetion(con);
        }

        return bean;
    }

    // this method returns the list of incoming files of a branch
    public static ArrayList<IncomingFileBean> giveIncomingFiles(String branch) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        ArrayList<IncomingFileBean> bean = new ArrayList<IncomingFileBean>();
        IncomingFileBean file = null;

        String sql = " SELECT fileId, fileNo, fileName, fileType, sender, sendDates, status, curLoc, owner, remark, sendTimes FROM files "
                + "WHERE curLoc = ? AND status != \"CLOSED\"";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, branch);
            res = pst.executeQuery();

            while (res.next()) {
                file = new IncomingFileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setSender(res.getString(5));

                // extract the last token from "sendates"
                String[] dates = res.getString(6).split("[,]+");
                int indexD = dates.length - 1;

                // extract the last token from "sendTimes"
                String[] times = res.getString(11).split("[,]+");
                int indexT = times.length - 1;

                file.setDateOfSend(dates[indexD] + " at " + times[indexT]);

                file.setStatus(res.getString(7));
                file.setCurLoc(res.getString(8));
                file.setOwner(res.getString(9));
                file.setRemark(res.getString(10));

                // so that the "view" know the file is recieved
                file.setFileRecieved(file.getStatus().equalsIgnoreCase("PROCESSING"));

                // for sorting perpus, convert String dateOfSend to Date
                file.setDate();

                bean.add(file);
            }
        } catch (Exception e) {
            logger.error(e);

            // error occured
            bean = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(pst);
            DatabaseConnector.freeConncetion(con);
        }

        return bean;
    }

    // this method returns the list of branches
    public static ArrayList<String> giveBranchList() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<String> branchList = new ArrayList<String>();
        String sql = "SELECT DISTINCT branchName FROM branchdetails";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            if (res.next() == false) {
                branchList = null;
            } else {
                do {
                    branchList.add(res.getString(1));

                } while (res.next());
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            branchList = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return branchList;
    }
}
