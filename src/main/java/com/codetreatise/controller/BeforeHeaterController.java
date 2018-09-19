package com.codetreatise.controller;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.PipeSpecificationsEntity;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import com.codetreatise.service.PipeSpecificationService;
import com.codetreatise.view.FxmlView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;
import sample.model.Station;
import sample.model.base.BaseModel;
import sample.model.pipeLine.PipeLine;
import sample.model.pipeLine.PipeSize;

import java.io.IOException;
import java.util.Map;

@Controller
public class BeforeHeaterController extends BaseController{

    @Lazy
    @Autowired
    StageManager stageManager;

    @Autowired
    PipeSpecificationService pipeSpecificationService;

    @Autowired
    CityGateStationService cityGateStationService;

    

    public Button okButton;
    public Button clearButton;
    public Button cancelButton;
    public TextField insulationFactorTextField = new TextField();
    public ComboBox insulationThicknessComboBox;
    public TextField insulationThicknessTextField;
    public RadioButton insulationRadioButton;
    public ComboBox mmOrInchComboBox;
    public ComboBox nominalDiameterComboBox;
    public ComboBox outerDiameterComboBox;
    public ComboBox wallThicknessComboBox;
    public TextField lineLengthTextField;
    public ComboBox lineLengthComboBox;
    public Label insulationFactorLabel;
    public Label insulationFactorDimensionLabel;
    public Label insulationThicknessLabel;


    @FXML
    TextField textField = new TextField();


    @FXML
    public void initialize() throws IOException {
        insulationThicknessComboBox.getItems().removeAll();
        insulationThicknessComboBox.getItems().addAll(PipeSpecificationsEntity.CENTIMETER, PipeSpecificationsEntity.INCH);
        insulationThicknessComboBox.getSelectionModel().select(PipeSpecificationsEntity.CENTIMETER);
//        insulationThicknessComboBox.getItems().removeAll(wallThicknessComboBox.getItems());
//        insulationThicknessComboBox.getItems().addAll("سانتی متر (cm)", "اینچ (inch)");
//        insulationThicknessComboBox.getSelectionModel().select("سانتی متر (cm)");
//        mmOrInchComboBox.getItems().removeAll(wallThicknessComboBox.getItems());
//        mmOrInchComboBox.getItems().addAll("1/8", "¼", "3/8", "½", "¾", "1", "1 ¼", "1 ½", "2", "2 ½", "3", "3 ½", "4", "5", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40"
//                , "42", "44", "46", "48");
        mmOrInchComboBox.getItems().removeAll();
        mmOrInchComboBox.getItems().addAll( "2","3","4","6","8","10","12","16","20","24","30");
        mmOrInchComboBox.getSelectionModel().select("8");

        //        {"1/8","¼","3/8","½","¾","1","1 ¼","1 ½","2","2 ½","3","3 ½","4","5","6","8","10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40"
//                ,"42","44","46","48"}


//        insulationFactorTextField.editableProperty().bind(insulationRadioButton.selectedProperty());


        insulationRadioButton.setOnAction(actionEvent -> {
            if (insulationRadioButton.isSelected()) {
                insulationThicknessLabel.setDisable(false);
                insulationThicknessTextField.setDisable(false);
                insulationThicknessComboBox.setDisable(false);
                insulationThicknessTextField.setDisable(false);
                insulationFactorLabel.setDisable(false);
                insulationFactorTextField.setDisable(false);
                insulationFactorDimensionLabel.setDisable(false);

            } else if (!insulationRadioButton.isSelected()) {
                insulationThicknessLabel.setDisable(true);
                insulationThicknessTextField.setDisable(true);
                insulationThicknessComboBox.setDisable(true);
                insulationThicknessTextField.setDisable(true);
                insulationThicknessTextField.clear();
                insulationFactorLabel.setDisable(true);
                insulationFactorTextField.setDisable(true);
                insulationFactorDimensionLabel.setDisable(true);
                insulationFactorTextField.clear();
            }
        });


        lineLengthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    lineLengthTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(lineLengthTextField.getText());
                        if(number > 1000){
                            lineLengthTextField.setText("1000");
                        }
                        else if(number <0){
                            lineLengthTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        lineLengthTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        insulationThicknessTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    insulationThicknessTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(insulationThicknessTextField.getText());
                        if(number > 100){
                            insulationThicknessTextField.setText("100");
                        }
                        else if(number <0){
                            insulationThicknessTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        insulationThicknessTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        insulationFactorTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    insulationFactorTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(insulationFactorTextField.getText());
//                        if(number > 100){
//                            insulationFactorTextField.setText("100");
//                        }
                        if(number <0){
                            insulationFactorTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        insulationFactorTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });


        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity!= null){
            setOnShow();
        }



    }
    @Override
    public void setOnShow() {
        PipeLine beforeHeater = (PipeLine) Station.getInstance().getList().get("beforeHeaterPipeLine");
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();

        if(cityGateStationEntity != null){
            if(cityGateStationEntity.getBeforeHeater() != null){
                lineLengthTextField.setText(String.valueOf(cityGateStationEntity.getBeforeHeater().getLength()));
                mmOrInchComboBox.getSelectionModel().select(cityGateStationEntity.getBeforeHeater().getPipeSizeUnit());
                insulationRadioButton.setSelected(cityGateStationEntity.getBeforeHeater().isInsulation());
                if(cityGateStationEntity.getBeforeHeater().isInsulation()){
                    insulationThicknessLabel.setDisable(false);
                    insulationThicknessTextField.setDisable(false);
                    insulationThicknessComboBox.setDisable(false);
                    insulationThicknessTextField.setDisable(false);
                    insulationFactorLabel.setDisable(false);
                    insulationFactorTextField.setDisable(false);
                    insulationFactorDimensionLabel.setDisable(false);
                    insulationFactorTextField.setText(String.valueOf(cityGateStationEntity.getBeforeHeater().getInsulationFactor()));
                    insulationThicknessTextField.setText(String.valueOf(cityGateStationEntity.getBeforeHeater().getInsulationThickness()));
                    insulationThicknessComboBox.getSelectionModel().select(cityGateStationEntity.getBeforeHeater().getInsulationThicknessUnit());

                }else{
                    insulationThicknessLabel.setDisable(true);
                    insulationThicknessTextField.setDisable(true);
                    insulationThicknessComboBox.setDisable(true);
                    insulationThicknessTextField.setDisable(true);
                    insulationThicknessTextField.clear();
                    insulationFactorLabel.setDisable(true);
                    insulationFactorTextField.setDisable(true);
                    insulationFactorDimensionLabel.setDisable(true);
                    insulationFactorTextField.clear();
                }
            }
//            lineLengthTextField.setText(String.valueOf(beforeHeater.getLength()));
//            mmOrInchComboBox.getSelectionModel().select(beforeHeater.getSize());
//            insulationRadioButton.setSelected(beforeHeater.isInsulation());
//            if(beforeHeater.isInsulation()){
//                insulationThicknessLabel.setDisable(false);
//                insulationThicknessTextField.setDisable(false);
//                insulationThicknessComboBox.setDisable(false);
//                insulationThicknessTextField.setDisable(false);
//                insulationFactorLabel.setDisable(false);
//                insulationFactorTextField.setDisable(false);
//                insulationFactorDimensionLabel.setDisable(false);
//                insulationFactorTextField.setText(String.valueOf(beforeHeater.getInsulationFactor()));
//                insulationThicknessTextField.setText(String.valueOf(beforeHeater.getInsulationThickness() * 100));
//                insulationThicknessComboBox.getSelectionModel().select("سانتی متر (cm)");
//
//            }else{
//                insulationThicknessLabel.setDisable(true);
//                insulationThicknessTextField.setDisable(true);
//                insulationThicknessComboBox.setDisable(true);
//                insulationThicknessTextField.setDisable(true);
//                insulationThicknessTextField.clear();
//                insulationFactorLabel.setDisable(true);
//                insulationFactorTextField.setDisable(true);
//                insulationFactorDimensionLabel.setDisable(true);
//                insulationFactorTextField.clear();
//            }






        }
        else{

        }
    }

    public void okAction(ActionEvent actionEvent) {
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity == null){
            cityGateStationEntity = new CityGateStationEntity();
        }
        PipeSpecificationsEntity pipeSpecificationsEntity = cityGateStationEntity.getBeforeHeater();
        if(pipeSpecificationsEntity==null){
            pipeSpecificationsEntity = new PipeSpecificationsEntity();
        }
        double insulationThickness = 0;
        double insulationFactor = 0;
        double pipelineLength = 0;
        if(!lineLengthTextField.getText().equals("")) {
            pipelineLength = Double.parseDouble(lineLengthTextField.getText());
        }
        else{
            showAlert("خطا","لطفا اطلاعات درست وارد کنید", "طول خط لوله به درستی وارد نشده است", Alert.AlertType.ERROR);

            return;
        }
        PipeSize pipesize = PipeLine.getSizeSelection().get(mmOrInchComboBox.getValue().toString());
        System.out.println(pipesize);
        double outerDiameter = pipesize.getOuterDiameter();
        double wallthickness = pipesize.getWallThickness();
        if (insulationRadioButton.isSelected()) {
            if(!insulationThicknessTextField.getText().equals("")) {
                double factor = 0.01;
                if(insulationThicknessComboBox.getValue().toString().equals("سانتی متر (cm)")) {
                    factor = 0.01;
                    pipeSpecificationsEntity.setInsulationThickness(Double.parseDouble(insulationThicknessTextField.getText()));
                    pipeSpecificationsEntity.setInsulationThicknessUnit(PipeSpecificationsEntity.CENTIMETER);
                }
                else if(insulationThicknessComboBox.getValue().toString().equals("اینچ (inch)")) {
                    factor = 0.0254;
                    pipeSpecificationsEntity.setInsulationThickness(Double.parseDouble(insulationThicknessTextField.getText()));
                    pipeSpecificationsEntity.setInsulationThicknessUnit(PipeSpecificationsEntity.INCH);
                }
                insulationThickness = factor * Double.parseDouble(insulationThicknessTextField.getText());
                System.out.println(insulationThickness);
            }
            else{
                return;
            }

            if(!insulationFactorTextField.getText().equals("")) {




                insulationFactor = Double.parseDouble(insulationFactorTextField.getText());


            }
            else{

                return;
            }
        }
        
//        PipeLine pipeLine = new PipeLine(outerDiameter, pipesize.getInnerDiameter(), wallthickness, insulationThickness, insulationFactor);


        PipeLine pipeLine = new PipeLine(mmOrInchComboBox.getValue().toString() , pipelineLength);
        pipeLine.setInsulationFactor(insulationFactor);
        pipeLine.setInsulationThickness(insulationThickness);
        pipeLine.setInsulation(insulationRadioButton.isSelected());

        pipeSpecificationsEntity.setPipeSizeUnit(mmOrInchComboBox.getValue().toString());
        pipeSpecificationsEntity.setLength(pipelineLength);
        pipeSpecificationsEntity.setInsulationFactor(insulationFactor);
        if(insulationRadioButton.isSelected()){
            pipeSpecificationsEntity.setInsulation(insulationRadioButton.isSelected());
        }
        else{
            pipeSpecificationsEntity.setInsulation(insulationRadioButton.isSelected());
            pipeSpecificationsEntity.setInsulationThicknessUnit(null);
            pipeSpecificationsEntity.setInsulationThickness(null);
            pipeSpecificationsEntity.setInsulationFactor(null);
        }



        pipeSpecificationsEntity = pipeSpecificationService.save(pipeSpecificationsEntity);
        cityGateStationEntity.setBeforeHeater(pipeSpecificationsEntity);
        cityGateStationEntity = cityGateStationService.save(cityGateStationEntity);
        stageManager.setCityGateStationEntity(cityGateStationEntity);




        Map<String, BaseModel> map = Station.getInstance().getList();
        map.put("beforeHeaterPipeLine", pipeLine);
        stageManager.switchScene(FxmlView.STATION);
        //        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

    }


    public void clearButton(ActionEvent actionEvent) {
        lineLengthTextField.clear();
        insulationThicknessTextField.clear();
        insulationFactorTextField.clear();
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity!=null){
            cityGateStationEntity.setBeforeHeater(null);
            cityGateStationEntity = cityGateStationService.save(cityGateStationEntity);
            stageManager.setCityGateStationEntity(cityGateStationEntity);

        }
//        Station.getInstance().getList().remove("beforeHeaterPipeLine");
    }

    public void cancelButton(ActionEvent actionEvent) {

        stageManager.switchScene(FxmlView.STATION);
//        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }



}
