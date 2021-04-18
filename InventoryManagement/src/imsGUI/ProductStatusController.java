package imsGUI;

import ims.CurrentStock;
import ims.IncomingGoods;
import ims.OutgoingGoods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    TextField inIDT;
    @FXML
    TextField inPIDT;
    @FXML
    TextField inDateT;
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
    TextField pIDT;
    @FXML
    TextField outDateT;
    @FXML
    TextField outQuantT;
    @FXML
    TextField outEmpIDT;


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
        incomingT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                intmp = incomingT.getSelectionModel().getSelectedItem();
                modIncomB.setVisible(true);
            }
        });
        outgoingT.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                outtmp= outgoingT.getSelectionModel().getSelectedItem();
                modOutB.setVisible(true);
            }
        });
    }
    public void addIncomingData(){
        incomingV.setVisible(true);
        inAddB.setVisible(true);
        modIncomB.setVisible(false);
        inModB.setVisible(false);
    }
    public void modIncomingData(){
        inModB.setVisible(true);
        incomingV.setVisible(true);
        inAddB.setVisible(false);
        addIncomB.setVisible(false);
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
