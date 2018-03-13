package simthread;

/**
 * Created by baozi on 2018/1/16.
 */
public class ThreadTest extends Thread{


    public ThreadTest() {
        super();
    }

    @Override
    public void run() {
        System.out.println("Extends Thread start");

    }

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
    }

}


