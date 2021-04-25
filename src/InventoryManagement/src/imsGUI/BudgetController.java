package imsGUI;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import ims.Budget;
import ims.Employees;
import ims.IncomingGoods;
import ims.InvoiceHistory;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class BudgetController {

    @FXML
    Label periodLabel;
    @FXML
    Label outLabel;
    @FXML
    Label inLabel;
    @FXML
    Label empLabel;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    TextField outgoing;
    @FXML
    TextField incoming;
    @FXML
    ComboBox<String> userInfo;
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
    TableColumn<Budget, Long> periodColumn;
    @FXML
    TableColumn<Budget, Date> startColumn;
    @FXML
    TableColumn<Budget, Date> endColumn;
    @FXML
    TableColumn<Budget, Double> incomeColumn;
    @FXML
    TableColumn<Budget, Double> outColumn;
    @FXML
    TableColumn<Budget, Double> netColumn;
    @FXML
    TableColumn<Budget, Long> empColumn;

    @FXML
    TableView<IncomingGoods> spentTable;
    @FXML
    TableColumn<IncomingGoods, Long> incomingID;
    @FXML
    TableColumn<IncomingGoods,Date> dateOut;
    @FXML
    TableColumn<IncomingGoods,Double> productPrice;
    @FXML
    TableColumn<IncomingGoods,Integer> productQuantity;
    @FXML
    TableView<InvoiceHistory> incomeTable;
    @FXML
    TableColumn<InvoiceHistory,Long> orderID;
    @FXML
    TableColumn<InvoiceHistory,Date> dateIn;
    @FXML
    TableColumn<InvoiceHistory,Long> incomeAmmount;


    ArrayList<Budget> allBudget = new ArrayList<>();
    ArrayList<Employees> allEmployees = new ArrayList<>();
    Budget budgetTMP;
    int bIndex = 1;
    long millis=System.currentTimeMillis();
    java.sql.Date startHold = new java.sql.Date(millis);
    java.sql.Date endHold = new java.sql.Date(millis);

    public void initialize(){
        setBudgetList();
        setEmpCombo();
        budgetDetails();
        setInvoiceTabTable();
        clearBoxes();
    }

    public void setBudgetList() {
        try {
            periodColumn.setCellValueFactory(new PropertyValueFactory<>("periodID"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));//the things in the parenthese at the end is the constructor argument name
            endColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
            incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
            outColumn.setCellValueFactory(new PropertyValueFactory<>("outgoing"));
            netColumn.setCellValueFactory(new PropertyValueFactory<>("net"));
            empColumn.setCellValueFactory(new PropertyValueFactory<>("employeeNo"));
            allBudget = Budget.selectAll();
            ObservableList<Budget> budgets = FXCollections.observableArrayList(allBudget);
            budgetTable.setItems(budgets);
        } catch (Exception e) {
            Global.exceptionAlert(e, "Set Budget List");
        }
    }
    public void setInvoiceTabTable(){
        try{
            incomingID.setCellValueFactory(new PropertyValueFactory<>("incomingID"));
            dateOut.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
            productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
            productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            spentTable.setItems(FXCollections.observableArrayList(IncomingGoods.selectAll()));
            orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            dateIn.setCellValueFactory(new PropertyValueFactory<>("dateProcessed"));
            incomeAmmount.setCellValueFactory(new PropertyValueFactory<>("totalCharge"));
            incomeTable.setItems(FXCollections.observableArrayList(InvoiceHistory.selectAll()));
        }catch (Exception e) {
            Global.exceptionAlert(e, "set Invoice Tab Tables");
        }
    }
    public void setEmpCombo(){
        try{
            userInfo.getItems().clear();
            allEmployees = Employees.selectAll();
            for(Employees allEmp : allEmployees){
                String hold = allEmp.getEmployeeNo() + " | " + allEmp.getEmployeeFn() + " " + allEmp.getEmployeeLn();
                userInfo.getItems().add(hold);
            }
        } catch (Exception e){
            Global.exceptionAlert(e, "Setting Budget Employee Combobox");
        }
    }
    public void budgetDetails() {
        try {
            budgetTable.setOnMouseClicked(mouseEvent -> {
                budgetTMP = budgetTable.getSelectionModel().getSelectedItem();
                startDate.setValue(budgetTMP.getDateStart().toLocalDate());
                endDate.setValue(budgetTMP.getDateEnd().toLocalDate());
                try{
                    String n="";
                    if(userInfo.getValue()!=null && n.equals(userInfo.getValue()))
                    userInfo.setValue(allBudget.get(bIndex).getEmployeeNo() + " | " + Employees.selectEmployeeByEmpID(budgetTMP.getEmployeeNo()).getEmployeeFn()+
                            " "+Employees.selectEmployeeByEmpID(budgetTMP.getEmployeeNo()).getEmployeeLn());
                } catch (SQLException throwables) {
                    Global.exceptionAlert(throwables, "Employees for Budget Combobox");
                }
                if(startDate.getValue() != null && endDate.getValue() != null) {
                    outgoing.setText(getSubtractedIncomeInPeriod().toString());
                    incoming.setText(getAddedIncomeInPeriod().toString());
                }
                modifyBudget();
            });
            startDate.setOnAction(event ->{
                if(endDate.getValue()!=null){
                    outgoing.setText(getSubtractedIncomeInPeriod().toString());
                    incoming.setText(getAddedIncomeInPeriod().toString());
                }
            });
            endDate.setOnAction(event ->{
                if(startDate.getValue()!=null){
                    outgoing.setText(getSubtractedIncomeInPeriod().toString());
                    incoming.setText(getAddedIncomeInPeriod().toString());
                }
            });
        } catch (Exception e) {
            Global.exceptionAlert(e, "Show budget details");
        }
    }
    public void saveBudget() {
        boolean hasEmployee = false;
        boolean hasPeriodID = false;
        Budget tmp = new Budget(startHold, endHold, 0, 0, 0);
        try {

            if (startDate.getValue() != null) tmp.setDateStart(java.sql.Date.valueOf((startDate.getValue())));
            else {
                Global.warningAlert("Incorrect Start Date", "Budget start date needs to have a year, month, and day.");
                startDate.setValue(null);
                return;
            }
            if (endDate.getValue() != null && endDate.getValue().compareTo(startDate.getValue())>0)
                tmp.setDateEnd(java.sql.Date.valueOf(endDate.getValue()));
            else {
                Global.warningAlert("Incorrect Start Date", "Budget end date needs to have a year, month, and day. It also needs to be after the Start Date");
                endDate.setValue(null);
                return;
            }
            if(startDate.getValue() != null && endDate.getValue() != null){
                outgoing.setText(getSubtractedIncomeInPeriod().toString());
                incoming.setText(getAddedIncomeInPeriod().toString());
                tmp.setOutgoing(Double.parseDouble(outgoing.getText()));
                tmp.setIncome(Double.parseDouble(incoming.getText()));
            }
            String n="";
            if(userInfo.getValue()!=null && n.equals(userInfo.getValue())) {
                String empSelected = userInfo.getSelectionModel().getSelectedItem();
                String[] hold = empSelected.split(" \\| ");
                tmp.setEmployeeNo(Long.parseLong(hold[0]));
                hasEmployee = true;
            }
            if (hasEmployee && hasPeriodID)
                Budget.addRecordEmpID(tmp);
            else if (hasEmployee) Budget.addRecordEmp(tmp);
            else if (hasPeriodID) Budget.addRecordID(tmp);
            else {
                Budget.addRecord(tmp);
            }
            setBudgetList();
        } catch (MySQLIntegrityConstraintViolationException e) {
            Global.exceptionAlert(e, "Add Budget");
        } catch (Exception p) {
            Global.exceptionAlert(p, "Save employee");
        }
    }
    public Double getSubtractedIncomeInPeriod(){
        double outGoing=0.0;
        try{
            ArrayList<IncomingGoods> orderDate =IncomingGoods.selectAll();
            for(int i=0;i<orderDate.size();i++){
                System.out.println("Outgoing $\nStart Date:"+startDate.getValue()+"End Date:"+endDate.getValue());
                if(startDate.getValue().compareTo(orderDate.get(i).getDateIn().toLocalDate())<0){
                    System.out.println("\tStart Date:"+startDate.getValue());
                    if(endDate.getValue().compareTo(orderDate.get(i).getDateIn().toLocalDate())>0) {
                        outGoing += orderDate.get(i).getProductPrice()*orderDate.get(i).getQuantity();
                    }
                }

            }
        }catch(Exception e){
            Global.exceptionAlert(e, "getting subtractedIncome");
        }
        return outGoing;
    }
    public Double getAddedIncomeInPeriod(){
        Double ingoing=0.0;
        try{
            ArrayList<InvoiceHistory> orderDate =InvoiceHistory.selectAll();
            for (InvoiceHistory invoiceHistory : orderDate) {
                System.out.println(" Incoming $\nStart Date:" + startDate.getValue() + "\nother date: " + invoiceHistory.getDateProcessed().toLocalDate() + "first comparison:" + startDate.getValue().compareTo(invoiceHistory.getDateProcessed().toLocalDate()));
                if (startDate.getValue().compareTo(invoiceHistory.getDateProcessed().toLocalDate()) < 0 || startDate.getValue().compareTo(invoiceHistory.getDateProcessed().toLocalDate()) == 0) {
                    System.out.println("\tIncoming=" + ingoing);
                    if (endDate.getValue().compareTo(invoiceHistory.getDateProcessed().toLocalDate()) > 0 || endDate.getValue().compareTo(invoiceHistory.getDateProcessed().toLocalDate()) == 0) {
                        ingoing += invoiceHistory.getTotalCharge();
                        System.out.println("\tIncoming=" + ingoing);
                    }
                }
            }
        }catch(Exception e){
            Global.exceptionAlert(e, "getting subtractedIncome");
        }
        return ingoing;
    }
    public void modifyDBBudget() {
        try {
            if (startDate.getValue() != null && !(startDate.getValue().equals(budgetTMP.getDateStart().toLocalDate())))
                Budget.modifyDateStart(budgetTMP.getPeriodID(), java.sql.Date.valueOf(startDate.getValue()));
            if (endDate.getValue() != null && endDate.getValue().compareTo(startDate.getValue())>0 && !(endDate.getValue().equals(budgetTMP.getDateEnd().toLocalDate())))
                Budget.modifyDateEnd(budgetTMP.getPeriodID(), java.sql.Date.valueOf(endDate.getValue()));
            else {
                Global.warningAlert("End Date", "The End Date has to be after the start Date");
                return;
            }
            Budget.modifyOutgoing(budgetTMP.getPeriodID(), Double.parseDouble(outgoing.getText()));
            Budget.modifyIncome(budgetTMP.getPeriodID(), Double.parseDouble(incoming.getText()));

            if (userInfo.getValue() != null){
                String empSelected = userInfo.getSelectionModel().getSelectedItem();
                String[] hold = empSelected.split(" \\| ");
                if (Long.parseLong(hold[0]) != budgetTMP.getEmployeeNo()) Budget.modifyEmployeeNo(budgetTMP.getPeriodID(), Long.parseLong(hold[0]));
            }
            initialize();
        }
        catch (Exception e) {
            Global.exceptionAlert(e, "Modify Budget");
        }
        endBudgetEdit();
    }
    public void deleteDBBudget() {
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
        delete.setTitle("Delete");
        delete.setHeaderText("Delete Budget");
        delete.setContentText("Are you sure you want to delete this Budget?");
        try {
            if (delete.showAndWait().get() == ButtonType.OK) {
                ims.Budget.deleteRecord(budgetTMP.getPeriodID());
                budgetTable.getItems().remove(budgetTMP);
                budgetTMP = null;
                initialize();
                hideInfo();
                clearBoxes();
                endBudgetEdit();
            }
        } catch (Exception e) {
            Global.exceptionAlert(e, "Delete Budget");
        }
    }

    public void modifyBudget() {
        outgoing.setEditable(true);
        incoming.setEditable(true);
        userInfo.setEditable(true);
        modifyBtn.setVisible(true);
        deleteBtn.setVisible(true);
        addBtn.setVisible(true);
        homeBtn.setVisible(true);
        loginBtn.setVisible(true);
    }
    public void endBudgetEdit() {
        incoming.setEditable(false);
        outgoing.setEditable(false);
        modifyBtn.setVisible(true);
        addBtn.setVisible(true);
        clearBoxes();
    }
    public void clearBoxes(){
        startDate.setValue(startHold.toLocalDate());
        System.out.println(startHold.toLocalDate());
        endDate.setValue(endHold.toLocalDate());
        incoming.clear();
        outgoing.clear();
    }
    public void hideInfo() {
        startDate.setEditable(false);
        endDate.setEditable(false);
        userInfo.setEditable(false);
        incoming.setEditable(false);
        outgoing.setEditable(false);
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