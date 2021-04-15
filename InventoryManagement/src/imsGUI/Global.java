package imsGUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Global {

    private Parent adminUserList;
    private Parent budget;
    private Parent homepage;
    private Parent login;
    private Parent orders;
    private Parent productList;
    private Parent productStatus;
    private Parent profiles;
    private Scene adminUserListScene;
    private Scene budgetScene;
    private Scene homepageScene;
    private Scene loginScene;
    private Scene ordersScene;
    private Scene productListScene;
    private Scene productStatusScene;
    private Scene profilesScene;

    public void loadFxmlFiles() throws Exception{
        adminUserList = FXMLLoader.load(getClass().getResource("AdminUserList.fxml"));
        budget = FXMLLoader.load(getClass().getResource("Budget.fxml"));
        homepage = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        login = FXMLLoader.load(getClass().getResource("Login.fxml"));
        orders = FXMLLoader.load(getClass().getResource("Orders.fxml"));
        productList = FXMLLoader.load(getClass().getResource("ProductList.fxml"));
        productStatus = FXMLLoader.load(getClass().getResource("ProductStatus.fxml"));
        profiles = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
        adminUserListScene = new Scene(adminUserList, 500, 500);
        budgetScene = new Scene(budget, 500, 500);
        homepageScene = new Scene(homepage, 500, 500);
        loginScene = new Scene(login, 500, 500);
        ordersScene = new Scene(orders, 500, 500);
        productListScene = new Scene(productList, 500, 500);
        productStatusScene = new Scene(productStatus, 500, 500);
        profilesScene = new Scene(profiles, 500, 500);
    }

    public static void exceptionAlert(Exception e, String location){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Label label = new Label("Exception stack trace: ");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        TextArea textArea = new TextArea(sw.toString());
        alert.getDialogPane().setHeaderText("Exception occurred in " + location);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        e.printStackTrace();
        alert.showAndWait();
    }

    public static void warningAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void informAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //Get set
    public Scene getAdminUserListScene() {
        return adminUserListScene;
    }
    public Scene getBudgetScene() {
        return budgetScene;
    }
    public Scene getHomepageScene() {
        return homepageScene;
    }
    public Scene getLoginScene() {
        return loginScene;
    }
    public Scene getOrdersScene() {
        return ordersScene;
    }
    public Scene getProductListScene() {
        return productListScene;
    }
    public Scene getProductStatusScene() {
        return productStatusScene;
    }
    public Scene getProfilesScene() {
        return profilesScene;
    }
}
