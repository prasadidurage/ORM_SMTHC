package lk.ijse.ormsmhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class homepageController {
    @FXML
    private Pane bodyPane;

    @FXML
    private Button btnAssignToProgram;

    @FXML
    private Button btnPatientManage;

    @FXML
    private Button btnPayments;

    @FXML
    private Button btnRegisterProgram;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnTherapyProgran;

    @FXML
    private Button btnTherapySession;

    @FXML
    private Button btnTherpist;

    @FXML
    private Button btnUserManage;


    @FXML
    private Label lblAppoiment;

    @FXML
    private Label lblPation;

    @FXML
    private Label lblTheraphy;

    @FXML
    void navigateToAssignProgram(ActionEvent event) {
        navigateTo("/view/AssignToPrograms.fxml");
    }

    @FXML
    void navigateToPatient(ActionEvent event) {
        navigateTo("/view/PatientManagement.fxml");
    }

    @FXML
    void navigateToPayment(ActionEvent event) {
        navigateTo("/view/PaymentManagement.fxml");
    }

    @FXML
    void navigateToHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminHomePage.fxml"));
        Pane newContent = loader.load();

        AnchorPane parent = (AnchorPane) bodyPane.getParent();
        parent.getChildren().remove(bodyPane);
        parent.getChildren().add(newContent);
    }

    @FXML
    void navigateToTherapist(ActionEvent event)  {
        navigateTo("/view/TherapistManage.fxml");
    }

    @FXML
    void navigateToTherapyProgram(ActionEvent event) {
        navigateTo("/view/TherapyProgram.fxml");
    }

    @FXML
    void navigateToTherapySession(ActionEvent event) {
        navigateTo("/view/TherapySessionManagement.fxml");
    }

    @FXML
    void navigateToUserManagePage(ActionEvent event) {
        navigateTo("/view/UserManagement.fxml");
    }

    @FXML
    void navigateToReports(ActionEvent event) {

    }

    @FXML
    void navigateRegisterPage(ActionEvent event) {
        navigateTo("/view/RegisterToProgram.fxml");
    }


    public void navigateTo(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane newContent = loader.load();

            bodyPane.getChildren().clear();  // Clear the old content
            bodyPane.getChildren().add(newContent);  // Add the new content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
