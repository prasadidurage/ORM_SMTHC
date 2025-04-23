package lk.ijse.ormsmhtc.controller;

import lk.ijse.ormsmhtc.bo.custom.impl.PatientBOImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.ormsmhtc.dto.PatientDto;
import lk.ijse.ormsmhtc.dto.tm.PatientTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private Label addressError;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label emailError;

    @FXML
    private Label historyError;

    @FXML
    private Label idError;

    @FXML
    private Label nameError;

    @FXML
    private Label phoneError;

    @FXML
    private TableView<PatientTM> tablePatient;

    @FXML
    private TableColumn<PatientTM, String> colAddress;

    @FXML
    private TableColumn<PatientTM, String> colEmail;

    @FXML
    private TableColumn<PatientTM, String> colHistory;

    @FXML
    private TableColumn<PatientTM, String> colName;

    @FXML
    private TableColumn<PatientTM, String> colPatientID;

    @FXML
    private TableColumn<PatientTM, String> colPhone;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Pane userPane;

    private PatientBOImpl patientBO = new PatientBOImpl();

    @FXML
    void addPatient(ActionEvent event) {
        String patientId = txtPatientId.getText();
        String patientName = txtPatientName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String history = txtHistory.getText();

        boolean isSaved = patientBO.savePatient(new PatientDto(patientId,patientName,address,phone,email,history));
        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Patient saved successfully").show();
            txtPatientId.setText("");
            txtPatientName.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
            txtHistory.setText("");
            refreshPage();
        }else{
            new Alert(Alert.AlertType.WARNING,"Patient saved not successfully").show();
        }

    }

    @FXML
    void deletePatient(ActionEvent event) {
        String patientId = txtPatientId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this patient?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = patientBO.deletePatient(patientId);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Patient Deleted successfully").show();
                refreshPage();
            }else{
                new Alert(Alert.AlertType.WARNING,"Patient is not Delete").show();
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void tableClick(MouseEvent event) {
        PatientTM selectedPatient = tablePatient.getSelectionModel().getSelectedItem();
        if (selectedPatient != null){
            txtPatientId.setText(selectedPatient.getId());
            txtPatientName.setText(selectedPatient.getName());
            txtAddress.setText(selectedPatient.getAddress());
            txtEmail.setText(selectedPatient.getEmail());
            txtPhone.setText(selectedPatient.getPhone());
            txtHistory.setText(selectedPatient.getHistory());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    @FXML
    void updatePatient(ActionEvent event) {
        String patientId = txtPatientId.getText();
        String patientName = txtPatientName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String history = txtHistory.getText();

        boolean isUpdate = patientBO.updatePatient(new PatientDto(patientId, patientName, address, phone, email, history));
        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Patient saved successfully").show();
            refreshPage();
        }else{
            new Alert(Alert.AlertType.WARNING,"Patient saved not successfully").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPatientID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("history"));
        try {
            refreshPage();
        }catch (Exception e) {
            throw e;
        }
    }

    public void refreshPage(){
        refreshTable();
        txtPatientId.setText(patientBO.getNextId());
        txtPatientName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtHistory.setText("");

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnReset.setDisable(false);
    }

    public void refreshTable(){
        ArrayList<PatientDto> patientDtos = patientBO.getAllPatient();
        ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
        for(PatientDto patientDto : patientDtos){
            PatientTM patient = new PatientTM(
                    patientDto.getId(),
                    patientDto.getName(),
                    patientDto.getAddress(),
                    patientDto.getPhone(),
                    patientDto.getEmail(),
                    patientDto.getHistory()
            );
            patientTMS.add(patient);
        }
        tablePatient.setItems(patientTMS);
    }
}
