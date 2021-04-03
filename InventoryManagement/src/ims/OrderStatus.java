package ims;
import java.sql.*;
import java.util.ArrayList;

public class OrderStatus {
    //TODO Read
    Date orderDate;
    Date invoiceDate;
    double outstandingBalance;
    String shippingStatus;

    public OrderStatus(Date orderDate, Date invoiceDate, double outstandingBalance, String shippingStatus) {
        this.orderDate = orderDate;
        this.invoiceDate = invoiceDate;
        this.outstandingBalance = outstandingBalance;
        this.shippingStatus = shippingStatus;
    }
}
