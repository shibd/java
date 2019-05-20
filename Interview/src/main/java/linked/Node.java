package linked;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 09:27
 * @Description:
 */
class Node {
    int message;
    Node next;
    public Node(int message) {
        this.message = message;
    }

    public static void print(Node head) {
        while (head.next != null) {
            head = head.next;
            System.out.print(head.message + " ");
        }
        System.out.println();
    }

    public static void addNode(Node head, Node node) {
        while (head.next != null) {
            head = head.next;
        }
        head.next = node;
    }
}
