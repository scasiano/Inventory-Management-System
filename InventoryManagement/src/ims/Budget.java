package ims;
import java.sql.*;
import java.util.ArrayList;

public class Budget {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    Date dateStart;
    Date dateEnd;
    double outgoing;
    double income;
    double net; //nullable //generated from outgoing and income
    long employeeNo; //nullable

    public Budget(Date dateStart, Date dateEnd, double outgoing, double income, double net, long employeeNo) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
        this.net = net;
        this.employeeNo = employeeNo;
    }

    //Get set
    public Date getDateStart() {
        return dateStart;
    }
    public Date getDateEnd() {
        return dateEnd;
    }
    public double getOutgoing() {
        return outgoing;
    }
    public double getIncome() {
        return income;
    }
    public double getNet() {
        return net;
    }
    public long getEmployeeNo() {
        return employeeNo;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }
    public void setIncome(double income) {
        this.income = income;
    }
    public void setEmployeeNo(long employeeNo) {
        this.employeeNo = employeeNo;
    }
}
