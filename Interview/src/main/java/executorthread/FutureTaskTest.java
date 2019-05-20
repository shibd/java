package executorthread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: baozi
 * @Date: 2019/4/26 14:18
 * @Description:
 */
public class FutureTaskTest {

    public static void main(String[] args) {

        FutureTask<String> stringFutureTask = new FutureTask<String>(new Runnable() {
            @Override
            public void run() {
                System.out.println("i am executorthread");
            }
        }, "baozi");

        stringFutureTask.run();
        try {
            String s = stringFutureTask.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
