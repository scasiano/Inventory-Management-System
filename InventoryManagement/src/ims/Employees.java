package ims;
import java.sql.*;
import java.util.ArrayList;

public class Employees {
    private long employeeNo; //PK
    private long userID; //FK
    private String employeeFn;
    private String employeeLn;
    private double payHour; //default 10
    private String position;
    private Date startDate;
    private Date endDate; //nullable

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

    public Employees(long employeeNo, long userID, String employeeFn, String employeeLn, String position, Date startDate, Date endDate) {
        this.employeeNo = employeeNo;
        this.userID = userID;
        this.employeeFn = employeeFn;
        this.employeeLn = employeeLn;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Employees(long employeeNo, long userID, String employeeFn, String employeeLn, double payHour, String position, Date startDate) {
        this.employeeNo = employeeNo;
        this.userID = userID;
        this.employeeFn = employeeFn;
        this.employeeLn = employeeLn;
        this.payHour = payHour;
        this.position = position;
        this.startDate = startDate;
    }

    public Employees(long employeeNo, long userID, String employeeFn, String employeeLn, String position, Date startDate) {
        this.employeeNo = employeeNo;
        this.userID = userID;
        this.employeeFn = employeeFn;
        this.employeeLn = employeeLn;
        this.position = position;
        this.startDate = startDate;
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

    //SQL Queries DELETE
    //EmployeeNo is FK for multiple tables, these entries must be changed first
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from employees where employee_no = " + primaryKey);
    }

    //SQL Queries ADD
    public static void addRecordPayDate(Employees recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date, end_date) values (" + recordToAdd.employeeNo + ", " + recordToAdd.userID + ", '" +recordToAdd.employeeFn + "', '" + recordToAdd.employeeLn + "', " + recordToAdd.payHour + ", '" + recordToAdd.position + "', '" + recordToAdd.startDate + "', '" + recordToAdd.endDate + "')");
    }
    public static void addRecordDate(Employees recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into employees(employee_no, user_id, employee_fn, employee_ln, position, start_date, end_date) values (" + recordToAdd.employeeNo + ", " + recordToAdd.userID + ", '" +recordToAdd.employeeFn + "', '" + recordToAdd.employeeLn + "', '" + recordToAdd.position + "', '" + recordToAdd.startDate + "', '" + recordToAdd.endDate + "')");
    }
    public static void addRecordPay(Employees recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into employees(employee_no, user_id, employee_fn, employee_ln, pay_hour, position, start_date) values (" + recordToAdd.employeeNo + ", " + recordToAdd.userID + ", '" +recordToAdd.employeeFn + "', '" + recordToAdd.employeeLn + "', " + recordToAdd.payHour + ", '" + recordToAdd.position + "', '" + recordToAdd.startDate + "')");
    }
    public static void addRecord(Employees recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into employees(employee_no, user_id, employee_fn, employee_ln, position, start_date) values (" + recordToAdd.employeeNo + ", '" + recordToAdd.userID + "', '" +recordToAdd.employeeFn + "', '" + recordToAdd.employeeLn + "', '" + recordToAdd.position + "', '" + recordToAdd.startDate + "')");
    }

    //SQL Queries MODIFY
    public static void modifyUserId(long primaryKey, long updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set user_id = " + updateValue + " where employee_no = " + primaryKey);
    }
    public static void modifyEmployeeFn(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set employee_fn = '" + updateValue + "' where employee_no = " + primaryKey);
    }
    public static void modifyEmployeeLn(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set employee_ln = '" + updateValue + "' where employee_no = " + primaryKey);
    }
    public static void modifyPayHour(long primaryKey, double updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set pay_hour = " + updateValue + " where employee_no = " + primaryKey);
    }
    public static void modifyPosition(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set position = '" + updateValue + "' where employee_no = " + primaryKey);
    }
    public static void modifyStartDate(long primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set start_date = '" + updateValue + "' where employee_no = " + primaryKey);
    }
    public static void modifyEndDate(long primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update employees set end_date = '" + updateValue + "' where employee_no = " + primaryKey);
    }

    //SQL Special Queries
    public static Employees selectEmployeeByEmpID(Long employeeNo) throws SQLException{
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from employees where employee_no=" + employeeNo);
        while (dbResult.next()) return new Employees(dbResult.getLong(1), dbResult.getLong(2), dbResult.getString(3), dbResult.getString(4), dbResult.getDouble(5), dbResult.getString(6), dbResult.getDate(7), dbResult.getDate(8));
        return null;
    }
}
