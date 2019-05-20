package sort;

/**
 *
 * @author 无敌小包子
 * @data 2017年4月19日 上午10:36:13
 */
public class BigHeapSort {

    public static void exchange(int A[], int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static int parttion(int A[], int left, int right) {
        // 拿right为基准
        int pivot = A[right];
        int tail = left;
        for (int i = left; i <= right; i++) {
            if (A[i] <= pivot) {
                exchange(A, i, tail);
                tail++;
            }
        }
        return tail - 1;
    }

    public static void quickSort(int A[], int left, int right) {
        int quickIndex;
        if (left < right) {
            System.out.println("left=" + left + " right=" + right);
            quickIndex = parttion(A, left, right);
            System.out.println(A[quickIndex]);
            print(A);
            quickSort(A, left, quickIndex - 1);
            quickSort(A, quickIndex + 1, right);
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

