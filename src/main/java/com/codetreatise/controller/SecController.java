package com.codetreatise.controller;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.ResultEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.bean.unitNumber.Pressure;
import com.codetreatise.bean.unitNumber.Temperature;
import com.codetreatise.config.SpringFXMLLoader;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ConditionService;
import com.codetreatise.service.ResultService;
import com.codetreatise.service.SecService;
import com.codetreatise.service.UserService;
import com.codetreatise.view.FxmlView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import ir.behinehsazan.gasStation.model.station.StationLogic;
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
import sample.controller.base.BaseController;
import sample.util.FileLocation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class SecController extends BaseController implements Initializable {
    public Button resultBtn;
    public Button backButton;
    ObjectMapper mapper = new ObjectMapper();

    public Button calculationBtn;
    private SecEntity secEntity;
    @Autowired
    private ResultService resultService;

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
    @Autowired
    CalculateController calculateController;
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
                conditionEntity.setInputPressure(new Pressure(Double.parseDouble(gasInputPressure.getText()), gasInputPressureCombo.getValue().toString()));
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

        CityGateStationEntity  cityGateStationEntity = stageManager.getCityGateStationEntity();

        if(cityGateStationEntity == null){
            showAlert("توجه","اطلاعات ایستگاه تکمیل نشده است!", "لطفا اطلاعات ایستگاه را تکمیل فرمایید.", AlertType.WARNING);
            return;
        }
        else{
            if(cityGateStationEntity.getGasEntity()==null){
                showAlert("توجه","اطلاعات ایستگاه تکمیل نشده است!", "لطفا اطلاعات گاز را تکمیل فرمایید.", AlertType.WARNING);
                return;
            }
        }
        CityGateStationEntity copyCgs = null;
        try {
//            copyCgs = getCopy(cityGateStationEntity);
            copyCgs = (CityGateStationEntity) cityGateStationEntity.clone();
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<ConditionEntity> conditionList = secEntity.getConditions();
        ExecutorService service = Executors.newFixedThreadPool(500);
        CityGateStationEntity finalCopyCgs = copyCgs;
        service.submit(new Runnable() {
            public void run(){
                secEntity.setCalculated(SecEntity.CALCULATING);
                secEntity = secService.save(secEntity);
                try{
                    backGroundSecCalculation(finalCopyCgs,conditionList);
                }
                catch (Exception e){
                    secEntity.setCalculated(SecEntity.NOT_CALCULATED);
                    secEntity = secService.save(secEntity);
                }

            }

            private void backGroundSecCalculation(CityGateStationEntity finalCopyCgs, List<ConditionEntity> conditionList) {

                List<ConditionEntity> conditionToSave = new ArrayList<>();
                if(conditionList.size()>1) {
//            ConditionEntity firstCondtion = conditionList.get(0);
                    Iterator<ConditionEntity> it=conditionList.iterator();
                    ConditionEntity firstCondtion = conditionList.listIterator().next();
                    ConditionEntity secondCondtion;
                    ConditionEntity calOne;
//            while (conditionList.listIterator().hasNext()) {
                    while (it.hasNext()) {
//                secondCondtion = conditionList.listIterator().next();
                        secondCondtion = it.next();

                        calOne = new ConditionEntity();

                        try {
                            calOne.setEnvTemperature(new Temperature((firstCondtion.getEnvTemperature().getCostumeTemperature(Temperature.KELVIN) + secondCondtion.getEnvTemperature().getCostumeTemperature(Temperature.KELVIN)) / 2, Temperature.KELVIN));
                            calOne.setWindSpeed((firstCondtion.getWindSpeed() + secondCondtion.getWindSpeed()) / 2);
                            long hours = DAYS.between(firstCondtion.getTime(), secondCondtion.getTime()) * 24;
                            calOne.setDebiInput(new Debi((secondCondtion.getDebiInput().getCostumeDebi(Debi.M3) - firstCondtion.getDebiInput().getCostumeDebi(Debi.M3)) / hours, Debi.M3_PER_HOUR));
                            calOne.setInputTemperature(new Temperature((firstCondtion.getInputTemperature().getCostumeTemperature(Temperature.KELVIN) + secondCondtion.getInputTemperature().getCostumeTemperature(Temperature.KELVIN)) / 2, Temperature.KELVIN));
                            calOne.setInputPressure(new Pressure((firstCondtion.getInputPressure().getCostumePressure(Pressure.KPA) + secondCondtion.getInputPressure().getCostumePressure(Pressure.KPA)) / 2, Pressure.KPA));
                            calOne.setOutputTemperature(new Temperature((firstCondtion.getOutputTemperature().getCostumeTemperature(Temperature.KELVIN) + secondCondtion.getOutputTemperature().getCostumeTemperature(Temperature.KELVIN)) / 2, Temperature.KELVIN));
                            calOne.setOutputPressure(new Pressure((firstCondtion.getOutputPressure().getCostumePressure(Pressure.KPA) + secondCondtion.getOutputPressure().getCostumePressure(Pressure.KPA)) / 2, Pressure.KPA));
                            calOne.setTime(secondCondtion.getTime());
                        }
                        catch (Exception e){

                            e.printStackTrace();

                        }

                        if(calOne!=null) {
                            finalCopyCgs.setCondition(calOne);
                            List<StationLogic> result = null;
                            List<StationLogic> result15 = null;
                            List<StationLogic> result8 = null;
                            try {
                                finalCopyCgs.setRequiredHydrate(true);
                                result = calculateController.calculate(finalCopyCgs);
                                finalCopyCgs.getCondition().setOutputTemperature(new Temperature(15.5, Temperature.CELSIUS));
                                finalCopyCgs.setRequiredHydrate(false);
                                result15 = calculateController.calculate(finalCopyCgs);
                                finalCopyCgs.getCondition().setOutputTemperature(new Temperature(8.0, Temperature.CELSIUS));
                                finalCopyCgs.setRequiredHydrate(false);
                                result8 = calculateController.calculate(finalCopyCgs);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }

                            ResultEntity resultEntity = secondCondtion.getResult();
                            if(resultEntity==null){
                                resultEntity = new ResultEntity();
                            }
                            resultEntity.setSingleCalculation(result);
                            resultEntity.setFifteenTemp(result15);
                            resultEntity.setEightTemp(result8);
                            resultEntity = resultService.save(resultEntity);
                            secondCondtion.setResult(resultEntity);
                            conditionService.save(secondCondtion);
                            conditionToSave.add(secondCondtion);

                        }





                        firstCondtion = secondCondtion;

                    }
                }



                secEntity.setCalculated(SecEntity.DOWN);
                secEntity = secService.save(secEntity);


            }
        });


//        Alert alert = new Alert(AlertType.WARNING);
//        alert.setTitle("محاسبات در حال انجام است");
//        alert.setHeaderText(null);
//        alert.setContentText("محاسبات در حال انجام است.");
//
//        alert.showAndWait();
        return;



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

    private CityGateStationEntity getCopy(CityGateStationEntity obj) throws IOException {
        TokenBuffer tb = new TokenBuffer(null, false);

//        mapper.writeValue(tb, obj);
        CityGateStationEntity copycgs = mapper.readValue(tb.asParser(), CityGateStationEntity.class);
        return copycgs;
//        copyGas.setComponent(gas.getComponent());
//        ObjectNode gasNode = (ObjectNode) mapper.readTree(mapper.writeValueAsString(copyGas));
//        gasNode.remove("component");
//        gasNode.remove("t");
//        gasNode.remove("p");
//        return  gasNode;
    }

    public void showResult(ActionEvent actionEvent) {
        if(stageManager.getCityGateStationEntity() == null){
            showAlert("توجه","اطلاعات ایستگاه تکمیل نشده است!", "لطفا اطلاعات ایستگاه را تکمیل فرمایید.", AlertType.WARNING);
            return;
        }
        else{
            if(stageManager.getCityGateStationEntity().getGasEntity()==null){
                showAlert("توجه","اطلاعات ایستگاه تکمیل نشده است!", "لطفا اطلاعات گاز را تکمیل فرمایید.", AlertType.WARNING);
                return;
            }
        }
        if(secEntity==null) {
            showNullSec();
            return;
        }

        stageManager.switchScene(FxmlView.SHOW_CHART);
    }

    @Override
    public void setOnShow() {

    }
}
