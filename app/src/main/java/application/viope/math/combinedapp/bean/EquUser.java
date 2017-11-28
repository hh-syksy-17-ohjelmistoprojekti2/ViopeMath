package application.viope.math.combinedapp.bean;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class EquUser {

    private String username;
    private String userid;

    public EquUser() {
        super();
        this.userid = null;
        this.username = null;
    }

    public EquUser(String userid, String username) {
        this.username = username;
        this.userid= userid;

    }

    public EquUser(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "EquUser{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}

