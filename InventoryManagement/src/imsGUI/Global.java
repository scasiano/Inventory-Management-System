package imsGUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;

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
    public static Boolean privilege;
    public static String currentUser;

    public void loadFxmlFiles() throws Exception{
        adminUserList = FXMLLoader.load(getClass().getResource("AdminUserList.fxml"));
        budget = FXMLLoader.load(getClass().getResource("Budget.fxml"));
        homepage = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        login = FXMLLoader.load(getClass().getResource("Login.fxml"));
        orders = FXMLLoader.load(getClass().getResource("Orders.fxml"));
        productList = FXMLLoader.load(getClass().getResource("ProductList.fxml"));
        productStatus = FXMLLoader.load(getClass().getResource("ProductStatus.fxml"));
        profiles = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
        adminUserListScene = new Scene(adminUserList);
        budgetScene = new Scene(budget);
        homepageScene = new Scene(homepage);
        loginScene = new Scene(login);
        ordersScene = new Scene(orders);
        productListScene = new Scene(productList);
        productStatusScene = new Scene(productStatus);
        profilesScene = new Scene(profiles);
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

    public static void loginAlert(File loginInformation, String dbUserName, String dbPassword, String dbIpAddress, String dbPort, String dbName){
        try {
            loginInformation.createNewFile();
            FileWriter fileWriter = new FileWriter("loginInformation");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            TextField username = new TextField();
            username.setText(dbUserName);
            PasswordField password = new PasswordField();
            password.setText(dbPassword);
            TextField ipAddress = new TextField();
            ipAddress.setText(dbIpAddress);
            TextField port = new TextField();
            port.setText(dbPort);
            TextField name = new TextField();
            name.setText(dbName);
            Label usernameLabel = new Label("Username: ");
            Label passwordLabel = new Label("Password: ");
            Label ipAddressLabel = new Label("IP Address: ");
            Label portLabel = new Label("Port: ");
            Label nameLabel = new Label("DB Name: ");
            Region usernameFiller = new Region();
            HBox.setHgrow(usernameFiller, Priority.ALWAYS);
            Region passwordFiller = new Region();
            HBox.setHgrow(passwordFiller, Priority.ALWAYS);
            Region ipAddressFiller = new Region();
            HBox.setHgrow(ipAddressFiller, Priority.ALWAYS);
            Region portFiller = new Region();
            HBox.setHgrow(portFiller, Priority.ALWAYS);
            Region nameFiller = new Region();
            HBox.setHgrow(nameFiller, Priority.ALWAYS);
            HBox usernameBox = new HBox(100, usernameLabel, usernameFiller, username);
            HBox passwordBox = new HBox(100, passwordLabel, passwordFiller, password);
            HBox ipAddressBox = new HBox(100, ipAddressLabel, ipAddressFiller, ipAddress);
            HBox portBox = new HBox(100, portLabel, portFiller, port);
            HBox nameBox = new HBox(100, nameLabel, nameFiller, name);
            alert.getDialogPane().setHeaderText("Login information for the Database:");
            VBox expContent = new VBox();
            expContent.getChildren().addAll(usernameBox, passwordBox, ipAddressBox, portBox, nameBox);
            expContent.setMaxWidth(Double.MAX_VALUE);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
            fileWriter.write(username.getText() + "," + password.getText() + "," + ipAddress.getText() + "," + port.getText() + "," + name.getText());
            fileWriter.close();
        }
        catch (IOException e){exceptionAlert(e, "login file");}
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
