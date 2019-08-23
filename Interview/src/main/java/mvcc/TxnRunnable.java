package mvcc;

/**
 * @author: baozi
 * @date: 2019/8/22 17:36
 * @description:
 */
@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}
