package ims;
import java.sql.*;
import java.util.ArrayList;

public class ActiveInvoice {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO: Read
    long orderID;
    Date dateProcessed;
    double totalCharge;
    double totalRecieved; //nullable //generated from totalcharger and outstandingbalance
    double outstandingBalance;

    ActiveInvoice(long orderID, Date dateProcessed, double totalCharge, double totalRecieved, double outstandingBalance){
        this.orderID = orderID;
        this.dateProcessed = dateProcessed;
        this.totalCharge = totalCharge;
        this.totalRecieved = totalRecieved;
        this.outstandingBalance = outstandingBalance;
    }

    //Get set
    public long getOrderID() {
        return orderID;
    }
    public Date getDateProcessed() {
        return dateProcessed;
    }
    public double getTotalCharge() {
        return totalCharge;
    }
    public double getTotalRecieved() {
        return totalRecieved;
    }
    public double getOutstandingBalance() {
        return outstandingBalance;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}
