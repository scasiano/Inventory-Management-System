package ims;
import java.sql.*;
import java.util.ArrayList;

public class Orders {
    //TODO: Add
    long orderID;
    String customerFn;
    String customerLn;
    String customerAdd;
    Date datePlaced;
    long employeeNo;

    public Orders(long orderID, String customerFn, String customerLn, String customerAdd, Date datePlaced, long employeeNo) {
        this.orderID = orderID;
        this.customerFn = customerFn;
        this.customerLn = customerLn;
        this.customerAdd = customerAdd;
        this.datePlaced = datePlaced;
        this.employeeNo = employeeNo;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public String getCustomerFn() {
        return customerFn;
    }
    public String getCustomerLn() {
        return customerLn;
    }
    public String getCustomerAdd() {
        return customerAdd;
    }
    public Date getDatePlaced() {
        return datePlaced;
    }
    public long getEmployeeNo() {
        return employeeNo;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setCustomerFn(String customerFn) {
        this.customerFn = customerFn;
    }
    public void setCustomerLn(String customerLn) {
        this.customerLn = customerLn;
    }
    public void setCustomerAdd(String customerAdd) {
        this.customerAdd = customerAdd;
    }
    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }
    public void setEmployeeNo(long employeeNo) {
        this.employeeNo = employeeNo;
    }

    //SQL Queries READ
    public static ArrayList<Orders> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from orders");
        ArrayList<Orders> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Orders(dbResult.getLong(1), dbResult.getString(2), dbResult.getString(3), dbResult.getString(4), dbResult.getDate(5), dbResult.getLong(6)));
        return resultList;
    }
    public static ArrayList<Long> selectOrderID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select order_id from orders");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectCustomerFn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select customer_fn from orders");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectCustomerLn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select customer_ln from orders");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectCustomerAdd() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select customer_add from orders");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Date> selectDatePlaced() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_placed from orders");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Long> selectEmployeeNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_no from orders");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }

    //SQL Queries DELETE
    //You must also delete all record where this orderID is FK
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from orders where order_id = " + primaryKey);
    }

    //SQL Queries ADD

    //SQL Queries MODIFY
    public static void modifyCustomerFn(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update orders set customer_fn = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyCustomerLn(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update orders set customer_ln = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyCustomerAdd(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update orders set customer_add = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyDatePlaced(long primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update orders set date_placed = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyEmployeeNo(long primaryKey, long updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update orders set employee_no = " + updateValue + " where order_id = " + primaryKey);
    }
}
