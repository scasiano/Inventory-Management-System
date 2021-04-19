package imsGUI;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import ims.Budget;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Array;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    TextField startDate;
    @FXML
    TextField endDate;
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
    HBox periodL;
    @FXML
    HBox transactionList;


    TableView<Long> periodTable;
    TableView<Date> startTable;
    ArrayList<Budget> allBudget = new ArrayList<Budget>();
    ObservableList<Long> bID;
    VBox idBox = new VBox();
    ObservableList<Date> bDate;
    VBox dateBox = new VBox();
    Label periodName = new Label();
    Label idNum = new Label();
    int bIndex = -1;

    DateFormat dfStart = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dfEnd = new SimpleDateFormat("yyyy-MM-dd");


    public void initialize() throws SQLException {
        setBudgetList();
        budgetDetails();
        if (Global.privilege) {
            addBtn.setVisible(true);
        } else {
            addBtn.setVisible(false);
        }
    }

    public void setBudgetList() {
        ArrayList<Long> periodList = new ArrayList<>();
        ArrayList<Date> dateList = new ArrayList<>();
       // periodName.setText("Start Date");
        //idNum.setText("Budget ID");
        idBox.getChildren().add(idNum);
        dateBox.getChildren().add(periodName);
        try {
            allBudget = Budget.selectAll();
            for (int i = 0; i < allBudget.size(); i++) {
                periodList.add(allBudget.get(i).getPeriodID());
                dateList.add(allBudget.get(i).getDateStart());
            }
            bID = FXCollections.observableArrayList(periodList);
            bDate = FXCollections.observableArrayList(dateList);
            startTable = new TableView<Date>();
            dateBox.getChildren().add(startTable);
            periodTable = new TableView<Long>();
            idBox.getChildren().add(periodTable);
            periodL.getChildren().addAll(idBox, dateBox);
        } catch (Exception e) {
            Global.exceptionAlert(e, "Set Budget List");
        }
       // modifyBtn.setVisibile(false);
    }

    public void budgetDetails() {
        try {
            startTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Integer index = startTable.getSelectionModel().getSelectedIndex();
                    bIndex = index;
                    periodTable.getSelectionModel().select(index);
                    String startText = dfStart.format((allBudget.get(index).getDateStart()));
                    startDate.setText(startText);
                    String endText = dfEnd.format((allBudget.get(index).getDateStart()));
                    endDate.setText(endText);
                    netProfit.setText("$" + String.valueOf(allBudget.get(index).getNet()));
                    outgoing.setText("$" + String.valueOf(allBudget.get(index).getOutgoing()));
                    incoming.setText("$" + String.valueOf(allBudget.get(index).getIncome()));
                    userInfo.setText(String.valueOf(allBudget.get(index).getEmployeeNo()));
                    budgetID.setText(String.valueOf(allBudget.get(index).getPeriodID()));
                    if (Global.privilege) {
                        modifyBtn.setVisible(true);
                    }
                }
            });
            periodTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Integer index = startTable.getSelectionModel().getSelectedIndex();
                    bIndex = index;
                    periodTable.getSelectionModel().select(index);
                    String startText = dfStart.format((allBudget.get(index).getDateStart()));
                    startDate.setText(startText);
                    String endText = dfEnd.format((allBudget.get(index).getDateStart()));
                    endDate.setText(endText);
                    netProfit.setText("$" + String.valueOf(allBudget.get(index).getNet()));
                    outgoing.setText("$" + String.valueOf(allBudget.get(index).getOutgoing()));
                    incoming.setText("$" + String.valueOf(allBudget.get(index).getIncome()));
                    userInfo.setText(String.valueOf(allBudget.get(index).getEmployeeNo()));
                    budgetID.setText(String.valueOf(allBudget.get(index).getPeriodID()));
                    if (Global.privilege) {
                        modifyBtn.setVisible(true);
                    }
                }
            });
        } catch (Exception e){
            Global.exceptionAlert(e, "Show Budget Details");
        }
    }

    public void  startBudget(ActionEvent event){
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
        clearBudgetInfo();
    }

    public void addDBBudget(ActionEvent event) throws SQLException {
        java.sql.Date dateTMPStart = new java.sql.Date(2000, 01, 01);
        java.sql.Date dateTMPEnd = new java.sql.Date(2000, 01, 02);
        Budget tmp = new Budget(0,dateTMPStart, dateTMPEnd, 0, 0, 0, 0);
        boolean flag = false;
        try{
            while(!flag){
                if(startDate.getText().length() > 0 && startDate.getText().length() <= 8){
                    flag = true;
                    tmp.setDateStart(java.sql.Date.valueOf(startDate.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect Start Date", "Budget start date needs have a year, month, and day.");
                    startDate.clear();
                }
                if(endDate.getText().length() > 0 && endDate.getText().length() <= 8){
                    flag = true;
                    tmp.setDateStart(java.sql.Date.valueOf(endDate.getText()));
                } else{
                    flag = false;
                    Global.warningAlert("Incorrect End Date", "Budget end date needs have a year, month, and day.");
                    endDate.clear();
                }
                if(userInfo.getText().length() > 0){
                    flag = true;
                    tmp.setEmployeeNo(Long.valueOf(userInfo.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Employee Number", "Budget needs an employee number.");
                    userInfo.clear();
                }
                if(netProfit.getText().length() > 0){
                    flag = true;
                    tmp.setIncome(Double.valueOf(netProfit.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Net Income", "Budget needs a net income.");
                    netProfit.clear();
                }
                if(incoming.getText().length() > 0){
                    flag = true;
                    tmp.setIncome(Double.valueOf(incoming.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Incoming Total", "Budget needs an incoming total.");
                    incoming.clear();
                }
                if(outgoing.getText().length() > 0){
                    flag = true;
                    tmp.setIncome(Double.valueOf(outgoing.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Outgoing Total", "Budget needs an outgoing total.");
                    outgoing.clear();
                }
                if(budgetID.getText().length() > 0){
                    flag = true;
                    tmp.setIncome(Long.valueOf(budgetID.getText()));
                }
                else{
                    flag = false;
                    Global.warningAlert("Incorrect Period ID", "Budget needs a period id.");
                    budgetID.clear();
                }
            }
            Budget.addRecord(tmp);
        } catch (MySQLIntegrityConstraintViolationException e){
            Global.warningAlert("Budget ID Exists", "Budget ID already exists. Budget Add Canceled.");
        }
        endBudgetEdit();
        clearBudgetList();
        initialize();
        clearBudgetInfo();
    }

    public void clearBudgetList(){
        periodTable.getItems().clear();
        startTable.getItems().clear();
        dateBox.getChildren().clear();
        idBox.getChildren().clear();
        periodL.getChildren().clear();
    }

    public void clearBudgetInfo(){
        startDate.clear();
        endDate.clear();
        userInfo.clear();
        incoming.clear();
        outgoing.clear();
        budgetID.clear();
        netProfit.clear();
    }

    public void endBudgetEdit(){
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

    /*public void modifyDBBudget(){
        java.sql.Date tmpDateEnd = allBudget.get(bIndex).getDateEnd();
        java.sql.Date tmpDateStart = allBudget.get(bIndex).getDateStart();
        try{
            ims.Budget.modifyDateEnd(allBudget.get(bIndex).getPeriodID(), tmpDateEnd);
            ims.Budget.modifyDateStart(allBudget.get(bIndex).getPeriodID(), tmpDateStart);
            ims.Budget.modifyIncome(allBudget.get(bIndex).getPeriodID(), Double.parseDouble(incoming.getText()));
            ims.Budget.modifyOutgoing(allBudget.get(bIndex).getPeriodID(), Double.valueOf(outgoing.getText()));
            ims.Budget.modifyEmployeeNo(allBudget.get(bIndex).getPeriodID(), Long.valueOf(userInfo.getText()));
        } catch (Exception e){
            Global.exceptionAlert(e, "Modify Budget");
        }
        endBudgetEdit();
    }*/

    public void modifyBudget(){
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
        clearBudgetInfo();
    }

    public void deleteDBBudget(){
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
        delete.setTitle("Delete");
        delete.setHeaderText("Delete Budget");
        delete.setContentText("Are you sure you want to delete this Budget?");
        try{
           if(delete.showAndWait().get() == ButtonType.OK){
               ims.Budget.deleteRecord(allBudget.get(bIndex).getDateStart());
               endBudgetEdit();
               clearBudgetList();
               initialize();
               clearBudgetInfo();
           }
        } catch (Exception e){
            Global.exceptionAlert(e, "Delete Budget");
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

