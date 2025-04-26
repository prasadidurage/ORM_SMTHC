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
import lk.ijse.ormsmhtc.bo.custom.impl.UserBOImpl;
import lk.ijse.ormsmhtc.dto.UserDto;
import lk.ijse.ormsmhtc.dto.tm.UserTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<String, UserTM> colEmail;

    @FXML
    private TableColumn<String, UserTM> colName;

    @FXML
    private TableColumn<String, UserTM> colPassword;

    @FXML
    private TableColumn<String, UserTM> colRole;

    @FXML
    private TableColumn<String, UserTM> colUserId;

    @FXML
    private TableColumn<String, UserTM> colUserName;

    @FXML
    private TableView<UserTM> tableUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUserId;

    @FXML
    private Pane userPane;

    UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void deleteProgram(ActionEvent event) {
        String userId = txtUserId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete this user",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDelete = userBO.deleteUser(userId);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Session Deleted successfully").show();
                refreshPAge();
            }else{
                new Alert(Alert.AlertType.WARNING,"Session is not Delete").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM userTM = tableUser.getSelectionModel().getSelectedItem();
        if (userTM != null){
            txtUserId.setText(userTM.getId());
            txtName.setText(userTM.getName());
            txtEmail.setText(userTM.getEmail());
        }else {
            System.out.println("nulls");
        }
    }

    @FXML
    void resetPage(ActionEvent event) {
        refreshPAge();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        try {
            refreshPAge();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshPAge(){
        refreshTable();
        txtEmail.setText("");
        txtName.setText("");
        txtUserId.setText("");
    }

    public void refreshTable(){
        ArrayList<UserDto> userDtos = userBO.getAllUsers();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
        for (UserDto userDto : userDtos){
            UserTM userTM = new UserTM(
                    userDto.getId(),
                    userDto.getName(),
                    userDto.getUsername(),
                    userDto.getPassword(),
                    userDto.getRole(),
                    userDto.getEmail()
            );
            userTMS.add(userTM);
        }
        tableUser.setItems(userTMS);
    }
}
