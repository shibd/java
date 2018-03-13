package pool;

import java.util.concurrent.*;

/**
 * Created by baozi on 2018/1/19.
 */
/**
 * Created by SilenceDut on 16/7/15.
 **/

public class ThreadPool {

    public static void main(String[] args) {
        ThreadPool futureTest = new ThreadPool();
        futureTest.useExecutor();
    }

    private void useExecutor() {

        SumTask sumTask = new SumTask(10);
        ExecutorService executor = Executors.newCachedThreadPool();


        FutureTask<Integer> futureTask = new FutureTask<Integer>(sumTask);
        executor.execute(futureTask);
        executor.execute(futureTask);

//        executor.shutdown();

        try {
            Integer integer = futureTask.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    class SumTask implements Callable<Integer> {
        int number = 0;

        public SumTask(int num) {
            this.number = num;
        }

        public Integer call() throws Exception {

//            System.out.println(Thread.currentThread().getName());
//            Thread.sleep(5000);

            int sum = number;
            for (int i = 0; i < number; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
