package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPane.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    VBox productsList;

    @FXML
    GridPane ParGrid;

    @FXML
    ScrollPane parentListContainer;

    private Stage stage;
    private Scene scene;
    private Pane pane;
//for testing purposes, will be moved later and implemented
   public void setProductList(ActionEvent event) {
       for(int i=0;i<7;i++)
        {
            Label label=new Label();
            label.setText("Label"+i);
            productsList.getChildren().add(label);
        }
    }

    public Controller() throws IOException {

    }
    //Methods for each of the views
    //change as needed

}

