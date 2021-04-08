package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane.*;
import javafx.stage.Stage;
import java.io.IOException;

public class TestController {
    @FXML
    private void openProducts() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}

