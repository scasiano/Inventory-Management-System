package ims;
import java.sql.*;
import java.util.ArrayList;

public class OrderStatus {
    Date orderDate;
    Date invoiceDate;
    double outstandingBalance;
    String shippingStatus;

    public OrderStatus(Date orderDate, Date invoiceDate, double outstandingBalance, String shippingStatus) {
        this.orderDate = orderDate;
        this.invoiceDate = invoiceDate;
        this.outstandingBalance = outstandingBalance;
        this.shippingStatus = shippingStatus;
    }

    //Get set
    public Date getOrderDate() {
        return orderDate;
    }
    public Date getInvoiceDate() {
        return invoiceDate;
    }
    public double getOutstandingBalance() {
        return outstandingBalance;
    }
    public String getShippingStatus() {
        return shippingStatus;
    }

    //SQL Queries READ
    public static ArrayList<OrderStatus> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from orders_status");
        ArrayList<OrderStatus> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new OrderStatus(dbResult.getDate(1), dbResult.getDate(2), dbResult.getDouble(3), dbResult.getString(4)));
        return resultList;
    }
    public static ArrayList<Date> selectOrderDate() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select order_date from orders_status");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Date> selectInvoiceDate() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select invoice_date from orders_status");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Double> selectOutstandingBudget() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select outstanding_balance from orders_status");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<String> selectShippingStatus() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select shipping_status from orders_status");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
}
