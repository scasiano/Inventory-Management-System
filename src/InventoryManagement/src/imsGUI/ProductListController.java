package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    ArrayList<Products> allProd = new ArrayList<>();
    ObservableList<Long> pID;
    ObservableList<String> prodN;
    VBox nameBox = new VBox();
    VBox idBox = new VBox();
    Label Name = new Label();
    int pIndex = -1;

    public void initialize() {
        setProductList();
        prodDetails();
        addProd.setVisible(Global.privilege);
        clearProdInfo();
    }
    public void setProductList() {
        try{allProd = Products.selectAll();}
        catch (SQLException e){Global.exceptionAlert(e, "get products");}
        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        Name.setText("Product Name:");
        Label ID = new Label();
        ID.setText("Product ID:");
        idBox.getChildren().add(ID);
        nameBox.getChildren().add(Name);
        try {
            for (Products products : allProd) {
                idList.add(products.getProductID());
                nameList.add(products.getName());
            }
            pID = FXCollections.observableArrayList(idList);
            prodN = FXCollections.observableArrayList(nameList);
            listName = new ListView<>(prodN);
            nameBox.getChildren().add(listName);
            listID = new ListView<>(pID);
            idBox.getChildren().add(listID);
            productsList.getChildren().addAll(nameBox, idBox);

        }
        catch (Exception e) {
            Global.exceptionAlert(e,"Set Product List");
        }
        startMod.setVisible(false);
    }
    public void prodDetails() {
        try {
            listID.setOnMouseClicked(event -> {
                int index = listID.getSelectionModel().getSelectedIndex();
                pIndex = index;
                listName.getSelectionModel().select(index);
                productName.setText(allProd.get(index).getName());
                productID.setText(String.valueOf(allProd.get(index).getProductID()));
                productMSRP.setText("$" + allProd.get(index).getMsrp());
                productPrice.setText("$" + allProd.get(index).getPrice());
                System.out.println(Global.privilege);
                if(Global.privilege)
                    startMod.setVisible(true);
            });
            listName.setOnMouseClicked(event -> {
                int index = listName.getSelectionModel().getSelectedIndex();
                pIndex = index;
                listID.getSelectionModel().select(index);
                productName.setText(allProd.get(index).getName());
                productID.setText(String.valueOf(allProd.get(index).getProductID()));
                productMSRP.setText("$" + allProd.get(index).getMsrp());
                productPrice.setText("$" + allProd.get(index).getPrice());
                System.out.println(Global.privilege);
                if(Global.privilege)
                    startMod.setVisible(true);
            });
        }
        catch (Exception e){
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
        try {
            try {
                if (productID.getText().length() > 0 && Long.parseLong(productID.getText()) >= 0)
                    temp.setProductID(Long.parseLong(productID.getText()));
                else {
                    Global.warningAlert("Incorrect ID", "Product ID needs to be greater than 0 and at most 9 digits long.");
                    productID.clear();
                    return;
                }
            }catch(NumberFormatException e){
                Global.warningAlert("Not a number", "Please enter a number for the Product ID");
                return;
            }
            if (productName.getText().length() > 0) temp.setName(productName.getText());
            else {
                Global.warningAlert("Incorrect Name", "Every product should have a name.");
                return;
            }
            if (productMSRP.getText().length() > 0 && Double.parseDouble(productMSRP.getText()) > 0) temp.setMsrp(Double.parseDouble(productMSRP.getText()));
            else {
                flag = false;
                Global.warningAlert("Incorrect MSRP", "Every product should have a MSRP.");
                productMSRP.clear();
                return;
            }
            if (productPrice.getText().length() > 0 && Double.parseDouble(productPrice.getText()) > 0) temp.setPrice(Double.parseDouble(productPrice.getText()));
            else {
                Global.informAlert("Incorrect Price", "Every product should have a Price.");
                productPrice.clear();
                return;
            }
            Products.addRecord(temp);
        }catch (MySQLIntegrityConstraintViolationException e) {
            Global.warningAlert("Product Id Exists", "Product ID already exists. Product Add canceled");
        }
        endProductEdit();
        clearProdList();
        initialize();
        clearProdInfo();
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
        try{
            ims.Products.modifyName(allProd.get(pIndex).getProductID(),productName.getText());
            if(productMSRP.getText().substring(0,1).equals("$"))
                ims.Products.modifyMsrp(allProd.get(pIndex).getProductID(),Double.parseDouble(productMSRP.getText().substring(1)));
            else
                ims.Products.modifyMsrp(allProd.get(pIndex).getProductID(),Double.parseDouble(productMSRP.getText()));
            if(productPrice.getText().substring(0,1).equals("$"))
                ims.Products.modifyPrice(allProd.get(pIndex).getProductID(),Double.parseDouble(productPrice.getText().substring(1)));
            else
                ims.Products.modifyPrice(allProd.get(pIndex).getProductID(),Double.parseDouble(productPrice.getText()));
            clearProdList();
            initialize();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify Products");
        }
        endProductEdit();
    }
    public void modProduct(){
        productName.setEditable(true);
        productMSRP.setEditable(true);
        productPrice.setEditable(true);
        addProd.setVisible(false);
        addProdLabel.setVisible(true);
        modBox.setVisible(true);
        startMod.setVisible(true);
    }
    public void delDBProduct() {
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
        }
        catch(Exception e) {
            Global.warningAlert("Delete Product", "This Product can't be deleted because it is being used somewhere else");
        }
    }
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

    @FXML
    private void openHomePage(ActionEvent event) {
        clearProdInfo();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) {
        clearProdInfo();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
