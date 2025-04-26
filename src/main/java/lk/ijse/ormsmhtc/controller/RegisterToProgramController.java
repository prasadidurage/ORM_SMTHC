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
import lk.ijse.ormsmhtc.bo.custom.impl.*;
import lk.ijse.ormsmhtc.dto.PatientProgramDto;
import lk.ijse.ormsmhtc.dto.PaymentDto;
import lk.ijse.ormsmhtc.dto.tm.PatientProgramTM;
import lk.ijse.ormsmhtc.util.Validation;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterToProgramController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbPrograms;

    @FXML
    private TableColumn<String, PatientProgramTM> colPayId;

    @FXML
    private TableColumn<String, PatientProgramTM> colPatientId;

    @FXML
    private TableColumn<String, PatientProgramTM> colProgramId;

    @FXML
    private TableColumn<Date, PatientProgramTM> colRegDate;

    @FXML
    private DatePicker datePick;

    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblTherpistId;

    @FXML
    private Pane programPane;

    @FXML
    private TableView<PatientProgramTM> tableRegistration;

    @FXML
    private TextField txtAmmount;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtInstallement;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtStatus;

    PatientProgramBOImpl patientProgramBO = (PatientProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT_PROGRAM);
    PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    PatientBOImpl patientBO = (PatientBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void delete(ActionEvent event) {
        String programId = cmbPrograms.getSelectionModel().getSelectedItem();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String paymentId = txtPaymentId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to Delete this Patients Program!",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = patientProgramBO.deletePatientProgram(programId,patientId,paymentId);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Patient Program delete success").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Patient Program delete not success").show();
            }
        }

    }

    @FXML
    void onClick(MouseEvent event) {
        PatientProgramTM selectedPatientProgram = tableRegistration.getSelectionModel().getSelectedItem();
        if (selectedPatientProgram != null){
            cmbPrograms.setValue(selectedPatientProgram.getProgramId());
            cmbPatientId.setValue(selectedPatientProgram.getPatientId());
            datePick.setValue(selectedPatientProgram.getRegisterDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            txtPaymentId.setText(selectedPatientProgram.getPaymentId());
            PaymentDto paymentDto = paymentBO.getAllById(selectedPatientProgram.getPaymentId());
            txtAmmount.setText(String.valueOf(paymentDto.getAmount()));
            txtBalance.setText(String.valueOf(paymentDto.getBalance()));
            txtStatus.setText(paymentDto.getStatus());
            txtInstallement.setText(paymentDto.getInstallment());

            btnRemove.setDisable(false);
            btnUpdate.setDisable(false);
            btnAdd.setDisable(true);
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void save(ActionEvent event) {
        String programId = cmbPrograms.getSelectionModel().getSelectedItem();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String paymentId = txtPaymentId.getText();
        double ammount = Double.parseDouble(txtAmmount.getText());
        LocalDate localDate = datePick.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String status = txtStatus.getText();
        String installment = txtInstallement.getText();
        double balance = Double.parseDouble(txtBalance.getText());
        boolean isValidAmount = Validation.isValid(String.valueOf(ammount),"price");
        boolean isValidBalance = Validation.isValid(String.valueOf(balance),"price");
        if (!isValidAmount){
            txtAmmount.setStyle("-fx-border-color: red");
        }else {
            txtAmmount.setStyle("-fx-border-color: black");
        }
        if (!isValidBalance){
            txtBalance.setStyle("-fx-border-color: red");
        }else {
            txtBalance.setStyle("-fx-border-color: black");
        }
        PaymentDto paymentDto = new PaymentDto(
                programId,
                patientId,
                date,
                balance,
                status,
                installment,
                ammount,
                paymentId
        );
        PatientProgramDto patientProgramDto = new PatientProgramDto(
                patientId,
                programId,
                date,
                paymentId
        );
        if (isValidAmount && isValidBalance && !paymentId.isEmpty() && !programId.isEmpty() && !patientId.isEmpty() && !installment.isEmpty() && !status.isEmpty()) {
            boolean isSave = patientProgramBO.savePatientProgram(patientProgramDto, paymentDto);
            if (isSave) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Patient Registration To Program is Success").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Patient Registration To Program is Not Success").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Or null input").show();
        }
    }

    @FXML
    void update(ActionEvent event) {
        String programId = cmbPrograms.getSelectionModel().getSelectedItem();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String paymentId = txtPaymentId.getText();
        double ammount = Double.parseDouble(txtAmmount.getText());
        LocalDate localDate = datePick.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String status = txtStatus.getText();
        String installment = txtInstallement.getText();
        double balance = Double.parseDouble(txtBalance.getText());
        boolean isValidAmount = Validation.isValid(String.valueOf(ammount),"price");
        boolean isValidBalance = Validation.isValid(String.valueOf(balance),"price");
        if (!isValidAmount){
            txtAmmount.setStyle("-fx-border-color: red");
        }else {
            txtAmmount.setStyle("-fx-border-color: black");
        }
        if (!isValidBalance){
            txtBalance.setStyle("-fx-border-color: red");
        }else {
            txtBalance.setStyle("-fx-border-color: black");
        }
        if (isValidAmount && isValidBalance && !paymentId.isEmpty() && !programId.isEmpty() && !patientId.isEmpty() && !installment.isEmpty() && !status.isEmpty()) {
            PaymentDto paymentDto = new PaymentDto(
                    programId,
                    patientId,
                    date,
                    balance,
                    status,
                    installment,
                    ammount,
                    paymentId
            );
            PatientProgramDto patientProgramDto = new PatientProgramDto(
                    patientId,
                    programId,
                    date,
                    paymentId
            );
            boolean isUpdate = patientProgramBO.updatePatientProgram(patientProgramDto, paymentDto);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Patient Registration To Program is Updated").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Patient Registration To Program is Not Update").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Or null input").show();
        }
    }

    @FXML
    void setBalance(KeyEvent event) {
        boolean isValidAmount = Validation.isValid(String.valueOf(txtAmmount.getText()),"price");
        if (!isValidAmount){
            txtAmmount.setStyle("-fx-border-color: red");
        }else {
            txtAmmount.setStyle("-fx-border-color: black");
        }
        if (isValidAmount) {
            try {
                String programID = cmbPrograms.getSelectionModel().getSelectedItem();
                double fee = therapyProgramBO.getFee(programID);
                System.out.println(fee);
                double amount = Double.parseDouble(txtAmmount.getText());
                double balance = fee - amount;
                txtBalance.setText(String.valueOf(balance));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colPayId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        try {
            refreshPage();
            setPatientId();
            setProgramIds();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshPage(){
        refreshTable();
        cmbPrograms.setValue("");
        cmbPatientId.setValue("");
        txtAmmount.setText("");
        txtBalance.setText("");
        datePick.setValue(null);
        txtInstallement.setText("");
        txtStatus.setText("");
        txtPaymentId.setText(paymentBO.getNextId());

        btnAdd.setDisable(false);
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void refreshTable(){
        ArrayList<PatientProgramDto> patientProgramDtos = patientProgramBO.getAllPatientProgram();
        ObservableList<PatientProgramTM> patientProgramTMS = FXCollections.observableArrayList();
        for (PatientProgramDto patientProgramDto : patientProgramDtos){
            PatientProgramTM patientProgramTM = new PatientProgramTM(
                    patientProgramDto.getPatientId(),
                    patientProgramDto.getProgramId(),
                    patientProgramDto.getRegisterDate(),
                    patientProgramDto.getPaymentId()
            );
            patientProgramTMS.add(patientProgramTM);
        }
        tableRegistration.setItems(patientProgramTMS);
    }

    public void setPatientId(){
        cmbPatientId.setPromptText("Select Patient ID");
        ArrayList<String> iDS = patientBO.getAllPatientId();
        cmbPatientId.getItems().addAll(iDS);
    }

    public void setProgramIds(){
        cmbPrograms.setPromptText("Select Programs ID");
        ArrayList<String> iDS = therapyProgramBO.getAllProgramId();
        cmbPrograms.getItems().addAll(iDS);
    }
}
