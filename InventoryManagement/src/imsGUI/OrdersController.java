package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.OrderItems;
import ims.Orders;
import ims.Tracking;
import ims.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersController {

    @FXML
    TableView<Orders> orderIDT;
    @FXML
    TableColumn oID;
    @FXML
    TableColumn oFName;
    @FXML
    TableColumn oLName;
    @FXML
    TableView<OrderItems> orderProds;
    @FXML
    TableColumn products;
    @FXML
    HBox orderList;
    @FXML
    TextField orderID;
    @FXML
    TextField customerName;
    @FXML
    TextField customerAddress;
    @FXML
    TextField shippingStatus;
    @FXML
    Label datePlaced;
    @FXML
    TextField trackingID;
    @FXML
    TextField carrier;
    @FXML
    TextField employeeID;
    @FXML
    Button addOrder;
    @FXML
    Label addOrderLabel;
    @FXML
    HBox addOrderBox;
    @FXML
    HBox modBox;
    @FXML
    Button startMod;

    ArrayList<Orders> allOrders;
    ArrayList<OrderItems> allItems;
    int oIndex=-1;
    //Orders otemp;
    public void initialize(){
        //setOrderProds();
        setOrderList();
        orderDetails();
    }
    public void setOrderList(){
        try{
            oID.setCellValueFactory(new PropertyValueFactory<Orders,Long>("orderID"));
            oFName.setCellValueFactory(new PropertyValueFactory<Orders,String>("customerFn"));
            oLName.setCellValueFactory(new PropertyValueFactory<Orders,String>("customerLn"));
            allOrders=Orders.selectAll();
            ObservableList<Orders> oIDs = FXCollections.observableArrayList(allOrders);
            orderIDT.setItems(oIDs);
        }catch(Exception e){
            Global.exceptionAlert(e,"Set Order ID List");
        }
    }
    public void setOrderProds(){
        try {
            products.setCellValueFactory(new PropertyValueFactory<OrderItems,Long>("productID"));
            allItems = OrderItems.selectAll();
            ObservableList<OrderItems> orderI = FXCollections.observableArrayList(allItems);
            orderProds.setItems(orderI);
        }catch(SQLException e){
            Global.exceptionAlert(e,"Set Order Items Products Table");
        }
    }
    public void orderDetails() {
        try {
            orderIDT.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    oIndex= orderIDT.getSelectionModel().getSelectedIndex();
                    orderID.setText(String.valueOf(allOrders.get(oIndex).getOrderID()));
                    customerName.setText(allOrders.get(oIndex).getCustomerFn()+" "+allOrders.get(oIndex).getCustomerLn());
                    customerAddress.setText(allOrders.get(oIndex).getCustomerAdd());
                    datePlaced.setText(String.valueOf(allOrders.get(oIndex).getDatePlaced()));
                    employeeID.setText(String.valueOf(allOrders.get(oIndex).getEmployeeNo()));
                    /*try {
                        shippingStatus.setText(Tracking.selectByOID(allOrders.get(oIndex).getOrderID()).getShippingStatus());
                        trackingID.setText(Tracking.selectByOID(allOrders.get(oIndex).getOrderID()).getTrackingID());
                        carrier.setText(Tracking.selectByOID(allOrders.get(oIndex).getOrderID()).getCarrier());
                    } catch (SQLException p) {
                        Global.exceptionAlert(p,"Show Order Tracking Details");
                    }*/
                    if(Global.privilege)
                        startMod.setVisible(true);
                }
            });
        }catch (Exception e){
            Global.exceptionAlert(e,"Show Order Details");
        }

    }
    public void addOrderClicked(ActionEvent event) throws SQLException {
        String[] name=customerName.getText().split(" ");
        int method =0;
        Orders tmp = new Orders(0, "", "", "", null, 0);
        boolean flag = false;
        try{
            while(!flag){
                if (orderID.getText().length() > 0 && Long.valueOf(orderID.getText().length()) >= 0){
                    flag = true;
                    tmp.setOrderID(Long.parseLong(orderID.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect ID", "Order ID needs to be greater than 0 and less than 9");
                    orderID.clear();
                }
                if (customerName.getText().length() > 0){
                    flag = true;
                    tmp.setCustomerFn(name[0]);
                    tmp.setCustomerLn(name[1]);
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Customer Name", "Every Order needs a Customer Name");
                    customerName.clear();
                }
                if (customerAddress.getText().length() > 0){
                    flag = true;
                    tmp.setCustomerAdd(customerAddress.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Address", "Every Order needs a Customer address");
                    customerAddress.clear();
                }
                if(datePlaced.getText().length()>0){
                    tmp.setDatePlaced(java.sql.Date.valueOf(datePlaced.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Date", "Every Order needs a Date placed in the format yyyy-MM-dd");
                    datePlaced.setText("");
                }
                if(Long.valueOf(employeeID.getText())>0){
                    method ++;
                    tmp.setEmployeeNo(Long.parseLong(employeeID.getText()));
                }

            }
            if(method==0){
                Orders.addRecord(tmp);}
            else{
                Orders.addRecordEmp(tmp);}
            orderIDT.getItems().add(tmp);
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Order Id Exists", "Order ID already exists. User add canceled");
        } catch(Exception p){
            Global.exceptionAlert(p,"Add Order");
        }
    }
    public void deleteOrderClicked() throws SQLException {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete Order");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Orders.deleteRecord(allOrders.get(oIndex).getOrderID());
                orderIDT.getItems().remove(orderIDT.getSelectionModel().getSelectedItem());
                oIndex=-1;
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    @FXML
    private void openHomePage(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
