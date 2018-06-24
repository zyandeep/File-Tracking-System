// this bean represents a branch

package bca.fts.model;

public class BranchBean {
    
    private int id = 0;
    private String officeName = null;
    private String headName = null;
    private String username = null;
    private String password = null;
    

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    public int getId() {
        return id;
    }
    
    public String getHeadName() {
        return headName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
}
