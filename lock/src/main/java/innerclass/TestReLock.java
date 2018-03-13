package innerclass;

/**
 * Created by baozi on 2018/1/24.
 */
public class TestReLock {

    private Sync sync;

    public void newSync() {
        sync = new Sync();
    }

    public TestAQS.TestConditionObject newCondition() {
        return sync.newContion();
    }

    public class Sync extends TestAQS {

        public TestConditionObject newContion() {
           return new TestConditionObject();
        }
    }
}

