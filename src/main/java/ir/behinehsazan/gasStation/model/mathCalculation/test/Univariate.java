package ir.behinehsazan.gasStation.model.mathCalculation.test;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

public class Univariate {

    public static void main(String[] args){
    double x[] = { 0.0, 1.0, 2.0 };
    double y[] = { 1.0, -1.0, 2.0};
    UnivariateInterpolator interpolator = new SplineInterpolator();
    UnivariateFunction function = interpolator.interpolate(x, y);
    double interpolationX = 0.5;
    double interpolatedY = function.value(interpolationX);
    System.out.println("f(" + interpolationX + ") = " + interpolatedY);
    }
}
