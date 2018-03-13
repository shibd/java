package za;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baozi on 2018/2/7.
 */
public class TA implements Runnable {

    public TA() {
        System.out.println("test2");
    }

    @Override
    public void run() {
        try {
            Thread.yield();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("test1");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new TA());
        t.start();
        t.join();
        System.out.println("test3");

        Map<String, String> map = new HashMap<String, String>();
    }
}
