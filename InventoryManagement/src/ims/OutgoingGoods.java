package ims;
import java.sql.*;
import java.util.ArrayList;

public class OutgoingGoods {
    //TODO: Add
    //TODO: Update
    //TODO: Delete
    //TODO Read
    int outgoingID;
    int productID;
    Date dateGo;
    int quantity;
    int employeeNo;

    public OutgoingGoods(int outgoingID, int productID, Date dateGo, int quantity, int employeeNo) {
        this.outgoingID = outgoingID;
        this.productID = productID;
        this.dateGo = dateGo;
        this.quantity = quantity;
        this.employeeNo = employeeNo;
    }
}
