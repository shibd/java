package blockqueue;

/**
 * @author: baozi
 * @date: 2019/9/19 16:50
 * @description:
 */
public class PrintNumber extends Thread {

    // 共享变量
    private static int startNum = 1;

    // 同步锁
    private Object lock;
    // 条件变量
    private String type;

    public PrintNumber(Object lock, String type) {
        this.lock = lock;
        this.type = type;
    }

    public static void main(String[] args) {
        Object lock = new Object();
        new PrintNumber(lock, "odd").start();
        new PrintNumber(lock, "even").start();
    }

    /**
     * 条件变量方法
     *
     * @return
     */
    private boolean isMe() {
        return (type.equals("odd") && startNum % 2 == 1) || (type.equals("even") && startNum % 2 == 0);
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (startNum < 100) {
                    while (!isMe()) {
                        lock.wait();
                    }
                    System.out.println(startNum);
                    startNum++;
                    lock.notifyAll();
                }
            }
        } catch (Exception e) {
        }
    }
}
