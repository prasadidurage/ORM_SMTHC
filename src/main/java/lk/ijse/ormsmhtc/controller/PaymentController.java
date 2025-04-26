package lk.ijse.ormsmhtc.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.ormsmhtc.bo.BOFactory;
import lk.ijse.ormsmhtc.bo.custom.impl.PatientBOImpl;
import lk.ijse.ormsmhtc.bo.custom.impl.PaymentBOImpl;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.ormsmhtc.dto.PaymentDto;
import lk.ijse.ormsmhtc.dto.tm.PaymentTM;
import lk.ijse.ormsmhtc.util.Validation;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbSessionId;

    @FXML
    private TableColumn<Double, PaymentTM> colAmmount;

    @FXML
    private TableColumn<Double, PaymentTM> colBalance;

    @FXML
    private TableColumn<Date, PaymentTM> colDate;

    @FXML
    private TableColumn<String, PaymentTM> colInstallment;

    @FXML
    private TableColumn<String, PaymentTM> colPatientId;

    @FXML
    private TableColumn<String, PaymentTM> colPaymentId;

    @FXML
    private TableColumn<String, PaymentTM> colProgramId;

    @FXML
    private TableColumn<String, PaymentTM> colSessionId;

    @FXML
    private TableColumn<String, PaymentTM> colStatus;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<PaymentTM> tablePatient;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtInstallment;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtStatus;

    @FXML
    private Pane userPane;

    PatientBOImpl patientBO = (PatientBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void deletePayment(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Payment?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = paymentBO.deleteSession(paymentId);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Payment Deleted successfully").show();
                refreshPage();
            }else{
                new Alert(Alert.AlertType.WARNING,"Payment is not Delete").show();
            }
        }
    }

    @FXML
    void donePayment(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String sessionId = cmbSessionId.getSelectionModel().getSelectedItem();
        double amount = Double.parseDouble(txtAmount.getText());
        double balance = Double.parseDouble(txtBalance.getText());
        String installment = txtInstallment.getText();
        String status = txtStatus.getText();
        Date date = Date.valueOf(datePicker.getValue());
        boolean isValidAmount = Validation.isValid(String.valueOf(amount),"price");
        boolean isValidBalance = Validation.isValid(String.valueOf(balance),"price");
        if (!isValidAmount){
            txtAmount.setStyle("-fx-border-color: red");
        }else {
            txtAmount.setStyle("-fx-border-color: black");
        }
        if (!isValidBalance){
            txtBalance.setStyle("-fx-border-color: red");
        }else {
            txtBalance.setStyle("-fx-border-color: black");
        }

        if (isValidAmount && isValidBalance && !paymentId.isEmpty() && !programId.isEmpty() && !patientId.isEmpty() && !installment.isEmpty() && !status.isEmpty()) {
            PaymentDto paymentDto = new PaymentDto(
                    paymentId,
                    amount,
                    installment,
                    status,
                    balance,
                    date,
                    patientId,
                    programId,
                    sessionId
            );
            boolean isSave = paymentBO.savePayment(paymentDto);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Done Successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Payment Done Not Successfully").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Or null input").show();
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void setBalance(KeyEvent event) {
        try {
            String programID = cmbProgramId.getSelectionModel().getSelectedItem();
            String patientID = cmbPatientId.getSelectionModel().getSelectedItem();
            if (programID == null || patientID == null) {
                programID = cmbProgramId.getValue();
                patientID = cmbPatientId.getValue();
            }
            double fee = paymentBO.getBalance(programID,patientID);
            if ( String.valueOf(fee).isEmpty()){
                fee = therapyProgramBO.getFee(programID);
            }
            System.out.println(fee);
            double amount = (!txtAmount.getText().trim().isEmpty())
                    ? Double.parseDouble(txtAmount.getText().trim())
                    : 0.0;
            double balance = fee - amount;
            txtBalance.setText(String.valueOf(balance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tableClick(MouseEvent event) {
        PaymentTM paymentTM = tablePatient.getSelectionModel().getSelectedItem();
        if (paymentTM != null){
            txtPaymentId.setText(paymentTM.getId());
            cmbProgramId.setValue(paymentTM.getProgramId());
            cmbPatientId.setValue(paymentTM.getPatientId());
            cmbSessionId.setValue(paymentTM.getTherapySessionId());
            txtAmount.setText(String.valueOf(paymentTM.getAmount()));
            txtBalance.setText(String.valueOf(paymentTM.getBalance()));
            txtInstallment.setText(paymentTM.getInstallment());
            txtStatus.setText(paymentTM.getStatus());
            LocalDate localDate = paymentTM.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePicker.setValue(localDate);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnAdd.setDisable(true);
        }
    }

    @FXML
    void updatePayment(ActionEvent event) {
        String paymentId = txtPaymentId.getText();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        System.out.println("PatientId = "+patientId+" Program- "+patientId);
        String sessionId = cmbSessionId.getSelectionModel().getSelectedItem();
        double amount = Double.parseDouble(txtAmount.getText());
        double balance = Double.parseDouble(txtBalance.getText());
        String installment = txtInstallment.getText();
        String status = txtStatus.getText();
        Date date = Date.valueOf(datePicker.getValue());
        boolean isValidAmount = Validation.isValid(String.valueOf(amount),"price");
        boolean isValidBalance = Validation.isValid(String.valueOf(balance),"price");
        if (!isValidAmount){
            txtAmount.setStyle("-fx-border-color: red");
        }else {
            txtAmount.setStyle("-fx-border-color: black");
        }
        if (!isValidBalance){
            txtBalance.setStyle("-fx-border-color: red");
        }else {
            txtBalance.setStyle("-fx-border-color: black");
        }

        if (isValidAmount && isValidBalance && !paymentId.isEmpty() && !programId.isEmpty() && !patientId.isEmpty() && !installment.isEmpty() && !status.isEmpty()) {

            PaymentDto paymentDto = new PaymentDto(
                paymentId,
                amount,
                installment,
                status,
                balance,
                date,
                patientId,
                programId,
                sessionId
        );
        boolean isUpdate = paymentBO.updatePayment(paymentDto);
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Payment Update Successfully").show();
            refreshPage();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Payment Update Not Successfully").show();
        }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Or null input").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("therapySessionId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInstallment.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));

        try {
            refreshPage();
            setPatientIds();
            setSessionIds();
            setProgramIds();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setPatientIds(){
        cmbPatientId.setPromptText("Select Patient ID");
        ArrayList<String> iDS = patientBO.getAllPatientId();
        cmbPatientId.getItems().addAll(iDS);
    }

    public void setSessionIds(){
        cmbSessionId.setPromptText("Select Session ID");
        ArrayList<String> iDs = therapySessionBO.getAllId();
        cmbSessionId.getItems().addAll(iDs);
    }

    public void setProgramIds(){
        cmbProgramId.setPromptText("Select Program ID");
        ArrayList<String> iDs = therapyProgramBO.getAllProgramId();
        cmbProgramId.getItems().addAll(iDs);
    }

    public void refreshTable(){
        ArrayList<PaymentDto> paymentDtos = paymentBO.getAll();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();
        for (PaymentDto paymentDto: paymentDtos){
            PaymentTM paymentTM = new PaymentTM(
                    paymentDto.getId(),
                    paymentDto.getAmount(),
                    paymentDto.getInstallment(),
                    paymentDto.getStatus(),
                    paymentDto.getBalance(),
                    paymentDto.getDate(),
                    paymentDto.getPatientId(),
                    paymentDto.getProgramId(),
                    paymentDto.getTherapySessionId()
            );
            paymentTMS.add(paymentTM);
        }
        tablePatient.setItems(paymentTMS);
    }

    public void refreshPage(){
        refreshTable();
        txtPaymentId.setText(paymentBO.getNextId());
        cmbProgramId.setValue("");
        cmbPatientId.setValue("");
        cmbSessionId.setValue("");
        txtAmount.setText("");
        txtBalance.setText("");
        txtInstallment.setText("");
        txtStatus.setText("");
        datePicker.getEditor().clear();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

//    public void generateInvoice(String paymentId) throws Exception {
//        JasperReport jasperReport = JasperCompileManager.compileReport("/report/invoice.jrxml");
//
//        // Get payment data from DAO
//        ArrayList<PaymentDto>  data = paymentBO.fondByPaymentId(paymentId);
//
//
//        // Create data source
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
//
//        // Fill report (no parameters)
//        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);
//
//        // View or export
//        JasperViewer.viewReport(print, false);
//        JasperExportManager.exportReportToPdfFile(print, "invoices/" + paymentId + ".pdf");
//    }
}
