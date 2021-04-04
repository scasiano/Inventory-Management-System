package ims;
import java.sql.*;
import java.util.ArrayList;

public class ActiveInvoice {
    //TODO: Update
    long orderID;
    Date dateProcessed;
    double totalCharge;
    double totalRecieved; //nullable //generated from totalcharges and outstandingbalance
    double outstandingBalance;

    //For adding
    ActiveInvoice(long orderID, Date dateProcessed, double totalCharge, double outstandingBalance){
        this.orderID = orderID;
        this.dateProcessed = dateProcessed;
        this.totalCharge = totalCharge;
        this.outstandingBalance = outstandingBalance;
    }

    //For returning
    ActiveInvoice(long orderID, Date dateProcessed, double totalCharge, double totalRecieved, double outstandingBalance){
        this.orderID = orderID;
        this.dateProcessed = dateProcessed;
        this.totalCharge = totalCharge;
        this.totalRecieved = totalRecieved;
        this.outstandingBalance = outstandingBalance;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public Date getDateProcessed() {
        return dateProcessed;
    }
    public double getTotalCharge() {
        return totalCharge;
    }
    public double getTotalRecieved() {
        return totalRecieved;
    }
    public double getOutstandingBalance() {
        return outstandingBalance;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    //SQL Queries READ
    public static ArrayList<ActiveInvoice> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from active_invoice");
        ArrayList<ActiveInvoice> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new ActiveInvoice(dbResult.getLong(1), dbResult.getDate(2), dbResult.getDouble(3), dbResult.getDouble(4), dbResult.getDouble(5)));
        return resultList;
    }
    public static ArrayList<Long> selectOrderId() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select order_id from active_invoice");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Date> selectDateProcessed() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_processed from active_invoice");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Double> selectTotalCharge() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select total_charge from active_invoice");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> selectTotalRecieved() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select total_recieved from active_invoice");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> selectOutstandingBalance() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select outstanding_balance from active_invoice");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }

    //SQL Queries DELETE
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from active_invoice where order_id = " + primaryKey);
    }

    //SQL Queries ADD
    //Must add to orders first, orderID FK
    public static void AddRecord(ActiveInvoice recordToAdd) throws SQLException {
        SqlController.dbStatement.executeUpdate("insert into active_invoice(order_id, date_processed, total_charge, outstanding_balance) values (" + recordToAdd.orderID + ", '" + recordToAdd.dateProcessed + "', " +recordToAdd.totalCharge + ", " + recordToAdd.outstandingBalance + ")");
    }
}
