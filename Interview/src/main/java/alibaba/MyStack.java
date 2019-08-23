package alibaba;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 21:27
 * @Description:
 */
// 你能检查出"内存泄露"吗？
public class MyStack<T> {
    private static final int INIT_CAPACITY = 16;
    private T[] elements;
    private int size = 0;

    public MyStack() {
        elements = (T[]) new Object[INIT_CAPACITY];
    }

    public void push(T elem) {
        ensureCapacity();
        elements[size++] = elem;
    }

    public T pop() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    /**
     * 保证栈能自动增长，当栈中空间不足时，自动增长为原长度的两倍
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public static void main(String[] args) {
        MyStack<String> myStack = new MyStack();
    }
}
