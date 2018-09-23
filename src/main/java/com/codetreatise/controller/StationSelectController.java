package com.codetreatise.controller;

import com.codetreatise.bean.User;
import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import com.codetreatise.service.UserService;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class StationSelectController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label userId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton rbMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ComboBox<String> cbRole;

    @Autowired
            private SecController secController;

    Callback<TableColumn<CityGateStationEntity, Boolean>, TableCell<CityGateStationEntity, Boolean>> cellFactory =
            new Callback<TableColumn<CityGateStationEntity, Boolean>, TableCell<CityGateStationEntity, Boolean>>() {
                @Override
                public TableCell<CityGateStationEntity, Boolean> call(final TableColumn<CityGateStationEntity, Boolean> param) {
                    final TableCell<CityGateStationEntity, Boolean> cell = new TableCell<CityGateStationEntity, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/click.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    CityGateStationEntity station = getTableView().getItems().get(getIndex());
                                    station = cityGateStationService.find(station.getId());
                                    stageManager.setCityGateStationEntity(station);
                                    stageManager.switchScene(FxmlView.STATION);
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void updateUser(CityGateStationEntity station) {
                            userId.setText(station.getProvince());
                            firstName.setText(station.getCity());
                            lastName.setText(station.getRegion());
                            dob.setValue(null);
                            rbMale.setSelected(true);
                            rbFemale.setSelected(true);
                            cbRole.getSelectionModel().select(station.getNominalCapacity());
                        }
                    };
                    return cell;
                }
            };
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button reset;
    @FXML
    private Button saveUser;
    @FXML
    private TableView<CityGateStationEntity> userTable;
    @FXML
    private TableColumn<CityGateStationEntity, Long> cgsId;
    @FXML
    private TableColumn<CityGateStationEntity, String> cgsProvince;
    @FXML
    private TableColumn<CityGateStationEntity, String> cgscity;
    @FXML
    private TableColumn<CityGateStationEntity, String> cgsRegion;
    @FXML
    private TableColumn<CityGateStationEntity, String> cgsAddress;
    @FXML
    private TableColumn<CityGateStationEntity, String> cgsNominalCapacity;
    @FXML
    private TableColumn<CityGateStationEntity, Boolean> colSelect;
    @FXML
    private MenuItem deleteUsers;
    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
    private UserService userService;
    @Autowired
    private CityGateStationService cityGateStationService;
    private ObservableList<CityGateStationEntity> userList = FXCollections.observableArrayList();
    private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.STATION);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+") &&
                emptyValidation("DOB", dob.getEditor().getText().isEmpty()) &&
                emptyValidation("Role", getRole() == null)) {

            if (userId.getText() == null || userId.getText() == "") {
                if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                        emptyValidation("Password", getPassword().isEmpty())) {

                    User user = new User();
                    user.setFirstName(getFirstName());
                    user.setLastName(getLastName());
                    user.setDob(getDob());
                    user.setGender(getGender());
                    user.setRole(getRole());
                    user.setEmail(getEmail());
                    user.setPassword(getPassword());

                    User newUser = userService.save(user);

                    saveAlert(newUser);
                }

            } else {
                User user = userService.find(Long.parseLong(userId.getText()));
                user.setFirstName(getFirstName());
                user.setLastName(getLastName());
                user.setDob(getDob());
                user.setGender(getGender());
                user.setRole(getRole());
                User updatedUser = userService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }


    }

    @FXML
    private void deleteUsers(ActionEvent event) {
        List<CityGateStationEntity> cityGateStationEntities = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("تایید حذف");
        alert.setHeaderText(null);
        alert.setContentText("آیا اطمینان به حذف این مورد دارید؟");
        Optional<ButtonType> action = alert.showAndWait();



        if (action.get() == ButtonType.OK){

            for(CityGateStationEntity cityGateStationEntity : cityGateStationEntities){
                SecEntity secEntity = secController.getSecEntity();
                List<SecEntity> secEntities = cityGateStationEntity.getSec();
                if(secEntities.contains(secEntity)){
                    secController.setSecEntity(null);
                }


                cityGateStationEntity.setSec(null);
                cityGateStationService.save(cityGateStationEntity);

            }


            cityGateStationService.deleteInBatch(cityGateStationEntities);
        }

        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.getEditor().clear();
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        cbRole.getSelectionModel().clearSelection();
        email.clear();
        password.clear();
    }

    private void saveAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been created and \n" + getGenderTitle(user.getGender()) + " id is " + user.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
        return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
    }

    public String getGender() {
        return rbMale.isSelected() ? "Male" : "Female";
    }

    public String getRole() {
        return cbRole.getSelectionModel().getSelectedItem();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        cbRole.setItems(roles);

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }

    /*
     *  Set All userTable column properties
     */
    private void setColumnProperties() {
		/* Override date format in table
		 * cgsRegion.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        cgsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cgsProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        cgscity.setCellValueFactory(new PropertyValueFactory<>("city"));
        cgsRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        cgsAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cgsNominalCapacity.setCellValueFactory(new PropertyValueFactory<>("nominalCapacity"));
        colSelect.setCellFactory(cellFactory);
    }

    /*
     *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(cityGateStationService.findAll());

        userTable.setItems(userList);
    }

    /*
     * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) alert.setContentText("Please Select " + field);
        else {
            if (empty) alert.setContentText("Please Enter " + field);
            else alert.setContentText("Please Enter Valid " + field);
        }
        alert.showAndWait();
    }
}
