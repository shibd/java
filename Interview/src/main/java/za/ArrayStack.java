package za;

/**
 * @Auther: baozi
 * @Date: 2019/3/4 11:12
 * @Description:
 */
public class ArrayStack {
    private Integer[] stack;
    // 栈的个数
    private Integer count;
    // 栈的大小
    private Integer n;

    public ArrayStack(Integer n) {
        this.stack = new Integer[n];
        this.n = n;
        count = 0;
    }

    public boolean push(Integer object) {
        if (count < n) {
            stack[count] = object;
            count ++;
            return true;
        }
        return false;
    }

    public Integer pop() {
        if (count <= 0 ) {
            return null;
        }
        Integer object = stack[count - 1];
        count--;
        return object;
    }

    public Integer peek() {
        if (count != 0) {
            return stack[count - 1];
        }
        return null;
    }

}
