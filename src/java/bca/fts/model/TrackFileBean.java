// this bean represents data that is needed to track a file

package bca.fts.model;

public class TrackFileBean {

    private int id = 0;
    private String no = null;
    private String name = null;
    private String owner = null;
    private String status = null;
    private String curLoc = null;              
    private String sender = null;                   // for 'REJECTED' files
    private String dateOfClose = null;
    private String dateOfCreation = null;          // need to be created form "recieveDates" and "recieveTimes" 
    private String recieveDates = null;
    private String recieveTimes = null;
    private String path = null;
    private String sendDates = null;
    private String sendTimes = null;
    private String dateOfSend = null;                  // uses while a is to track a file with its "DoS"
    private String dateOfReceive = null;               // uses while a is to track a file with its "DoR"

    
    public void setDateOfReceive(String dateOfReceive) {
        this.dateOfReceive = dateOfReceive;
    }

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSendDates(String sendDates) {
        this.sendDates = sendDates;
    }

    public void setSendTimes(String sendTimes) {
        this.sendTimes = sendTimes;
    }

    public void setRecieveTimes(String recieveTimes) {
        this.recieveTimes = recieveTimes;
    }

    public void setRecieveDates(String recieveDates) {
        this.recieveDates = recieveDates;
    }
    
    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
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

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCurLoc(String curLoc) {
        this.curLoc = curLoc;
    }

    public void setDateOfClose(String dateOfClose) {
        this.dateOfClose = dateOfClose;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    
    public String getDateOfReceive() {
        return dateOfReceive;
    }

    public String getDateOfSend() {
        return dateOfSend;
    }

    public String getPath() {
        return path;
    }

    public String getSendDates() {
        return sendDates;
    }

    public String getSendTimes() {
        return sendTimes;
    }
 
    public String getRecieveDates() {
        return recieveDates;
    }

    public String getRecieveTimes() {
        return recieveTimes;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getCurLoc() {
        return curLoc;
    }

    public String getDateOfClose() {
        return dateOfClose;
    }

    public int getId() {
        return id;
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
    
}
