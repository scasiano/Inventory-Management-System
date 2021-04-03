package ims;
import java.sql.*;
import java.util.ArrayList;

public class OrderItems {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    long orderID;
    long productID;

    public OrderItems(long orderID, long productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public long getProductID() {
        return productID;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }

    //SQL Queries READ
    public static ArrayList<OrderItems> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from order_items");
        ArrayList<OrderItems> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new OrderItems(dbResult.getLong(1), dbResult.getLong(2)));
        return resultList;
    }
    public static ArrayList<Long> selectOrderID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select order_id from order_items");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Long> selectProductID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from order_items");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
}
