// this bean represents a sent file

package bca.fts.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class SendFileBean {
    
    // the logger object
    private static final Logger logger = Logger.getLogger(SendFileBean.class);
    
    private int fileId = 0;
    private String senderBranch = null;
    private String forwardBranch = null;
    private String dateOfSend = null;
    private String timeOfSend = null;
    private String remark = null;
    
     // for sorting
    private Date date = null;
    
    // all the "getter" methods

    public Date getDate() {
        return date;
    }
    
    public String getTimeOfSend() {
        return timeOfSend;
    }

    public String getRemark() {
        return remark;
    }
    
    public int getFileId() {
        return fileId;
    }

    public String getSenderBranch() {
        return senderBranch;
    }

    public String getForwardBranch() {
        return forwardBranch;
    }

    public String getDateOfSend() {
        return dateOfSend;
    }
    
    
    // all the "setter" methods
     public void setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");

        try {

            this.date = formatter.parse(this.dateOfSend);
        } 
        catch (Exception e) {
           logger.error(e); 
        }
    }
    
    public void setTimeOfSend( String timeOfSend ) {
        this.timeOfSend = timeOfSend;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setSenderBranch(String senderBranch) {
        this.senderBranch = senderBranch;
    }

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setForwardBranch( String forwardBranch ) {
        this.forwardBranch = forwardBranch;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}