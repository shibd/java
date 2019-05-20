package linked;

import static linked.Node.*;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 09:19
 * @Description:
 * 删除链表倒数第N个结点
 */
public class RemoveNLinked {


    public static void removeN(Node head, int n) {

        if (head == null) {
            throw new RuntimeException("head is null");
        }

        Node probe = head;
        Node curr = head;
        while (n-- > 0) {
            probe = probe.next;
            if (probe == null) {
                throw new RuntimeException("链表长度小于N,probe is null");
            }
        }
        System.out.println("probe" + probe.message);

        while (probe.next != null) {
            probe = probe.next;
            curr = curr.next;
        }

        curr.next = curr.next.next;
    }



    public static void main(String[] args) {
        Node head = new Node(-1);
        addNode(head, new Node(2));
        addNode(head, new Node(5));
        addNode(head, new Node(3));
        addNode(head, new Node(6));
        addNode(head, new Node(8));
        print(head);

        removeN(head, 4);
        print(head);
    }

}

