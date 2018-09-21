package ir.behinehsazan.gasStation.model.mathCalculation;

import java.io.Serializable;

public interface FindRoot extends Serializable {
    default double rootFind(){
        double x = 0, del = 1e-10, a = -100000    , b = 100000;
        double dx = b-a;
        int k = 0;
        while (Math.abs(dx) > del) {
            x = (a+b)/2;
            if ((function(a)*function(x)) < 0) {
                b  = x;
                dx = b-a;
            }
            else {
                a = x;
                dx = b-a;
            }
            k++;
        }
        System.out.println("Iteration number: " + k);
        System.out.println("Root obtained: " + x);
        System.out.println("Estimated error: " + dx);
        return x;
    };

    double function(double delta);
}
