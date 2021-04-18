package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
    Button save;
    @FXML
    Button update;
    @FXML
    Button load;
    @FXML
    Button delete;
    @FXML
    Button homepageBtn;
    @FXML
    Button loginBtn;
    @FXML
    HBox usersList;
    @FXML
    HBox userResults;

    ArrayList<Users> allUsers = new ArrayList<Users>();
    ListView<String> columnUN;
    ListView<Long> columnID;
    int uIndex = -1;

    public void initialize() throws SQLException {
      //setUsersList();
       userDetails();
       //Users.getUserID(Global.currentUser);
    }

    public void setUsersList() {
        ObservableList<Long> userID;
        ObservableList<String> userFName;
        ObservableList<String> userLName;
        ObservableList<String> userName;
        ObservableList<String> userPassword;
        ObservableList<String> userRole;
        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> fnameList = new ArrayList<>();
        ArrayList<String> lnameList = new ArrayList<>();
        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> passwordList = new ArrayList<>();
        ArrayList<String> roleList = new ArrayList<>();

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
            for (int i = 0; i < allUsers.size(); i++) {
                idList.add(allUsers.get(i).getUserID());
                usernameList.add(allUsers.get(i).getUsername());
            }
            userID = FXCollections.observableArrayList(idList);
            userName = FXCollections.observableArrayList(usernameList);
            columnUN = new ListView<String>(userName);
            un.getChildren().add(columnUN);
            columnID = new ListView<Long>(userID);
            id.getChildren().add(columnID);
            usersList.getChildren().addAll(id, un);
        } catch (Exception e) {
            Global.exceptionAlert(e,"Set User List");
        }
    }

    public void userDetails() {
        try {
            Users currentUser = Users.selectUserByUserName(Global.currentUser);
            username.setText(String.valueOf(currentUser.getUsername()));
            user_id.setText(String.valueOf(currentUser.getUserID()));
            fname.setText(currentUser.getFName());
            lname.setText(currentUser.getLName());
            password.setText(currentUser.getPassword());
            role.setText(currentUser.getRole());
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Profile auto-fill");
        }
    }
        /*columnUN.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Integer index = columnUN.getSelectionModel().getSelectedIndex();
                columnUN.getSelectionModel().select(index);
                username.setText(allUsers.get(index).getUsername());
                user_id.setText(String.valueOf(allUsers.get(index).getUserID()));
                fname.setText(allUsers.get(index).getFName());
                lname.setText(allUsers.get(index).getLName());
                password.setText(allUsers.get(index).getPassword());
                role.setText(allUsers.get(index).getRole());
            }
        });
         */

        //save.setOnAction(e -> saveButtonClicked());
        //delete.setOnAction(e -> deleteButtonClicked());

    public void startUser(ActionEvent event) {
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

    public void saveButtonClicked(ActionEvent event) throws SQLException {
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

    public void modifyUser(){
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
    /*public void clearUserInfo(){
        user_id.clear();
        username.clear();
        fname.clear();
        lname.clear();
        role.clear();
        password.clear();
    }*/
/*public void deleteButtonClicked() throws SQLException {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
           try{ ims.Users.deleteRecord(allUsers.get(uIndex).getUserID());
            endUserEdit();
            columnUN.getItems().clear();
            columnID.getItems().clear();
            usersList.getChildren().clear();
            usersList.getChildren().clear();
            clearUserInfo();
            initialize();}catch(Exception e){
               Global.exceptionAlert(e,"Delete User");
           }
        }
    }*/
    /*public void loadTables() {
           try {
                username.setEditable(false);
                user_id.setEditable(false);
                fname.setEditable(false);
                lname.setEditable(false);
                role.setEditable(false);
                password.setEditable(false);
                username.setVisible(true);
                user_id.setVisible(true);
                fname.setVisible(true);
                lname.setVisible(true);
                role.setVisible(true);
                password.setVisible(true);
                save.setVisible(true);
                update.setVisible(true);
                delete.setVisible(true);
                user_id.clear();
                username.clear();
                fname.clear();
                lname.clear();
                role.clear();
                password.clear();
                initialize();
            } catch (Exception e) {
                Global.exceptionAlert(e,"Load Tables");
            }
    }*/

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
