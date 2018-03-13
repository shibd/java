import java.util.Stack;

/**
 * Created by baozi on 13/03/2018.
 */
public class Solution3 {


    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer> min = new Stack<Integer>();

    Integer max = Integer.MAX_VALUE;

    public void push(int node) {
        stack.push(node);
        if (max > node) {
            min.push(node);
            max = node;
        }
    }

    public void pop() {
        Integer pop = stack.pop();
        if (pop == min.peek()) {
            min.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        return min.peek();
    }
}
