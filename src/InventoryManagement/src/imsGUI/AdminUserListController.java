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
    HBox userAddHBox;
    @FXML
    HBox modUHBox;
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
    TextField usrFName;
    @FXML
    TextField usrLName;
    @FXML
    TextField uUsername;
    @FXML
    TextField uUID;
    @FXML
    ComboBox<String> usrRoleC;
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
    TextField EID;
    @FXML
    ComboBox<String> empUIDC;
    @FXML
    ComboBox<String> empRoleC;
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
    @FXML
    Label endDateLabel;
    @FXML
    DatePicker endDate;

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
        setCombos();
        getSelectedInfo();
        clearUsrData();
        clearEmpData();
        hideData();
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
        try{
            userT.setOnMouseClicked(event -> {
                utemp= userT.getSelectionModel().getSelectedItem();
                uIndex= userT.getSelectionModel().getSelectedIndex();
                usrFName.setText(allUsers.get(uIndex).getFName());
                usrLName.setText(allUsers.get(uIndex).getLName());
                usrRoleC.setValue(allUsers.get(uIndex).getRole());
                uUsername.setText(allUsers.get(uIndex).getUsername());
                uUID.setText(String.valueOf(allUsers.get(uIndex).getUserID()));
                userModify.setVisible(true);
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Show User Admin details");
        }
        try{
            empT.setOnMouseClicked(event -> {
                etemp=empT.getSelectionModel().getSelectedItem();
                eIndex= empT.getSelectionModel().getSelectedIndex();
                empFname.setText(etemp.getEmployeeFn());
                empLname.setText(etemp.getEmployeeLn());
                empRoleC.setValue(allEmps.get(eIndex).getPosition());
                empUIDC.setValue(allUsers.get(eIndex).getUserID() +" | "+ etemp.getEmployeeFn() + " " + etemp.getEmployeeLn());
                EID.setText(String.valueOf(allEmps.get(eIndex).getEmployeeNo()));
                startDate.setValue(allEmps.get(eIndex).getStartDate().toLocalDate());
                if(allEmps.get(eIndex).getEndDate()!=null)
                    endDate.setValue(allEmps.get(eIndex).getEndDate().toLocalDate());
                pay.setText(String.valueOf(allEmps.get(eIndex).getPayHour()));
                empModify.setVisible(true);
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Show Employee Admin details");
        }

        empUIDC.setOnAction(event -> {
            if(empUIDC.getValue()!=null) {
                String selectedItem = empUIDC.getSelectionModel().getSelectedItem();
                String[] s = selectedItem.split(" \\| ");
                String[] name = s[1].split(" ");
                empFname.setText(name[0]);
                empLname.setText(name[1]);
            }
        });
    }
    public void setCombos(){
        empUIDC.getItems().clear();
        usrRoleC.getItems().clear();
        empRoleC.getItems().clear();
        try{
            allUsers= Users.selectAll();
            ArrayList<String> fname = Users.selectFName();
            ArrayList<String> lname = Users.selectLName();
            for (int i=0; i<allUsers.size();i++) {
                String s = allUsers.get(i).getUserID() + " | " + fname.get(i)+" "+lname.get(i);
                empUIDC.getItems().add(s);
            }
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Set Product Combo");
        }
        usrRoleC.getItems().add("User");
        usrRoleC.getItems().add("Customer");
        empRoleC.getItems().add("Clerk");
        empRoleC.getItems().add("Financier");
        empRoleC.getItems().add("Assistant Manager");
        empRoleC.getItems().add("Manager");
        empRoleC.getItems().add("IT");
        empRoleC.getItems().add("Admin");
    }
    public void saveUserClicked() {
        boolean newU=true;
        if(utemp==null) {
            newU=false;
        }
        Users tmp = new Users(0, "", "", "", "", "");
        try{
                if (uUID.getText().length() > 0 && Long.parseLong(uUID.getText()) >= 0)
                    tmp.setUserID(Long.parseLong(uUID.getText()));
                else{
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9");
                    uUID.clear();
                    return;
                }
                if (uUsername.getText().length() > 0)
                    tmp.setUsername(uUsername.getText());
                else{
                    Global.warningAlert("Incorrect Username", "Every User needs a Username");
                    uUsername.clear();
                    return;
                }
                if (usrFName.getText().length() > 0)
                    tmp.setFName(usrFName.getText());
                else{
                    Global.warningAlert("Incorrect first name", "Every User needs a first name");
                    usrFName.clear();
                    return;
                }
                if (usrLName.getText().length() > 0)
                    tmp.setLName(usrLName.getText());
                else{
                    Global.warningAlert("Incorrect last name", "Every User needs a last name");
                    usrLName.clear();
                    return;
                }
                if (usrRoleC.getValue()!=null)
                    tmp.setRole(usrRoleC.getValue());
                else{
                    Global.warningAlert("Incorrect role", "Every User needs a role type");
                    usrRoleC.setValue(null);
                    return;
                }

                //default
                String pass="password"+tmp.getLName().substring(0,2)+tmp.getFName().substring(0,2)+"!";
                tmp.setPassword(pass);
            if(!newU) {
                Users.addRecord(tmp);
                userT.getItems().add(tmp);
            }
            setUserTable();
            setCombos();
            clearUsrData();
            hideData();
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
                String n="";
                if (empUIDC.getValue()!=null ){
                    String selectedItem = empUIDC.getSelectionModel().getSelectedItem();
                    String[] s = selectedItem.split(" \\| ");
                    etmp.setUserID(Long.parseLong(s[0]));
                }
                else{
                    Global.warningAlert("Incorrect ID", "Please Select a User");
                    empUIDC.setValue(null);
                    return;
                }
                etmp.setEmployeeFn(empFname.getText());
                etmp.setEmployeeLn(empLname.getText());
                if (EID.getText().length() > 0 && Long.parseLong(EID.getText()) >= 0)
                    etmp.setEmployeeNo(Long.parseLong(EID.getText()));
                else{
                    Global.warningAlert("Incorrect ID", "User ID needs to be greater than 0 and less than 9 digits");
                    EID.clear();
                    return;
                }
                if (pay.getText().length() > 0){
                    method--;
                    etmp.setPayHour(Double.parseDouble(pay.getText()));
                }
                if (empRoleC.getValue()!=null){
                    etmp.setPosition(empRoleC.getValue());
                    Users.modifyRole(etmp.getUserID(),empRoleC.getValue());
                }
                else{
                    Global.warningAlert("Incorrect role", "Every Employee needs a role type");
                    empRoleC.setValue(null);
                    return;
                }
                if(startDate.getValue()!=null)
                    etmp.setStartDate(java.sql.Date.valueOf(startDate.getValue()));
                else{
                    Global.warningAlert("Incorrect Start Date", "Every Employee needs a Start Date ");
                    startDate.setValue(null);
                    return;
                }

            switch (method){
                case 1: Employees.addRecord(etmp);
                case 0: Employees.addRecordPay(etmp);
            }
            empT.getItems().add(etmp);
            setUserTable();
            setEmpTable();
            clearEmpData();
            hideData();
        }catch(MySQLIntegrityConstraintViolationException e){
            Global.exceptionAlert(e,"Add employee");
        }catch(Exception p){
            Global.exceptionAlert(p,"Save Employee");
        }
    }
    public void modifyDBUser(){
        try{
            Users.modifyFName(allUsers.get(uIndex).getUserID(), usrFName.getText());
            Users.modifyLName(allUsers.get(uIndex).getUserID(),usrLName.getText());
            Users.modifyRole(allUsers.get(uIndex).getUserID(), usrRoleC.getValue());
            Users.modifyUsername(allUsers.get(uIndex).getUserID(), uUsername.getText());
            initialize();
            clearUsrData();
            hideData();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"Modify Database User");
        }
    }
    public void modifyDBEmp(){
        try{
            Employees.modifyEmployeeFn(allEmps.get(eIndex).getEmployeeNo(),empFname.getText());
            Employees.modifyEmployeeLn(allEmps.get(eIndex).getEmployeeNo(),empLname.getText());
            if(empUIDC.getValue()!=null) {
                String selectedItem = empUIDC.getSelectionModel().getSelectedItem();
                String[] s = selectedItem.split(" \\| ");
                Employees.modifyUserId(allEmps.get(eIndex).getEmployeeNo(), Long.parseLong(s[0]));
            }
            Employees.modifyStartDate(allEmps.get(eIndex).getEmployeeNo(),java.sql.Date.valueOf(startDate.getValue()));
            if(empRoleC.getValue()!=null) {
                Employees.modifyPosition(allEmps.get(eIndex).getEmployeeNo(), empRoleC.getValue());
                Users.modifyRole(allEmps.get(eIndex).getUserID(),empRoleC.getValue());
            }
            if(pay.getText().length()>0) Employees.modifyPayHour(allEmps.get(eIndex).getEmployeeNo(),Double.parseDouble(pay.getText()));
            if(endDate.getValue()!=null) Employees.modifyEndDate(allEmps.get(eIndex).getEmployeeNo(),java.sql.Date.valueOf(endDate.getValue()));
            initialize();
            clearEmpData();
            hideData();
        }
        catch(Exception e){
            Global.exceptionAlert(e,"modify Database Employee");
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
                setUserTable();
                clearUsrData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete User");
            }
        }
    }
    public void deleteEmpClicked() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete");
        deleteAlert.setHeaderText("Delete Employee");
        deleteAlert.setContentText("Are you sure you want to delete this Employee?");
        if(deleteAlert.showAndWait().get() == ButtonType.OK){
            try{
                Users.modifyRole(etemp.getUserID(),"Employee");
                Employees.deleteRecord(etemp.getEmployeeNo());
                empT.getItems().remove(empT.getSelectionModel().getSelectedItem());
                etemp=null;
                hideData();
                setEmpTable();
                clearEmpData();
            }
            catch(Exception e){
                Global.exceptionAlert(e,"Delete Employee");
            }
        }
    }
    public void addUserData(){
        userV.setVisible(true);
        userAddHBox.setVisible(true);
        userModify.setVisible(false);
        modUHBox.setVisible(false);
        clearUsrData();
    }
    public void addEmpData(){
        empV.setVisible(true);
        empAddB.setVisible(true);
        empModify.setVisible(false);
        empModB.setVisible(false);
        clearEmpData();
    }
    public void clearUsrData(){
        usrFName.clear();
        usrLName.clear();
        usrRoleC.setValue(null);
        uUsername.clear();
        uUID.clear();
    }
    public void clearEmpData(){
        empFname.clear();
        empLname.clear();
        empUIDC.setValue(null);
        EID.clear();
        pay.clear();
        empUIDC.setValue(null);
        empRoleC.setValue(null);
        startDate.setValue(null);
        endDate.setValue(null);
    }
    public void modUserData(){
        userV.setVisible(true);
        modUHBox.setVisible(true);
        userAdd.setVisible(false);
        userAddHBox.setVisible(false);
        uUID.setEditable(false);
    }
    public void modEmpData(){
        empV.setVisible(true);
        empModB.setVisible(true);
        empAdd.setVisible(false);
        EID.setEditable(false);
        endDateLabel.setVisible(true);
        endDate.setVisible(true);
    }
    public void hideData(){
        userV.setVisible(false);
        modUHBox.setVisible(false);
        userAddHBox.setVisible(false);
        userAdd.setVisible(true);
        empV.setVisible(false);
        empModB.setVisible(false);
        empAddB.setVisible(false);
        empAdd.setVisible(true);
        userModify.setVisible(false);
        empModify.setVisible(false);
        uUID.setEditable(true);
        EID.setEditable(true);
        endDateLabel.setVisible(false);
        endDate.setVisible(false);
        empT.getSelectionModel().clearSelection();
        userT.getSelectionModel().clearSelection();
    }

    @FXML
    private void openHomePage(ActionEvent event) {
        clearUsrData();
        clearEmpData();
        hideData();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getHomepageScene());
        stage.show();
    }
    @FXML
    private void openLogin(ActionEvent event) {
        clearUsrData();
        clearEmpData();
        hideData();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(ImsGui.global.getLoginScene());
        stage.show();
    }
}
