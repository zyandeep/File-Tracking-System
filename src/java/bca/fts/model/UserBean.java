// this bean represents a branch user

package bca.fts.model;

public class UserBean {
    
    private String userName = null;
    private String password = null;

    public UserBean() {
        userName = " ";
        password = " ";
    }

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
