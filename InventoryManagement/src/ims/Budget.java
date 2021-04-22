package ims;
import java.sql.*;
import java.util.ArrayList;

public class Budget {
    private long periodID; //PK //Auto Increment
    private Date dateStart; //Unique
    private Date dateEnd; //Unique
    private double outgoing;
    private double income;
    private double net; //nullable //generated from outgoing and income
    private long employeeNo; //nullable

    public Budget(long periodID, Date dateStart, Date dateEnd, double outgoing, double income, double net, long employeeNo) {
        this.periodID = periodID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
        this.net = net;
        this.employeeNo = employeeNo;
    }

    public Budget(long periodID, Date dateStart, Date dateEnd, double outgoing, double income, long employeeNo) {
        this.periodID = periodID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
        this.employeeNo = employeeNo;
    }

    public Budget(long periodID, Date dateStart, Date dateEnd, double outgoing, double income) {
        this.periodID = periodID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
    }

    public Budget(Date dateStart, Date dateEnd, double outgoing, double income, long employeeNo) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
        this.employeeNo = employeeNo;
    }

    public Budget(Date dateStart, Date dateEnd, double outgoing, double income) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.outgoing = outgoing;
        this.income = income;
    }



    //Get set
    public long getPeriodID(){
        return periodID;
    }
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
    public void setPeriodID(long periodID){
        this.periodID = periodID;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    public void setNet(double net){
        this.net = net;
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
        while (dbResult.next()) resultList.add(new Budget(dbResult.getLong(1), dbResult.getDate(2), dbResult.getDate(3), dbResult.getDouble(4), dbResult.getDouble(5), dbResult.getDouble(6), dbResult.getLong(7)));
        return resultList;
    }
    public static ArrayList<Date> selectPeriodID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select period_id from budget");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
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

    //SQL Queries ADD
    public static void addRecordEmpID(Budget recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into budget(period_id, date_start, date_end, outgoing, income, employee_no) values (" + recordToAdd.periodID + ", '" + recordToAdd.dateStart + "', '" + recordToAdd.dateEnd + "', " +recordToAdd.outgoing + ", " + recordToAdd.income + ", " + recordToAdd.employeeNo + ")");
    }
    public static void addRecordEmp(Budget recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into budget(date_start, date_end, outgoing, income, employee_no) values ('" + recordToAdd.dateStart + "', '" + recordToAdd.dateEnd + "', " +recordToAdd.outgoing + ", " + recordToAdd.income + ", " + recordToAdd.employeeNo + ")");
    }
    public static void addRecordID(Budget recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into budget(period_id, date_start, date_end, outgoing, income) values (" + recordToAdd.periodID + ", '" + recordToAdd.dateStart + "', '" + recordToAdd.dateEnd + "', " +recordToAdd.outgoing + ", " + recordToAdd.income + ")");
    }
    public static void addRecord(Budget recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into budget(date_start, date_end, outgoing, income) values ('" + recordToAdd.dateStart + "', '" + recordToAdd.dateEnd + "', " +recordToAdd.outgoing + ", " + recordToAdd.income + ")");
    }

    //SQL Queries MODIFY
    public static void modifyDateStart(Date primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update budget set date_start = '" + updateValue + "' where date_start = '" + primaryKey + "'");
    }
    public static void modifyDateEnd(Date primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update budget set date_end = '" + updateValue + "' where date_start = '" + primaryKey + "'");
    }
    public static void modifyOutgoing(Date primaryKey, double updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update budget set outgoing = " + updateValue + " where date_start = '" + primaryKey + "'");
    }
    public static void modifyIncome(Date primaryKey, double updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update budget set income = " + updateValue + " where date_start = '" + primaryKey + "'");
    }
    public static void modifyEmployeeNo(Date primaryKey, long updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update budget set employee_no = " + updateValue + " where date_start = '" + primaryKey + "'");
    }
}
