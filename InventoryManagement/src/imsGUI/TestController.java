package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestController {
    @FXML
    private void openAdminU(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminUserList.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
    @FXML
    private void openBudget(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Budget.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
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
    @FXML
    private void openOrders(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Orders.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openProduct(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductList.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openProductStatus(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductStatus.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openProfiles(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

