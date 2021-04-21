package imsGUI;

import ims.Users;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;

public class Global {

    private Scene adminUserListScene;
    private Scene budgetScene;
    private Scene homepageScene;
    private Scene loginScene;
    private Scene ordersScene;
    private Scene productListScene;
    private Scene productStatusScene;
    private Scene profilesScene;
    private AdminUserListController adminUserListController;
    private BudgetController budgetController;
    private HomepageController homepageController;
    private LoginController loginController;
    private OrdersController ordersController;
    private ProductListController productListController;
    private ProductStatusController productStatusController;
    private ProfilesController profilesController;
    public static Boolean privilege=true;
    public static Users currentUser;

    public void loadFxmlFiles() throws Exception{
        FXMLLoader adminUserListLoader = new FXMLLoader(getClass().getResource("AdminUserList.fxml"));
        FXMLLoader budgetLoader = new FXMLLoader(getClass().getResource("Budget.fxml"));
        FXMLLoader homepageLoader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        FXMLLoader ordersLoader = new FXMLLoader(getClass().getResource("Orders.fxml"));
        FXMLLoader productListLoader = new FXMLLoader(getClass().getResource("ProductList.fxml"));
        FXMLLoader productStatusLoader = new FXMLLoader(getClass().getResource("ProductStatus.fxml"));
        FXMLLoader profilesLoader = new FXMLLoader(getClass().getResource("Profiles.fxml"));
        Parent adminUserList = adminUserListLoader.load();
        Parent budget = budgetLoader.load();
        Parent homepage = homepageLoader.load();
        Parent login = loginLoader.load();
        Parent orders = ordersLoader.load();
        Parent productList = productListLoader.load();
        Parent productStatus = productStatusLoader.load();
        Parent profiles = profilesLoader.load();
        adminUserListScene = new Scene(adminUserList);
        budgetScene = new Scene(budget,720,480);
        homepageScene = new Scene(homepage);
        loginScene = new Scene(login);
        ordersScene = new Scene(orders,720,480);
        productListScene = new Scene(productList,720,480);
        productStatusScene = new Scene(productStatus,720,480);
        profilesScene = new Scene(profiles);
        adminUserListController = adminUserListLoader.getController();
        budgetController = budgetLoader.getController();
        homepageController = homepageLoader.getController();
        loginController = loginLoader.getController();
        ordersController = ordersLoader.getController();
        productListController = productListLoader.getController();
        productStatusController = productStatusLoader.getController();
        profilesController = profilesLoader.getController();
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
    public AdminUserListController getAdminUserListController() {
        return adminUserListController;
    }
    public BudgetController getBudgetController() {
        return budgetController;
    }
    public HomepageController getHomepageController() {
        return homepageController;
    }
    public LoginController getLoginController() {
        return loginController;
    }
    public OrdersController getOrdersController() {
        return ordersController;
    }
    public ProductListController getProductListController() {
        return productListController;
    }
    public ProductStatusController getProductStatusController() {
        return productStatusController;
    }
    public ProfilesController getProfilesController() {
        return profilesController;
    }
}
