package za;

/**
 * @Auther: baozi
 * @Date: 2019/3/4 13:51
 * @Description:
 */
public class ArrayQueue {

    private Object[] queue;
    private int n;

    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int n) {
        queue = new Object[n];
        this.n = n;
    }

    public boolean enqueue(Object object) {
        if (tail < n) {
            queue[tail++] = object;
            return true;
        }
        return false;
    }

    public Object dequeue() {
        if (head < n) {
            return queue[head++];
        }
        return null;
    }

}
