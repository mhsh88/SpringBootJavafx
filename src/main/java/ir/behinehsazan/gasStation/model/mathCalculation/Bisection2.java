package ir.behinehsazan.gasStation.model.mathCalculation;



public class Bisection2
{

    public static void main(String args[])
    {

    }

    private double precision = .000001;
    private double cubic(double x)
    {
        double Fn = ((Math.pow(x,3)) - (7 * (Math.pow(x,2))) + (5 * x) + 3);
        return (Fn);
    }
    private  double bisector(double left, double right)
    {
        double midpoint;
        while (Math.abs(right - left) > precision)
        {
            // Find the Midpoint of the funtion
            midpoint = ((left + right) / 2);
            System.out.print(midpoint);
            System.out.print("\n");
            //determine the appropriate half to search in
            if ((cubic(left) * cubic(midpoint)) > 0)
                left = midpoint;
            else
                right = midpoint;
        }
        return ((left + right) / 2);
    }
    private int Main()
    {
        System.out.print(bisector(0, 2));
        System.out.print("\n");
        System.out.print(bisector(5, 7));
        System.out.print("\n");
        return 0;
    }
}


