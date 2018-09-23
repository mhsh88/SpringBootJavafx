package com.codetreatise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import ir.behinehsazan.gasStation.model.burner.Burner;
import ir.behinehsazan.gasStation.model.gas.Gas;
import ir.behinehsazan.gasStation.model.heater.Heater;
import ir.behinehsazan.gasStation.model.heater.Heaters;
import ir.behinehsazan.gasStation.model.pipeLine.base.BasePipe;
import ir.behinehsazan.gasStation.model.regulator.Regulator;
import ir.behinehsazan.gasStation.model.run.base.BaseRun;
import ir.behinehsazan.gasStation.model.station.StationLogic;
import sample.model.Station;
import sample.model.heaters.HeatersModel;
import sample.model.pipeLine.PipeLine;
import sample.model.run.Runs;
import sample.model.stationProperties.StationPropertice;

import java.io.IOException;
import java.util.List;

public class TemoCalculateController{
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
    ObjectMapper mapper = new ObjectMapper();

    ArrayNode arrayNode = mapper.createArrayNode();
    ObjectNode stationItems = mapper.createObjectNode();



    public final static String cubicMeter = " متر مکعب بر ساعت ";
    public final static String windSpeed = " m/s ";
    double Th;
    public ObjectNode calculate() throws IOException {
        boolean state;

        Station station = Station.getInstance();
        Runs runs = (Runs) station.getList().get("Runs");
        StationPropertice stationPropertice = (StationPropertice) station.getList().get("stationPropertice");
        HeatersModel heatersModel = (HeatersModel) station.getList().get("HeatersModel");
        PipeLine afterHeaterPipeLine = (PipeLine) station.getList().get("afterHeaterPipeLine");
        PipeLine beforeHeaterPipeLine = (PipeLine) station.getList().get("beforeHeaterPipeLine");
        if(stationPropertice == null){
            throw new NullPointerException("اطلاعات ایستگاه تکمیل نشده است");
        }
        else{

            if(runs != null){
                double tempDebi = 0;
                for(int i = 0 ; i < runs.getRuns().size(); i++){
                    tempDebi += runs.getRuns().get(i).getDebi();

                }
                if(stationPropertice.getDebi() < tempDebi){
                    stationPropertice.setDebi(tempDebi);
                }

            }



            StationLogic stationLogic = new StationLogic();
            Gas gas = new Gas();
            Double[] component = stationPropertice.getComponent();
            gas.setComponent(component);
            stationLogic.setGas(gas);
            stationLogic.setTenv(stationPropertice.getEnvironmentTemp());
            stationLogic.setVair(stationPropertice.getWindVelocity());
            stationLogic.setDebi(stationPropertice.getDebi());
            stationLogic.setTin(stationPropertice.getInputTemp());
            stationLogic.setPin(stationPropertice.getInputPressure());
            stationLogic.setTout(stationPropertice.getOutputTemp());
            stationLogic.setPout(stationPropertice.getOutputPressure());


            station.getList().put("beforeHeaterPipeLine", stationLogic.setBeforeHeater(beforeHeaterPipeLine));



            stationLogic.setRegulator();


            stationLogic.setRuns(runs);

            stationLogic.setCollector(runs);




            station.getList().put("afterHeaterPipeLine", stationLogic.setAfterHeater(afterHeaterPipeLine));


            stationLogic.setHeaters(heatersModel);
//
            station.setStationLogic(stationLogic);




            ObjectNode userInput = mapper.createObjectNode();
            if (stationLogic.getBeforeHeater() != null && station.getList().get("beforeHeaterPipeLine")!=null) {


//
                ObjectNode beforeHeater = mapper.createObjectNode();
                PipeLine beforeheaterpipeline = (PipeLine) station.getList().get("beforeHeaterPipeLine");
                ObjectNode beforeHeaterInput = mapper.createObjectNode();
                beforeHeaterInput.put("T", beforeheaterpipeline.getTin() - 273.15);
                beforeHeaterInput.put("P", beforeheaterpipeline.getPin() - 101.235);
                gas.calculate(beforeheaterpipeline.getPin(), beforeheaterpipeline.getTin());
                beforeHeaterInput.put("gas", removeGasProperty(gas));
                beforeHeater.put("input", beforeHeaterInput);

                ObjectNode beforeHeaterOutput = mapper.createObjectNode();
                beforeHeaterOutput.put("T", beforeheaterpipeline.getTout() - 273.15);
                beforeHeaterOutput.put("P", beforeheaterpipeline.getPout() - 101.235);
                gas.calculate(beforeheaterpipeline.getPout(), beforeheaterpipeline.getTout());
                beforeHeaterOutput.put("gas", removeGasProperty(gas));
                beforeHeater.put("output", beforeHeaterOutput);
                beforeHeater.put("insulationLoss", -1 * beforeheaterpipeline.getWithInsulationConsumption());
                beforeHeater.put("noInsulationLoss", -1 * beforeheaterpipeline.getWithInsulationConsumption());

                userInput.put("beforeHeater", beforeHeater);
            }
//
//
//
            if (stationLogic.getHeaters() != null) {

                Heaters heaters = stationLogic.getHeaters();
                List<Heater> heater = heaters.getHeaters();
                ObjectNode allHeaters = mapper.createObjectNode();
                ArrayNode jsonHeaters = mapper.createArrayNode();
                setHeaterJson(heater, jsonHeaters);

                allHeaters.put("heaters", jsonHeaters);
                allHeaters.put("consumption", heaters.getConsumption());

                ObjectNode heaterInput = mapper.createObjectNode();
                heaterInput.put("T", heaters.getTin() - 273.15);
                heaterInput.put("P", heaters.getPin() - 101.235);
                gas.calculate(heaters.getPin(),heaters.getTin() - 273.15);
                heaterInput.put("gas",removeGasProperty(gas));
                allHeaters.put("input",heaterInput);

                ObjectNode heaterOutput = mapper.createObjectNode();
                heaterOutput.put("T", heaters.getTout() - 273.15);
                heaterOutput.put("P", heaters.getPout() - 101.235);
                gas.calculate(heaters.getPout(),heaters.getTout());
                heaterOutput.put("gas",removeGasProperty(gas));
                allHeaters.put("output",heaterOutput);

                userInput.put("heater", allHeaters);

            }

            setAfterHeaterJson(afterHeaterPipeLine, stationLogic, gas, userInput);
//
//
            setCollectorJson(afterHeaterPipeLine, stationLogic, gas, userInput);
            setRunJson(afterHeaterPipeLine, stationLogic, gas, userInput);

            setRegulatorJson(stationLogic, gas, userInput);

            stationItems.put("userInputTemperature", userInput);


            // T_hydrate temperature




        }




//        saveUserTempInputResults();
        calculateHydrateResults();
        return stationItems;





    }

    private void setAfterHeaterJson(PipeLine afterHeaterPipeLine, StationLogic stationLogic, Gas gas, ObjectNode userInput) throws IOException {
        if(stationLogic.getAfterHeater() != null && afterHeaterPipeLine !=null && gas !=null) {
            ObjectNode afterHeater = mapper.createObjectNode();

            ObjectNode afterHeaterInput = mapper.createObjectNode();
            afterHeaterInput.put("T", afterHeaterPipeLine.getTin() - 273.15);
            afterHeaterInput.put("P", afterHeaterPipeLine.getPin() - 101.235);
            gas.calculate(afterHeaterPipeLine.getPin(), afterHeaterPipeLine.getTin());
            afterHeaterInput.put("gas", removeGasProperty(gas));
            afterHeater.put("input", afterHeaterInput);

            ObjectNode afterHeaterOutput = mapper.createObjectNode();
            afterHeaterOutput.put("T", afterHeaterPipeLine.getTout() - 273.15);
            afterHeaterOutput.put("P", afterHeaterPipeLine.getPout() - 101.235);
            gas.calculate(afterHeaterPipeLine.getPout(), afterHeaterPipeLine.getTout());
            afterHeaterOutput.put("gas", removeGasProperty(gas));
            afterHeater.put("output", afterHeaterOutput);
            afterHeater.put("insulationLoss", -1 * afterHeaterPipeLine.getWithInsulationConsumption());
            afterHeater.put("noInsulationLoss", -1 * afterHeaterPipeLine.getWithInsulationConsumption());

            userInput.put("afterHeater", afterHeater);
        }
    }

    private void setCollectorJson(PipeLine afterHeaterPipeLine, StationLogic stationLogic, Gas gas, ObjectNode userInput) throws IOException {
        if (stationLogic != null) {
            if (stationLogic.getCollector() != null && afterHeaterPipeLine !=null && gas !=null) {

                BasePipe collector = stationLogic.getCollector();
                ObjectNode jsonCollector = mapper.createObjectNode();

                ObjectNode collectorInput = mapper.createObjectNode();
                collectorInput.put("T", collector.getTin() - 273.15);
                collectorInput.put("P", collector.getPin() - 101.235);
                gas.calculate(collector.getPin(),collector.getTin());
                collectorInput.put("gas",removeGasProperty(gas));
                jsonCollector.put("input",collectorInput);

                ObjectNode collectorOutput = mapper.createObjectNode();
                collectorOutput.put("T", afterHeaterPipeLine.getTout() - 273.15);
                collectorOutput.put("P", afterHeaterPipeLine.getPout() - 101.235);
                gas.calculate(afterHeaterPipeLine.getPout(),afterHeaterPipeLine.getTout());
                collectorOutput.put("gas",removeGasProperty(gas));
                jsonCollector.put("output",collectorOutput);

                jsonCollector.put("loss", -1 * collector.getConsumption());

                userInput.put("collector", jsonCollector);

            }
        }
    }

    private void setRunJson(PipeLine afterHeaterPipeLine, StationLogic stationLogic, Gas gas, ObjectNode userInput) throws IOException {
        if (stationLogic != null) {
            if (stationLogic.getRuns() != null && afterHeaterPipeLine!=null && gas !=null) {

                ir.behinehsazan.gasStation.model.run.Runs logicRuns = stationLogic.getRuns();
                List<BaseRun> run = logicRuns.getRuns();
                ArrayNode runArray = mapper.createArrayNode();
                int i = 1;
                for (BaseRun r : run) {
                    ObjectNode jsonRun = mapper.createObjectNode();

                    ObjectNode runInput = mapper.createObjectNode();
                    runInput.put("T", r.getTin() - 273.15);
                    runInput.put("P", r.getPin() - 101.235);
                    gas.calculate(r.getPin(),r.getTin());
                    runInput.put("gas",removeGasProperty(gas));
                    jsonRun.put("input",runInput);

                    ObjectNode runOutput = mapper.createObjectNode();
                    runOutput.put("T", afterHeaterPipeLine.getTout() - 273.15);
                    runOutput.put("P", afterHeaterPipeLine.getPout() - 101.235);
                    gas.calculate(afterHeaterPipeLine.getPout(),afterHeaterPipeLine.getTout());
                    runOutput.put("gas",removeGasProperty(gas));
                    jsonRun.put("output",runOutput);

                    jsonRun.put("loss", -1 * r.getConsumption());
                    runArray.add(jsonRun);
                }
                userInput.put("runs", runArray);

            }
        }
    }

    private void setRegulatorJson(StationLogic stationLogic, Gas gas, ObjectNode userInput) throws IOException {
        if (stationLogic != null) {
            if (stationLogic.getRegulator() != null) {
                Regulator regulator = stationLogic.getRegulator();
                ObjectNode jsonRegulator = mapper.createObjectNode();

                ObjectNode regulatorInput = mapper.createObjectNode();
                regulatorInput.put("T", regulator.getTin() - 273.15);
                regulatorInput.put("P", regulator.getPin() - 101.235);
                gas.calculate(regulator.getPin(),regulator.getTin());
                regulatorInput.put("gas",removeGasProperty(gas));
                jsonRegulator.put("input",regulatorInput);

                ObjectNode regulatorOutput = mapper.createObjectNode();
                regulatorOutput.put("T", regulator.getTout() - 273.15);
                regulatorOutput.put("P", regulator.getPout() - 101.235);
                gas.calculate(regulator.getPout(),regulator.getTout());
                regulatorOutput.put("gas",removeGasProperty(gas));
                jsonRegulator.put("output",regulatorOutput);
                userInput.put("regulator", jsonRegulator);
             }
        }
    }

    private void setHeaterJson(List<Heater> heater, ArrayNode jsonHeaters) {
        for (Heater h : heater) {
            ObjectNode jsonHeater = mapper.createObjectNode();
            jsonHeater.put("efficiency", h.getEfficiency() * 100);

            List<Burner> burners = h.getBurners();
            ArrayNode jsonBurners = mapper.createArrayNode();
            int temp2 = 1;
            for (Burner b : burners) {
                ObjectNode jsonBurner = mapper.createObjectNode();
                jsonBurner.put("flueGasTemperature", b.getTstack());
                jsonBurner.put("oxygenPercent", b.getOxygen());
                jsonBurner.put("efficiency", b.getEfficiency());
                jsonBurner.put("consumption", b.getConsumption());
                jsonBurners.add(jsonBurner);
            }

            jsonHeater.put("burners", jsonBurners);
            jsonHeaters.add(jsonHeater);
        }
    }

    private void calculateHydrateResults() throws IOException {

        Station station = Station.getInstance();
        Runs runs = (Runs) station.getList().get("Runs");
        StationPropertice stationPropertice = (StationPropertice) station.getList().get("stationPropertice");
        HeatersModel heatersModel = (HeatersModel) station.getList().get("HeatersModel");
        PipeLine afterHeaterPipeLine = (PipeLine) station.getList().get("afterHeaterPipeLine");
        PipeLine beforeHeaterPipeLine = (PipeLine) station.getList().get("beforeHeaterPipeLine");
        if(stationPropertice == null){
            throw new NullPointerException("اطلاعات ایستگاه تکمیل نشده است");
//            showAlert("خطا","خطا در اطلاعات ورودی","اطلاعات ایستگاه تکمیل نشده است", Alert.AlertType.ERROR);
//            return false;
        }
        else{



            StationLogic stationLogic = new StationLogic();
            Gas gas = new Gas();
            Double[] component = stationPropertice.getComponent();
            gas.setComponent(component);
            gas.calculate(stationPropertice.getOutputPressure(), stationPropertice.getOutputTemp(),component);
            Th = gas.getT_h();
            stationLogic.setGas(gas);
            stationLogic.setTenv(stationPropertice.getEnvironmentTemp());
            stationLogic.setVair(stationPropertice.getWindVelocity());
            stationLogic.setDebi(stationPropertice.getDebi());
            stationLogic.setTin(stationPropertice.getInputTemp());
            stationLogic.setPin(stationPropertice.getInputPressure());
            if(gas.getT_h()>50){

                throw new IllegalArgumentException("دمای هیدرات برای شرایط زیر قابل محاسبه نیست");
//                ObservableList<Table> data = ShowResultsController.getHydrateData();
//                data.clear();
//                data.add(new Table("خطا", "","دمای هیدرات برای شرایط زیر قابل محاسبه نیست"));
//                return;
            }
            stationLogic.setTout(Th + 273.15 + 2);
            stationLogic.setPout(stationPropertice.getOutputPressure());

            station.getList().put("beforeHeaterPipeLine", stationLogic.setBeforeHeater(beforeHeaterPipeLine));

            stationLogic.setRegulator();


            stationLogic.setRuns(runs);

            stationLogic.setCollector(runs);




            station.getList().put("afterHeaterPipeLine", stationLogic.setAfterHeater(afterHeaterPipeLine));


            stationLogic.setHeaters(heatersModel);
//
            station.setStationLogic(stationLogic);

            ObjectNode userInput = mapper.createObjectNode();

            ObjectNode beforeHeater = mapper.createObjectNode();

            if(stationLogic.getBeforeHeater()!=null && beforeHeaterPipeLine != null) {
                ObjectNode beforeHeaterInput = mapper.createObjectNode();
                beforeHeaterInput.put("T", beforeHeaterPipeLine.getTin() - 273.15);
                beforeHeaterInput.put("P", beforeHeaterPipeLine.getPin() - 101.235);
                gas.calculate(beforeHeaterPipeLine.getPin(), beforeHeaterPipeLine.getTin());
                beforeHeaterInput.put("gas", removeGasProperty(gas));
                beforeHeater.put("input", beforeHeaterInput);

                ObjectNode beforeHeaterOutput = mapper.createObjectNode();
                beforeHeaterOutput.put("T", beforeHeaterPipeLine.getTout() - 273.15);
                beforeHeaterOutput.put("P", beforeHeaterPipeLine.getPout() - 101.235);
                gas.calculate(beforeHeaterPipeLine.getPout(), beforeHeaterPipeLine.getTout());
                beforeHeaterOutput.put("gas", removeGasProperty(gas));
                beforeHeater.put("output", beforeHeaterOutput);
                beforeHeater.put("insulationConsumption", -1 * beforeHeaterPipeLine.getWithInsulationConsumption());
                beforeHeater.put("noInsulationConsumption", -1 * beforeHeaterPipeLine.getWithInsulationConsumption());

                userInput.put("beforeHeater", beforeHeater);
            }


            if (stationLogic.getHeaters() != null) {

                Heaters heaters = stationLogic.getHeaters();
                List<Heater> heater = heaters.getHeaters();
                ObjectNode allHeaters = mapper.createObjectNode();
                ArrayNode jsonHeaters = mapper.createArrayNode();
                setHeaterJson(heater, jsonHeaters);

                allHeaters.put("heaters", jsonHeaters);
                allHeaters.put("consumption", heaters.getConsumption());

                ObjectNode heaterInput = mapper.createObjectNode();
                heaterInput.put("T", heaters.getTin() - 273.15);
                heaterInput.put("P", heaters.getPin() - 101.235);
                gas.calculate(heaters.getPin(),heaters.getTin());
                heaterInput.put("gas",removeGasProperty(gas));
                allHeaters.put("input",heaterInput);

                ObjectNode heaterOutput = mapper.createObjectNode();
                heaterOutput.put("T", heaters.getTout() - 273.15);
                heaterOutput.put("P", heaters.getPout() - 101.235);
                gas.calculate(heaters.getPout(),heaters.getTout());
                heaterOutput.put("gas",removeGasProperty(gas));
                allHeaters.put("output",heaterOutput);

                userInput.put("heater", allHeaters);

            }


            setAfterHeaterJson(afterHeaterPipeLine, stationLogic, gas, userInput);


            setCollectorJson(afterHeaterPipeLine, stationLogic, gas, userInput);
            setRunJson(afterHeaterPipeLine, stationLogic, gas, userInput);

            setRegulatorJson(stationLogic, gas, userInput);

            stationItems.put("hydrateTemperature", userInput);


            // T_hydrate temperature




        }

//        saveHydrateResults();
//        return state;

    }

    private ObjectNode removeGasProperty(Gas gas) throws IOException {
        TokenBuffer tb = new TokenBuffer(null, false);

        mapper.writeValue(tb, gas);
        Gas copyGas = mapper.readValue(tb.asParser(), Gas.class);
        copyGas.setComponent(gas.getComponent());
        ObjectNode gasNode = (ObjectNode) mapper.readTree(mapper.writeValueAsString(copyGas));
        gasNode.remove("component");
        gasNode.remove("t");
        gasNode.remove("p");
        return  gasNode;
    }
}
