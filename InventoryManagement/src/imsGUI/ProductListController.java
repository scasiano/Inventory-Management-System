package imsGUI;

import ims.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductListController{

    @FXML
    HBox productsList;
    @FXML
    TextField productName;
    @FXML
    TextField productID;
    @FXML
    TextField productMSRP;
    @FXML
    TextField productPrice;
    /*@FXML
    TextField productDescription;*/
    @FXML
    Button addProd;
    @FXML
    Label addProdLabel;
    @FXML
    HBox addProdBox;
    @FXML
    HBox modBox;

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
                productMSRP.setText("$"+String.valueOf(allProd.get(index).getMsrp()));
                productPrice.setText("$"+String.valueOf(allProd.get(index).getPrice()));
            }
        });
        listName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer index = listName.getSelectionModel().getSelectedIndex();
                listID.getSelectionModel().select(index);
                productName.setText(allProd.get(index).getName());
                productID.setText(String.valueOf(allProd.get(index).getProductID()));
                productMSRP.setText("$"+String.valueOf(allProd.get(index).getMsrp()));
                productPrice.setText("$"+String.valueOf(allProd.get(index).getPrice()));
            }
        });
    }
    public void startProduct(ActionEvent event){
        productName.setEditable(true);
        productID.setEditable(true);
        productMSRP.setEditable(true);
        productPrice.setEditable(true);
        addProd.setVisible(false);
        addProdBox.setVisible(true);
    }
    public void addDBProduct(ActionEvent event) throws SQLException {
        Products temp = new Products(0,"",0,0);
        if(productID.getText().length()==8 && productName.getText().length()>0 && productMSRP.getText().length()>2 && productPrice.getText().length()>3){
            try{ temp.setProductID(Long.valueOf(productID.getText()));
            temp.setName(productName.getText());
            temp.setMsrp(Double.valueOf(productMSRP.getText()));
            temp.setPrice(Double.valueOf(productPrice.getText()));
            Products.addRecord(temp);
            addProductEnd();
            listID.getItems().clear();
            listName.getItems().clear();
            setProductList();
            }catch(Exception e){e.printStackTrace();}
        }
        else
        {
            Alert noProd=new Alert(Alert.AlertType.ERROR);
            noProd.setHeaderText("Input not valid");
            noProd.setContentText("Check your inputs for the product.\n\tProductID should be 8 numbers\n\t Product should have a name\n\t The Msrp and price need to be alt least 4 characters long.");
            noProd.showAndWait();
        }
    }
    public void addProductEnd(){
        productName.setEditable(false);
        productID.setEditable(false);
        productMSRP.setEditable(false);
        productPrice.setEditable(false);
        addProd.setVisible(true);
        addProdLabel.setVisible(false);
        addProdBox.setVisible(false);
    }
    public void modProduct(){
        productName.setEditable(true);
        productMSRP.setEditable(true);
        productPrice.setEditable(true);
        addProd.setVisible(false);
        addProdLabel.setVisible(true);
        modBox.setVisible(true);
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
