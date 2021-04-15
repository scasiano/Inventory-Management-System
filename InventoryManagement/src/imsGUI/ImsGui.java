package imsGUI;

import ims.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImsGui extends Application {
    public static Global global = new Global();
    protected String User;
    protected boolean r;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            ims.SqlController.initializeSql();
            global.loadFxmlFiles();
        }
        catch(Exception e) {
            Global.exceptionAlert(e, "launch");
            System.exit(503);
        }
        primaryStage.setTitle("Inventory Management Database");
        primaryStage.setScene(global.getHomepageScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
