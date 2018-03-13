package za;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baozi on 2018/2/22.
 */
public class LockTest {


    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (lock1.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + " 锁住lock1");
                        try {
                            if (lock2.tryLock()) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " 锁住lock1 + lock2");
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            }
                        } finally {
                            lock1.unlock();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + "获取锁失败，重新获取---");
                    try {
                        //防止发生活锁
                        TimeUnit.NANOSECONDS.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock2.lock();
                    if (lock2.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + " 锁住lock2");
                        try {
                            try {
                                if (lock1.tryLock()) {
                                    System.out.println(Thread.currentThread().getName() + " 锁住lock2 + lock2");
                                    break;
                                }
                            } finally {
                                lock1.unlock();
                            }
                        } finally {
                            lock2.unlock();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + "获取锁失败，重新获取---");
                    try {
                        //防止发生活锁
                        TimeUnit.NANOSECONDS.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
