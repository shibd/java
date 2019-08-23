package mvcc;

/**
 * @author: baozi
 * @date: 2019/8/22 17:31
 * @description:
 */
public final class VersionedRef<T> {
    final T value;
    final long version;
    // 构造方法
    public VersionedRef(T value, long version) {
        this.value = value;
        this.version = version;
    }
}
