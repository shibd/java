package linked;

import static linked.Node.addNode;
import static linked.Node.print;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 09:39
 * @Description:
 * 翻转链表
 */
public class ReversalLinked {

    public static void reversal(Node head) {

        if (head == null) {
            throw new RuntimeException("head为空");
        }
        Node preNode = head.next;
        if (preNode == null) {
            throw new RuntimeException("链表问空");
        }
        Node curNode = preNode.next;
        if (curNode == null) {
            return;
        }
        preNode.next = null;

        while (curNode != null) {
            Node tmpNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = tmpNode;
        }
        head.next = preNode;
    }



    public static void main(String[] args) {
        Node head = new Node(-1);
        addNode(head, new Node(2));
        addNode(head, new Node(5));
//        addNode(head, new Node(3));
//        addNode(head, new Node(6));
//        addNode(head, new Node(8));
        reversal(head);

        print(head);
    }
}
