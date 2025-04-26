package lk.ijse.ormsmhtc.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.ormsmhtc.bo.BOFactory;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapistBOImpl;
import lk.ijse.ormsmhtc.dto.TherapistDto;
import lk.ijse.ormsmhtc.dto.tm.TherapistTM;
import lk.ijse.ormsmhtc.util.Validation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistManageController implements Initializable {

    @FXML
    private Pane bodyPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapistTM, String> colEmail;

    @FXML
    private TableColumn<TherapistTM, String> colName;

    @FXML
    private TableColumn<TherapistTM, String> colPhone;

    @FXML
    private TableColumn<TherapistTM, String> colSpecialization;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistId;

    @FXML
    private Label errorEmail;

    @FXML
    private Label errorId;

    @FXML
    private Label errorName;

    @FXML
    private Label errorPhone;

    @FXML
    private Label errorSpecialization;

    @FXML
    private TableView<TherapistTM> tableTherapist;

    @FXML
    private TextField txtSpecialization;

    @FXML
    private TextField txtTherapistEmail;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapyNumber;

    private TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @FXML
    void addTherapist(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String email = txtTherapistEmail.getText();
        String phone = txtTherapyNumber.getText();
        String specialization = txtSpecialization.getText();
        boolean isCorrectEmail = Validation.isValid(email,"gmail");
        boolean isCorrectPhone = Validation.isValid(phone,"phone");
        if (!isCorrectEmail){
            txtTherapistEmail.setStyle("-fx-border-color: red");
        }else {
            txtTherapistEmail.setStyle("-fx-border-color: black");
        }
        if (!isCorrectPhone){
            txtTherapyNumber.setStyle("-fx-border-color: red");
        }else {
            txtTherapyNumber.setStyle("-fx-border-color: black");
        }
        if (isCorrectPhone && isCorrectEmail && !id.isEmpty() && !name.isEmpty() && !specialization.isEmpty()) {
            boolean isSaved = therapistBO.saveTherapist(new TherapistDto(id, name, phone, email, specialization));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist saved successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Therapist saved not successfully").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong or null input").show();
        }
    }

    @FXML
    void clickTable(MouseEvent event) {
        TherapistTM selectedTherapist = tableTherapist.getSelectionModel().getSelectedItem();
        if (selectedTherapist != null){
            txtTherapistId.setText(selectedTherapist.getId());
            txtTherapistName.setText(selectedTherapist.getName());
            txtTherapistEmail.setText(selectedTherapist.getEmail());
            txtTherapyNumber.setText(selectedTherapist.getPhone());
            txtSpecialization.setText(selectedTherapist.getSpecialization());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    @FXML
    void deleteTherapist(ActionEvent event) {
        String id = txtTherapistId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = therapistBO.deletePatient(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Therapist Deleted successfully").show();
                refreshPage();
            }else{
                new Alert(Alert.AlertType.WARNING,"Therapist is not Delete").show();
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void updateTherapist(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String email = txtTherapistEmail.getText();
        String phone = txtTherapyNumber.getText();
        String specialization = txtSpecialization.getText();
        boolean isCorrectEmail = Validation.isValid(email,"gmail");
        boolean isCorrectPhone = Validation.isValid(phone,"phone");
        if (!isCorrectEmail){
            txtTherapistEmail.setStyle("-fx-border-color: red");
        }else {
            txtTherapistEmail.setStyle("-fx-border-color: black");
        }
        if (!isCorrectPhone){
            txtTherapyNumber.setStyle("-fx-border-color: red");
        }else {
            txtTherapyNumber.setStyle("-fx-border-color: black");
        }
        if (isCorrectPhone && isCorrectEmail && !id.isEmpty() && !name.isEmpty() && !specialization.isEmpty()) {

            boolean isUpdate = therapistBO.updateTherapist(new TherapistDto(id, name, phone, email, specialization));
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist update successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.WARNING, "Therapist update not successfully").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong or null input").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        try{
            refreshPage();
        }catch(Exception e){
            throw e;
        }
    }

    public void refreshPage() {
        refreshTable();
        txtTherapistId.setText(therapistBO.getNextId());
        txtTherapistName.setText("");
        txtTherapyNumber.setText("");
        txtTherapistEmail.setText("");
        txtSpecialization.setText("");

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnReset.setDisable(false);
    }

    public void refreshTable(){
        ArrayList<TherapistDto> therapistDtoList = therapistBO.getAllTherapist();
        ObservableList<TherapistTM> therapistList = FXCollections.observableArrayList();
        for(TherapistDto therapistDto: therapistDtoList){
            TherapistTM therapistTM = new TherapistTM(
                    therapistDto.getId(),
                    therapistDto.getName(),
                    therapistDto.getPhone(),
                    therapistDto.getEmail(),
                    therapistDto.getSpecialization()
            );
            therapistList.add(therapistTM);
        }
        tableTherapist.setItems(therapistList);
    }
}
