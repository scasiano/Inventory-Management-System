package imsGUI;

import ims.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class ImsGui extends Application {
    public static Global global = new Global();
    protected String User;
    protected boolean r;
    private static String dbUserName = "root";
    private static String dbPassword = "root";
    private static String dbIpAddress = "localhost";
    private static String dbPort = "3306";
    private static String dbName = "inventory_management";

    @Override
    public void start(Stage primaryStage) {
        try{
            File loginInformation = new File("loginInformation");
            boolean showAlert = true;
            if (!loginInformation.exists()){
                Global.loginAlert(loginInformation, "", "", "", "", "");
                showAlert = false;
            }
            BufferedReader lineRead = new BufferedReader(new FileReader("loginInformation"));
            Scanner objectRead = new Scanner(lineRead.readLine());
            objectRead.useDelimiter(",");
            dbUserName = objectRead.next();
            dbPassword = objectRead.next();
            dbIpAddress = objectRead.next();
            dbPort = objectRead.next();
            dbName = objectRead.next();
            if (dbUserName == null || dbUserName.equals("") || dbPassword == null || dbPassword.equals("") || dbIpAddress == null || dbIpAddress.equals("") || dbPort == null || dbPort.equals("") || dbName == null || dbName.equals("")){
                Global.warningAlert("User information incorrect", "Some portions of the user information was read as blank, please restart the program");
                loginInformation.delete();
                System.exit(4122);
            }
            if (showAlert) Global.loginAlert(loginInformation, dbUserName, dbPassword, dbIpAddress, dbPort, dbName);
        }
        catch (IOException e){
            Global.warningAlert("Login file error", "Login file threw exception, default values will be used.");
            Global.exceptionAlert(e, "database login file");
            dbUserName = "root";
            dbPassword = "root";
            dbIpAddress = "localhost";
            dbPort = "3306";
            dbName = "inventory_management";
        }
        try {
            ims.SqlController.initializeSql(dbUserName, dbPassword, dbIpAddress, dbPort, dbName);
            global.loadFxmlFiles();
        }
        catch(Exception e) {
            Global.exceptionAlert(e, "launch");
            System.exit(4167);
        }
        primaryStage.setTitle("Inventory Management Database");
        primaryStage.setScene(global.getLoginScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
