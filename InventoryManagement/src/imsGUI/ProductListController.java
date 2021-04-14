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
    @FXML
    Button startMod;

    ListView<Long> listID;
    ListView<String> listName;
    ArrayList<Products> allProd =new ArrayList<Products>();
    ObservableList<Long> pID;
    ObservableList<String> prodN;
    VBox nameBox = new VBox();
    VBox idBox =new VBox();
    Label Name=new Label();
    int pIndex;

    public void initialize() throws SQLException{
        setProductList();
        prodDetails();
    }
    public void setProductList() throws SQLException {

        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> nameList= new ArrayList<>();
        Name.setText("Product Name:");
        Label ID=new Label();
        ID.setText("Product ID:");
        idBox.getChildren().add(ID);
        nameBox.getChildren().add(Name);
        try {
            allProd=Products.selectAll();
            for (int i = 0; i < allProd.size(); i++) {
                idList.add(allProd.get(i).getProductID());
                nameList.add(allProd.get(i).getName());
                }
            pID = FXCollections.observableArrayList(idList);
            prodN = FXCollections.observableArrayList(nameList);
            listName= new ListView<String>(prodN);
            nameBox.getChildren().add(listName);
            listID= new ListView<Long>(pID);
            idBox.getChildren().add(listID);
            productsList.getChildren().addAll(nameBox, idBox);
       }catch(Exception e){e.printStackTrace();}
    }
    public void prodDetails(){
        listID.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer index = listID.getSelectionModel().getSelectedIndex();
                pIndex=index;
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
                pIndex=index;
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
        addProdLabel.setVisible(true);
        addProd.setVisible(false);
        addProdBox.setVisible(true);
        startMod.setVisible(false);
    }
    public void addDBProduct(ActionEvent event) throws SQLException {
        Products temp = new Products(0,"",0,0);
        if(
             Double.valueOf(productID.getText())>0 && productName.getText().length()>0 && productMSRP.getText().length()>2 && productPrice.getText().length()>3){
             try{
                temp.setProductID(Long.valueOf(productID.getText()));
                temp.setName(productName.getText());
                temp.setMsrp(Double.valueOf(productMSRP.getText()));
                temp.setPrice(Double.valueOf(productPrice.getText()));
                Products.addRecord(temp);
                endProductEdit();
                listID.getItems().clear();
                listName.getItems().clear();
                idBox.getChildren().clear();
                nameBox.getChildren().clear();
                productsList.getChildren().clear();
                setProductList();
             }catch(Exception e){
                e.printStackTrace();
             }
        }
        else
        {
            Alert noProd=new Alert(Alert.AlertType.ERROR);
            noProd.setHeaderText("Input not valid");
            noProd.setContentText("Check your inputs for the product.\n\tProductID should be 8 numbers\n\t Product should have a name\n\t The Msrp and price need to be alt least 4 characters long.");
            noProd.showAndWait();
        }
    }
    public void endProductEdit(){
        productName.setEditable(false);
        productID.setEditable(false);
        productMSRP.setEditable(false);
        productPrice.setEditable(false);
        addProd.setVisible(true);
        addProdLabel.setVisible(false);
        addProdBox.setVisible(false);
        modBox.setVisible(false);
        startMod.setVisible(true);
    }
    public void modDBProduct(){
        allProd.get(pIndex).setName(productName.getText());
        allProd.get(pIndex).setMsrp(Double.valueOf(productMSRP.getText()));
        allProd.get(pIndex).setPrice(Double.valueOf(productPrice.getText()));
    }
    public void modProduct(){
        productName.setEditable(true);
        productMSRP.setEditable(true);
        productPrice.setEditable(true);
        addProd.setVisible(false);
        addProdLabel.setVisible(true);
        modBox.setVisible(true);
        startMod.setVisible(false);
    }
    public void delProduct(){
       /* Alert delete=new Alert(Alert.AlertType.CONFIRMATION);
        if(delete.)
        ims.Products.deleteRecord(allProd.get(pIndex).getProductID());*/
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
