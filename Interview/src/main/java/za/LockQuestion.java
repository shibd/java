package za; /**
 * Created by baozi on 2018/2/22.
 */

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by zjw on 2017/8/9.
 */
public class LockQuestion {
    public static void main(String[] args) {
        Thread th1 = new Thread(new DeadLock(true));
        Thread th2 = new Thread(new DeadLock(false));
        th1.setName("线程1");
        th2.setName("线程2");
        th1.start();
        th2.start();
    }
    static class DeadLock implements Runnable {
        private final static Object o1 = new Object();
        private final static Object o2 = new Object();
        private final static Semaphore a1 = new Semaphore(1);
        private final static Semaphore a2 = new Semaphore(1);
        boolean lockFlag;
        DeadLock(boolean lockFlag) {
            this.lockFlag = lockFlag;
        }
        @Override
        public void run() {
            try {
                if (lockFlag) {
                    if (a1.tryAcquire(1, TimeUnit.SECONDS)) {
                        synchronized (o1) {
                            try {
                                System.out.println(Thread.currentThread().getName()  + " " + new Date().toString() + " Lock 锁住 o1");
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (a2.tryAcquire(1, TimeUnit.SECONDS)) {
                                synchronized (o2) {
                                    System.out.println(Thread.currentThread().getName() + " " + new Date().toString() + "Lock 锁住 o2");
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + " " + new Date().toString() + "Lock 锁 o2 失败");
                            }
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + new Date().toString() + "Lock 锁 o1 失败");
                    }
                    a1.release();
                    a2.release();
                } else {
                    if (a1.tryAcquire(1, TimeUnit.SECONDS)) {
                        synchronized (o2) {
                            try {
                                System.out.println(new Date().toString() + " Lock 锁住 o1");
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (a2.tryAcquire(1, TimeUnit.SECONDS)) {
                                synchronized (o1) {
                                    System.out.println(Thread.currentThread().getName()  + " " + new Date().toString() + "Lock 锁住 o2");
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName()  + " " + new Date().toString() + "Lock 锁 o2 失败");
                            }
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + new Date().toString() + "Lock 锁 o1 失败");
                    }
                    a1.release();
                    a2.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
