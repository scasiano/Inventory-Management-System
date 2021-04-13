package imsGUI;

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
    PasswordField password;
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

    public void initialize() throws SQLException {
        setUsersList();
        userDetails();
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
            e.printStackTrace();
        }
    }

    public void userDetails() {
        columnID.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Integer index = columnID.getSelectionModel().getSelectedIndex();
                columnUN.getSelectionModel().select(index);
                username.setText(allUsers.get(index).getUsername());
                user_id.setText(String.valueOf(allUsers.get(index).getUserID()));
                fname.setText(allUsers.get(index).getFName());
                lname.setText(allUsers.get(index).getLName());
                password.setText(allUsers.get(index).getPassword());
                role.setText(allUsers.get(index).getRole());
            }
        });
        columnUN.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

        //save.setOnAction(e -> saveButtonClicked());
        //delete.setOnAction(e -> deleteButtonClicked());
    }

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

    public void saveButtonClicked(ActionEvent event) {
        Users tmp = new Users(0, "", "", "", "", "");
        if (Double.valueOf(user_id.getText()) > 0 && username.getText().length() > 0
                && fname.getText().length() > 0 && lname.getText().length() > 0 && role.getText().length() > 0
                && password.getText().length() > 0) {
            try {
                tmp.setUserID(Long.valueOf(user_id.getText()));
                tmp.setUsername(username.getText());
                tmp.setFName(fname.getText());
                tmp.setLName(lname.getText());
                tmp.setRole(role.getText());
                tmp.setPassword(password.getText());
                Users.addRecord(tmp);
                columnUN.getItems().clear();
                columnID.getItems().clear();
                setUsersList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert noUser = new Alert(Alert.AlertType.ERROR);
            noUser.setTitle("Invalid User");
            noUser.setHeaderText("Invalid Input");
            noUser.setContentText("Make sure all fields are correct when making User.");
            noUser.showAndWait();
        }
    }

    public void endUserEdit() {

    }


    public void deleteButtonClicked() {

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

    public void loadTables() {
        load.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
            }
        });
    }

    @FXML

    private void openHomePage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void openLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
