package imsGUI;

import ims.Employees;
import ims.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//import java.util.Date;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminUserListController {
    @FXML
    TableView<Users> userT;
    @FXML
    TableColumn userID;
    @FXML
    TableColumn uFirstName;
    @FXML
    TableColumn ULastName;
    @FXML
    TableColumn userName;
    @FXML
    TableColumn role;
    @FXML
    TableColumn userAction;

    @FXML
    TableView<Employees> empT;
    @FXML
    TableColumn empID;
    @FXML
    TableColumn empUserID;
    @FXML
    TableColumn firstName;
    @FXML
    TableColumn lastName;
    @FXML
    TableColumn payHour;
    @FXML
    TableColumn dateStart;
    @FXML
    TableColumn curAdminAction;

    ArrayList<Users> allUsers;
    ArrayList<Employees> allEmps;
    public void initialize(){
        setUserTable();
        setEmpTable();
    }

    public void setUserTable(){
        try {
            userID.setCellValueFactory(new PropertyValueFactory<Users,Long>("userID"));
            uFirstName.setCellValueFactory(new PropertyValueFactory<Users,String>("fName"));
            ULastName.setCellValueFactory(new PropertyValueFactory<Users, String>("lName"));
            userName.setCellValueFactory(new PropertyValueFactory<Users,String>("username"));
            role.setCellValueFactory(new PropertyValueFactory<Users,String>("role"));
            allUsers = Users.selectAll();
            ObservableList<Users> users = FXCollections.observableArrayList(allUsers);
            userT.setItems(users);
        }catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    public void setEmpTable(){
        try {
            empID.setCellValueFactory(new PropertyValueFactory<Employees, Long>("employeeNo"));
            empUserID.setCellValueFactory(new PropertyValueFactory<Employees, Long>("userID"));
            firstName.setCellValueFactory(new PropertyValueFactory<Employees, String>("employeeFn"));
            lastName.setCellValueFactory(new PropertyValueFactory<Employees, String>("employeeLn"));
            payHour.setCellValueFactory(new PropertyValueFactory<Employees, Double>("position"));
            dateStart.setCellValueFactory(new PropertyValueFactory<Employees, Date>("startDate"));
            //curAdminAction.setCellValueFactory(new PropertyValueFactory<Employees,Long>("employeeNo"));
            allEmps = Employees.selectAll();
            ObservableList<Employees> emps = FXCollections.observableArrayList(allEmps);
            empT.setItems(emps);
        }catch(SQLException e){
            System.out.println(e);
            System.out.println(e.getSQLState());
        }
    }
    @FXML
    private void openHomePage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
