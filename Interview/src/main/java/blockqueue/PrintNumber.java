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

    // 共享变量
    private static int startNum = 1;

    // 自己满足的条件变量
    private Condition my;
    // 其他人满足的条件变量
    private Condition other;
    // 同步锁
    private Lock lock;
    // 条件变量
    private String type;

    public PrintNumber(Lock lock, Condition my, Condition other, String type) {
        this.lock = lock;
        this.my = my;
        this.other = other;
        this.type = type;
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition ji = lock.newCondition();
        Condition ou = lock.newCondition();
        new PrintNumber(lock, ji, ou, "odd").start();
        new PrintNumber(lock, ou, ji, "even").start();
    }

    private boolean isMe() {
        return (type.equals("odd") && startNum % 2 == 1) || (type.equals("even") && startNum % 2 == 0);
    }

    @Override
    public void run() {
        try {
            lock.lock();
            while (startNum < 100) {
                while (!isMe()) {
                    my.await();
                }
                System.out.println(startNum);
                startNum++;
                other.signalAll();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }
}
