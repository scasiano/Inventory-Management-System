package imsGUI;

import ims.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductListController{
public void initialize() throws SQLException{
    setProductList();
}
    @FXML
    HBox productsList;
    public void getProductInfo(){

    }
   public void setProductList() throws SQLException {
        ArrayList<Products> allProd =new ArrayList<Products>();
       ObservableList<Long> productID;
       ObservableList<String> productN;
       ArrayList<Long> idList = new ArrayList<>();
       ArrayList<String> nameList= new ArrayList<>();

        /*VBox id=new VBox();
        VBox name = new VBox();*/
       /*TextField pID = new TextField();
         TextField pName = new TextField();
         pID.setText(String.valueOf(allProd.get(i).getProductID()));
         pID.setEditable(false);
         pName.setText(allProd.get(i).getName());
         pName.setEditable(false);
         id.getChildren().add(pID);
         name.getChildren().add(pName);*/
             try {
               allProd=Products.selectAll();
                 for (int i = 0; i < allProd.size(); i++) {
                     idList.add(allProd.get(i).getProductID());
                     nameList.add(allProd.get(i).getName());
                 }
                 productID = FXCollections.observableArrayList(idList);
                 productN = FXCollections.observableArrayList(nameList);
                 ListView<Long> listID = new ListView<Long>(productID);
                 ListView<String> listName = new ListView<String>(productN);
                 productsList.getChildren().addAll(listID,listName);
             }catch(Exception e){e.printStackTrace();}
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
