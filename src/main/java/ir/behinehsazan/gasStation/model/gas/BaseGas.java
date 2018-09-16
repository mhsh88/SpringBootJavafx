package ir.behinehsazan.gasStation.model.gas;

import ir.behinehsazan.gasStation.model.mathCalculation.FindRoot;
import ir.behinehsazan.gasStation.model.mathCalculation.MathCalculation;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;

import java.util.ArrayList;


public class BaseGas implements FindRoot {

    protected Double D;
    protected Double rou;
    protected Double U;
    protected Double H;
    protected Double S;
    protected Double C_v;
    protected Double C_p;
    protected Double mu;
    protected Double kappa;
    protected Double w;
    protected Double Z;
    protected Double P;
    protected Double T;
    protected Double[] component;
    protected Double[] Xi;

    protected Double M;
    protected Double F;
    protected Double Q;
    protected Double G;
    protected Double V;
    protected Double K;
    protected Double tau;
    protected Double B;
    protected ArrayList<Double> C_n = new ArrayList<Double>();
    protected Double p1;
    public static final Double R = 8.314510;
    private double T_h;

    public double getT_h() {
        return T_h;
    }



    public Double getZ() {
        return Z;
    }

    public Double getP() {
        return P;
    }

    public void setP(Double p) {
        P = p;
    }

    public Double getT() {
        return T;
    }

    public void setT(Double t) {
        T = t;
    }

    public static Double getT_theta() {
        return T_theta;
    }

    public static Double getP_theta() {
        return p_theta;
    }

    protected static final Double T_theta = 298.15;
    protected static final Double tau_theta = 1 / T_theta;
    protected static final Double p_theta = 0.101325 * 1000;
    protected static final Double rou_theta = p_theta / (R * T_theta);
    protected Double delta_theta;


    double[][] E_star_ij = {
            {1.0, 1.02274, 0.97164, 0.97012, 0.945939, 0.973384, 0.946914, 0.94552, 0.95934, 1.0, 1.0, 1.0, 1.0, 1.0, 1.08632, 1.021, 1.00571, 0.746954, 0.902271, 1.0, 1.0},
            {1.02274, 1.0, 0.960644, 0.925053, 0.960237, 0.897362, 0.906849, 0.859764, 0.726255, 0.855134, 0.831229, 0.80831, 0.786323, 0.765171, 1.28179, 1.0, 1.5, 0.849408, 0.955052, 1.0, 1.0},
            {0.97164, 0.960644, 1.0, 1.0, 0.994635, 0.989844, 1.01953, 0.999268, 1.00235, 1.107274, 0.88088, 0.880973, 0.881067, 0.881161, 1.17052, 1.0, 0.990126, 0.708218, 0.931484, 1.0, 1.0},
            {0.97012, 0.925053, 1.0, 1.0, 1.02256, 1.01306, 1.0, 1.00532, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.16446, 1.0, 1.0, 0.693168, 0.946871, 1.0, 1.0},
            {0.945939, 0.960237, 0.994635, 1.02256, 1.0, 1.0049, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.034787, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.973384, 0.897362, 0.989844, 1.01306, 1.0049, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.3, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.946914, 0.906849, 1.01953, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.3, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.94552, 0.859764, 0.999268, 1.00532, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.95934, 0.726255, 1.00235, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 0.855134, 1.107274, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.008692, 1.0, 1.0},
            {1.0, 0.831229, 0.88088, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.010126, 1.0, 1.0},
            {1.0, 0.80831, 0.880973, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.011501, 1.0, 1.0},
            {1.0, 0.786323, 0.881067, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.012821, 1.0, 1.0},
            {1.0, 0.765171, 0.881161, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.014089, 1.0, 1.0},
            {1.08632, 1.28179, 1.17052, 1.16446, 1.034787, 1.3, 1.3, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.1, 1.0, 1.0, 1.0, 1.0},
            {1.021, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.00571, 1.5, 0.990126, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.1, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.746954, 0.849408, 0.708218, 0.693168, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.902271, 0.955052, 0.931484, 0.946871, 1.0, 1.0, 1.0, 1.0, 1.0, 1.008692, 1.010126, 1.011501, 1.012821, 1.014089, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
    };
    double[][] G_star_ij = {{1.0, 0.982746, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {0.982746, 1.0, 0.807653, 0.370296, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.67309, 1.0, 1.0, 1.0},
            {1.0, 0.807653, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.95731, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 0.370296, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.95731, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.67309, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
    double[][] V_ij = {{1.0, 0.835058, 0.886106, 0.816431, 0.915502, 0.993556, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.408838, 1.0, 1.0, 1.0, 0.993476, 1.0, 1.0},
            {0.835058, 1.0, 0.963827, 0.96987, 1.0, 1.0, 1.0, 1.0, 1.0, 1.066638, 1.077634, 1.088178, 1.098291,
                    1.108021, 1.0, 1.0, 0.9, 1.0, 1.04529, 1.0, 1.0},
            {0.886106, 0.963827, 1.0, 1.0, 0.990877, 0.992291, 1.0, 1.00367, 1.0, 1.302576, 1.191904, 1.205769,
                    1.219634, 1.233498, 1.15639, 1.0, 1.0, 1.0, 0.736833, 1.0, 1.0},
            {0.816431, 0.96987, 1.0, 1.0, 1.065173, 1.25, 1.25, 1.25, 1.25, 1.0, 1.0, 1.0, 1.0, 1.0, 1.61666,
                    1.0, 1.0, 1.0, 0.971926, 1.0, 1.0},
            {0.915502, 1.0, 0.990877, 1.065173, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0, 1.0, 1.0},
            {0.993556, 1.0, 0.992291, 1.25, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.25, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.00367, 1.25, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.25, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.066638, 1.302576, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.028973, 1.0, 1.0},
            {1.0, 1.077634, 1.191904, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.033754, 1.0, 1.0},
            {1.0, 1.088178, 1.205769, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.038338, 1.0, 1.0},
            {1.0, 1.098291, 1.219634, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.042735, 1.0, 1.0},
            {1.0, 1.108021, 1.233498, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.046966, 1.0, 1.0},
            {0.408838, 1.0, 1.15639, 1.61666, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 0.9, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {0.993476, 1.04529, 0.736833, 0.971926, 1.0, 1.0, 1.0, 1.0, 1.0, 1.028973, 1.033754, 1.038338,
                    1.042735, 1.046966, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0}};
    double[][] K_ij = {{1.0, 0.982361, 1.00363, 1.00796, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.03227, 1.0,
            1.0, 1.0, 0.942596, 1.0, 1.0},
            {0.982361, 1.0, 0.995933, 1.00851, 1.0, 1.0, 1.0, 1.0, 1.0, 0.910183, 0.895362, 0.881152, 0.86752,
                    0.854406, 1.0, 1.0, 1.0, 1.0, 1.00779, 1.0, 1.0},
            {1.00363, 0.995933, 1.0, 1.0, 1.007619, 0.997596, 1.0, 1.002529, 1.0, 0.982962, 0.983565, 0.982707,
                    0.981849, 0.980991, 1.02326, 1.0, 1.0, 1.0, 1.00008, 1.0, 1.0},
            {1.00796, 1.00851, 1.0, 1.0, 0.986893, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.02034, 1.0,
                    1.0, 1.0, 0.999969, 1.0, 1.0},
            {1.0, 1.0, 1.007619, 0.986893, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 0.997596, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.002529, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 0.910183, 0.982962, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 0.96813, 1.0, 1.0},
            {1.0, 0.895362, 0.983565, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 0.96287, 1.0, 1.0},
            {1.0, 0.881152, 0.982707, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 0.957828, 1.0, 1.0},
            {1.0, 0.86752, 0.981849, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    0.952441, 1.0, 1.0},
            {1.0, 0.854406, 0.980991, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 0.948338, 1.0, 1.0},
            {1.03227, 1.0, 1.02326, 1.02034, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {0.942596, 1.00779, 1.00008, 0.999969, 1.0, 1.0, 1.0, 1.0, 1.0, 0.96813, 0.96287, 0.957828,
                    0.952441, 0.948338, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                    1.0, 1.0}};

    double[] a_n = {1.53832600e-01, 1.34195300e+00, -2.99858300e+00, -4.83122800e-02,
            3.75796500e-01, -1.58957500e+00, -5.35884700e-02, 8.86594630e-01
            , -7.10237040e-01, -1.47172200e+00, 1.32185035e+00, -7.86659250e-01
            , 2.29129000e-09, 1.57672400e-01, -4.36386400e-01, -4.40815900e-02
            , -3.43388800e-03, 3.20590500e-02, 2.48735500e-02, 7.33227900e-02
            , -1.60057300e-03, 6.42470600e-01, -4.16260100e-01, -6.68995700e-02
            , 2.79179500e-01, -6.96605100e-01, -2.86058900e-03, -8.09883600e-03
            , 3.15054700e+00, 7.22447900e-03, -7.05752900e-01, 5.34979200e-01
            , -7.93149100e-02, -1.41846500e+00, -5.99905000e-17, 1.05840200e-01
            , 3.43172900e-02, -7.02284700e-03, 2.49558700e-02, 4.29681800e-02
            , 7.46545300e-01, -2.91961300e-01, 7.29461600e+00, -9.93675700e+00
            , -5.39980800e-03, -2.43256700e-01, 4.98701600e-02, 3.73379700e-03
            , 1.87495100e+00, 2.16814400e-03, -6.58716400e-01, 2.05518000e-04
            , 9.77619500e-03, -2.04870800e-02, 1.55732200e-02, 6.86241500e-03
            , -1.22675200e-03, 2.85090800e-03};
    double[] b_n = {1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.
            , 2., 2., 2., 2., 2., 2., 2., 2., 2., 3., 3., 3., 3., 3., 3., 3., 3., 3.
            , 3., 4., 4., 4., 4., 4., 4., 4., 5., 5., 5., 5., 5., 6., 6., 7., 7., 8.
            , 8., 8., 9., 9.};

    double[] c_n = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 1., 1., 1., 1., 1.
            , 0., 0., 1., 1., 1., 1., 1., 1., 1., 0., 1., 1., 1., 1., 1., 1., 1., 1.
            , 1., 0., 0., 1., 1., 1., 1., 1., 0., 1., 1., 1., 1., 0., 1., 0., 1., 1.
            , 1., 1., 1., 1.};
    double[] k_n = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 3., 2., 2., 2., 4., 4.
            , 0., 0., 2., 2., 2., 4., 4., 4., 4., 0., 1., 1., 2., 2., 3., 3., 4., 4.
            , 4., 0., 0., 2., 2., 2., 4., 4., 0., 2., 2., 4., 4., 0., 2., 0., 2., 1.
            , 2., 2., 2., 2.};

    double[] u_n = {0., 0.5, 1., 3.5, -0.5, 4.5, 0.5, 7.5, 9.5, 6., 12., 12.5
            , -6., 2., 3., 2., 2., 11., -0.5, 0.5, 0., 4., 6., 21.
            , 23., 22., -1., -0.5, 7., -1., 6., 4., 1., 9., -3., 21.
            , 8., -0.5, 0., 2., 7., 9., 22., 23., 1., 9., 3., 8.
            , 23., 1.5, 5., -0.5, 4., 7., 3., 0., 1., 0.};
    double[] g_n = {0., 0., 0., 0., 1., 1., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 1., 0., 0., 1., 1., 1., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 1.
            , 0., 1., 0., 0.};
    double[] q_n = {0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 1., 0., 1., 0., 0., 0., 0., 0., 0., 0., 0.
            , 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 1., 0., 0., 1., 0., 0.
            , 0., 0., 0., 1.};
    double[] f_n = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 1., 0., 0., 0., 0., 1., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0.};
    double[] s_n = {0., 0., 0., 0., 0., 0., 0., 1., 1., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0.};
    double[] w_n = {0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 1., 1., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0.};
    Double[] M_i = {28.0135
            , 44.01
            , 16.043
            , 30.07
            , 44.097
            , 58.123
            , 58.123
            , 72.15
            , 72.15
            , 86.177
            , 100.204
            , 114.231
            , 128.258
            , 142.285
            , 2.0159
            , 31.9988
            , 28.01
            , 18.0153
            , 34.082
            , 4.0026
            , 39.948};

    double[] E_i = {99.73778, 241.9606, 151.3183, 244.1667, 298.1183, 337.6389,
            324.0689, 370.6823, 365.5999, 402.636293, 427.72263, 450.325022,
            470.840891, 489.558373, 26.95794, 122.7667, 105.5348, 514.0156,
            296.355, 2.610111, 119.6299};

    double[] K_i = {0.4479153, 0.4557489, 0.4619255, 0.5279209, 0.583749, 0.6341423
            , 0.6406937, 0.6798307, 0.6738577, 0.7175118, 0.7525189, 0.784955
            , 0.8152731, 0.8437826, 0.3514916, 0.4186954, 0.4533894, 0.3825868
            , 0.4618263, 0.3589888, 0.4216551};

    Double[] G_i = {0.027815, 0.189065, 0., 0.0793, 0.141239, 0.281835, 0.256692
            , 0.366911, 0.332267, 0.289731, 0.337542, 0.383381, 0.427354, 0.469659
            , 0.034369, 0.021, 0.038953, 0.3325, 0.0885, 0., 0.};

    Double[] Q_i = {0., 0.69, 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 1.06775, 0.633276, 0., 0.,};
    Double[] F_i = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 0.
            , 0., 0., 0.};
    double[] S_i = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0.
            , 0., 0., 0., 0., 0., 0., 0., 1.5822, 0.39, 0.
            , 0.};
    double[] W_i = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1.
            , 0., 0., 0.};

    double[] Ao1_i = {23.2653, 26.35604, 35.53603, 42.42766, 50.40669, 42.22997, 39.9994
            , 48.37597, 48.86978, 52.69477, 57.77391, 62.95591, 67.79407, 71.63669
            , 18.7728, 22.49931, 23.15547, 27.27642, 27.28069, 15.74399, 15.74399};

    double[] Ao2_i = {-2801.72907, -4902.17152, -15999.69151, -23639.65301, -31236.63551,
            -38957.80933, -38525.50276, -45215.83, -51198.30946, -52746.83318,
            -57104.81056, -60546.76385, -66600.12837, -74131.45483, -5836.9437,
            -2318.32269, -2635.24412, -7766.73308, -6069.03587, -745.375, -745.375};

    double[] Bo_i = {3.50031, 3.50002, 4.00088, 4.00263, 4.02939, 4.33944, 4.06714, 4., 4.
            , 4., 4., 4., 4., 4., 2.47906, 3.50146, 3.50055,
            4.00392, 4., 2.5, 2.5};
    double[] Co_i = {1.37320000e-01, 2.04452000e+00, 7.63150000e-01, 4.33939000e+00
            , 6.60569000e+00, 9.44893000e+00, 8.97575000e+00, 8.95043000e+00
            , 1.17618000e+01, 1.16977000e+01, 1.37266000e+01, 1.56865000e+01,
            1.80241000e+01, 2.10069000e+01, 0.95806, 1.07558, 1.02865, 0.01059, 3.11942
            , 0.0
            , 0.0};
    double[] Do_i = {662.738, 919.306, 820.659, 559.314, 479.856, 468.27, 438.27
            , 178.67, 292.503, 182.326, 169.789, 158.922, 156.854, 164.947,
            228.734, 2235.71, 1550.45, 268.795, 1833.63, 0., 0.};

    double[] Eo_i = {-1.46600000e-01, -1.06044000e+00, 4.60000000e-03, 1.23722000e+00,
            3.19700000e+00, 6.89406000e+00, 5.25156000e+00, 2.18360000e+01
            , 2.01101000e+01, 2.68142000e+01, 3.04707000e+01, 3.38029000e+01,
            3.81235000e+01, 4.34931000e+01, 0.45444, 1.01334, 0.00493, 0.98763, 1.00243, 0.0, 0.0};
    double[] Fo_i = {680.562, 865.07, 178.41, 223.284, 200.893, 183.636, 198.018,
            840.538, 910.237, 859.207, 836.195, 815.064, 814.882, 836.264,
            326.843, 1116.69, 704.525, 1141.41, 847.181, 0., 0.};
    double[] Go_i = {0.90066, 2.03366, 8.74432, 13.1974, 19.1921, 24.4618, 25.1423
            , 33.4032, 33.1688, 38.6164, 43.5561, 48.1731, 53.3415, 58.3657
            , 1.56039, 0., 0., 3.06904, 0., 0., 0.};
    double[] Ho_i = {1740.06, 483.553, 1062.82, 1031.38, 955.312, 1914.1, 1905.02
            , 1774.25, 1919.37, 1826.59, 1760.46, 1693.07, 1693.79, 1750.24
            , 1651.71, 0., 0., 2507.37, 0., 0., 0.};

    double[] Io_i = {0.0
            , 0.01393
            , -4.46921
            , -6.01989
            , -8.37267
            , 14.7824
            , 16.1388
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , -1.3756
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , 0.0
            , 0.0};

    double[] Jo_i = {0., 341.109, 1090.53, 1071.29, 1027.29, 903.185, 893.765,
            0., 0., 0., 0., 0., 0., 0.,
            1671.69, 0., 0., 0., 0., 0., 0.};

    final double[][] Ch = {{-1.60624067400000000, 0.94300879800000000, - 0.07952887700000000, - 0.22221523100000000,
            0.10232186600000000, 5.71407681800000000, 0.03245301800000000,
            0.38657166700000000, 5.49180622100000000, 40.21527998000000000, - 0.00143821000000000,
            - 0.01023342100000000,
            0.20477971800000000, 5.72853864300000000, 44.13499613000000000},
        {0.40165793600000000, -0.21208744900000000, - 0.01232112200000000, 0.04548728500000000,
            - 0.00924222800000000, - 0.27407968000000000,
            - 0.00491592200000000, - 0.02388913000000000, - 0.48101573500000000, - 3.86828445000000000,
            0.00021709700000000, 0.00234085000000000,
            0.03063534700000000, - 0.07842183200000000, - 3.26417032000000000}};


    public void setComponent(Double[] component) {
        double sum = MathCalculation.listSum(component);
        // TODO chech sum is not zero
        for (int i = 0; i < component.length; i++) {
            component[i] = component[i] / sum;

        }

        this.Xi = component;
    }

    public Double[] getComponent() {
        return this.Xi;
    }


    public void calculate(double P, double T) {
        calculate(P, T, getComponent());

    }

    public void calculate(double P, double T, Double[] component) {
        setP(P);
        setT(T);
        setComponent(component);
        tau = 1 / getT();

        M = MathCalculation.dotProduct(M_i, getComponent());
        F = MathCalculation.dotProduct(F_i, getComponent());
        Q = MathCalculation.dotProduct(Q_i, getComponent());
        double temp = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 21; j++) {
                temp += Xi[i] * Xi[j] * (G_star_ij[i][j] - 1) * (G_i[i] + G_i[j]);

            }
        }

        G = MathCalculation.dotProduct(G_i, getComponent()) + temp;

        ArrayList<Double> __V = new ArrayList<Double>();
        __V.clear();
        ArrayList<Double> _V = new ArrayList<Double>();
        _V.clear();
        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 21; j++) {
                __V.add(Xi[i] * Xi[j] * (Math.pow(V_ij[i][j], 5) - 1) * (Math.pow((E_i[i] * E_i[j]), (5.0 / 2))));

            }
            _V.add(MathCalculation.listSum(__V));
            __V.clear();
        }
        V = Math.pow(Math.pow(MathCalculation.dotProduct(MathCalculation.powProduct(E_i, 5.0 / 2), getComponent()), 2) + 2 * MathCalculation.listSum(_V), 1.0 / 5);


        temp = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 21; j++) {
                temp += Xi[i] * Xi[j] * (Math.pow(K_ij[i][j], 5) - 1) * Math.pow((K_i[i] * K_i[j]), (5.0 / 2));

            }
        }

        K = Math.pow(Math.pow(MathCalculation.dotProduct(MathCalculation.powProduct(K_i, 5.0 / 2), getComponent()), 2) + 2 * temp, 1.0 / 5);


        ArrayList<Double> B_star_n = new ArrayList<Double>();
        ArrayList<Double> Bn = new ArrayList<Double>();
        ArrayList<Double> B_star_n1 = new ArrayList<Double>();
        ArrayList<Double> B_star_n2 = new ArrayList<Double>();
        Double e_ij, g_ij, B_star_nij, b_star_n;
        B_star_n.clear();
        Bn.clear();
        for (int n = 0; n < 18; n++) {
            B_star_n1.clear();
            for (int i = 0; i < 21; i++) {
                B_star_n2.clear();
                for (int j = 0; j < 21; j++) {
                    e_ij = E_star_ij[i][j] * Math.pow((E_i[i] * E_i[j]), 0.5);
                    g_ij = G_star_ij[i][j] * ((G_i[i] + G_i[j]) / 2);
                    B_star_nij = Math.pow((g_ij + 1 - g_n[n]), g_n[n]) * Math.pow((Q_i[i] * Q_i[j] + 1 - q_n[n]), q_n[n]) *
                            Math.pow(Math.pow(F_i[i] * F_i[j], 0.5) + 1 - f_n[n], f_n[n]) * Math.pow(
                            S_i[i] * S_i[j] + 1 - s_n[n], s_n[n]) *
                            Math.pow(W_i[i] * W_i[j] + 1 - w_n[n], w_n[n]);
                    b_star_n = a_n[n] * Xi[i] * Xi[j] * B_star_nij * Math.pow(e_ij, u_n[n]) * Math.pow((K_i[i] * K_i[j]), (3.0 / 2));
                    B_star_n2.add(b_star_n);
                }
                B_star_n1.add(MathCalculation.listSum(B_star_n2));
            }
            B_star_n.add(MathCalculation.listSum(B_star_n1));
            Bn.add(B_star_n.get(n) * Math.pow(tau, u_n[n]));
        }
        B = MathCalculation.listSum(Bn);


        ArrayList<Double> p11 = new ArrayList<Double>();
        p11.clear();
        Double _C_n;
        for (int n = 12; n < 18; n++) {
            _C_n = a_n[n] * Math.pow((G + 1 - g_n[n]), g_n[n]) * Math.pow((Math.pow(Q, 2) + 1 - q_n[n]), q_n[n]) * Math.pow(
                    (F + 1 - f_n[n]), f_n[n]) * Math.pow(V, u_n[n]);
            p11.add(_C_n * Math.pow(tau, u_n[n]));
        }
        p1 = MathCalculation.listSum(p11);


        C_n.clear();

        for (int m = 12; m < 58; m++) {
            C_n.add(a_n[m] * Math.pow((G + 1 - g_n[m]), g_n[m]) * Math.pow(Math.pow(Q, 2) + 1 - q_n[m], q_n[m]) * Math.pow(
                    (F + 1 - f_n[m]), f_n[m]) * Math.pow(V, u_n[m]));
        }

        UnivariateFunction function = new MyFunction();
        final double relativeAccuracy = 1.0e-12;
        final double absoluteAccuracy = 1.0e-8;
        final int maxOrder = 5;
//        UnivariateSolver nonBracketing = new BrentSolver(relativeAccuracy, absoluteAccuracy);
//        double baseRoot = nonBracketing.solve(100, function, 0.0, 1000.0);

        BisectionSolver bSolver = new BisectionSolver(relativeAccuracy, absoluteAccuracy);
        double res = bSolver.solve(100, function, 0.0, 1000.0, 1.0);

//        UnivariateSolver solver = new BracketingNthOrderBrentSolver(relativeAccuracy, absoluteAccuracy, maxOrder);
//        double c = solver.solve(100, function, -1000000.0, 1000000.0, 100.0);

//        double soldelta = rootFind();
        double soldelta = res;

//        double result = instanceFun()


        Z = (P * tau * Math.pow(K, 3)) / (soldelta * R * 1);

        delta_theta = rou_theta * Math.pow(K, 3);


        ArrayList<Double> cop_i_R = new ArrayList<Double>();
        ArrayList<Double> phi_o_i = new ArrayList<Double>();
        ArrayList<Double> phi_o_tau_i = new ArrayList<Double>();

        for (int i = 0; i < 21; i++) {
            try {
                cop_i_R.add(Bo_i[i] + // cop_i_R = cop_i_R / R
                        Co_i[i] * Math.pow((Do_i[i] * tau / Math.sinh(Do_i[i] * tau)), 2) +
                        Eo_i[i] * Math.pow((Fo_i[i] * tau / Math.cosh(Fo_i[i] * tau)), 2) +
                        Go_i[i] * Math.pow((Ho_i[i] * tau / Math.sinh(Ho_i[i] * tau)), 2) +
                        Io_i[i] * Math.pow((Jo_i[i] * tau / Math.cosh(Jo_i[i] * tau)), 2));
                if (Double.isNaN(cop_i_R.get(i))) {
                    cop_i_R.set(i, 0.0);
                }
            } catch (Exception e) {
                cop_i_R.add(0.0);
            }

            try {
                phi_o_i.add(Xi[i] * (Ao1_i[i] + Ao2_i[i] * tau + Bo_i[i] * Math.log(tau) +
                        Co_i[i] * Math.log(Math.sinh(Do_i[i] * tau)) -
                        Eo_i[i] * Math.log(Math.cosh(Fo_i[i] * tau)) +
                        Go_i[i] * Math.log(Math.sinh(Ho_i[i] * tau)) -
                        Io_i[i] * Math.log(Math.cosh(Jo_i[i] * tau)) + Math.log(Xi[i])));
                if (Double.isNaN(phi_o_i.get(i))) phi_o_i.set(i, 0.0);
            } catch (Exception e) {
                phi_o_i.add(0.0);
            }
            try {
                phi_o_tau_i.add(Xi[i] * (Ao2_i[i] + (Bo_i[i] - 1) / tau +
                        Co_i[i] * Do_i[i] * ((Math.cosh(Do_i[i] * tau)) / Math.sinh(Do_i[i] * tau)) -
                        Eo_i[i] * Fo_i[i] * ((Math.sinh(Fo_i[i] * tau)) / Math.cosh(Fo_i[i] * tau)) +
                        Go_i[i] * Ho_i[i] * ((Math.cosh(Ho_i[i] * tau)) / Math.sinh(Ho_i[i] * tau)) -
                        Io_i[i] * Jo_i[i] * ((Math.sinh(Jo_i[i] * tau)) / Math.cosh(Jo_i[i] * tau))));
                if (Double.isNaN(phi_o_tau_i.get(i)))
                    phi_o_tau_i.set(i, 0.0);
            } catch (Exception e) {
                phi_o_tau_i.add(0.0);
            }
        }

//       double[] c = cop_i_R.stream().mapToDouble(D -> D).toArray();
//       Double[] arr = ListAdapter.adapt(cop_i_R).asLazy().collectDouble(each -> each).toArray();
//        (Double[]) cop_i_R.stream().mapToDouble(Double::doubleValue).toArray());
        Double cop_R = MathCalculation.dotProduct(Xi, cop_i_R.stream().mapToDouble(Double::doubleValue).toArray());
        Double phi_o = MathCalculation.listSum(phi_o_i) + Math.log(soldelta / delta_theta) + Math.log(tau_theta / tau);
        Double phi_o_tau = MathCalculation.listSum(phi_o_tau_i);
        Double phi_o_tau_tau = -Math.pow(tau, (-2.0)) * (cop_R - 1);


        ArrayList<Double> tauphi_tau_1 = new ArrayList<>();

        ArrayList<Double> tau2_phi_tautau_1 = new ArrayList<>();
        ArrayList<Double> phi_2_1 = new ArrayList<>();

        tauphi_tau_1.clear();
        tau2_phi_tautau_1.clear();
        phi_2_1.clear();

        for (int i = 0; i < 18; i++) {
            tauphi_tau_1.add(u_n[i] * B_star_n.get(i) * Math.pow(tau, u_n[i]));
            tau2_phi_tautau_1.add(((Math.pow(u_n[i], 2) - u_n[i]) * (B_star_n.get(i)) * (Math.pow(tau, u_n[i]))));
            phi_2_1.add((1 - u_n[i]) * B_star_n.get(i) * Math.pow(tau, u_n[i]));
        }


        ArrayList<Double> phi1 = new ArrayList<>();
        ArrayList<Double> tauphi_tau_2 = new ArrayList<>();
        ArrayList<Double> tau2_phi_tautau_2 = new ArrayList<>();
        ArrayList<Double> delta_phi_delta_1 = new ArrayList<>();
        ArrayList<Double> phi_1_1 = new ArrayList<>();
        ArrayList<Double> phi_2_2 = new ArrayList<>();
        for (int i = 12; i < 18; i++) {
            phi1.add(C_n.get(i - 12) * Math.pow(tau, u_n[i]));
            tauphi_tau_2.add(u_n[i] * C_n.get(i - 12) * Math.pow(tau, u_n[i]));
            tau2_phi_tautau_2.add((Math.pow(u_n[i], 2) - u_n[i]) * C_n.get(i - 12) * Math.pow(tau, u_n[i]));
            delta_phi_delta_1.add(C_n.get(i - 12) * Math.pow(tau, u_n[i]));
            phi_1_1.add(C_n.get(i - 12) * Math.pow(tau, u_n[i]));
            phi_2_2.add((1 - u_n[i]) * C_n.get(i - 12) * Math.pow(tau, u_n[i]));
        }


        ArrayList<Double> phi2 = new ArrayList<>();
        ArrayList<Double> tauphi_tau_3 = new ArrayList<>();
        ArrayList<Double> tau2_phi_tautau_3 = new ArrayList<>();
        ArrayList<Double> delta_phi_delta_2 = new ArrayList<>();
        ArrayList<Double> phi_1_2 = new ArrayList<>();
        ArrayList<Double> phi_2_3 = new ArrayList<>();
        for (int i = 12; i < 58; i++) {
            phi2.add(C_n.get(i - 12) * (Math.pow(tau, u_n[i])) * (Math.pow(soldelta, b_n[i])) * Math.exp(-c_n[i] * Math.pow(soldelta, k_n[i])));
            tauphi_tau_3.add(u_n[i] * C_n.get(i - 12) *
                    Math.pow(tau, u_n[i]) * (Math.pow(soldelta, b_n[i])) *
                    Math.exp(-c_n[i] * Math.pow(soldelta, k_n[i])));
            tau2_phi_tautau_3.add((Math.pow(u_n[i], 2) - u_n[i]) * C_n.get(i - 12) *
                    (Math.pow(tau, u_n[i])) * (Math.pow(soldelta, b_n[i])) *
                    Math.exp(-c_n[i] * Math.pow(soldelta, k_n[i])));
            delta_phi_delta_2.add((C_n.get(i - 12) * (Math.pow(tau, u_n[i]))) * Math.pow(soldelta, b_n[i]) *
                    (b_n[i] - c_n[i] * k_n[i] * (Math.pow(soldelta, k_n[i]))) *
                    Math.exp(-c_n[i] * (Math.pow(soldelta, k_n[i]))));

            phi_1_2.add(C_n.get(i - 12) * Math.pow(tau, u_n[i]) * Math.pow(soldelta, b_n[i]) *
                    (b_n[i] - (1 + k_n[i]) * c_n[i] * k_n[i] * Math.pow(soldelta, k_n[i]) +
                            Math.pow((b_n[i] - c_n[i] * k_n[i] * Math.pow(soldelta, k_n[i])), 2)) *
                    Math.exp(-c_n[i] * (Math.pow(soldelta, k_n[i]))));

            phi_2_3.add((1 - u_n[i]) * C_n.get(i - 12) * Math.pow(tau, u_n[i]) * Math.pow(soldelta, b_n[i]) *
                    (b_n[i] - c_n[i] * k_n[i] * Math.pow(soldelta, k_n[i])) *
                    Math.exp(-c_n[i] * (Math.pow(soldelta, k_n[i]))));

        }

        Double phi = phi_o + B * soldelta / Math.pow(K, 3) - soldelta * MathCalculation.listSum(phi1) + MathCalculation.listSum(phi2);
        Double tau_phi_tau = tau * phi_o_tau + (soldelta / Math.pow(K, 3)) * MathCalculation.listSum(tauphi_tau_1) - soldelta * MathCalculation.listSum(
                tauphi_tau_2) + MathCalculation.listSum(tauphi_tau_3);
        Double tau2_phi_tautau = Math.pow(tau, 2) * phi_o_tau_tau + (soldelta / Math.pow(K, 3)) * MathCalculation.listSum(tau2_phi_tautau_1) - soldelta * MathCalculation.listSum(tau2_phi_tautau_2) + MathCalculation.listSum(tau2_phi_tautau_3);
        Double delta_phi_delta = 1 + B * soldelta / (Math.pow(K, 3)) - soldelta * MathCalculation.listSum(delta_phi_delta_1) + MathCalculation.listSum(delta_phi_delta_2);
        Double phi_1 = 1 + 2 * B * (soldelta / (Math.pow(K, 3))) - 2 * soldelta * MathCalculation.listSum(phi_1_1) + MathCalculation.listSum(phi_1_2);
        Double phi_2 = 1 + (soldelta / Math.pow(K, 3)) * MathCalculation.listSum(phi_2_1) - soldelta * MathCalculation.listSum(phi_2_2) + MathCalculation.listSum(phi_2_3);


        D = M * soldelta / Math.pow(K, 3);
        rou = soldelta / Math.pow(K, 3);
        U = R * T / M * tau_phi_tau;
        H = R * T / M * (tau_phi_tau + delta_phi_delta);
        S = (tau_phi_tau - phi) * R / M;
        C_v = R / M * (-tau2_phi_tautau);
        C_p = R / M * (-tau2_phi_tautau + Math.pow(phi_2, 2) / phi_1);
        mu = (phi_2 / phi_1 - 1) / C_p / M / rou * 1000;
        kappa = phi_1 / Z * C_p / C_v;
        w = Math.sqrt(phi_1 * C_p / C_v * R * T / M * 1000);


        double Ma = 28.95;  //# molecular mass of air
        double Gamma_g = M / Ma;
        double psi = getP() * 0.145038;
        double [] mass_matrix = {1, Math.log(psi), Math.log(Gamma_g), Math.pow((Math.log(psi)) , 2.0) , Math.log(psi) * Math.log(Gamma_g), Math.pow(Math.log(Gamma_g) , 2.0),
                Math.pow((Math.log(psi)) , 3.0), Math.pow(Math.log(psi) , 2.0) * Math.log(Gamma_g), Math.pow(Math.log(Gamma_g) , 2.0) * Math.log(psi), Math.pow((Math.log(Gamma_g)) , 3),
                Math.pow((Math.log(psi)) , 4.0), Math.pow(Math.log(psi) , 3.0) * Math.log(Gamma_g), Math.pow(Math.log(psi) , 2.0) * Math.pow(Math.log(Gamma_g) , 2.0),
                Math.pow(Math.log(Gamma_g) , 3.0) * Math.log(psi), Math.pow(Math.log(Gamma_g),  4)};
        double[] cc;
        if (Gamma_g <= 0.7) {
             cc = Ch[0];
        }
        else{
        cc = Ch[1];}

        double T_h = 1 / MathCalculation.dotProduct( cc, mass_matrix);
        T_h = (T_h - 32) / 1.8;
        this.T_h = T_h;


    }


    @Override
    public double function(double delta) {

        ArrayList<Double> _C_n = new ArrayList<Double>();
        _C_n.clear();
        for (int n = 12; n < 58; n++) {
            _C_n.add(C_n.get(n - 12) * Math.pow(tau, u_n[n]) * Math.pow(delta, b_n[n]) * (b_n[n] - c_n[n] * k_n[n] * Math.pow(delta, k_n[n]))
                    * Math.exp(-c_n[n] * Math.pow(delta, k_n[n])));

        }
        double __C_n = MathCalculation.listSum(_C_n);

        return (delta * R) / (tau * Math.pow(K, 3)) * (1 + B * delta / Math.pow(K, 3) - delta * p1 + __C_n) - P;
    }





//    @Override
//    public double value(double x) {
//        return function(x);
//    }
//
//    @Override
//    public DerivativeStructure value(DerivativeStructure t) throws MathIllegalArgumentException {
//        return t.sin();
//    }


//    public double instanceFun(BaseGas gas){
//        double result;
//        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
//        result = solver.solve(1000, gas, -10000, 10000);
//        return result;
//    }


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
