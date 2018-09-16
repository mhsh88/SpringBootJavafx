package ir.behinehsazan.gasStation.model.burner.base;

import ir.behinehsazan.gasStation.model.base.EntityBase;
import ir.behinehsazan.gasStation.model.gas.Gas;
import javafx.scene.control.Alert;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import sample.controller.base.BaseController;

import static ir.behinehsazan.gasStation.model.mathCalculation.MathCalculation.*;

public class BaseBurner extends EntityBase {
    private Gas gas;

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
    }

    public double getTstack() {
        return Tstack;
    }

    public void setTstack(double tstack) {
        Tstack = tstack;
    }

    public double getTamb() {
        return Tamb + 273.15;
    }

    public void setTamb(double tamb) {
        Tamb = tamb - 273.15;
    }

    private double oxygen;
    private double Tstack;
    private double Tamb;

    final double[] O2factor = {0, 0, 2, 3.5, 5, 6.5, 6.5, 8, 8, 9.5, 11, 12.5, 14, 15.5, 1, 0, 0.5, 0, 0, 0, 0};
    final double[] H2Ofactor = {0, 0, 2, 3, 4, 5, 5, 6, 6, 7, 8, 9, 10, 11, 2, 0, 0, 1, 0, 0, 0};
    final double[] CO2factor = {0, 1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10, 0, 0, 1, 0, 0, 0, 0};
    final double[] N2factor = {1.0, 0.0, 7.52, 13.16, 18.8, 24.44, 24.44, 30.08, 30.08, 35.72, 41.36, 47.0, 52.64, 58.28, 3.76, 0.0, 1.88, 0.0, 0.0, 0.0, 0.0};
    final double[] dencity ={1.2504,1.977,0.7175,1.355,2.011,2.7083,2.5326,2.975,2.975,0,0,0,0,0,0.0845,1.429,1.165,0.5040,1.434,0.1664,1.661};
    final double[] HHV = {0,0,55499,51876,50346,49500,49500,48776,48776,0,0,0,0,0,141790,0,10160.4048,0,0,0,0};
    final double[] temp ={0,100,200,300,400,500,600,700,800,900,1000};
    final UnivariateInterpolator interpolator = new SplineInterpolator();
    final UnivariateFunction Cp_Co2 = interpolator.interpolate(temp, new double[]{1.620, 1.725, 1.808, 1.884, 1.951, 2.009, 2.064, 2.110, 2.156, 2.189, 2.227});
    final UnivariateFunction Cp_O2 = interpolator.interpolate(temp, new double[]{1.306,1.319,1.340,1.360,1.381,1.398,1.419,1.436,1.453,1.469,1.482});
    final UnivariateFunction Cp_N2 = interpolator.interpolate(temp, new double[]{1.302,1.302,1.306,1.310,1.323,1.335,1.344,1.360,1.377,1.386,1.398});
    final UnivariateFunction Cp_H2O = interpolator.interpolate(temp, new double[]{1.427,1.440,1.457,1.473,1.494,1.520,1.545,1.570,1.595,1.620,1.645});
    final double[] tempforH2O ={100,120,160,200,240,280,320,360,400,440,500};
    final UnivariateFunction ro_H2O = interpolator.interpolate(tempforH2O, new double[]{0.590,0.558,0.504,0.460,0.424,0.393,0.366,0.343,0.322,0.304,0.281});
    final double[] HHVmole ={0,0,889,1560,2220,2877,2877,3507,3507,4163,4817,5470,6125,6778,286,0,0,0,0,0,0};

    public BaseBurner(){
    }

    public BaseBurner(Gas gas, double oxygen,  double tamb, double tstack) {
        super();
        this.gas = gas;
        this.oxygen = oxygen;
        this.Tstack = tstack;
        this.Tamb = tamb ;
//        setEfficiency(0.8);
        calculate();
    }

    @Override
    public void calculate() {
        double Tstack_temp;
        double Tamp_temp;
        Gas g = getGas();
        double O2percent = getOxygen();
        double Tamb = getTamb() - 273.15;
        double Tstack = getTstack();
        if (Tstack < Tamb) Tstack = Tamb;
        if (Tamb <= 100)
        {
            Tamp_temp = 100;
        }
        else if(Tamb >= 500)
        {
            Tamp_temp = 500;
        }
        else{
            Tamp_temp = Tamb;
        }

        if (Tstack <= 0){
            Tstack_temp = 0;
        }
        else if( Tstack >= 1000){
            Tstack_temp = 1000;
        }
        else{
            Tstack_temp = Tstack;
        }

        g.calculate(101.325, 273.15 + 15);
        double A0 = 1 / 0.21 * dotProduct(g.getComponent(), O2factor);
        double G0prime = dotProduct(g.getComponent(), CO2factor) + 3.76 * dotProduct(g.getComponent(), O2factor);
        double Gwf = dotProduct(g.getComponent(), H2Ofactor);

        double G0 = G0prime + Gwf;

        double CO2max = 100 / G0prime * dotProduct(g.getComponent(), CO2factor);

        double landa = 1 + ((G0prime / A0) * (O2percent / (21 - O2percent)));
        double dry_mass = dotProduct(g.getComponent(), CO2factor ) * 44.01 + dotProduct( g.getComponent() , O2factor) * 32 * (landa - 1) + dotProduct(g.getComponent(), N2factor) * landa * 28.0134;
        double dry_mass_fraction = dry_mass / g.getM();
        double CO2 = CO2max * ((21 - O2percent) / 21);
//        # O2 = O2percent  # TODO check
        double O2 = 21 - (21 / landa);
        double N2 = 100 - (O2 + CO2);
        double dry_gas_dencity = dotProduct(dencity, new double[]{N2, CO2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, O2, 0, 0, 0, 0, 0}) / 100;

        double CP_Stack = (Cp_Co2.value(Tstack_temp) * CO2 / 100 + Cp_O2.value(Tstack_temp) * O2 / 100 + Cp_N2.value(Tstack_temp) * N2 / 100) / dry_gas_dencity;
        double CP_amb = (Cp_Co2.value(Tamp_temp) * CO2 / 100 + Cp_O2.value(Tamp_temp) * O2 / 100 + Cp_N2.value(Tamp_temp) * N2 / 100) / dry_gas_dencity;
        double latent_heat;
        if (Tstack < 60.09)
            latent_heat = 0;
        else
            latent_heat = 2358.40;

        Double[] massFraction = matrixDevide(multiply(g.getComponent(), dencity) , dotProduct(g.getComponent(), dencity));
        double mH2O = dotProduct(g.getComponent(), H2Ofactor ) * 18.02;
        double mH20_fraction = mH2O / g.getM();
        // muH2OtoFuel = mH2O / g.getM();
        double heatCapacity = dotProduct( massFraction, HHV);
        double HHVd = heatCapacity * g.getD();

        double ro_H2O_amb = ro_H2O.value(Tamp_temp);

        double CP_H2O_amb = Cp_H2O.value(Tamp_temp) / ro_H2O_amb;
        double ro_H2O_stack;
        if (Tstack_temp <= 100){
            ro_H2O_stack =  ro_H2O.value(100);}
        else if(Tstack_temp >= 500){
            ro_H2O_stack = ro_H2O.value(500);}
        else{
            ro_H2O_stack = ro_H2O.value(Tstack_temp);}

        double CP_H2O_stack = Cp_H2O.value(Tstack_temp) / ro_H2O_stack;




        double loss = (dry_mass_fraction * (CP_Stack * (Tstack + 273.15) - CP_amb * (Tamb + 273.15)) + mH20_fraction * (
                CP_H2O_stack * (Tstack + 273.15) - CP_H2O_amb * (Tamb + 273.15)) + mH20_fraction * latent_heat) / heatCapacity;
        double loss_net = (dry_mass_fraction * (CP_Stack * (Tstack + 273.15) - CP_amb * (Tamb + 273.15)) + mH20_fraction * (CP_H2O_stack * (Tstack + 273.15) - CP_H2O_amb * (Tamb + 273.15))) / heatCapacity;
        double eff_net = 1 - loss_net;

        double eff = 1 - loss;
        if (eff_net>1) eff_net = 1.0;
        if (eff >1)
            eff = 1.0;

        if(eff_net <= 0.0 || eff <= 0.0){
            BaseController.showAlert("خطا","خطا در اطلاعات آنالیز مشعل","درصد اکسیژن موجود در دودکش بیشتر از حد مجاز است.", Alert.AlertType.ERROR);
            throw new IllegalArgumentException("O2 percent is not the proper one");
        }

        setEfficiency(eff);


    }


}

