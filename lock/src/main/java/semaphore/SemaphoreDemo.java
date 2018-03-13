package semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by baozi on 2018/1/23.
 */
public class SemaphoreDemo {


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new SemaphoreTest()).start();
        }

    }

    static class SemaphoreTest implements Runnable {

        public static Semaphore semaphore = new Semaphore(1,false);

        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread() + "getting semaphore");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread() + "realsing semaphore");
                semaphore.release();
            } catch (Exception e) {
            }
        }
    }
}

