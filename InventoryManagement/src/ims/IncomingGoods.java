package ims;
import java.sql.*;
import java.util.ArrayList;

public class IncomingGoods {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    long incomingID;
    long productID;
    Date dateIn;
    String trackingNo; //nullable
    int quantity;
    int employeeNo; //nullable

    public IncomingGoods(long incomingID, long productID, Date dateIn, String trackingNo, int quantity, int employeeNo) {
        this.incomingID = incomingID;
        this.productID = productID;
        this.dateIn = dateIn;
        this.trackingNo = trackingNo;
        this.quantity = quantity;
        this.employeeNo = employeeNo;
    }

    //Get set
    public long getIncomingID() {
        return incomingID;
    }
    public long getProductID() {
        return productID;
    }
    public Date getDateIn() {
        return dateIn;
    }
    public String getTrackingNo() {
        return trackingNo;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getEmployeeNo() {
        return employeeNo;
    }
    public void setIncomingID(long incomingID) {
        this.incomingID = incomingID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }
    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setEmployeeNo(int employeeNo) {
        this.employeeNo = employeeNo;
    }
}
