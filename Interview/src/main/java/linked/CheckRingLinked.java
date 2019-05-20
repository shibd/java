package linked;

import static linked.Node.addNode;
import static linked.Node.print;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 09:55
 * @Description: 检测链表是否有环
 */
public class CheckRingLinked {


    public static boolean ring(Node head) {
        if (head == null || head.next == null) {
            throw new RuntimeException("链表为空");
        }
        Node slowNode = head.next;
        Node fastNode = head.next.next;

        while (true) {
            System.out.println(slowNode.message);
            if (fastNode == slowNode) {
                return true;
            }
            fastNode = fastNode.next;
            if (fastNode == null) {
                return false;
            }
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(-1);
        Node node = new Node(2);
        addNode(head, node);
        addNode(head, new Node(5));
        addNode(head, new Node(3));
        addNode(head, new Node(6));
        addNode(head, new Node(9));
        addNode(head, new Node(8));
        Node tail = new Node(10);
        tail.next = node;
        addNode(head, tail);
//        print(head);

        System.out.println(ring(head));
    }
}
