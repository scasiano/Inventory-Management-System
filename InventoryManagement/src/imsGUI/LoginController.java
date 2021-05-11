package imsGUI;

import ims.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            boolean admin;
            boolean averagePrivelege;
            String role = authenticateUser(User, Pass);
            if (role.equals("")){
                Global.warningAlert("Incorrect Credentials", "Your username and password do not match, please try again");
                passBox.clear();
                return;
            }
            admin = role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("IT");
            Global.privilege=admin;
            Global.averagePrivelege= !(role.contains("User")) && !(role.contains("Customer"));
            System.out.println(Global.averagePrivelege);
            Global.currentUser = Users.selectUserByUserName(User);
            Stage stage = (Stage) LogBut.getScene().getWindow();
            stage.setScene(ImsGui.global.getHomepageScene());
            stage.show();
            userBox.clear();
            passBox.clear();
            ImsGui.global.getProfilesController().userDetails();
            ImsGui.global.getHomepageController().initialize();

        } catch (Exception e) {
            Global.exceptionAlert(e, "login");
        }
    }
}

    //once login is set up this should lead to homepage
