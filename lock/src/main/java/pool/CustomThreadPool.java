package pool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baozi on 2018/1/24.
 */
public class CustomThreadPool {

    private static final Lock lock = new ReentrantLock();
    private static Condition con = lock.newCondition();



    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        ExecutorService executorService = Executors.newFixedThreadPool(4);

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 20,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10));
//        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 40; i++) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            executorService.execute(new ThreadTest());
//            Future<String> submit = executorService.submit(new CallTest());
//            String s = submit.get();
//            System.out.println(s);
        }
        System.out.println(Thread.currentThread().getName());
        executorService.shutdown();
    }

    static class ThreadTest implements Runnable {

        public void run() {
            try {

                con.await();
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(6000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class CallTest implements Callable<String> {

        public String call() throws Exception {
            return Thread.currentThread().getName();
        }
    }

}

