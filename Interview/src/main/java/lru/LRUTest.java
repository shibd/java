package lru;

/**
 * @author baozi
 * @date 2020/6/20 11:05 AM
 */
public class LRUTest {

    // 防止引用逃逸
    private static volatile LRUTest instance = null;

    private LRUTest() {
    }

    public static LRUTest getInstance() {
        if (instance == null) {
            synchronized (LRUTest.class) {
                if (instance == null) {
                    // new的方法由很多步骤
                    instance = new LRUTest();
                }
            }
        }
        return instance;
    }
}
