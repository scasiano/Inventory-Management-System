package ims;
import java.sql.*;
import java.util.ArrayList;

public class OrderItems {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
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
}
