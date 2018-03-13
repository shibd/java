package simthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by baozi on 2018/1/19.
 */
public class CallableTest implements Callable<String>{

    public String call() throws Exception {
        return "wo shi baozi";
    }


    public static void main(String[] args) {

        CallableTest ct = new CallableTest();
        FutureTask<String> f = new FutureTask<String>(ct);

        Thread t = new Thread(f);
        t.start();

        try {
            String s = f.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        RCallableT rt = new RCallableT();
        FutureTask<String> f2 = new FutureTask(rt, String.class);
        try {
            String s = f2.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(f2);
        t2.start();

    }
}

class RCallableT implements Runnable {
    public void run() {
        System.out.println("baozi");
        return ;
    }
}
