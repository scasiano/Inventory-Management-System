package ims;
import java.sql.*;
import java.util.ArrayList;

public class CurrentStock { //This entire table is autogen, all products exist here and quantity is changed based on incoming v. outgoing
    long productID;
    int quantity;

    public CurrentStock(long productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    //Get set
    public long getProductID() {
        return productID;
    }
    public int getQuantity() {
        return quantity;
    }

    //SQL Queries READ
    public static ArrayList<CurrentStock> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from current_stock");
        ArrayList<CurrentStock> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new CurrentStock(dbResult.getLong(1), dbResult.getInt(2)));
        return resultList;
    }
    public static ArrayList<Long> selectProductID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from current_stock");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Integer> selectQuantity() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select quantity from current_stock");
        ArrayList<Integer> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getInt(1));
        return resultList;
    }
}
