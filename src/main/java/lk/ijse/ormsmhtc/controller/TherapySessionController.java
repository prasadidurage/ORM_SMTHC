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
import lk.ijse.ormsmhtc.bo.custom.impl.*;
import lk.ijse.ormsmhtc.dto.CustomDto;
import lk.ijse.ormsmhtc.dto.TherapySessionDTO;
import lk.ijse.ormsmhtc.dto.tm.TherapistAvailableTimeTM;
import lk.ijse.ormsmhtc.dto.tm.TherapySessionTM;
import lk.ijse.ormsmhtc.util.Validation;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancle;

    @FXML
    private Button btnReschedule;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cmbPatientId;

    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<Date, TherapySessionTM> colDate;
    @FXML
    private TableColumn<Time, TherapySessionTM> colEndTime;

    @FXML
    private TableColumn<String, TherapySessionTM> colPatientId;

    @FXML
    private TableColumn<String, TherapySessionTM> colProgramId;

    @FXML
    private TableColumn<String, TherapySessionTM> colSessionId;

    @FXML
    private TableColumn<Time, TherapySessionTM> colStartTime;

    @FXML
    private TableColumn<String, TherapySessionTM> colTherapistId;

    @FXML
    private TableColumn<TherapistAvailableTimeTM,Date> colDay;

    @FXML
    private TableColumn<TherapistAvailableTimeTM,String> colTime;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblStartTime;

    @FXML
    private TableView<TherapistAvailableTimeTM> tableAvailability;

    @FXML
    private TableView<TherapySessionTM> tableSession;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtStartTime;

    @FXML
    private Pane userPane;

    TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
    TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    PatientBOImpl patientBO = (PatientBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapistProgramBOImpl therapistProgramBO = (TherapistProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_PROGRAM);
    PatientProgramBOImpl patientProgramBO = (PatientProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT_PROGRAM);

    @FXML
    void addSession(ActionEvent event) {
        String sessionId = txtSessionId.getText();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        String therapistId = cmbTherapistId.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(datePicker.getValue());
        String start = txtStartTime.getText().trim();
        String end = txtEndTime.getText().trim();

        boolean isCorrectStartTime = Validation.isValid(start,"time");
        boolean isCorrectEndTime = Validation.isValid(end,"time");

        if (!isCorrectStartTime){
            txtStartTime.setStyle("-fx-border-color: red");
        }else {
            txtStartTime.setStyle("-fx-border-color: black");
        }

        if (!isCorrectEndTime){
            txtEndTime.setStyle("-fx-border-color: red");
        }else {
            txtEndTime.setStyle("-fx-border-color: black");
        }

        if (isCorrectStartTime && isCorrectEndTime && !sessionId.isEmpty() && !patientId.isEmpty() && !programId.isEmpty() && !therapistId.isEmpty()) {
            String formattedStart = formatToSqlTime(start);
            String formattedEnd = formatToSqlTime(end);
            Time startTime = Time.valueOf(formattedStart);
            Time endTime = Time.valueOf(formattedEnd);
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                    sessionId,
                    date,
                    startTime,
                    endTime,
                    therapistId,
                    patientId,
                    programId
            );
            boolean isSave = therapySessionBO.saveTherapySession(therapySessionDTO);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session Save Successful").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session not Save Successful").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid or null input").show();
        }
    }

    String formatToSqlTime(String time) {
        if (time == null) return null;

        if (time.matches("^\\d{2}:\\d{2}$")) {
            return time + ":00";
        } else if (time.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            return time;
        } else {
            return null;
        }
    }

    @FXML
    void cancleSession(ActionEvent event) {
        String sessionId = txtSessionId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this session?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = therapySessionBO.deleteSession(sessionId);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Session Deleted successfully").show();
                refreshPage();
            }else{
                new Alert(Alert.AlertType.WARNING,"Session is not Delete").show();
            }
        }
    }

    @FXML
    void rescheduleSession(ActionEvent event) {
        String sessionId = txtSessionId.getText();
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        String therapistId = cmbTherapistId.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(datePicker.getValue());
        String start = txtStartTime.getText().trim();
        String end = txtEndTime.getText().trim();

        boolean isCorrectStartTime = Validation.isValid(start,"time");
        boolean isCorrectEndTime = Validation.isValid(end,"time");

        if (!isCorrectStartTime){
            txtStartTime.setStyle("-fx-border-color: red");
        }else {
            txtStartTime.setStyle("-fx-border-color: black");
        }

        if (!isCorrectEndTime){
            txtEndTime.setStyle("-fx-border-color: red");
        }else {
            txtEndTime.setStyle("-fx-border-color: black");
        }

        if (isCorrectStartTime && isCorrectEndTime && !sessionId.isEmpty() && !patientId.isEmpty() && !programId.isEmpty() && !therapistId.isEmpty()) {
            String formattedStart = formatToSqlTime(start);
            String formattedEnd = formatToSqlTime(end);
            Time startTime = Time.valueOf(formattedStart);
            Time endTime = Time.valueOf(formattedEnd);
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                    sessionId,
                    date,
                    startTime,
                    endTime,
                    therapistId,
                    patientId,
                    programId
            );
            boolean isUpdate = therapySessionBO.updateTherapySession(therapySessionDTO);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session Update Successful").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session Update Not Successful").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid or null input").show();
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void tableClick(MouseEvent event) {
        TherapySessionTM tm = tableSession.getSelectionModel().getSelectedItem();
        txtSessionId.setText(tm.getId());
        cmbPatientId.setValue(tm.getPatientId());
        cmbProgramId.setValue(tm.getTherapyProgramID());
        cmbTherapistId.setValue(tm.getTherapistId());
        LocalDate localDate = tm.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setValue(localDate);
        txtStartTime.setText(String.valueOf(tm.getStartTime()));
        txtEndTime.setText(String.valueOf(tm.getEndTime()));
        setTimeTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("therapyProgramID"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        try {
            refreshPage();
            setPatientIds();
            cmbPatientId.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue != null) {
                    cmbProgramId.setDisable(false);
                    setProgramIds();
                }
            });
            cmbProgramId.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue != null) {
                    cmbTherapistId.setDisable(false);
                    setTherapistIds();
                }
            });
            cmbTherapistId.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue != null) {
                    setTimeTable();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshPage(){
        refreshTable();
        cmbPatientId.setValue("");
        cmbProgramId.setValue("");
        cmbTherapistId.setValue("");
        txtSessionId.setText(therapySessionBO.getNextSessionId());
        txtEndTime.setText("");
        txtStartTime.setText("");
        datePicker.cancelEdit();

        cmbProgramId.getItems().clear();
        cmbTherapistId.getItems().clear();
        cmbProgramId.setDisable(true);
        cmbTherapistId.setDisable(true);
        tableAvailability.getItems().clear();
    }

    public void refreshTable(){
        ArrayList<TherapySessionDTO> therapySessionDTOS = therapySessionBO.getAll();
        ObservableList<TherapySessionTM> therapySessionTMS = FXCollections.observableArrayList();
        for (TherapySessionDTO therapySessionDTO : therapySessionDTOS){
            TherapySessionTM tm = new TherapySessionTM(
                    therapySessionDTO.getId(),
                    therapySessionDTO.getDate(),
                    therapySessionDTO.getStartTime(),
                    therapySessionDTO.getEndTime(),
                    therapySessionDTO.getTherapistId(),
                    therapySessionDTO.getPatientId(),
                    therapySessionDTO.getTherapyProgramID()
            );
            therapySessionTMS.add(tm);
        }
        tableSession.setItems(therapySessionTMS);
    }


    public void setProgramIds(){
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        System.out.println(patientId);
        if (patientId == null) {
            cmbProgramId.setPromptText("Select Program ID");
            ArrayList<String> iDS = therapyProgramBO.getAllProgramId();
            cmbProgramId.getItems().addAll(iDS);
        } else {
            ArrayList<String> IDS = patientProgramBO.getProgramsIdByPatient(patientId);
            System.out.println(IDS);
            cmbProgramId.getItems().addAll(IDS);
        }
    }

    public void setPatientIds(){
        cmbPatientId.setPromptText("Select Patient ID");
        ArrayList<String> iDS = patientBO.getAllPatientId();
        cmbPatientId.getItems().addAll(iDS);
    }

    public void setTherapistIds(){
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        cmbTherapistId.setPromptText("Select Therapist ID");
        if (programId == null){
            ArrayList<String> therapistId = therapistBO.getAllTherapistId();
            cmbTherapistId.getItems().addAll(therapistId);
        }else{
            ArrayList<String> IDS = therapistProgramBO.getTherapistId(programId);
            cmbTherapistId.getItems().addAll(IDS);
        }
    }

    public void setTimeTable(){
        String programId = cmbProgramId.getSelectionModel().getSelectedItem();
        String therapistId = cmbTherapistId.getSelectionModel().getSelectedItem();
        ArrayList<CustomDto> customDtos = therapySessionBO.getAvailableTime(programId,therapistId);
        ObservableList<TherapistAvailableTimeTM> timeTMS = FXCollections.observableArrayList();
        for (CustomDto customDto : customDtos){
            TherapistAvailableTimeTM timeTM = new TherapistAvailableTimeTM(
                    customDto.getDate(),
                    customDto.getTime()
            );
            timeTMS.add(timeTM);
        }
        tableAvailability.setItems(timeTMS);
    }
}
