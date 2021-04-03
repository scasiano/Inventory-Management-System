package ims;

import java.sql.*;
import java.util.*;

public class Products {

    public static ResultSet SelectAll() throws SQLException {
        return SqlController.dbStatement.executeQuery("select * from products");
    }
    public static ResultSet SelectProductId() throws SQLException {
        return SqlController.dbStatement.executeQuery("select product_id from products");
    }
    public static ResultSet SelectName() throws SQLException {
        return SqlController.dbStatement.executeQuery("select name from products");
    }
    public static ResultSet SelectMsrp() throws SQLException {
        return SqlController.dbStatement.executeQuery("select msrp from products");
    }
    public static ResultSet SelectPrice() throws SQLException {
        return SqlController.dbStatement.executeQuery("select price from products");
    }

    //TODO: Add
    //TODO: Update
    //TODO: Delete
}
