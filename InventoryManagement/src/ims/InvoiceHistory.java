package ims;
import java.sql.*;
import java.util.ArrayList;

public class InvoiceHistory {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    long orderID;
    Date datePlaced;
    double totalCharge;

    public InvoiceHistory(long orderID, Date datePlaced, double totalCharge) {
        this.orderID = orderID;
        this.datePlaced = datePlaced;
        this.totalCharge = totalCharge;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public Date getDatePlaced() {
        return datePlaced;
    }
    public double getTotalCharge() {
        return totalCharge;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }
    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    //SQL Queries READ
    public static ArrayList<InvoiceHistory> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from invoice_history");
        ArrayList<InvoiceHistory> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new InvoiceHistory(dbResult.getLong(1), dbResult.getDate(2), dbResult.getDouble(3)));
        return resultList;
    }
    public static ArrayList<Long> selectOrderID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select order_id from invoice_history");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Date> selectDatePlaced() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_placed from invoice_history");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Double> selectTotalCharge() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select total_charge from invoice_history");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
}
