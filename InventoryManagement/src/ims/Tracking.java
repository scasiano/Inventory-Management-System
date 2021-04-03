package ims;
import java.sql.*;
import java.util.ArrayList;

public class Tracking {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    long orderID;
    String shippingStatus;
    String trackingID;
    String carrier;

    public Tracking(long orderID, String shippingStatus, String trackingID, String carrier) {
        this.orderID = orderID;
        this.shippingStatus = shippingStatus;
        this.trackingID = trackingID;
        this.carrier = carrier;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public String getShippingStatus() {
        return shippingStatus;
    }
    public String getTrackingID() {
        return trackingID;
    }
    public String getCarrier() {
        return carrier;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
