package ir.behinehsazan.gasStation.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.behinehsazan.gasStation.model.base.GasConsumer;
import ir.behinehsazan.gasStation.model.heater.Heater;
import ir.behinehsazan.gasStation.model.heater.Heaters;
import ir.behinehsazan.gasStation.model.pipeLine.base.BasePipe;
import ir.behinehsazan.gasStation.model.regulator.Regulator;
import ir.behinehsazan.gasStation.model.run.Runs;
import ir.behinehsazan.gasStation.model.run.base.BaseRun;
import sample.model.heaters.HeatersModel;
import sample.model.pipeLine.PipeLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StationLogic extends GasConsumer {




    double stationConsumption;
    private BasePipe beforeHeater = new BasePipe();
    private Heaters heaters = new Heaters();
    private BasePipe afterHeater = new BasePipe();
    private BasePipe collector = new BasePipe();
    private Runs runs = new Runs();
    private Regulator regulator = new Regulator();
    private String message;
    private PipeLine calBeforeHeater;
    private PipeLine calAfterHeater;

    @JsonProperty("calAfterHeater")
    public PipeLine getCalAfterHeater() {
        return calAfterHeater;
    }

    public void setCalAfterHeater(PipeLine calAfterHeater) {
        this.calAfterHeater = calAfterHeater;
    }





    @JsonProperty("calBeforeHeater")
    public PipeLine getCalBeforeHeater() {
        return calBeforeHeater;
    }

    public void setCalBeforeHeater(PipeLine calBeforeHeater) {
        this.calBeforeHeater = calBeforeHeater;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ArrayList<GasConsumer> allComponent = new ArrayList<GasConsumer>();

    public void setStationConsumption(double stationConsumption) {
        this.stationConsumption = stationConsumption;
    }

    public double getStationConsumption(){
        stationConsumption = 0;

        return this.beforeHeater.getConsumption() +
                this.heaters.getConsumption() +
                this.afterHeater.getConsumption() +
                this.collector.getConsumption() +
                this.runs.getConsumption();
    }
    public BasePipe getBeforeHeater() {
        return beforeHeater;
    }


    public PipeLine setBeforeHeater(PipeLine beforeHeater) {
        if(beforeHeater != null && getTenv() != null)  {
//            double od = beforeHeater.getOD();
//            double id = beforeHeater.getID();
//            double thic = beforeHeater.getLineThickness();
//            double iff = beforeHeater.getInsulationFactor();
//            double it = beforeHeater.getInsulationThickness();
//            double l = beforeHeater.getLength();
//            double debi = getDebi();
//            double tin = getTin();
//            double pin = getPin();
            this.beforeHeater.setOuterDiameter(beforeHeater.getOD());
            this.beforeHeater.setInterDiameter(beforeHeater.getID());
            this.beforeHeater.setInsulationFactor(0.0);
            this.beforeHeater.setInsulationThickness(0.0);
            this.beforeHeater.setLength(beforeHeater.getLength());
            this.beforeHeater.setDebi(this.getDebi());
            this.beforeHeater.setTin(getTin());
            this.beforeHeater.setPin(this.getPin());
            this.beforeHeater.setInverse(false);
            this.beforeHeater.setGas(getGas());
            this.beforeHeater.calculate();
            this.beforeHeater.calConsumption();

            beforeHeater.setNotIsulationConsumption(this.beforeHeater.getConsumption());


            this.beforeHeater.setInsulationFactor(beforeHeater.getInsulationFactor());
            this.beforeHeater.setInsulationThickness(beforeHeater.getInsulationThickness());
            this.beforeHeater.calculate();
            this.beforeHeater.calConsumption();
            beforeHeater.setTin(this.beforeHeater.getTin());
            beforeHeater.setPin(this.beforeHeater.getPin());
            beforeHeater.setTout(this.beforeHeater.getTout());
            beforeHeater.setPout(this.beforeHeater.getPout());
            beforeHeater.setWithInsulationConsumption(this.beforeHeater.getConsumption());




        }
        else{
            this.beforeHeater.setTin(this.getTin());
            this.beforeHeater.setTout(this.beforeHeater.getTin());
            this.beforeHeater.setPin(this.getPin());
            this.beforeHeater.setPout(this.beforeHeater.getPin());
            this.beforeHeater.setConsumption(0.0);

            if(beforeHeater != null) {
                beforeHeater.setTin(this.beforeHeater.getTin());
                beforeHeater.setPin(this.beforeHeater.getPin());
                beforeHeater.setTout(this.beforeHeater.getTout());
                beforeHeater.setPout(this.beforeHeater.getPout());
                beforeHeater.setNotIsulationConsumption(this.beforeHeater.getConsumption());
                beforeHeater.setWithInsulationConsumption(this.beforeHeater.getConsumption());
            }


        }


        this.setCalBeforeHeater(beforeHeater);
        return beforeHeater;
    }

    public Heaters getHeaters() {
        return heaters;
    }

    public void setHeaters(HeatersModel heaters) {
        if(heaters != null && getTenv() != null){
            this.heaters.setTin(beforeHeater.getTout());
            this.heaters.setTout(afterHeater.getTin());
            this.heaters.setPin(beforeHeater.getPout());
            this.heaters.setPout(afterHeater.getPin());
            this.heaters.setDebi(getDebi());
            this.heaters.setGas(getGas());

            for(int i = 0; i < heaters.getHeaterModels().size(); i ++){
                Heater heater = new Heater();
                heater.setEfficiency(heaters.getHeaterModels().get(i).getEfficiency());

                for(int j = 0; j < heaters.getHeaterModels().get(i).getBurners().size(); j++) {
                    double flue = heaters.getHeaterModels().get(i).getBurners().get(j).getFlueGasTemp();
                    double oxygen = heaters.getHeaterModels().get(i).getBurners().get(j).getOxygenPecent();
                    heater.setBurners(new ir.behinehsazan.gasStation.model.burner.Burner(getGas(), oxygen,
                            getTenv() - 273.15, flue));
                }

                this.heaters.getHeaters().add(heater);
            }

            this.heaters.calculate();
            this.heaters.calConsumption();
            this.heaters.componentConsumptionCal();

        }
        else{
            this.heaters.setTin(beforeHeater.getTout());
            this.heaters.setTout(afterHeater.getTin());
            this.heaters.setPin(beforeHeater.getPout());
            this.heaters.setPout(afterHeater.getPin());
            if(getDebi() > 0){
                this.heaters.setDebi(getDebi());
                this.heaters.setGas(getGas());
                this.heaters.calConsumption();
            }
            else {
                this.heaters.setConsumption(0.0);
            }
        }
    }

    public BasePipe getAfterHeater() {
        return afterHeater;
    }

    public PipeLine setAfterHeater(PipeLine afterHeater) {

        if(afterHeater != null && getTenv() != null) {
            double od = afterHeater.getOD();
            double id = afterHeater.getID();
            double thic = afterHeater.getLineThickness();
            double iff = afterHeater.getInsulationFactor();
            double it = afterHeater.getInsulationThickness();
            double l = afterHeater.getLength();
            double debi = getDebi();
            double tin = getTin();
            double pin = getPin();
            this.afterHeater.setOuterDiameter(afterHeater.getOD());
            this.afterHeater.setInterDiameter(afterHeater.getID());
            this.afterHeater.setInsulationFactor(0.0);
            this.afterHeater.setInsulationThickness(0.0);
            this.afterHeater.setLength(afterHeater.getLength());
            this.afterHeater.setDebi(this.getDebi());
            this.afterHeater.setTout(this.collector.getTin());
            this.afterHeater.setPout(this.collector.getPin());
            this.afterHeater.setInverse(true);
            this.afterHeater.setGas(getGas());
            this.afterHeater.calculate();
            this.afterHeater.calConsumption();


            afterHeater.setNotIsulationConsumption(this.afterHeater.getConsumption());


            this.afterHeater.setInsulationFactor(afterHeater.getInsulationFactor());
            this.afterHeater.setInsulationThickness(afterHeater.getInsulationThickness());
            this.afterHeater.calculate();
            this.afterHeater.calConsumption();
            afterHeater.setTin(this.afterHeater.getTin());
            afterHeater.setPin(this.afterHeater.getPin());
            afterHeater.setTout(this.afterHeater.getTout());
            afterHeater.setPout(this.afterHeater.getPout());
            afterHeater.setWithInsulationConsumption(this.afterHeater.getConsumption());


        }
        else{
            this.afterHeater.setTout(this.collector.getTin());
            this.afterHeater.setTin(this.afterHeater.getTout());
            this.afterHeater.setPin(this.collector.getPin());
            this.afterHeater.setPout(this.afterHeater.getPin());
            this.afterHeater.setConsumption(0.0);
            if(afterHeater!= null) {

                afterHeater.setTin(this.afterHeater.getTin());
                afterHeater.setPin(this.afterHeater.getPin());
                afterHeater.setTout(this.afterHeater.getTout());
                afterHeater.setPout(this.afterHeater.getPout());
                afterHeater.setNotIsulationConsumption(this.afterHeater.getConsumption());
                afterHeater.setWithInsulationConsumption(this.afterHeater.getConsumption());
            }

        }
        setCalAfterHeater(afterHeater);
        return afterHeater;

    }

    public BasePipe getCollector() {
        return collector;
    }

    public void setCollector(sample.model.run.Runs runs) {
        if(runs != null && getTenv() != null && runs.getCollector() != null) {
            double temp = -273.15;
            double pi = 0;
            double debi = 0.0;
            for(BaseRun r : this.runs.getRuns()){
                if(r.getTin() > temp){
                    temp = r.getTin();
                }
                if(r.getPin() > pi){
                    pi = r.getPin();
                }
                debi += r.getDebi();
            }
            setDebi(debi);
            this.collector.setGas(getGas());
            this.collector.setOuterDiameter(runs.getCollector().getOD());
            this.collector.setInterDiameter(runs.getCollector().getID());
            this.collector.setInsulationFactor(runs.getCollector().getInsulationFactor());
            this.collector.setInsulationThickness(runs.getCollector().getInsulationThickness());
            this.collector.setLength(runs.getCollector().getLength());
            this.collector.setDebi(this.getDebi());
            this.collector.setTout(temp);
            this.collector.setPout(pi);
            this.collector.setInverse(true);
            this.collector.calculate();
            this.collector.calConsumption();

        }
        else{
            this.collector.setTout(this.runs.getTin());
            this.collector.setPout(this.runs.getPin());
            this.collector.setTin(this.collector.getTout());
            this.collector.setPin(this.collector.getPout());
            this.collector.setConsumption(0.0);

        }

    }

    public Runs getRuns() {
        return runs;
    }

    public void setRuns(sample.model.run.Runs runs) {
        if(runs != null && getTenv() != null && runs.getRuns().size()>0){
            this.runs.getRuns().clear();



            List<Double> tempList = new ArrayList<>();
            for(sample.model.run.Run r : runs.getRuns()) {
                BaseRun run = new BaseRun();
                run.setDebi(r.getDebi());
                run.setInterDiameter(r.getID());
                run.setOuterDiameter(r.getOD());
                run.setTout(getRegulator().getTin());
                run.setPout(getRegulator().getPin());
                run.setLength(r.getLength());
                run.setGas(getGas());
                run.setInverse(true);
                run.calculate();
                tempList.add(run.getTin());
                this.runs.getRuns().add(run);
            }
         for(BaseRun run : this.runs.getRuns()){
                run.setTin(tempList.get(tempList.indexOf(Collections.min(tempList))));
                run.calConsumption();
         }

        }
        else{
            this.runs.setTout(this.regulator.getTin());
            this.runs.setPout(this.regulator.getPin());
            this.runs.setTin(this.runs.getTout());
            this.runs.setPin(this.runs.getPout());
            this.runs.setConsumption(0.0);
        }
    }

    public Regulator getRegulator() {
        return this.regulator;
    }

    public void setRegulator() {
        double P1 = getPin();
        double p2 = getPout();
        double t1 = getTin();
        double t2 = getTout();
        this.regulator = new Regulator(getPin(),getTout(),getPout(),getGas(), true);

        this.regulator.calculate();


    }



    @Override
    public void calculate() {
        if(getBeforeHeater() != null){
            getBeforeHeater().calculate();

        }else

            for(GasConsumer gasConsumer : allComponent){
                gasConsumer.calculate();
            }







    }

}
