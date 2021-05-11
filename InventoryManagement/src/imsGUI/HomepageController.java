package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomepageController {
    @FXML
    Button profilesB;
    @FXML
    Button prodListB;
    @FXML
    Button prodStatB;
    @FXML
    Button ordersB;
    @FXML
    Button budgetB;
    @FXML
    Button adminB;

    public void initialize(){
        allowAdmin();
        allowEmp();
    }
    private void allowAdmin(){
        adminB.setVisible(Global.privilege);
    }
    private void allowEmp(){budgetB.setVisible(Global.averagePrivelege); prodStatB.setVisible(Global.averagePrivelege);}
    @FXML
    private void openAdminU(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getAdminUserListScene());
        ImsGui.global.getAdminUserListController().initialize();
        stage.show();
    }
    @FXML
    private void openBudget(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getBudgetScene());
        ImsGui.global.getBudgetController().initialize();
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
        ImsGui.global.getOrdersController().initialize();
        stage.show();
    }
    @FXML
    private void openProduct(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getProductListScene());
        ImsGui.global.getProfilesController().initialize();
        stage.show();
    }
    @FXML
    private void openProductStatus(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getProductStatusScene());
        ImsGui.global.getProductStatusController().initialize();
        stage.show();
    }
    @FXML
    private void openProfiles(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getProfilesScene());
        ImsGui.global.getProfilesController().initialize();
        stage.show();
    }
}
