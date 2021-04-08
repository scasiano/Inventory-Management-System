package imsGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestController {
    @FXML
    private void openProducts() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductList.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}

