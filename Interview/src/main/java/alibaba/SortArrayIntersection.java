package alibaba;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: baozi
 * @Date: 2019/4/9 19:35
 * @Description:
 * 两个整数求交集,单个二分法
 */
public class SortArrayIntersection {


    public static List<Integer> intersection(int[] array1, int[] array2) {

        List<Integer> results = new ArrayList<Integer>();

        for (int i = 0; i < array1.length; i++) {
            if (select(array2, array1[i])) {
                results.add(array1[i]);
            }
        }

        return results;
    }

    public static boolean select(int[] a, int num) {
        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (a[mid] == num) {
                return true;
            } else if (a[mid] > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        int[] a = {1, 2, 3, 4, 5, 6};
        int[] b = {3, 6};

        System.out.println(intersection(a, b));
    }
}
