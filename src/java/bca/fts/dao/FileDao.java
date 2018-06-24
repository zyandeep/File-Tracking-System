package bca.fts.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import bca.fts.util.*;
import org.apache.log4j.*;

import bca.fts.model.*;
import java.util.ArrayList;

public class FileDao {

    private static final Logger logger = Logger.getLogger(FileDao.class);

    // create a new file record
    public static int addFile(FileBean file) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "INSERT INTO files( fileNo, fileName, fileType, description, owner, path, recieveDates, recieveTimes )"
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?) ";

        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    // for retriving auto_increment fileID 

            ps.setString(1, file.getNo());
            ps.setString(2, file.getName());
            ps.setString(3, file.getType());
            ps.setString(4, file.getDescription());
            ps.setString(5, file.getOwner());
            ps.setString(6, file.getOwner());
            ps.setString(7, file.getDateOfRecieve());
            ps.setString(8, file.getTimeOfRecieve());

            row = ps.executeUpdate();

            // "res" gonna hold the auto_increment number( here fileID )
            res = ps.getGeneratedKeys();

            if (res.next()) {
                file.setId(res.getInt(1));   // set fileID to the file bean
            }

        } catch (Exception e) {
            logger.error(e);

            // database related error
            row = -1;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // retrieve a file status via it's "fileID"
    public static String giveFileStatus(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT status FROM files WHERE fileId = ?";
        String fileStatus = "";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {
                fileStatus = res.getString(1);
            }
        } catch (Exception e) {
            logger.error(e);

            // error occured
            fileStatus = "";
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return fileStatus;
    }

    // change a file status via it's "fileID"
    public static int changeFileStatus(int id, String status) {

        int row = 0;

        // get the System date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String sysDate = sdf.format(new java.util.Date());

        // get the System time
        sdf = new SimpleDateFormat("hh:mm:ss a");
        String sysTime = sdf.format(new java.util.Date());

        if (status.equalsIgnoreCase("PROCESSING")) {
            row = receiveFile(id, sysDate, sysTime);
        } else {
            // combine the sysDate and sysTime
            sysDate = sysDate.concat(" at ").concat(sysTime);

            row = closeFile(id, sysDate);
        }

        return row;
    }

    // retrieve a file's current location via it's "fileID"
    public static String giveCurrentLoc(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT curLoc FROM files WHERE fileId = ?";
        String curLoc = "";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {
                curLoc = res.getString(1);
            }
        } catch (Exception e) {
            logger.error(e);

        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }
        return curLoc;

    }

    // this method returns a file record via fileID
    public static FileBean giveFileRecord(int fileId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileId, fileNo, fileName, fileType, description FROM files WHERE fileId = ?";
        FileBean file = null;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, fileId);

            res = ps.executeQuery();

            // create the file bean
            if (res.next()) {
                file = new FileBean();

                file.setId(res.getInt(1));
                file.setNo(res.getString(2));
                file.setName(res.getString(3));
                file.setType(res.getString("fileType"));
                file.setDescription(res.getString(5));
            }
        } catch (Exception e) {
            logger.error(e);

            // error occured
            file = null;
        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return file;
    }

    // this method updates an existing file record via "fileId"
    public static int updateFile(FileBean file) {

        Connection con = null;
        PreparedStatement ps = null;

        int id = file.getId();
        String no = file.getNo();
        String name = file.getName();
        String type = file.getType();
        String description = file.getDescription();

        int row = 0;
        String sql = "UPDATE files SET fileNo=?, fileName=?, fileType=?, description=?"
                + "WHERE fileId=?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, no);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4, description);
            ps.setInt(5, id);

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            // for duplicate usernames
            row = 0;
        } finally {

            // clean up database resources
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // this method sends a file ELECTRICALLY
    public static int sendFile(SendFileBean file) {

        Connection con = null;
        PreparedStatement ps = null;

        int row = 0;

        String[] dosPath = giveSdPathNSt(file.getFileId());

        // append the new date-of-sent to "sendDates", seperated by ","
        dosPath[0] = (dosPath[0] == null) ? file.getDateOfSend() : dosPath[0].concat(",").concat(file.getDateOfSend());

        // append the new branch node to the end of "path" seperated by","
        dosPath[1] = dosPath[1].concat(",").concat(file.getForwardBranch());

        // append the new time-of-sent to "sendTimes", seperated by ","
        dosPath[2] = (dosPath[2] == null) ? file.getTimeOfSend() : dosPath[2].concat(",").concat(file.getTimeOfSend());

        String sql = "UPDATE files SET status = \"PENDING\", sender = ?, curLoc = ?, "
                + "path = ?, sendDates= ?, remark = ?, sendTimes = ? "
                + "WHERE fileId = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, file.getSenderBranch());
            ps.setString(2, file.getForwardBranch());
            ps.setString(3, dosPath[1]);
            ps.setString(4, dosPath[0]);
            ps.setString(5, null);
            ps.setString(6, dosPath[2]);
            ps.setInt(7, file.getFileId());

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

    // retrieve a file's "sendDates", "path" and "sendTimes" via it's "fileID"
    public static String[] giveSdPathNSt(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT sendDates, path, sendTimes FROM files WHERE fileId = ?";
        String[] dosPath = new String[3];

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {
                dosPath[0] = res.getString(1);
                dosPath[1] = res.getString(2);
                dosPath[2] = res.getString(3);
            }
        } catch (Exception e) {
            logger.error(e);

            dosPath = null;

        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return dosPath;
    }

    // retrieve a file's "recieveDates" and "recieveTimes" via it's "fileID"
    public static String[] giveRecDatesNTimes(int id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT recieveDates, recieveTimes FROM files WHERE fileId = ?";
        String[] rdNt = new String[2];

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            res = ps.executeQuery();

            if (res.next()) {
                rdNt[0] = res.getString(1);
                rdNt[1] = res.getString(2);
            }
        } catch (Exception e) {
            logger.error(e);

            rdNt = null;

        } finally {

            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return rdNt;
    }

    // this method REJECTS a file
    public static int rejectFile(SendFileBean file) {

        Connection con = null;
        PreparedStatement ps = null;

        int row = 0;

        String[] dosPath = giveSdPathNSt(file.getFileId());

        // append the new date-of-sent to "sendDates", seperated by ","
        dosPath[0] = (dosPath[0] == null) ? file.getDateOfSend() : dosPath[0].concat(",").concat(file.getDateOfSend());

        // append the new branch node to the end of "path" seperated by","
        dosPath[1] = dosPath[1].concat(",").concat(file.getForwardBranch());

        // append the new time-of-sent to "sendTimes", seperated by ","
        dosPath[2] = (dosPath[2] == null) ? file.getTimeOfSend() : dosPath[2].concat(",").concat(file.getTimeOfSend());

        String sql = "UPDATE files SET status = \"REJECTED\", sender = ?, curLoc = ?, "
                + "path = ?, sendDates= ?, remark = ?, sendTimes = ? "
                + "WHERE fileId = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, file.getSenderBranch());
            ps.setString(2, file.getForwardBranch());
            ps.setString(3, dosPath[1]);
            ps.setString(4, dosPath[0]);
            ps.setString(5, file.getRemark());
            ps.setString(6, dosPath[2]);
            ps.setInt(7, file.getFileId());

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

    // this method CLOSES a file
    public static int closeFile(int id, String dateOfClose) {

        Connection con = null;
        PreparedStatement ps = null;

        int row = 0;

        String sql = "UPDATE files SET status = \"CLOSED\", dateOfClose = ? WHERE fileId = ?";

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, dateOfClose);
            ps.setInt(2, id);

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

    // this method receives a file and its status to "PROCESSING"
    public static int receiveFile(int id, String dateOfReceive, String timeOfReceive) {

        Connection con = null;
        PreparedStatement ps = null;

        // when a file's status is "NEW", its DOR and TOR shouldn't be counted. 
        // it's status is "PENDING" or "REJECTED" those should be counted. 
        String status = giveFileStatus(id);
        String sql = "";
        int row = 0;

        // this array will hold the concataneted "recieveDates" and "recieveTimes"
        String[] rdNt = null;

        if (status.equalsIgnoreCase("NEW")) {

            sql = "UPDATE files SET status = \"PROCESSING\" WHERE fileId = ?";
        } else {

            rdNt = giveRecDatesNTimes(id);

            // append the new date-of-receive to "receiveDates", seperated by ","
            rdNt[0] = rdNt[0].concat(",").concat(dateOfReceive);

            // append the time-of-receive to the end of "receiveTimes" seperated by","
            rdNt[1] = rdNt[1].concat(",").concat(timeOfReceive);

            sql = "UPDATE files SET status = \"PROCESSING\", recieveDates = ?, recieveTimes = ? WHERE fileId = ?";
        }

        try {

            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            if (status.equalsIgnoreCase("NEW")) {
                ps.setInt(1, id);
            } else {
                ps.setString(1, rdNt[0]);
                ps.setString(2, rdNt[1]);
                ps.setInt(3, id);
            }

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

    // this method returns an ArrayList of file record via fileName
    public static ArrayList<String> autocompleteFileName(String key) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        String sql = "SELECT fileName FROM files WHERE fileName LIKE ?";

        ArrayList<String> fileNames = new ArrayList<String>();

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, "%" + key + "%");

            res = ps.executeQuery();

            while (res.next()) {
                fileNames.add(res.getString(1));
            }

        } catch (Exception e) {
            logger.error(e);

            // error occured
            fileNames = null;
        } finally {
            // clean up database resources
            DatabaseConnector.closeResultSet(res);
            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return fileNames;
    }

    // delete a file type
    public static int deleteFileType(String fileType) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM filetypes WHERE type = ?";

        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, fileType);

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            row = -1;
        } finally {

            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // create a new file type
    public static int addFileType(String fileType) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO filetypes (type) VALUES(?)";

        int row = 0;

        try {
            con = DatabaseConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, fileType);

            row = ps.executeUpdate();
        } catch (Exception e) {
            logger.error(e);

            row = -1;
        } finally {

            DatabaseConnector.closePreStatement(ps);
            DatabaseConnector.freeConncetion(con);
        }

        return row;
    }

    // this method will verify weather a file type is already registered or not 
    public static boolean checkUniqueFiletype(String filetype) {

        Connection con = null;
        ResultSet res = null;
        PreparedStatement pst = null;

        boolean ans = false;

        String sql = "SELECT * FROM filetypes WHERE type = ?";

        try {
            con = DatabaseConnector.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1, filetype);

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
