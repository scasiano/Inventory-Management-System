package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.Employees;
import ims.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProfilesController {

    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField username;
    @FXML
    TextField user_id;
    @FXML
    TextField role;
    @FXML
    TextField password;
    @FXML
    Label empL;
    @FXML
    TextField emp_id;

    @FXML
    Button homepageBtn;
    @FXML
    Button loginBtn;

    String passWord;
    public void initialize() {
        userDetails();
    }

    public void userDetails() {
        try {
            if (Global.currentUser == null) return;
            Users currentUser = Global.currentUser;
            username.setText(currentUser.getUsername());
            user_id.setText(Long.toString(currentUser.getUserID()));
            fname.setText(currentUser.getFName());
            lname.setText(currentUser.getLName());
            password.setText(currentUser.getPassword());
            passWord=currentUser.getPassword();
            role.setText(currentUser.getRole());
            if(!role.getText().equals("User") && !role.getText().equals("Customer")){
                empL.setVisible(true);
                emp_id.setVisible(true);
               try {
                   emp_id.setText(Long.toString(Employees.selectEmployeeByUserID(currentUser.getUserID()).getEmployeeNo()));
               }catch(NullPointerException e){
                   empL.setVisible(false);
                   emp_id.setVisible(false);
               }
            }
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Profile auto-fill");
        }
    }

    public void modifyDBProfile(){
        try{
                if (Global.currentUser == null) return;
                System.out.println("Here");
                Users userTMP = Global.currentUser;
                Users.modifyFName(userTMP.getUserID(), fname.getText());
                Users.modifyLName(userTMP.getUserID(), lname.getText());
                if (!password.getText().equals(passWord)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    Label userL = new Label();
                    userL.setText("Current Username");
                    TextField username = new TextField();
                    username.setText(userTMP.getUsername());
                    username.setEditable(false);
                    Label passL = new Label();
                    passL.setText("Confirm current Password");
                    PasswordField passwordC = new PasswordField();
                    VBox textHolder = new VBox();
                    textHolder.getChildren().addAll(userL, username, passL, passwordC);
                    alert.getDialogPane().setContent(textHolder);
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        try {
                            if (!passwordC.getText().isEmpty() && !Users.authenticateUser(username.getText(), passwordC.getText()).equals(""))
                                Users.modifyPassword(userTMP.getUserID(), password.getText());
                            else {
                                Global.warningAlert("Incorrect Password", "Passwords do not match");
                                return;
                            }
                        }catch(Exception e){
                            Global.exceptionAlert(e,"Authenticate Password Update(Profiles)");
                        }
                    }
                    else {
                        alert.hide();
                        Global.warningAlert("", "Update Canceled");
                        return;
                    }
                }
                Users.modifyUsername(userTMP.getUserID(), username.getText());
                Global.currentUser=Users.selectUserByUserID(userTMP.getUserID());
                initialize();
        } catch (Exception e){
            Global.exceptionAlert(e, "Modify Profile");
        }
    }

    @FXML
    private void openHomePage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }

    @FXML
    private void openLogin(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
