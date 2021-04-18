package imsGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class BudgetController {

    @FXML
    Label periodLabel;
    @FXML
    Label netLabel;
    @FXML
    Label outLabel;
    @FXML
    Label inLabel;
    @FXML
    Label empLabel;
    @FXML
    DatePicker dateRange;//this returns localDate which has to be cast into a SQL Date
    @FXML
    TextField netProfit;
    @FXML
    TextField outgoing;
    @FXML
    TextField incoming;
    @FXML
    TextField userInfo;
    @FXML
    Button addBtn;
    @FXML
    Button modifyBtn;
    @FXML
    Button deleteBtn;
    @FXML
    HBox periodList;
    

}
