package ims;
import java.sql.*;
import java.util.ArrayList;

public class DestinationProducts {
    Date datePlaced;
    long productID;
    String customerAdd;

    public DestinationProducts(Date datePlaced, long productID, String customerAdd) {
        this.datePlaced = datePlaced;
        this.productID = productID;
        this.customerAdd = customerAdd;
    }

    //Get set
    public Date getDatePlaced() {
        return datePlaced;
    }
    public long getProductID() {
        return productID;
    }
    public String getCustomerAdd() {
        return customerAdd;
    }

    //SQL Queries READ
    public static ArrayList<DestinationProducts> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from destination_products");
        ArrayList<DestinationProducts> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new DestinationProducts(dbResult.getDate(1), dbResult.getLong(2), dbResult.getString(3)));
        return resultList;
    }
    public static ArrayList<Date> selectIncomingID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_placed from destination_products");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Long> selectProductID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from destination_products");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectDateIn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select customer_add from destination_products");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
}
