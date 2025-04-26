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
import lk.ijse.ormsmhtc.dto.TherapistProgramDto;
import lk.ijse.ormsmhtc.dto.tm.AvailableTimeTM;
import lk.ijse.ormsmhtc.dto.tm.TherapistProgramTM;
import lk.ijse.ormsmhtc.util.Validation;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class AssignToProgramsController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbDays;

    @FXML
    private ComboBox<String> cmbPrograms;

    @FXML
    private ComboBox<String> cmbTherapist;

    @FXML
    private TableColumn<TherapistProgramTM, String> colDay;

    @FXML
    private TableColumn<TherapistProgramTM, LocalTime> colEndTime;

    @FXML
    private TableColumn<TherapistProgramTM, String> colProgramId;

    @FXML
    private TableColumn<TherapistProgramTM, LocalTime> colStartTime;

    @FXML
    private TableColumn<TherapistProgramTM, String> colTherapist;

    @FXML
    private TableColumn<AvailableTimeTM, String> colTimes;

    @FXML
    private TableColumn<AvailableTimeTM, String> colDays;


    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblTherpistId;

    @FXML
    private Pane programPane;

    @FXML
    private TableView<AvailableTimeTM> tablTimes;

    @FXML
    private TableView<TherapistProgramTM> tablePrograms;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);
    TherapistProgramBOImpl therapistProgramBO = (TherapistProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_PROGRAM);

    @FXML
    void getAvailableTime(ActionEvent event) {
        String therapistId = cmbTherapist.getSelectionModel().getSelectedItem();
        getAvailableTimes(therapistId);
    }

    @FXML
    void getDays(ActionEvent event) {

    }

    @FXML
    void getPrograms(ActionEvent event) {

    }

    @FXML
    void getTherapist(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {
        String programId = cmbPrograms.getValue();
        String therapistId = cmbTherapist.getValue();

        boolean isDelete = therapistProgramBO.deleteTherapistProgram(programId,
                therapistId);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure Delete this Program Details",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Delete Program Details Successfully").show();
                loadPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Delete Program Details Not Successfully").show();
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        loadPage();
    }

    @FXML
    void save(ActionEvent event) {
        String programId = cmbPrograms.getValue();
        String therapistId = cmbTherapist.getValue();
        String day = cmbDays.getValue();
        boolean isStartTime = Validation.isValid(txtStartTime.getText(),"time");
        boolean isEndTime = Validation.isValid(txtEndTime.getText(),"time");
        if (!isStartTime){
            txtStartTime.setStyle("-fx-border-color: red");
        }else {
            txtStartTime.setStyle("-fx-border-color: black");
        }
        if (!isEndTime){
            txtEndTime.setStyle("-fx-border-color: red");
        }else {
            txtEndTime.setStyle("-fx-border-color: black");
        }
        if (isStartTime && isEndTime && programId != null && therapistId != null && day != null) {
            LocalTime startTime = LocalTime.parse(txtStartTime.getText());
            LocalTime endTime = LocalTime.parse(txtEndTime.getText());
            boolean isSaved = therapistProgramBO.saveTherapistProgram(programId,
                    therapistId,
                    day,
                    startTime,
                    endTime);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Assign Program Successfully Done").show();
                loadPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Assign Program Not Successfully Done").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Input").show();
        }
    }

    @FXML
    void setDataToTextFields(MouseEvent event) {
        TherapistProgramTM selectedItem = tablePrograms.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            cmbPrograms.setValue(selectedItem.getProgramId());
            cmbTherapist.setValue(selectedItem.getTherapistId());
            cmbDays.setValue(selectedItem.getDay());
            txtStartTime.setText(String.valueOf(selectedItem.getStartTime()));
            txtEndTime.setText(String.valueOf(selectedItem.getEndTime()));

            btnAdd.setDisable(true);
            btnRemove.setDisable(false);
            btnUpdate.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    @FXML
    void update(ActionEvent event) {
        String programId = cmbPrograms.getValue();
        String therapistId = cmbTherapist.getValue();
        String day = cmbDays.getValue();
        boolean isStartTime = Validation.isValid(txtStartTime.getText(),"time");
        boolean isEndTime = Validation.isValid(txtEndTime.getText(),"time");
        if (!isStartTime){
            txtStartTime.setStyle("-fx-border-color: red");
        }else {
            txtStartTime.setStyle("-fx-border-color: black");
        }
        if (!isEndTime){
            txtEndTime.setStyle("-fx-border-color: red");
        }else {
            txtEndTime.setStyle("-fx-border-color: black");
        }
        if (isStartTime && isEndTime && programId != null && therapistId != null && day != null) {

            LocalTime startTime = LocalTime.parse(txtStartTime.getText());
            LocalTime endTime = LocalTime.parse(txtEndTime.getText());
            boolean isUpdate = therapistProgramBO.updateTherapistProgram(programId,
                    therapistId,
                    day,
                    startTime,
                    endTime);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Update Program Details Successfully").show();
                loadPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Update Program Details Not Successfully").show();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colDays.setCellValueFactory(new PropertyValueFactory<>("day"));
        colTimes.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        try {
            loadPage();
            setTherapistIds();
            setProgramIds();
            setDay();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPage(){
        loadTable();
        cmbDays.getSelectionModel().clearSelection();
        cmbPrograms.getSelectionModel().clearSelection();
        cmbTherapist.getSelectionModel().clearSelection();

        cmbDays.setPromptText("Select a Day");
        cmbPrograms.setPromptText("Select Programs ID");
        cmbTherapist.setPromptText("Select Therapist ID");

        txtEndTime.setText("");
        txtStartTime.setText("");
        
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnRemove.setDisable(true);
    }

    public void loadTable(){
        ArrayList<TherapistProgramDto> therapistProgramDtos = therapistProgramBO.getAllTherapistProgram();
        ObservableList<TherapistProgramTM> therapistProgramTMS = FXCollections.observableArrayList();
        for (TherapistProgramDto therapistProgramDto: therapistProgramDtos){
            TherapistProgramTM therapistProgramTM = new TherapistProgramTM(
                    therapistProgramDto.getProgramId(),
                    therapistProgramDto.getTherapistId(),
                    therapistProgramDto.getDay(),
                    therapistProgramDto.getStartTime(),
                    therapistProgramDto.getEndTime()
            );
            therapistProgramTMS.addAll(therapistProgramTM);
        }
        tablePrograms.setItems(therapistProgramTMS);
    }

    public void setTherapistIds(){
        cmbTherapist.setPromptText("Select Therapist ID");
        ArrayList<String> iDS = therapistBO.getAllTherapistId();
        cmbTherapist.getItems().addAll(iDS);
    }

    public void setProgramIds(){
        cmbPrograms.setPromptText("Select Programs ID");
        ArrayList<String> iDS = therapyProgramBO.getAllProgramId();
        cmbPrograms.getItems().addAll(iDS);
    }

    public void setDay(){
        cmbDays.setPromptText("Select a Day");
        cmbDays.getItems().addAll("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
    }

    public void getAvailableTimes(String therapistId) {
        ArrayList<CustomDto> timeSlot = therapistProgramBO.getAvailableTime(therapistId);
        ObservableList<AvailableTimeTM> timeTMS = FXCollections.observableArrayList();

        ArrayList<String> daysOfWeek = new ArrayList<>(Arrays.asList(
                "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
        ));

        for (String day : daysOfWeek) {
            StringBuilder availableTimes = new StringBuilder();

            for (CustomDto customDto : timeSlot) {
                if (customDto.getDay().equalsIgnoreCase(day)) {
                    availableTimes.append(customDto.getAvailableTimeList()).append(", ");
                }
            }

            if (availableTimes.length() > 0) {
                availableTimes.setLength(availableTimes.length() - 2); // remove last comma
                timeTMS.add(new AvailableTimeTM(day, availableTimes.toString()));
            }
        }

        tablTimes.setItems(timeTMS);
    }
}
