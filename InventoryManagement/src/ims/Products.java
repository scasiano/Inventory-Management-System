package ims;
import java.sql.*;
import java.util.ArrayList;

public class Products {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    long productID;
    String name;
    double msrp;
    double price;

    Products(long productID, String name, double msrp, double price){
        this.productID = productID;
        this.name = name;
        this.msrp = msrp;
        this.price = price;
    }

    //Get set
    public long getProductID() {
        return productID;
    }
    public String getName() {
        return name;
    }
    public double getMsrp() {
        return msrp;
    }
    public double getPrice() {
        return price;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    //SQL Queries READ
    public static ArrayList<Products> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from products");
        ArrayList<Products> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Products(dbResult.getLong(1), dbResult.getString(2), dbResult.getDouble(3), dbResult.getDouble(4)));
        return resultList;
    }
    public static ArrayList<Long> selectProductId() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from products");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectName() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select name from products");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Double> selectMsrp() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select msrp from products");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> selectPrice() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select price from products");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
}