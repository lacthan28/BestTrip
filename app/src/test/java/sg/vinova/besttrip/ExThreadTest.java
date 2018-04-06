package sg.vinova.besttrip;

import android.util.Log;

import org.junit.Test;

public class ExThreadTest {
    private  volatile int MY_INT = 0;

    @Test
    public void mainTest() {
        new ChangeListener().start();
        new ChangeMaker().start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert true;
    }

     class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while ( local_value < 5){
                if( local_value!= MY_INT){
                    System.out.println("ChangeListener" + "Got Change for MY_INT : {0}" + MY_INT);
                    local_value= MY_INT;
                }
            }
        }
    }

     class ChangeMaker extends Thread{
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT <5){
                int result = local_value+1;
                System.out.println("ChangeMaker: " + "Incrementing MY_INT to {0}" + result);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
