package com.codetreatise.controller;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.bean.unitNumber.Pressure;
import com.codetreatise.bean.unitNumber.Temperature;
import com.codetreatise.config.SpringFXMLLoader;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ConditionService;
import com.codetreatise.service.SecService;
import com.codetreatise.service.UserService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
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
import java.util.List;
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

    public Button calculationBtn;
    private SecEntity secEntity;

    public SecEntity getSecEntity() {
        return secEntity;
    }

    public void setSecEntity(SecEntity secEntity) {
        this.secEntity = secEntity;
    }

    public ComboBox envTempCombo;
    public TextField envTemp;
    public JFXTextField gasOutputPressure;
    public ComboBox gasOutputPressureCombo;
    public JFXTextField gasOutputTemp;
    public ComboBox gasOutputTempCombo;
    public JFXTextField gasInputPressure;
    public ComboBox gasInputPressureCombo;
    public JFXTextField gasInputTemp;
    public ComboBox gasInputTempCombo;
    public JFXTextField counter;
    public JFXTextField windSpeed;

    public TableColumn<ConditionEntity, String> colOutputPressure;
    public TableColumn<ConditionEntity, String> colOutputTemp;
    public TableColumn<ConditionEntity, String> colInputPressure;
    public TableColumn<ConditionEntity, String> colInputTemp;
    public TableColumn<ConditionEntity, String> colDebi;
    public TableColumn<ConditionEntity, String> colWindSpeed;
    public TableColumn<ConditionEntity, String> colEnvTemp;
    public TableColumn<ConditionEntity, LocalDate> colDate;
    public MenuItem updateCondition;
    @Lazy
    @Autowired
    StageManager stageManager;
    @Autowired
    SecService secService;
    @FXML
    public Button chooseFileBtn;
    @FXML
    public JFXDrawer drawer;
    @FXML
    public JFXHamburger hamburger;

    @FXML
    private DatePicker time;


    Callback<TableColumn<ConditionEntity, Boolean>, TableCell<ConditionEntity, Boolean>> conditionCellFactory =
            new Callback<TableColumn<ConditionEntity, Boolean>, TableCell<ConditionEntity, Boolean>>() {
                @Override
                public TableCell<ConditionEntity, Boolean> call(final TableColumn<ConditionEntity, Boolean> param) {
                    final TableCell<ConditionEntity, Boolean> cell = new TableCell<ConditionEntity, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/pencil-edit-button.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    ConditionEntity conditionEntity = getTableView().getItems().get(getIndex());

                                    updateCondition(conditionEntity);
//                                    u(user);
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

                        private void updateCondition(ConditionEntity conditionEntity) {
                            conditionId = conditionEntity.getId();
                            envTempCombo.getSelectionModel().select(conditionEntity.getEnvTemperature().getUnit());
                            gasInputTempCombo.getSelectionModel().select(conditionEntity.getInputTemperature().getUnit());
                            gasInputPressureCombo.getSelectionModel().select(conditionEntity.getInputPressure().getUnit());
                            gasOutputTempCombo.getSelectionModel().select(conditionEntity.getOutputTemperature().getUnit());
                            gasOutputPressureCombo.getSelectionModel().select(conditionEntity.getOutputPressure().getUnit());
                            envTemp.setText(String.valueOf(conditionEntity.getEnvTemperature().getTemperature()));
                            windSpeed.setText(String.valueOf(conditionEntity.getWindSpeed()));
                            counter.setText(String.valueOf(conditionEntity.getDebiInput().getDebi()));
                            gasOutputPressure.setText(String.valueOf(conditionEntity.getOutputPressure().getPressure()));
                            gasInputTemp.setText(String.valueOf(conditionEntity.getInputTemperature().getTemperature()));
                            gasInputPressure.setText(String.valueOf(conditionEntity.getInputPressure().getPressure()));
                            gasOutputTemp.setText(String.valueOf(conditionEntity.getOutputTemperature().getTemperature()));
                            time.setValue(conditionEntity.getTime());
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
    private Button saveCondition;
    @FXML
    private TableView<ConditionEntity> conditionTable;
    @FXML
    private TableColumn<ConditionEntity, Boolean> colEdit;
    @FXML
    private MenuItem deleteConditions;

    @Autowired
    private UserService userService;
    @Autowired
    private ConditionService conditionService;
    private ObservableList<ConditionEntity> conditionList = FXCollections.observableArrayList();
    private Long conditionId;


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

    }

    @FXML
    private void saveCondition(ActionEvent event) {
        if(secEntity==null) {
            showNullSec();
            return;
        }
        if(secEntity.getExcelFile()!=null || secEntity.getFileLocation()!=null || secEntity.getFileName()!=null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText(" دارای فایل اکسل است.");
            alert.setContentText("امکان حذف یا اضافه و ویرایش وجود ندارد. ");

            alert.showAndWait();
            return;
        }


        ConditionEntity conditionEntity;
        List<ConditionEntity> conditionEntities;
        if(conditionId!=null) {
            if (!envTemp.getText().equals("") && !counter.getText().equals("") &&
                    !gasInputTemp.getText().equals("") && gasInputPressure.getText().equals("") &&
                    !gasOutputPressure.getText().equals("") && !gasOutputTemp.getText().equals("")) {

                conditionEntity = new ConditionEntity();
                conditionEntity.setEnvTemperature(new Temperature(Double.parseDouble(envTemp.getText()), envTempCombo.getValue().toString()));
                conditionEntity.setDebiInput(new Debi(Double.parseDouble(counter.getText()), Debi.M3));
                conditionEntity.setInputTemperature(new Temperature(Double.parseDouble(gasInputTemp.getText()), gasInputTempCombo.getValue().toString()));
                conditionEntity.setInputPressure(new Pressure(Double.parseDouble(gasInputTemp.getText()), gasInputPressureCombo.getValue().toString()));
                conditionEntity.setOutputTemperature(new Temperature(Double.parseDouble(gasOutputTemp.getText()), gasOutputTempCombo.getValue().toString()));
                conditionEntity.setOutputPressure(new Pressure(Double.parseDouble(gasOutputPressure.getText()), gasOutputPressureCombo.getValue().toString()));
                conditionEntity.setWindSpeed(Double.parseDouble(windSpeed.getText().equals("")? "0.0": windSpeed.getText()));
                conditionEntity.setTime(time.getValue());
                conditionEntity = conditionService.save(conditionEntity);
                conditionEntities = secEntity.getConditions();
                conditionEntities.add(conditionEntity);
                secEntity.setConditions(conditionEntities);
                secService.save(secEntity);
                secEntity = secService.find(secEntity.getId());




                saveAlert(conditionEntity);


            }
            else {
                conditionEntity = conditionService.find(conditionId);
                conditionEntity.setEnvTemperature(new Temperature(Double.parseDouble(envTemp.getText()), envTempCombo.getValue().toString()));
                conditionEntity.setDebiInput(new Debi(Double.parseDouble(counter.getText()), Debi.M3));
                conditionEntity.setInputTemperature(new Temperature(Double.parseDouble(gasInputTemp.getText()), gasInputTempCombo.getValue().toString()));
                conditionEntity.setInputPressure(new Pressure(Double.parseDouble(gasInputTemp.getText()), gasInputPressureCombo.getValue().toString()));
                conditionEntity.setOutputTemperature(new Temperature(Double.parseDouble(gasOutputTemp.getText()), gasOutputTempCombo.getValue().toString()));
                conditionEntity.setOutputPressure(new Pressure(Double.parseDouble(gasOutputPressure.getText()), gasOutputPressureCombo.getValue().toString()));
                conditionEntity.setWindSpeed(Double.parseDouble(windSpeed.getText().equals("")? "0.0": windSpeed.getText()));
                conditionEntity.setTime(time.getValue()==null?LocalDate.now(): time.getValue());
                conditionEntity = conditionService.save(conditionEntity);
                conditionEntities = secEntity.getConditions();
                conditionEntities.add(conditionEntity);
                secEntity.setConditions(conditionEntities);
                secService.save(secEntity);
                secEntity = secService.find(secEntity.getId());



                updateAlert(conditionEntity);
            }
        }
        else{

            conditionEntity = new ConditionEntity();
            conditionEntity.setEnvTemperature(new Temperature(Double.parseDouble(envTemp.getText()), envTempCombo.getValue().toString()));
            conditionEntity.setDebiInput(new Debi(Double.parseDouble(counter.getText()), Debi.M3));
            conditionEntity.setInputTemperature(new Temperature(Double.parseDouble(gasInputTemp.getText()), gasInputTempCombo.getValue().toString()));
            conditionEntity.setInputPressure(new Pressure(Double.parseDouble(gasInputTemp.getText()), gasInputPressureCombo.getValue().toString()));
            conditionEntity.setOutputTemperature(new Temperature(Double.parseDouble(gasOutputTemp.getText()), gasOutputTempCombo.getValue().toString()));
            conditionEntity.setOutputPressure(new Pressure(Double.parseDouble(gasOutputPressure.getText()), gasOutputPressureCombo.getValue().toString()));
            conditionEntity.setWindSpeed(Double.parseDouble(windSpeed.getText().equals("")? "0.0": windSpeed.getText()));
            conditionEntity.setTime(time.getValue()==null? LocalDate.now():time.getValue());
            conditionEntity = conditionService.save(conditionEntity);
            conditionEntities = secEntity.getConditions();
            conditionEntities.add(conditionEntity);
            secEntity.setConditions(conditionEntities);
            secService.save(secEntity);
            secEntity = secService.find(secEntity.getId());



            saveAlert(conditionEntity);
        }

            clearFields();
            loadConditionDetails();





    }

    @FXML
    private void deleteCondition(ActionEvent event) {
        if(secEntity!=null && (secEntity.getExcelFile()!=null || secEntity.getFileLocation()!=null || secEntity.getFileName()!=null)){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText(" دارای فایل اکسل است.");
            alert.setContentText("امکان حذف یا اضافه و ویرایش وجود ندارد. ");

            alert.showAndWait();
            return;
        }
        List<ConditionEntity> conditionEntityList = conditionTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("اطمینان از حذف");
        alert.setHeaderText(null);
        alert.setContentText("آیا اطمینان به حذف این موارد دارید؟");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) conditionService.deleteInBatch(conditionEntityList);

        clearFields();
        loadConditionDetails();
    }

    private void clearFields() {
        envTempCombo.getSelectionModel().select(Temperature.CELSIUS);
        gasInputTempCombo.getSelectionModel().select(Temperature.CELSIUS);
        gasInputPressureCombo.getSelectionModel().select(Pressure.PSI_GAUGE);
        gasOutputTempCombo.getSelectionModel().select(Temperature.CELSIUS);
        gasOutputPressureCombo.getSelectionModel().select(Pressure.PSI_GAUGE);

        envTemp.clear();
        windSpeed.clear();
        counter.clear();
        gasOutputPressure.clear();
        gasInputTemp.clear();
        gasInputPressure.clear();
        gasOutputTemp.clear();
        time.getEditor().clear();
        conditionId = null;
    }

    private void saveAlert(ConditionEntity condition) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("عملیات موفق");
        alert.setHeaderText(null);
        alert.setContentText("شرایط با موفقیت ثبت شد.");
        alert.showAndWait();
    }

    private void updateAlert(ConditionEntity user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("عملیات موفق");
        alert.setHeaderText(null);
        alert.setContentText("شرایط با موفقیت به روز رسانی شد.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
        return (gender.equals("Male")) ? "his" : "her";
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
//            transition.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
        drawer.setOnDrawerOpening(event ->
        {
            AnchorPane.setRightAnchor(drawer, 0.0);
            AnchorPane.setLeftAnchor(drawer, 0.0);
            AnchorPane.setTopAnchor(drawer, 0.0);
            AnchorPane.setBottomAnchor(drawer, 0.0);
        });

        drawer.setOnDrawerClosed(event ->
        {
            AnchorPane.clearConstraints(drawer);

            AnchorPane.setLeftAnchor(drawer, -306.0);
            AnchorPane.setTopAnchor(drawer, 0.0);
            AnchorPane.setBottomAnchor(drawer, 0.0);
        });

        envTempCombo.getItems().removeAll(envTempCombo.getItems());
        envTempCombo.getItems().addAll(Temperature.CELSIUS, Temperature.FAHRENHEIT,Temperature.KELVIN, Temperature.RANKINE);
        envTempCombo.getSelectionModel().select(Temperature.CELSIUS);

        gasInputTempCombo.getItems().removeAll(gasInputTempCombo.getItems());
        gasInputTempCombo.getItems().addAll(Temperature.CELSIUS, Temperature.FAHRENHEIT,Temperature.KELVIN, Temperature.RANKINE);
        gasInputTempCombo.getSelectionModel().select(Temperature.CELSIUS);

        gasInputPressureCombo.getItems().removeAll(gasInputPressureCombo.getItems());
        gasInputPressureCombo.getItems().addAll(Pressure.KPA, Pressure.MPA, Pressure.PSI, Pressure.KPA_GAUGE, Pressure.MPA_GAUGE, Pressure.PSI_GAUGE);
        gasInputPressureCombo.getSelectionModel().select(Pressure.PSI_GAUGE);

        gasOutputTempCombo.getItems().removeAll(gasOutputTempCombo.getItems());
        gasOutputTempCombo.getItems().addAll(Temperature.CELSIUS, Temperature.FAHRENHEIT,Temperature.KELVIN, Temperature.RANKINE);
        gasOutputTempCombo.getSelectionModel().select(Temperature.CELSIUS);

        gasOutputPressureCombo.getItems().removeAll(gasOutputPressureCombo.getItems());
        gasOutputPressureCombo.getItems().addAll(Pressure.KPA, Pressure.MPA, Pressure.PSI, Pressure.KPA_GAUGE, Pressure.MPA_GAUGE, Pressure.PSI_GAUGE);
        gasOutputPressureCombo.getSelectionModel().select(Pressure.PSI_GAUGE);




        conditionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        clearFields();

        setColumnProperties();

        // Add all users into table
        loadConditionDetails();
    }



    /*
     *  Set All conditionTable column properties
     */
    private void setColumnProperties() {


        colOutputPressure.setCellValueFactory(new PropertyValueFactory<>("outputPressureWithUnit"));
        colOutputTemp.setCellValueFactory(new PropertyValueFactory<>("outputTempWithUnit"));
        colInputPressure.setCellValueFactory(new PropertyValueFactory<>("inputPressureWithUnit"));
        colInputTemp.setCellValueFactory(new PropertyValueFactory<>("inputTempWithUnit"));
        colDebi.setCellValueFactory(new PropertyValueFactory<>("debiWithUnit"));
        colWindSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        colEnvTemp.setCellValueFactory(new PropertyValueFactory<>("envTempWithUnit"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("time"));
        colEdit.setCellFactory(conditionCellFactory);
    }

    /*
     *  Add All users to observable list and update table
     */
    private void loadConditionDetails() {
        conditionList.clear();
        if(secEntity != null) {
            if(secEntity.getId() !=null){
                secEntity = secService.find(secEntity.getId());
            }
            conditionList.addAll(conditionService.findBySec(secEntity));
        }

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

    public void calculateSec(ActionEvent actionEvent) {
        if(secEntity == null) {
            showNullSec();
            return;
        }

    }

    private void showNullSec() {


            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("انتخاب مصرف انرژی ویژه");
            alert.setHeaderText(null);
            alert.setContentText("مصرف انرژی ویژه (SEC) انتخاب نشده است. ابتدا باید مصرف انرژی ویژه (SEC) انتخاب شود.");

            alert.showAndWait();

            drawer.open();

            return;

    }
}
