package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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


public class ProductListController {

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
    ArrayList<Products> allProd = new ArrayList<Products>();
    ObservableList<Long> pID;
    ObservableList<String> prodN;
    VBox nameBox = new VBox();
    VBox idBox = new VBox();
    Label Name = new Label();
    int pIndex = -1;

    public void initialize() throws SQLException {
        setProductList();
        prodDetails();
        if(Global.privilege){
            addProd.setVisible(true);
        }
        else
            addProd.setVisible(false);
    }

    public void setProductList() {

        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        Name.setText("Product Name:");
        Label ID = new Label();
        ID.setText("Product ID:");
        idBox.getChildren().add(ID);
        nameBox.getChildren().add(Name);
        try {
            allProd = Products.selectAll();
            for (int i = 0; i < allProd.size(); i++) {
                idList.add(allProd.get(i).getProductID());
                nameList.add(allProd.get(i).getName());
            }
            pID = FXCollections.observableArrayList(idList);
            prodN = FXCollections.observableArrayList(nameList);
            listName = new ListView<String>(prodN);
            nameBox.getChildren().add(listName);
            listID = new ListView<Long>(pID);
            idBox.getChildren().add(listID);
            productsList.getChildren().addAll(nameBox, idBox);
        } catch (Exception e) {
            Global.exceptionAlert(e,"Set Product List");
        }
        startMod.setVisible(false);
    }

    public void prodDetails() {
        try {
            listID.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Integer index = listID.getSelectionModel().getSelectedIndex();
                    pIndex = index;
                    listName.getSelectionModel().select(index);
                    productName.setText(allProd.get(index).getName());
                    productID.setText(String.valueOf(allProd.get(index).getProductID()));
                    productMSRP.setText("$" + String.valueOf(allProd.get(index).getMsrp()));
                    productPrice.setText("$" + String.valueOf(allProd.get(index).getPrice()));
                    if(Global.privilege)
                        startMod.setVisible(true);
                }
            });
            listName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Integer index = listName.getSelectionModel().getSelectedIndex();
                    pIndex = index;
                    listID.getSelectionModel().select(index);
                    productName.setText(allProd.get(index).getName());
                    productID.setText(String.valueOf(allProd.get(index).getProductID()));
                    productMSRP.setText("$" + String.valueOf(allProd.get(index).getMsrp()));
                    productPrice.setText("$" + String.valueOf(allProd.get(index).getPrice()));
                    if(Global.privilege)
                        startMod.setVisible(true);
                }
            });
        }catch (Exception e){
            Global.exceptionAlert(e,"Show Product Details");
        }

    }

    public void startProduct(ActionEvent event) {
        productName.setEditable(true);
        productID.setEditable(true);
        productMSRP.setEditable(true);
        productPrice.setEditable(true);
        addProdLabel.setVisible(true);
        addProd.setVisible(false);
        addProdBox.setVisible(true);
        startMod.setVisible(false);
        clearProdInfo();
    }

    public void addDBProduct(ActionEvent event) throws SQLException {
        Products temp = new Products(0, "", 0, 0);
        boolean flag = false;
        //productMSRP.getText().length()>2 && productPrice.getText().length()>3){
        try {
            while (!flag) {
                if (productID.getText().length() > 0 && Long.valueOf(productID.getText()) >= 0) {
                    flag = true;
                    temp.setProductID(Long.valueOf(productID.getText()));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect ID", "Product ID needs to be greater than 0 and at most 9 digits long.");
                    productID.clear();
                }
                if (productName.getText().length() > 0) {
                    flag = true;
                    temp.setName(productName.getText());
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect Name", "Every product should have a name.");
                }
                if (productMSRP.getText().length() > 0 && Double.valueOf(productMSRP.getText()) > 0) {
                    flag = true;
                    temp.setMsrp(Double.valueOf(productMSRP.getText()));
                } else {
                    flag = false;
                    Global.warningAlert("Incorrect MSRP", "Every product should have a MSRP.");
                    productMSRP.clear();
                }
                if (productPrice.getText().length() > 0 && Double.valueOf(productPrice.getText()) > 0) {
                    flag = true;
                    temp.setPrice(Double.valueOf(productPrice.getText()));
                } else {
                    flag = false;
                    Global.informAlert("Incorrect Price", "Every product should have a Price.");
                    productPrice.clear();
                }
            }
            Products.addRecord(temp);
        } catch (MySQLIntegrityConstraintViolationException e) {
            Global.warningAlert("Product Id Exists", "Product ID already exists. Product Add canceled");
        }
        endProductEdit();
        clearProdList();
        initialize();
        clearProdInfo();


    }


        /*else
        {
            Alert noProd=new Alert(Alert.AlertType.ERROR);
            noProd.setHeaderText("Input not valid");
            noProd.setContentText("Check your inputs for the product.\n\tProductID should be 8 numbers\n\t Product should have a name\n\t The Msrp and price need to be alt least 4 characters long.");
            noProd.showAndWait();
        }*/
    public void clearProdList(){
        listID.getItems().clear();
        listName.getItems().clear();
        idBox.getChildren().clear();
        nameBox.getChildren().clear();
        productsList.getChildren().clear();
    }
    public void clearProdInfo(){
        productID.clear();
        productName.clear();
        productMSRP.clear();
        productPrice.clear();
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
    public void modDBProduct() throws SQLException {
        try{
            ims.Products.modifyName(allProd.get(pIndex).getProductID(),productName.getText());
            ims.Products.modifyMsrp(allProd.get(pIndex).getProductID(),Double.valueOf(productMSRP.getText().substring(1)));
            ims.Products.modifyPrice(allProd.get(pIndex).getProductID(),Double.valueOf(productPrice.getText().substring(1)));
        }catch(Exception e){
            Global.exceptionAlert(e,"Modify Products");
        }
        initialize();
        endProductEdit();
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
    public void delDBProduct() throws SQLException {
        Alert delete=new Alert(Alert.AlertType.CONFIRMATION);
        delete.setHeaderText("Delete Product");
        delete.setContentText("Are you Sure you want to Delete?");
        try {
            if (delete.showAndWait().get() == ButtonType.OK) {
                ims.Products.deleteRecord(allProd.get(pIndex).getProductID());
                endProductEdit();
                clearProdList();
                initialize();
                clearProdInfo();
            }
        }catch(Exception e){Global.exceptionAlert(e,"Delete Product");}
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
