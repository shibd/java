package sort;

/**
 * @Auther: baozi
 * @Date: 2019/4/8 22:14
 * @Description:
 */
public class QuitSort {

    public static void swap(int A[], int left, int right) {
        int num = A[left];
        A[left] = A[right];
        A[right] = num;
    }

    public static int pation(int A[], int left, int right) {
        int priovt = A[right];
        int tail = left;
        for (int i = left; i <= right; i++) {
            if (A[i] <= priovt) {
                swap(A, i, tail);
                tail++;
            }
        }
        return tail;
    }

    public static void quickSort(int A[], int left, int right) {
        if (left < right) {
            int pation = pation(A, left, right);
            quickSort(A, left, pation - 1);
            quickSort(A, pation, right);
        }
    }

    public static void print(int A[]) {
        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int A[] = { 5, 2, 9, 4, 7, 6, 1, 10, 3, 8 };// 从小到大堆排序
        quickSort(A, 0, A.length - 1);
        print(A);
    }
}
