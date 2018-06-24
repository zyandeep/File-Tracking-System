// this bean represents a processing  file

package bca.fts.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ProcessingFileBean {
    
    private static final Logger logger = Logger.getLogger(ProcessingFileBean.class);
    
    private int id = 0;
    private String no = null;
    private String name = null;
    private String type = null;
    private String sender = null;
    private String dateOfSend = null;
    private String curLoc = null;
    private String dateOfRecieve = null;
    private String owner = null;
    
    // for pendind, rejected file sorting
    private Date date = null;
    
    
    // all the "SETTER" methods
    public void setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");

        try {

            this.date = formatter.parse(this.dateOfRecieve); 
        } 
        catch (Exception e) {
           logger.error(e); 
        }
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
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

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setCurLoc(String curLoc) {
        this.curLoc = curLoc;
    }

    public void setDateOfRecieve(String dateOfRecieve) {
        this.dateOfRecieve = dateOfRecieve;
    }

    
    
    // all the "GETTER" methods

    public Date getDate() {
        return date;
    }

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getDateOfSend() {
        return dateOfSend;
    }

    public String getCurLoc() {
        return curLoc;
    }

    public String getDateOfRecieve() {
        return dateOfRecieve;
    }

}
