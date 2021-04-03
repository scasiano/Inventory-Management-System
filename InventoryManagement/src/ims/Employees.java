package ims;
import java.sql.*;
import java.util.ArrayList;

public class Employees {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
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
}
