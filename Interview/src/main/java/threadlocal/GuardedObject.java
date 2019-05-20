package threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @Auther: baozi
 * @Date: 2019/5/10 18:39
 * @Description:
 */
class GuardedObject<T> {
    // 保存所有 GuardedObject
    final static Map<Object, GuardedObject>
            gos = new ConcurrentHashMap<>();
    final Lock lock =
            new ReentrantLock();
    final Condition done =
            lock.newCondition();
    final int timeout = 2;
    // 受保护的对象
    T obj;

    // 静态方法创建 GuardedObject
    static <K> GuardedObject
    create(K key) {
        GuardedObject go = new GuardedObject();
        gos.put(key, go);
        return go;
    }

    static <K, T> void
    fireEvent(K key, T obj) {
        GuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }


    // 获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA 管程推荐写法
            while (!p.test(obj)) {
                done.await(timeout,
                        TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        // 返回非空的受保护对象
        return obj;
    }

    // 事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }



    public static void main(String[] args) {


        new Thread(() -> {
            System.out.println("send message");
            GuardedObject<Msg> guardedObject = GuardedObject.create("id");
            Msg msg = guardedObject.get(
                    t -> t != null
            );
            System.out.println(msg.result);
        }).start();

        new Thread(() -> {
            // 没有发送，这里不可能收到ID的，所以模拟这个收发过程
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("recive message");
            Msg msg = new Msg();
            msg.id = "id";
            msg.result = "i am result";
            GuardedObject.fireEvent("id", msg);
        }).start();
    }
}

class Msg {
    public String id;
    public String result;
}
