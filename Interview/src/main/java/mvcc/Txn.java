package mvcc;

/**
 * @author: baozi
 * @date: 2019/8/22 17:35
 * @description:
 */
// 事务接口
public interface Txn {
    <T> T get(TxnRef<T> ref);
    <T> void set(TxnRef<T> ref, T value);
}


