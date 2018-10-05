package ir.behinehsazan.gasStation.model.pipeLine.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.behinehsazan.gasStation.model.base.GasConsumer;
import ir.behinehsazan.gasStation.model.gas.Gas;
import ir.behinehsazan.gasStation.model.mathCalculation.FindRoot;
import ir.behinehsazan.gasStation.model.mathCalculation.MathCalculation;
import javafx.scene.control.Alert;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import sample.controller.base.BaseController;

public class BasePipe extends GasConsumer implements FindRoot {
    @JsonIgnore
    private double length;
    private Gas gas;
    @JsonIgnore
    private double outerDiameter;
    @JsonIgnore
    private double interDiameter;
    @JsonIgnore
    private double insulationThickness;
    @JsonIgnore
    private double insulationFactor;
    @JsonIgnore
    private double R;
//    private boolean Inverse = false;
    @JsonIgnore
    private double pmean;
    @JsonIgnore
    private double tmean;


    public BasePipe(){
        setEfficiency(1.0);
    }
    public BasePipe(double T, double P, Gas g, double OD, double ID, double pipeLength, double QSdot, double t, double k_insolation, boolean inverse){
        super();
        setInverse(inverse);
        if(isInverse()){
            setTout(T);
            setPout(P);
        }
        else{
            setTin(T);
            setPin(P);
        }
        setGas(g);
        setOuterDiameter(OD);
        setInterDiameter(ID);
        setLength(pipeLength);
        setDebi(QSdot);
        setInsulationThickness(t);
        setInsulationFactor(k_insolation);
        calculate();

    }

    final static private double[] Temp = {175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900,
            950, 1000, 1050, 1100, 1150, 1200, 1250, 1300, 1350, 1400, 1500, 1600, 1700, 1800, 1900};
    final static private double[] cp = {1002.3, 1002.5, 1002.7, 1003.1, 1003.8, 1004.9, 1006.3, 1008.2, 1010.6, 1013.5, 1020.6, 1029.5, 1039.8,
            1051.1, 1062.9, 1075, 1087, 1098.7, 1110.1, 1120.9, 1131.3, 1141.1, 1150.2, 1158.9, 1167, 1174.6, 1181.7,
            1188.4, 1194.6, 1200.5, 1211.2, 1220.7, 1229.3, 1237, 1244};
    final static private double[] mu_table = {0.00001182, 0.00001329, 0.00001467, 0.00001599, 0.00001725, 0.00001846, 0.00001962, 0.00002075,
            0.00002181, 0.00002286, 0.00002485, 0.0000267, 0.00002849, 0.00003017, 0.00003178, 0.00003332,
            0.00003482, 0.00003624, 0.00003763, 0.00003897, 0.00004026, 0.00004153, 0.00004276, 0.00004396,
            0.00004511, 0.00004626, 0.00004736, 0.00004846, 0.00004952, 0.00005057, 0.00005264, 0.00005457,
            0.00005646, 0.00005829, 0.00006008};
    final static private double[] k_table = {0.00001593, 0.00001809, 0.0000202, 0.00002227, 0.00002428, 0.00002624, 0.00002816, 0.00003003,
            0.00003186, 0.00003365, 0.0000371, 0.00004041, 0.00004357, 0.00004661, 0.00004954, 0.00005236,
            0.00005509, 0.00005774, 0.0000603, 0.00006276, 0.0000652, 0.00006754, 0.00006985, 0.00007209,
            0.00007427, 0.0000764, 0.00007849, 0.00008054, 0.00008253, 0.0000845, 0.00008831, 0.00009199,
            0.00009554, 0.00009899, 0.00010233};
    final static private double[] rou_table = {2.017, 1.765, 1.569, 1.412, 1.284, 1.177, 1.086, 1.009, 0.9413, 0.8824, 0.7844, 0.706, 0.6418,
            0.5883, 0.543, 0.5043, 0.4706, 0.4412, 0.4153, 0.3922, 0.3716, 0.353, 0.3362, 0.3209, 0.3069,
            0.2941, 0.2824, 0.2715, 0.2615, 0.2521, 0.2353, 0.2206, 0.2076, 0.1961, 0.1858};
    final static private UnivariateInterpolator interpolator = new SplineInterpolator();
    final static private UnivariateFunction mu_interpole = interpolator.interpolate(Temp, mu_table);
    final static private UnivariateFunction rou_interpole = interpolator.interpolate(Temp, rou_table);
    final static private UnivariateFunction cp_interpole = interpolator.interpolate(Temp, cp);
    final static private UnivariateFunction k_interpole = interpolator.interpolate(Temp, k_table);


    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public double getOuterDiameter() {
        return outerDiameter;
    }

    public void setOuterDiameter(double outerDiameter) {
        this.outerDiameter = outerDiameter;
    }

    public double getInterDiameter() {
        return interDiameter;
    }

    public void setInterDiameter(double interDiameter) {
        this.interDiameter = interDiameter;
    }

    public double getInsulationThickness() {
        return insulationThickness;
    }

    public void setInsulationThickness(double insulationThickness) {
        this.insulationThickness = insulationThickness;
    }

    public double getInsulationFactor() {
        return insulationFactor;
    }

    public void setInsulationFactor(double insulationFactor) {
        this.insulationFactor = insulationFactor;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
//    public void setInverse(boolean bool){
//        this.Inverse = bool;
//
//    }
//    public boolean isInverse(){
//        return this.Inverse;
//    }

    public double getPmean() {
        return pmean;
    }

    public void setPmean(double pmean) {
        this.pmean = pmean;
    }

    public double getTmean() {
        return tmean;
    }

    public void setTmean(double tmean) {
        this.tmean = tmean;
    }

    @Override
    public void calculate() {

        double A = Math.PI * getInterDiameter() * getInterDiameter() / 4;
        double V = getQdot() / A;
        double t1;
        if(isInverse())
            t1 = getTout();
        else
            t1 = getTin();
        double t2 = t1 + 1;
        double Ts = getTenv() + 5;
        Gas g = getGas();
        double hair;
        double deltaP = .0;
        double pin = getPin();
        double pout = getPout();
        while (Math.abs((t1 - t2) / t2) > Math.pow(10, -5)) {
            if(isInverse())
                g.calculate(getPout(), (t1 + t2) / 2);
            else
                g.calculate(getPin(), (t1 + t2) / 2);
            double mdot = getQdot() * g.getD();
            t1 = t2;
            double rou = g.getD() * 0.001;
            double T = g.getT() * 1.8;
            double Mg = g.getM();
            double X = 3.5 + 986 / T + 0.01 * Mg;
            double Y = 2.4 - 0.2 * X;
            double K = ((9.4 + 0.02 * Mg) * Math.pow(T, 1.5)) / (209 + 19 * Mg + T);
            double mu = (K * Math.pow(10, -4)) * Math.exp(X * Math.pow(rou, Y));

            R = g.getD() * V * getInterDiameter() / (mu * 0.001);

            double k_gas = 0.05;

            double Pr = g.getC_p() * mu / k_gas;
            if (getVair() >= 2) {
                hair = 10.45 - getVair() + 10 * Math.pow(getVair(), (1.0 / 2));
            } else {
                hair = hairCalculation(Ts);
            }
            double gamma = g.getM() / 28.966;

            double k_steel = 52;  //# thermal conductivity of steel is 45 W/m.K


            UnivariateFunction function = new MyFunction();
            final double relativeAccuracy = 1.0e-12;
            final double absoluteAccuracy = 1.0e-8;
            final int maxOrder = 5;
//            UnivariateSolver nonBracketing = new BrentSolver(relativeAccuracy, absoluteAccuracy);
//            double baseRoot = nonBracketing.solve(100, function, 0.0, 1000000.0);

            BisectionSolver bSolver = new BisectionSolver(relativeAccuracy, absoluteAccuracy);
            double baseRoot = bSolver.solve(100, function, 0.0, 1000.0, 1.0);


//            double friction = rootFind();
            double friction = Math.abs(baseRoot);
            deltaP = friction * g.getD() * (Math.pow(V , 2) / (2 * getInterDiameter())) * getLength()  * 0.001;
            if(isInverse())
                pin = (getPout() - deltaP);
//                setPin(getPout() - deltaP);
            else
                pout = (getPin() - deltaP);
//                setPout(getPin() - deltaP);

            double Power = deltaP * getQdot();

            double Nu = 4.82 + 0.0185 * Math.pow((R * Pr) , 0.827);

            double h_gas = Nu * k_gas / getInterDiameter();
            double h_Total;
            if (getInsulationThickness() > 0){
            h_Total = 1 / (1 / hair + 1 / h_gas + ((getOuterDiameter() - getInterDiameter()) / Math.log(getOuterDiameter() / getInterDiameter())) / k_steel +
                    ((getOuterDiameter() + 2 * getInsulationThickness() - getOuterDiameter()) / Math.log((getOuterDiameter() + 2 * getInsulationThickness()) / getOuterDiameter())) / getInsulationFactor());}
            else{
            h_Total = 1 / (1 / hair + 1 / h_gas + ((getOuterDiameter() - getInterDiameter()) / Math.log(getOuterDiameter() / getInterDiameter())) / k_steel);}

            if(isInverse()){

            setTin((getTout() - getTenv()) / Math.exp(
                    -Math.PI * getInterDiameter() * getLength() * h_Total / (mdot * g.getC_p() * 1000)) + getTenv());
            double tt = getTin();
            if(Math.abs(tt)>273.15 + 70){
                BaseController.showAlert("خطا","خطا در اطلاعات ورودی","دبی وارد شده برای ران‌ها یا ایستگاه کمتر از حد مجاز است.", Alert.AlertType.ERROR);
                throw new IllegalArgumentException("دبی وارد شده کمتر از حد مجاز است. \n لطفا اطلاعات صحیح وارد نمایید.");
            }

            }
            else{
                setTout((getTin() - getTenv()) * Math.exp(
                        -Math.PI * getInterDiameter() * getLength() * h_Total / (mdot * g.getC_p() * 1000)) + getTenv());
            }

            setConsumption(mdot * g.getC_p() * (getTin() - getTout()));
            double outter_A = Math.PI * getOuterDiameter() * getOuterDiameter() / 4;
            double deltaT = getConsumption() * 1000 / (hair * outter_A);
            Ts = getTenv() + deltaT;
            if(isInverse())
                t2 = getTin();
            else
                t2= getTout();


        }
        if(isInverse())
            setPin(pin);
        else
            setPout(pout);



    }



    private double hairCalculation(double ts) {
        double g = 9.81;
        double mu = mu_interpole.value(getTenv());
        double rou = rou_interpole.value(getTenv());
        double Cp_air = cp_interpole.value(getTenv());
        double k_air = k_interpole.value(getTenv());
        double beta = 2 / (ts - getTenv());
        double nu = mu / rou;
        double Gr = (g * beta * (ts - getTenv()) * Math.pow(getOuterDiameter(), 3)) / Math.pow(nu, 2);
        double Pr = mu * Cp_air / k_air;
        double Ra = Gr * Pr;
        double Tf = (ts + getTenv()) / 2;
        double Nu = Math.pow((0.6 + ((0.387 * Math.pow(Ra, (1.0 / 6))) /
                Math.pow((1 + Math.pow((0.559 / Pr), (9.0 / 16))), (8.0 / 27)))), 2);
        double hair = k_air * Nu / getOuterDiameter();
        return hair;

    }

    @Override
    public double function(double f) {
        if (f <= 0){
        f = Math.abs(f);
        }

        return 1 / Math.sqrt(f) + 2 * MathCalculation.logN(0.01 / (0.4 * 3.7) + 2.57 / (R * Math.sqrt(f)),2);


    }

    class MyFunction implements UnivariateFunction {
        public double value(double x) {
            double y = function(x);
// if (somethingBadHappens) {
// throw new LocalException(x);
// }
            return y;
        }

    }
}
