package com.codetreatise.controller;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ChartController extends BaseController {

    @FXML
    public Button backButton;
    public Button refreshBtn;
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private SecController secController;

    public LineChart secChart;

    public CategoryAxis x;

    public NumberAxis y;

    private Stage primaryStage;

    @Override
    public void setOnShow() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        XYChart.Series set1 = new XYChart.Series<>();
        XYChart.Series set2 = new XYChart.Series<>();
        set1.getData().clear();
        set2.getData().clear();

        List<ConditionEntity> conditions = secController.getSecEntity().getConditions();
        Iterator<ConditionEntity> it = conditions.iterator();
        if (conditions.size() > 0) {
            do {
                // statements
                ConditionEntity conditionEntity =  it.next();
                if(conditionEntity.getResult()== null){
                    continue;
                }
                else if(conditionEntity.getResult().getSingleCalculation() == null){
                    continue;
                }
                else if(conditionEntity.getResult().getSingleCalculation().get(0) == null){
                    continue;

                }else if(conditionEntity.getResult().getSingleCalculation().get(0).getHeaters() == null){
                    continue;
                }
                try {



//                String xDate = new JalaliCalendar(new GregorianCalendar(conditionEntity.getTime().getYear(), conditionEntity.getTime().getYear(), conditionEntity.getTime().getYear())).toString();
//                System.out.println(xDate);
                    String xDAta = conditionEntity.getTime().toString();

                    double yDate = conditionEntity.getResult().getSingleCalculation().get(0).getHeaters().getConsumption() * 24;
                    double yData2 = conditionEntity.getResult().getSingleCalculation().get(1).getHeaters().getConsumption() * 24;
                    set1.getData().add(new XYChart.Data(xDAta, yDate));
                    set2.getData().add(new XYChart.Data(xDAta, yData2));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            } while (it.hasNext());

        }


        secChart.getData().addAll(set1,set2);

    }

    public void backButton(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.SEC);
    }

    public void refresh(ActionEvent actionEvent) {
        secChart.getData().clear();
        initialize(null,null);
    }
}
