package innerclass;

/**
 * Created by baozi on 2018/1/24.
 */
public class Test {

    public static void main(String[] args) {

        TestReLock lock = new TestReLock();
        lock.newSync();
        TestAQS.TestConditionObject con = lock.newCondition();
        con.await();

        //
//        TestAQS.TestConditionObject tt = new TestAQS.TestConditionObject();
    }
}
