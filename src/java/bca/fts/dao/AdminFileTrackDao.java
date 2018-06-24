package bca.fts.dao;

import java.sql.*;
import java.util.*;
import org.apache.log4j.*;

import bca.fts.util.*;
import bca.fts.model.*;

public class AdminFileTrackDao {

    // the logger object
    private static final Logger logger = Logger.getLogger(AdminFileTrackDao.class);

    // this method verify whether file can be tracked or not
    public static TrackFileBean isFileTracable(int id) {

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

    // this method returns an ArrayList of file record via fileNo
    public static ArrayList<TrackFileBean> giveFilesViaNo(String no) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE fileNo = ?";

        ArrayList<TrackFileBean> files = new ArrayList<TrackFileBean>();
        TrackFileBean f = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, no);

            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                do {
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

    // this method returns an ArrayList of file record via fileName
    public static ArrayList<TrackFileBean> giveFilesViaName(String name) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                + "path, sendDates, sendTimes FROM files WHERE fileName = ?";

        ArrayList<TrackFileBean> files = new ArrayList<TrackFileBean>();
        TrackFileBean f = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, name);

            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                do {
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

    // this method returns an ArrayList of file record via file status, source branch and destination branch
    public static ArrayList<TrackFileBean> giveFilesViaStatus(String status, String atBranch, String senderBranch) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "";
        ArrayList<TrackFileBean> files = new ArrayList<TrackFileBean>();
        TrackFileBean f = null;

        try {

            con = DatabaseConnector.getConnection();

            if (atBranch == null && senderBranch == null) {
                sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                        + "path, sendDates, sendTimes FROM files WHERE status = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, status);

            } else if (atBranch != null && senderBranch == null) {
                ps = queryWithAtBranch(status, atBranch, con);
            } else if (atBranch == null && senderBranch != null) {
                sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                        + "path, sendDates, sendTimes FROM files WHERE status = ?";

                ps = con.prepareStatement(sql);

                if (status.equalsIgnoreCase("NEW")) {
                    return null;
                } else {
                    ps.setString(1, status);
                }

            } else {
                ps = createQuery(status, atBranch, con);

                if (status.equalsIgnoreCase("NEW")) {
                    return null;
                }
            }

            // now execute the query
            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                boolean isRecordOk = true;

                do {

                    // this block of code determines whether a branch is sent a particular file  
                    if (senderBranch != null) {

                        isRecordOk = false;

                        String tPath = res.getString(11);

                        if (tPath.contains(senderBranch)) {

                            String[] branches = tPath.split("[,]+");

                            for (int i = 0; i < branches.length; i++) {

                                if (branches[i].equalsIgnoreCase(senderBranch) && (i + 1) != branches.length) {

                                    isRecordOk = true;
                                    break;
                                }
                            }
                        }

                    }

                    if (isRecordOk) {
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

    private static PreparedStatement queryWithAtBranch(String status, String branch, Connection con)
            throws SQLException {

        String sql = "";
        PreparedStatement ps = null;

        if (status.equalsIgnoreCase("NEW")) {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status = \"NEW\" AND owner = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
        } else if (status.equalsIgnoreCase("PENDING")) {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status = \"PENDING\" AND curLoc = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
        } else if (status.equalsIgnoreCase("PROCESSING")) {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status=\"PROCESSING\" AND "
                    + "( curLoc = ? OR (owner = ? AND sendDates IS NULL) )";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
            ps.setString(2, branch);
        } else if (status.equalsIgnoreCase("REJECTED")) {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status=\"REJECTED\" AND sender = ? ";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
        } else {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status=\"CLOSED\" AND "
                    + "( curLoc = ? OR (owner = ? AND sendDates IS NULL) )";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
            ps.setString(2, branch);
        }

        return ps;
    }

    private static PreparedStatement createQuery(String status, String branch, Connection con)
            throws SQLException {

        String sql = "";
        PreparedStatement ps = null;

        if (status.equalsIgnoreCase("NEW")) {
            ps = con.prepareStatement(sql);        // so that no error appears in the tomcat log 

        } else if (status.equalsIgnoreCase("REJECTED")) {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status=\"REJECTED\" AND sender = ? ";

            ps = con.prepareStatement(sql);
            ps.setString(1, branch);
        } else {
            sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                    + "path, sendDates, sendTimes FROM files WHERE status = ? AND curLoc = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, branch);
        }

        return ps;
    }

    // this method returns an ArrayList of file record via branch name, incoming file, outgoing files and owned files
    public static ArrayList<TrackFileBean> giveFilesViaBranch(String branch, String inf, String otf, String of) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "";
        ArrayList<TrackFileBean> files = new ArrayList<TrackFileBean>();
        TrackFileBean f = null;

        boolean isRecordOk = false;

        try {

            con = DatabaseConnector.getConnection();

            /*
             all the owned files, all the sent files owned by "branch", all the recieved files owned by "branch"
             */
            if (of != null) {

                sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                        + "path, sendDates, sendTimes FROM files WHERE owner = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, branch);
            } else {
                sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                        + "path, sendDates, sendTimes FROM files";

                ps = con.prepareStatement(sql);
            }

            // now execute the query
            res = ps.executeQuery();

            if (res.next() == false) {
                return null;
            } else {

                do {

                    // all the files touches by "branch" 
                    if (of == null && inf == null && otf == null) {

                        isRecordOk = false;

                        if (res.getString(11).contains(branch)) {
                            isRecordOk = true;
                        }
                    } // all the files incoming to the "branch" 
                    else if (inf != null && otf == null && of == null) {

                        isRecordOk = false;

                        if (res.getString(11).lastIndexOf(branch) > 0) {
                            isRecordOk = true;
                        }
                    } // all the outing files from the "branch"
                    else if (otf != null && inf == null && of == null) {

                        isRecordOk = false;

                        String tPath = res.getString(11);

                        if (tPath.contains(branch)) {

                            String[] branches = tPath.split("[,]+");

                            for (int i = 0; i < branches.length; i++) {

                                if (branches[i].equalsIgnoreCase(branch) && (i + 1) != branches.length) {
                                    isRecordOk = true;
                                    break;
                                }
                            }
                        }
                    } // all the files owned by "branch" 
                    else if (of != null && otf == null && inf == null) {
                        isRecordOk = true;
                    } // all the incoming and outgoing files of "branch" 
                    else if (otf != null && inf != null && of == null) {
                        isRecordOk = false;

                        String tPath = res.getString(11);

                        if (tPath.contains(branch) && !tPath.equalsIgnoreCase(branch)) {
                            isRecordOk = true;
                        }
                    } // all the incoming files created by that same branch
                    else if (of != null && inf != null && otf == null) {
                        isRecordOk = false;

                        if (res.getString(11).lastIndexOf(branch) > 0) {
                            isRecordOk = true;
                        }
                    } // all the outgoing files created by that branch
                    else if (of != null && otf != null && inf == null) {
                        isRecordOk = false;

                        if (!res.getString(11).equalsIgnoreCase(branch)) {
                            isRecordOk = true;
                        }
                    } // all the incoming/outgoing files created by that branch
                    else if (of != null && otf != null && inf != null) {
                        isRecordOk = false;

                        if (!res.getString(11).equalsIgnoreCase(branch)) {
                            isRecordOk = true;
                        }
                    }

                    // load the file into the ArrayList
                    if (isRecordOk) {
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

    // this method return a list of file records on the basis of "date-of-send"
    public static ArrayList<TrackFileBean> giveFilesViaDoS(String dos, String sender, String receiver, String status) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "";
        ArrayList<TrackFileBean> files = null;
        TrackFileBean f = null;

        // detemines whether a record to be added in the List or not based on a condition
        boolean isRecordOk = false;

        // files sent by a branch on a particular date
        if ((sender != null && receiver == null && status == null)) {
            files = BranchFileTrackDao.giveFilesDoS(dos, sender);

            return files;
        } else {

            try {
                con = DatabaseConnector.getConnection();

                // create the appropiate query
                if (status != null) {
                    sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                            + "path, sendDates, sendTimes FROM files WHERE sendDates IS NOT NULL AND status = ?";

                    ps = con.prepareStatement(sql);
                    ps.setString(1, status);
                } else {
                    sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                            + "path, sendDates, sendTimes FROM files WHERE sendDates IS NOT NULL";

                    ps = con.prepareStatement(sql);
                }

                // now execute the query
                res = ps.executeQuery();

                if (res.next() == false) {
                    return null;
                } else {

                    // create the ArrayList
                    files = new ArrayList<TrackFileBean>();

                    // now iterate over the records
                    do {

                        // all the files sent on a "date" ( with a "status")
                        if ((sender == null && receiver == null && status == null)
                                || (sender == null && receiver == null && status != null)) {

                            isRecordOk = false;

                            if (res.getString(12).contains(dos)) {
                                isRecordOk = true;
                            }

                        } // all the files sent on a "date" to a "branch" ( with a "status")
                        else if ((sender == null && receiver != null && status == null)
                                || (sender == null && receiver != null && status != null)) {

                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(12);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (date.contains(dos) && path.contains(receiver)) {

                                for (int i = 0; i < tempDates.length; i++) {
                                    if (tempDates[i].equalsIgnoreCase(dos) && tempBranch[i + 1].equalsIgnoreCase(receiver)) {

                                        isRecordOk = true;

                                        break;
                                    }
                                }

                            }

                        } // all the files sent on a "date" from "sender" to "reciever" ( with a "status")
                        else if ((sender != null && receiver != null && status == null)
                                || (sender != null && receiver != null && status != null)) {
                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(12);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (date.contains(dos) && path.contains(sender) && path.contains(receiver)) {

                                for (int i = 0; i < tempDates.length; i++) {
                                    if (tempDates[i].equalsIgnoreCase(dos) && tempBranch[i].equalsIgnoreCase(sender)
                                            && tempBranch[i + 1].equalsIgnoreCase(receiver)) {

                                        isRecordOk = true;

                                        break;
                                    }
                                }

                            }

                        } // files sent by a branch on a particular date having "status"
                        else if (sender != null && receiver == null && status != null) {
                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(12);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (path.contains(sender) && date.contains(dos)) {

                                for (int i = 0; i < tempDates.length; i++) {
                                    if (tempDates[i].equalsIgnoreCase(dos) && tempBranch[i].equalsIgnoreCase(sender)) {

                                        isRecordOk = true;

                                        break;
                                    }
                                }

                            }
                        }

                        // load the file into the ArrayList
                        if (isRecordOk) {
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

                DatabaseConnector.closeResultSet(res);
                DatabaseConnector.closePreStatement(ps);
                DatabaseConnector.freeConncetion(con);

            }

        }

        return files;
    }

    // this method return a list of file records that on the basis of "date-of-send"
    public static ArrayList<TrackFileBean> giveFilesViaDoR(String dor, String atBranch, String sender, String status) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "";
        ArrayList<TrackFileBean> files = null;
        TrackFileBean f = null;

        // detemines whether a record to be added in the List or not based on a condition
        boolean isRecordOk = false;

        // all the files received on a "date" by a "branch"
        if (atBranch != null && sender == null && status == null) {
            files = BranchFileTrackDao.giveFilesDoR(dor, atBranch);

            return files;
        } else {

            try {

                con = DatabaseConnector.getConnection();

                // create the appropiate query
                if (status != null) {
                    sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                            + "path, sendDates, sendTimes FROM files WHERE status = ?";

                    ps = con.prepareStatement(sql);
                    ps.setString(1, status);
                } else {
                    sql = "SELECT fileId, fileNo, fileName, owner, status, curLoc, sender, dateOfClose, recieveDates, recieveTimes, "
                            + "path, sendDates, sendTimes FROM files ";

                    ps = con.prepareStatement(sql);
                }

                // now execute the query
                res = ps.executeQuery();

                if (res.next() == false) {
                    return null;
                } else {

                    // create the ArrayList
                    files = new ArrayList<TrackFileBean>();

                    // now iterate over the records
                    do {

                        // all the files recieved on a "date" ( with a "status")
                        if ((atBranch == null && sender == null && status == null)
                                || (atBranch == null && sender == null && status != null)) {

                            isRecordOk = false;

                            if (res.getString(9).contains(dor)) {
                                isRecordOk = true;
                            }
                        } // all the files recieved on a "date" send by a "branch"
                        else if ((atBranch == null && sender != null && status == null)
                                || (atBranch == null && sender != null && status != null)) {

                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(9);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (path.contains(sender) && date.contains(dor)) {

                                for (int i = 0; i < tempDates.length; i++) {

                                    if (tempDates[i].equalsIgnoreCase(dor) && tempBranch[i - 1].equalsIgnoreCase(sender)) {

                                        isRecordOk = true;
                                        break;
                                    }
                                }

                            }
                        } // all the files recieved on a "date" at a "branch" send by a "branch" ( with a status )
                        else if ((atBranch != null && sender != null && status == null)
                                || (atBranch != null && sender != null && status != null)) {

                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(9);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (path.contains(atBranch) && path.contains(sender) && date.contains(dor)) {

                                for (int i = 0; i < tempDates.length; i++) {

                                    if (tempDates[i].equalsIgnoreCase(dor) && tempBranch[i].equalsIgnoreCase(atBranch)
                                            && tempBranch[i - 1].equalsIgnoreCase(sender)) {

                                        isRecordOk = true;
                                        break;
                                    }
                                }

                            }
                        } // all the files received on a "date" by a "branch" with a "status"
                        else if (atBranch != null && sender == null && status != null) {

                            isRecordOk = false;

                            String path = res.getString(11);
                            String date = res.getString(9);

                            String tempBranch[] = path.split("[,]+");
                            String tempDates[] = date.split("[,]+");

                            if (path.contains(atBranch) && date.contains(dor)) {

                                for (int i = 0; i < tempDates.length; i++) {

                                    if (tempDates[i].equalsIgnoreCase(dor) && tempBranch[i].equalsIgnoreCase(atBranch)) {

                                        isRecordOk = true;
                                        break;
                                    }
                                }

                            }
                        }

                        // load the file into the ArrayList
                        if (isRecordOk) {
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

                // all the files recieved on a "date"
            } catch (Exception e) {
                logger.error(e);

                // error occured
                files = null;
            } finally {

                DatabaseConnector.closeResultSet(res);
                DatabaseConnector.closePreStatement(ps);
                DatabaseConnector.freeConncetion(con);

            }
        }

        return files;
    }

}
