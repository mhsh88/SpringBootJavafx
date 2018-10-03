package com.codetreatise.controller;

import com.codetreatise.bean.station.*;
import com.codetreatise.bean.unitNumber.DebiConstants;
import com.codetreatise.bean.unitNumber.PressureConstants;
import com.codetreatise.bean.unitNumber.TemperatureConstants;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.CityGateStationService;
import ir.behinehsazan.gasStation.model.burner.Burner;
import ir.behinehsazan.gasStation.model.gas.Gas;
import ir.behinehsazan.gasStation.model.heater.Heater;
import ir.behinehsazan.gasStation.model.heater.Heaters;
import ir.behinehsazan.gasStation.model.pipeLine.base.BasePipe;
import ir.behinehsazan.gasStation.model.regulator.Regulator;
import ir.behinehsazan.gasStation.model.run.base.BaseRun;
import ir.behinehsazan.gasStation.model.station.StationLogic;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import sample.controller.base.BaseController;
import sample.controller.showResults.ShowResultsController;
import sample.model.Station;
import sample.model.heater.HeaterModel;
import sample.model.heaters.HeatersModel;
import sample.model.pipeLine.PipeLine;
import sample.model.run.Collector;
import sample.model.run.Run;
import sample.model.run.Runs;
import sample.model.showResultEntity.Table;
import sample.model.stationProperties.StationPropertice;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class CalculateController extends BaseController{

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CityGateStationService cityGateStationService;

    public final static String celciusDegree = " (C°)";
    public final static String percent = " (٪)";
    public final static String pressureDegree = " (kPa)";
    public final static String PSI = "Psi";
    public final static String KPA = "kPa";
    public final static String MPA = "MPa";
    public final static String CELCIUS = "°C";
    public final static String FARENHEIT = "°F";
    public final static String MINUS = "-";
    public final static String SPACE = " ";


    public final static String cubicMeter = " متر مکعب بر ساعت ";
    public final static String windSpeed = " m/s ";
    double Th;


    public List<StationLogic> calculate(CityGateStationEntity cityGateStationEntity) throws Exception {
        boolean state = false;
        List<StationLogic> stationLogics = new ArrayList<>();

//        CityGateStationEntity cityGateStationEntity = stageManager.getCityGateStationEntity();

        Runs runs = new Runs();
        if (cityGateStationEntity == null) {
    //            showAlert("خطا", "خطا در اطلاعات ورودی", "اطلاعات ایستگاه تکمیل نشده است", Alert.AlertType.ERROR);
            throw new Exception("city gate station is null");
//            return null;
        }
        if (cityGateStationEntity != null) {
            if (cityGateStationEntity.getCondition() == null || cityGateStationEntity.getGasEntity() == null) {
//                showAlert("خطا", "خطا در اطلاعات ورودی", "اطلاعات ایستگاه تکمیل نشده است", Alert.AlertType.ERROR);
                throw new Exception("city gate station is null");
            }
            if (cityGateStationEntity.getRuns() != null && cityGateStationEntity.getRuns().size() > 0) {

                ArrayList<Run> logicRuns = new ArrayList<>();
                for (RunEntity runEntity : cityGateStationEntity.getRuns()) {
                    Run run = new Run(runEntity.getPipeSizeUnit(), runEntity.getLength(), runEntity.getDebiInput().getDebi());
                    logicRuns.add(run);
                }
                runs.setRuns(logicRuns);

            }
            if (cityGateStationEntity.getCollector() != null) {
                Collector collector = new Collector(cityGateStationEntity.getCollector().getPipeSizeUnit(),
                        cityGateStationEntity.getCollector().getLength());
                runs.setCollector(collector);
            }

        }

        if (runs != null && runs.getRuns() != null && runs.getRuns().size() > 0) {
            double tempDebi = 0;
            for (int i = 0; i < runs.getRuns().size(); i++) {
                tempDebi += runs.getRuns().get(i).getDebi();

            }
            if (cityGateStationEntity.getCondition().getDebiInput().getDebi() < tempDebi) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("توجه");
                alert.setHeaderText("مجموع دبی عبوری تعیین شده برای ران‌ها بیش از مقدار مشخص شده برای ایستگاه است.");
                alert.setContentText("آیا مایل به اصلاح میزان دبی عبوری ایستگاه هستید؟");
                ButtonType okButton = new ButtonType("بله", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("خیر", ButtonBar.ButtonData.NO);
//                    ButtonType cancelButton = new ButtonType("خروج", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == okButton) {
                    cityGateStationEntity.getCondition().getDebiInput().setDebi(tempDebi);
//                    cityGateStationEntity = cityGateStationService.save(cityGateStationEntity);
//                    stageManager.setCityGateStationEntity(cityGateStationEntity);
                    // ... user chose "One"
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }


        stationLogics.add(getStationLogic(cityGateStationEntity, runs, true));
        if(cityGateStationEntity.isRequiredHydrate()){
            stationLogics.add(getStationLogic(cityGateStationEntity, runs, false));
        }

        return stationLogics;
    }

//        Station station = Station.getInstance();
//        Runs runs = (Runs) station.getList().get("Runs");

//        StationPropertice stationPropertice = (StationPropertice) station.getList().get("stationPropertice");
//        HeatersModel heatersModel = (HeatersModel) station.getList().get("HeatersModel");
    private StationLogic getStationLogic(CityGateStationEntity cityGateStationEntity, Runs runs, boolean userInput){

        StationLogic stationLogic = new StationLogic();

        PipeLine beforeHeaterPipeline = null;
        if(cityGateStationEntity.getBeforeHeater()!=null){
            PipeSpecificationsEntity pipeSpecificationsEntity = cityGateStationEntity.getBeforeHeater();
            beforeHeaterPipeline = new PipeLine(pipeSpecificationsEntity.getPipeSizeUnit(), pipeSpecificationsEntity.getLength());
            beforeHeaterPipeline.setInsulation(pipeSpecificationsEntity.isInsulation());
            beforeHeaterPipeline.setInsulationFactor(pipeSpecificationsEntity.getInsulationFactor());
            beforeHeaterPipeline.setInsulationThickness(pipeSpecificationsEntity.getInsulationThickness());
        }
        HeatersModel heatersModel = null;
        if(cityGateStationEntity.getHeaters() != null && cityGateStationEntity.getHeaters().size()>0){
            heatersModel = new HeatersModel();
            ArrayList<HeaterModel> heaterModels = new ArrayList<>();
            for(HeaterEntity heaterEntity : cityGateStationEntity.getHeaters()){
                ArrayList<sample.model.burner.Burner> burners = new ArrayList<>();
                for(BurnersEntity burnersEntity : heaterEntity.getBurners()){
                    sample.model.burner.Burner burner = new sample.model.burner.Burner(burnersEntity.getOxygenPercent(), burnersEntity.getFlueGasTemprature());
                    burners.add(burner);
                }
                HeaterModel heaterModel = new HeaterModel(heaterEntity.getEfficiency(), burners);
                heaterModels.add(heaterModel);
            }
            heatersModel.setHeaterModels(heaterModels);

        }
        PipeLine afterHeaterPipeline = null;
        if(cityGateStationEntity.getAfterHeater()!=null){
            PipeSpecificationsEntity pipeSpecificationsEntity = cityGateStationEntity.getAfterHeater();
            afterHeaterPipeline = new PipeLine(pipeSpecificationsEntity.getPipeSizeUnit(), pipeSpecificationsEntity.getLength());
            afterHeaterPipeline.setInsulation(pipeSpecificationsEntity.isInsulation());
            afterHeaterPipeline.setInsulationFactor(pipeSpecificationsEntity.getInsulationFactor());
            afterHeaterPipeline.setInsulationThickness(pipeSpecificationsEntity.getInsulationThickness());
        }

        GasEntity gasEntity = cityGateStationEntity.getGasEntity();
        Gas gas = new Gas();
        Double[] component = gasEntity.getComponent();
        gas.setComponent(component);
        stationLogic.setGas(gas);
        ConditionEntity conditionEntity = cityGateStationEntity.getCondition();
        stationLogic.setTenv(conditionEntity.getEnvTemperature()!=null? conditionEntity.getEnvTemperature().getCostumeTemperature(TemperatureConstants.KELVIN) : null);//TODO
        stationLogic.setVair(conditionEntity.getWindSpeed());
        stationLogic.setDebi(conditionEntity.getDebiInput().getCostumeDebi(DebiConstants.M3_PER_HOUR)); //TODO
        stationLogic.setTin(conditionEntity.getInputTemperature().getCostumeTemperature(TemperatureConstants.KELVIN));
        stationLogic.setPin(conditionEntity.getInputPressure().getCostumePressure(PressureConstants.KPA));
        if(userInput) {
            stationLogic.setTout(conditionEntity.getOutputTemperature().getCostumeTemperature(TemperatureConstants.KELVIN));
        }
        else{
            gas.calculate(conditionEntity.getOutputPressure().getCostumePressure(PressureConstants.KPA),
                    conditionEntity.getOutputTemperature().getCostumeTemperature(TemperatureConstants.KELVIN),component);
            Th = gas.getT_h();
            if(Th>50){
               stationLogic.setMessage("دمای هیدرات برای شرایط زیر قابل محاسبه نیست");
               return stationLogic;
            }
            stationLogic.setTout(Th + 273.15 + 2);
        }
        if(!userInput)
            stationLogic.setMessage("T_h");
        stationLogic.setPout(conditionEntity.getOutputPressure().getCostumePressure(PressureConstants.KPA));

       stationLogic.setBeforeHeater(beforeHeaterPipeline);

        stationLogic.setRegulator();


        stationLogic.setRuns(runs);

        stationLogic.setCollector(runs);




       stationLogic.setAfterHeater(afterHeaterPipeline);


        stationLogic.setHeaters(heatersModel);
        return stationLogic;

    }


    private void saveHydrateResults() {
        ObservableList<Table> data = ShowResultsController.getHydrateData();
        data.clear();

        StationLogic stationLogic = Station.getInstance().getStationLogic();
        StationPropertice stationPropertice = (StationPropertice) Station.getInstance().getList().get("stationPropertice");
        if (stationPropertice == null) {
            return;

        }
        else{
            data.add(new Table("محاسبات بر اساس دمای هیدرات به علاوه ۲ درجه", "", ""));
//            System.out.println(Th);
            data.add(new Table("دمای هیدرات ", CELCIUS, String.valueOf(Th)));

        }
        if (stationLogic != null) {
            if (stationLogic.getBeforeHeater() != null) {


//                    BasePipe beforeHeater = stationLogic.getBeforeHeater();
//                    data.add(new Table("خط لوله قبل از گرم کن", ""));
//
//                    data.add(new Table("دمای گاز ورودی", String.valueOf(beforeHeater.getTin() - 273.15)));
//                    data.add(new Table("فشار گاز ورودی", String.valueOf(beforeHeater.getPin())));
//                    data.add(new Table("دمای گاز خروجی", String.valueOf(beforeHeater.getTout() - 273.15)));
//                    data.add(new Table("فشار گاز خروجی", String.valueOf(beforeHeater.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(beforeHeater.getTin() - 273.15 - beforeHeater.getTout() - 273.15)));
//                    data.add(new Table("تلفات حرارتی", String.valueOf(beforeHeater.getConsumption())));
                if(Station.getInstance().getList().get("beforeHeaterPipeLine") !=null) {
                    PipeLine beforeHeaterSampleModel = (PipeLine) Station.getInstance().getList().get("beforeHeaterPipeLine");
                    data.add(new Table("خط لوله قبل از گرم کن", "", ""));

                    data.add(new Table("دمای گاز ورودی", CELCIUS, String.valueOf(beforeHeaterSampleModel.getTin() - 273.15)));
                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(beforeHeaterSampleModel.getPin())));
                    data.add(new Table("دمای گاز خروجی", CELCIUS, String.valueOf(beforeHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(beforeHeaterSampleModel.getPout())));
                    data.add(new Table("اختلاف دما", CELCIUS, String.valueOf(beforeHeaterSampleModel.getTin() - 273.15 - beforeHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table(   "تلفات حرارتی با عایق حرارتی" , cubicMeter, String.valueOf(beforeHeaterSampleModel.getWithInsulationConsumption())));
                    data.add(new Table("تلفات حرارتی بدون عایق حرارتی" , cubicMeter, String.valueOf(beforeHeaterSampleModel.getNotIsulationConsumption())));



                }

            }
        }

        if (stationLogic != null) {
            if (stationLogic.getHeaters() != null) {

                Heaters heaters = stationLogic.getHeaters();
                List<Heater> heater = heaters.getHeaters();
                data.add(new Table("اطلاعات گرم کن ", "", ""));
                int temp = 1;
                for (Heater h : heater) {

                    data.add(new Table("گرم کن " + temp, "", ""));
                    data.add(new Table("بازده جذب گرمایی کویل‌ها " + temp, percent, String.valueOf(h.getEfficiency() * 100)));

                    List<Burner> burners = h.getBurners();
                    int temp2 = 1;
                    for (Burner b : burners) {
                        data.add(new Table("دمای دودکش مشعل " + temp2 , CELCIUS, String.valueOf(b.getTstack())));
                        data.add(new Table("درصد اکسیژن مشعل " + temp2, percent, String.valueOf(b.getOxygen())));
                        data.add(new Table("بازده ناخالص (gross) مشعل " + temp2, percent, String.valueOf(b.getEfficiency() * 100)));
                        double tem = b.getConsumption();
                        data.add(new Table("مصرف مشعل " + temp2 , cubicMeter, String.valueOf(b.getConsumption())));

                        temp2++;
                    }
                    temp++;
                }
                data.add(new Table("مصرف گرمکن‌ها" , cubicMeter, String.valueOf(heaters.getConsumption())));
            }
        }

        if (stationLogic != null) {
            if (stationLogic.getAfterHeater() != null) {

//                BasePipe afterHeater = stationLogic.getAfterHeater();
//                data.add(new Table("خط لوله بعد از گرم کن", ""));
//                data.add(new Table("دمای گاز ورودی", String.valueOf(afterHeater.getTin() - 273.15)));
//
//                data.add(new Table("فشار گاز ورودی", String.valueOf(afterHeater.getPin())));
//                data.add(new Table("دمای گاز خروجی", String.valueOf(afterHeater.getTout() - 273.15)));
//                data.add(new Table("فشار گاز خروجی", String.valueOf(afterHeater.getPout())));
//                data.add(new Table("اختلاف دما", String.valueOf(afterHeater.getTin() - 273.15 - afterHeater.getTout() - 273.15)));
//                data.add(new Table("تلفات حرارتی", String.valueOf(afterHeater.getConsumption())));

                if(Station.getInstance().getList().get("afterHeaterPipeLine") !=null) {
                    PipeLine afterHeaterSampleModel = (PipeLine) Station.getInstance().getList().get("afterHeaterPipeLine");
                    data.add(new Table("خط لوله بعد از گرم کن", "", ""));

                    data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(afterHeaterSampleModel.getTin() - 273.15)));
                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(afterHeaterSampleModel.getPin())));
                    data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(afterHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(afterHeaterSampleModel.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(afterHeaterSampleModel.getTin()  - afterHeaterSampleModel.getTout() )));
                    data.add(new Table("تلفات حرارتی با عایق حرارتی" , cubicMeter,  String.valueOf(-1 * afterHeaterSampleModel.getWithInsulationConsumption())));
                    data.add(new Table("تلفات حرارتی بدون عایق حرارتی" , cubicMeter, String.valueOf(-1 * afterHeaterSampleModel.getNotIsulationConsumption())));



                }


            }
        }

        if (stationLogic != null) {
            if (stationLogic.getCollector() != null) {

                BasePipe collector = stationLogic.getCollector();
                data.add(new Table("اطلاعات کلکتور", "", ""));
                data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(collector.getTin() - 273.15)));

                data.add(new Table( "فشار گاز ورودی" , PSI, String.valueOf(collector.getPin())));
                data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(collector.getTout() - 273.15)));
                data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(collector.getPout())));
//                data.add(new Table("اختلاف دما", String.valueOf(collector.getTin() - 273.15 - collector.getTout() - 273.15)));
                data.add(new Table("تلفات حرارتی" , cubicMeter, String.valueOf(-1 * collector.getConsumption())));
            }
        }
        if (stationLogic != null) {
            if (stationLogic.getRuns() != null) {
                data.add(new Table("اطلاعات ران ها", "", ""));

                ir.behinehsazan.gasStation.model.run.Runs runs = stationLogic.getRuns();
                List<BaseRun> run = runs.getRuns();
                int i = 1;
                for (BaseRun r : run) {
                    data.add(new Table("ران " + i, "",""));

                    data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(r.getTin() - 273.15)));
                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(r.getPin())));
                    data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(r.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(r.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(r.getTin() - 273.15 - r.getTout() - 273.15)));
                    data.add(new Table("تلفات حرارتی" , cubicMeter, String.valueOf(-1 * r.getConsumption())));
                    i++;
                }
            }
        }

        if (stationLogic != null) {
            if (stationLogic.getRegulator() != null) {
                Regulator regulator = stationLogic.getRegulator();
                data.add(new Table("رگولاتور", "", ""));
                data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(regulator.getTin() - 273.15)));
                data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(regulator.getPin())));
                data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(regulator.getTout() - 273.15)));
                data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(regulator.getPout())));
            }
        }


    }

    public static void saveUserTempInputResults() {
        ObservableList<Table> data = ShowResultsController.getData();
        data.clear();

        StationLogic stationLogic = Station.getInstance().getStationLogic();
        StationPropertice stationPropertice = (StationPropertice) Station.getInstance().getList().get("stationPropertice");
        if (stationPropertice != null) {
            data.add(new Table("استان", "", stationPropertice.getProvince()));
            data.add(new Table("شهر", "", stationPropertice.getCity()));
            data.add(new Table("منطقه", "", stationPropertice.getArea()));
            data.add(new Table("آدرس", "", stationPropertice.getAddress()));
            data.add(new Table("ظرفیت نامی" , cubicMeter, stationPropertice.getNominalCapacity()));
            if(stationPropertice.getEnvironmentTemp()!=null) {
                data.add(new Table("دمای محیط" , CELCIUS, String.valueOf(stationPropertice.getEnvironmentTemp() - 273.15)));
            }
            data.add(new Table("سرعت باد" , windSpeed, String.valueOf(stationPropertice.getWindVelocity())));
            data.add(new Table("دمای گاز ورودی به ایستگاه" , CELCIUS, String.valueOf(stationPropertice.getInputTemp() - 273.15)));
            data.add(new Table("فشار گاز ورودی به ایستگاه" , PSI, String.valueOf(stationPropertice.getInputPressure())));
            data.add(new Table("دمای گاز خروجی از ایستگاه" , CELCIUS, String.valueOf(stationPropertice.getOutputTemp() - 273.15)));
            data.add(new Table("فشار گاز خروجی از ایستگاه" , PSI, String.valueOf(stationPropertice.getOutputPressure())));
            data.add(new Table("دبی عبوری از ایستگاه"  , cubicMeter, String.valueOf(stationPropertice.getDebi())));
            data.add(new Table("نیتروژن", percent, String.valueOf(stationPropertice.getComponent()[0] * 100)));
            data.add(new Table("دی اکسید کربن", percent, String.valueOf(stationPropertice.getComponent()[1] * 100)));
            data.add(new Table("متان", percent, String.valueOf(stationPropertice.getComponent()[2] * 100)));
            data.add(new Table("اتان", percent, String.valueOf(stationPropertice.getComponent()[3] * 100)));
            data.add(new Table("پروپان", percent, String.valueOf(stationPropertice.getComponent()[4] * 100)));
            data.add(new Table("نرمال بوتان", percent, String.valueOf(stationPropertice.getComponent()[5] * 100)));
            data.add(new Table("ایزو بوتان", percent, String.valueOf(stationPropertice.getComponent()[6] * 100)));
            data.add(new Table("نرمال پنتان", percent, String.valueOf(stationPropertice.getComponent()[7] * 100)));
            data.add(new Table("ایزو پنتان", percent, String.valueOf(stationPropertice.getComponent()[8] * 100)));
            data.add(new Table("هگزان", percent, String.valueOf(stationPropertice.getComponent()[9] * 100)));
            data.add(new Table("هپتان", percent, String.valueOf(stationPropertice.getComponent()[10] * 100)));
            data.add(new Table("اکتان", percent, String.valueOf(stationPropertice.getComponent()[11] * 100)));
            data.add(new Table("نونان", percent, String.valueOf(stationPropertice.getComponent()[12] * 100)));
            data.add(new Table("دکان", percent, String.valueOf(stationPropertice.getComponent()[13] * 100)));
            data.add(new Table("هیدروژن", percent, String.valueOf(stationPropertice.getComponent()[14] * 100)));
            data.add(new Table("اکسیژن", percent, String.valueOf(stationPropertice.getComponent()[15] * 100)));
            data.add(new Table("کربن منواکسید", percent, String.valueOf(stationPropertice.getComponent()[16] * 100)));
            data.add(new Table("آب", percent, String.valueOf(stationPropertice.getComponent()[17] * 100)));
            data.add(new Table("هیدروژن سولفید", percent, String.valueOf(stationPropertice.getComponent()[18] * 100)));
            data.add(new Table("هلیوم", percent, String.valueOf(stationPropertice.getComponent()[19] * 100)));
            data.add(new Table("آرگون", percent, String.valueOf(stationPropertice.getComponent()[20] * 100)));
        }
        if (stationLogic != null) {
            if (stationLogic.getBeforeHeater() != null) {


//                    BasePipe beforeHeater = stationLogic.getBeforeHeater();
//                    data.add(new Table("خط لوله قبل از گرم کن", ""));
//
//                    data.add(new Table("دمای گاز ورودی", String.valueOf(beforeHeater.getTin() - 273.15)));
//                    data.add(new Table("فشار گاز ورودی", String.valueOf(beforeHeater.getPin())));
//                    data.add(new Table("دمای گاز خروجی", String.valueOf(beforeHeater.getTout() - 273.15)));
//                    data.add(new Table("فشار گاز خروجی", String.valueOf(beforeHeater.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(beforeHeater.getTin() - 273.15 - beforeHeater.getTout() - 273.15)));
//                    data.add(new Table("تلفات حرارتی", String.valueOf(beforeHeater.getConsumption())));
                if(Station.getInstance().getList().get("beforeHeaterPipeLine") !=null) {
                    PipeLine beforeHeaterSampleModel = (PipeLine) Station.getInstance().getList().get("beforeHeaterPipeLine");
                    data.add(new Table("خط لوله قبل از گرم کن", "", ""));

                    data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(beforeHeaterSampleModel.getTin() - 273.15)));
                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(beforeHeaterSampleModel.getPin())));
                    data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(beforeHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(beforeHeaterSampleModel.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(beforeHeaterSampleModel.getTin() - 273.15 - beforeHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("تلفات حرارتی با عایق حرارتی" , cubicMeter,  String.valueOf(-1 * beforeHeaterSampleModel.getWithInsulationConsumption())));
                    data.add(new Table("تلفات حرارتی بدون عایق حرارتی" , cubicMeter,  String.valueOf(-1 * beforeHeaterSampleModel.getNotIsulationConsumption())));



                }

            }
        }

        if (stationLogic != null) {
            if (stationLogic.getHeaters() != null) {

                Heaters heaters = stationLogic.getHeaters();
                List<Heater> heater = heaters.getHeaters();
                data.add(new Table("اطلاعات گرم کن ", "", ""));
                int temp = 1;
                for (Heater h : heater) {

                    data.add(new Table("گرم کن " + temp, "", ""));
                    data.add(new Table("بازده جذب گرمایی کویل‌ها " , percent, String.valueOf(h.getEfficiency() * 100)));

                    List<Burner> burners = h.getBurners();
                    int temp2 = 1;
                    for (Burner b : burners) {
                        data.add(new Table("دمای دودکش مشعل " + temp2 , CELCIUS, String.valueOf(b.getTstack())));
                        data.add(new Table("درصد اکسیژن مشعل " + temp2 + percent, percent, String.valueOf(b.getOxygen())));
                        data.add(new Table("بازده ناخالص (gross) مشعل " + temp2, percent, String.valueOf(b.getEfficiency() * 100)));
                        double tem = b.getConsumption();
                        data.add(new Table("مصرف مشعل " + temp2 , cubicMeter, String.valueOf(b.getConsumption())));

                        temp2++;
                    }
                    temp++;
                }
                data.add(new Table("مصرف گرمکن‌ها" , cubicMeter, String.valueOf(heaters.getConsumption())));
            }
        }

        if (stationLogic != null) {
            if (stationLogic.getAfterHeater() != null) {

//                BasePipe afterHeater = stationLogic.getAfterHeater();
//                data.add(new Table("خط لوله بعد از گرم کن", ""));
//                data.add(new Table("دمای گاز ورودی", String.valueOf(afterHeater.getTin() - 273.15)));
//
//                data.add(new Table("فشار گاز ورودی", String.valueOf(afterHeater.getPin())));
//                data.add(new Table("دمای گاز خروجی", String.valueOf(afterHeater.getTout() - 273.15)));
//                data.add(new Table("فشار گاز خروجی", String.valueOf(afterHeater.getPout())));
//                data.add(new Table("اختلاف دما", String.valueOf(afterHeater.getTin() - 273.15 - afterHeater.getTout() - 273.15)));
//                data.add(new Table("تلفات حرارتی", String.valueOf(afterHeater.getConsumption())));

                if(Station.getInstance().getList().get("afterHeaterPipeLine") !=null) {
                    PipeLine afterHeaterSampleModel = (PipeLine) Station.getInstance().getList().get("afterHeaterPipeLine");
                    data.add(new Table("خط لوله بعد از گرم کن", "", ""));

                    data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(afterHeaterSampleModel.getTin() - 273.15)));
                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(afterHeaterSampleModel.getPin())));
                    data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(afterHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(afterHeaterSampleModel.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(afterHeaterSampleModel.getTin() - 273.15 - afterHeaterSampleModel.getTout() - 273.15)));
                    data.add(new Table("تلفات حرارتی با عایق حرارتی" , cubicMeter,  String.valueOf(-1 * afterHeaterSampleModel.getWithInsulationConsumption())));
                    data.add(new Table("تلفات حرارتی بدون عایق حرارتی" , cubicMeter,  String.valueOf(-1 * afterHeaterSampleModel.getNotIsulationConsumption())));



                }


            }
        }

        if (stationLogic != null) {
            if (stationLogic.getCollector() != null) {

                BasePipe collector = stationLogic.getCollector();
                data.add(new Table("اطلاعات کلکتور", "", ""));
                data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(collector.getTin() - 273.15)));
                data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(collector.getPin())));
                data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(collector.getTout() - 273.15)));
                data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(collector.getPout())));
//                data.add(new Table("اختلاف دما", String.valueOf(collector.getTin() - 273.15 - collector.getTout() - 273.15)));
                data.add(new Table("تلفات حرارتی" , cubicMeter, String.valueOf(collector.getConsumption())));
            }
        }
        if (stationLogic != null) {
            if (stationLogic.getRuns() != null) {
                data.add(new Table("اطلاعات ران ها", "", ""));

                ir.behinehsazan.gasStation.model.run.Runs runs = stationLogic.getRuns();
                List<BaseRun> run = runs.getRuns();
                int i = 1;
                for (BaseRun r : run) {
                    data.add(new Table("ران " + i, "",""));


                    data.add(new Table("دمای گاز ورودی " , CELCIUS, String.valueOf(r.getTin() - 273.15)));

                    data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(r.getPin())));
                    data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(r.getTout() - 273.15)));
                    data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(r.getPout())));
//                    data.add(new Table("اختلاف دما", String.valueOf(r.getTin() - 273.15 - r.getTout() - 273.15)));
                    data.add(new Table("تلفات حرارتی" , cubicMeter,  String.valueOf(-1 * r.getConsumption())));
                    i++;
                }
            }
        }

        if (stationLogic != null) {
            if (stationLogic.getRegulator() != null) {
                Regulator regulator = stationLogic.getRegulator();
                data.add(new Table("رگولاتور", "", ""));
                data.add(new Table("دمای گاز ورودی" , CELCIUS, String.valueOf(regulator.getTin() - 273.15)));
                data.add(new Table("فشار گاز ورودی" , PSI, String.valueOf(regulator.getPin())));
                data.add(new Table("دمای گاز خروجی" , CELCIUS, String.valueOf(regulator.getTout() - 273.15)));
                data.add(new Table("فشار گاز خروجی" , PSI, String.valueOf(regulator.getPout())));
            }
        }


    }

    @Override
    public void setOnShow() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
