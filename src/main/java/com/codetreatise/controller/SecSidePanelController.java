package com.codetreatise.controller;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import com.codetreatise.service.SecService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.util.FileLocation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class SecSidePanelController implements Initializable {

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

        if (action.get() == ButtonType.OK) secService.deleteInBatch(users);

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
                if(validateExcelFile(chooseFileLocation)) {
                    SecEntity fileSecEntity = new SecEntity();
                    fileSecEntity.setExcelFile(getExcelFile());

                    fileSecEntity.setTime(getDate() == null ? LocalDate.now() : getDate());
                    fileSecEntity.setName(name.getText());
                    fileSecEntity.setFileLocation(chooseFileLocation);
                    fileSecEntity.setFileName(chooseFileName);

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

    private boolean validateExcelFile(String chooseFileLocation) {
        return true;
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
