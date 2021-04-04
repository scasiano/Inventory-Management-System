package ims;
import java.sql.*;
import java.util.ArrayList;

public class Employees {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    long employeeNo;
    long userID;
    String employeeFn;
    String employeeLn;
    double payHour; //default 10
    String position;
    Date startDate;
    Date endDate; //nullable

    public Employees(long employeeNo, long userID, String employeeFn, String employeeLn, double payHour, String position, Date startDate, Date endDate) {
        this.employeeNo = employeeNo;
        this.userID = userID;
        this.employeeFn = employeeFn;
        this.employeeLn = employeeLn;
        this.payHour = payHour;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Get set
    public long getEmployeeNo() {
        return employeeNo;
    }
    public long getUserID() {
        return userID;
    }
    public String getEmployeeFn() {
        return employeeFn;
    }
    public String getEmployeeLn() {
        return employeeLn;
    }
    public double getPayHour() {
        return payHour;
    }
    public String getPosition() {
        return position;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEmployeeNo(long employeeNo) {
        this.employeeNo = employeeNo;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public void setEmployeeFn(String employeeFn) {
        this.employeeFn = employeeFn;
    }
    public void setEmployeeLn(String employeeLn) {
        this.employeeLn = employeeLn;
    }
    public void setPayHour(double payHour) {
        this.payHour = payHour;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //SQL Queries READ
    public static ArrayList<Employees> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from employees");
        ArrayList<Employees> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Employees(dbResult.getLong(1), dbResult.getLong(2), dbResult.getString(3), dbResult.getString(4), dbResult.getDouble(5), dbResult.getString(6), dbResult.getDate(7), dbResult.getDate(8)));
        return resultList;
    }
    public static ArrayList<Long> selectEmployeeNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_no from employees");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Long> selectUserID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select user_id from employees");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectEmployeeFn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_fn from employees");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectEmployeeLn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_ln from employees");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Double> selectPayHour() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select pay_hour from employees");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<String> selectPosition() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select position from employees");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Date> selectStartDate() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select start_date from employees");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Date> selectEndDate() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select end_date from employees");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
}