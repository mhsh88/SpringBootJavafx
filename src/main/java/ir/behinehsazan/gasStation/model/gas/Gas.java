package ir.behinehsazan.gasStation.model.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import static ir.behinehsazan.gasStation.model.mathCalculation.MathCalculation.*;

public class Gas extends BaseGas {
    final double[] dencity ={1.2504,1.977,0.7175,1.355,2.011,2.7083,2.5326,2.975,2.975,0,0,0,0,0,0.0845,1.429,1.165,0.5040,1.434,0.1664,1.661};
    final double[] HHV = {0,0,55499,51876,50346,49500,49500,48776,48776,0,0,0,0,0,141790,0,10160.4048,0,0,0,0};
    private double hhvd;
    @JsonProperty
    public double getD() {
        return d;
    }
    @JsonProperty
    public Double getM() {
        return m;
    }
    @JsonProperty
    public Double getU() {
        return u;
    }
    @JsonProperty
    public Double getH(){return h;}
    @JsonProperty
    public double getC_p() {
        return c_p;
    }
    @JsonProperty
    public double getC_v() {
        return c_v;
    }

    @JsonProperty
    public double getKappa(){
        return kappa;
    }
    @JsonProperty
    public double getW(){
        return w;
    }
    @JsonProperty
    public double getS(){
        return s;
    }


    @JsonProperty
    public Double getMu() {
        return mu;
    }

    public void setMu(Double mu) {
        this.mu = mu;
    }


    @JsonProperty
    public double getHhvd(){
        Double[] massFraction = matrixDevide(multiply(this.getComponent(), dencity) , dotProduct(this.getComponent(), dencity));
        double heatCapacity = dotProduct( massFraction, HHV);
        calculate(this.getP_theta(), this.getT_theta());
        double HHVd = heatCapacity * this.getD();
        return HHVd;
    }

    public void setHhvd(Double hhvd) {
        this.hhvd = hhvd;
    }


}
