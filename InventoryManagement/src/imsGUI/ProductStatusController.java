package imsGUI;

import ims.CurrentStock;
import ims.IncomingGoods;
import ims.OutgoingGoods;
import ims.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductStatusController {
    @FXML
    TableView<IncomingGoods> incomingT;
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
    TableColumn inAdminAction;

    @FXML
    TableView outgoingT;
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
    TableColumn outAdminAction;

    @FXML
    TableView currentT;
    @FXML
    TableColumn productID;
    @FXML
    TableColumn curProductName;
    @FXML
    TableColumn curQuantity;
    @FXML
    TableColumn curAdminAction;

    ArrayList<IncomingGoods> allIn =new ArrayList<IncomingGoods>();
    ArrayList<OutgoingGoods> allOut =new ArrayList<OutgoingGoods>();
    ArrayList<CurrentStock> allCur=new ArrayList<CurrentStock>();
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

    @FXML
    private void openHomePage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
