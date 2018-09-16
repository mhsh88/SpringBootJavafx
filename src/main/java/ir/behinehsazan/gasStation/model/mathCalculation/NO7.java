package ir.behinehsazan.gasStation.model.mathCalculation;///////////////////////////////////////////////////////////////////////////
//                                                                       //
// 	 Newton						                                        //
//behzad torkian
//                                                          //
///////////////////////////////////////////////////////////////////////////


public class NO7 {
  public static void main(String argv[]) {
    double del = 1e-5,xx = 0 ;
    double dx =0, x=Math.PI/2;
    int k = 0;
    while (Math.abs(xx-x) > del && k<10 && f(x)!=0) {
      dx = f(x)/d(x);
      xx=x;
      x =x - dx;
      k++;
    
    System.out.println("Iteration number: " + k);
    System.out.println("Root obtained: " + x);
    System.out.println("Estimated error: " + Math.abs(xx-x));
    }
  }

// Method to provide function f(x)

  public static double f(double x) {
    return 1/2+1/4*x*x-x*Math.sin(x)-1/2*Math.cos(2*x);
  }

// Method to provide the derivative f'(x).

  public static double d(double x) {
    return 1./2*x-(x*Math.cos(x)+Math.sin(x))+Math.sin(2*x);
  }
}

// it going to infinty and we cann not find the root by using this method 
// look setp 2 
// output 
/*Iteration number: 1
Root obtained: -2.220446049250313E-16
Estimated error: 1.5707963267948968
Iteration number: 2
Root obtained: Infinity
Estimated error: Infinity
Iteration number: 3
Root obtained: NaN
Estimated error: NaN
Press any key to continue...*/