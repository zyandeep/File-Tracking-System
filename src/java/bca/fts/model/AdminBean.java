// this bean represents the admin

package bca.fts.model;

public class AdminBean {
    
    private String userName = null;
    private String password = null;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
