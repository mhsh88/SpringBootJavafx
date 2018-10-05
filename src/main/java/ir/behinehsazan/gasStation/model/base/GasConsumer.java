package ir.behinehsazan.gasStation.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.behinehsazan.gasStation.model.gas.Gas;

public abstract class GasConsumer extends EntityBase {
    double Tin;
    double Tout;
    double Pin;
    double Pout;
    @JsonIgnore
    boolean Inverse = false;
    Gas gas;

    @JsonProperty("gas")
    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public double getPin() {
        double P;
        if(Math.abs(Pin - 0.001) < 0.01){
            P  = Pout;}
        else{
            P =  Pin;}

            return P;
    }

    public void setPin(double pin) {
        Pin = pin;
    }

    public double getPout() {
        return Pout;
    }

    public void setPout(double pout) {
        Pout = pout;
    }

    public double getTin() {
        double T;
        if(Math.abs(Tin - 0.001) < 0.01){
            T  = Tout;}
        else{
            T =  Tin;}

        return T;
    }

    public void setTin(double tin) {
        Tin = tin;
    }

    public double getTout() {
        return Tout;
    }

    public void setTout(double tout) {
        Tout = tout;
    }
    @JsonIgnore
    public boolean isInverse() {
        return Inverse;
    }

    public void setInverse(boolean inverse) {
        Inverse = inverse;
    }

    @JsonIgnore
    public double getQdot(){
        Gas g = getGas();
        
        g.calculate(g.getP_theta(), g.getT_theta());

        double P2 = g.getP();
        double Z2 = g.getZ();
        double T2 = g.getT();
        if(getPin() > 0 && getTin() > 0) {
            g.calculate(getPin(), getTin());
        }else {
            g.calculate(getPout(), getTout());
        }
        double P1 = g.getP();
        double Z1 = g.getZ();
        double T1 = g.getT();

        double Qdot = (getDebi() / 3600) * (P2 * Z1 * T1) / (P1 * Z2 * T2);
        return Qdot;

    }
    public void calConsumption(){
        Gas g = getGas();
        double heatConsumption;
        double Dstd;
        g.calculate(g.getP_theta(), g.getT_theta());
        Dstd = g.getD();
        g.calculate((getPin() + getPout()) / 2.0, (getTin() + getTout())/2.0 );
        double D = g.getD();
        g.calculate(getPin() , getTin());
        double H1 = g.getH();
        g.calculate(getPout(), getTout());
        double H2 = g.getH();

        heatConsumption = getQdot() * D * (H2 - H1); // kJ/s
        // change kJ/s to kJ/hr
        heatConsumption = heatConsumption * 3600;
        // change kJ/hr to kg/hr
        heatConsumption = heatConsumption / g.getHhvd();
        //kg/hr to Standard m^3/hr
        heatConsumption = heatConsumption / Dstd;

        this.consumption = heatConsumption;

    }
}
