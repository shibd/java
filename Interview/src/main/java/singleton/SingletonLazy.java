package singleton;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: baozi
 * @Date: 2019/3/4 13:30
 * @Description:
 */
public class SingletonLazy {

    private SingletonLazy() {}

    private static volatile SingletonLazy singletonLazy = null;

    public static SingletonLazy getInstance() {
        if (singletonLazy == null) {
            synchronized (SingletonLazy.class) {
                if (singletonLazy == null) {
                    singletonLazy = new SingletonLazy();
                }
            }
        }
        return singletonLazy;
    }

    public static volatile Set<Integer> hashCodeSet = new HashSet<Integer>();

    public static void main(String[] args) {
        int threadNum = 50;
        while (threadNum-- > 0) {
            new Thread(new Runnable() {
                public void run() {
                    hashCodeSet.add(SingletonLazy.getInstance().hashCode());
                }
            }).start();
        }

        if (hashCodeSet.size() == 1) {
            System.out.println("is ok, " + hashCodeSet);
        } else {
            throw new RuntimeException("error");
        }
    }
}
