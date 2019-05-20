package executorthread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: baozi
 * @Date: 2019/4/26 14:30
 * @Description:
 */
public class FutureTaskTest3 implements Runnable{

    private static final ExecutorService executor = new ThreadPoolExecutor(8,
            8,
            1,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000)
    );

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new FutureTaskTest3()).start();
        new Thread(new FutureTaskTest3()).start();
        new Thread(new FutureTaskTest3()).start();
//        executor.shutdown();
    }

    private void save(String result) {
        System.out.println("save:" + result);
    }

    @Override
    public void run() {
        FutureTask inquiryTask1 = new FutureTask(new InquiryTask1());
        FutureTask inquiryTask2 = new FutureTask(new InquiryTask2());
        FutureTask inquiryTask3 = new FutureTask(new InquiryTask3());
        List<FutureTask> futureTasks = Arrays.asList(inquiryTask1, inquiryTask2, inquiryTask3);
        executor.submit(inquiryTask1);
        executor.submit(inquiryTask2);
        executor.submit(inquiryTask3);
        for (FutureTask<String> stringFutureTask : futureTasks) {
            try {
                save(stringFutureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

class InquiryTask1 implements Callable<String> {
    private String getInquiryTask1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "getInquiryTask1";
    }
    @Override
    public String call() throws Exception {
        return getInquiryTask1();
    }
}
class InquiryTask2 implements Callable<String> {
    private String getInquiryTask2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "getInquiryTask2";
    }
    @Override
    public String call() throws Exception {
        return getInquiryTask2();
    }
}
class InquiryTask3 implements Callable<String> {
    private String getInquiryTask3() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "getInquiryTask3";
    }
    @Override
    public String call() throws Exception {
        return getInquiryTask3();
    }
}
