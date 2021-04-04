package ims;
import java.sql.*;
import java.util.ArrayList;

public class Budget {
    //TODO: Add
    //TODO: Update
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

    //SQL Queries READ
    public static ArrayList<Budget> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from budget");
        ArrayList<Budget> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Budget(dbResult.getDate(1), dbResult.getDate(2), dbResult.getDouble(3), dbResult.getDouble(4), dbResult.getDouble(5), dbResult.getLong(5)));
        return resultList;
    }
    public static ArrayList<Date> selectDateStart() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_start from budget");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Date> selectDateEnd() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_end from budget");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Double> selectOutgoing() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select outgoing from budget");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> selectIncome() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select income from budget");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> selectNet() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select net from budget");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Long> selectEmployeeNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select net from budget");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }

    //SQL Queries DELETE
    public static void deleteRecord(Date primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from budget where date_start = '" + primaryKey + "'");
    }
}
