package imsGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ImsGui extends Application {
    public static Global global = new Global();
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
            try{
                dbUserName = objectRead.next();
                dbPassword = objectRead.next();
                dbIpAddress = objectRead.next();
                dbPort = objectRead.next();
                dbName = objectRead.next();
                lineRead.close();
                objectRead.close();
            }
            catch (NoSuchElementException e){
                Global.warningAlert("Error Reading File", "An error reading from loginInformation has occurred, please restart the program and fill in login information again.");
                lineRead.close();
                objectRead.close();
                loginInformation.delete();
                System.exit(4122);
            }
            if (dbUserName == null || dbUserName.equals("") || dbPassword == null || dbPassword.equals("") || dbIpAddress == null || dbIpAddress.equals("") || dbPort == null || dbPort.equals("") || dbName == null || dbName.equals("")){
                Global.warningAlert("Login Information Blank", "Login information for the DB cannot be blank.\nPlease restart the program and ensure that all fields are filled\nNote: if your database user does not use a password, you must set up a user with a password for this program.");
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
        VBox splashVBox = null;
        try{
            splashVBox = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        }
        catch (IOException e) {
            Global.exceptionAlert(e, "Splash");
        }
        SplashScreenController.loadSplashScreen(primaryStage, splashVBox);
    }

    public static void showLogin(Stage primaryStage){
        primaryStage.setTitle("Inventory Management Database");
        primaryStage.setScene(global.getLoginScene());
        primaryStage.show();
    }

    public static void startProcessing(){
        try {
            ims.SqlController.initializeSql(dbUserName, dbPassword, dbIpAddress, dbPort, dbName);
            global.loadFxmlFiles();
        }
        catch(Exception e) {
            Global.exceptionAlert(e, "launch");
            System.exit(4167);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
