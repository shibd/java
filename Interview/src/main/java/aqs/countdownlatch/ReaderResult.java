package aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 获取计算结果并输出
 *
 * @author leizhimin 2008-9-20 11:15:22
 */
public class ReaderResult extends Thread {
    Calculator c;
    CountDownLatch counter;

    public ReaderResult(Calculator c, CountDownLatch counter) {
        this.counter = counter;
        this.c = c;
    }

    public void run() {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (c) {
            try {
                System.out.println(Thread.currentThread() + "等待计算结果。。。");
                counter.countDown();
                c.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "计算结果为：" + c.total);
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        CountDownLatch counter = new CountDownLatch(3);

        //启动三个线程，分别获取计算结果
        ReaderResult t1 = new ReaderResult(calculator, counter);
        ReaderResult t2 = new ReaderResult(calculator, counter);
        ReaderResult t3 = new ReaderResult(calculator, counter);
        t1.start();
        t2.start();
        t3.start();

        //启动计算线程
        try {
            counter.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calculator.start();
    }
}
