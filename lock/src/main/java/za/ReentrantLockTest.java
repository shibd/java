package za;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by baozi on 2018/1/11.
 */
public class ReentrantLockTest {

    private Lock lock = new ReentrantLock();

    public void test() {

        Condition condition = lock.newCondition();

    }

    public static void main(String[] args) {


    }
}
