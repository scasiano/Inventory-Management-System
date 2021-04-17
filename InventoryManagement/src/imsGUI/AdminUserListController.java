package imsGUI;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ims.Employees;
import ims.Users;
import javafx.beans.Observable;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.util.Date;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminUserListController {
    @FXML
    TableView<Users> userT;
    @FXML
    Button userModify;
    @FXML
    Button userAdd;
    @FXML
    VBox userV;
    @FXML
    HBox userAddB;
    @FXML
    HBox userModB;
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
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField username;
    @FXML
    TextField user_id;
    @FXML
    TextField usrRole;
    @FXML
    Button save;
    @FXML
    Button update;
    @FXML
    Button delete;

    @FXML
    TableView<Employees> empT;
    @FXML
    Button empModify;
    @FXML
    Button empAdd;
    @FXML
    VBox empV;
    @FXML
    HBox empAddB;
    @FXML
    HBox empModB;
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
    TextField empFname;
    @FXML
    TextField empLname;
    @FXML
    TextField empUsername;
    @FXML
    TextField emp_id;
    @FXML
    TextField userid;
    @FXML
    TextField empRole;
    @FXML
    TextField pay;
    @FXML
    TextField startDate;
    @FXML
    Button empSave;
    @FXML
    Button empUpdate;
    @FXML
    Button empDelete;

    ArrayList<Users> allUsers;
    ArrayList<Employees> allEmps;
    Users utemp;
    Employees etemp;
    public void initialize(){
        setUserTable();
        setEmpTable();
        getSelectedInfo();
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
    public void getSelectedInfo(){
        userT.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                 utemp= userT.getSelectionModel().getSelectedItem();
                userModify.setVisible(true);
            }
        });
        empT.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                etemp=empT.getSelectionModel().getSelectedItem();
                empModify.setVisible(true);
            }
        });
    }
    public void saveUserClicked(ActionEvent event) throws SQLException {
        boolean newU=true;
        if(utemp==null)
        {newU=false;}
        Users tmp = new Users(0, "", "", "", "", "");
        boolean flag = false;
        try{
            while(!flag){
                if (user_id.getText().length() > 0 && Long.valueOf(user_id.getText().length()) >= 0){
                    flag = true;
                    tmp.setUserID(Long.parseLong(user_id.getText()));
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
                    tmp.setFName(fname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    fname.clear();
                }
                if (lname.getText().length() > 0){
                    flag = true;
                    tmp.setLName(lname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    lname.clear();
                }
                if (usrRole.getText().length() > 0){
                    flag = true;
                    tmp.setRole(usrRole.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    usrRole.clear();
                }

                //default
                String pass="password"+tmp.getLName().substring(0)+tmp.getFName().substring(0)+"!";
                tmp.setPassword(pass);
            }
            if(!newU) {
                Users.addRecord(tmp);
                userT.getItems().add(tmp);
            }
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("User Id Exists", "User ID already exists. User add canceled");
        } catch(Exception p){
            Global.exceptionAlert(p,"Save Employee");
        }
    }
    public void saveEmpClicked(ActionEvent event) throws SQLException {
        boolean newU=true;
        if(etemp==null)
        {newU=false;}

        Employees etmp = new Employees(0,0, "","", 0, "",null ) ;
        Users tmp = new Users(0, "", "", "", "", "");
        boolean flag = false;
        try{
            while(!flag){
                if (emp_id.getText().length() > 0 && Long.valueOf(emp_id.getText().length()) >= 0){
                    etmp.setEmployeeNo(Long.parseLong(emp_id.getText()));
                    flag = true;
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    emp_id.clear();
                }
                if (userid.getText().length() > 0 && Long.valueOf(userid.getText().length()) >= 0){
                    etmp.setUserID(Long.parseLong(userid.getText()));
                    flag = true;
                    tmp.setUserID(Long.parseLong(userid.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    userid.clear();
                }if (empFname.getText().length() > 0){
                    flag = true;
                    etmp.setEmployeeFn(empFname.getText());
                    tmp.setFName(empFname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    empFname.clear();
                }
                if (empLname.getText().length() > 0){
                    flag = true;
                    etmp.setEmployeeFn(empLname.getText());
                    tmp.setLName(empLname.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    empLname.clear();
                }
                if (pay.getText().length() > 0){
                    flag = true;
                    etmp.setPayHour(Double.valueOf(pay.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    pay.clear();
                }
                if (empRole.getText().length() > 0){
                    flag = true;
                    etmp.setPosition(empRole.getText());
                    tmp.setRole(empRole.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    empRole.clear();
                }
                if (empUsername.getText().length() > 0){
                    flag = true;
                    tmp.setUsername(empUsername.getText());
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Username", "Every User needs a Username");
                    empUsername.clear();
                }
                if(startDate.getText().length()>0){
                    etmp.setStartDate(java.sql.Date.valueOf(startDate.getText()));
                }
                //default
                String pass="password"+tmp.getLName().substring(0)+tmp.getFName().substring(0)+"!";
                tmp.setPassword(pass);
            }
            if(!newU) {
                Users.addRecord(tmp);
                userT.getItems().add(tmp);
                empT.getItems().add(etmp);
                Employees.addRecord(etmp);
            }
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("User Id Exists", "User ID already exists. User add canceled");
        } catch(Exception p){
            Global.exceptionAlert(p,"Save Employee");
        }
    }
    public void addUserData(){
        userV.setVisible(true);
        userAddB.setVisible(true);
        userModify.setVisible(false);
        userModB.setVisible(false);
    }
    public void addEmpData(){
        empV.setVisible(true);
        empAddB.setVisible(true);
        empModify.setVisible(false);
        empModB.setVisible(false);
    }
    public void showUserData(){
        userV.setVisible(true);
        userModB.setVisible(true);
        userAdd.setVisible(false);
    }
    public void showEmpData(){
        empV.setVisible(true);
        empModB.setVisible(true);
        empAdd.setVisible(false);
    }
    public void hideUserData(){
        userV.setVisible(false);
        userModB.setVisible(false);
        userAddB.setVisible(false);
        userAdd.setVisible(true);
    }
    public void hideEmpData(){
        empV.setVisible(false);
        empModB.setVisible(false);
        empAddB.setVisible(false);
        empAdd.setVisible(true);
    }
    public void deleteUserClicked() throws SQLException {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Users.deleteRecord(utemp.getUserID());
                userT.getItems().remove(userT.getSelectionModel().getSelectedItem());
                utemp=null;
                hideUserData();
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void deleteEmpClicked() throws SQLException {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Users.deleteRecord(etemp.getUserID());
                empT.getItems().remove(empT.getSelectionModel().getSelectedItem());
                etemp=null;
                hideEmpData();
            }catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void modifyDBUser() throws SQLException{
       /* allUsers.get(uIndex).setPassword(password.getText());
        initialize();
        endUserEdit();*/
    }
    @FXML
    private void openHomePage(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
