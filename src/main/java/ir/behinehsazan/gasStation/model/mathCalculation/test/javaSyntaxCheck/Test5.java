package ir.behinehsazan.gasStation.model.mathCalculation.test.javaSyntaxCheck;



import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.apache.commons.math3.exception.DimensionMismatchException;

import java.util.TreeSet;

public class Test5 {

    public static void main(String args[]) {
        NewtonRaphsonSolver test = new NewtonRaphsonSolver(1E-10);

        UnivariateDifferentiableFunction f = new UnivariateDifferentiableFunction() {

            public double value(double x) {
                return Math.sin(x);
            }

            public DerivativeStructure value(DerivativeStructure t) throws
                    DimensionMismatchException {
                return t.sin();
            }
        };

        double EPSILON = 1e-6;
        TreeSet<Double> set = new TreeSet<>();
        for (int i = 1; i <= 5000; i++) {
            set.add(test.solve(1000, f, i, i + EPSILON));
        }
        for (Double s : set) {
            if (s > 0) {
                System.out.println(s);
            }
        }
    }
}


