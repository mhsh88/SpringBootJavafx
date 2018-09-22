package com.codetreatise.controller;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.bean.unitNumber.Pressure;
import com.codetreatise.bean.unitNumber.Temperature;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import com.codetreatise.service.ConditionService;
import com.codetreatise.service.SecService;
import com.jfoenix.controls.JFXButton;
import ir.huri.jcal.JalaliCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.util.FileLocation;
import sample.util.excel.ExcelPOIHelper;
import sample.util.excel.MyCell;

import javax.management.BadAttributeValueExpException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class SecSidePanelController implements Initializable {

    public TableColumn colSelect;
    private SecEntity secEntity;
    private String chooseFileName;
    private String chooseFileLocation;
    @Lazy
    @Autowired
    StageManager stageManager;

    @Autowired
    SecService secService;

    @Autowired
    CityGateStationService cityGateStationService;

    @Autowired
    SecController secController;

    @Autowired
    ConditionService conditionService;

    @FXML
    private Button save;
    @FXML
    private Button reset;
    @FXML
    private Button chooseFileBtn;
    @FXML
    private TableView<SecEntity> secTable;
    @FXML
    private TableColumn<SecEntity, String> colFile;
    @FXML
    private TableColumn<SecEntity, LocalDate> colDate;
    @FXML
    private TableColumn<SecEntity, String> colSecName;
    @FXML
    private MenuItem deleteSec;
    @FXML
    private TextField name;
    @FXML
    private DatePicker time;

    private ObservableList<SecEntity> secList = FXCollections.observableArrayList();

    Callback<TableColumn<SecEntity, Boolean>, TableCell<SecEntity, Boolean>> cellFactory =
            new Callback<TableColumn<SecEntity, Boolean>, TableCell<SecEntity, Boolean>>() {
                @Override
                public TableCell<SecEntity, Boolean> call(final TableColumn<SecEntity, Boolean> param) {
                    final TableCell<SecEntity, Boolean> cell = new TableCell<SecEntity, Boolean>() {
                        final Button btnEdit = new Button();
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/point-at.png"));

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    SecEntity secEntity = getTableView().getItems().get(getIndex());
                                    secController.setSecEntity(secEntity);
                                    secController.drawer.close();
                                    secController.initialize(null, null);

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

                    };
                    return cell;
                }
            };
    @Autowired
    private ExcelPOIHelper excelPOIHelper;

    public SecEntity getSecEntity() {
        return secEntity;
    }

    public void setSecEntity(SecEntity secEntity) {
        this.secEntity = secEntity;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(chooseFileName!=null){
            chooseFileBtn.setText(chooseFileName);
        }

        secTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setColumnProperties();
        loadSecDetails();

    }


    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    public void deletSec(ActionEvent actionEvent) {
        List<SecEntity> users = secTable.getSelectionModel().getSelectedItems();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تایید حذف");
        alert.setHeaderText(null);
        alert.setContentText("آیا اطمینان به حذف این مورد دارید؟");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {

            for(SecEntity secEntity : users){

                secEntity.setConditions(null);
                secService.save(secEntity);

            }
            if(users.contains(secController.getSecEntity())){
                secController.setSecEntity(null);
                secController.initialize(null,null);
            }
            secService.deleteInBatch(users);
        }

        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity!= null){cityGateStationEntity = cityGateStationService.find(cityGateStationEntity.getId());}
        stageManager.setCityGateStationEntity(cityGateStationEntity);
        loadSecDetails();
    }

    public void reset(ActionEvent actionEvent) {
        name.setText(null);
        time.getEditor().clear();
        chooseFileBtn.setText("Choose File");
        chooseFileLocation = null;
        chooseFileName = null;
        secEntity = null;
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
            chooseFileName = file.getName();
            chooseFileLocation = file.getAbsolutePath();
            chooseFileBtn.setText(chooseFileName);
        }

        if (file != null) {

            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
            if (fileExtension.equals("xls") || fileExtension.equals("XLS")) {

            }
            else if (fileExtension.equals("xlsx") || fileExtension.equals("XLSX")) {

            }

            System.out.println(fileName);

        }
    }


    public void save(ActionEvent actionEvent) throws IOException {
        if(secEntity == null ){
            if( chooseFileLocation == null){
                secEntity = new SecEntity();
                secEntity.setName(name.getText());
                secEntity.setTime(getDate()==null? LocalDate.now() : getDate());
            }
            else if(chooseFileName != null){
                List<ConditionEntity> list = validateExcelFile(chooseFileLocation);
                if(list!=null) {
                    SecEntity fileSecEntity = new SecEntity();
                    fileSecEntity.setExcelFile(getExcelFile());

                    fileSecEntity.setTime(getDate() == null ? LocalDate.now() : getDate());
                    fileSecEntity.setName(name.getText());
                    fileSecEntity.setFileLocation(chooseFileLocation);
                    fileSecEntity.setFileName(chooseFileName);
                    list = conditionService.save(list);
                    fileSecEntity.setConditions(list);
                    secEntity = fileSecEntity;
                }

            }

        }
        else{
            secEntity = new SecEntity();
            secEntity.setName(name.getText());
            secEntity.setTime(getDate() == null ? LocalDate.now() : getDate());

        }
        List<SecEntity> secList;
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity==null){
            cityGateStationEntity = new CityGateStationEntity();
             secList = new ArrayList<>();
             cityGateStationEntity.setSec(secList);

        }
        secEntity = secService.save(secEntity);
        secList = cityGateStationEntity.getSec();
        if(secList == null){
            secList = new ArrayList<>();
        }
        secList.add(secEntity);
        cityGateStationEntity.setSec(secList);
        cityGateStationEntity = cityGateStationService.save(cityGateStationEntity);
        stageManager.setCityGateStationEntity(cityGateStationEntity);
        loadSecDetails();
        name.setText(null);
        time.getEditor().clear();
        chooseFileBtn.setText("Choose File");
        chooseFileLocation = null;
        chooseFileName = null;
        secEntity = null;

    }

    private byte[] getExcelFile() throws IOException {
        if(chooseFileLocation!=null){
            File file = new File(chooseFileLocation);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        }
        return new byte[0];
    }

    private List<ConditionEntity> validateExcelFile(String chooseFileLocation) {
        List<ConditionEntity> conditionEntities = null;
        try {
            Map<Integer, List<MyCell>> data
                    = excelPOIHelper.readExcel(chooseFileLocation);
            return conditionEntities = checkExcelData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conditionEntities;
    }

    private List<ConditionEntity> checkExcelData(Map<Integer, List<MyCell>> data) {

        List<ConditionEntity> conditionEntities = new ArrayList<>();
            for(int i : data.keySet()) {
                try {
                    List<MyCell> list = data.get(i);
                    ConditionEntity conditionEntity = new ConditionEntity();
                    conditionEntity.setEnvTemperature(new Temperature(Double.parseDouble(list.get(0).getContent()), Temperature.CELSIUS));
                    conditionEntity.setWindSpeed(Double.parseDouble(list.get(1).getContent()));
                    conditionEntity.setDebiInput(new Debi(Double.parseDouble(list.get(2).getContent()), Debi.M3));
                    conditionEntity.setInputTemperature(new Temperature(Double.parseDouble(list.get(3).getContent()), Temperature.CELSIUS));
                    conditionEntity.setInputPressure(new Pressure(Double.parseDouble(list.get(4).getContent()), Pressure.PSI_GAUGE));
                    conditionEntity.setOutputTemperature(new Temperature(Double.parseDouble(list.get(5).getContent()), Temperature.CELSIUS));
                    conditionEntity.setOutputPressure(new Pressure(Double.parseDouble(list.get(6).getContent()), Pressure.PSI_GAUGE));
                    LocalDate localDate = getLocaleDate(list.get(7).getContent());
                    conditionEntity.setTime(localDate);
                    conditionEntities.add(conditionEntity);




                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return conditionEntities;
    }

    private LocalDate getLocaleDate(String content) throws BadAttributeValueExpException {
        try {
            if (content != null) {
                String[] timeString = content.split("/");
                JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]), Integer.parseInt(timeString[2]));
                return getLocalDateFromDate(jalaliCalendar.toGregorian().getTime());
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
    public static LocalDate getLocalDateFromDate(Date date){
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public LocalDate getDate() {
        return time.getValue();
    }

    /*
     *  Set All conditionTable column properties
     */
    private void setColumnProperties() {


        colSecName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("time"));
        colFile.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        colSelect.setCellFactory(cellFactory);
//        colEdit.setCellFactory(conditionCellFactory);
    }

    private void loadSecDetails() {

        secList.clear();
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity!=null) {
            secList.addAll(secService.findByCityGateStation(cityGateStationEntity));
//            secList.addAll(cityGateStationEntity.getSec());
        }

        secTable.setItems(secList);
    }
}
