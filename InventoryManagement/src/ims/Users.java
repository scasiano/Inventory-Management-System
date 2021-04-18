package ims;
import imsGUI.Global;

import java.sql.*;
import java.util.ArrayList;

public class Users {
    long userID;
    String username;
    String password;
    String fName;
    String lName;
    String role;

    public Users(long userID, String username, String password, String fName, String lName, String role) {
        this.userID = userID; //PK
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

    //SQL Queries DELETE
    //You must also delete entry from employee table, if exists
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from users where user_id = " + primaryKey);
    }

    //SQL Queries ADD
    public static void addRecord(Users recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into users(user_id, username, password, fname, lname, role) values (" + recordToAdd.userID + ", '" + recordToAdd.username + "', '" +recordToAdd.password + "', '" + recordToAdd.fName + "', '" + recordToAdd.lName + "', '" + recordToAdd.role + "');");
    }

    //SQL Queries MODIFY
    public static void modifyUsername(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update users set username = '" + updateValue + "' where user_id = " + primaryKey);
    }
    public static void modifyPassword(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update users set password = '" + updateValue + "' where user_id = " + primaryKey);
    }
    public static void modifyFName(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update users set fname = '" + updateValue + "' where user_id = " + primaryKey);
    }
    public static void modifyLName(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update users set lname = '" + updateValue + "' where user_id = " + primaryKey);
    }
    public static void modifyRole(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update users set role = '" + updateValue + "' where user_id = " + primaryKey);
    }

    //SQL Queries CHECK USER
    public static String authenticateUser(String username, String password) throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from users where username='" + username + "'");
        while (dbResult.next()) if (password.equals(dbResult.getString(3))) return dbResult.getString(6);
        return "";
    }
    public static Users selectUserIDByUserName(String username) throws SQLException{
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from users where username='" + username + "'");
        while (dbResult.next()) return new Users(dbResult.getLong(1), dbResult.getString(2), dbResult.getString(3), dbResult.getString(4), dbResult.getString(5), dbResult.getString(6));
        return null;
    }
}
