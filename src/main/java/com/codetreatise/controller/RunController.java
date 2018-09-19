package com.codetreatise.controller;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.PipeSpecificationsEntity;
import com.codetreatise.bean.station.RunEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import com.codetreatise.service.PipeSpecificationService;
import com.codetreatise.service.RunService;
import com.codetreatise.view.FxmlView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;
import sample.model.Station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RunController extends BaseController{

    @Lazy
    @Autowired
    StageManager stageManager;

    @Autowired
    CityGateStationService cityGateStationService;

    @Autowired
    RunService runService;

    @Autowired
    PipeSpecificationService pipeSpecificationService;




    public TextField runNumberInput;
    public TextField runLengthInput;
    public TextField runColectorLengthInput;
    public TabPane runTapPane;
    public ComboBox collectorComboBox;
    public Button okButton;
    public Button clearButton;
    public Button cancelButton;

    @FXML
    public void initialize() throws IOException {
        collectorComboBox.getItems().removeAll();
        collectorComboBox.getItems().addAll( "2","3","4","6","8","10","12","16","20","24","30");
        collectorComboBox.getSelectionModel().select("8");

        runNumberInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    runNumberInput.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Integer number = Integer.parseInt(runNumberInput.getText());
                        if(number > 20){
                            runNumberInput.setText("20");
                        }
                        else if(number <0){
                            runNumberInput.setText("0");
                        }


                    }
                    catch (Exception e){

                        runNumberInput.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        runLengthInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    runLengthInput.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(runLengthInput.getText());
                        if(number > 50){
                            runLengthInput.setText("50");
                        }
                        else if(number <0){
                            runLengthInput.setText("0");
                        }


                    }
                    catch (Exception e){

                        runLengthInput.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

        runColectorLengthInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    runColectorLengthInput.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(runColectorLengthInput.getText());
                        if(number > 50){
                            runColectorLengthInput.setText("50");
                        }
                        else if(number <0){
                            runColectorLengthInput.setText("0");
                        }


                    }
                    catch (Exception e){

                        runColectorLengthInput.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });


        if(stageManager.getCityGateStationEntity()!=null){
            setOnShow();
        }





    }



    public void runNumInput(KeyEvent keyEvent) {
        runTapPane.getTabs().clear();
        if(runNumberInput.getText().equals("")) return;
        int runNumber = Integer.parseInt(runNumberInput.getText());
        if(runNumber>10){
            runNumberInput.setText("10");
            runNumber = 20;

        }
        else if(runNumber<0){
            runNumberInput.setText("0");
            runNumber = 0;
        }
        for (int i = 1; i <= runNumber; i++) {

            Tab tab = new Tab();




            HBox childHBox = new HBox();

            tab.setText("ران  " + i);
            GridPane runContainer = new GridPane();
            runContainer.add(new Label("دبی گاز عبوری "), 1, 0);
            TextField textField = addDebiValidator(new TextField());


            runContainer.add(textField, 0, 0);
            runContainer.add(new Label("سایز (اینچ) "), 1, 1);
            ComboBox comboBox = new ComboBox();
            comboBox.getItems().removeAll();
            comboBox.getItems().addAll( "2","3","4","6","8","10","12","16","20","24","30");
            comboBox.getSelectionModel().select("8");
            runContainer.add(comboBox, 0, 1);

            childHBox.getChildren().add(runContainer);
            childHBox.setAlignment(Pos.CENTER);
            tab.setContent(childHBox);
            runTapPane.getTabs().add(tab);
            runTapPane.setMinSize(200,100);
            runTapPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);




        }



    }


    public void ok(ActionEvent actionEvent) {
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        if(cityGateStationEntity==null){
            cityGateStationEntity = new CityGateStationEntity();
        }
        List<RunEntity> runs = cityGateStationEntity.getRuns();
        if(runs == null){

            runs = new ArrayList<>();
        }
        else{
            runs.clear();
        }
        PipeSpecificationsEntity collector = cityGateStationEntity.getCollector();
        if(collector==null){
            collector = new PipeSpecificationsEntity();
        }

        if(runNumberInput.getText().equals("") ||
                runColectorLengthInput.getText().equals("") ||
                runLengthInput.getText().equals("")){return;}

        int runNumber = Integer.parseInt(runNumberInput.getText());
//        ArrayList<Run> runs = new ArrayList<Run>();
        collector.setInsulation(false);
        collector.setLength(Double.parseDouble(runColectorLengthInput.getText()));
        collector.setPipeSizeUnit(collectorComboBox.getValue().toString());
//         collector = new Collector(collectorComboBox.getValue().toString().toString(),
//                Double.parseDouble(runColectorLengthInput.getText()));

        double runLength = Double.parseDouble(runLengthInput.getText());

        ObservableList<Tab> tabs = runTapPane.getTabs();
        for(Tab t : tabs){
            HBox hBox = (HBox) t.getContent();
            ObservableList<Node> obj = hBox.getChildren();
//            System.out.println(obj.get(0));
            GridPane gridPane = (GridPane) obj.get(0);

            ObservableList<Node> tabObject = gridPane.getChildren();

            TextField rundebi = (TextField) tabObject.get(1);
            ComboBox runsize = (ComboBox) tabObject.get(3);
            if(rundebi.getText().equals("")){
                return;
            }


//            System.out.println(rundebi.getText() + " " + runsize.getValue().toString());
            RunEntity run = new RunEntity(runLength, runsize.getValue().toString());
            run = runService.save(run);
            run.setDebiInput(new Debi( Double.parseDouble(rundebi.getText()), Debi.M3_PER_HOUR ));//TODO
            run = runService.save(run);
            runs.add(run);
//            runs.add(new Run(runsize.getValue().toString(),runLength, Double.parseDouble(rundebi.getText()) ));


        }

        collector = pipeSpecificationService.save(collector);
        runs = runService.save(runs);
        cityGateStationEntity.setRuns(runs);
        cityGateStationEntity.setCollector(collector);
        cityGateStationEntity =  cityGateStationService.save(cityGateStationEntity);
        stageManager.setCityGateStationEntity(cityGateStationEntity);
//        Runs allRun = new Runs(runs, collector);
//        Station.getInstance().getList().put("Runs", allRun);
//        System.out.println(StationLogic.getInstance().getList().get("Runs"));
//        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        stageManager.switchScene(FxmlView.STATION);
    }

    public void clear(ActionEvent actionEvent) {
        runNumberInput.clear();
        runColectorLengthInput.clear();
        runLengthInput.clear();
        runTapPane.getTabs().clear();
        Station.getInstance().getList().remove("Runs");
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        cityGateStationEntity.getRuns().clear();
        cityGateStationEntity.setCollector(null);
        cityGateStationEntity = cityGateStationService.save(cityGateStationEntity);
        stageManager.setCityGateStationEntity(cityGateStationEntity);


    }

    public void cancel(ActionEvent actionEvent) {
//        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stageManager.switchScene(FxmlView.STATION);
    }

    @Override
    public void setOnShow() {
//        Runs runs = (Runs) Station.getInstance().getList().get("Runs");
        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();
        List<RunEntity> runs;
        if(cityGateStationEntity!=null){
            if(cityGateStationEntity.getRuns()!=null && cityGateStationEntity.getRuns().size()>0){
                runs = cityGateStationEntity.getRuns();
                runNumberInput.setText(String.valueOf(runs.size()));
                runLengthInput.setText(String.valueOf(runs.get(0).getLength()));

                for (int i = 1; i <= runs.size(); i++) {

                    Tab tab = new Tab();




                    HBox childHBox = new HBox();

                    tab.setText("ران  " + i);
                    GridPane runContainer = new GridPane();
                    runContainer.add(new Label("دبی گاز عبوری "), 1, 0);
                    TextField debiTextField = addDebiValidator(new TextField());

                    debiTextField.setText(String.valueOf(runs.get(i - 1).getDebiInput().getDebi())); // TODO add debi input unit
                    runContainer.add(debiTextField, 0, 0);
                    runContainer.add(new Label("سایز (اینچ) "), 1, 1);
                    ComboBox comboBox = new ComboBox();
                    comboBox.getItems().removeAll();
                    comboBox.getItems().addAll( "2","3","4","6","8","10","12","16","20","24","30");
                    comboBox.getSelectionModel().select(runs.get(i-1).getPipeSizeUnit());
                    runContainer.add(comboBox, 0, 1);

                    childHBox.getChildren().add(runContainer);
                    childHBox.setAlignment(Pos.CENTER);
                    tab.setContent(childHBox);
                    runTapPane.getTabs().add(tab);
                    runTapPane.setMinSize(200,100);
                    runTapPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);




                }

            }
            if(cityGateStationEntity.getCollector()!=null){
                runColectorLengthInput.setText(String.valueOf(cityGateStationEntity.getCollector().getLength()));
                collectorComboBox.getSelectionModel().select(cityGateStationEntity.getCollector().getPipeSizeUnit());
            }
        }

//        if(runs != null){
//            runTapPane.getTabs().clear();
//            runNumberInput.setText(String.valueOf(runs.getRuns().size()));
//            if(runNumberInput.getText().equals("")) return;
//            int runNumber = Integer.parseInt(runNumberInput.getText());
//
//            runLengthInput.setText(String.valueOf(runs.getRuns().get(runs.getRuns().size() - 1).getLength()));
//            runColectorLengthInput.setText(String.valueOf(runs.getCollector().getLength()));
//            collectorComboBox.getSelectionModel().select(runs.getCollector().getSize());
//
//            ArrayList<Run> run = runs.getRuns();
//
//            for (int i = 1; i <= runNumber; i++) {
//
//                Tab tab = new Tab();
//
//
//
//
//                HBox childHBox = new HBox();
//
//                tab.setText("ران  " + i);
//                GridPane runContainer = new GridPane();
//                runContainer.add(new Label("دبی گاز عبوری "), 1, 0);
//                TextField debiTextField = addDebiValidator(new TextField());
//
//                debiTextField.setText(String.valueOf(run.get(i - 1).getDebi()));
//                runContainer.add(debiTextField, 0, 0);
//                runContainer.add(new Label("سایز (اینچ) "), 1, 1);
//                ComboBox comboBox = new ComboBox();
//                comboBox.getItems().removeAll();
//                comboBox.getItems().addAll( "2","3","4","6","8","10","12","16","20","24","30");
//                comboBox.getSelectionModel().select(run.get(i-1).getSize());
//                runContainer.add(comboBox, 0, 1);
//
//                childHBox.getChildren().add(runContainer);
//                childHBox.setAlignment(Pos.CENTER);
//                tab.setContent(childHBox);
//                runTapPane.getTabs().add(tab);
//                runTapPane.setMinSize(200,100);
//                runTapPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
//
//
//
//
//            }
//
//
//        }

    }

    private TextField addDebiValidator(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    textField.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Double number = Double.parseDouble(textField.getText());
                        if(number > 100000000){
                            textField.setText("100000000");
                        }
                        else if(number <0){
                            textField.setText("0");
                        }


                    }
                    catch (Exception e){

                        textField.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });
        return textField;
    }
}
