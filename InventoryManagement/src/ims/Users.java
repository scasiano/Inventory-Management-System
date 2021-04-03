package ims;
import java.sql.*;
import java.util.ArrayList;

public class Users {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    long userID;
    String username;
    String password;
    String fName;
    String lName;
    String role;

    public Users(long userID, String username, String password, String fName, String lName, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
    }

    //Get set
    public long getUserID() {
        return userID;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFName() {
        return fName;
    }
    public String getLName() {
        return lName;
    }
    public String getRole() {
        return role;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFName(String fName) {
        this.fName = fName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
