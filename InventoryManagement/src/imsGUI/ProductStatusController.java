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

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductStatusController {
    @FXML
    TableView<IncomingGoods> incomingT;
    @FXML
    Button addIncomB;
    @FXML
    Button modIncomB;
    @FXML
    VBox incomingV;
    @FXML
    HBox inAddB;
    @FXML
    HBox inModB;
    @FXML
    TableColumn inID;
    @FXML
    TableColumn inProductID;
    @FXML
    TableColumn dateIn;
    @FXML
    TableColumn trackingID;
    @FXML
    TableColumn inQuantity;
    @FXML
    TableColumn inEmpID;
    @FXML
    TextField inPIDT;
    @FXML
    DatePicker inDateT;
    @FXML
    TextField trackNoT;
    @FXML
    TextField inQuantT;
    @FXML
    ComboBox inEmpC;
    @FXML
    Button inDelete;
    @FXML
    ComboBox inProductsList;

    @FXML
    TableView<OutgoingGoods> outgoingT;
    @FXML
    Button addOutB;
    @FXML
    Button modOutB;
    @FXML
    VBox outgoingV;
    @FXML
    HBox outAddB;
    @FXML
    HBox outModB;
    @FXML
    TableColumn outID;
    @FXML
    TableColumn outProductID;
    @FXML
    TableColumn dateGo;
    @FXML
    TableColumn outQuantity;
    @FXML
    TableColumn outEmpID;
    @FXML
    TextField outPIDT;
    @FXML
    DatePicker outDateT;
    @FXML
    TextField outQuantT;
    @FXML
    ComboBox outEmpC;
    @FXML
    Button outDelete;
    @FXML
    ComboBox outProductsList;


    @FXML
    TableView currentT;
    @FXML
    TableColumn productID;
    @FXML
    TableColumn curProductName;
    @FXML
    TableColumn curQuantity;


    ArrayList<IncomingGoods> allIn =new ArrayList<>();
    ArrayList<OutgoingGoods> allOut =new ArrayList<>();
    ArrayList<CurrentStock> allCur=new ArrayList<>();
    ArrayList<Products> allProd = new ArrayList<>();
    ArrayList<Employees> allEmps = new ArrayList<>();
    ArrayList<String> pNames;
    ArrayList<String> eNames = new ArrayList<>();
    IncomingGoods intmp;
    OutgoingGoods outtmp;
    public void initialize(){
        setIncomingTable();
        setOutgoingTable();
        setProdCombo();
        setEmpCombo();
        setCurrentTable();
        getSelectedInfo();

    }
    public void setIncomingTable(){
        try {
            inID.setCellValueFactory(new PropertyValueFactory<IncomingGoods,Long>("incomingID"));
            inProductID.setCellValueFactory(new PropertyValueFactory<IncomingGoods,Long>("productID"));
            dateIn.setCellValueFactory(new PropertyValueFactory<IncomingGoods, Date>("dateIn"));
            trackingID.setCellValueFactory(new PropertyValueFactory<IncomingGoods,String>("trackingNo"));
            inQuantity.setCellValueFactory(new PropertyValueFactory<IncomingGoods,Integer>("quantity"));
            inEmpID.setCellValueFactory(new PropertyValueFactory<IncomingGoods,Long>("employeeNo"));
            allIn = IncomingGoods.selectAll();
            ObservableList<IncomingGoods> incoming = FXCollections.observableArrayList(allIn);
            incomingT.setItems(incoming);
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    public void setOutgoingTable(){
        try {
            outID.setCellValueFactory(new PropertyValueFactory<OutgoingGoods,Long>("outgoingID"));
            outProductID.setCellValueFactory(new PropertyValueFactory<OutgoingGoods,Long>("productID"));
            dateGo.setCellValueFactory(new PropertyValueFactory<OutgoingGoods, Date>("dateGo"));
            outQuantity.setCellValueFactory(new PropertyValueFactory<OutgoingGoods,Integer>("quantity"));
            outEmpID.setCellValueFactory(new PropertyValueFactory<OutgoingGoods,Long>("employeeNo"));
            allOut = OutgoingGoods.selectAll();
            ObservableList<OutgoingGoods> outgoing = FXCollections.observableArrayList(allOut);
            outgoingT.setItems(outgoing);
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    public void setCurrentTable(){
        try {
            productID.setCellValueFactory(new PropertyValueFactory<CurrentStock,Long>("productID"));
            //curProductName.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
            curQuantity.setCellValueFactory(new PropertyValueFactory<CurrentStock,Integer>("quantity"));
            allCur = CurrentStock.selectAll();
            ObservableList<CurrentStock> current = FXCollections.observableArrayList(allCur);
            currentT.setItems(current);
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    public void getSelectedInfo(){
        try{
            incomingT.setOnMouseClicked(event -> {
                intmp = incomingT.getSelectionModel().getSelectedItem();
                int i = incomingT.getSelectionModel().getSelectedIndex();
                modIncomB.setVisible(true);
                inProductsList.setValue(intmp.getProductID() +" | "+pNames.get(i));
                inDateT.setValue(intmp.getDateIn().toLocalDate());
                trackNoT.setText(intmp.getTrackingNo());
                inQuantT.setText(Integer.toString(intmp.getQuantity()));
                inEmpC.setValue(intmp.getEmployeeNo() +" | "+eNames.get(i));
            });
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Incoming Mouse Click");
        }
        try{
            outgoingT.setOnMouseClicked(event -> {
                outtmp = outgoingT.getSelectionModel().getSelectedItem();
                modOutB.setVisible(true);
                outDateT.setValue(outtmp.getDateGo().toLocalDate());
                outEmpC.setValue(outtmp.getEmployeeNo());
                outQuantT.setText(String.valueOf(outtmp.getQuantity()));
                outProductsList.setValue(outtmp.getProductID());
            });
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Outgoing Mouse Click");
        }
    }

    public void setProdCombo(){
        try{
            allProd= Products.selectAll();
            pNames = Products.selectName();
            for (Products products : allProd) {
                if (CurrentStock.selectQuantityByProductID(products.getProductID()) != 0) {
                    String s = products.getProductID() + " | " + products.getName();
                    outProductsList.getItems().add(s);
                    inProductsList.getItems().add(s);
                }
            }
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Product Combo");
        }
    }
    public void setEmpCombo(){
        try{
            allEmps= Employees.selectAll();
            ArrayList<String> ftemp=Employees.selectEmployeeFn();
            ArrayList<String> ltemp=Employees.selectEmployeeLn();
            for(int i=0;i<ftemp.size();i++){
                eNames.add(ftemp.get(i)+" "+ltemp.get(i));
            }
            for (Employees allEmp : allEmps) {
                String s = allEmp.getEmployeeNo() + " | " + allEmp.getEmployeeFn() + " " + allEmp.getEmployeeLn();
                inEmpC.getItems().add(s);
                outEmpC.getItems().add(s);
            }
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Employee Combo");
        }
    }
    public void saveIncomingClicked(ActionEvent event) {
        boolean hasTrack = false;
        boolean hasEmp = false;
        IncomingGoods tmp= new IncomingGoods(0, null, "", 0, 0);
        try{
            try{
                if (inProductsList.getValue() != null){
                    String selectedItem = (String) inProductsList.getSelectionModel().getSelectedItem();
                    String[] s = selectedItem.split(" | ");
                    tmp.setProductID(Long.parseLong(s[0]));
                }
            }
            catch(Exception e){Global.exceptionAlert(e,"Incoming  Products Combo");}
            if (inDateT.getValue()!=null) tmp.setDateIn(java.sql.Date.valueOf(inDateT.getValue()));
            else{
                Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyyy-MM-dd");
                inDateT.setValue(null);
                return;
            }
            if (trackNoT.getText().length() > 0){
                tmp.setTrackingNo(trackNoT.getText());
                hasTrack = true;
            }
            if (inQuantT.getText().length() > 0) tmp.setQuantity(Integer.parseInt(inQuantT.getText()));
            else{
                Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                inQuantT.clear();
                return;
            }
            if(inEmpC.getValue()!=null){
                String selectedItem = (String) inEmpC.getSelectionModel().getSelectedItem();
                String[] s = selectedItem.split(" | ");
                tmp.setProductID(Long.parseLong(s[0]));
                hasEmp = true;
            }
            if (hasEmp && hasTrack) IncomingGoods.addRecordTrackEmp(tmp);
            else if (hasEmp) IncomingGoods.addRecordEmp(tmp);
            else if (hasTrack) IncomingGoods.addRecordTrack(tmp);
            else IncomingGoods.addRecord(tmp);
            setIncomingTable();
            setCurrentTable();
            hideData();
        }
        catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Incoming Id Exists", "Incoming ID already exists. Incoming Good add canceled");
            Global.exceptionAlert(e, "add incoming");
        }
        catch(Exception p){
            Global.exceptionAlert(p,"Save Incoming");
        }
    }

    public void saveOutgoingClicked(ActionEvent event) {
        boolean hasEmp = false;
        OutgoingGoods tmp = new OutgoingGoods(0, null, 0, 0);
        try {
            try{
                if (outProductsList.getValue()!=null) {
                    String selectedItem = (String) outProductsList.getSelectionModel().getSelectedItem();
                    String[] s = selectedItem.split(" | ");
                    tmp.setProductID(Long.parseLong(s[0]));
                }
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Out Products Combo");
            }
            if (outDateT.getValue()!=null) {
                tmp.setDateGo(java.sql.Date.valueOf(outDateT.getValue()));
            }
            else {
                Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyyy-MM-dd");
                outDateT.setValue(null);
                return;
            }
            if (outQuantT.getText().length() > 0) {
                tmp.setQuantity(Integer.parseInt(outQuantT.getText()));
            }
            else {
                Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                outQuantT.clear();
                return;
            }
            if (outEmpC.getValue()!=null) {
                String selectedItem = outEmpC.getSelectionModel().getSelectedItem().toString();
                String[] s = selectedItem.split(" | ");
                tmp.setEmployeeNo(Long.parseLong(s[0]));
                hasEmp = true;
            }
            if (hasEmp) OutgoingGoods.addRecordEmp(tmp);
            else OutgoingGoods.addRecord(tmp);
            setOutgoingTable();
            setCurrentTable();
            hideData();
        }
        catch(MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Outgoing Id Exists", "Outgoing ID already exists. Incoming Good add canceled");
        }
        catch(Exception p){
            Global.exceptionAlert(p, "Save Outgoing");
        }
    }
    public void modIncom(){
        try{
            String selected = inProductsList.getSelectionModel().getSelectedItem().toString();
            String[] p = selected.split(" | ");
            IncomingGoods.modifyDateIn(intmp.getIncomingID(),java.sql.Date.valueOf(inDateT.getValue()));
            if(inEmpC.getValue()!=null){
                String selectedItem = inEmpC.getSelectionModel().getSelectedItem().toString();
                String[] s = selectedItem.split(" | ");
                IncomingGoods.modifyEmployeeNo(intmp.getIncomingID(),Long.parseLong(s[0]));
            }
            IncomingGoods.modifyProductID(intmp.getIncomingID(),(Long.parseLong(p[0])));
            IncomingGoods.modifyQuantity(intmp.getIncomingID(),Integer.parseInt(inQuantT.getText()));
            if (trackNoT.getText().length() > 0){
                IncomingGoods.modifyTrackingNo(intmp.getIncomingID(),trackNoT.getText());
            }
            setIncomingTable();
            setCurrentTable();
            hideData();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify Incoming Goods");
        }
    }
    public void modOut(){
        try{
            String selected = outProductsList.getSelectionModel().getSelectedItem().toString();
            String[] p = selected.split(" | ");
            OutgoingGoods.modifyDateGo(outtmp.getOutgoingID(),java.sql.Date.valueOf(outDateT.getValue()));
            OutgoingGoods.modifyProductID(outtmp.getOutgoingID(),(Long.parseLong(p[0])));
            OutgoingGoods.modifyQuantity(outtmp.getOutgoingID(),Integer.parseInt(outQuantT.getText()));
            if (outEmpC.getValue()!=null) {
                String selectedItem = outEmpC.getSelectionModel().getSelectedItem().toString();
                String[] s = selectedItem.split(" | ");
                OutgoingGoods.modifyEmployee(outtmp.getOutgoingID(), Long.parseLong(s[0]));
            }
            setOutgoingTable();
            setCurrentTable();
            hideData();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify Incoming Goods");
        }
    }
    public void deleteIncomingClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this Incoming Good?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.IncomingGoods.deleteRecord(intmp.getIncomingID());
                incomingT.getItems().remove(incomingT.getSelectionModel().getSelectedItem());
                intmp=null;
                hideData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete Incoming Good");
            }
        }
    }
    public void deleteOutgoingClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this Incoming Good?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Users.deleteRecord(outtmp.getOutgoingID());
                outgoingT.getItems().remove(outgoingT.getSelectionModel().getSelectedItem());
                outtmp=null;
                hideData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete Outgoing Good");
            }
        }
    }
    public void clearBox(){
        inQuantT.clear();
        trackNoT.clear();
        inDateT.setValue(null);
        outDateT.setValue(null);
        outQuantT.clear();
    }
    public void addIncomingData(){
        incomingV.setVisible(true);
        addIncomB.setVisible(false);
        modIncomB.setVisible(false);
        inAddB.setVisible(true);
        inModB.setVisible(false);
    }
    public void modIncomingData(){
        inModB.setVisible(true);
        addIncomB.setVisible(false);
        modIncomB.setVisible(false);
        incomingV.setVisible(true);
        inAddB.setVisible(false);

        if(!Global.privilege) {
            outDelete.setVisible(false);
            inDelete.setVisible(false);
        }
    }
    public void addOutgoingData(){
        outgoingV.setVisible(true);
        addOutB.setVisible(false);
        modOutB.setVisible(false);
        outAddB.setVisible(true);
        outModB.setVisible(false);
    }
    public void modOutgoingData(){
        outModB.setVisible(true);
        addOutB.setVisible(false);
        modOutB.setVisible(false);
        outgoingV.setVisible(true);
        outAddB.setVisible(false);
        if(!Global.privilege) {
            outDelete.setVisible(false);
            inDelete.setVisible(false);
        }
    }
    public void hideData(){
        addIncomB.setVisible(true);
        incomingV.setVisible(false);
        inAddB.setVisible(false);
        modIncomB.setVisible(false);
        inModB.setVisible(false);
        addOutB.setVisible(true);
        outgoingV.setVisible(false);
        outAddB.setVisible(false);
        modOutB.setVisible(false);
        outModB.setVisible(false);
        incomingT.getSelectionModel().clearSelection();
        outgoingT.getSelectionModel().clearSelection();
        clearBox();
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
