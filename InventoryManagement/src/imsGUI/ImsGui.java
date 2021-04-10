package imsGUI;

import ims.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;

public class ImsGui extends Application {
    protected String User;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Test.fxml"));
        primaryStage.setTitle("Inventory Management Database");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            ims.SqlController.initializeSql();
            ArrayList<Long> prodName = Products.selectProductID();
            for(int i=0;i<prodName.size();i++)
            {
                System.out.println(prodName.get(i));
            }
        }catch(Exception e)
        {e.printStackTrace();}
        launch(args);
    }
}
