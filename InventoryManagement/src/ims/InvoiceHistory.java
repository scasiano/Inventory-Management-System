package ims;
import java.sql.*;
import java.util.ArrayList;

public class InvoiceHistory {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    long orderID;
    Date datePlaced;
    double totalCharge;

    public InvoiceHistory(long orderID, Date datePlaced, double totalCharge) {
        this.orderID = orderID;
        this.datePlaced = datePlaced;
        this.totalCharge = totalCharge;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public Date getDatePlaced() {
        return datePlaced;
    }
    public double getTotalCharge() {
        return totalCharge;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }
    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
}
