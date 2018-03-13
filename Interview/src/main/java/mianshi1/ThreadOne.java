package mianshi1;

/**
 * Created by baozi on 13/03/2018.
 * 编写两个线程，一个线程打印1~25，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。
 * 使用synchronized做线程间通信, 使用lock的status来防止死锁。
 */
public class ThreadOne {

    private final Lock lock = new Lock();

    public Runnable newThreadOne() {
        final int max = 52;
        return new Runnable() {
            String[] numArray = Helper.buildNumArray(max);

            @Override
            public void run() {
                try {
                    for (int i = 0; i < max; i += 2) {
                        synchronized (lock) {
                            while (lock.status == 2) {
                                lock.wait();
                            }
                            Helper.printStr(numArray[i], numArray[i + 1]);
                            lock.status = 2;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public Runnable newThreadTwo() {
        final int max = 26;
        return new Runnable() {
            String[] charArray = Helper.buildCharArray(max);

            @Override
            public void run() {
                try {
                    for (int i = 0; i < max; i++) {
                        synchronized (lock) {
                            while (lock.status == 1) {
                                lock.wait();
                            }
                            Helper.printStr(charArray[i]);
                            lock.status = 1;
                            lock.notifyAll();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    static class Lock {
        int status = 1;
    }

    public static void main(String[] args) {
        ThreadOne t = new ThreadOne();
        Runnable one = t.newThreadOne();
        Runnable two = t.newThreadTwo();
        Helper.run(one);
        Helper.run(two);
        Helper.shutDown();
    }
}
