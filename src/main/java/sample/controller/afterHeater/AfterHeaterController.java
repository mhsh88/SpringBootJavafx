package sample.controller.afterHeater;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import sample.controller.base.BaseController;
import sample.model.Station;
import sample.model.base.BaseModel;
import sample.model.pipeLine.PipeLine;
import sample.model.pipeLine.PipeSize;

import java.io.IOException;
import java.util.Map;

public class AfterHeaterController extends BaseController{



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

//    public final static Map<String, PipeSize> sizeSelection = new HashMap<String, PipeSize>() {
//        {
//
//            put("2", new PipeSize(3.9, 60.3));
//
//            put("3", new PipeSize(5.5, 88.9));
//
//            put("4", new PipeSize(6.02, 114.3));
//            put("6", new PipeSize(7.11, 168.30));
//            put("8", new PipeSize(8.18, 219.10));
//            put("10", new PipeSize(9.27, 273.10));
//            put("12", new PipeSize(9.53, 323.90));
//            put("16", new PipeSize(9.53, 406.40));
//            put("20", new PipeSize(9.53, 508));
//            put("24", new PipeSize(9.53, 610));
//            put("30", new PipeSize(9.53, 762));
//
//
//        }
//    };

    @FXML
    TextField textField = new TextField();


    @FXML
    public void initialize() throws IOException {
        insulationThicknessComboBox.getItems().removeAll();
        insulationThicknessComboBox.getItems().addAll("سانتی متر (cm)", "اینچ (inch)");
        insulationThicknessComboBox.getSelectionModel().select("سانتی متر (cm)");
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


    }
    @Override
    public void setOnShow() {
        PipeLine afterHeater = (PipeLine) Station.getInstance().getList().get("afterHeaterPipeLine");
        if(afterHeater != null){
            lineLengthTextField.setText(String.valueOf(afterHeater.getLength()));
            mmOrInchComboBox.getSelectionModel().select(afterHeater.getSize());
            insulationRadioButton.setSelected(afterHeater.isInsulation());
            if(afterHeater.isInsulation()){
                insulationThicknessLabel.setDisable(false);
                insulationThicknessTextField.setDisable(false);
                insulationThicknessComboBox.setDisable(false);
                insulationThicknessTextField.setDisable(false);
                insulationFactorLabel.setDisable(false);
                insulationFactorTextField.setDisable(false);
                insulationFactorDimensionLabel.setDisable(false);
                insulationFactorTextField.setText(String.valueOf(afterHeater.getInsulationFactor()));
                insulationThicknessTextField.setText(String.valueOf(afterHeater.getInsulationThickness() * 100));
                insulationThicknessComboBox.getSelectionModel().select("سانتی متر (cm)");

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
        else{

        }
    }

    public void okAction(ActionEvent actionEvent) {
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
            if(!insulationThicknessTextField.getAlignment().equals("")) {
                double factor = 0.01;
                if(insulationThicknessComboBox.getValue().toString().equals("سانتی متر (cm)"))
                    factor = 0.01;
                else if(insulationThicknessComboBox.getValue().toString().equals("اینچ (inch)"))
                    factor = 0.0254;
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

        Map<String, BaseModel> map = Station.getInstance().getList();
        map.put("afterHeaterPipeLine", pipeLine);
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

    }


    public void clearButton(ActionEvent actionEvent) {
        lineLengthTextField.clear();
        insulationThicknessTextField.clear();
        insulationFactorTextField.clear();
        Station.getInstance().getList().remove("afterHeaterPipeLine");
    }

    public void cancelButton(ActionEvent actionEvent) {

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

}
