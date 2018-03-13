package mianshi1;

/**
 * Created by baozi on 13/03/2018.
 * 编写两个线程，一个线程打印1~25，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。
 * 使用synchronized做线程间通信, 使用lock的status来防止死锁。
 */
public class ThreadThree {

    private Lock threadStatus = new Lock();

    static class Lock {
        int status = 1;
    }

    public Runnable newThreadOne() {
        final int max = 52;
        return new Runnable() {
            String[] numArray = Helper.buildNumArray(max);

            @Override
            public void run() {
                for (int i = 0; i < max; i += 2) {
                    while (threadStatus.status == 2) {
                    }
                    Helper.printStr(numArray[i], numArray[i + 1]);
                    threadStatus.status = 2;
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
                for (int i = 0; i < max; i++) {
                    while (threadStatus.status == 1) {
                    }
                    Helper.printStr(charArray[i]);
                    threadStatus.status = 1;
                }
            }
        };
    }


    public static void main(String[] args) {
        ThreadThree t = new ThreadThree();
        Runnable one = t.newThreadOne();
        Runnable two = t.newThreadTwo();
        Helper.run(one);
        Helper.run(two);
        Helper.shutDown();
    }
}
