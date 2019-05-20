package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * https://www.cnblogs.com/zzlp/p/5174745.html
 *  * 用读写锁实现的一个缓存系统，读的时候可以并发执行，
 *  * 当缓存中没有数据或者数据超过30分钟时，要到数据库中查询数据
 *  * 此时只能写数据，不能读数据。当完数据之后，又可以并发地读取数据。
 *  * 这样做的话，可以提高 系统的效率
 * @Auther: baozi
 * @Date: 2019/3/4 10:43
 * @Description:
 */
public class CacheDemo {

    private static final long MINUTE_30 = 1000 * 60 * 30;

    private Map<String, Message> map = new HashMap<String, Message>();//缓存器

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        CacheDemo cacheDemo = new CacheDemo();
        System.out.println(cacheDemo.get("wo"));
    }

    public Message get(String id) {
        Message value = null;
        rwl.readLock().lock();//首先开启读锁，从缓存中去取
        try {
            value = map.get(id);
            if (value == null || (System.currentTimeMillis() - value.getLastUpdateTime() > MINUTE_30)) {  //如果缓存中没有释放读锁，上写锁
                // java不知道锁升级,必须先释放读锁,再获取写锁
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    if (value == null || (System.currentTimeMillis() - value.getLastUpdateTime() > MINUTE_30)) {
                        value = selectObjectById(id);  //此时可以去数据库中查找，这里简单的模拟一下
                        // update
                        map.put(id, value);
                    }
                    rwl.readLock().lock(); //降级为读锁
                } finally {
                    rwl.writeLock().unlock(); //释放写锁
                }
            }
        } finally {
            rwl.readLock().unlock(); //最后释放读锁
        }
        return value;
    }

    public void updateCache(String id) {

    }

    /**
     * 查询数据库获取最新message
     * @param id
     * @return
     */
    public Message selectObjectById(String id) {
        return new Message(id, System.currentTimeMillis(), "mock:" + id);
    }

}

class Message {
    private String id;
    private long lastUpdateTime;
    private Object message;

    public Message() {
    }

    public Message(String id, long lastUpdateTime, Object message) {
        this.id = id;
        this.lastUpdateTime = lastUpdateTime;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", message=" + message +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
