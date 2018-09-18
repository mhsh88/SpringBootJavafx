package com.codetreatise.controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import ir.behinehsazan.gasStation.model.mathCalculation.MathCalculation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;
import sample.model.Station;
import sample.model.base.BaseModel;
import sample.model.stationProperties.StationPropertice;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

@Controller
public class StationPropertyController extends BaseController {
    @Lazy
    @Autowired
    StageManager stageManager;
    final static private String PSI = "Psi";
    final static private String MPA = "MPa";
    final static private String KPA = "kPa";
    final static private String MOLPERCENT = "درصد مولی";
    final static private String WEIGHTPERCENT = "درصد جرمی";



    DecimalFormat df = new DecimalFormat("#.###");
    public GridPane mainGridPane = new GridPane();
    public Label totalNumberText;
    Stage stage;
    private StationPropertice stationPropertice = new StationPropertice();


    public ComboBox inputGasTempComboBox = new ComboBox();
    public ComboBox inputGasPressureComboBox= new ComboBox();
    public ComboBox outputGasTempComboBox= new ComboBox();
    public ComboBox outputGasPressureComboBox= new ComboBox();
    public ComboBox environmentTempComboBox= new ComboBox();


    // TODO it must check for inputs of double and numbers



    public TextField carbonDioxideTextField = new TextField();
    public TextField methanTextField = new TextField();
    public TextField ethaneTextField = new TextField();
    public TextField propaneTextField = new TextField();
    public TextField nButaneTextField = new TextField();
    public TextField isoButaneTextField = new TextField();
    public TextField nPentaneTextField = new TextField();
    public TextField isoPentaneTextField = new TextField();
    public TextField hexaneTextField = new TextField();
    public TextField heptaneTextField = new TextField();
    public TextField octaneTextField = new TextField();
    public TextField nonaneTextField = new TextField();
    public TextField decaneTextField = new TextField();
    public TextField hydrogenTextField = new TextField();
    public TextField oxygenTextField = new TextField();
    public TextField carbonMonoxideTextField = new TextField();
    public TextField waterTextField = new TextField();
    public TextField hydrogenSulfideTextField = new TextField();
    public TextField heliumTextField = new TextField();
    public TextField argonTextField = new TextField();
    public TextField provinceTextField = new TextField();
    public TextField cityTextField = new TextField();
    public TextField areaTextField = new TextField();
    public TextField nominalCapacityTextField = new TextField();
    public TextArea addressTextArea = new TextArea();
    public TextField inputGasTempTextField = new TextField();
    public TextField inputGasPressureTextField = new TextField();
    public TextField outputGasTempTextField = new TextField();
    public TextField outputGasPressureTextField = new TextField();
    public TextField environmentTempTextField = new TextField();
    public TextField windSpeedTextField = new TextField();
    public TextField stationDebiTextField = new TextField();
    public Button clearButton = new Button();
    public Button okButton  = new Button();
    private Double[] component = new Double[21];
    @FXML
    ComboBox gasPercentType = new ComboBox();
    @FXML
    TextField textField = new TextField();
    @FXML
    private Button cancelButton = new Button();
    @FXML
    TextField nitrogenTextField = new TextField();


    public StationPropertyController() {
    }

    @FXML
    public void initialize() throws IOException {
        gasPercentType.getItems().removeAll(gasPercentType.getItems());
        gasPercentType.getItems().addAll("درصد مولی", "درصد جرمی");
        gasPercentType.getSelectionModel().select("درصد مولی");
        inputGasTempComboBox.getItems().removeAll(inputGasTempComboBox.getItems());
        inputGasTempComboBox.getItems().addAll("°C", "°F");
        inputGasTempComboBox.getSelectionModel().select("°C");
        inputGasPressureComboBox.getItems().removeAll(inputGasTempComboBox.getItems());
        inputGasPressureComboBox.getItems().addAll("kPa", "MPa","Psi");
        inputGasPressureComboBox.getSelectionModel().select("Psi");
        outputGasTempComboBox.getItems().removeAll(inputGasTempComboBox.getItems());
        outputGasTempComboBox.getItems().addAll("°C", "°F");
        outputGasTempComboBox.getSelectionModel().select("°C");
        outputGasPressureComboBox.getItems().removeAll(inputGasTempComboBox.getItems());
        outputGasPressureComboBox.getItems().addAll("kPa","MPa", "Psi");
        outputGasPressureComboBox.getSelectionModel().select("Psi");
        environmentTempComboBox.getItems().removeAll(inputGasTempComboBox.getItems());
        environmentTempComboBox.getItems().addAll("°C", "°F");
        environmentTempComboBox.getSelectionModel().select("°C");
        methanTextField.setText("100");
        nitrogenTextField.setText("0");
        carbonDioxideTextField.setText("0");
        ethaneTextField.setText("0");
        propaneTextField.setText("0");
        nButaneTextField.setText("0");
        isoButaneTextField.setText("0");
        nPentaneTextField.setText("0");
        isoPentaneTextField.setText("0");
        hexaneTextField.setText("0");
        heptaneTextField.setText("0");
        octaneTextField.setText("0");
        nonaneTextField.setText("0");
        decaneTextField.setText("0");
        hydrogenTextField.setText("0");
        oxygenTextField.setText("0");
        carbonMonoxideTextField.setText("0");
        waterTextField.setText("0");
        hydrogenSulfideTextField.setText("0");
        heliumTextField.setText("0");
        argonTextField.setText("0");
        inputGasTempTextField.setText("15");
        inputGasPressureTextField.setText("800");
        outputGasPressureTextField.setText("250");
        outputGasTempTextField.setText("8");
        environmentTempTextField.setText("15");
        windSpeedTextField.setText("4");
        stationDebiTextField.setText("40000");


        nitrogenTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    nitrogenTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(nitrogenTextField.getText());
                        if(number > 100){
                            nitrogenTextField.setText("100");
                        }
                        else if(number <0){
                            nitrogenTextField.setText("0");
                        }


                    }
                    catch (Exception e){
                        nitrogenTextField.setText("");
                        e.printStackTrace();
                    }
                }
            }
        });

        carbonDioxideTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    carbonDioxideTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(carbonDioxideTextField.getText());
                        if(number > 100){
                            carbonDioxideTextField.setText("100");
                        }
                        else if(number <0){
                            carbonDioxideTextField.setText("0");
                        }


                    }
                    catch (Exception e){
                        carbonDioxideTextField.setText("");
                        e.printStackTrace();
                    }
                }
            }
        });
        methanTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    methanTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(methanTextField.getText());
                        if(number > 100){
                            methanTextField.setText("100");
                        }
                        else if(number <0){
                            methanTextField.setText("0");
                        }


                    }
                    catch (Exception e){
                        methanTextField.setText("");
                        e.printStackTrace();
                    }
                }
            }
        });

        ethaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    ethaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(ethaneTextField.getText());
                        if(number > 100){
                            ethaneTextField.setText("100");
                        }
                        else if(number <0){
                            ethaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){
                        ethaneTextField.setText("");
                        e.printStackTrace();
                    }
                }
            }
        });

        propaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    propaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(propaneTextField.getText());
                        if(number > 100){
                            propaneTextField.setText("100");
                        }
                        else if(number <0){
                            propaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){
                        propaneTextField.setText("");
//                        e.printStackTrace();
                    }
                }
            }
        });

        nButaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    nButaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(nButaneTextField.getText());
                        if(number > 100){
                            nButaneTextField.setText("100");
                        }
                        else if(number <0){
                            nButaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        nButaneTextField.setText("");

                    }
                }
            }
        });

        isoButaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    isoButaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(isoButaneTextField.getText());
                        if(number > 100){
                            isoButaneTextField.setText("100");
                        }
                        else if(number <0){
                            isoButaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        isoButaneTextField.setText("");

                    }
                }
            }
        });

        nPentaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    nPentaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(nPentaneTextField.getText());
                        if(number > 100){
                            nPentaneTextField.setText("100");
                        }
                        else if(number <0){
                            nPentaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        nPentaneTextField.setText("");

                    }
                }
            }
        });

        isoPentaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    isoPentaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(isoPentaneTextField.getText());
                        if(number > 100){
                            isoPentaneTextField.setText("100");
                        }
                        else if(number <0){
                            isoPentaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        isoPentaneTextField.setText("");

                    }
                }
            }
        });

        hexaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    hexaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(hexaneTextField.getText());
                        if(number > 100){
                            hexaneTextField.setText("100");
                        }
                        else if(number <0){
                            hexaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        hexaneTextField.setText("");

                    }
                }
            }
        });

        heptaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    heptaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(heptaneTextField.getText());
                        if(number > 100){
                            heptaneTextField.setText("100");
                        }
                        else if(number <0){
                            heptaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        heptaneTextField.setText("");

                    }
                }
            }
        });

        octaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    octaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(octaneTextField.getText());
                        if(number > 100){
                            octaneTextField.setText("100");
                        }
                        else if(number <0){
                            octaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        octaneTextField.setText("");

                    }
                }
            }
        });

        nonaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    nonaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(nonaneTextField.getText());
                        if(number > 100){
                            nonaneTextField.setText("100");
                        }
                        else if(number <0){
                            nonaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        nonaneTextField.setText("");

                    }
                }
            }
        });

        decaneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    decaneTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(decaneTextField.getText());
                        if(number > 100){
                            decaneTextField.setText("100");
                        }
                        else if(number <0){
                            decaneTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        decaneTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });
        hydrogenTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    hydrogenTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(hydrogenTextField.getText());
                        if(number > 100){
                            hydrogenTextField.setText("100");
                        }
                        else if(number <0){
                            hydrogenTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        hydrogenTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });
        oxygenTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    oxygenTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(oxygenTextField.getText());
                        if(number > 100){
                            oxygenTextField.setText("100");
                        }
                        else if(number <0){
                            oxygenTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        oxygenTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        carbonMonoxideTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    carbonMonoxideTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(carbonMonoxideTextField.getText());
                        if(number > 100){
                            carbonMonoxideTextField.setText("100");
                        }
                        else if(number <0){
                            carbonMonoxideTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        carbonMonoxideTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        waterTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    waterTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(waterTextField.getText());
                        if(number > 100){
                            waterTextField.setText("100");
                        }
                        else if(number <0){
                            waterTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        waterTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        hydrogenSulfideTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    hydrogenSulfideTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(hydrogenSulfideTextField.getText());
                        if(number > 100){
                            hydrogenSulfideTextField.setText("100");
                        }
                        else if(number <0){
                            hydrogenSulfideTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        hydrogenSulfideTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        heliumTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    heliumTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(heliumTextField.getText());
                        if(number > 100){
                            heliumTextField.setText("100");
                        }
                        else if(number <0){
                            heliumTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        heliumTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        argonTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    argonTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(argonTextField.getText());
                        if(number > 100){
                            argonTextField.setText("100");
                        }
                        else if(number <0){
                            argonTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        argonTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        inputGasTempTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    inputGasTempTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(inputGasTempTextField.getText());
                        if(number > 500){
                            inputGasTempTextField.setText("500");
                        }
                        else if(number <-60){
                            inputGasTempTextField.setText("-60");
                        }


                    }
                    catch (Exception e){

                        inputGasTempTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        inputGasPressureTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    inputGasPressureTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(inputGasPressureTextField.getText());
                        if(number > 20000){
                            inputGasPressureTextField.setText("20000");
                        }
                        else if(number <0){
                            inputGasPressureTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        inputGasPressureTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        outputGasTempTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    outputGasTempTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(outputGasTempTextField.getText());
                        if(number > 500){
                            outputGasTempTextField.setText("500");
                        }
                        else if(number <-60){
                            outputGasTempTextField.setText("-60");
                        }


                    }
                    catch (Exception e){

                        outputGasTempTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });


        outputGasPressureTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    outputGasPressureTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(outputGasPressureTextField.getText());
                        if(number > 20000){
                            outputGasPressureTextField.setText("20000");
                        }
                        else if(number <0){
                            outputGasPressureTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        outputGasPressureTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        environmentTempTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    environmentTempTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(environmentTempTextField.getText());
                        if(number > 100){
                            environmentTempTextField.setText("100");
                        }
                        else if(number <-60){
                            environmentTempTextField.setText("-60");
                        }


                    }
                    catch (Exception e){

                        environmentTempTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });


        windSpeedTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    windSpeedTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(windSpeedTextField.getText());
                        if(number > 100){
                            windSpeedTextField.setText("100");
                        }
                        else if(number <0){
                            windSpeedTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        windSpeedTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });




        stationDebiTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    stationDebiTextField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(stationDebiTextField.getText());
                        if(number > 10000000){
                            stationDebiTextField.setText("10000000");
                        }
                        else if(number <0){
                            stationDebiTextField.setText("0");
                        }


                    }
                    catch (Exception e){

                        stationDebiTextField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });




















//        nitrogenTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable,
//                                String oldValue, String newValue) {
//                try {
//                    Integer.parseInt(newValue);
//                    if (newValue.endsWith("f") || newValue.endsWith("d")) {
//                        carbonDioxideTextField.setText(newValue.substring(0, newValue.length()-1));
//                    }
//                }
//                catch (Exception e) {
//                    nitrogenTextField.setText(oldValue);
//                }
//            }
//        });





//        nitrogenTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
//            if (!newValue) { //when focus lost
//                if(!nitrogenTextField.getText().matches("^(0|[1-9][0-9]*)$")){
//                    //when it not matches the pattern (1.0 - 6.0)
//                    //set the textField empty
//                    System.out.println("inside if");
//                    nitrogenTextField.setText("");
//                    nitrogenTextField.clear();
//                }
//            }
//
//        });

//          stage = (Stage) mainGridPane.getScene().getWindow();


//        setOnStart();
    }
    @Override
    public void setOnShow() {
        StationPropertice stationPropertice = (StationPropertice) Station.getInstance().getList().get("stationPropertice");
        DecimalFormat dff = new DecimalFormat("#0.0");
        if(stationPropertice != null){
            Double[] tempComponent = stationPropertice.getComponent();

            double[] component = Arrays.stream(tempComponent).map(n -> df.format(n * 100)).mapToDouble(Double::valueOf).toArray();

            nitrogenTextField.setText(String.valueOf(component[0]));
            carbonDioxideTextField.setText(String.valueOf(component[1]));
            methanTextField.setText(String.valueOf(component[2]));
            ethaneTextField.setText(String.valueOf(component[3]));
            propaneTextField.setText(String.valueOf(component[4]));
            nButaneTextField.setText(String.valueOf(component[5]));
            isoButaneTextField.setText(String.valueOf(component[6]));
            nPentaneTextField.setText(String.valueOf(component[7]));
            isoPentaneTextField.setText(String.valueOf(component[8]));
            hexaneTextField.setText(String.valueOf(component[9]));
            heptaneTextField.setText(String.valueOf(component[10]));
            octaneTextField.setText(String.valueOf(component[11]));
            nonaneTextField.setText(String.valueOf(component[12]));
            decaneTextField.setText(String.valueOf(component[13]));
            hydrogenTextField.setText(String.valueOf(component[14]));
            oxygenTextField.setText(String.valueOf(component[15]));
            carbonMonoxideTextField.setText(String.valueOf(component[16]));
            waterTextField.setText(String.valueOf(component[17]));
            hydrogenSulfideTextField.setText(String.valueOf(component[18]));
            heliumTextField.setText(String.valueOf(component[19]));
            argonTextField.setText(String.valueOf(component[20]));
            inputGasTempTextField.setText(dff.format(stationPropertice.getInputTemp() - 273.15));
//            System.out.println(inputGasPressureComboBox.getValue().toString());

//            Double temdsd = Double.parseDouble(dff.format((stationPropertice.getInputPressure() - 101.235) * 0.145038));
//            System.out.println(temdsd.getClass());
//            System.out.println(Math.round((stationPropertice.getInputPressure() - 101.235) * 0.145038));
            double pressure = (inputGasPressureComboBox.getValue().toString().equals(PSI)) ? Double.parseDouble(dff.format((stationPropertice.getInputPressure() - 101.235) * 0.145038)) :
                    (inputGasPressureComboBox.getValue().toString().equals(MPA) ? Math.round((stationPropertice.getInputPressure() - 101.235) / 1000): Math.round(stationPropertice.getInputPressure() - 101.235));
            inputGasPressureTextField.setText(String.valueOf(pressure));
            pressure = (outputGasPressureComboBox.getValue().toString().equals(PSI)) ? Double.parseDouble(dff.format(((stationPropertice.getOutputPressure() - 101.235) * 0.145038))) :
                    (outputGasPressureComboBox.getValue().toString().equals(MPA) ? Math.round((stationPropertice.getOutputPressure() - 101.235) / 1000) : Math.round(stationPropertice.getOutputPressure() - 101.235));
            outputGasPressureTextField.setText(String.valueOf(pressure));
            outputGasTempTextField.setText(dff.format(stationPropertice.getOutputTemp() - 273.15));
            if(stationPropertice.getEnvironmentTemp() != null) {
                environmentTempTextField.setText(dff.format(stationPropertice.getEnvironmentTemp() - 273.15));
            }
            else environmentTempTextField.setText("");
            windSpeedTextField.setText(dff.format(stationPropertice.getWindVelocity()));
            stationDebiTextField.setText(dff.format(stationPropertice.getDebi()));
            provinceTextField.setText(stationPropertice.getProvince());
            cityTextField.setText(stationPropertice.getCity());
            areaTextField.setText(stationPropertice.getArea());
            nominalCapacityTextField.setText(stationPropertice.getNominalCapacity());
            addressTextArea.setText(stationPropertice.getAddress());

            DecimalFormat totalNumberDF = new DecimalFormat("#0.00");
            totalNumberText.setText(String.valueOf(totalNumberDF.format(Arrays.stream(component).sum())));


        }
        else{
            try {
                initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }


    public void okButton(ActionEvent actionEvent) {
        // TODO it must check whether text is ok or not
        try {
            component[0] = gasInputCheck(nitrogenTextField);
            component[1] = gasInputCheck(carbonDioxideTextField);
            component[2] = gasInputCheck(methanTextField);
            component[3] = gasInputCheck(ethaneTextField);
            component[4] = gasInputCheck(propaneTextField);
            component[5] = gasInputCheck(nButaneTextField);
            component[6] = gasInputCheck(isoButaneTextField);
            component[7] = gasInputCheck(nPentaneTextField);
            component[8] = gasInputCheck(isoPentaneTextField);
            component[9] = gasInputCheck(hexaneTextField);
            component[10] = gasInputCheck(heptaneTextField);
            component[11] = gasInputCheck(octaneTextField);
            component[12] = gasInputCheck(nonaneTextField);
            component[13] = gasInputCheck(decaneTextField);
            component[14] = gasInputCheck(hydrogenTextField);
            component[15] = gasInputCheck(oxygenTextField);
            component[16] = gasInputCheck(carbonMonoxideTextField);
            component[17] = gasInputCheck(waterTextField);
            component[18] = gasInputCheck(hydrogenSulfideTextField);
            component[19] = gasInputCheck(heliumTextField);
            component[20] = gasInputCheck(argonTextField);
        }
        catch (Exception e){
            showAlert("خطا", "خطا در اطلاعات ورودی","اطلاعات وارد شده برای اجزای گاز صحیح نمی باشد" , Alert.AlertType.ERROR );
            return;
        }
        if(MathCalculation.listSum(component) <= 0){
            showAlert("خطا", "خطا در اطلاعات ورودی","مجموع اجزای گاز نمی تواند صفر یا کمتر از صفر باشد" , Alert.AlertType.ERROR );
            return;
        }
        component = MathCalculation.normal(component);

        Double[] M_i = {28.0135
                , 44.01
                , 16.043
                , 30.07
                , 44.097
                , 58.123
                , 58.123
                , 72.15
                , 72.15
                , 86.177
                , 100.204
                , 114.231
                , 128.258
                , 142.285
                , 2.0159
                , 31.9988
                , 28.01
                , 18.0153
                , 34.082
                , 4.0026
                , 39.948};
        if(gasPercentType.getValue().toString().equals("درصد جرمی")){


            component =   MathCalculation.normal(MathCalculation.matrixDevide(MathCalculation.pointToPointDivistion(component, M_i), MathCalculation.dotProduct(component, M_i)));
        }

        stationPropertice.setComponent(component);


        stationPropertice.setProvince(provinceTextField.getText());
        stationPropertice.setCity(cityTextField.getText());
        stationPropertice.setArea(areaTextField.getText());
        stationPropertice.setNominalCapacity(nominalCapacityTextField.getText());
        stationPropertice.setAddress(addressTextArea.getText());

        try {
            if(inputGasTempTextField.getText().equals("")){
                showAlert("اخطار","دمای گاز ورودی به ایستگاه وارد نشده است","دمای گاز ورودی به ایستگاه را وارد نمایید", Alert.AlertType.WARNING);
                return;
            }
             if(inputGasTempComboBox.getValue().toString().equals("°F")){
                 stationPropertice.setInputTemp((Double.parseDouble(inputGasTempTextField.getText() ) - 32)/1.8);

            }else if(inputGasTempComboBox.getValue().toString().equals("°C")){
                 stationPropertice.setInputTemp(Double.parseDouble(inputGasTempTextField.getText() ));
             }


        }
        catch (Exception e){


            showAlert("خطا","دمای گاز ورودی به ایستگاه وارد نشده است","دمای گاز ورودی به ایستگاه را وارد نمایید", Alert.AlertType.ERROR);
            return;
        }


        try {
            if(inputGasPressureTextField.getText().equals("")){
                showAlert("اخطار","فشار گاز ورودی به ایستگاه وارد نشده است","فشار گاز ورودی به ایستگاه را وارد نمایید", Alert.AlertType.WARNING);
                return;
            }
            double prefactor = 1.0;
            if(inputGasPressureComboBox.getValue().toString().equals("MPa")){
                prefactor = 1000.0;
            }else if(inputGasPressureComboBox.getValue().toString().equals("kPa")){
                prefactor = 1.0;
            }else if(inputGasPressureComboBox.getValue().toString().equals("Psi")){
                prefactor = 6.89476;
            }else
                prefactor = 1.0;


            stationPropertice.setInputPressure(prefactor * Double.parseDouble(inputGasPressureTextField.getText()));
        }
        catch (Exception e){


            showAlert("خطا","فشار گاز ورودی به ایستگاه وارد نشده است","فشار گاز ورودی به ایستگاه را وارد نمایید", Alert.AlertType.ERROR);
            return;
        }
        try {
            if(outputGasPressureTextField.getText().equals("")){
                showAlert("اخطار","فشار گاز خروجی به ایستگاه وارد نشده است","فشار گاز خروجی به ایستگاه را وارد نمایید", Alert.AlertType.WARNING);
                return;
            }

            double prefactor = 1.0;
            if(outputGasPressureComboBox.getValue().toString().equals("MPa")){
                prefactor = 1000.0;
            }else if(outputGasPressureComboBox.getValue().toString().equals("kPa")){
                prefactor = 1.0;
            }else if(outputGasPressureComboBox.getValue().toString().equals("Psi")){
                prefactor = 6.89476;
            }else
                prefactor = 1.0;
            stationPropertice.setOutputPressure(prefactor * Double.parseDouble(outputGasPressureTextField.getText()));
        }
        catch (Exception e){


            showAlert("خطا","فشار گاز خروجی به ایستگاه وارد نشده است","فشار گاز خروجی به ایستگاه را وارد نمایید", Alert.AlertType.ERROR);
            return;
        }

        try {
            if(outputGasTempTextField.getText().equals("")){
                showAlert("اخطار","دمای گاز خروجی به ایستگاه وارد نشده است","دمای گاز خروجی به ایستگاه را وارد نمایید", Alert.AlertType.WARNING);
                return;
            }
            if(outputGasTempComboBox.getValue().toString().equals("°F")){
                stationPropertice.setOutputTemp((Double.parseDouble(outputGasTempTextField.getText() ) - 32)/1.8);

            }else if(outputGasTempComboBox.getValue().toString().equals("°C")){
                stationPropertice.setOutputTemp(Double.parseDouble(outputGasTempTextField.getText()));
            }
        }
        catch (Exception e){


            showAlert("خطا","دمای گاز خروجی به ایستگاه وارد نشده است","دمای گاز خروجی به ایستگاه را وارد نمایید", Alert.AlertType.ERROR);
            return;
        }


        try {

            if(!environmentTempTextField.getText().equals("")){

                if(environmentTempComboBox.getValue().toString().equals("°F")){
                    stationPropertice.setEnvironmentTemp((Double.parseDouble(environmentTempTextField.getText() ) - 32)/1.8);

                }else if(environmentTempComboBox.getValue().toString().equals("°C")){
                    stationPropertice.setEnvironmentTemp(Double.parseDouble(environmentTempTextField.getText()));
                }
            }
            else{
                stationPropertice.setEnvironmentTemp(null);
            }
        }
        catch (Exception e){


            showAlert("خطا","دمای هوا به درستی وارد نشده است","دمای هوا را به درستی وارد نمایید", Alert.AlertType.ERROR);
            return;
        }

        try {

            if(!windSpeedTextField.getText().equals("")){
                stationPropertice.setWindVelocity(Double.parseDouble(windSpeedTextField.getText()));
            }
            else if (windSpeedTextField.getText().equals("")){
                stationPropertice.setWindVelocity(0);
            }
        }
        catch (Exception e){


            showAlert("خطا","سرعت باد به درستی وارد نشده است","سرعت باد را به درستی وارد نمایید", Alert.AlertType.ERROR);
            return;
        }

        try {

            if(!stationDebiTextField.getText().equals("")){
                stationPropertice.setDebi(Double.parseDouble(stationDebiTextField.getText()));
            }
            else if (stationDebiTextField.getText().equals("")){
                stationPropertice.setDebi(0);
            }
        }
        catch (Exception e){


            showAlert("خطا","سرعت باد به درستی وارد نشده است","سرعت باد را به درستی وارد نمایید", Alert.AlertType.ERROR);
            return;
        }














        Station station = Station.getInstance();
        Map<String, BaseModel> tempMap = station.getList();
        tempMap.put("stationPropertice",stationPropertice);

//
// ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        stageManager.switchScene(FxmlView.STATION);


    }

    public void clearButton(ActionEvent actionEvent) {

        nitrogenTextField.clear();
        carbonDioxideTextField.clear();
        methanTextField.clear();
        ethaneTextField.clear();
        propaneTextField.clear();
        nButaneTextField.clear();
        isoButaneTextField.clear();
        nPentaneTextField.clear();
        isoPentaneTextField.clear();
        hexaneTextField.clear();
        heptaneTextField.clear();
        octaneTextField.clear();
        nonaneTextField.clear();
        decaneTextField.clear();
        hydrogenTextField.clear();
        oxygenTextField.clear();
        carbonMonoxideTextField.clear();
        waterTextField.clear();
        hydrogenSulfideTextField.clear();
        heliumTextField.clear();
        argonTextField.clear();
        provinceTextField.clear();
        cityTextField.clear();
        areaTextField.clear();
        nominalCapacityTextField.clear();
        addressTextArea.clear();
        inputGasTempTextField.clear();
        inputGasPressureTextField.clear();
        outputGasTempTextField.clear();
        outputGasPressureTextField.clear();
        environmentTempTextField.clear();
        windSpeedTextField.clear();
        stationDebiTextField.clear();

        Station.getInstance().getList().remove("stationPropertice");





    }
    @FXML
    private void cancelButton(ActionEvent actionEvent)  {
//        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        stageManager.switchScene(FxmlView.STATION);

//        Platform.exit();
        // get a handle to the stage
//        System.exit(0);
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
////        // do what you have to do
//        stage.close();

    }


    public void gasPropertyInput(KeyEvent keyEvent) {

        component[0] = (!nitrogenTextField.getText().equals("")) ? Double.parseDouble(nitrogenTextField.getText()) : 0.0;
        component[1] = (!carbonDioxideTextField.getText().equals("")) ? Double.parseDouble(carbonDioxideTextField.getText()) : 0.0;
        component[2] = (!methanTextField.getText().equals("")) ? Double.parseDouble(methanTextField.getText()) : 0.0;
        component[3] = (!ethaneTextField.getText().equals("")) ? Double.parseDouble(ethaneTextField.getText()) : 0.0;
        component[4] = (!propaneTextField.getText().equals("")) ? Double.parseDouble(propaneTextField.getText()) : 0.0;
        component[5] = (!nButaneTextField.getText().equals("")) ? Double.parseDouble(nButaneTextField.getText()) : 0.0;
        component[6] = (!isoButaneTextField.getText().equals("")) ? Double.parseDouble(isoButaneTextField.getText()) : 0.0;
        component[7] = (!nPentaneTextField.getText().equals("")) ? Double.parseDouble(nPentaneTextField.getText()) : 0.0;
        component[8] = (!isoPentaneTextField.getText().equals("")) ? Double.parseDouble(isoPentaneTextField.getText()) : 0.0;
        component[9] = (!hexaneTextField.getText().equals("")) ? Double.parseDouble(hexaneTextField.getText()) : 0.0;
        component[10] = (!heptaneTextField.getText().equals("")) ? Double.parseDouble(heptaneTextField.getText()) : 0.0;
        component[11] = (!octaneTextField.getText().equals("")) ? Double.parseDouble(octaneTextField.getText()) : 0.0;
        component[12] = (!nonaneTextField.getText().equals("")) ? Double.parseDouble(nonaneTextField.getText()) : 0.0;
        component[13] = (!decaneTextField.getText().equals("")) ? Double.parseDouble(decaneTextField.getText()) : 0.0;
        component[14] = (!hydrogenTextField.getText().equals("")) ? Double.parseDouble(hydrogenTextField.getText()) : 0.0;
        component[15] = (!oxygenTextField.getText().equals("")) ? Double.parseDouble(oxygenTextField.getText()) : 0.0;
        component[16] = (!carbonMonoxideTextField.getText().equals("")) ? Double.parseDouble(carbonMonoxideTextField.getText()) : 0.0;
        component[17] = (!waterTextField.getText().equals("")) ? Double.parseDouble(waterTextField.getText()) : 0.0;
        component[18] = (!hydrogenSulfideTextField.getText().equals("")) ? Double.parseDouble(hydrogenSulfideTextField.getText()) : 0.0;
        component[19] = (!heliumTextField.getText().equals("")) ? Double.parseDouble(heliumTextField.getText()) : 0.0;
        component[20] = (!argonTextField.getText().equals("")) ? Double.parseDouble(argonTextField.getText()) : 0.0;

        totalNumberText.setText(String.valueOf(df.format(MathCalculation.listSum(component))));





    }

    public double gasInputCheck(TextField textField){
        return (!textField.getText().equals("")) ? Double.parseDouble(textField.getText()) : 0.0;

    }

    }
