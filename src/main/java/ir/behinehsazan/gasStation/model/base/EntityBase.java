package ir.behinehsazan.gasStation.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class EntityBase implements Base{

    protected static Double Tenv;
    protected static Double Vair;
    protected double consumption;
    @JsonIgnore
    protected double efficiency;
    @JsonIgnore
    protected double debi;


    public static Double getVair() {
        return Vair;
    }

    public static void setVair(Double vair) {
        Vair = vair;
    }

    public static Double getTenv() {
        return Tenv;
    }

    public static void setTenv(Double tenv) {
        Tenv = tenv;
    }




    @JsonProperty("debi")
    public double getDebi() {
        return debi;
    }

    public void setDebi(double debi) {
        this.debi = debi;
    }



    @JsonProperty("efficiency")
    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }


    public double getConsumption() {
        return consumption / getEfficiency();
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
