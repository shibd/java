package pool;

/**
 * Created by baozi on 2018/1/19.
 */
public class ThreadPoolSource {

    public static final int COUNT_BITS = Integer.SIZE - 3;
    public static final int CAPACITY   = (1 << 29) - 1;
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    public static void main(String[] args) {
        System.out.println("COUNT_BITS:" + COUNT_BITS);
        System.out.println("CAPACITY:" + CAPACITY);
        System.out.println("RUNNING:" + RUNNING);
        System.out.println("SHUTDOWN:" + SHUTDOWN);
        System.out.println("STOP:" + STOP);
        System.out.println("TIDYING:" + TIDYING);
        System.out.println("TERMINATED:" + TERMINATED);
        System.out.println(9 & CAPACITY);

    }
}
