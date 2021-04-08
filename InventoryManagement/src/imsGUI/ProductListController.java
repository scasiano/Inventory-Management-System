package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductListController {
    @FXML
    VBox productsList;
    public void setProductList(ActionEvent event) {
        for(int i=0;i<7;i++)
        {
            Label label=new Label();
            label.setText("Label"+i);
            productsList.getChildren().add(label);
        }
    }

}
