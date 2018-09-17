package sample.model.burner;

import sample.model.base.BaseModel;

public class Burner extends BaseModel{
    double oxygenPecent;
    double flueGasTemp;

    public Burner(double oxygenPecent, double flueGasTemp) {

        this.oxygenPecent = oxygenPecent;
        this.flueGasTemp = flueGasTemp;
    }


    public double getOxygenPecent() {
        return oxygenPecent;
    }

    public void setOxygenPecent(double oxygenPecent) {
        this.oxygenPecent = oxygenPecent;
    }

    public double getFlueGasTemp() {
        return flueGasTemp;
    }

    public void setFlueGasTemp(double flueGasTemp) {
        this.flueGasTemp = flueGasTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Burner burner = (Burner) o;

        if (Double.compare(burner.oxygenPecent, oxygenPecent) != 0) return false;
        return Double.compare(burner.flueGasTemp, flueGasTemp) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(oxygenPecent);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(flueGasTemp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Burner{" +
                "Oxygen Pecent=" + oxygenPecent +
                ", FlueGas Temp=" + flueGasTemp +
                '}';
    }
}
