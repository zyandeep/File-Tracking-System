// this bean represents a closed file

package bca.fts.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ClosedFileBean {
    
     // the logger object
    private static final Logger logger = Logger.getLogger(ClosedFileBean.class);
    
    private int id = 0;
    private String no = null;
    private String name = null;
    private String type = null;
    private String owner = null;
    private String dateOfSend = null;
    private String curLoc = null;
    private String dateOfClose = null;
   
    // for pendind, rejected file sorting
    private Date date = null;
    
    
    // all the "SETTER" methods
     
    public void setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss a");

        try {

            this.date = formatter.parse(this.dateOfClose); 
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

    public void setDateOfSend(String dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public void setCurLoc(String curLoc) {
        this.curLoc = curLoc;
    }

    public void setDateOClose(String dateOfClose) {
        this.dateOfClose = dateOfClose;
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


    public String getDateOfSend() {
        return dateOfSend;
    }

    public String getCurLoc() {
        return curLoc;
    }

    public String getDateOfClose() {
        return dateOfClose;
    }
    
}
