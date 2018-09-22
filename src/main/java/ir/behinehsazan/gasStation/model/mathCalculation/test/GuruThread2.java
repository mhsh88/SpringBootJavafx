package ir.behinehsazan.gasStation.model.mathCalculation.test;

public class GuruThread2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        GuruThread3 threadguru1 = new GuruThread3("guru1");
        threadguru1.start();
        GuruThread3 threadguru2 = new GuruThread3("guru2");
        threadguru2.start();
    }
}
class GuruThread3 implements Runnable {
    Thread guruthread;
    private String guruname;
    GuruThread3(String name) {
        guruname = name;
    }
    @Override
    public void run() {
        System.out.println("Thread running " + guruname);
        for (int i = 0; i < 4; i++) {
            System.out.println(i);
            System.out.println(guruname);
            try {
                System.out.println("my adding");
                if(this.guruname.equals("guru1"))
                    Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
            } catch (Exception e) {

            }
        }
    }
    public void start() {
        System.out.println("Thread started");
        if (guruthread == null) {
            guruthread = new Thread(this, guruname);
            guruthread.start();
        }

    }
}