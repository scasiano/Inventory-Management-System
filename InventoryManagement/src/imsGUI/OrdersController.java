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
    HBox orderList;
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
    ArrayList<String> eNames = new ArrayList<>();
    boolean hasProd=false;
    int oIndex=-1;
    //Orders otemp;
    public void initialize(){
        setOrderList();
        orderDetails();
        setCombos();
    }
    public void setOrderList(){
        try{
            oID.setCellValueFactory(new PropertyValueFactory<Orders,Long>("orderID"));
            oFName.setCellValueFactory(new PropertyValueFactory<Orders,String>("customerFn"));
            oLName.setCellValueFactory(new PropertyValueFactory<Orders,String>("customerLn"));
            allOrders=Orders.selectAll();
            ObservableList<Orders> oIDs = FXCollections.observableArrayList(allOrders);
            orderIDT.setItems(oIDs);
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Order ID List");
        }
    }
    public void setOrderProds(){
        try {
            products.setCellValueFactory(new PropertyValueFactory<OrderItems,Long>("productID"));
            allItems = OrderItems.selectByOrderID(allOrders.get(oIndex).getOrderID());
            ObservableList<OrderItems> orderI = FXCollections.observableArrayList(allItems);
            orderProds.setItems(orderI);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set Order Items Products Table");
        }
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
            allEmps= Employees.selectAll();
            /*ArrayList<String> ftemp=Employees.selectEmployeeFn();
            ArrayList<String> ltemp=Employees.selectEmployeeLn();
            for(int i=0;i<ftemp.size();i++){
                eNames.add(ftemp.get(i)+" "+ltemp.get(i));
            }*/
            for (Employees allEmp : allEmps) {
                String s = Long.toString(allEmp.getEmployeeNo());
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
                oIndex= orderIDT.getSelectionModel().getSelectedIndex();
                orderID.setText(String.valueOf(allOrders.get(oIndex).getOrderID()));
                customerName.setText(allOrders.get(oIndex).getCustomerFn()+" "+allOrders.get(oIndex).getCustomerLn());
                customerAddress.setText(allOrders.get(oIndex).getCustomerAdd());
                datePlaced.setText(String.valueOf(allOrders.get(oIndex).getDatePlaced()));
                empIDC.setValue(Long.toString(allOrders.get(oIndex).getEmployeeNo()));
                setOrderProds();
                try {
                    shippingStatus.setValue(Tracking.selectByOrderID(allOrders.get(oIndex).getOrderID()).getShippingStatus());
                    trackingID.setText(Tracking.selectByOrderID(allOrders.get(oIndex).getOrderID()).getTrackingID());
                    carrier.setValue(Tracking.selectByOrderID(allOrders.get(oIndex).getOrderID()).getCarrier());
                }
                catch (SQLException a){
                    a.printStackTrace();
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
                if (orderID.getText().length() > 0 && Long.parseLong(orderID.getText()) >= 0) {
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
                        oTmp.setEmployeeNo(Long.parseLong(empIDC.getValue()));
                }
                if(shippingStatus.getValue()!=null)
                    tTmp.setShippingStatus(shippingStatus.getValue());
                if(!Objects.equals(shippingStatus.getValue(), "Not Shipped")){
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
        shippingStatus.setEditable(true);
        trackingID.setEditable(true);
        carrier.setEditable(true);
        empIDC.setEditable(true);
        modOrder.setVisible(false);
        addOrderHBox.setVisible(true);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        shippingStatus.setValue(null);
        orderProds.getItems().clear();
    }
    public void modifyOrderClicked(){
        try{
            String[] name=customerName.getText().split(" ");
            Orders.modifyCustomerFn(allOrders.get(oIndex).getOrderID(),name[0]);
            Orders.modifyCustomerLn(allOrders.get(oIndex).getOrderID(),name[1]);
            Orders.modifyCustomerAdd(allOrders.get(oIndex).getOrderID(),customerAddress.getText());
            if(empIDC.getValue()!=null){
                String s = empIDC.getSelectionModel().getSelectedItem();
                Orders.modifyEmployeeNo(allOrders.get(oIndex).getOrderID(),Long.parseLong(s));
            }
            if(shippingStatus.getValue()!=null) Tracking.modifyShippingStatus(allOrders.get(oIndex).getOrderID(),shippingStatus.getValue());
            clearOrderInfo();
        }
        catch(Exception e){Global.exceptionAlert(e,"Modify Order");}
    }
    public void modOrder(){
        orderProds.setVisible(true);
        customerName.setEditable(true);
        customerAddress.setEditable(true);
        shippingStatus.setEditable(true);
        empIDC.setEditable(true);
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
                ims.Orders.deleteRecord(allOrders.get(oIndex).getOrderID());
                orderIDT.getItems().remove(orderIDT.getSelectionModel().getSelectedItem());
                oIndex=-1;
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void clearOrderInfo(){
        orderProds.setVisible(true);
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
        modOrder.setVisible(false);
        addOrderHBox.setVisible(true);
        addDate.setVisible(false);
        addOrder.setVisible(true);
        modOrder.setVisible(true);
        startMod.setVisible(false);
        modBox.setVisible(false);
        addOrderHBox.setVisible(false);
        productsList.setVisible(false);
        addOrderItem.setVisible(false);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        shippingStatus.setValue(null);
        orderProds.getItems().clear();
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
