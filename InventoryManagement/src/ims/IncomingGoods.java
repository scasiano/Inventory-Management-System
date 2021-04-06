package ims;
import java.sql.*;
import java.util.ArrayList;

public class IncomingGoods {
    //TODO: Add
    //TODO: Update
    long incomingID;
    long productID;
    Date dateIn;
    String trackingNo; //nullable
    int quantity;
    long employeeNo; //nullable

    public IncomingGoods(long incomingID, long productID, Date dateIn, String trackingNo, int quantity, long employeeNo) {
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
    public long getEmployeeNo() {
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
    public void setEmployeeNo(long employeeNo) {
        this.employeeNo = employeeNo;
    }

    //SQL Queries READ
    public static ArrayList<IncomingGoods> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from incoming_goods");
        ArrayList<IncomingGoods> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new IncomingGoods(dbResult.getLong(1), dbResult.getLong(2), dbResult.getDate(3), dbResult.getString(4), dbResult.getInt(5), dbResult.getInt(5)));
        return resultList;
    }
    public static ArrayList<Long> selectIncomingID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select incoming_id from incoming_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Long> selectProductID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from incoming_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Date> selectDateIn() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_in from incoming_goods");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<String> selectTrackingNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select track_no from incoming_goods");
        ArrayList<String> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getString(1));
        return resultList;
    }
    public static ArrayList<Integer> selectQuantity() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select quantity from incoming_goods");
        ArrayList<Integer> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getInt(1));
        return resultList;
    }
    public static ArrayList<Long> selectEmployeeNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_no from incoming_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }

    //SQL Queries DELETE
    public static void deleteRecord(long primaryKey) throws SQLException{
        SqlController.dbStatement.executeUpdate("delete from incoming_goods where incoming_id = " + primaryKey);
    }

    //SQL Queries ADD

    //SQL Queries MODIFY
    public static void modifyProductID(long primaryKey, long updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update incoming_goods set product_id = " + updateValue + " where incoming_id = " + primaryKey);
    }
    public static void modifyDateIn(long primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update incoming_goods set date_in = '" + updateValue + "' where incoming_id = " + primaryKey);
    }
    public static void modifyTrackingNo(long primaryKey, Date updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update incoming_goods set track_no = '" + updateValue + "' where incoming_id = " + primaryKey);
    }
    public static void modifyQuantity(long primaryKey, int updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update incoming_goods set quantity = " + updateValue + " where incoming_id = " + primaryKey);
    }
    public static void modifyEmployeeNo(long primaryKey, long updateValue) throws SQLException {
        SqlController.dbStatement.executeUpdate("update incoming_goods set product_id = " + updateValue + " where incoming_id = " + primaryKey);
    }
}
