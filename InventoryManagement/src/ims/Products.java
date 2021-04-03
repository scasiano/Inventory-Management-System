package ims;

import java.sql.*;
import java.util.*;

public class Products {
    int productID;
    String name;
    double msrp;
    double price;

    Products(int productID, String name, double msrp, double price){
        this.productID = productID;
        this.name = name;
        this.msrp = msrp;
        this.price = price;
    }
    public static ArrayList<Products> SelectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from products");
        ArrayList<Products> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Products(dbResult.getInt(1), dbResult.getString(2), dbResult.getDouble(3), dbResult.getDouble(4)));
        return resultList;
    }
    public static ArrayList<Integer> SelectProductId() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from products");
        ArrayList<Integer> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getInt(1));
        return resultList;
    }
    public static ArrayList<String> SelectName() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select name from products");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Double> SelectMsrp() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select msrp from products");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }
    public static ArrayList<Double> SelectPrice() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select price from products");
        ArrayList<Double> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDouble(1));
        return resultList;
    }

    //TODO: Add
    //TODO: Update
    //TODO: Delete
}
