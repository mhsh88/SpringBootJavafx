package com.codetreatise.controller;

import com.codetreatise.bean.User;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.config.SpringFXMLLoader;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ConditionService;
import com.codetreatise.service.UserService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.util.FileLocation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class SecController implements Initializable {

    @Lazy
    @Autowired
    StageManager stageManager;
    @FXML
    public Button chooseFileBtn;
    @FXML
    public TableView<SecEntity> secTable;
    @FXML
    public TableColumn<SecEntity, String> colFile;
    @FXML
    public TableColumn<SecEntity, LocalDate> colDate;
    @FXML
    public TableColumn<SecEntity, String> colSecName;
    @FXML
    public TableColumn<SecEntity, Boolean> colSecEdit;
    @FXML
    public JFXDrawer drawer;
    @FXML
    public JFXHamburger hamburger;


    @FXML
    private Button btnLogout;

    @FXML
    private Label userId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField name;

    @FXML
    private DatePicker time;


    Callback<TableColumn<SecEntity, Boolean>, TableCell<SecEntity, Boolean>> secCellFactory =
            new Callback<TableColumn<SecEntity, Boolean>, TableCell<SecEntity, Boolean>>() {
                @Override
                public TableCell<SecEntity, Boolean> call(final TableColumn<SecEntity, Boolean> param) {
                    final TableCell<SecEntity, Boolean> cell = new TableCell<SecEntity, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    SecEntity SecEntity = getTableView().getItems().get(getIndex());
//                                    updateUser(user);
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

                        private void updateUser(User user) {
                            userId.setText(Long.toString(user.getId()));
                            firstName.setText(user.getFirstName());
                            name.setText(user.getLastName());
                            time.setValue(user.getDob());
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<ConditionEntity, Boolean>, TableCell<ConditionEntity, Boolean>> conditionCellFactory =
            new Callback<TableColumn<ConditionEntity, Boolean>, TableCell<ConditionEntity, Boolean>>() {
                @Override
                public TableCell<ConditionEntity, Boolean> call(final TableColumn<ConditionEntity, Boolean> param) {
                    final TableCell<ConditionEntity, Boolean> cell = new TableCell<ConditionEntity, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    ConditionEntity conditionEntity = getTableView().getItems().get(getIndex());
//                                    updateUser(user);
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

                        private void updateUser(User user) {
                            userId.setText(Long.toString(user.getId()));
                            firstName.setText(user.getFirstName());
                            name.setText(user.getLastName());
                            time.setValue(user.getDob());
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
    private TableView<ConditionEntity> conditionTable;
    @FXML
    private TableColumn<ConditionEntity, Long> colUserId;
    @FXML
    private TableColumn<ConditionEntity, String> colFirstName;
    @FXML
    private TableColumn<ConditionEntity, String> colLastName;
    @FXML
    private TableColumn<ConditionEntity, LocalDate> colDOB;
    @FXML
    private TableColumn<ConditionEntity, String> colGender;
    @FXML
    private TableColumn<ConditionEntity, String> colRole;
    @FXML
    private TableColumn<ConditionEntity, String> colEmail;
    @FXML
    private TableColumn<ConditionEntity, Boolean> colEdit;
    @FXML
    private MenuItem deleteUsers;

    @Autowired
    private UserService userService;
    @Autowired
    private ConditionService conditionService;
    private ObservableList<ConditionEntity> conditionList = FXCollections.observableArrayList();


    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+") &&
                emptyValidation("DOB", time.getEditor().getText().isEmpty())) {

            if (userId.getText() == null || userId.getText() == "") {
                if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                        emptyValidation("Password", getPassword().isEmpty())) {

                    User user = new User();
                    user.setFirstName(getFirstName());
                    user.setLastName(getLastName());
                    user.setDob(getDob());
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
                User updatedUser = userService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }


    }

    @FXML
    private void deleteUsers(ActionEvent event) {
//        List<User> users = conditionTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

//        if (action.get() == ButtonType.OK) userService.deleteInBatch(users);

        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        name.clear();
        time.getEditor().clear();
//        rbMale.setSelected(true);
//        rbFemale.setSelected(false);
//        cbRole.getSelectionModel().clearSelection();
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
        return name.getText();
    }

    public LocalDate getDob() {
        return time.getValue();
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
        try {
            SpringFXMLLoader springFXMLLoader = stageManager.getSpringFXMLLoader();
            VBox box = (VBox) springFXMLLoader.load("/fxml/SecSidePanel.fxml");
//            FXMLLoader loader = new FXMLLoader();
//            VBox box = loader.load();
//            SecSidePanelController controller = loader.getController();
//            controller.setCallback(this);
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(SecController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });


        secTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setSecColumn();
        conditionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }

    private void setSecColumn() {

        colSecName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("time"));
        colFile.setCellValueFactory(new PropertyValueFactory<>("excelFile"));
//        colSecEdit.setCellFactory(secCellFactory);
    }

    /*
     *  Set All conditionTable column properties
     */
    private void setColumnProperties() {
		/* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
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

        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellFactory(conditionCellFactory);
    }

    /*
     *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        conditionList.clear();
        conditionList.addAll(conditionService.findAll());

        conditionTable.setItems(conditionList);
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

    public void backAction(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.STATION);
    }

    public void chooseFile(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
//        FileChooserBuilder fcb = FileChooserBuilder.create();
//        FileChooser fc = fcb.title("Open Dialog").build();
        chooser.setTitle("Select File To Open");

//Set extension filter
        FileChooser.ExtensionFilter excelFilter =
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx");


        chooser.getExtensionFilters().addAll(excelFilter);
        chooser.setInitialDirectory(new FileLocation().getFileLocation());

        File file = chooser.showOpenDialog(new Stage());

        if (file == null) {
            return;
        }
        String dir = "";
        if (file.isDirectory()) {
            dir = file.getAbsolutePath();
        } else {
            dir = file.getAbsolutePath().replaceAll(file.getName(), "");
        }

        if (file != null) {

            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
            if (fileExtension.equals("cgs") || fileExtension.equals("CGS")) {
            }
        }
    }
}
