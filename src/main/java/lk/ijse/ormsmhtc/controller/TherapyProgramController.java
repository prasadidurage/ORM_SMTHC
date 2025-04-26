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
import lk.ijse.ormsmhtc.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.ormsmhtc.dto.TherapyProgramDto;
import lk.ijse.ormsmhtc.dto.tm.TherapyProgramTM;
import lk.ijse.ormsmhtc.util.Validation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapyProgramTM, Double> colCost;

    @FXML
    private TableColumn<TherapyProgramTM, String> colDescription;

    @FXML
    private TableColumn<TherapyProgramTM, String> colDuration;

    @FXML
    private TableColumn<TherapyProgramTM, String> colName;

    @FXML
    private TableColumn<TherapyProgramTM, String> colProgramId;

    @FXML
    private Label errorCost;

    @FXML
    private Label errorDesc;

    @FXML
    private Label errorDuration;

    @FXML
    private Label errorId;

    @FXML
    private Label errorName;

    @FXML
    private Pane programPane;

    @FXML
    private TableView<TherapyProgramTM> tableProgram;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    private TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void addProgram(ActionEvent event) {
        boolean isCorrectCost = Validation.isValid(txtCost.getText(),"price");
        if (!isCorrectCost){
            txtCost.setStyle("-fx-border-color: red");
        }else {
            txtCost.setStyle("-fx-border-color: black");
        }
        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        String duration = txtDuration.getText();
        double cost = Double.parseDouble(txtCost.getText());
        String description = txtDescription.getText();

        if (isCorrectCost && !id.isEmpty() && !name.isEmpty() && !duration.isEmpty() && !description.isEmpty() ) {
            boolean isSaved = therapyProgramBO.saveTherapyProgram(new TherapyProgramDto(id, name, duration, cost, description));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Program Saved Successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Program Not Saved!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid or null input").show();
        }

    }

    @FXML
    void deleteProgram(ActionEvent event) {
        String id = txtProgramId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this program?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = therapyProgramBO.deleteTherapy(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Therapy Program Deleted successfully").show();
                refreshPage();
            }else{
                new Alert(Alert.AlertType.WARNING,"Therapy Program is not Delete").show();
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void tableClick(MouseEvent event) {
        TherapyProgramTM selectedTherapyProgram = tableProgram.getSelectionModel().getSelectedItem();
        if (selectedTherapyProgram != null){
            txtProgramId.setText(selectedTherapyProgram.getId());
            txtProgramName.setText(selectedTherapyProgram.getName());
            txtDuration.setText(selectedTherapyProgram.getDuration());
            txtCost.setText(String.valueOf(selectedTherapyProgram.getCost()));
            txtDescription.setText(selectedTherapyProgram.getDescription());

            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }

    @FXML
    void updateProgram(ActionEvent event) {
        try {
            boolean isCorrectCost = Validation.isValid(txtCost.getText(),"price");
            if (!isCorrectCost){
                txtCost.setStyle("-fx-border-color: red");
            }else {
                txtCost.setStyle("-fx-border-color: black");
            }

            String id = txtProgramId.getText();
            String name = txtProgramName.getText();
            String duration = txtDuration.getText();
            double cost = Double.parseDouble(txtCost.getText());
            String description = txtDescription.getText();

            if (isCorrectCost && !id.isEmpty() && !name.isEmpty() && !duration.isEmpty() && !description.isEmpty() ) {

                boolean isUpdate = therapyProgramBO.updateTherapyProgram(new TherapyProgramDto(id, name, duration, cost, description));
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy Program Update Successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy Program Not Updated!").show();
                }
            }else {
                    new Alert(Alert.AlertType.ERROR,"Invalid or null input").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        try {
            refreshPage();
        }catch (Exception e){
            throw e;
        }
    }

    public void refreshPage(){
        refreshTable();
        txtProgramId.setText(therapyProgramBO.getNextId());
        txtCost.setText("");
        txtDescription.setText("");
        txtDuration.setText("");
        txtProgramName.setText("");

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnReset.setDisable(false);
    }

    public void refreshTable(){
        ArrayList<TherapyProgramDto> therapyProgramDtos = therapyProgramBO.getAllPrograms();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();
        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos){
            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDto.getId(),
                    therapyProgramDto.getName(),
                    therapyProgramDto.getDuration(),
                    therapyProgramDto.getCost(),
                    therapyProgramDto.getDescription()
            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        tableProgram.setItems(therapyProgramTMS);
    }
}
