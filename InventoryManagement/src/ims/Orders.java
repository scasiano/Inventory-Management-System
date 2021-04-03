package ims;
import java.sql.*;
import java.util.ArrayList;

public class Orders {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    int orderID;
    String customerFn;
    String customerLn;
    String customerAdd;
    Date datePlaced;
    int employeeNo;

    public Orders(int orderID, String customerFn, String customerLn, String customerAdd, Date datePlaced, int employeeNo) {
        this.orderID = orderID;
        this.customerFn = customerFn;
        this.customerLn = customerLn;
        this.customerAdd = customerAdd;
        this.datePlaced = datePlaced;
        this.employeeNo = employeeNo;
    }
}
