package imsGUI;

import ims.Products;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductListController{

    @FXML
    HBox productsList;
    @FXML
    Label productName;
    @FXML
    Label productID;
    @FXML
    Label productMSRP;
    @FXML
    Label productPrice;
    @FXML
    Label productDescription;

    ListView<Long> listID;
    ListView<String> listName;
    ArrayList<Products> allProd =new ArrayList<Products>();

    public void initialize() throws SQLException{
        setProductList();
        prodDetails();

    }

    public void setProductList() throws SQLException {

       ObservableList<Long> productID;
       ObservableList<String> productN;
       ArrayList<Long> idList = new ArrayList<>();
       ArrayList<String> nameList= new ArrayList<>();


        VBox name = new VBox();
        Label Name=new Label();
        Name.setText("Product Name:");
        VBox id=new VBox();
        Label ID=new Label();
        ID.setText("Product ID:");
        id.getChildren().add(ID);
        name.getChildren().add(Name);
        try {
            allProd=Products.selectAll();
            for (int i = 0; i < allProd.size(); i++) {
                idList.add(allProd.get(i).getProductID());
                nameList.add(allProd.get(i).getName());
                }
            productID = FXCollections.observableArrayList(idList);
            productN = FXCollections.observableArrayList(nameList);
            listName= new ListView<String>(productN);
            name.getChildren().add(listName);
            listID= new ListView<Long>(productID);
            id.getChildren().add(listID);
            productsList.getChildren().addAll(name,id);
        }catch(Exception e){e.printStackTrace();}
    }
    public void prodDetails(){
        listID.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer index = listID.getSelectionModel().getSelectedIndex();
                listName.getSelectionModel().select(index);
                productName.setText(allProd.get(index).getName());
                productID.setText(String.valueOf(allProd.get(index).getProductID()));
                productMSRP.setText(String.valueOf(allProd.get(index).getMsrp()));
                productPrice.setText(String.valueOf(allProd.get(index).getPrice()));
            }
        });
        listName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer index = listName.getSelectionModel().getSelectedIndex();
                listID.getSelectionModel().select(index);
                productName.setText(allProd.get(index).getName());
                productID.setText(String.valueOf(allProd.get(index).getProductID()));
                productMSRP.setText(String.valueOf(allProd.get(index).getMsrp()));
                productPrice.setText(String.valueOf(allProd.get(index).getPrice()));
            }
        });
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
