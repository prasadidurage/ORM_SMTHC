package lk.ijse.ormsmhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ReceptionHomePageController {
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
    private Button btnTherapySession;

    @FXML
    void navigateRegisterPage(ActionEvent event) {
        navigateTo("/view/RegisterToProgram.fxml");
    }

    @FXML
    void navigateToAssignProgram(ActionEvent event) {
        navigateTo("/view/AssignToPrograms.fxml");
    }

    @FXML
    void navigateToHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReceptionHomePage.fxml"));
        Pane newContent = loader.load();

        AnchorPane parent = (AnchorPane) bodyPane.getParent();
        parent.getChildren().remove(bodyPane);
        parent.getChildren().add(newContent);
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
    void navigateToReports(ActionEvent event) {
        navigateTo("/view/Reports.fxml");
    }

    @FXML
    void navigateToSettingPage(MouseEvent event) {
        navigateTo("/view/SettingFile.fxml");
    }

    @FXML
    void navigateToTherapySession(ActionEvent event) {
        navigateTo("/view/TherapySessionManagement.fxml");
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
