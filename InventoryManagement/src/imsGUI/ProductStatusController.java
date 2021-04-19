package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.CurrentStock;
import ims.IncomingGoods;
import ims.OutgoingGoods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
    TextField inIDT;
    @FXML
    TextField inPIDT;
    @FXML
    DatePicker inDateT;
    @FXML
    TextField trackNoT;
    @FXML
    TextField inQuantT;
    @FXML
    TextField inEmpT;

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
    TextField outIDT;
    @FXML
    TextField outPIDT;
    @FXML
    DatePicker outDateT;
    @FXML
    TextField outQuantT;
    @FXML
    TextField outEmpT;


    @FXML
    TableView currentT;
    @FXML
    TableColumn productID;
    @FXML
    TableColumn curProductName;
    @FXML
    TableColumn curQuantity;


    ArrayList<IncomingGoods> allIn =new ArrayList<IncomingGoods>();
    ArrayList<OutgoingGoods> allOut =new ArrayList<OutgoingGoods>();
    ArrayList<CurrentStock> allCur=new ArrayList<CurrentStock>();
    IncomingGoods intmp;
    OutgoingGoods outtmp;
    public void initialize(){
       setIncomingTable();
       setOutgoingTable();
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
        }catch(SQLException e){
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
        }catch(SQLException e){
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
        }catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    public void getSelectedInfo(){
        try{
            incomingT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                intmp = incomingT.getSelectionModel().getSelectedItem();
                modIncomB.setVisible(true);
                inIDT.setText(String.valueOf(intmp.getIncomingID()));
                inPIDT.setText(String.valueOf(intmp.getProductID()));
                inDateT.setValue(intmp.getDateIn().toLocalDate());
                trackNoT.setText(String.valueOf(intmp.getTrackingNo()));
                inQuantT.setText(String.valueOf(intmp.getQuantity()));
                inEmpT.setText(String.valueOf(intmp.getEmployeeNo()));
            }
            });
            }catch(Exception e){
            Global.exceptionAlert(e,"Incoming Mouse Click");
        }
        try{
        outgoingT.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                outtmp= outgoingT.getSelectionModel().getSelectedItem();
                modOutB.setVisible(true);
                outIDT.setText(String.valueOf(outtmp.getOutgoingID()));
                outDateT.setValue(outtmp.getDateGo().toLocalDate());
                outEmpT.setText(String.valueOf(outtmp.getEmployeeNo()));
                outQuantT.setText(String.valueOf(outtmp.getQuantity()));
                //outPIDT.setText(String.valueOf(outtmp.getProductID()));
            }
        });}catch(Exception e){
            Global.exceptionAlert(e,"Outgoing Mouse Click");
        }
    }
    public void saveIncomingClicked(ActionEvent event) throws SQLException {
        int newU=1;
        IncomingGoods tmp = new IncomingGoods(0, 0, null, "",0,0);
        boolean flag = false;
        try{
            while(!flag){
                if (inIDT.getText().length() > 0 && Long.valueOf(inIDT.getText().length()) >= 0){
                    flag = true;
                    tmp.setIncomingID((Long.parseLong(inIDT.getText())));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Incoming ID", "Needs to be greater than 0 and have 9 or less digits.");
                    inIDT.clear();
                }
                if (inPIDT.getText().length() > 0 && Long.valueOf(inPIDT.getText().length()) >= 0){
                    flag = true;
                    tmp.setProductID(Long.parseLong(inPIDT.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Product ID", "Needs to be greater than 0 and have 9 or less digits.");
                    inPIDT.clear();
                }
                if (inDateT.getValue()!=null){
                    flag = true;
                    tmp.setDateIn(java.sql.Date.valueOf(inDateT.getValue()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyy-MM-dd");
                    inDateT.setValue(null);
                }
                if (trackNoT.getText().length() > 0){
                    flag = true;
                    tmp.setTrackingNo(trackNoT.getText());
                    newU --;
                }
                if (inQuantT.getText().length() > 0){
                    flag = true;
                    tmp.setQuantity(Integer.valueOf(inQuantT.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                    inQuantT.clear();
                }
                if(inEmpT.getText().length() > 0){
                    tmp.setProductID(Long.valueOf(inEmpT.getText()));
                    newU+=2;
                }
            }
            if(newU==1) {
                IncomingGoods.addRecord(tmp);
                incomingT.getItems().add(tmp);
            }
            if(newU==0)
            {
                IncomingGoods.addRecordTrack(tmp);
                incomingT.getItems().add(tmp);
            }
            if(newU==2) {
                IncomingGoods.addRecordTrackEmp(tmp);
                incomingT.getItems().add(tmp);
            }
            if(newU==3) {
                IncomingGoods.addRecordEmp(tmp);
                incomingT.getItems().add(tmp);

            }
            hideData();
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Incoming Id Exists", "Incoming ID already exists. Incoming Good add canceled");
        } catch(Exception p){
            Global.exceptionAlert(p,"Save Incoming");
        }
    }

    public void saveOutgoingClicked(ActionEvent event) throws SQLException {
        int newU = 1;
        OutgoingGoods tmp = new OutgoingGoods(0, 0, null, 0, 0);
        boolean flag = false;
        try {
            while (!flag) {
                if (outIDT.getText().length() > 0 && Long.valueOf(outIDT.getText().length()) >= 0) {
                    flag = true;
                    tmp.setOutgoingID((Long.parseLong(outIDT.getText())));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect Outgoing ID", "Needs to be greater than 0 and have 9 or less digits.");
                    outIDT.clear();
                }
                if (outPIDT.getText().length() > 0 && Long.valueOf(outPIDT.getText().length()) >= 0) {
                    flag = true;
                    tmp.setProductID(Long.parseLong(outPIDT.getText()));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect Product ID", "Needs to be greater than 0 and have 9 or less digits.");
                    outIDT.clear();
                }
                if (outDateT.getValue()!=null) {
                    flag = true;
                    tmp.setDateGo(java.sql.Date.valueOf(outDateT.getValue()));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect Date", "Date needs to be in the format of yyy-MM-dd");
                    outDateT.setValue(null);
                }
                if (outQuantT.getText().length() > 0) {
                    flag = true;
                    tmp.setQuantity(Integer.valueOf(outQuantT.getText()));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect Quantity", "Every Incoming Shipment needs a Quantity");
                    outQuantT.clear();
                }
                if (outEmpT.getText().length() > 0) {
                    tmp.setEmployeeNo(Long.valueOf(inEmpT.getText()));
                    newU++;
                }
            }
            if (newU == 1) {
                OutgoingGoods.addRecord(tmp);
                outgoingT.getItems().add(tmp);
            }
            if (newU == 2) {
                OutgoingGoods.addRecordEmp(tmp);
                outgoingT.getItems().add(tmp);
            }
            hideData();
        }catch(MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Outgoing Id Exists", "Outgoing ID already exists. Incoming Good add canceled");
        } catch(Exception p){
            Global.exceptionAlert(p, "Save Outgoing");
        }
    }
    public void modIncom(){
        try{
            IncomingGoods.modifyDateIn(intmp.getIncomingID(),java.sql.Date.valueOf(inDateT.getValue()));
            if(inEmpT.getText().length() > 0){
                IncomingGoods.modifyEmployeeNo(intmp.getIncomingID(),Long.valueOf(inEmpT.getText()));
            }
            IncomingGoods.modifyProductID(intmp.getIncomingID(),(Long.valueOf(inPIDT.getText())));
            IncomingGoods.modifyQuantity(intmp.getIncomingID(),Integer.parseInt(inQuantT.getText()));
            if (trackNoT.getText().length() > 0){
                IncomingGoods.modifyTrackingNo(intmp.getIncomingID(),trackNoT.getText());
            }
            setIncomingTable();
            hideData();
        }catch(Exception e){
            Global.exceptionAlert(e,"Modify Incoming Goods");
        }
    }
    public void modOut(){
        try{
            OutgoingGoods.modifyDateGo(outtmp.getOutgoingID(),java.sql.Date.valueOf(outDateT.getValue()));
            OutgoingGoods.modifyProductID(outtmp.getOutgoingID(),(Long.parseLong(outPIDT.getText())));
            OutgoingGoods.modifyQuantity(outtmp.getOutgoingID(),Integer.parseInt(outQuantT.getText()));
            if (outEmpT.getText().length() > 0)
                OutgoingGoods.modifyEmployee(outtmp.getOutgoingID(),Long.valueOf(inEmpT.getText()));
            setOutgoingTable();
            hideData();
        }catch(Exception e){
            Global.exceptionAlert(e,"Modify Incoming Goods");
        }
    }
    public void deleteIncomingClicked() throws SQLException {
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
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete Incoming Good");
            }
        }
    }
    public void deleteOutgoingClicked() throws SQLException {
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
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete Outgoing Good");
            }
        }
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
        inIDT.setEditable(false);
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
        outIDT.setEditable(false);
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
