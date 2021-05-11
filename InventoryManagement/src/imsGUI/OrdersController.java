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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

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
    @FXML
    VBox invoiceBox;
    @FXML
    Label orderTotal;
    @FXML
    TextField ammountPaid;

    ArrayList<Orders> allOrders;
    ArrayList<OrderItems> allItems = new ArrayList<>();
    ArrayList<Products> allProd;
    ArrayList<Employees> allEmps;
    Orders otemp;
    ActiveInvoice aIntmp;
    InvoiceHistory iIntmp;
    Double oPrice;
    long millis=System.currentTimeMillis();
    java.sql.Date placedDate = new java.sql.Date(millis);

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
            int i=0;
            productsList.getItems().clear();
            empIDC.getItems().clear();
            carrier.getItems().clear();
            shippingStatus.getItems().clear();
            allProd=Products.selectAll();
            for (int value=0;value< allProd.size();value++) {
                if (CurrentStock.selectQuantityByProductID(allProd.get(value).getProductID()) != 0) {
                    String s = allProd.get(value).getProductID() + " | " + allProd.get(value).getName();
                    i++;
                    productsList.getItems().add(s);
                }
                if(i==0 && value==allProd.size()-1){
                    Global.warningAlert("No Available Products","All products have a quantity of zero. Please order some Incoming Goods in Product Status.");
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
            lockCombobox();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Combo");
        }
    }
    public void orderDetails() {
        try {
            orderIDT.setOnMouseClicked(event -> {
                shippingStatus.setValue(null);
                carrier.setValue(null);
                empIDC.setValue(null);
                otemp = orderIDT.getSelectionModel().getSelectedItem();
                orderID.setText(String.valueOf(otemp.getOrderID()));
                customerName.setText(otemp.getCustomerFn() + " " + otemp.getCustomerLn());
                customerAddress.setText(otemp.getCustomerAdd());
                datePlaced.setText(String.valueOf(otemp.getDatePlaced()));
                try {
                    if(otemp.getEmployeeNo() != 0)
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
                orderTotal.setText(Double.toString(orderTotal()));
                invoiceBox.setVisible(true);
                try{
                    if(InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size()==0){
                        ammountPaid.setText(Double.toString(ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalRecieved()));
                    }
                    else if(InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size()>0){
                        ammountPaid.setText(Double.toString(InvoiceHistory.selectHistoryByOrderID(otemp.getOrderID()).getTotalCharge()));
                        ammountPaid.setEditable(false);
                    }
                }catch(Exception e){
                    Global.exceptionAlert(e,"Getting Invoice Info");
                }
            });
        }catch (Exception e){
            Global.exceptionAlert(e,"Show Order Details");
        }
    }
    public void addOrderClicked() {
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
                String n="";
                if(empIDC.getValue()!=null && !n.equals(empIDC.getValue())){
                    System.out.println("Why?"+!n.equals(empIDC.getValue()));
                        empBool=true;
                        String s = empIDC.getSelectionModel().getSelectedItem();
                        String[] p = s.split(" \\| ");
                        oTmp.setEmployeeNo(Long.parseLong(p[0]));
                }
                if(shippingStatus.getValue()!=null)
                    tTmp.setShippingStatus(shippingStatus.getValue());
                if(!tTmp.getShippingStatus().equals("Not Shipped")){
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
            if(!empBool){
                Orders.addRecord(oTmp);
            }
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
            addOrderItemToDB();
            setOrderList();
            oPrice=orderTotal();
            orderTotal.setText(oPrice.toString());
           try {
               if (!ammountPaid.getText().isEmpty()) {
                   if (Double.parseDouble(ammountPaid.getText()) < oPrice) {
                       aIntmp = new ActiveInvoice(Long.parseLong(orderID.getText()), oTmp.getDatePlaced(), Double.parseDouble(orderTotal.getText()), Double.parseDouble(ammountPaid.getText()), Double.parseDouble(orderTotal.getText()) - Double.parseDouble(ammountPaid.getText()));
                       ActiveInvoice.addRecord(aIntmp);
                   } else {
                       System.out.println(Long.parseLong(orderID.getText()) + ", " + oTmp.getDatePlaced() + ", " + Double.parseDouble(orderTotal.getText()));
                       iIntmp = new InvoiceHistory(Long.parseLong(orderID.getText()), oTmp.getDatePlaced(), Double.parseDouble(orderTotal.getText()));
                       InvoiceHistory.addRecord(iIntmp);
                   }
               } else
                   Orders.deleteRecord(Long.parseLong(orderID.getText()));
           }catch(NullPointerException e){Global.warningAlert("Ammount Paid box empty","Please make sure to have a Ammount Paid");}
            clearOrderInfo();
            lockCombobox();
        }
        catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Order Id Out of bounds", "Order ID number is too big.");
            try{
                OrderItems.deleteRecord(Long.parseLong(orderID.getText()));
            }catch(Exception ed){Global.exceptionAlert(ed,"Tried deleting order items after order ID failed");}
        }
        catch(Exception p){
            Global.exceptionAlert(p,"Add Order");
        }
    }
    public void addOrderItem() {
        String selectedItem = productsList.getSelectionModel().getSelectedItem();
        String[] s = selectedItem.split(" \\| ");
        if (orderID.getText().length() > 0) {
            OrderItems orderItems = new OrderItems(Long.parseLong(orderID.getText()), Long.parseLong(s[0]),s[1]);
            products.setCellValueFactory(new PropertyValueFactory<>("productID"));
            prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            allItems.add(orderItems);
            ObservableList<OrderItems> orderI = FXCollections.observableArrayList(allItems);
            orderProds.setItems(orderI);
            hasProd= true;
            orderTotal.setText(Double.toString(orderTotal()));
        }
        else
            Global.warningAlert("No Order ID","Please Create an Order ID before adding a product");
    }
    public void addOrderItemToDB() {
        for (OrderItems items:allItems) {
            try{
                OrderItems.addRecord(items);
            }catch(Exception e){
                Global.warningAlert("Order Items","Order Items are not adding to the DB");
            }
        }
    }
    public double orderTotal(){
        double orderSum = 0;
        for (OrderItems item : allItems)
            try {
                orderSum += Products.selectProductMsrpByProductID(item.getProductID());
            }catch(Exception e){
                Global.exceptionAlert(e, "Get Product Price total");
            }
        return orderSum;
    }
    public void modifyOrderClicked(){
        try{

            String[] name=customerName.getText().split(" ");
            Orders.modifyCustomerFn(otemp.getOrderID(),name[0]);
            Orders.modifyCustomerLn(otemp.getOrderID(),name[1]);
            Orders.modifyCustomerAdd(otemp.getOrderID(),customerAddress.getText());
            String n="";
            if(empIDC.getValue()!=null && !n.equals(empIDC.getValue())){
                System.out.println("Why?"+!n.equals(empIDC.getValue()));
                String s = empIDC.getSelectionModel().getSelectedItem();
                String[] p = s.split(" \\| ");
                Orders.modifyEmployeeNo(otemp.getOrderID(),Long.parseLong(p[0]));
            }
            if(shippingStatus.getValue()!=null)
                Tracking.modifyShippingStatus(otemp.getOrderID(),shippingStatus.getValue());
            if(!shippingStatus.getValue().equals("Not Shipped")){
                if(trackingID.getText()!=null) {
                   Tracking.modifyTrackingID(otemp.getOrderID(),trackingID.getText());
                }
                else{
                    Global.warningAlert("Shipping Status", "Your shipping status is Shipped or Delivered. You should have a tracking number.");
                    return;
                }
                if(carrier.getValue()!=null) {
                    Tracking.modifyCarrier(otemp.getOrderID(),carrier.getValue());
                }
                else{
                    Global.warningAlert("Shipping Status", "Your shipping status is Shipped or Delivered. You should have a the Carrier Organization.");
                    return;
                }
            }
           // System.out.println(!ammountPaid.getText().isEmpty());
          //  System.out.println("Balance Resolved:"+!(Double.parseDouble(ammountPaid.getText()) < ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalCharge()));
            if(!ammountPaid.getText().isEmpty()){
                if(InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size()==0) {
                    aIntmp=ActiveInvoice.selectActiveByOrderID(otemp.getOrderID());
                    if (Double.parseDouble(ammountPaid.getText()) < Double.parseDouble(orderTotal.getText())) {
                        //aIntmp = ActiveInvoice.selectActiveByOrderID(otemp.getOrderID());
                        //ActiveInvoice.modifyTotalCharge(aIntmp.getOrderID(), Double.valueOf(orderTotal.getText()));
                        // System.out.println(ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalCharge()+"-"+Double.valueOf(ammountPaid.getText()));
                        ActiveInvoice.modifyOutstandingBalance(otemp.getOrderID(), ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalCharge() - Double.valueOf(ammountPaid.getText()));
                        // System.out.println(ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalCharge()-Double.valueOf(ammountPaid.getText())+"="+ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getOutstandingBalance());
                    } else
                        deleteActiveInvoice();
                }
            }

            if(InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size()==0){
                ammountPaid.setText(Double.toString(ActiveInvoice.selectActiveByOrderID(otemp.getOrderID()).getTotalRecieved()));
            }
            else if(InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size()>0){
                ammountPaid.setText(Double.toString(InvoiceHistory.selectHistoryByOrderID(otemp.getOrderID()).getTotalCharge()));
                ammountPaid.setEditable(false);
            }
            clearOrderInfo();
            lockCombobox();
        }
        catch(Exception e){Global.exceptionAlert(e,"Modify Order");}
    }
    public void deleteOrderClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        oPrice=orderTotal();
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete Order");
        deleteAlert.setContentText("Are you sure you want to delete this Order?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                    if (InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size() == 0) {
                        ActiveInvoice.deleteRecord(otemp.getOrderID());
                        Orders.deleteRecord(otemp.getOrderID());
                        orderIDT.getItems().remove(orderIDT.getSelectionModel().getSelectedItem());
                        otemp = null;
                        clearOrderInfo();
                        lockCombobox();
                    }
                    else
                        Global.warningAlert("Cannot Delete", "You cannot delete an order that is complete because the invoice is no longer active.");
                    clearOrderInfo();
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete Order");
            }
        }
        //clearOrderInfo();
    }
    public void deleteActiveInvoice(){
        try{
            ActiveInvoice.deleteRecord(aIntmp.getOrderID());
            iIntmp = new InvoiceHistory(Long.parseLong(orderID.getText()),java.sql.Date.valueOf(datePlaced.getText()), Double.parseDouble(orderTotal.getText()));
            InvoiceHistory.addRecord(iIntmp);
            ammountPaid.setEditable(false);
            clearOrderInfo();
        }catch(Exception e){
            Global.exceptionAlert(e,"Delete Active Invoice");
        }
    }

    public void addOrder(){
        while(allItems.size()>0){
            int i=allItems.size()-1;
            allItems.remove(i);
        }
        ammountPaid.setText(null);
        orderTotal.setText(null);
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
        trackingID.setEditable(true);
        modOrder.setVisible(false);
        addOrderHBox.setVisible(true);
        invoiceBox.setVisible(true);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        shippingStatus.setValue(null);
        ammountPaid.setEditable(true);
        orderProds.getItems().clear();
        unlockCombobox();
    }

    public void modOrder(){
        orderProds.setVisible(true);
        customerName.setEditable(true);
        customerAddress.setEditable(true);
        try {
            if (InvoiceHistory.selectArrHistoryByOrderID(otemp.getOrderID()).size() == 0 || Double.parseDouble(ammountPaid.getText()) < Double.parseDouble(orderTotal.getText()))
                ammountPaid.setEditable(true);
            else{
             carrier.setDisable(true);
             empIDC.setDisable(true);
             trackingID.setEditable(false);
             shippingStatus.setDisable(true);
            }
        }catch(Exception e){
            Global.exceptionAlert(e,"try to set up modification: Invoice");
        }

                carrier.setDisable(false);
                trackingID.setEditable(true);
        modBox.setVisible(true);
        addOrder.setVisible(false);
        addOrderHBox.setVisible(false);
        startMod.setVisible(true);
        productsList.setVisible(false);
        addOrderItem.setVisible(false);
        invoiceBox.setVisible(true);
        unlockCombobox();
    }
    public void clearOrderInfo(){
        invoiceBox.setVisible(true);
        orderID.clear();
        customerName.clear();
        customerAddress.clear();
        trackingID.clear();
        datePlaced.setText(null);
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
        invoiceBox.setVisible(false);
        productsList.setValue(null);
        empIDC.setValue(null);
        carrier.setValue(null);
        shippingStatus.setValue(null);
        orderProds.getItems().clear();
        empIDC.setValue(null);
        addDate.setValue(placedDate.toLocalDate());
        productsList.setValue(null);
        shippingStatus.setValue(null);
        ammountPaid.setText(null);
        orderTotal.setText(null);
        while(allItems.size()>0){
            int i=allItems.size()-1;
            allItems.remove(i);
        }
        empIDC.setValue(null);
        lockCombobox();
    }

    private void lockCombobox(){
        empIDC.setDisable(true);
        carrier.setDisable(true);
        shippingStatus.setDisable(true);
    }

    private void unlockCombobox(){
        empIDC.setDisable(false);
        carrier.setDisable(false);
        shippingStatus.setDisable(false);
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
