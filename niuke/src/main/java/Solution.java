/**
 * Created by baozi on 2018/3/11.
 */
public class Solution {

    public long fibonacci(int n) {

        int result[] = {0, 1};
        if (n < 2) {
            return result[n];
        }

        long fOne = 0;
        long fTwo = 1;
        long fN = 0;

        for (int i = 2; i <= n; ++i) {
            fN = fOne + fTwo;
            fOne = fTwo;
            fTwo = fN;
        }
        return fN;
    }

    public static double power(double base, int exponent) {

        if (base == 0) {
            return 0;
        }

        double result = 1.0;
        int ee = Math.abs(exponent);

        for (int i = 0; i < ee; i++) {
            result *= base;
        }

        if (exponent < 0) {
            return 1 / result;
        }
        return result;

    }

    public static void reOrderArray(int[] a) {

        int n = a.length;
        int left = 0;
        int right;

        while (left < n) {

            while (left < n && !isEvent(a[left]))
                left++;

            right = left + 1;

            while (right < n && isEvent(a[right]))
                right++;

            if (right < n) {
                int tmp = a[right];
                for (int right2 = right - 1; right2 >= left; right2--) {
                    a[right2 + 1] = a[right2];
                }
                a[left++] = tmp;
            } else {
                break;
            }
        }

    }

    public static boolean isEvent(int n) {
        return n % 2 == 0 ? true : false;
    }

    public static ListNode FindKthToTail(ListNode head, int k) {

        if (head == null || k == 0) {
            return null;
        }

        ListNode head2 = head;

        while (k-- > 1) {
            if (head.next != null) {
                head = head.next;
            } else {
                return null;
            }
        }

        while (head.next != null) {
            head = head.next;
            head2 = head2.next;
        }
        return head2;
    }

    public static ListNode reverseList(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode nextNode = head.next;
        head.next = null;
        ListNode lastNode = head;
        ListNode node = head;

        while (nextNode != null) {
            node = nextNode;
            nextNode = node.next;

            //反转
            node.next = lastNode;
            lastNode = node;
        }
        return node;
    }

    public static ListNode merge(ListNode list1,ListNode list2) {

        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }

        ListNode mergeHead = null;

        if (list1.val < list2.val) {
            mergeHead = list1;
            mergeHead.next = merge(list1.next, list2);
        } else {
            mergeHead = list2;
            mergeHead.next = merge(list1, list2.next);
        }
        return mergeHead;
    }

    public static void pList(ListNode head) {
        if (head == null) {
            System.out.println("head is null");
            return ;
        }
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(3);
        ListNode l3 = new ListNode(8);
        ListNode l4 = new ListNode(10);
        ListNode l5 = new ListNode(14);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

        ListNode ll1 = new ListNode(2);
        ListNode ll2 = new ListNode(5);
        ListNode ll3 = new ListNode(7);
        ListNode ll4 = new ListNode(9);
        ListNode ll5 = new ListNode(11);
        ListNode ll6 = new ListNode(15);
        ll1.next = ll2;
        ll2.next = ll3;
        ll3.next = ll4;
        ll4.next = ll5;
        ll5.next = ll6;


        pList(merge(l1, ll1));
//        System.out.println(merge(l1, ll1));
    }


}



class ListNode {
    int val;
    ListNode next = null;

    public ListNode(int val) {
        this.val = val;
    }
}
