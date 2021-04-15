package imsGUI;

import ims.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImsGui extends Application {
    protected String User;
    protected boolean r;
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            ims.SqlController.initializeSql();
        }
        catch(Exception e) {
            Global.exceptionAlert(e, "launch");
            System.exit(503);
        }
        Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        primaryStage.setTitle("Inventory Management Database");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
