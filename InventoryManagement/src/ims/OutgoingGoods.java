package ims;
import java.sql.*;
import java.util.ArrayList;

public class OutgoingGoods {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    long outgoingID;
    long productID;
    Date dateGo;
    int quantity;
    long employeeNo; //nullable

    public OutgoingGoods(long outgoingID, long productID, Date dateGo, int quantity, long employeeNo) {
        this.outgoingID = outgoingID;
        this.productID = productID;
        this.dateGo = dateGo;
        this.quantity = quantity;
        this.employeeNo = employeeNo;
    }

    //Get set
    public long getOutgoingID() {
        return outgoingID;
    }
    public long getProductID() {
        return productID;
    }
    public Date getDateGo() {
        return dateGo;
    }
    public int getQuantity() {
        return quantity;
    }
    public long getEmployeeNo() {
        return employeeNo;
    }
    public void setOutgoingID(long outgoingID) {
        this.outgoingID = outgoingID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }
    public void setDateGo(Date dateGo) {
        this.dateGo = dateGo;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setEmployeeNo(long employeeNo) {
        this.employeeNo = employeeNo;
    }

    //SQL Queries READ
    public static ArrayList<OutgoingGoods> selectAll() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select * from outgoing_goods");
        ArrayList<OutgoingGoods> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(new OutgoingGoods(dbResult.getLong(1), dbResult.getLong(2), dbResult.getDate(3), dbResult.getInt(4), dbResult.getLong(5)));
        return resultList;
    }
    public static ArrayList<Long> selectOutgoingID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select outgoing_id from outgoing_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Long> selectProductID() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select product_id from outgoing_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
    public static ArrayList<Date> selectDateGo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select date_go from outgoing_goods");
        ArrayList<Date> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getDate(1));
        return resultList;
    }
    public static ArrayList<Integer> selectQuantity() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select quantity from outgoing_goods");
        ArrayList<Integer> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getInt(1));
        return resultList;
    }
    public static ArrayList<Long> selectEmployeeNo() throws SQLException {
        ResultSet dbResult = SqlController.dbStatement.executeQuery("select employee_no from outgoing_goods");
        ArrayList<Long> resultList = new ArrayList<>();
        while (dbResult.next()) resultList.add(dbResult.getLong(1));
        return resultList;
    }
}