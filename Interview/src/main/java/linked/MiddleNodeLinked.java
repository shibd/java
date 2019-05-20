package linked;

import static linked.Node.addNode;
import static linked.Node.print;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 17:44
 * @Description:
 * 求链表的中间节点
 */
public class MiddleNodeLinked {


    public static Node middle(Node head) {

        if (head == null || head.next == null) {
            throw new RuntimeException("链表为空");
        }
        Node fastNode = head;
        Node slowNode = head;

        while (fastNode != null) {
            fastNode = fastNode.next;
            if (fastNode == null) {
                break;
            }
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }

        return slowNode;
    }


    public static void main(String[] args) {
        Node head = new Node(-1);
        addNode(head, new Node(2));
        addNode(head, new Node(5));
        addNode(head, new Node(3));
        addNode(head, new Node(6));
        addNode(head, new Node(9));
        addNode(head, new Node(8));
        print(head);

        Node middle = middle(head);
        System.out.println(middle.message);
    }
}
