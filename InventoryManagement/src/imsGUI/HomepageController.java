package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomepageController {
    public void initialize(){}

  //  public void logout(){    }
    @FXML
    private void openAdminU(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getAdminUserListScene());
        stage.show();
    }
    @FXML
    private void openBudget(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getBudgetScene());
        stage.show();
    }
    @FXML

    private void openLogin(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
    @FXML
    private void openOrders(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getOrdersScene());
        stage.show();
    }
    @FXML
    private void openProduct(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //stage.setTitle("Hello World");
        stage.setScene(ImsGui.global.getProductListScene());
        stage.show();
    }
    @FXML
    private void openProductStatus(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getProductStatusScene());
        stage.show();
    }
    @FXML
    private void openProfiles(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getProfilesScene());
        stage.show();
    }
}
