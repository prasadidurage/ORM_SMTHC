package lk.ijse.ormsmhtc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.ormsmhtc.bo.BOFactory;
import lk.ijse.ormsmhtc.bo.custom.impl.UserBOImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class singupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnSingUp;

    @FXML
    private ComboBox<?> cmbJobRole;

    @FXML
    private Label lblCPassword;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblJobrole;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblSingIn;

    @FXML
    private Label lblUserName;

    @FXML
    private PasswordField tctCPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);


    @FXML
    void selectjobRole(ActionEvent event) {

    }

    @FXML
    void singInOnClick(MouseEvent event) {

    }

    @FXML
    void singUpOnclick(ActionEvent event) {


    }

}
