package simthread;

/**
 * Created by baozi on 2018/1/16.
 */
public class ThreadTest2 implements Runnable{

    public void run() {
        System.out.println("Implements Runnable Start");
    }

    public static void main(String[] args) {
        ThreadTest2 test2 = new ThreadTest2();
        Thread t = new Thread(test2);
        t.start();
    }

}
