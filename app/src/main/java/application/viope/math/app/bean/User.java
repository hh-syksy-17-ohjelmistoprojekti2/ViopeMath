package application.viope.math.app.bean;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class User {

    private String username;
    private String userid;

    public User() {
        super();
        this.userid = null;
        this.username = null;
    }

    public User(String userid, String username) {
        this.username = username;
        this.userid= userid;

    }

    public User(String username){
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
        return "User{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
