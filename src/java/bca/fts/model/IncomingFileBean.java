// this bean represents a incoming file
package bca.fts.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.log4j.*;

public class IncomingFileBean {
    
    // the logger object
    private static final Logger logger = Logger.getLogger(IncomingFileBean.class);
    
    private int id = 0;
    private String no = null;
    private String name = null;
    private String type = null;
    private String status = null;
    private String dateOfSend = null;
    private String sender = null;
    private String remark = null;

    // these two variables are required in "branchList" that is shown in the "Forward" drop-down list 
    private String owner = null;
    private String curLoc = null;

    // to make sure whether a file has been recieved or not
    private boolean fileRecieved = false;

    // for sorting
    private Date date = null;

    // all the "SETTER" method
    public void setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");

        try {

            this.date = formatter.parse(this.dateOfSend);
        } 
        catch (Exception e) {
           logger.error(e); 
        }
    }

    
    public void setFileRecieved(boolean fileRecieved) {
        this.fileRecieved = fileRecieved;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurLoc(String curLoc) {
        this.curLoc = curLoc;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // all the "GETTER" method
    public Date getDate() {
        return date;
    }

    public boolean isFileRecieved() {
        return fileRecieved;
    }

    public int getId() {
        return id;
    }

    public String getDateOfSend() {
        return dateOfSend;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public String getOwner() {
        return owner;
    }

    public String getSender() {
        return sender;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getCurLoc() {
        return curLoc;
    }

    public String getRemark() {
        return remark;
    }

}
