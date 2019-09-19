package blockqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: baozi
 * @date: 2019/9/19 16:50
 * @description:
 */
public class PrintNumber extends Thread {

    Condition my;
    Condition other;
    Lock lock;
    int startNum;

    public PrintNumber(int num, Lock lock, Condition my, Condition other) {
        this.lock = lock;
        this.my = my;
        this.other = other;
        this.startNum = num;
    }

    @Override
    public void run(){
        try {
            lock.lock();
            while (startNum <= 100) {
                System.out.println(startNum);
                startNum += 2;
                other.signalAll();
                my.await();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition ji = lock.newCondition();
        Condition ou = lock.newCondition();
        new PrintNumber(1, lock, ji, ou).start();
        new PrintNumber(2, lock, ou, ji).start();
    }
}
