package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfilesController {

    @FXML
    Label lFName;
    @FXML
    Label lLName;
    @FXML
    Label lUsername;
    @FXML
    Label lUser_id;
    @FXML
    Label lRole;
    @FXML
    Label lPassword;
    @FXML
    Label lConfirmPassword;
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
    TextField confirmPassword;
    @FXML
    Button save;
    @FXML
    Button homepageBtn;
    @FXML
    Button loginBtn;
    @FXML
    HBox usersList;
    @FXML
    HBox userResults;

    ArrayList<Users> allUsers = new ArrayList<>();
    ListView<String> columnUN;
    ListView<Long> columnID;
    int uIndex = -1;

    public void initialize() {
        userDetails();
        clearUserInfo();
    }

    public void setUsersList() { // not currently in use.
        ObservableList<Long> userID;
        ObservableList<String> userName;
        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> usernameList = new ArrayList<>();

        VBox un = new VBox();
        Label UN = new Label();
        UN.setText("Username");

        VBox id = new VBox();
        Label ID = new Label();
        ID.setText("ID");

        un.getChildren().add(UN);
        id.getChildren().add(ID);

        try {
            allUsers = Users.selectAll();
            for (Users allUser : allUsers) {
                idList.add(allUser.getUserID());
                usernameList.add(allUser.getUsername());
            }
            userID = FXCollections.observableArrayList(idList);
            userName = FXCollections.observableArrayList(usernameList);
            columnUN = new ListView<>(userName);
            un.getChildren().add(columnUN);
            columnID = new ListView<>(userID);
            id.getChildren().add(columnID);
            usersList.getChildren().addAll(id, un);
        } catch (Exception e) {
            Global.exceptionAlert(e,"Set User List");
        }
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
            role.setText(currentUser.getRole());
            confirmPassword.setText((currentUser.getPassword()));
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Profile auto-fill");
        }
    }

    public void startUser(ActionEvent event) { //  not in use.
        username.setEditable(true);
        fname.setEditable(true);
        lname.setEditable(true);
        role.setEditable(true);
        password.setEditable(true);
        user_id.setEditable(true);
        save.setVisible(true);
        usersList.setVisible(true);
        userResults.setVisible(true);
    }

    public void modifyDBProfile(){
        try{
            if(Global.privilege = false) {
                if (Global.currentUser == null) return;
                System.out.println("Here");
                Users userTMP = Global.currentUser;
                Users.modifyFName(userTMP.getUserID(), userTMP.getFName());
                Users.modifyLName(userTMP.getUserID(), userTMP.getLName());
                Users.modifyUsername(userTMP.getUserID(), userTMP.getUsername());
                Users.modifyRole(userTMP.getUserID(), userTMP.getRole());
                if (password.getText().equals(confirmPassword.getText())) {
                    Users.modifyPassword(userTMP.getUserID(), userTMP.getPassword());
                } else {
                    Global.warningAlert("Incorrect Password", "Passwords do not match");
                } // Working through changes but not saving to DB.
            } else {
                if (Global.currentUser == null) return;
                Users userTMP = Global.currentUser;
                Users.modifyFName(userTMP.getUserID(), userTMP.getFName());
                Users.modifyLName(userTMP.getUserID(), userTMP.getLName());
                if (password.getText().equals(confirmPassword.getText())) {
                    Users.modifyPassword(userTMP.getUserID(), userTMP.getPassword());
                } else {
                    Global.warningAlert("Incorrect Password", "Passwords do not match");
                }
            }

            //initialize();
        } catch (Exception e){
            Global.exceptionAlert(e, "Modify User");
        }
    }

   public void saveButtonClicked(ActionEvent event) throws SQLException {  // not in use.
        Users tmp = new Users(0, "", "", "", "", "");
        boolean flag = false;

        try{
            while(!flag){
                if (user_id.getText().length() > 0 && Long.valueOf(user_id.getText().length()) >= 0){
                    flag = true;
                    tmp.setUserID(Long.valueOf(user_id.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    user_id.clear();
                }
                if (username.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(username.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Username", "Every User needs a Username");
                    username.clear();
                }
                if (fname.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(fname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    fname.clear();
                }
                if (lname.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(lname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    lname.clear();
                }
                if (role.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(role.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    role.clear();
                }
                if (password.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(password.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect password", "Every User needs a password");
                    password.clear();
                }
            }
            Users.addRecord(tmp);
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("User Id Exists", "User ID already exists. User add canceled");
        }
        endUserEdit();
        columnUN.getItems().clear();
        columnID.getItems().clear();
        usersList.getChildren().clear();
        usersList.getChildren().clear();
        usersList.getChildren().clear();
        initialize();
        clearUserInfo();
    }

    public void clearUserInfo(){
        user_id.clear();
        username.clear();
        fname.clear();
        lname.clear();
        role.clear();
        password.clear();
    }

    public void endUserEdit() {
        user_id.setEditable(false);
        username.setEditable(false);
        fname.setEditable(false);
        lname.setEditable(false);
        role.setEditable(false);
        password.setEditable(false);
        save.setVisible(true);
        usersList.setVisible(true);
        userResults.setVisible(true);
    }


    public void modifyUser(){ // not in use.
        username.setEditable(false);
        fname.setEditable(false);
        lname.setEditable(false);
        role.setEditable(false);
        password.setEditable(true);
        user_id.setEditable(false);
        save.setVisible(true);
        usersList.setVisible(true);
        userResults.setVisible(true);
    }

    @FXML
    private void openHomePage(ActionEvent event) {
        clearUserInfo();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }

    @FXML
    private void openLogin(ActionEvent event) {
        clearUserInfo();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
