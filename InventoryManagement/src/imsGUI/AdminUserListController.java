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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

//import java.util.Date;

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
    TableColumn<Users, Long> userID;
    @FXML
    TableColumn<Users, String> uFirstName;
    @FXML
    TableColumn<Users, String> ULastName;
    @FXML
    TableColumn<Users, String> userName;
    @FXML
    TableColumn<Users, String> role;
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
    TableColumn<Employees, Long> empID;
    @FXML
    TableColumn<Employees, Long> empUserID;
    @FXML
    TableColumn<Employees, String> firstName;
    @FXML
    TableColumn<Employees, String> lastName;
    @FXML
    TableColumn<Employees, Double> payHour;
    @FXML
    TableColumn<Employees, Date> dateStart;
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
    DatePicker startDate;
    @FXML
    Button empSave;
    @FXML
    Button empUpdate;
    @FXML
    Button empDelete;

    DatePicker endDate=new DatePicker();
    ArrayList<Users> allUsers;
    ArrayList<Employees> allEmps;
    Users utemp;
    Employees etemp;
    int method=1;
    int uIndex=-1;
    int eIndex=-1;
    public void initialize(){
        setUserTable();
        setEmpTable();
        getSelectedInfo();
        userDetails();
        adminDetails();
    }

    public void setUserTable(){
        try {
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            uFirstName.setCellValueFactory(new PropertyValueFactory<>("fName"));
            ULastName.setCellValueFactory(new PropertyValueFactory<>("lName"));
            userName.setCellValueFactory(new PropertyValueFactory<>("username"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            allUsers = Users.selectAll();
            ObservableList<Users> users = FXCollections.observableArrayList(allUsers);
            userT.setItems(users);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set User Table");
        }
    }
    public void setEmpTable(){
        try {
            empID.setCellValueFactory(new PropertyValueFactory<>("employeeNo"));
            empUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("employeeFn"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("employeeLn"));
            payHour.setCellValueFactory(new PropertyValueFactory<>("position"));
            dateStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            allEmps = Employees.selectAll();
            ObservableList<Employees> emps = FXCollections.observableArrayList(allEmps);
            empT.setItems(emps);
        }
        catch(SQLException e){
            Global.exceptionAlert(e,"Set Employees Table");
        }
    }
    public void getSelectedInfo(){
        userT.setOnMouseClicked(event -> {
            utemp= userT.getSelectionModel().getSelectedItem();
            userModify.setVisible(true);
        });
        empT.setOnMouseClicked(event -> {
            etemp=empT.getSelectionModel().getSelectedItem();
            empModify.setVisible(true);
        });
    }
    public void saveUserClicked() {
        boolean newU=true;
        if(utemp==null) {
            newU=false;
        }
        Users tmp = new Users(0, "", "", "", "", "");
        try{
                if (user_id.getText().length() > 0 && Long.parseLong(user_id.getText()) >= 0)
                    tmp.setUserID(Long.parseLong(user_id.getText()));
                else{
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    user_id.clear();
                    return;
                }
                if (username.getText().length() > 0)
                    tmp.setUsername(username.getText());
                else{
                    Global.warningAlert("Incorrect Username", "Every User needs a Username");
                    username.clear();
                    return;
                }
                if (fname.getText().length() > 0)
                    tmp.setFName(fname.getText());
                else{
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    fname.clear();
                    return;
                }
                if (lname.getText().length() > 0)
                    tmp.setLName(lname.getText());
                else{
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    lname.clear();
                    return;
                }
                if (usrRole.getText().length() > 0)
                    tmp.setRole(usrRole.getText());
                else{
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    usrRole.clear();
                    return;
                }

                //default
                String pass="password"+tmp.getLName().substring(0,2)+tmp.getFName().substring(0,2)+"!";
                tmp.setPassword(pass);
            if(!newU) {
                Users.addRecord(tmp);
                userT.getItems().add(tmp);
            }
        }
        catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("User Id Exists", "User ID already exists. User add canceled");
        }
        catch(Exception p){
            Global.exceptionAlert(p,"Save Employee");
        }
    }
    public void saveEmpClicked(){
        method=1;
        Employees etmp = new Employees(0,0, "","", 0, "",null );
        Users utmp = new Users(0, "", "", "", "", "");
        try{
                if (emp_id.getText().length() > 0 && Long.parseLong(emp_id.getText()) >= 0)
                    etmp.setEmployeeNo(Long.parseLong(emp_id.getText()));
                else{
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    emp_id.clear();
                    return;
                }
                if (userid.getText().length() > 0 && Long.parseLong(userid.getText()) >= 0){
                    etmp.setUserID(Long.parseLong(userid.getText()));
                    utmp.setUserID(Long.parseLong(userid.getText()));
                }
                else{
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    userid.clear();
                    return;
                }
                if (empFname.getText().length() > 0){
                    etmp.setEmployeeFn(empFname.getText());
                    utmp.setFName(empFname.getText());
                }
                else{
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    empFname.clear();
                    return;
                }
                if (empLname.getText().length() > 0){
                    etmp.setEmployeeFn(empLname.getText());
                    utmp.setLName(empLname.getText());
                }
                else{
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    empLname.clear();
                    return;
                }
                if (pay.getText().length() > 0){
                    method--;
                    etmp.setPayHour(Double.parseDouble(pay.getText()));
                }
                if (empRole.getText().length() > 0){
                    etmp.setPosition(empRole.getText());
                    utmp.setRole(empRole.getText());
                }
                else{
                    Global.warningAlert("Incorrect role", "Every Employee needs a role type");
                    empRole.clear();
                    return;
                }
                if (empUsername.getText().length() > 0)
                    utmp.setUsername(empUsername.getText());
                else{
                    Global.warningAlert("Incorrect Username", "Every Employee needs a Username");
                    empUsername.clear();
                    return;
                }
                if(startDate.getValue()!=null)
                    etmp.setStartDate(java.sql.Date.valueOf(startDate.getValue()));
                else{
                    Global.warningAlert("Incorrect Start Date", "Every Employee needs a Start Date ");
                    startDate.setValue(null);
                    return;
                }
                if(endDate.getValue()!=null){
                    method += 2;
                    etmp.setEndDate(java.sql.Date.valueOf(endDate.getValue()));
                }
                //default
                String pass="password"+utmp.getLName().substring(0,2)+utmp.getFName().substring(0,2)+"!";
                utmp.setPassword(pass);
            try{
                    Users.addRecord(utmp);
                    userT.getItems().add(utmp);
            }catch (MySQLIntegrityConstraintViolationException a){
                Global.warningAlert("User Id Exists", "You do not need to Add another User");
            }
            switch (method){
                case 0: Employees.addRecordPay(etmp);
                case 1: Employees.addRecord(etmp);
                case 2: Employees.addRecordPayDate(etmp);
                case 3: Employees.addRecordDate(etmp);
            }
            empT.getItems().add(etmp);
        }catch(MySQLIntegrityConstraintViolationException e){
            Global.exceptionAlert(e,"Add employee");
        }catch(Exception p){
            Global.exceptionAlert(p,"Save Employee");
        }
    }
    public void deleteUserClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Users.deleteRecord(utemp.getUserID());
                userT.getItems().remove(userT.getSelectionModel().getSelectedItem());
                utemp=null;
                hideData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void deleteEmpClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete User");
        deleteAlert.setContentText("Are you sure you want to delete this User?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                ims.Users.deleteRecord(etemp.getUserID());
                empT.getItems().remove(empT.getSelectionModel().getSelectedItem());
                etemp=null;
                hideData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void modifyDBUser(){
        try{
            Users.modifyFName(allUsers.get(uIndex).getUserID(),fname.getText());
            Users.modifyLName(allUsers.get(uIndex).getUserID(),lname.getText());
            Users.modifyRole(allUsers.get(uIndex).getUserID(),usrRole.getText());
            Users.modifyUsername(allUsers.get(uIndex).getUserID(),username.getText());
            initialize();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify Database User");
        }
    }
    public void modifyDBEmp(){
        try{
        Employees.modifyEmployeeFn(allEmps.get(eIndex).getEmployeeNo(),empFname.getText());
        Employees.modifyEmployeeLn(allEmps.get(eIndex).getEmployeeNo(),empLname.getText());
        Employees.modifyUserId(allEmps.get(eIndex).getEmployeeNo(),Long.parseLong(userid.getText()));
        Employees.modifyStartDate(allEmps.get(eIndex).getEmployeeNo(),java.sql.Date.valueOf(startDate.getValue()));
        Employees.modifyPosition(allEmps.get(eIndex).getEmployeeNo(),empRole.getText());
        if(method==0 ||method==2) Employees.modifyPayHour(allEmps.get(eIndex).getEmployeeNo(),Double.parseDouble(pay.getText()));
        if(method==2 || method==3) Employees.modifyEndDate(allEmps.get(eIndex).getEmployeeNo(),java.sql.Date.valueOf(endDate.getValue()));
        initialize();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"modify Database Employee");
        }
        hideData();
    }
    public void userDetails(){
        try{
            userT.setOnMouseClicked(event -> {
                uIndex= userT.getSelectionModel().getSelectedIndex();
                fname.setText(allUsers.get(uIndex).getFName());
                lname.setText(allUsers.get(uIndex).getLName());
                usrRole.setText(allUsers.get(uIndex).getRole());
                username.setText(allUsers.get(uIndex).getUsername());
                user_id.setText(String.valueOf(allUsers.get(uIndex).getUserID()));
                userModify.setVisible(true);
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Show admin details");
        }
    }
    public void adminDetails(){
        try{
            empT.setOnMouseClicked(event -> {
                eIndex= empT.getSelectionModel().getSelectedIndex();
                empFname.setText(allUsers.get(eIndex).getFName());
                empLname.setText(allUsers.get(eIndex).getLName());
                empRole.setText(allUsers.get(eIndex).getRole());
                empUsername.setText(allUsers.get(eIndex).getUsername());
                userid.setText(String.valueOf(allUsers.get(eIndex).getUserID()));
                emp_id.setText(String.valueOf(allEmps.get(eIndex).getEmployeeNo()));
                pay.setText(String.valueOf(allEmps.get(eIndex).getPayHour()));
                empModify.setVisible(true);
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Show admin details");
        }
    }
    public void addUserData(){
        userV.setVisible(true);
        userAddB.setVisible(true);
        userModify.setVisible(false);
        userModB.setVisible(false);
        clearUsrData();
    }
    public void addEmpData(){
        empV.setVisible(true);
        empAddB.setVisible(true);
        empModify.setVisible(false);
        empModB.setVisible(false);

        clearEmpData();
    }
    public void showUserData(){
        userV.setVisible(true);
        userModB.setVisible(true);
        userAdd.setVisible(false);
        userAddB.setVisible(false);
        user_id.setEditable(false);
    }
    public void showEmpData(){
        empV.setVisible(true);
        empModB.setVisible(true);
        empAdd.setVisible(false);
        empV.setVisible(false);
        empAddB.setVisible(false);
        emp_id.setEditable(false);
        empV.getChildren().add(endDate);
    }
    public void hideData(){
        userV.setVisible(false);
        userModB.setVisible(false);
        userAddB.setVisible(false);
        userAdd.setVisible(true);
        empV.setVisible(false);
        empModB.setVisible(false);
        empAddB.setVisible(false);
        empAdd.setVisible(true);
        userModify.setVisible(false);
        empModify.setVisible(false);
        user_id.setEditable(true);
        emp_id.setEditable(true);
        empT.getSelectionModel().clearSelection();
        userT.getSelectionModel().clearSelection();
    }
    public void clearEmpData(){
        empFname.clear();
        empLname.clear();
        empRole.clear();
        empUsername.clear();
        userid.clear();
        emp_id.clear();
        pay.clear();
    }
    public void clearUsrData(){
        fname.clear();
        lname.clear();
        usrRole.clear();
        username.clear();
        user_id.clear();
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
