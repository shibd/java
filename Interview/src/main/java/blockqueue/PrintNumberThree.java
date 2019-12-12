package blockqueue;

public class PrintNumberThree extends Thread {

    /**
     * 共享变量
     */
    private volatile static int startNum = 1;

    /**
     * 共享锁
     */
    private Object lock = PrintNumber.class;

    /**
     * 协作数组
     */
    private volatile static boolean[] stats = new boolean[3];

    /**
     * 线程标识
     */
    private int me;

    public PrintNumberThree(int me) {
        this.me = me;
    }

    public static void main(String[] args) {
        stats[0] = true;
        new PrintNumberThree(0).start();
        new PrintNumberThree(1).start();
        new PrintNumberThree(2).start();
    }

    private boolean isMe() {
        return stats[me];
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (startNum < 100) {
                    while (!isMe()) {
                        // 在此判断需要退出线程
                        if (startNum >= 100) {
                            return;
                        }
                        lock.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + startNum);
                    startNum++;
                    if (startNum % 10 == 1) {
                        stats[me] = false;
                        stats[(me + 1) % 3] = true;
                    }

                    // 临界点临时解决
                    if (startNum == 99) {
                        stats[0] = false;
                        stats[1] = false;
                        stats[2] = false;
                        startNum++;
                        System.out.println(Thread.currentThread().getName() + ":" + startNum);
                    }
//                    System.out.println(stats[0] + " " + stats[1] + " " + stats[2]);
                    lock.notifyAll();
                }
            }
        } catch (Exception e) {
        }
    }
}
