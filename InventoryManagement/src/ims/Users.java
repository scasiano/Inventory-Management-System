package ims;
import java.sql.*;
import java.util.ArrayList;

public class Users {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
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

    //SQL Queries READ
    public static ArrayList<Users> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from users");
        ArrayList<Users> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Users(dbResult.getLong(1), dbResult.getString(2), dbResult.getString(3), dbResult.getString(4), dbResult.getString(5), dbResult.getString(6)));
        return resultList;
    }
    public static ArrayList<Long> selectUserID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select user_id from users");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectUsername() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select username from users");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectPassword() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select password from users");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectFName() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select fname from users");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectLName() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select lname from users");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectRole() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select role from users");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
}