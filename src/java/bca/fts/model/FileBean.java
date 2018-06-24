// this bean represents a file object

package bca.fts.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class FileBean {
   
    // the logger object
    private static final Logger logger = Logger.getLogger(FileBean.class);
    
    private int id = 0;
    private String no = null;
    private String name = null;
    private String type = null;
    private String description = null;
    private String owner = null;
    private String status = null;
    private String forwardBranch = null;
    private String dateOfSend = null;
    private String timeOfSend = null;
    private String curLoc = null;
    private String sender = null;
    private String remark = null;
    private String dateOfRecieve = null;
    private String timeOfRecieve = null;
    
    // for pendind, rejected file sorting
    private Date date = null;
    
    // ALL THE "SETTER" METHODS
    
    public void setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");

        try {

            this.date = formatter.parse(this.dateOfSend); 
        } 
        catch (Exception e) {
           logger.error(e); 
        }
    }

    
    public void setTimeOfSend(String timeOfSend) {
        this.timeOfSend = timeOfSend;
    }

    public void setTimeOfRecieve(String timeOfRecieve) {
        this.timeOfRecieve = timeOfRecieve;
    }

    public void setDateOfRecieve(String dateOfRecieve) {
        this.dateOfRecieve = dateOfRecieve;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setForwardBranch(String forwardBranch) {
        this.forwardBranch = forwardBranch;
    }

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setCurLoc(String curLoc) {
        this.curLoc = curLoc;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    
    // ALL THE "GETTER" METHODS
    public Date getDate() {
        return date;
    }

    public String getTimeOfSend() {
        return timeOfSend;
    }

    public String getTimeOfRecieve() {
        return timeOfRecieve;
    }

    public String getDateOfRecieve() {
        return dateOfRecieve;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public String getNo() {
        return no;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfSend() {
        return dateOfSend;
    }

    public String getForwardBranch() {
        return forwardBranch;
    }

    public String getCurLoc() {
        return curLoc;
    }

    public String getSender() {
        return sender;
    }

    public String getRemark() {
        return remark;
    }
    
}
