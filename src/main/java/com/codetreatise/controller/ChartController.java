package com.codetreatise.controller;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.SecService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class ChartController extends BaseController {

    public JFXTextField fifteenTextField;
    public JFXTextField eightTextField;
    public JFXTextField hydrateTextField;
    public JFXTextField inputTextField;
//    private String decimalPattern = "([0-9]*)\\.([0-9]*)";
    private String decimalPattern = "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
        "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
        "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
        "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";

    @FXML
    public Button backButton;
    public Button refreshBtn;
    public Button fifteenBtn;
    public Button eightBtn;
    public Button hydrateBtn;
    public Button inputBtn;
    public Label fifteenLabel;
    public Label eightLabel;
    public Label inputLabel;
    public Label hydrateLabel;
    DecimalFormat df = new DecimalFormat("#.###");
    private double sumInput = 0.0;
    private double sumHydrate = 0.0;
    private double sumEight = 0.0;
    private double sumFifteen = 0.0;
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private SecController secController;

    @Autowired
    private SecService secService;



    public LineChart secChart;

    public CategoryAxis x;

    public NumberAxis y;

    private Stage primaryStage;
    private boolean checking = false;

    @Override
    public void setOnShow() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        XYChart.Series inputSet = new XYChart.Series<>();
        XYChart.Series hydrateSet = new XYChart.Series<>();
        XYChart.Series eightSet = new XYChart.Series<>();
        XYChart.Series fifteenSet = new XYChart.Series<>();
        inputSet.getData().clear();
        hydrateSet.getData().clear();
        eightSet.getData().clear();
        fifteenSet.getData().clear();

        List<ConditionEntity> conditions = secService.find(secController.getSecEntity().getId()).getConditions();
        Iterator<ConditionEntity> it = conditions.iterator();
        ConditionEntity first = conditions.iterator().next();
        ConditionEntity second = null;
         sumInput = 0.0;
         sumHydrate = 0.0;
         sumEight = 0.0;
         sumFifteen = 0.0;


        if (conditions.size() > 0) {

            do {
                // statements
                second =  it.next();

                if(second.getResult()== null){
                    continue;
                }
                else if(second.getResult().getSingleCalculation() == null){
                    continue;
                }
                else if(second.getResult().getSingleCalculation().get(0) == null){
                    continue;

                }else if(second.getResult().getSingleCalculation().get(0).getHeaters() == null){
                    continue;
                }
                try {



//                String xDate = new JalaliCalendar(new GregorianCalendar(conditionEntity.getTime().getYear(), conditionEntity.getTime().getYear(), conditionEntity.getTime().getYear())).toString();
//                System.out.println(xDate);
                    String xDAta = second.getTime().toString();

                    long hours = DAYS.between(first.getTime(),second.getTime()) * 24;
                    double input = second.getResult().getSingleCalculation().get(0).getHeaters().getConsumption() * hours;
                    double hydrate = second.getResult().getSingleCalculation().get(1).getHeaters().getConsumption() * hours;
                    double eight = second.getResult().getEightTemp().get(0).getHeaters().getConsumption() * hours;
                    double fifteen = second.getResult().getFifteenTemp().get(0).getHeaters().getConsumption() * hours;

                    sumInput += input;
                    sumHydrate += hydrate;
                    sumEight += eight;
                    sumFifteen += fifteen;

                    inputSet.getData().add(new XYChart.Data(xDAta, input));
                    hydrateSet.getData().add(new XYChart.Data(xDAta, hydrate));
                    eightSet.getData().add(new XYChart.Data(xDAta, eight));
                    fifteenSet.getData().add(new XYChart.Data(xDAta, fifteen));

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                first = second;
            } while (it.hasNext());

            LocalDate initialTime = conditions.get(0).getTime();
            LocalDate endDuration = conditions.get(conditions.size()-1).getTime();
            double days = DAYS.between(initialTime, endDuration);
            sumInput = sumInput / days * 365;
            sumHydrate = sumHydrate / days * 365;
            sumEight = sumEight / days * 365;
            sumFifteen = sumFifteen / days * 365;
            inputSet.setName("اطلاعات ورودی");
            hydrateSet.setName("دمای هیدرات");
            fifteenSet.setName("دمای ۱۵ درجه سلسیوس");
            eightSet.setName("دمای ۸ درجه سلسیوس");

        }




        secChart.getData().addAll(inputSet,hydrateSet,eightSet,fifteenSet);

        inputLabel.setText(String.valueOf(df.format(sumInput)) + Debi.SPACE+ Debi.M3);
        hydrateLabel.setText(String.valueOf(df.format(sumHydrate))+ Debi.SPACE+ Debi.M3);
        eightLabel.setText(String.valueOf(df.format(sumEight))+ Debi.SPACE+ Debi.M3);
        fifteenLabel.setText(String.valueOf(df.format(sumFifteen))+ Debi.SPACE+ Debi.M3);


        eightTextField.setText("0");
        inputTextField.setText("0");
        fifteenTextField.setText("0");
        hydrateTextField.setText("0");



    }


    public void backButton(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.SEC);
    }

    public void refresh(ActionEvent actionEvent) {
        secChart.getData().clear();
        initialize(null,null);
    }

    public void fifteen(ActionEvent actionEvent) {
        if(fifteenTextField.getText()!= null && !fifteenTextField.getText().equals("") && Pattern.matches(decimalPattern, fifteenTextField.getText())){


            fifteenLabel.setText(String.valueOf(df.format(sumFifteen * (1 + Double.parseDouble(fifteenTextField.getText())/100)))+ Debi.SPACE+ Debi.M3);
        }

    }

    public void eight(ActionEvent actionEvent) {
        if(eightTextField.getText()!= null && !eightTextField.getText().equals("") && Pattern.matches(decimalPattern, eightTextField.getText())){


            eightLabel.setText(String.valueOf(df.format(sumEight * (1 + Double.parseDouble(eightTextField.getText())/100)))+ Debi.SPACE+ Debi.M3);
        }


    }

    public void hydrate(ActionEvent actionEvent) {

        if(hydrateTextField.getText()!= null && !hydrateTextField.getText().equals("") && Pattern.matches(decimalPattern, hydrateTextField.getText())){


            hydrateLabel.setText(String.valueOf(df.format(sumHydrate * (1 + Double.parseDouble(hydrateTextField.getText())/100)))+ Debi.SPACE+ Debi.M3);
        }


    }

    public void input(ActionEvent actionEvent) {
        if(inputTextField.getText()!= null && !inputTextField.getText().equals("") && Pattern.matches(decimalPattern, inputTextField.getText())){


            inputLabel.setText(String.valueOf(df.format(sumInput * (1 + Double.parseDouble(inputTextField.getText())/100)))+ Debi.SPACE+ Debi.M3);
        }

    }
}
