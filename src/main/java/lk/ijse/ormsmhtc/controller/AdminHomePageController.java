package lk.ijse.ormsmhtc.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.ormsmhtc.bo.BOFactory;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapistBOImpl;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.ormsmhtc.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.ormsmhtc.dto.CustomDto;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

        @FXML
        private BarChart<String, Long> barTherapistPerformance;

        @FXML
        private BarChart<String, Long> barTherapySession;

        @FXML
        private Pane bodyPane;

        @FXML
        private ComboBox<String> cmbTherapistId;

        @FXML
        private ComboBox<String> cmbTherapySessionId;

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
        private Label lblPattion;

        @FXML
        private Label lblPattion1;

        @FXML
        private Label lblthrapist;

        TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
        TherapySessionBOImpl therapySessionBO = (TherapySessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);
        TherapyProgramBOImpl therapyProgramBO = (TherapyProgramBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

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
                navigateTo("/view/Reports.fxml");
        }

        @FXML
        void navigateToSettingPage(MouseEvent event) {
                navigateTo("/view/SettingFile.fxml");
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




        @Override
        public void initialize(URL location, ResourceBundle resources) {
//                ArrayList<String> therapists = therapistBO.getAllTherapistId();
//                cmbTherapistId.getItems().setAll(therapists);
//
//                ArrayList<String> therapySession = therapyProgramBO.getAllProgramId();
//                cmbTherapySessionId.getItems().setAll(therapySession);
//
//                cmbTherapistId.setOnAction(event -> {
//                        setBarTherapistPerformance();
//                });
//
//                cmbTherapySessionId.setOnAction(event -> {
//                        setBarTherapySession();
//                });
        }
}


