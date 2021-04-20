package imsGUI;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import ims.Budget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BudgetController {

    @FXML
    Label periodLabel;
    @FXML
    Label netLabel;
    @FXML
    Label outLabel;
    @FXML
    Label inLabel;
    @FXML
    Label empLabel;
    @FXML
    Label transactionsLabel;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    TextField netProfit;
    @FXML
    TextField outgoing;
    @FXML
    TextField incoming;
    @FXML
    TextField userInfo;
    @FXML
    TextField budgetID;
    @FXML
    Button addBtn;
    @FXML
    Button modifyBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button homeBtn;
    @FXML
    Button loginBtn;
    @FXML
    TableView<Budget> budgetTable;
    @FXML
    TableColumn periodTable;
    @FXML
    TableColumn startTable;
    @FXML
    TableColumn endTable;
    @FXML
    TableColumn incomeTable;
    @FXML
    TableColumn outTable;
    @FXML
    TableColumn netTable;
    @FXML
    TableColumn empTable;

    DatePicker endD = new DatePicker();
    DatePicker startD = new DatePicker();
    ArrayList<Budget> allBudget = new ArrayList<Budget>();
    Budget budgetTMP;
    int bIndex = 1;

    Date startHold = new Date(2021-01-01);
    Date endHold = new Date(2021-01-02);

    DateFormat dfStart = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dfEnd = new SimpleDateFormat("yyyy-MM-dd");


    public void initialize() {
        setBudgetList();
        budgetDetails();
        getSelectedInfo();
    }

    public void setBudgetList() {
        try {
            periodTable.setCellValueFactory(new PropertyValueFactory<Budget, Long>("periodID"));
            startTable.setCellValueFactory(new PropertyValueFactory<Budget, Date>("dateStart"));//the things in the parenthese at the end is the constructor argument name
            endTable.setCellValueFactory(new PropertyValueFactory<Budget, Date>("dateEnd"));
            incomeTable.setCellValueFactory(new PropertyValueFactory<Budget, Double>("income"));
            outTable.setCellValueFactory(new PropertyValueFactory<Budget, Double>("outgoing"));
            netTable.setCellValueFactory(new PropertyValueFactory<Budget, Double>("net"));
            empTable.setCellValueFactory(new PropertyValueFactory<Budget, Long>("employeeNo"));
            allBudget = Budget.selectAll();
            ObservableList<Budget> budgets = FXCollections.observableArrayList(allBudget);
            budgetTable.setItems(budgets);
        }
        catch (Exception e) {
            Global.exceptionAlert(e, "Set Budget List");
        }
    }

    public void getSelectedInfo() {
        try {
            budgetTable.setOnMouseClicked(event -> {
                budgetTMP = budgetTable.getSelectionModel().getSelectedItem();
            });
        }
        catch (Exception e) {
            Global.exceptionAlert(e, "Show Budget Details");
        }
    }

    public void saveBudget(ActionEvent event) {
        boolean newB = true;
        boolean hasEmployee = false;
        boolean hasPeriodID = false;
        if (budgetTMP == null) newB = false;
        Budget tmp = new Budget(0, startHold, endHold, 0, 0, 0, 0);
        try {
            if (budgetID.getText().length() > 0) {
                tmp.setPeriodID(Long.parseLong(budgetID.getText()));
                hasPeriodID = true;
            }
            if (startDate.getValue() != null) tmp.setDateStart(java.sql.Date.valueOf((startDate.getValue())));
            else {
                Global.warningAlert("Incorrect Start Date", "Budget start date needs to have a year, month, and day.");
                startDate.setValue(null);
                return;
            }
            if (endDate.getValue() != null) tmp.setDateEnd(java.sql.Date.valueOf(endDate.getValue()));
            else {
                Global.warningAlert("Incorrect Start Date", "Budget end date needs to have a year, month, and day.");
                endDate.setValue(null);
                return;
            }
            if (outgoing.getText().length() > 0) tmp.setOutgoing(Double.parseDouble(outgoing.getText()));
            else {
                Global.warningAlert("Incorrect Outgoing Total", "Budget needs an outgoing total.");
                outgoing.clear();
                return;
            }
            if (incoming.getText().length() > 0) tmp.setIncome(Double.parseDouble(incoming.getText()));
            else {
                Global.warningAlert("Incorrect Incoming Total", "Budget needs an incoming total.");
                incoming.clear();
                return;
            }
            if (netProfit.getText().length() > 0) tmp.setNet(Double.parseDouble(netProfit.getText()));
            else {
                Global.warningAlert("Incorrect Net Income", "Budget needs a net income.");
                netProfit.clear();
                return;
            }
            if (userInfo.getText().length() > 0) {
                tmp.setEmployeeNo(Long.parseLong(userInfo.getText()));
                hasEmployee = true;
            }
            try {
                if (!newB) Budget.addRecord(tmp);
            }
            catch (MySQLIntegrityConstraintViolationException e) {
                Global.warningAlert("Budget ID Exists", "Budget ID is already in use. Budget add canceled.");
            }
            if (hasEmployee && hasPeriodID) Budget.addRecordEmpID(tmp);
            else if (hasEmployee) Budget.addRecordEmp(tmp);
            else if (hasPeriodID) Budget.addRecordID(tmp);
            else {Budget.addRecord(tmp);}
            setBudgetList();
        }
        catch (MySQLIntegrityConstraintViolationException e) {
            Global.exceptionAlert(e, "Add Budget");
        }
        catch (Exception p) {
            Global.exceptionAlert(p, "Save employee");
        }
    }

    public void startBudget(ActionEvent event) {
        startDate.setEditable(true);
        endDate.setEditable(true);
        netProfit.setEditable(true);
        outgoing.setEditable(true);
        incoming.setEditable(true);
        userInfo.setEditable(true);
        modifyBtn.setVisible(true);
        deleteBtn.setVisible(true);
        addBtn.setVisible(true);
        homeBtn.setVisible(true);
        loginBtn.setVisible(true);
    }

    public void budgetDetails(){
        try{
            budgetTable.setOnMouseClicked(mouseEvent -> {
                bIndex= budgetTable.getSelectionModel().getSelectedIndex();
                budgetID.setText(String.valueOf(allBudget.get(bIndex).getPeriodID()));
                incoming.setText(String.valueOf(allBudget.get(bIndex).getIncome()));
                outgoing.setText(String.valueOf(allBudget.get(bIndex).getOutgoing()));
                netProfit.setText(String.valueOf(allBudget.get(bIndex).getNet()));
                userInfo.setText(String.valueOf(allBudget.get(bIndex).getEmployeeNo()));
            });
        }
        catch (Exception e){
            Global.exceptionAlert(e, "Show budget details");
        }
    }

    public void endBudgetEdit() {
        startDate.setEditable(false);
        endDate.setEditable(false);
        userInfo.setEditable(false);
        incoming.setEditable(false);
        outgoing.setEditable(false);
        budgetID.setEditable(false);
        netProfit.setEditable(false);
        modifyBtn.setVisible(true);
        addBtn.setVisible(true);
    }

    public void modifyDBBudget(){
        try{
            Budget.modifyDateStart(budgetTMP.getDateStart(), budgetTMP.getDateStart());
            Budget.modifyDateEnd(budgetTMP.getDateStart(), budgetTMP.getDateEnd());
            Budget.modifyOutgoing(budgetTMP.getDateStart(), budgetTMP.getOutgoing());
            Budget.modifyIncome(budgetTMP.getDateStart(), budgetTMP.getIncome());
            Budget.modifyEmployeeNo(budgetTMP.getDateStart(), budgetTMP.getEmployeeNo());
            initialize();
        } catch (Exception e){
            Global.exceptionAlert(e, "Modify Budget");
        }
        endBudgetEdit();
    }

    public void modifyBudget() {
        startDate.setEditable(true);
        endDate.setEditable(true);
        netProfit.setEditable(true);
        outgoing.setEditable(true);
        incoming.setEditable(true);
        userInfo.setEditable(true);
        modifyBtn.setVisible(true);
        deleteBtn.setVisible(true);
        addBtn.setVisible(true);
        homeBtn.setVisible(true);
        loginBtn.setVisible(true);
    }

    public void deleteDBBudget() {
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
        delete.setTitle("Delete");
        delete.setHeaderText("Delete Budget");
        delete.setContentText("Are you sure you want to delete this Budget?");
        try {
            if (delete.showAndWait().get() == ButtonType.OK) {
                ims.Budget.deleteRecord(allBudget.get(bIndex).getDateStart());
                budgetTable.getItems().remove(budgetTable.getSelectionModel().getSelectedItems());
                budgetTMP=null;
                hideInfo();
                endBudgetEdit();
                initialize();
            }
        }
        catch (Exception e) {
            Global.exceptionAlert(e, "Delete Budget");
        }
    }

    public void hideInfo(){
        startDate.setEditable(false);
        endDate.setEditable(false);
        userInfo.setEditable(false);
        incoming.setEditable(false);
        outgoing.setEditable(false);
        budgetID.setEditable(false);
        netProfit.setEditable(false);
        modifyBtn.setVisible(true);
        addBtn.setVisible(true);
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