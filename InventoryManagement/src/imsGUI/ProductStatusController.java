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
    Button inAddButton;
    @FXML
    Button inModButton;
    @FXML
    VBox incomingV;
    @FXML
    HBox inAddHBox;
    @FXML
    HBox inModHBox;
    @FXML
    TableColumn<IncomingGoods, Long> inID;
    @FXML
    TableColumn<IncomingGoods, Long> inProductID;
    @FXML
    TableColumn<IncomingGoods, Date> dateIn;
    @FXML
    TableColumn<IncomingGoods, String> trackingID;
    @FXML
    TableColumn<IncomingGoods, Integer> inQuantity;
    @FXML
    TableColumn<IncomingGoods, Long> inEmpID;
    @FXML
    DatePicker inDatePick;
    @FXML
    TextField trackNoT;
    @FXML
    TextField inQuantTBox;
    @FXML
    ComboBox<String> inEmpC;
    @FXML
    Button inDelete;
    @FXML
    ComboBox<String> inProductsC;

    @FXML
    TableView<OutgoingGoods> outgoingT;
    @FXML
    Button outAddButton;
    @FXML
    Button outModButton;
    @FXML
    VBox outgoingV;
    @FXML
    HBox outAddHBox;
    @FXML
    HBox outModHBox;
    @FXML
    TableColumn<OutgoingGoods, Long> outID;
    @FXML
    TableColumn<OutgoingGoods, Long> outProductID;
    @FXML
    TableColumn<OutgoingGoods, Date> dateGo;
    @FXML
    TableColumn<OutgoingGoods, Integer> outQuantity;
    @FXML
    TableColumn<OutgoingGoods, Long> outEmpID;
    @FXML
    DatePicker outDatePick;
    @FXML
    TextField outQuantTBox;
    @FXML
    ComboBox<String> outEmpC;
    @FXML
    Button outDelete;
    @FXML
    ComboBox<String> outProductsC;


    @FXML
    TableView<CurrentStock> currentT;
    @FXML
    TableColumn<CurrentStock, Long> productID;
    @FXML
    TableColumn curProductName;
    @FXML
    TableColumn<CurrentStock, Integer> curQuantity;


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
        setInProdCombo();
        setOutProdCombo();
        setEmpCombo();
        setCurrentTable();
        getSelectedInfo();

    }
    public void setIncomingTable(){
        try {
            inID.setCellValueFactory(new PropertyValueFactory<>("incomingID"));
            inProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            dateIn.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
            trackingID.setCellValueFactory(new PropertyValueFactory<>("trackingNo"));
            inQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            inEmpID.setCellValueFactory(new PropertyValueFactory<>("employeeNo"));
            allIn = IncomingGoods.selectAll();
            ObservableList<IncomingGoods> incoming = FXCollections.observableArrayList(allIn);
            incomingT.setItems(incoming);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set Incoming Table");
        }
    }
    public void setOutgoingTable(){
        try {
            outID.setCellValueFactory(new PropertyValueFactory<>("outgoingID"));
            outProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            dateGo.setCellValueFactory(new PropertyValueFactory<>("dateGo"));
            outQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            outEmpID.setCellValueFactory(new PropertyValueFactory<>("employeeNo"));
            allOut = OutgoingGoods.selectAll();
            ObservableList<OutgoingGoods> outgoing = FXCollections.observableArrayList(allOut);
            outgoingT.setItems(outgoing);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set Outgoing Table");
        }
    }
    public void setCurrentTable(){
        try {
            productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            //curProductName.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
            curQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            allCur = CurrentStock.selectAll();
            ObservableList<CurrentStock> current = FXCollections.observableArrayList(allCur);
            currentT.setItems(current);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set Current Table");
        }
    }
    public void getSelectedInfo(){
        try{
            incomingT.setOnMouseClicked(event -> {
                intmp = incomingT.getSelectionModel().getSelectedItem();
                int i = incomingT.getSelectionModel().getSelectedIndex();
                inModButton.setVisible(true);
                inProductsC.setValue(intmp.getProductID() +" | "+pNames.get(i));
                inDatePick.setValue(intmp.getDateIn().toLocalDate());
                trackNoT.setText(intmp.getTrackingNo());
                inQuantTBox.setText(Integer.toString(intmp.getQuantity()));
                inEmpC.setValue(intmp.getEmployeeNo() +" | "+eNames.get(i));
            });
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Incoming Mouse Click");
        }
        try{
            outgoingT.setOnMouseClicked(event -> {
                outtmp = outgoingT.getSelectionModel().getSelectedItem();
                int i = outgoingT.getSelectionModel().getSelectedIndex();
                outModButton.setVisible(true);
                outProductsC.setValue(outtmp.getProductID()+" | "+pNames.get(i));
                outDatePick.setValue(outtmp.getDateGo().toLocalDate());
                outQuantTBox.setText(String.valueOf(outtmp.getQuantity()));
                outEmpC.setValue(outtmp.getEmployeeNo()+" | " + eNames.get(i));

            });
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Outgoing Mouse Click");
        }

    }
    public void setInProdCombo(){
        try{
            inProductsC.getItems().clear();
            allProd= Products.selectAll();
            pNames = Products.selectName();
            for (int i=0; i<allProd.size();i++) {
                    String s = allProd.get(i).getProductID() + " | " + allProd.get(i).getName();
                    inProductsC.getItems().add(s);
            }
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Product Combo");
        }
    }
    public void setOutProdCombo(){
        try{
            outProductsC.getItems().clear();
            allProd= Products.selectAll();
            pNames = Products.selectName();
            for (int i=0; i<allProd.size();i++) {
                if (CurrentStock.selectQuantityByProductID(allProd.get(i).getProductID()) != 0) {
                    String s = allProd.get(i).getProductID() + " | " + allProd.get(i).getName();
                    outProductsC.getItems().add(s);
                }
            }
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Product Combo");
        }
    }
    public void setEmpCombo(){
        try{
            inEmpC.getItems().clear();
            outEmpC.getItems().clear();
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
    public void saveIncomingClicked() {
        boolean hasTrack = false;
        boolean hasEmp = false;
        IncomingGoods tmp= new IncomingGoods(0, null, "", 0);
        try{
            try{
                if (inProductsC.getValue() != null){
                    String selectedItem = inProductsC.getSelectionModel().getSelectedItem();
                    String[] s = selectedItem.split(" | ");
                    tmp.setProductID(Long.parseLong(s[0]));
                }
            }
            catch(Exception e){Global.exceptionAlert(e,"Incoming  Products Combo");}
            if (inDatePick.getValue()!=null)
                tmp.setDateIn(java.sql.Date.valueOf(inDatePick.getValue()));
            else{
                Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyyy-MM-dd");
                inDatePick.setValue(null);
                return;
            }
            if (trackNoT.getText().length() > 0){
                tmp.setTrackingNo(trackNoT.getText());
                hasTrack = true;
            }
            if (inQuantTBox.getText().length() > 0)
                tmp.setQuantity(Integer.parseInt(inQuantTBox.getText()));
            else{
                Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                inQuantTBox.clear();
                return;
            }
            if(inEmpC.getValue()!=null){
                String selectedItem = inEmpC.getSelectionModel().getSelectedItem();
                String[] s = selectedItem.split(" | ");
                tmp.setEmployeeNo(Long.parseLong(s[0]));
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
            Global.warningAlert("Incoming Id Exists", "Incoming ID already exists. Incoming Order add canceled");
            Global.exceptionAlert(e, "add incoming");
        }
        catch(Exception p){
            Global.exceptionAlert(p,"Save Incoming");
        }
    }

    public void saveOutgoingClicked() {
        boolean hasEmp = false;
        OutgoingGoods tmp = new OutgoingGoods(0, null, 0 );
        try {
            try{
                if (outProductsC.getValue()!=null) {
                    String selectedItem = outProductsC.getSelectionModel().getSelectedItem();
                    String[] s = selectedItem.split(" | ");
                    tmp.setProductID(Long.parseLong(s[0]));
                }
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Out Products Combo");
            }
            if (outDatePick.getValue()!=null)
                tmp.setDateGo(java.sql.Date.valueOf(outDatePick.getValue()));
            else {
                Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyyy-MM-dd");
                outDatePick.setValue(null);
                return;
            }
            try {
                if (outQuantTBox.getText().length() > 0)
                    tmp.setQuantity(Integer.parseInt(outQuantTBox.getText()));
                else {
                    Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                    outQuantTBox.clear();
                    return;
                }
            }catch(Exception e){
                Global.warningAlert("Quantity","There aren't enough products to send out.");
            }
            if (outEmpC.getValue()!=null) {
                String selectedItem = outEmpC.getSelectionModel().getSelectedItem().toString();
                String[] s = selectedItem.split(" | ");
                tmp.setEmployeeNo(Long.parseLong(s[0]));
                hasEmp = true;
            }
            if (hasEmp)
                OutgoingGoods.addRecordEmp(tmp);
            else
                OutgoingGoods.addRecord(tmp);
            setOutgoingTable();
            setCurrentTable();
            hideData();
        }
        catch(MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Outgoing Id Exists", "Outgoing ID already exists. Outgoing Order add canceled");
        }
        catch(Exception p){
            Global.exceptionAlert(p, "Save Outgoing");
        }
    }
    public void modIncom(){
        try{
            String selected = inProductsC.getSelectionModel().getSelectedItem();
            String[] p = selected.split(" | ");
            IncomingGoods.modifyProductID(intmp.getIncomingID(),(Long.parseLong(p[0])));
            IncomingGoods.modifyDateIn(intmp.getIncomingID(),java.sql.Date.valueOf(inDatePick.getValue()));
            IncomingGoods.modifyQuantity(intmp.getIncomingID(),Integer.parseInt(inQuantTBox.getText()));
            IncomingGoods.modifyTrackingNo(intmp.getIncomingID(),trackNoT.getText());
            if(inEmpC.getValue()!=null){
                String selectedItem = inEmpC.getSelectionModel().getSelectedItem();
                String[] s = selectedItem.split(" | ");
                IncomingGoods.modifyEmployeeNo(intmp.getIncomingID(),Long.parseLong(s[0]));
            }
            setIncomingTable();
            setCurrentTable();
            hideData();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify OutGoing Goods");
        }
    }
    public void modOut(){
        try{
            String selected = outProductsC.getSelectionModel().getSelectedItem().toString();
            String[] p = selected.split(" | ");
            OutgoingGoods.modifyDateGo(outtmp.getOutgoingID(),java.sql.Date.valueOf(outDatePick.getValue()));
            OutgoingGoods.modifyProductID(outtmp.getOutgoingID(),(Long.parseLong(p[0])));
            try{
                OutgoingGoods.modifyQuantity(outtmp.getOutgoingID(),Integer.parseInt(outQuantTBox.getText()));
            }catch(Exception e){
                Global.warningAlert("Quantity","There aren't enough products to send out.");
            }
            if (outEmpC.getValue()!=null) {
                String selectedItem = outEmpC.getSelectionModel().getSelectedItem();
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
        inQuantTBox.clear();
        trackNoT.clear();
        inDatePick.setValue(null);
        outDatePick.setValue(null);
        outQuantTBox.clear();
    }
    public void addIncomingData(){
        incomingV.setVisible(true);
        inAddButton.setVisible(false);
        inModButton.setVisible(false);
        inAddHBox.setVisible(true);
        inModHBox.setVisible(false);
        inProductsC.setValue(null);
        inEmpC.setValue(null);
    }
    public void addOutgoingData(){
        outgoingV.setVisible(true);
        outAddButton.setVisible(false);
        outModButton.setVisible(false);
        outAddHBox.setVisible(true);
        outModHBox.setVisible(false);
        outProductsC.setValue(null);
        outEmpC.setValue(null);
    }
    public void modIncomingData(){
        inModHBox.setVisible(true);
        inAddButton.setVisible(false);
        inModButton.setVisible(false);
        incomingV.setVisible(true);
        inAddHBox.setVisible(false);

        if(!Global.privilege) {
            outDelete.setVisible(false);
            inDelete.setVisible(false);
        }
    }
    public void modOutgoingData(){
        outModHBox.setVisible(true);
        outAddButton.setVisible(false);
        outModButton.setVisible(false);
        outgoingV.setVisible(true);
        outAddHBox.setVisible(false);
        if(!Global.privilege) {
            outDelete.setVisible(false);
            inDelete.setVisible(false);
        }
    }
    public void hideData(){
        inAddButton.setVisible(true);
        incomingV.setVisible(false);
        inAddHBox.setVisible(false);
        inModButton.setVisible(false);
        inModHBox.setVisible(false);
        outAddButton.setVisible(true);
        outgoingV.setVisible(false);
        outAddHBox.setVisible(false);
        outModButton.setVisible(false);
        outModHBox.setVisible(false);
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
