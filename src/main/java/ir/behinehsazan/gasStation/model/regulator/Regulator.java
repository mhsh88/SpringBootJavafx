package ir.behinehsazan.gasStation.model.regulator;

import ir.behinehsazan.gasStation.model.gas.Gas;
import ir.behinehsazan.gasStation.model.regulator.base.BaseRegulator;

public class Regulator extends BaseRegulator {
    public Regulator(){
    }
    public Regulator(double pin, double tout, double pout, Gas gas, boolean b) {
        super(pin, tout, pout,gas,  b);
    }
}
