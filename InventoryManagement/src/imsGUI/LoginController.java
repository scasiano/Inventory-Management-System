package imsGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ims.Users;
import static ims.Users.authenticateUser;

public class LoginController {

    @FXML
    TextField userBox;
    @FXML
    TextField passBox;
    @FXML
    Button LogBut;

    public void Login() {
        try {
            String User = userBox.getText();
            String Pass = passBox.getText();
            Boolean admin;
            String role = authenticateUser(User, Pass);
            admin = role.equals("Admin");

            Stage stage = (Stage) LogBut.getScene().getWindow();
            stage.setScene(ImsGui.global.getHomepageScene());
            stage.show();

        } catch (Exception e) {
            Global.exceptionAlert(e, "login");
        }
    }
}

    //once login is set up this should lead to homepage
