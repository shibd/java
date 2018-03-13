package innerclass;


/**
 * Created by baozi on 2018/1/24.
 */
public class TestAQS {

    public int a = 10;

    final long fullyRelease() {
        System.out.println("test" + a);
        return 0;
    }


    public class TestConditionObject {

        public final void await() {
            fullyRelease();
        }
    }
}
