package imsGUI;

import ims.Users;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilesController {

    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    TextField role;
    @FXML
    TextField id;
    @FXML
    TextField fn;
    @FXML
    TextField ln;
    @FXML
    TextField un;
    @FXML
    PasswordField pw;
    final ObservableList options = FXCollections.observableArrayList();
    private ObservableList<Users> data = FXCollections.observableArrayList();
    TableView<Users> table;

    private FileChooser fileChooser;
    private Button browse;
    private File file;
    private final Desktop desktop = Desktop.getDesktop();

    private FileInputStream fis;
    private Button exportToXL, importXLtoDB;

    Label label = new Label("Label");

    TextField username = new TextField();
    PasswordField password = new PasswordField();

    public void profiles() {

        Button save = new Button("Save");
        save.setOnAction(e -> {
            if (validatefName() & validatelName() & validatePassword()) {
                try {
                    String query = "INSERT INTO users (user_id, username, password, fname, lname, role) " +
                            "VALUES(?,?,?,?,?,?)";
                    pst = conn.prepareStatement(query);

                    pst.setString(1, id.getText());
                    pst.setString(2, un.getText());
                    pst.setString(3, pw.getText());
                    pst.setString(4, fn.getText());
                    pst.setString(5, ln.getText());
                    pst.setString(6, role.getText());

                    fis = new FileInputStream(file);
                    //pst.setBinaryStream(7, (InputStream) fis, (int) file.length());

                    Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);
                    saveAlert.setTitle("Save Alert");
                    saveAlert.setHeaderText("Save");
                    saveAlert.setContentText("User has been saved.");
                    saveAlert.showAndWait();

                    pst.execute();

                    pst.close();
                    clearFields();
                } catch (SQLException | FileNotFoundException e1) {
                    label.setText("SQL Error");
                    System.err.println(e1);
                }
                refreshTable();
            }

        });


        Button update = new Button("Update");
        update.setOnAction(e -> {
            if (validatefName() & validatelName() & validatePassword()) {
                try {
                    String query = "update users set user_id=?, username=?, password=?, fname=?, lname=?, " +
                            "role=? where user_id='" + id.getText() + "'";
                    pst = conn.prepareStatement(query);

                    pst.setString(1, id.getText());
                    pst.setString(2, un.getText());
                    pst.setString(3, pw.getText());
                    pst.setString(4, fn.getText());
                    pst.setString(5, ln.getText());
                    pst.setString(6, role.getText());

                    fis = new FileInputStream(file);
                    pst.setBinaryStream(7, (InputStream) fis, (int) file.length());

                    Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateAlert.setTitle("Update Alert");
                    updateAlert.setHeaderText("Update");
                    updateAlert.setContentText("User has been updated.");
                    updateAlert.showAndWait();

                    pst.execute();

                    pst.close();
                    clearFields();
                } catch (SQLException | FileNotFoundException e1) {
                    label.setText("SQL Error");
                    System.err.println(e1);
                }
                refreshTable();
            }
        });

        table = new TableView<>();

        TableColumn column1 = new TableColumn("User ID");
        column1.setMaxWidth(50);
        column1.setCellValueFactory(new PropertyValueFactory<>("User ID"));

        TableColumn column2 = new TableColumn("Username");
        column2.setMaxWidth(50);
        column2.setCellValueFactory(new PropertyValueFactory<>("Username"));

        TableColumn column3 = new TableColumn("Password");
        column3.setMaxWidth(50);
        column3.setCellValueFactory(new PropertyValueFactory<>("Password"));

        TableColumn column4 = new TableColumn("First Name");
        column4.setMaxWidth(50);
        column4.setCellValueFactory(new PropertyValueFactory<>("First Name"));

        TableColumn column5 = new TableColumn("Last Name");
        column5.setMaxWidth(50);
        column5.setCellValueFactory(new PropertyValueFactory<>("Last Name"));

        TableColumn column6 = new TableColumn("Role");
        column6.setMaxWidth(50);
        column6.setCellValueFactory(new PropertyValueFactory<>("Role"));

        table.getColumns().addAll(column1, column2, column3, column4, column5, column6);
        table.setTableMenuButtonVisible(true);

        Button delete = new Button("Delete User");
        delete.setOnAction(e -> {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Confirm Delete");
            deleteAlert.setHeaderText("Delete");
            deleteAlert.setContentText("Are you sure you want to delete this user?");
            Optional<ButtonType> confirmDelete = deleteAlert.showAndWait();

/*            if (deleteAlert.getButtonTypes() == ButtonType.OK) {
                try {
                    String query = "delete from ims where user_id=?";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, id.getText());
                    pst.executeUpdate();

                    pst.close();
                } catch (SQLException ex) {
                    System.err.println("SQL Error");
                }

                clearFields();
                refreshTable();
            }*/
        });
    }

    private boolean validatefName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(fn.getText());
        if(m.find() && m.group().equals(fn.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate First Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid First Name");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validatelName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(ln.getText());
        if(m.find() && m.group().equals(ln.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Last Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Last Name");
            alert.showAndWait();

            return false;
        }
    }



    private boolean validatePassword(){
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
        Matcher m = p.matcher(pw.getText());
        if(m.matches()){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 6 -15");
            alert.showAndWait();

            return false;
        }
    }

    public void refreshTable(){
        data.clear();
        try{
            String query = "select * from UserDatabase";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while(rs.next()){
                data.add(new Users(
                        rs.getLong("User ID"),
                        rs.getString("First Name"),
                        rs.getString("Last Name"),
                        rs.getString("Role"),
                        rs.getString("Username"),
                        rs.getString("Password")
                ));
                table.setItems(data);
            }
            pst.close();
            rs.close();
        }catch(Exception e2){
            System.err.println(e2);
        }
    }

    public void clearFields(){
        id.clear();
        fn.clear();
        ln.clear();
        un.clear();
        pw.clear();
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
