package ir.behinehsazan.gasStation.model.mathCalculation;

public class BisectionMethod {

    public static void main(String argv[]) {
        calculate();
    }
    public static void calculate(){
        double x = 0, del = 1e-10, a = 1, b = 2;
        double dx = b-a;
        int k = 0;
        while (Math.abs(dx) > del) {
            x = (a+b)/2;
            if ((f(a)*f(x)) < 0) {
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

    }

    // Method to provide function f(x)=exp(x)*log(x)-x*x.

    public static double f(double x) {
        return Math.exp(x)*Math.log(x)-x*x;
    }
}
