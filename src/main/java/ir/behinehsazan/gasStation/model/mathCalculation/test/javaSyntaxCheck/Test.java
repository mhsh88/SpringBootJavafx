package ir.behinehsazan.gasStation.model.mathCalculation.test.javaSyntaxCheck;

import ir.behinehsazan.gasStation.model.base.EntityBase;

public class Test {

    public static void main(String[] args){
        EntityBase t1 = new EntityBase() {
            @Override
            public void calculate() {

            }
        };
        EntityBaseTest t2 = new EntityBaseTest();
        EntityBaseTest2 t3 = new EntityBaseTest2();
        t1.setTenv(54.0);
        System.out.println(t2.getTenv());
        System.out.println(t3.getTenv());
        t3.setTenv(90.0);
        System.out.println(t1.getTenv());




    }
}
