package ir.behinehsazan.gasStation.model.base;

public abstract class EntityBase implements Base{

    protected static Double Tenv;
    protected static Double Vair;
    protected double consumption;
    protected double efficiency;
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




    public double getDebi() {
        return debi;
    }

    public void setDebi(double debi) {
        this.debi = debi;
    }



    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }


    public double getConsumption() {
        return consumption / getEfficiency();
    }

    public void setConsumption(double constumption) {
        this.consumption = constumption ;
    }



}
