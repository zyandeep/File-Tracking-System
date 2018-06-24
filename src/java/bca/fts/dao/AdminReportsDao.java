package bca.fts.dao;

import java.util.ArrayList;
import java.sql.*;
import org.apache.log4j.*;

import bca.fts.model.*;
import bca.fts.util.*;

public class AdminReportsDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminReportsDao.class);

    // this will return list of registered branches
    public static ArrayList<BranchBean> giveBranches() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<BranchBean> bean = new ArrayList<BranchBean>();
        BranchBean branch = null;

        String sql = "select id, branchName, incharge, username FROM branchdetails ORDER BY branchName ASC";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                branch = new BranchBean();
                branch.setId(res.getInt(1));
                branch.setOfficeName(res.getString(2));
                branch.setHeadName(res.getString(3));
                branch.setUsername(res.getString(4));
                //
                bean.add(branch);
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            bean = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return bean;
    }

    // this will return list of registered files
    public static ArrayList<FileBean> giveRegisteredFiles() {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        ArrayList<FileBean> bean = new ArrayList<FileBean>();
        FileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, description, owner, status, curLoc, sender FROM files ORDER BY fileId DESC";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            res = pst.executeQuery();

            while (res.next()) {
                file = new FileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setDescription(res.getString(5));
                file.setOwner(res.getString(6));
                file.setStatus(res.getString(7));
                file.setCurLoc(res.getString(8));
                file.setSender(res.getString(9));

                bean.add(file);
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            bean = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(pst);
            DatabaseConnector.freeConncetion(con);

        }

        return bean;
    }

    // this will return list of registered files
    public static ArrayList<FileBean> givePendingFiles() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<FileBean> bean = new ArrayList<FileBean>();
        FileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, sender, curLoc, sendDates, sendTimes FROM files "
                + "WHERE status=\"PENDING\"";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                file = new FileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setSender(res.getString(5));
                file.setCurLoc(res.getString(6));

                // extract the last token from "sendDates" and "senTimes" 
                String[] dates = res.getString(7).split("[,]+");
                String[] times = res.getString(8).split("[,]+");

                // 'cause sendDates.length == sendTimes.length
                int index = dates.length - 1;

                file.setDateOfSend(dates[index] + " at " + times[index]);

                // set the date for sorting
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
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return bean;
    }

    // this will return list of registered files
    public static ArrayList<FileBean> giveRejectedFiles() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<FileBean> bean = new ArrayList<FileBean>();
        FileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, sender, curLoc, sendDates, remark, sendTimes FROM files "
                + "WHERE status=\"REJECTED\"";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                file = new FileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setSender(res.getString(5));
                file.setCurLoc(res.getString(6));

                // extract the last token from "sendDates" and "senTimes" 
                String[] dates = res.getString(7).split("[,]+");
                String[] times = res.getString(9).split("[,]+");

                // 'cause sendDates.length == sendTimes.length
                int index = dates.length - 1;

                file.setDateOfSend(dates[index] + " at " + times[index]);

                file.setRemark(res.getString(8));

                // set the date for sorting
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
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);

        }

        return bean;
    }

    // this will return list of processing files
    public static ArrayList<ProcessingFileBean> giveProcessingFiles() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<ProcessingFileBean> bean = new ArrayList<ProcessingFileBean>();
        ProcessingFileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, sender, sendDates, curLoc, recieveDates, owner, sendTimes, recieveTimes "
                + "FROM files WHERE status=\"PROCESSING\"";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                file = new ProcessingFileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setSender(res.getString(5));

                // extract the last token from "sendDates" and "sendTimes
                String sd = res.getString(6);

                if (sd != null) {

                    String[] s_dates = res.getString(6).split("[,]+");
                    String[] s_times = res.getString(10).split("[,]+");

                    int s_index = s_dates.length - 1;

                    sd = s_dates[s_index] + " at " + s_times[s_index];
                }

                file.setDateOfSend(sd);
                file.setCurLoc(res.getString(7));
                file.setOwner(res.getString(9));

                // extract the last token from "recieveDates" and "recieveTimes"
                String[] dates = res.getString(8).split("[,]+");
                String[] times = res.getString(11).split("[,]+");

                // 'cause recieveDates.length == recieveTimes.length
                int index = dates.length - 1;

                file.setDateOfRecieve(dates[index] + " at " + times[index]);

                // set the date for sorting
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
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);

        }

        return bean;
    }

    // this will return list of closed files
    public static ArrayList<ClosedFileBean> giveClosedFiles() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<ClosedFileBean> bean = new ArrayList<ClosedFileBean>();
        ClosedFileBean file = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, owner, sendDates, curLoc, dateOfClose, sendTimes FROM files "
                + "WHERE status=\"CLOSED\"";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                file = new ClosedFileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString(4));
                file.setOwner(res.getString(5));

                // "sendDates", "sendTimes" are array of String. The first element of the arrays is the first "dateOfSend"
                // of the file
                // verify, if "sendDates" is null
                String sd = res.getString(6);
                String time = res.getString(9);

                file.setDateOfSend((sd == null) ? null : sd.split("[,]+")[0] + " at " + time.split("[,]+")[0]);
                file.setCurLoc(res.getString(7));
                file.setDateOClose(res.getString(8));

                // set the date for sorting
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
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);

        }

        return bean;
    }

    // this will return list of registered file types
    public static ArrayList<String> giveFileTypes() {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<String> fileTypes = new ArrayList<String>();

        String sql = "SELECT type FROM filetypes";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            while (res.next()) {
                fileTypes.add(res.getString(1));
            }

        } catch (Exception e) {
            logger.error(e);

            // database-error occured
            fileTypes = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return fileTypes;
    }

}
