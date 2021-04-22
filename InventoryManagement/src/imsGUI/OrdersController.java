package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class OrdersController {

    @FXML
    TableView<Orders> orderIDT;
    @FXML
    TableColumn<Orders, Long> oID;
    @FXML
    TableColumn<Orders, String> oFName;
    @FXML
    TableColumn<Orders, String> oLName;
    @FXML
    TableView<OrderItems> orderProds;
    @FXML
    TableColumn<OrderItems, Long> products;
    @FXML
    TableColumn<OrderItems, String> prodName;
    @FXML
    TextField orderID;
    @FXML
    TextField customerName;
    @FXML
    TextField customerAddress;
    @FXML
    ComboBox<String> shippingStatus;
    @FXML
    Label datePlaced;
    @FXML
    DatePicker addDate;
    @FXML
    TextField trackingID;
    @FXML
    ComboBox<String> carrier;
    @FXML
    ComboBox<String> empIDC;
    @FXML
    Button addOrder;
    @FXML
    Button modOrder;
    @FXML
    Label addOrderLabel;
    @FXML
    HBox addOrderHBox;
    @FXML
    HBox modBox;
    @FXML
    Button startMod;
    @FXML
    Button addOrderItem;
    @FXML
    ComboBox<String> productsList;

    ArrayList<Orders> allOrders;
    ArrayList<OrderItems> allItems;
    ArrayList<OrderItems> allAddItems =new ArrayList<>();
    ArrayList<Products> allProd;
    ArrayList<Employees> allEmps;
    Orders otemp;
    boolean hasProd=false;

    public void initialize(){
        setOrderList();
        orderDetails();
        setCombos();
        clearOrderInfo();
    }
    public void setOrderList(){
        try{
            oID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            oFName.setCellValueFactory(new PropertyValueFactory<>("customerFn"));
            oLName.setCellValueFactory(new PropertyValueFactory<>("customerLn"));
            allOrders=Orders.selectAll();
            ObservableList<Orders> oIDs = FXCollections.observableArrayList(allOrders);
            orderIDT.setItems(oIDs);
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Order ID List");
        }
    }
    public void setOrderProds(){
            products.setCellValueFactory(new PropertyValueFactory<>("productID"));
            prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            ObservableList<OrderItems> orderI = FXCollections.observableArrayList(allItems);
            orderProds.setItems(orderI);
    }
    public void setCombos(){
        try{
            productsList.getItems().clear();
            empIDC.getItems().clear();
            carrier.getItems().clear();
            shippingStatus.getItems().clear();
            allProd=Products.selectAll();
            for (Products value : allProd) {
                if (CurrentStock.selectQuantityByProductID(value.getProductID()) != 0) {
                    String s = value.getProductID() + " | " + value.getName();
                    productsList.getItems().add(s);
                }
            }
            allEmps=Employees.selectAll();
            for (Employees allEmp : allEmps) {
                String s = allEmp.getEmployeeNo() + " | " + allEmp.getEmployeeFn() + " " + allEmp.getEmployeeLn();
                empIDC.getItems().add(s);
            }
            carrier.getItems().add("UPS");
            carrier.getItems().add("FEDX");
            carrier.getItems().add("USPS");
            shippingStatus.getItems().add("Not Shipped");
            shippingStatus.getItems().add("Shipped");
            shippingStatus.getItems().add("Delivered");
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Combo");
        }
    }
    public void orderDetails() {
        try {
            orderIDT.setOnMouseClicked(event -> {
                otemp = orderIDT.getSelectionModel().getSelectedItem();
                orderID.setText(String.valueOf(otemp.getOrderID()));
                customerName.setText(otemp.getCustomerFn() + " " + otemp.getCustomerLn());
                customerAddress.setText(otemp.getCustomerAdd());
                datePlaced.setText(String.valueOf(otemp.getDatePlaced()));
                try {
                    empIDC.setValue(otemp.getEmployeeNo() + " | " + Employees.selectEmployeeByEmpID(otemp.getEmployeeNo()).getEmployeeFn() + " " + Employees.selectEmployeeByEmpID(otemp.getEmployeeNo()).getEmployeeLn());
                    allItems = OrderItems.selectByOrderID(otemp.getOrderID());
                    shippingStatus.setValue(Tracking.selectByOrderID(otemp.getOrderID()).getShippingStatus());
                    trackingID.setText(Tracking.selectByOrderID(otemp.getOrderID()).getTrackingID());
                    carrier.setValue(Tracking.selectByOrderID(otemp.getOrderID()).getCarrier());
                }
                catch (SQLException a){
                    Global.exceptionAlert(a, "Setting Employee ID ComboBox");
                }
                if(Global.privilege) modOrder.setVisible(true);
                setOrderProds();
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e,"Show Order Details");
        }
    }
    public void addOrderClicked(ActionEvent event) {
        String[] name=customerName.getText().split(" ");
        boolean empBool =false;
        boolean trackBool=false;
        boolean carryBool=false;
        Orders oTmp = new Orders(0, "", "", "", null, 0);
        Tracking tTmp= new Tracking(0,"Not Shipped");
        try{
                if (orderID.getText().length() > 0 && Long.parseLong(orderID.getText()) >= 0){
                    oTmp.setOrderID(Long.parseLong(orderID.getText()));
                    tTmp.setOrderID(Long.parseLong(orderID.getText()));
                }
                else{
                    Global.warningAlert("Incorrect ID", "Order ID needs to be greater than 0 and less than 9");
                    orderID.clear();
                    return;
                }
                try{
                    if (customerName.getText().length() > 0){
                        oTmp.setCustomerFn(name[0]);
                        oTmp.setCustomerLn(name[1]);
                    }
                    else{
                        Global.warningAlert("Incorrect Customer Name", "Every Order needs a Customer Name");
                        customerName.clear();
                        return;
                    }
                }
                catch(Exception e){
                    Global.warningAlert("Incomplete Name","Users Need a first and Last Name");
                    return;
                }
                if (customerAddress.getText().length() > 0)
                    oTmp.setCustomerAdd(customerAddress.getText());
                else{
                    Global.warningAlert("Incorrect Address", "Every Order needs a Customer address");
                    customerAddress.clear();
                    return;
                }
                if(addDate.getValue()!=null)
                    oTmp.setDatePlaced(java.sql.Date.valueOf(addDate.getValue()));
                else{
                    Global.warningAlert("Incorrect Date", "Every Order needs a Date placed in the format yyyy-MM-dd");
                    addDate.setValue(null);
                    return;
                }
                if(empIDC.getValue()!=null){
                        empBool=true;
                        String s = empIDC.getSelectionModel().getSelectedItem();
                        String[] p = s.split(" \\| ");
                        oTmp.setEmployeeNo(Long.parseLong(p[0]));
                }
                if(shippingStatus.getValue()!=null)
                    tTmp.setShippingStatus(shippingStatus.getValue());
                if(shippingStatus.getValue().equals("Not Shipped")){
                    if(!trackingID.getText().isEmpty()) {
                        tTmp.setTrackingID(trackingID.getText());
                        trackBool=true;
                    }
                    else{
                        Global.warningAlert("Shipping Status", "Your shipping status is Shipped or Delivered. You should have a tracking number.");
                        return;
                    }
                    if(carrier.getValue()!=null) {
                        tTmp.setCarrier(carrier.getValue());
                        carryBool=true;
                    }
                    else{
                        Global.warningAlert("Shipping Status", "Your shipping status is Shipped or Delivered. You should have a the Carrier Organization.");
                        return;
                    }
                }
                if(!trackingID.getText().isEmpty()) {
                    tTmp.setTrackingID(trackingID.getText());
                    trackBool=true;
                }
                if(carrier.getValue()!=null) {
                    tTmp.setCarrier(carrier.getValue());
                    carryBool=true;
                }

                // Adding Order info if there is a product
            if(!hasProd) {
                Global.warningAlert("Missing Products", "You do not have any products for this order");
                return;
            }
            if(!empBool)
                Orders.addRecord(oTmp);
            else
                Orders.addRecordEmp(oTmp);
            //adding Tacking Info
            if(carryBool && trackBool)
                Tracking.addRecordTrackCarrier(tTmp);
            else if(carryBool)
                Tracking.addRecordCarrier(tTmp);
            else if(trackBool)
                Tracking.addRecordTrack(tTmp);
            else
                Tracking.addRecord(tTmp);
            orderIDT.getItems().add(oTmp);
            setOrderList();
            clearOrderInfo();
        }
        catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Order Id Exists", "Order ID already exists. User add canceled");
        }
        catch(Exception p){
            Global.exceptionAlert(p,"Add Order");
        }
    }
    public void addOrderItem() {
        String selectedItem = productsList.getSelectionModel().getSelectedItem();
        String[] s = selectedItem.split(" \\| ");
        if (orderID.getText().length() > 0) {
            OrderItems orderItems = new OrderItems(Long.parseLong(orderID.getText()), Long.parseLong(s[0]));
            products.setCellValueFactory(new PropertyValueFactory<OrderItems,Long>("productID"));
            allAddItems.add(orderItems);
            ObservableList<OrderItems> orderI = FXCollections.observableArrayList(allAddItems);
            orderProds.setItems(orderI);
            hasProd= true;
        }
        else
            Global.warningAlert("No Order ID","Please Create an Order ID before adding a product");
    }
    public void addOrder(){
        orderID.clear();
        customerName.clear();
        customerAddress.clear();
        trackingID.clear();
        productsList.setVisible(true);
        addOrderItem.setVisible(true);
        addDate.setVisible(true);
        orderID.setEditable(true);
        customerName.setEditable(true);
        customerAddress.setEditable(true);
        shippingStatus.setEditable(false);
        trackingID.setEditable(true);
        carrier.setEditable(false);
        empIDC.setEditable(false);
        customerAddress.setDisable(false);
        shippingStatus.setDisable(false);
        empIDC.setDisable(false);
        modOrder.setVisible(false);
        addOrderHBox.setVisible(true);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        carrier.setEditable(false);
        carrier.setDisable(false);
        shippingStatus.setValue(null);
        orderProds.getItems().clear();
    }
    public void modifyOrderClicked(){
        try{
            String[] name=customerName.getText().split(" ");
            Orders.modifyCustomerFn(otemp.getOrderID(),name[0]);
            Orders.modifyCustomerLn(otemp.getOrderID(),name[1]);
            Orders.modifyCustomerAdd(otemp.getOrderID(),customerAddress.getText());
            if(empIDC.getValue()!=null){
                String s = empIDC.getSelectionModel().getSelectedItem();
                String[] p = s.split(" \\| ");
                Orders.modifyEmployeeNo(otemp.getOrderID(),Long.parseLong(p[0]));
            }
            if(shippingStatus.getValue()!=null) Tracking.modifyShippingStatus(otemp.getOrderID(),shippingStatus.getValue());
            clearOrderInfo();
        }
        catch(Exception e){Global.exceptionAlert(e,"Modify Order");}
    }
    public void modOrder(){
        orderProds.setVisible(true);
        customerName.setEditable(true);
        customerAddress.setEditable(false);
        shippingStatus.setEditable(false);
        empIDC.setEditable(false);
        customerAddress.setDisable(false);
        shippingStatus.setDisable(false);
        empIDC.setDisable(false);
        modBox.setVisible(true);
        addOrder.setVisible(false);
        addOrderHBox.setVisible(false);
        startMod.setVisible(true);
        productsList.setVisible(false);
        addOrderItem.setVisible(false);
    }
    public void deleteOrderClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete Order");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                Orders.deleteRecord(otemp.getOrderID());
                orderIDT.getItems().remove(orderIDT.getSelectionModel().getSelectedItem());
                otemp=null;
                clearOrderInfo();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void clearOrderInfo(){

        orderID.clear();
        customerName.clear();
        customerAddress.clear();
        trackingID.clear();
        orderID.setEditable(false);
        customerName.setEditable(false);
        customerAddress.setEditable(false);
        shippingStatus.setEditable(false);
        trackingID.setEditable(false);
        carrier.setEditable(false);
        empIDC.setEditable(false);
        orderProds.setVisible(true);
        modOrder.setVisible(false);
        addOrderHBox.setVisible(true);
        addDate.setVisible(false);
        addOrder.setVisible(true);
        startMod.setVisible(false);
        modBox.setVisible(false);
        addOrderHBox.setVisible(false);
        productsList.setVisible(false);
        addOrderItem.setVisible(false);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        carrier.setDisable(true);
        shippingStatus.setValue(null);
        shippingStatus.setDisable(true);
        orderProds.getItems().clear();
        empIDC.setValue(null);
        empIDC.setDisable(true);
        productsList.setValue(null);
        shippingStatus.setValue(null);
    }

    @FXML
    private void openHomePage(ActionEvent event) {
        clearOrderInfo();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) {
        clearOrderInfo();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
