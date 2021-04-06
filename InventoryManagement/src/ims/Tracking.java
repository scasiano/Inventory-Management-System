package ims;
import java.sql.*;
import java.util.ArrayList;

public class Tracking {
    //TODO: Add
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

    //SQL Queries READ
    public static ArrayList<Tracking> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from tracking");
        ArrayList<Tracking> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new Tracking(dbResult.getLong(1), dbResult.getString(2), dbResult.getString(3), dbResult.getString(4)));
        return resultList;
    }
    public static ArrayList<Long> selectOutgoingID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select outgoing_id from tracking");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<String> selectShippingStatus() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select shipping_status from tracking");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectTrackingID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select tracking_id from tracking");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<String> selectCarrier() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select carrier from tracking");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }

    //SQL Queries DELETE
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from tracking where order_id = " + primaryKey);
    }

    //SQL Queries ADD

    //SQL Queries MODIFY
    public static void modifyShippingStatus(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update tracking set shipping_status = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyTrackingID(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update tracking set tracking_id = '" + updateValue + "' where order_id = " + primaryKey);
    }
    public static void modifyCarrier(long primaryKey, String updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update tracking set carrier = '" + updateValue + "' where order_id = " + primaryKey);
    }
}
