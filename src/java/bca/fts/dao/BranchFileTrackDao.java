package bca.fts.dao;

import java.sql.*;
import java.util.*;
import org.apache.log4j.*;

import bca.fts.util.*;
import bca.fts.model.*;

public class BranchFileTrackDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(BranchFileTrackDao.class);

    // this method verify whether a branch can track a file or not
    public static TrackFileBean isFileTracable(int id, String branch) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        TrackFileBean f = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE fileId = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {

                f = new TrackFileBean();

                // now check whether the branch "touch" the file; verify the "path" field
                if (res.getString(11).contains(branch)) {

                    f.setId(res.getInt(1));
                    f.setNo(res.getString(2));
                    f.setName(res.getString(3));
                    f.setOwner(res.getString(4));
                    f.setStatus(res.getString(5));
                    f.setCurLoc(res.getString(6));
                    f.setSender(res.getString(7));
                    f.setDateOfClose(res.getString(8));
                    f.setRecieveDates(res.getString(9));
                    f.setRecieveTimes(res.getString(10));

                    // extract the date-of-creation
                    f.setDateOfCreation(
                            f.getRecieveDates().split("[,]+")[0] + " at " + f.getRecieveTimes().split("[,]+")[0]
                    );

                    f.setPath(res.getString(11));
                    f.setSendDates(res.getString(12));
                    f.setSendTimes(res.getString(13));

                } else {

                    // f.id == -1 if the request is denied
                    f.setId(-1);
                }

            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            f = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return f;
    }

    // this method returns a list of file records via file no 
    public static ArrayList<TrackFileBean> giveFileRecordViaNo(String no, String branch) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE fileNo = ?";

        ArrayList<TrackFileBean> files = null;
        TrackFileBean f = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, no);

            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                // file record found. Now check whether the branch name includes in the path
                files = new ArrayList<TrackFileBean>();

                do {

                    if (res.getString(11).contains(branch)) {        // yes, the branch touch the file
                        f = new TrackFileBean();

                        f.setId(res.getInt(1));
                        f.setNo(res.getString(2));
                        f.setName(res.getString(3));
                        f.setOwner(res.getString(4));
                        f.setStatus(res.getString(5));
                        f.setCurLoc(res.getString(6));
                        f.setSender(res.getString(7));
                        f.setDateOfClose(res.getString(8));
                        f.setRecieveDates(res.getString(9));
                        f.setRecieveTimes(res.getString(10));

                        // extract the date-of-creation
                        f.setDateOfCreation(
                                f.getRecieveDates().split("[,]+")[0] + " at " + f.getRecieveTimes().split("[,]+")[0]
                        );

                        f.setPath(res.getString(11));
                        f.setSendDates(res.getString(12));
                        f.setSendTimes(res.getString(13));

                        // add the file to the List
                        files.add(f);
                    }

                } while (res.next());
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            files = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return files;
    }

    // this method returns a list of file records via file name 
    public static ArrayList<TrackFileBean> giveFileRecordViaName(String name, String branch) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE fileName = ?";

        ArrayList<TrackFileBean> files = null;
        TrackFileBean f = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, name);

            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                // file record found. Now check whether the branch name includes in the path
                files = new ArrayList<TrackFileBean>();

                do {

                    if (res.getString(11).contains(branch)) {        // yes, the branch touch the file
                        f = new TrackFileBean();

                        f.setId(res.getInt(1));
                        f.setNo(res.getString(2));
                        f.setName(res.getString(3));
                        f.setOwner(res.getString(4));
                        f.setStatus(res.getString(5));
                        f.setCurLoc(res.getString(6));
                        f.setSender(res.getString(7));
                        f.setDateOfClose(res.getString(8));
                        f.setRecieveDates(res.getString(9));
                        f.setRecieveTimes(res.getString(10));

                        // extract the date-of-creation
                        f.setDateOfCreation(
                                f.getRecieveDates().split("[,]+")[0] + " at " + f.getRecieveTimes().split("[,]+")[0]
                        );

                        f.setPath(res.getString(11));
                        f.setSendDates(res.getString(12));
                        f.setSendTimes(res.getString(13));

                        // add the file to the List
                        files.add(f);
                    }

                } while (res.next());
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            files = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return files;
    }

    // this method return a list of file records that a branch can track on the basis of "date-of-send"
    public static ArrayList<TrackFileBean> giveFilesDoS(String dos, String branch) {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<TrackFileBean> files = null;
        String[] branches = null;
        String[] dates = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE sendDates IS NOT NULL";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            if (res.next() == false) {
                return files;
            } else {

                files = new ArrayList<TrackFileBean>();
                TrackFileBean f = null;

                boolean isFound;

                // record found. so iterate over them/it
                do {

                    isFound = false;

                    String path = res.getString(11);
                    String date = res.getString(12);

                    String tempBranch[] = path.split("[,]+");
                    String tempDates[] = date.split("[,]+");

                    if (path.contains(branch) && date.contains(dos)) {

                        for (int i = 0; i < tempDates.length; i++) {
                            if (tempDates[i].equalsIgnoreCase(dos) && tempBranch[i].equalsIgnoreCase(branch)) {

                                isFound = true;

                                break;
                            }
                        }

                    }

                    if (isFound) {

                        f = new TrackFileBean();
                        f.setId(res.getInt(1));
                        f.setNo(res.getString(2));
                        f.setName(res.getString(3));
                        f.setOwner(res.getString(4));
                        f.setStatus(res.getString(5));
                        f.setCurLoc(res.getString(6));
                        f.setSender(res.getString(7));
                        f.setDateOfClose(res.getString(8));
                        f.setRecieveDates(res.getString(9));
                        f.setRecieveTimes(res.getString(10));

                        // extract the date-of-creation
                        f.setDateOfCreation(
                                f.getRecieveDates().split("[,]+")[0] + " at " + f.getRecieveTimes().split("[,]+")[0]
                        );

                        f.setPath(res.getString(11));
                        f.setSendDates(res.getString(12));
                        f.setSendTimes(res.getString(13));

                        // set the file's "DoS"
                        f.setDateOfSend(dos);

                        // now add the record to the List
                        files.add(f);
                    }

                } while (res.next());

            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            files = null;

        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return files;
    }

    // this method return a list of file records that a branch can track on the basis of "date-of-send"
    public static ArrayList<TrackFileBean> giveFilesDoR(String dor, String branch) {

        Connection con = null;
        Statement st = null;
        ResultSet res = null;

        ArrayList<TrackFileBean> files = null;
        String[] branches = null;
        String[] dates = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files ";

        try {
            con = DatabaseConnector.getConnection();
            st = con.createStatement();

            res = st.executeQuery(sql);

            if (res.next() == false) {
                return files;
            } else {

                files = new ArrayList<TrackFileBean>();
                TrackFileBean f = null;

                boolean isFound;

                // record found. so iterate over them/it
                do {

                    isFound = false;

                    // first check "path" and "recieveDates" and then verify whether the given branch is located at that index
                    // where the given date is found
                    String path = res.getString(11);
                    String date = res.getString(9);

                    String tempBranch[] = path.split("[,]+");
                    String tempDates[] = date.split("[,]+");

                    if (path.contains(branch) && date.contains(dor)) {

                        for (int i = 0; i < tempDates.length; i++) {

                            if (tempDates[i].equalsIgnoreCase(dor) && tempBranch[i].equalsIgnoreCase(branch)) {

                                isFound = true;
                                break;
                            }
                        }

                    }

                    if (isFound) {

                        f = new TrackFileBean();
                        f.setId(res.getInt(1));
                        f.setNo(res.getString(2));
                        f.setName(res.getString(3));
                        f.setOwner(res.getString(4));
                        f.setStatus(res.getString(5));
                        f.setCurLoc(res.getString(6));
                        f.setSender(res.getString(7));
                        f.setDateOfClose(res.getString(8));
                        f.setRecieveDates(res.getString(9));
                        f.setRecieveTimes(res.getString(10));

                        // extract the date-of-creation
                        f.setDateOfCreation(
                                f.getRecieveDates().split("[,]+")[0] + " at " + f.getRecieveTimes().split("[,]+")[0]
                        );

                        f.setPath(res.getString(11));
                        f.setSendDates(res.getString(12));
                        f.setSendTimes(res.getString(13));

                        // set the given date-of-receive 
                        f.setDateOfReceive(dor);

                        // now add the record to the List
                        files.add(f);
                    }

                } while (res.next());

            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            files = null;

        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closeStatement(st);
            DatabaseConnector.freeConncetion(con);
        }

        return files;
    }

}
