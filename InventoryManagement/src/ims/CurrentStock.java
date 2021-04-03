package ims;
import java.sql.*;
import java.util.ArrayList;

public class CurrentStock {
    //TODO Read
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
}
