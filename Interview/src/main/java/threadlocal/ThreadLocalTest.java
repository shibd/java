package threadlocal;

/**
 * @Auther: baozi
 * @Date: 2019/5/10 09:27
 * @Description:
 */
public class ThreadLocalTest {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
    ThreadLocal<String> threadLocal3 = new ThreadLocal<>();


    private void init() {
        threadLocal.set(Thread.currentThread().getName());
        threadLocal2.set(Thread.currentThread().getName() + " this 2");
        threadLocal3.set(Thread.currentThread().getName() + " this 3");
    }


    public void getT() {
        System.out.println(threadLocal.get());
        System.out.println(threadLocal2.get());
        System.out.println(threadLocal3.get());
    }


    public static void main(String[] args) {

        ThreadLocalTest threadLocalTest = new ThreadLocalTest();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                threadLocalTest.init();
                threadLocalTest.getT();
            }).start();
        }
    }
}
