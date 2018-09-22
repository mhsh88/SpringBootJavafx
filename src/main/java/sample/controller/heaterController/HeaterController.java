package sample.controller.heaterController;

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
import javafx.scene.layout.VBox;
import sample.controller.base.BaseController;
import sample.controller.calculate.CalculateController;
import sample.model.Station;
import sample.model.burner.Burner;
import sample.model.heater.HeaterModel;
import sample.model.heaters.HeatersModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HeaterController extends BaseController{

    public TextField heaterNumberInput;
    public TabPane tabPane = new TabPane();

    @FXML
    public void initialize() throws IOException {
        tabPane.getTabs().clear();
        tabPane.setMinSize(400,200);

        heaterNumberInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
                    heaterNumberInput.setText(newValue.replaceAll("[^[A-z]+$]", ""));
                }
                else{
                    try{
                        Integer number = Integer.parseInt(heaterNumberInput.getText());
                        if(number > 20){
                            heaterNumberInput.setText("30");
                        }
                        else if(number <0){
                            heaterNumberInput.setText("0");
                        }


                    }
                    catch (Exception e){

                        heaterNumberInput.setText(newValue.replaceAll("[^\\d]",""));

                    }
                }
            }
        });

    }

    @FXML
    public void okAction(ActionEvent actionEvent) {
        HeatersModel stationHeatersModel = new HeatersModel();
        ArrayList<HeaterModel> heaterModels = new ArrayList<HeaterModel>();

        ObservableList<Tab> Tabs = tabPane.getTabs();
        int heaterNumber = Tabs.size();
//        System.out.println(Tabs);
        for(Tab t: Tabs) {
            VBox vbox = (VBox) t.getContent();
//            TabPane tPane = (TabPane) hBoxes.getChildren();
            ObservableList<Node> hboxobj = vbox.getChildren();
            HBox hbox1 = (HBox) hboxobj.get(0);
            ObservableList<Node> onjInHBox1 = hbox1.getChildren();
            TextField heaterRandeman = (TextField) onjInHBox1.get(0);
            System.out.println(heaterRandeman.getText());
            HBox hbox = (HBox) hboxobj.get(1);
            ObservableList<Node> obj = hbox.getChildren();
            TabPane childTabPane = (TabPane) obj.get(0);
            ObservableList<Tab> childTabs = childTabPane.getTabs();

//             burners = null;
            ArrayList<Burner> burners = new ArrayList<Burner>();
            for (Tab tt : childTabs) {

                HBox childhbox = (HBox) tt.getContent();
                ObservableList<Node> gridList = childhbox.getChildren();
                GridPane gridPane = (GridPane) gridList.get(0);
                ObservableList<Node> gridPaneChildren = gridPane.getChildren();
                TextField oxygenTextField = (TextField) gridPaneChildren.get(1);
                TextField flueGasTempTextField = (TextField) gridPaneChildren.get(3);
                System.out.println(oxygenTextField.getText());
                System.out.println(flueGasTempTextField.getText());

                if(!oxygenTextField.getText().equals("") && !flueGasTempTextField.getText().equals("")) {
                    burners.add(new Burner(Double.parseDouble(oxygenTextField.getText()), Double.parseDouble(flueGasTempTextField.getText())));
                }

            }
            if(heaterRandeman.getText().equals("")) {
                if(!(burners.size() < 1)) {
                    heaterModels.add(new HeaterModel(0.75, burners));
                }

            }
            else{
                if(!(burners.size() < 1)){
                    heaterModels.add(new HeaterModel(Double.parseDouble(heaterRandeman.getText()) / 100, burners));
                }
            }



        }
        if(heaterModels.size() < 1){
            showAlert("خطا","خطا در اطلاعات ورودی" , "اطلاعاتی برای هیچ کدام از گرم‌کن‌ها وارد نشده است!", Alert.AlertType.ERROR);
            return;
        }
        stationHeatersModel.setHeaterModels(heaterModels);

        Station.getInstance().getList().put("HeatersModel", stationHeatersModel);
        System.out.println(Station.getInstance().getList().get("HeatersModel"));

//        StationLogic station = StationLogic.getInstance();
//        station.getList().put();

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();


    }
    @FXML
    private void textInput(KeyEvent keyEvent) {
        tabPane.getTabs().clear();
//        tabPane.setMinSize(300,200);
        if(heaterNumberInput.getText().equals("")) return;
        int heaterNumber = Integer.parseInt(heaterNumberInput.getText());
        if(heaterNumber>20){
            heaterNumberInput.setText("20");
            heaterNumber = 20;

        }
        else if(heaterNumber<0){
            heaterNumberInput.setText("0");
            heaterNumber = 0;
        }
        String[] heaterList = new String[heaterNumber];
        for(int i = 1; i <= heaterNumber; i++){
            heaterList[i-1] = String.valueOf(i);
        }
//        System.out.println(heaterNumberInput.getText());
//        TabPane tabPane = new TabPane();
//        BorderPane borderPane = new BorderPane();
        for (int i = 1; i <= heaterNumber; i++) {
            Tab tab = new Tab();
//            tab.setGraphic(new Circle(0, 0, 10));
            tab.setText("گرم کن " + i);
            TabPane burnerTabPane = new TabPane();
            VBox childVBox = new VBox();
            Label randemanLabel = new Label("بازده جذب گرمایی کویل‌های گرم کن" + CalculateController.percent);
            TextField randemanTextField = addRandemanValidator(new TextField());
            HBox randemanHbox = new HBox();
            randemanHbox.getChildren().add(randemanTextField);
            randemanHbox.getChildren().add(randemanLabel);

            randemanHbox.setAlignment(Pos.CENTER);

            childVBox.getChildren().add(randemanHbox);


            for(int j = 1; j <= 3; j++){
                Tab burnerTab = new Tab();




                HBox childHBox = new HBox();

                burnerTab.setText("مشعل " + j);
                GridPane burnerContainer = new GridPane();
                burnerContainer.add(new Label("درصد اکسیژن ٪"), 1, 0);
                burnerContainer.add(addOxygenValidator(new TextField()), 0, 0);
                burnerContainer.add(new Label("دمای دودکش "), 1, 1);
                burnerContainer.add(addFlueGasValidator(new TextField()), 0, 1);
                childHBox.getChildren().add(burnerContainer);
                childHBox.setAlignment(Pos.CENTER);
                burnerTab.setContent(childHBox);
                burnerTabPane.getTabs().add(burnerTab);
                burnerTabPane.setMinSize(300, 200);
                burnerTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            }
            HBox hbox = new HBox();
//            hbox.getChildren().add(new Label("گرم کن " + i));
            hbox.getChildren().add(burnerTabPane);
            hbox.setAlignment(Pos.CENTER);
            childVBox.getChildren().add(hbox);
            childVBox.setAlignment(Pos.CENTER);

            tab.setContent(childVBox);
            tabPane.getTabs().add(tab);
        }

    }

    public void cancelButton(ActionEvent actionEvent) {

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void clearButton(ActionEvent actionEvent) {
        heaterNumberInput.clear();
        tabPane.getTabs().clear();
        Station.getInstance().getList().remove("HeatersModel");
    }

    @Override
    public void setOnShow() {
        HeatersModel heatersModel = (HeatersModel) Station.getInstance().getList().get("HeatersModel");
        if(heatersModel != null){
            tabPane.getTabs().clear();
            ArrayList<HeaterModel> heaters = heatersModel.getHeaterModels();

            heaterNumberInput.setText(String.valueOf(heaters.size()));
//        tabPane.setMinSize(300,200);
            if(heaterNumberInput.getText().equals("")) return;
            int heaterNumber = Integer.parseInt(heaterNumberInput.getText());
            if(heaterNumber>20){
                heaterNumberInput.setText("20");
                heaterNumber = 20;

            }
            else if(heaterNumber<0){
                heaterNumberInput.setText("0");
                heaterNumber = 0;
            }
            String[] heaterList = new String[heaterNumber];
            for(int i = 1; i <= heaterNumber; i++){
                heaterList[i-1] = String.valueOf(i);
            }
//        System.out.println(heaterNumberInput.getText());
//        TabPane tabPane = new TabPane();
//        BorderPane borderPane = new BorderPane();
            for (int i = 1; i <= heaterNumber; i++) {
                Tab tab = new Tab();
//            tab.setGraphic(new Circle(0, 0, 10));
                tab.setText("گرم کن " + i);
                TabPane burnerTabPane = new TabPane();
                VBox childVBox = new VBox();
                Label randemanLabel = new Label("راندمان حرارتی گرم کن");
                TextField randemanTextField = addRandemanValidator(new TextField());

                randemanTextField.setText(String.valueOf(Math.round(heaters.get(i-1).getEfficiency() * 100)));
                HBox randemanHbox = new HBox();
                randemanHbox.getChildren().add(randemanTextField);
                randemanHbox.getChildren().add(randemanLabel);

                randemanHbox.setAlignment(Pos.CENTER);

                childVBox.getChildren().add(randemanHbox);

                ArrayList<Burner> burners = heaters.get(i-1).getBurners();
                for(int j = 1; j <= 3; j++){
                    Tab burnerTab = new Tab();




                    HBox childHBox = new HBox();

                    burnerTab.setText("مشعل " + j);
                    GridPane burnerContainer = new GridPane();
                    burnerContainer.add(new Label("درصد اکسیژن ٪"), 1, 0);
                    TextField oxygenTextField =  addOxygenValidator(new TextField());
                    TextField flueTextField =  addFlueGasValidator(new TextField());

                        try{
                            oxygenTextField.setText(String.valueOf(burners.get(j-1).getOxygenPecent()));
                            flueTextField.setText(String.valueOf(burners.get(j-1).getFlueGasTemp()));

                        }
                        catch (IndexOutOfBoundsException e){

                        }


                    burnerContainer.add(oxygenTextField, 0, 0);
                    burnerContainer.add(new Label("دمای دودکش "), 1, 1);
                    burnerContainer.add(flueTextField, 0, 1);
                    childHBox.getChildren().add(burnerContainer);
                    childHBox.setAlignment(Pos.CENTER);
                    burnerTab.setContent(childHBox);
                    burnerTabPane.getTabs().add(burnerTab);
                    burnerTabPane.setMinSize(300, 200);
                    burnerTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

                }
                HBox hbox = new HBox();
//            hbox.getChildren().add(new Label("گرم کن " + i));
                hbox.getChildren().add(burnerTabPane);
                hbox.setAlignment(Pos.CENTER);
                childVBox.getChildren().add(hbox);
                childVBox.setAlignment(Pos.CENTER);

                tab.setContent(childVBox);
                tabPane.getTabs().add(tab);
            }



        }


    }
    private TextField addOxygenValidator(TextField textField){
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
                        if(number > 20.99){
                            textField.setText("20.99");
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

    private TextField addRandemanValidator(TextField textField){
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
                        if(number >= 100){
                            textField.setText("100");
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

    private TextField addFlueGasValidator(TextField textField){
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
                        if(number > 1500){
                            textField.setText("1500");
                        }
                        else if(number <0){
                            textField.setText("-60");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
