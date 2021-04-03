package ims;

import java.sql.*;
import java.util.*;

public class Products {

    public static ResultSet SelectAll() throws SQLException {
        ArrayList<String[]> dbResultList = new ArrayList<>();
        ResultSet dbResultSet = SqlController.dbStatement.executeQuery("select * from products");
        return dbResultSet;
    }
    public static ResultSet SelectProductId() throws SQLException {
        ArrayList<String[]> dbResultList = new ArrayList<>();
        ResultSet dbResultSet = SqlController.dbStatement.executeQuery("select product_id from products");
        return dbResultSet;
    }
    public static ResultSet SelectName() throws SQLException {
        ArrayList<String[]> dbResultList = new ArrayList<>();
        ResultSet dbResultSet = SqlController.dbStatement.executeQuery("select name from products");
        return dbResultSet;
    }
    public static ResultSet SelectMsrp() throws SQLException {
        ArrayList<String[]> dbResultList = new ArrayList<>();
        ResultSet dbResultSet = SqlController.dbStatement.executeQuery("select msrp from products");
        return dbResultSet;
    }
    public static ResultSet SelectPrice() throws SQLException {
        ArrayList<String[]> dbResultList = new ArrayList<>();
        ResultSet dbResultSet = SqlController.dbStatement.executeQuery("select price from products");
        return dbResultSet;
    }

    //TODO: Add
    //TODO: Update
    //TODO: Delete
}
