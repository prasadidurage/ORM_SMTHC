package lk.ijse.ormsmhtc.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lk.ijse.ormsmhtc.bo.BOFactory;
import lk.ijse.ormsmhtc.bo.custom.impl.UserBOImpl;
import lk.ijse.ormsmhtc.dto.UserDto;
import lk.ijse.ormsmhtc.util.EncryptPassword;
import lk.ijse.ormsmhtc.util.Util;

public class SettingController {

    @FXML
    private Button btnUpdate;


    @FXML
    private Label errorConfirm;

    @FXML
    private Label errorCurrent;

    @FXML
    private Label errorNew;

    @FXML
    private Group grpConfirm;

    @FXML
    private Group grpConfirmHide;

    @FXML
    private Group grpCurrent;

    @FXML
    private Group grpCurrentHide;

    @FXML
    private Group grpNew;

    @FXML
    private Group grpNewHide;

    @FXML
    private PasswordField passwordCurrent;

    @FXML
    private PasswordField passwordNew;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private TextField txtHideConfirmPassword;

    @FXML
    private TextField txtHideNewPassword;

    @FXML
    private TextField txtHidePasswordCurrent;

    UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void showConfirmPassword(MouseEvent event) {
        grpConfirmHide.setVisible(false);
        grpConfirm.setVisible(true);
    }

    @FXML
    void showCurrentPassword(MouseEvent event) {
        grpCurrentHide.setVisible(false);
        grpCurrent.setVisible(true);
    }

    @FXML
    void showNewPassword(MouseEvent event) {
        grpNewHide.setVisible(false);
        grpNew.setVisible(true);
    }

    @FXML
    void updatePasswords(ActionEvent event) {
        UserDto userDto = Util.getInstance().getUserDto();
        String currentPwd;
        String newPwd;
        String confirmPwd;
        if (passwordCurrent.isVisible()) {
            currentPwd = passwordCurrent.getText();
        } else {
            currentPwd = txtHidePasswordCurrent.getText();
        }
        if (passwordNew.isVisible()) {
            newPwd = passwordNew.getText();
        } else {
            newPwd = txtHideNewPassword.getText();
        }
        if (passwordConfirm.isVisible()) {
            confirmPwd = passwordConfirm.getText();
        } else {
            confirmPwd = txtHideConfirmPassword.getText();
        }
        if (!confirmPwd.equals(newPwd)){
            errorConfirm.setText("Confirm password is mismatch");
        }
        if (confirmPwd.equals(newPwd) && currentPwd != null && EncryptPassword.verifyPassword(currentPwd,userDto.getPassword())){
            String newPassword = EncryptPassword.hashPassword(newPwd);
            boolean isUpdate = userBO.update(userDto,newPassword);
            if (isUpdate){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Password Upgraded").show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Password Not Upgraded").show();
            }
        }else {
            errorCurrent.setText("The current Password is wrong");
            refreshPage();
        }

    }

//    @FXML
//    void hideConfirmPassword(MouseEvent event) {
//        String pw = passwordConfirm.getText();
//        txtHideConfirmPassword.setText(pw);
//        grpConfirmHide.setVisible(true);
//        grpConfirm.setVisible(false);
//    }

//    @FXML
//    void hideCurrentPassword(MouseEvent event) {
//        String pw = passwordCurrent.getText();
//        txtHidePasswordCurrent.setText(pw);
//        grpCurrentHide.setVisible(true);
//        grpCurrent.setVisible(false);
//    }

//    @FXML
//    void hideNewPassword(MouseEvent event) {
//        String pw = passwordNew.getText();
//        txtHideNewPassword.setText(pw);
//        grpNewHide.setVisible(true);
//        grpNew.setVisible(false);
//    }

    public void refreshPage(){
        passwordConfirm.setText("");
        passwordNew.setText("");
        passwordCurrent.setText("");
        txtHidePasswordCurrent.setText("");
        txtHideConfirmPassword.setText("");
        txtHideNewPassword.setText("");
        grpCurrentHide.setVisible(false);
        grpNewHide.setVisible(false);
        grpConfirmHide.setVisible(false);
        grpNew.setVisible(true);
        grpConfirm.setVisible(true);
        grpCurrent.setVisible(true);
        errorCurrent.setText("");
        errorConfirm.setText("");
        errorNew.setText("");
    }
}
