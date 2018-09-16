package ir.behinehsazan.gasStation.model.run;

import ir.behinehsazan.gasStation.model.base.GasConsumer;
import ir.behinehsazan.gasStation.model.run.base.BaseRun;

import java.util.ArrayList;

public class Runs extends GasConsumer{
    ArrayList<BaseRun> runs = new ArrayList<BaseRun>();

    public double getConsumption(){
        Double consumption = 0.0;
        for(BaseRun run: runs){
             consumption += run.getConsumption();
        }
        return consumption;
    }

    public ArrayList<BaseRun> getRuns() {
        return runs;
    }

    @Override
    public void calculate() {
    for(BaseRun run : runs){
        run.calculate();
    }
    }
}
