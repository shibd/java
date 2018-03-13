package za;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baozi on 2018/1/11.
 */
public class ProductQueue {

    private final Object[] items;
    private final Lock lock = new ReentrantLock(true);
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int head, tail, count;

    public ProductQueue(int maxSize) {
        items = new Object[maxSize];
    }

    public ProductQueue() {
        this(10);
    }

    public void put(Object t) throws InterruptedException {
        lock.lock();
        try {
            while (count == getCapacity()) {
                notFull.await();
            }
            items[tail] = t;
            if (++tail == getCapacity()) {
                tail = 0;
            }
            ++count;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print() {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                System.out.print(items[i]);
            }
        }
        System.out.println();
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object ret = items[head];
            items[head] = null;//GC
            if (++head == getCapacity()) {
                head = 0;
            }
            --count;
            notFull.signalAll();
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final ProductQueue queue = new ProductQueue(10);

        //生产线程
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        try {
                            queue.put(i + " ");
                            System.out.print("生产");
                            queue.print();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        //消费线程
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        queue.take();
                        System.out.print("消费");
                        queue.print();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
