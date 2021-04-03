package ims;
import java.sql.*;
import java.util.ArrayList;

public class DestinationProducts {
    //TODO: READ
    Date datePlaced;
    int productID;
    String customerAdd;

    public DestinationProducts(Date datePlaced, int productID, String customerAdd) {
        this.datePlaced = datePlaced;
        this.productID = productID;
        this.customerAdd = customerAdd;
    }
}
